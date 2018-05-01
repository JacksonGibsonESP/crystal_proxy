package ru.mai.dep810.webapp.repository;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mai.dep810.webapp.model.ArticleContent;
import ru.mai.dep810.webapp.model.RowItem;
import ru.mai.dep810.webapp.model.SearchResult;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class ElasticRepository {

    @Autowired
    private Client client;

    @Value("${elastic.index.name}")
    private String indexName;

    @Value("${elastic.highlights.amount}")
    private Integer amount;

    @Value("${elastic.highlights.length}")
    private Integer length;

    public SearchResult<RowItem> search(
            String query,
            String chemicalElement,
            String chemicalFormula,
            String crystalSystem,
            String radiusType,
            String spaceGroup,
            int from,
            int size) {
        String queryStr = "";

        if (StringUtils.isNotBlank(query)) {
            queryStr += query;
        }
        if (StringUtils.isNotBlank(chemicalElement)) {
            queryStr += " " + chemicalElement;
        }
        if (StringUtils.isNotBlank(chemicalFormula)) {
            queryStr += " " + chemicalFormula;
        }
        if (StringUtils.isNotBlank(crystalSystem)) {
            queryStr += " " + crystalSystem;
        }
        if (StringUtils.isNotBlank(radiusType)) {
            queryStr += " " + radiusType;
        }
        if (StringUtils.isNotBlank(spaceGroup)) {
            queryStr += " " + spaceGroup;
        }

        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(queryStr, "attachment.content", "filename");

        multiMatchQueryBuilder.analyzer("crystal");

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<mark>");
        highlightBuilder.postTags("</mark>");
        highlightBuilder.field("attachment.content").highlighterType("plain");
        highlightBuilder.field("filename").highlighterType("plain");
        highlightBuilder.fragmentSize(length);
        highlightBuilder.numOfFragments(amount);

        ActionFuture<SearchResponse> execute = client.prepareSearch(indexName)
                .setQuery(multiMatchQueryBuilder)
                .setFetchSource(new String[] {
                        "_id",
                        "filename",
                        "attachment.author",
                        "attachment.date",
                        "attachment.title"
                }, new String[] {})
                .setFrom(from)
                .setSize(size)
                .highlighter(highlightBuilder)
                .execute();

        return convertToSearchResult(execute);
    }

    private SearchResult<RowItem> convertToSearchResult(ActionFuture<SearchResponse> execute) {
        try {
            SearchResponse searchResponse = execute.get();

            List<RowItem> rows = Arrays.stream(searchResponse.getHits().getHits())
                    .map(e -> {
                        RowItem rowItem = new RowItem();
                        rowItem.set_id(e.getId());
                        rowItem.setFilename((String)e.getSourceAsMap().get("filename"));
                        if (!e.getHighlightFields().isEmpty()) {
                            if (e.getHighlightFields().get("attachment.content") != null) {
                                rowItem.setHighlights(
                                        Arrays.stream(e.getHighlightFields().get("attachment.content").getFragments())
                                                .map(Text::string).collect(Collectors.toList())
                                );
                            }
                            if (e.getHighlightFields().get("filename") != null) {
                                rowItem.setFilenameHighlights(
                                        Arrays.stream(e.getHighlightFields().get("filename").getFragments())
                                                .map(Text::string).collect(Collectors.toList())
                                );
                            }
                        }
                        if (getAttachmentMember(e, "author") != null) {
                            rowItem.setAuthor((String) getAttachmentMember(e, "author"));
                        }
                        if (getAttachmentMember(e, "date") != null) {
                            rowItem.setDate((String) getAttachmentMember(e, "date"));
                        }
                        if (getAttachmentMember(e, "title") != null) {
                            rowItem.setTitle((String) getAttachmentMember(e, "title"));
                        }
                        return rowItem;
                    })
                    .collect(Collectors.toList());

            return new SearchResult<>(rows, searchResponse.getHits().getTotalHits());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object getAttachmentMember(SearchHit searchHit, String member) {
        if (searchHit.getSourceAsMap().get("attachment") != null) {
            return ((Map<String, Object>) searchHit.getSourceAsMap().get("attachment")).get(member);
        }
        return null;
    }

    public ArticleContent getContentById(String id) {
        GetResponse response = client.prepareGet("crystal", "article", id)
                .setFetchSource(new String[] {"attachment.content"}, new String[] {})
                .get();

        ArticleContent result = new ArticleContent();
        result.setContent(((Map<String, String>) response.getSourceAsMap().get("attachment")).get("content"));
        return result;
    }

    public String getPathById(String id) {
        GetResponse response = client.prepareGet("crystal", "article", id)
                .setFetchSource(new String[] {"path"}, new String[] {})
                .get();
        return (String) response.getSourceAsMap().get("path");
    }
}

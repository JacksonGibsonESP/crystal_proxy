package ru.mai.dep810.webapp.repository;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mai.dep810.webapp.model.SearchResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class ElasticRepository {

    @Autowired
    Client client;

    @Value("${elastic.index.name}")
    String indexName;

    public SearchResult<Map<String, Object>> findPosts(
            String query,
            String chemicalElement,
            String chemicalFormula,
            String crystalSystem,
            String radiusType,
            String spaceGroup,
            int from,
            int size) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        if (StringUtils.isNotBlank(query)) {
            queryBuilder.must(QueryBuilders.queryStringQuery(query));
        }
        if (StringUtils.isNotBlank(chemicalElement)) {
            queryBuilder.must(QueryBuilders.queryStringQuery(chemicalElement));
        }
        if (StringUtils.isNotBlank(chemicalFormula)) {
            queryBuilder.must(QueryBuilders.queryStringQuery(chemicalFormula));
        }
        if (StringUtils.isNotBlank(crystalSystem)) {
            queryBuilder.must(QueryBuilders.queryStringQuery(crystalSystem));
        }
        if (StringUtils.isNotBlank(radiusType)) {
            queryBuilder.must(QueryBuilders.queryStringQuery(radiusType));
        }
        if (StringUtils.isNotBlank(spaceGroup)) {
            queryBuilder.must(QueryBuilders.queryStringQuery(spaceGroup));
        }

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("attachment.content").highlighterType("plain");

        ActionFuture<SearchResponse> execute = client.prepareSearch(indexName)
                .setQuery(queryBuilder)
                .setFetchSource(new String[] {"_id", "attachment.content"}, new String[] {})
                .setFrom(from)
                .setSize(size)
                .highlighter(highlightBuilder)
                .execute();

        return convertToSearchResult(execute);
    }

    private SearchResult<Map<String, Object>> convertToSearchResult(ActionFuture<SearchResponse> execute) {
        try {
            SearchResponse searchResponse = execute.get();

            List<Map<String, Object>> rows = Arrays.stream(searchResponse.getHits().getHits())
                    .map(e -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("_id", e.getId());
                        if (!e.getHighlightFields().isEmpty()) {
                            map.put("highlight",
                                    Arrays.stream(e.getHighlightFields().get("attachment.content").getFragments())
                                            .map(Text::string).collect(Collectors.toList())
                            );
                        }
                        map.put("attachment.content", ((Map<String, Object>) e.getSourceAsMap().get("attachment")).get("content"));
                        return map;
                    })
                    .collect(Collectors.toList());

            return new SearchResult<>(rows, searchResponse.getHits().getTotalHits());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}

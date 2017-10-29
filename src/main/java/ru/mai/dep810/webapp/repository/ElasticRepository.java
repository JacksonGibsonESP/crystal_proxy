package ru.mai.dep810.webapp.repository;

import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mai.dep810.webapp.model.Message;
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

    public SearchResult<Map<String, Object>> findPosts(String query) {

        ListenableActionFuture<SearchResponse> execute = client.prepareSearch(indexName)
                .setQuery(QueryBuilders.queryStringQuery(query))
                .setFetchSource(new String[] {"userId", "message", "createDate"}, new String[] {})
                .execute();

        return convertToPosts(execute);
    }

    public SearchResult<Map<String, Object>> findPostsByField(
            String field,
            String fieldValue,
            boolean fuzzy,
            int start,
            int limit
    ) {
        ListenableActionFuture<SearchResponse> execute = client.prepareSearch(indexName)
                .setQuery(fuzzy ? QueryBuilders.fuzzyQuery(field, fieldValue) : QueryBuilders.termQuery(field, fieldValue))
                .setFrom(start)
                .setSize(limit)
                .setFetchSource(new String[] {"userId", "message", "createDate"}, new String[] {})
                .execute();

        return convertToPosts(execute);
    }

    private SearchResult<Map<String, Object>> convertToPosts(ListenableActionFuture<SearchResponse> execute) {
        try {
            SearchResponse searchResponse = execute.get();
            List<Map<String, Object>> rows = Arrays.stream(searchResponse.getHits().getHits())
                    .map(SearchHit::getSourceAsMap)
                    .collect(Collectors.toList());
            return new SearchResult<>(rows, searchResponse.getHits().getTotalHits());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IndexResponse addMessage(Message message) {
        Map<String, Object> json = new HashMap<>();
        json.put("userId", message.getUserId());
        json.put("message", message.getMessage());
        json.put("createDate", message.getCreateDate());

        return client.prepareIndex("twitter", "tweet", message.getId()).setSource(json).get();
    }

}

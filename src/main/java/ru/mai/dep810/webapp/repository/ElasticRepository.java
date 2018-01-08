package ru.mai.dep810.webapp.repository;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mai.dep810.webapp.model.SearchResult;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class ElasticRepository {

    @Autowired
    Client client;

    @Value("${elastic.index.name}")
    String indexName;

    public SearchResult<String> findPosts(String query) {

        ActionFuture<SearchResponse> execute = client.prepareSearch(indexName)
                .setQuery(QueryBuilders.queryStringQuery(query))
                .setFetchSource(new String[] {"_id"}, new String[] {})
                .execute();

        return convertToSearchResult(execute);
    }

    private SearchResult<String> convertToSearchResult(ActionFuture<SearchResponse> execute) {
        try {
            SearchResponse searchResponse = execute.get();
            List<String> rows = Arrays.stream(searchResponse.getHits().getHits())
                    .map(SearchHit::getId)
                    .collect(Collectors.toList());
            return new SearchResult<>(rows, searchResponse.getHits().getTotalHits());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}

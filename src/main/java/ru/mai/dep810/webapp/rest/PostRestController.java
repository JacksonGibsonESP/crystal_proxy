package ru.mai.dep810.webapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.dep810.webapp.model.SearchResult;
import ru.mai.dep810.webapp.repository.ElasticRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class PostRestController {

    @Autowired
    private ElasticRepository elasticRepository;

    @RequestMapping(value = "/api/post/search", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public SearchResult<?> searchPosts(@RequestParam("q") String query) {
        return elasticRepository.findPosts(query);
    }

    // Пример вызова - нечеткий поиск по "java" в заголовке, вывести не более 100 результатов начиная с 30-го
    // http://localhost:8082/api/post/Title/java?fuzzy=true&start=30&limit=100
    @RequestMapping(value = "/api/post/{field}/{value}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public SearchResult<?> searchPostsByField(
            @PathVariable("field") String field,
            @PathVariable("value") String value,
            @RequestParam(value = "fuzzy", required = false) boolean fuzzy,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit
            ) {
        return elasticRepository.findPostsByField(field, value, fuzzy, start, limit);
    }
}

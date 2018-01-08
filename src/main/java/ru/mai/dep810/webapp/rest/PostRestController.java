package ru.mai.dep810.webapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/api/search", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public SearchResult<?> searchPosts(@RequestParam("q") String query) {
        return elasticRepository.findPosts(query);
    }

}

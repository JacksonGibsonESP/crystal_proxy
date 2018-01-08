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
    public SearchResult<?> searchPosts(
            @RequestParam(value = "Query", required = false) String query,
            @RequestParam(value = "ChemicalElement", required = false) String chemicalElement,
            @RequestParam(value = "ChemicalFormula", required = false) String chemicalFormula,
            @RequestParam(value = "CrystalSystem", required = false) String crystalSystem,
            @RequestParam(value = "RadiusType", required = false) String radiusType,
            @RequestParam(value = "SpaceGroup", required = false) String spaceGroup,
            @RequestParam(value = "from", required = false, defaultValue = "0") int from,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return elasticRepository.findPosts(
                query, chemicalElement, chemicalFormula, crystalSystem, radiusType, spaceGroup, from, size);
    }

}

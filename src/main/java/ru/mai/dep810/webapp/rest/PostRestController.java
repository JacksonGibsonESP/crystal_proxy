package ru.mai.dep810.webapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mai.dep810.webapp.model.SearchResult;
import ru.mai.dep810.webapp.repository.ElasticRepository;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class PostRestController {

    @Autowired
    private ElasticRepository elasticRepository;

    @CrossOrigin
    @RequestMapping(value = "/api/search", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public SearchResult<?> search(
            @RequestParam(value = "Query", required = false) String query,
            @RequestParam(value = "ChemicalElement", required = false) String chemicalElement,
            @RequestParam(value = "ChemicalFormula", required = false) String chemicalFormula,
            @RequestParam(value = "CrystalSystem", required = false) String crystalSystem,
            @RequestParam(value = "RadiusType", required = false) String radiusType,
            @RequestParam(value = "SpaceGroup", required = false) String spaceGroup,
            @RequestParam(value = "from", required = false, defaultValue = "0") int from,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return elasticRepository.search(
                query, chemicalElement, chemicalFormula, crystalSystem, radiusType, spaceGroup, from, size);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/getContentById", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public Map<String, String> getContentById(@RequestParam(value = "Id") String id) {
        return elasticRepository.getContentById(id);
    }
}

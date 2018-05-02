package ru.mai.dep810.webapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mai.dep810.webapp.model.*;
import ru.mai.dep810.webapp.repository.ElasticRepository;
import ru.mai.dep810.webapp.repository.JsonRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class PostRestController {

    @Autowired
    private ElasticRepository elasticRepository;

    @Autowired
    private JsonRepository jsonRepository;

    @CrossOrigin
    @RequestMapping(value = "/api/search", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public SearchResult<RowItem> search(
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
    public ArticleContent getContentById(@RequestParam(value = "Id") String id) {
        return elasticRepository.getContentById(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/getChemicalElements", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ChemicalElements getChemicalElements() {
        return jsonRepository.getChemicalElements();
    }

    @CrossOrigin
    @RequestMapping(value = "/api/getChemicalFormulas", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ChemicalFormulas getChemicalFormulas() {
        return jsonRepository.getChemicalFormulas();
    }

    @CrossOrigin
    @RequestMapping(value = "/api/getCrystalSystems", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public CrystalSystems getCrystalSystems() {
        return jsonRepository.getCrystalSystems();
    }

    @CrossOrigin
    @RequestMapping(value = "/api/getRadiusTypes", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public RadiusTypes getRadiusTypes() {
        return jsonRepository.getRadiusTypes();
    }
}

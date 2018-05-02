package ru.mai.dep810.webapp.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mai.dep810.webapp.config.ElasticSearchConfig;
import ru.mai.dep810.webapp.model.ChemicalElements;
import ru.mai.dep810.webapp.model.ChemicalFormulas;
import ru.mai.dep810.webapp.model.CrystalSystems;
import ru.mai.dep810.webapp.model.RadiusTypes;

import java.io.IOException;
import java.io.InputStream;

@Component
public class JsonRepository {
    private static final Logger log = Logger.getLogger(ElasticSearchConfig.class);

    @Value("${chemical.elements.json.path}")
    private String chemicalElementsPath;

    @Value("${chemical.formulas.json.path}")
    private String chemicalFormulasPath;

    @Value("${crystal.systems.json.path}")
    private String crystalSystemsPath;

    @Value("${radius.types.json.path}")
    private String radiusTypesPath;

    public ChemicalElements getChemicalElements() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<ChemicalElements> typeReference = new TypeReference<ChemicalElements>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream(chemicalElementsPath);
        try {
            return mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            log.error("Не удалось распарсить chemicalElements.json: ", e);
            return new ChemicalElements();
        }
    }

    public ChemicalFormulas getChemicalFormulas() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<ChemicalFormulas> typeReference = new TypeReference<ChemicalFormulas>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream(chemicalFormulasPath);
        try {
            return mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            log.error("Не удалось распарсить chemicalFormulas.json: ", e);
            return new ChemicalFormulas();
        }
    }

    public CrystalSystems getCrystalSystems() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<CrystalSystems> typeReference = new TypeReference<CrystalSystems>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream(crystalSystemsPath);
        try {
            return mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            log.error("Не удалось распарсить crystalSystems.json: ", e);
            return new CrystalSystems();
        }
    }

    public RadiusTypes getRadiusTypes() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<RadiusTypes> typeReference = new TypeReference<RadiusTypes>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream(radiusTypesPath);
        try {
            return mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            log.error("Не удалось распарсить radiusTypes.json: ", e);
            return new RadiusTypes();
        }
    }
}

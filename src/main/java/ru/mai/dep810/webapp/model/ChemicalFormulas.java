package ru.mai.dep810.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ChemicalFormulas {
    private List<String> chemicalFormulas = new ArrayList<>();

    public List<String> getChemicalFormulas() {
        return chemicalFormulas;
    }

    public void setChemicalFormulas(List<String> chemicalFormulas) {
        this.chemicalFormulas = chemicalFormulas;
    }
}

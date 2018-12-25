package com.epam.cruiseCompany.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Port {
    private int id;
    private String name;
    private List<Excursion> excursions;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Excursion> getExcursions() {
        return excursions;
    }
    public void setExcursions(List<Excursion> excursions) {
        this.excursions = excursions;
    }

    public Port() {
        this(0, "noName", new ArrayList<>());
    }

    public Port(int id, String name, List<Excursion> excursions) {
        this.id = id;
        this.name = name;
        this.excursions = excursions;
    }
}

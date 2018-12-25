package com.epam.cruiseCompany.model.entity.people;


public class Person {
    protected int id;
    protected String name;
    protected String surName;

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
    public String getSurName() {
        return surName;
    }
    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Person() {this(0, "noName", "noSurName");
    }

    public Person(int id, String name, String surName) {
        this.id = id;
        this.name = name;
        this.surName = surName;
    }
}

package com.epam.cruiseCompany.model.entity.people;



public class Crew extends Person{
    private String position;

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public Crew() {
        this("noPosition");
    }

    public Crew(String position) {
        this.position = position;
    }

    public Crew(int id, String name, String surName, String position) {
        super(id, name, surName);
        this.position = position;
    }
}

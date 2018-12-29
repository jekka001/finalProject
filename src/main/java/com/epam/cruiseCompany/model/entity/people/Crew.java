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

    public Crew(int id, String name, String surname, String position) {
        super(id, name, surname);
        this.position = position;
    }
}

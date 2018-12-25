package com.epam.cruiseCompany.model.entity;


import com.epam.cruiseCompany.model.entity.people.Crew;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private int id;
    private String name;
    private int passengerCapacity;
    private List<Crew> crew;

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
    public int getPassengerCapacity() {
        return passengerCapacity;
    }
    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
    public List<Crew> getCrew() {
        return crew;
    }
    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public Ship() {
        this(0, "noName", 0, new ArrayList<>());
    }

    public Ship(int id, String name, int passengerCapacity, List<Crew> crew) {
        this.id = id;
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.crew = crew;
    }
}

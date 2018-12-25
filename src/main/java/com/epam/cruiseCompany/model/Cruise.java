package com.epam.cruiseCompany.model;

import com.epam.cruiseCompany.model.entity.Port;
import com.epam.cruiseCompany.model.entity.Ship;
import com.epam.cruiseCompany.model.entity.ticket.Ticket;

import java.time.Instant;
import java.util.*;

public class Cruise {
    private int id;
    private String name;
    private List<Port> ports;
    private String cityDeparture;
    private Instant startCruise;
    private Instant durationCruise;
    private Ship ship;
    private List<Ticket> passengers;

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
    public List<Port> getPorts() {
        return ports;
    }
    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }
    public String getCityDeparture() {
        return cityDeparture;
    }
    public void setCityDeparture(String cityDeparture) {
        this.cityDeparture = cityDeparture;
    }
    public Instant getStartCruise() {
        return startCruise;
    }
    public void setStartCruise(Instant startCruise) {
        this.startCruise = startCruise;
    }
    public Instant getDurationCruise() {
        return durationCruise;
    }
    public void setDurationCruise(Instant durationCruise) {
        this.durationCruise = durationCruise;
    }
    public Ship getShip() {
        return ship;
    }
    public void setShip(Ship ship) {
        this.ship = ship;
    }
    public List<Ticket> getPassengers() {
        return passengers;
    }
    public void setPassengers(List<Ticket> passengers) {
        this.passengers = passengers;
    }

    public Cruise() {
        this(0,"noName",new ArrayList<>(),"noCity",Instant.now(), Instant.now(),
                new Ship(), new ArrayList<>());
    }

    public Cruise(int id, String name, List<Port> ports, String cityDeparture, Instant startCruise,
                  Instant durationCruise, Ship ship, List<Ticket> passengers) {
        this.id = id;
        this.name = name;
        this.ports = ports;
        this.cityDeparture = cityDeparture;
        this.startCruise = startCruise;
        this.durationCruise = durationCruise;
        this.ship = ship;
        this.passengers = passengers;
    }
}

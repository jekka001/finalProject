package com.epam.cruiseCompany.model;

import com.epam.cruiseCompany.model.entity.Port;
import com.epam.cruiseCompany.model.entity.Ship;
import com.epam.cruiseCompany.model.entity.ticket.CruiseTicket;

import java.time.Instant;
import java.util.*;

public class Cruise {
    private int id;
    private String name;
    private List<Port> ports;
    private String cityDeparture;
    private Instant startCruise;
    private Instant durationCruise;
    private List<Ship> ships;
    private List<CruiseTicket> tickets;

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
    public List<Ship> getShips() {
        return ships;
    }
    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }
    public List<CruiseTicket> getTickets() {
        return tickets;
    }
    public void setTickets(List<CruiseTicket> tickets) {
        this.tickets = tickets;
    }

    public Cruise() {
        this(0, "noName", new ArrayList<>(), "noCityDeparture", Instant.now(),
                Instant.now(), new ArrayList<>(), new ArrayList<>());
    }

    public Cruise(int id, String name, List<Port> ports, String cityDeparture, Instant startCruise,
                  Instant durationCruise, List<Ship> ships, List<CruiseTicket> tickets) {
        this.id = id;
        this.name = name;
        this.ports = ports;
        this.cityDeparture = cityDeparture;
        this.startCruise = startCruise;
        this.durationCruise = durationCruise;
        this.ships = ships;
        this.tickets = tickets;
    }
}

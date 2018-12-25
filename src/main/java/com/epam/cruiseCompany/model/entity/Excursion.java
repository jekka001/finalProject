package com.epam.cruiseCompany.model.entity;


import com.epam.cruiseCompany.model.entity.ticket.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Excursion {
    private int id;
    private String name;
    private List<Ticket> tickets;

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
    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Excursion() {
        this(0, "noName", new ArrayList<>());
    }

    public Excursion(int id, String name, List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.tickets = tickets;
    }
}

package com.epam.cruiseCompany.model.entity.ticket;

import com.epam.cruiseCompany.model.entity.people.Client;

public class Ticket {
    protected int id;
    private double price;
    private Status status;
    private Client client;
    private TicketClass ticketClass;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public TicketClass getTicketClass() {
        return ticketClass;
    }
    public void setTicketClass(TicketClass ticketClass) {
        this.ticketClass = ticketClass;
    }

    public Ticket() {
        this(0, 0, Status.NO_STATUS, new Client(), TicketClass.NO_CLASS);
    }

    public Ticket(int id, double price, Status status, Client client, TicketClass ticketClass) {
        this.id = id;
        this.price = price;
        this.status = status;
        this.client = client;
        this.ticketClass = ticketClass;
    }
}

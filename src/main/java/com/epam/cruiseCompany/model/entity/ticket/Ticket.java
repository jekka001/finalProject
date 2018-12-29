package com.epam.cruiseCompany.model.entity.ticket;


import com.epam.cruiseCompany.model.entity.people.User;

public class Ticket {
    protected int id;
    private double price;
    private Status status;
    private User user;

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
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Ticket() {
        this(0, 0, Status.NO_STATUS, new User());
    }

    public Ticket(int id, double price, Status status, User user) {
        this.id = id;
        this.price = price;
        this.status = status;
        this.user = user;
    }
}

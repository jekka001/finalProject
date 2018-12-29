package com.epam.cruiseCompany.model.entity.ticket;


import com.epam.cruiseCompany.model.entity.people.User;

public class CruiseTicket extends Ticket {
    private TicketClass ticketClass;

    public TicketClass getTicketClass() {
        return ticketClass;
    }
    public void setTicketClass(TicketClass ticketClass) {
        this.ticketClass = ticketClass;
    }

    public CruiseTicket(){
        this(TicketClass.NO_CLASS);
    }

    public CruiseTicket(TicketClass ticketClass) {
        this.ticketClass = ticketClass;
    }

    public CruiseTicket(int id, double price, Status status, TicketClass ticketClass, User user) {
        super(id, price, status, user);
        this.ticketClass = ticketClass;
    }
}

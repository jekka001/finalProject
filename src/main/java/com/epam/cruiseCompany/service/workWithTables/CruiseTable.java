package com.epam.cruiseCompany.service.workWithTables;


import com.epam.cruiseCompany.model.entity.ticket.TicketClass;

import java.time.Instant;

public class CruiseTable {
    protected String nameCruise;
    protected String cityDeparture;
    protected Instant startCruise;
    protected Instant durationCruise;
    protected String nameShip;
    protected int ticketId;
    protected TicketClass ticketClass;

    public String getNameCruise() {
        return nameCruise;
    }
    public void setNameCruise(String nameCruise) {
        this.nameCruise = nameCruise;
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
    public String getNameShip() {
        return nameShip;
    }
    public void setNameShip(String nameShip) {
        this.nameShip = nameShip;
    }
    public int getTicketId() {
        return ticketId;
    }
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
    public TicketClass getTicketClass() {
        return ticketClass;
    }
    public void setTicketClass(TicketClass ticketClass) {
        this.ticketClass = ticketClass;
    }

    public CruiseTable() {
    }

    public CruiseTable(String nameCruise, String cityDeparture, Instant startCruise, Instant durationCruise, String nameShip, int ticketId, TicketClass ticketClass) {
        this.nameCruise = nameCruise;
        this.cityDeparture = cityDeparture;
        this.startCruise = startCruise;
        this.durationCruise = durationCruise;
        this.nameShip = nameShip;
        this.ticketId = ticketId;
        this.ticketClass = ticketClass;
    }

    @Override
    public String toString() {
        return "CruiseTable{" +
                "nameCruise='" + nameCruise + '\'' +
                ", cityDeparture='" + cityDeparture + '\'' +
                ", startCruise=" + startCruise +
                ", durationCruise=" + durationCruise +
                ", nameShip='" + nameShip + '\'' +
                ", ticketId=" + ticketId +
                ", ticketClass=" + ticketClass +
                '}';
    }
}

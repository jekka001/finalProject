package com.epam.cruiseCompany.service.workWithTables;

import com.epam.cruiseCompany.dao.connection.ConnectionPoolHolder;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.dao.impl.AbstractDao;
import com.epam.cruiseCompany.dao.impl.CruiseDao;
import com.epam.cruiseCompany.model.Cruise;
import com.epam.cruiseCompany.model.entity.people.User;
import com.epam.cruiseCompany.model.entity.ticket.CruiseTicket;
import com.epam.cruiseCompany.model.entity.ticket.Status;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectionTable extends CruiseTable {
    private int counterFirstTicket = 0;
    private int countTicketInTable = 10;
    private double ticketPrice;

    private int userId;

    private Connection connection;
    private AbstractDao<Cruise> cruiseDao;
    private AbstractDao<CruiseTicket> cruiseTicketDao;

    public int getCounterFirstTicket() {
        return counterFirstTicket;
    }
    public void setCounterFirstTicket(int counterFirstTicket) {
        this.counterFirstTicket = counterFirstTicket;
    }
    public int getCountTicketInTable() {
        return countTicketInTable;
    }
    public void setCountTicketInTable(int countTicketInTable) {
        this.countTicketInTable = countTicketInTable;
    }
    public double getTicketPrice() {
        return ticketPrice;
    }
    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public SelectionTable() {
        connection = new ConnectionPoolHolder().getConnection();
        cruiseDao = MySqlDaoFactory.getInstance().createCruiseDao(connection);
        cruiseTicketDao = MySqlDaoFactory.getInstance().createCruiseTicketDao(connection);
    }

    public List<SelectionTable> getCruise(){
        return getCruiseFromBD();
    }
    private List<SelectionTable> getCruiseFromBD(){
        return ((CruiseDao) cruiseDao).cruiseForTable(counterFirstTicket, countTicketInTable);
    }
    private boolean isEmptyCruise(){
        return getCruiseFromBD().isEmpty();
    }

    public void buyTicket(int ticketId, int userId){
        this.ticketId = ticketId;
        this.userId = userId;
        CruiseTicket boughtTicket = buyTicket();
        cruiseTicketDao.update(boughtTicket);
    }
    private CruiseTicket buyTicket(){
        CruiseTicket cruiseTicket = findCruiseTicketInBD();
        cruiseTicket.setStatus(Status.SOLD);
        User user = addUser();
        cruiseTicket.setUser(user);
        return cruiseTicket;
    }
    private User addUser(){
        User user = new User();
        user.setId(userId);
        return user;
    }
    private CruiseTicket findCruiseTicketInBD(){
        return cruiseTicketDao.findById(ticketId);
    }

    public void increaseCounterFirstTicket(){
        counterFirstTicket += countTicketInTable;
        if(isEmptyCruise()){
            decreaseCounterFirstTicket();
        }
    }
    public void decreaseCounterFirstTicket() {
        if(checkCounterFirstTicket()){
            counterFirstTicket -= countTicketInTable;
        }
    }
    private boolean checkCounterFirstTicket(){
        return counterFirstTicket > countTicketInTable;
    }

    public List<SelectionTable> getFoundCruise(String nameCruise){
        return findCruise(nameCruise);
    }
    private List<SelectionTable> findCruise(String nameCruise){
        List<SelectionTable> cruises = getCruiseFromBD();
        List<SelectionTable> tempCruise = new ArrayList<>();
        for(SelectionTable selectionTable : cruises){
            if(selectionTable.getNameCruise().equals(nameCruise)){
                tempCruise.add(selectionTable);
            }
        }
        return tempCruise;
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

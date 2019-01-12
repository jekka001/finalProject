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
import java.util.List;

public class UserCruiseTable extends CruiseTable {
    private int counterFirstTicket = 0;
    private int countTicketInTable = 10;
    private int numberOfTickets;

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
    public int getNumberOfTickets() {
        return numberOfTickets;
    }
    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserCruiseTable() {
        connection = new ConnectionPoolHolder().getConnection();
        cruiseDao = MySqlDaoFactory.getInstance().createCruiseDao(connection);
        cruiseTicketDao = MySqlDaoFactory.getInstance().createCruiseTicketDao(connection);
    }

    public List<UserCruiseTable> getUserCruise(int userId){
        this.userId = userId;
        return getUserCruiseFromBD();
    }
    private List<UserCruiseTable> getUserCruiseFromBD(){
        return ((CruiseDao) cruiseDao).userCruise(userId, counterFirstTicket, countTicketInTable);
    }
    private boolean isEmptyUserCruise(){
        return getUserCruiseFromBD().isEmpty();
    }

    public void increaseCounterFirstTicket(){
        counterFirstTicket += countTicketInTable;
        if(isEmptyUserCruise()){
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

    public void returnPurchasedTicket(int ticketId){
        this.ticketId = ticketId;
        CruiseTicket freeTicket = freeUpTicket();
        cruiseTicketDao.update(freeTicket);
    }
    private CruiseTicket freeUpTicket(){
        CruiseTicket cruiseTicket = findCruiseTicketInBD();
        cruiseTicket.setStatus(Status.AVAILABLE);
        User user = removeUser();
        cruiseTicket.setUser(user);
        return cruiseTicket;
    }
    private User removeUser(){
        User user = new User();
        user.setId(0);
        return user;
    }
    private CruiseTicket findCruiseTicketInBD(){
        return cruiseTicketDao.findById(ticketId);
    }


    public UserCruiseTable getFoundUserCruise(int userId, int ticketId){
        this.ticketId = ticketId;
        this.userId = userId;
        return findCruise();
    }
    private UserCruiseTable findCruise(){
        List<UserCruiseTable> userCruises = getUserCruiseFromBD();

        for(UserCruiseTable userCruiseTable : userCruises){
            if(equalsUserTicketWithFound(userCruiseTable.getTicketId())){
                return userCruiseTable;
            }
        }

        return null;
    }
    private boolean equalsUserTicketWithFound(int userTicketId){
        CruiseTicket foundTicket = findCruiseTicketInBD();
        return foundTicket != null && userTicketId == foundTicket.getId();
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "UserCruiseTable{" +
                "nameCruise='" + nameCruise + '\'' +
                ", cityDeparture='" + cityDeparture + '\'' +
                ", startCruise=" + startCruise +
                ", durationCruise=" + durationCruise +
                ", nameShip='" + nameShip + '\'' +
                ", ticketId=" + ticketId +
                ", numberOfTickets=" + numberOfTickets +
                ", ticketClass=" + ticketClass +
                '}';
    }
}

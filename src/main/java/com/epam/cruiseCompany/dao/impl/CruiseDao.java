package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.model.Cruise;
import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.model.entity.Port;
import com.epam.cruiseCompany.model.entity.Ship;
import com.epam.cruiseCompany.model.entity.ticket.CruiseTicket;
import com.epam.cruiseCompany.model.entity.ticket.Status;
import com.epam.cruiseCompany.model.entity.ticket.TicketClass;
import com.epam.cruiseCompany.service.workWithTables.CruiseTable;
import com.epam.cruiseCompany.service.workWithTables.SelectionTable;
import com.epam.cruiseCompany.service.workWithTables.UserCruiseTable;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CruiseDao extends AbstractDao<Cruise> {
    private static final String SQL_INSERT = "INSERT INTO cruise(id, name, city_departure, start_cruise, duration_cruise) " +
            "VALUES(?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_CRUISE_PORT = "INSERT INTO cruise_port(cruise_id, port_id) VALUES(?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM cruise";
    private static final String SQL_FIND_USER_CRUISE = "SELECT cruise.name, cruise.city_departure, cruise.start_cruise, " +
            "cruise.duration_cruise, ship.name, cruise_ticket.id, count(cruise_ticket.ticket_class), cruise_ticket.ticket_class FROM cruise " +
            "INNER JOIN ship ON cruise.id = ship.cruise_id " +
            "INNER JOIN cruise_ticket ON cruise.id = cruise_ticket.cruise_id " +
            "WHERE cruise_ticket.user_id = ? " +
            "GROUP BY cruise_ticket.ticket_class, cruise.name, cruise.start_cruise, ship.name " +
            "LIMIT ?, ?";
    private static final String SQL_FIND_FREE_CRUISE = "SELECT cruise.name, cruise.city_departure, cruise.start_cruise, " +
            "cruise.duration_cruise, ship.name, cruise_ticket.id, cruise_ticket.price, cruise_ticket.ticket_class FROM cruise " +
            "INNER JOIN ship ON cruise.id = ship.cruise_id " +
            "INNER JOIN cruise_ticket ON cruise.id = cruise_ticket.cruise_id " +
            "WHERE cruise_ticket.status = ? " +
            "GROUP BY cruise_ticket.ticket_class, cruise.name, cruise.start_cruise, ship.name " +
            "LIMIT ?, ?";
    private static final String SQL_UPDATE = "UPDATE cruise SET name = ?, city_departure = ?, start_cruise = ?," +
            " duration_cruise = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM cruise WHERE id = ?";
    private static final String SQL_DELETE_PORT = "DELETE FROM cruise_port WHERE cruise_id = ?";

    private DaoFactory parentFactory = MySqlDaoFactory.getInstance();
    private AbstractDao<CruiseTicket> ticketDao = parentFactory.createCruiseTicketDao(connection);
    private AbstractDao<Ship> shipDao = parentFactory.createShipDao(connection);
    private AbstractDao<Port> portDao = parentFactory.createPortDao(connection);

    public CruiseDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Cruise> findAll() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);

            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cruise findById(int id) {
        if(isExistCruise(id)) {
            return findByInt("id", id).get(0);
        }
        return null;
    }
    private boolean isExistCruise(int id){
        return !findByInt("id", id).isEmpty();
    }
    @Override
    public List<Cruise> findByString(String type, String value) {
        String currentSql = getSelectQuery(type);

        try (PreparedStatement preparedStatement = connection.prepareStatement(currentSql)) {
            preparedStatement.setString(1, value);

            ResultSet resultSet = preparedStatement.executeQuery();

            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cruise> findByInt(String type, int value) {
        String currentSql = getSelectQuery(type);

        try (PreparedStatement preparedStatement = connection.prepareStatement(currentSql)) {
            preparedStatement.setInt(1, value);

            ResultSet resultSet = preparedStatement.executeQuery();

            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(Cruise object) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setInt(1, object.getId());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setString(3, object.getCityDeparture());
            preparedStatement.setLong(4, object.getStartCruise().getEpochSecond());
            preparedStatement.setLong(5, object.getDurationCruise().getEpochSecond());
            preparedStatement.execute();

            createPort(object.getPorts(), object.getId());
            createShip(object.getShips(), object.getId());
            createTicket(object.getTickets(), object.getId());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Cruise update(Cruise object) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, object.getName());
            preparedStatement.setString(2, object.getCityDeparture());
            preparedStatement.setLong(3, object.getStartCruise().getEpochSecond());
            preparedStatement.setLong(4, object.getDurationCruise().getEpochSecond());
            preparedStatement.setInt(5, object.getId());

            preparedStatement.execute();

            updatePort(object.getPorts(), object.getId());
            updateShip(object.getShips(), object.getId());
            updateTicket(object.getTickets(), object.getId());

            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Cruise object) {
        return delete(object.getId());
    }

    @Override
    public boolean delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            deletePort(id);
            deleteShip(id);
            deleteTicket(id);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Cruise> parseSet(ResultSet resultSet) throws SQLException {
        List<Cruise> cruiseList = new ArrayList<>();

        while (resultSet.next()) {
            cruiseList.add(fillCruise(resultSet));
        }

        return cruiseList;
    }
    private Cruise fillCruise(ResultSet resultSet) throws SQLException{
        Cruise tempCruise = new Cruise();

        tempCruise.setId(resultSet.getInt("id"));
        tempCruise.setName(resultSet.getString("name"));
        tempCruise.setPorts(portDao.findByInt("cruise_id", resultSet.getInt("id")));
        tempCruise.setCityDeparture(resultSet.getString("city_departure"));
        tempCruise.setStartCruise(Instant.ofEpochSecond(resultSet.getLong("start_cruise")));
        tempCruise.setDurationCruise(Instant.ofEpochSecond(resultSet.getLong("duration_cruise")));
        tempCruise.setShips(shipDao.findByInt("cruise_id", resultSet.getInt("id")));
        tempCruise.setTickets(ticketDao.findByInt("cruise_id", resultSet.getInt("id")));

        return tempCruise;
    }
    private String getSelectQuery(String type) {
        return SQL_FIND_ALL + " WHERE " + type + " = ?";
    }

    private void createPort(List<Port> portList, int cruiseId) {
        for (Port port : portList) {
            createCruisePortId(cruiseId, port.getId());
        }
    }
    private void createCruisePortId(int cruiseId, int portId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_CRUISE_PORT)) {
            preparedStatement.setInt(1, cruiseId);
            preparedStatement.setInt(2, portId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void updatePort(List<Port> portList, int cruiseId){
        deletePort(cruiseId);
        createPort(portList, cruiseId);
    }
    private void deletePort(int cruiseId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PORT)) {
            preparedStatement.setInt(1, cruiseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTicket(List<CruiseTicket> cruiseTicketList, int cruiseId) {
        for (CruiseTicket cruiseTicket : cruiseTicketList) {
            ticketDao.create(cruiseTicket);
            ((CruiseTicketDao)ticketDao).updateCruiseId(cruiseTicket, cruiseId);
        }
    }
    private void updateTicket(List<CruiseTicket> cruiseTicketList, int cruiseId) {
        deleteTicket(cruiseId);
        createTicket(cruiseTicketList, cruiseId);
    }
    private void deleteTicket(int cruiseId) {
        List<CruiseTicket> cruiseTicketList = ticketDao.findByInt("cruise_id", cruiseId);

        for (CruiseTicket cruiseTicket : cruiseTicketList) {
            ticketDao.delete(cruiseTicket);
        }
    }

    private void createShip(List<Ship> shipList, int cruiseId) {
        for (Ship ship : shipList) {
            shipDao.create(ship);
            ((ShipDao)shipDao).updateCruiseId(ship, cruiseId);
        }
    }
    private void updateShip(List<Ship> shipList, int cruiseId) {
        deleteShip(cruiseId);
        createShip(shipList, cruiseId);
    }
    private void deleteShip(int cruiseId) {
        List<Ship> shipList = shipDao.findByInt("cruise_id", cruiseId);

        for (Ship ship : shipList) {
            shipDao.delete(ship);
        }
    }

    public List<UserCruiseTable> userCruise(int userId, int start, int quantity){
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_CRUISE)){
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, quantity);

            ResultSet resultSet = preparedStatement.executeQuery();
            return parseSetUserCruise(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    private List<UserCruiseTable> parseSetUserCruise(ResultSet resultSet) throws SQLException {
        List<UserCruiseTable> cruiseList = new ArrayList<>();

        while (resultSet.next()) {
            cruiseList.add(fillUserCruise(resultSet));
        }

        return cruiseList;
    }
    private UserCruiseTable fillUserCruise(ResultSet resultSet) throws SQLException{
        UserCruiseTable tempUserCruise = new UserCruiseTable();

        tempUserCruise.setNameCruise(resultSet.getString(1));
        tempUserCruise.setCityDeparture(resultSet.getString(2));
        tempUserCruise.setStartCruise(Instant.ofEpochSecond(resultSet.getInt(3)));
        tempUserCruise.setDurationCruise(Instant.ofEpochSecond(resultSet.getInt(4)));
        tempUserCruise.setNameShip(resultSet.getString(5));
        tempUserCruise.setTicketId(resultSet.getInt(6));
        tempUserCruise.setNumberOfTickets(resultSet.getInt(7));
        tempUserCruise.setTicketClass(TicketClass.valueOf(resultSet.getString(8)));
        tempUserCruise.closeConnection();
        return tempUserCruise;
    }

    public List<SelectionTable> cruiseForTable(int start, int quantity){
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_FREE_CRUISE)){
            preparedStatement.setString(1, Status.AVAILABLE.toString());
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, quantity);

            ResultSet resultSet = preparedStatement.executeQuery();
            return parseSetCruiseForTable(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    private List<SelectionTable> parseSetCruiseForTable(ResultSet resultSet) throws SQLException {
        List<SelectionTable> cruiseList = new ArrayList<>();

        while (resultSet.next()) {
            cruiseList.add(fillCruiseTable(resultSet));
        }

        return cruiseList;
    }
    private SelectionTable fillCruiseTable(ResultSet resultSet) throws SQLException{
        SelectionTable tempCruise = new SelectionTable();
        tempCruise.setNameCruise(resultSet.getString(1));
        tempCruise.setCityDeparture(resultSet.getString(2));
        tempCruise.setStartCruise(Instant.ofEpochSecond(resultSet.getInt(3)));
        tempCruise.setDurationCruise(Instant.ofEpochSecond(resultSet.getInt(4)));
        tempCruise.setNameShip(resultSet.getString(5));
        tempCruise.setTicketId(resultSet.getInt(6));
        tempCruise.setTicketPrice(resultSet.getDouble(7));
        tempCruise.setTicketClass(TicketClass.valueOf(resultSet.getString(8)));
        tempCruise.closeConnection();
        return tempCruise;
    }

}

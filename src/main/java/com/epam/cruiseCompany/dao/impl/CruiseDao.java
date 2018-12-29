package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.model.Cruise;
import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.model.entity.Port;
import com.epam.cruiseCompany.model.entity.Ship;
import com.epam.cruiseCompany.model.entity.ticket.CruiseTicket;
import com.epam.cruiseCompany.model.entity.ticket.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//-
public class CruiseDao extends AbstractDao<Cruise> {
    private static final String SQL_INSERT = "INSERT INTO cruise(name, cityDeparture, startCruise, durationCruise) " +
                                            "VALUES(?, ?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM cruise";
    private static final String SQL_UPDATE = "UPDATE cruise SET name = ?, cityDeparture = ?, startCruise = ?," +
                                            " durationCruise = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM cruise WHERE id = ?";
    private DaoFactory parentFactory = MySqlDaoFactory.getInstance();

    public CruiseDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Cruise> findAll() {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);
            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cruise findById(int id) {
        return findByInt("id", id).get(0);
    }

    @Override
    public List<Cruise> findByString(String type, String value) {
        String currentSql = getSelectQuery(type);
        try(PreparedStatement preparedStatement = connection.prepareStatement(currentSql)) {
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
        try(PreparedStatement preparedStatement = connection.prepareStatement(currentSql)) {
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
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getName());
            preparedStatement.setString(2, object.getCityDeparture());
            preparedStatement.setLong(3, object.getStartCruise().getEpochSecond());
            preparedStatement.setLong(4, object.getDurationCruise().getEpochSecond());


            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                object.setId(generatedKeys.getInt("id"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Cruise update(Cruise object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, object.getName());
            preparedStatement.setString(2, object.getCityDeparture());
            preparedStatement.setLong(3, object.getStartCruise().getEpochSecond());
            preparedStatement.setLong(4, object.getDurationCruise().getEpochSecond());
          //  preparedStatement.setInt(5, object.getShip().getId());
            preparedStatement.setInt(6, object.getId());

            preparedStatement.execute();

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
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)){
            preparedStatement.setInt(1, id);

            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Cruise> parseSet(ResultSet resultSet) throws SQLException {
        List<Cruise> cruiseList = new ArrayList<>();
        AbstractDao<CruiseTicket> ticketDao = parentFactory.createCruiseTicketDao(connection);
        AbstractDao cruisePortDao = parentFactory.createCruisePortDao(connection);
        AbstractDao<Ship> shipDao = parentFactory.createShipDao(connection);
        while(resultSet.next()){
            Cruise tempCruise = new Cruise();
            tempCruise.setId(resultSet.getInt("id"));
            tempCruise.setName(resultSet.getString("name"));
            tempCruise.setPorts(cruisePortDao.findByInt("cruise_id", resultSet.getInt("id")));
            tempCruise.setCityDeparture(resultSet.getString("city_departure"));
            tempCruise.setStartCruise(resultSet.getDate("start_cruise").toInstant());
            tempCruise.setDurationCruise(resultSet.getDate("duration_cruise").toInstant());
            tempCruise.setShip(shipDao.findByInt("cruise_id", resultSet.getInt("id")));
            tempCruise.setTickets(ticketDao.findByInt("cruise_id", resultSet.getInt("id")));
            cruiseList.add(tempCruise);
        }
        return cruiseList;
    }
    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}

    private void createPort(List<Port> portList, int cruiseId){
        CruisePortDao cruisePortDao = parentFactory.createCruisePortDao(connection);
        for(Port port : portList) {
           // cruisePortDao.create(port);
            cruisePortDao.updateCruiseId(port, cruiseId);
        }
    }
    private void updatePort(List<Port> portList, int cruiseId){
        PortDao portDao = parentFactory.createPortDao(connection);
        List<Port> newPort = new ArrayList<>();
        deletePort(cruiseId);
        for(Port port : portList) {
            if (portDao.findById(port.getId()) != null){
                portDao.delete(port);
                newPort.add(port);
            }else {
                newPort.add(port);
            }
        }
        createPort(newPort, cruiseId);
    }
    private void deletePort(int cruiseId){
        CruisePortDao portDao = parentFactory.createCruisePortDao(connection);
       /* List<Port> portList = portDao.findByInt("cruise_id", cruiseId);
        for(Port port : portList){
            portDao.delete(port);
        }*/
    }

    private void createTicket(List<CruiseTicket> cruiseTicketList, int cruiseId){
        CruiseTicketDao cruiseTicketDao = parentFactory.createCruiseTicketDao(connection);
        for(CruiseTicket cruiseTicket : cruiseTicketList) {
            cruiseTicketDao.create(cruiseTicket);
            cruiseTicketDao.updateCruiseId(cruiseTicket, cruiseId);
        }
    }
    private void updateTicket(List<CruiseTicket> cruiseTicketList, int cruiseId){
        CruiseTicketDao cruiseTicketDao = parentFactory.createCruiseTicketDao(connection);
        List<CruiseTicket> newCruiseTicket = new ArrayList<>();
        deleteTicket(cruiseId);
        for(CruiseTicket cruiseTicket : cruiseTicketList) {
            if (cruiseTicketDao.findById(cruiseTicket.getId()) != null){
                cruiseTicketDao.delete(cruiseTicket);
                newCruiseTicket.add(cruiseTicket);
            }else {
                newCruiseTicket.add(cruiseTicket);
            }
        }
        createTicket(newCruiseTicket, cruiseId);
    }
    private void deleteTicket(int cruiseId){
        CruiseTicketDao cruiseTicketDao = parentFactory.createCruiseTicketDao(connection);
        List<CruiseTicket> cruiseTicketList = cruiseTicketDao.findByInt("cruise_id", cruiseId);
        for(CruiseTicket cruiseTicket : cruiseTicketList){
            cruiseTicketDao.delete(cruiseTicket);
        }
    }

    private void createShip(List<Ship> shipList, int cruiseId){
        ShipDao shipDao = parentFactory.createShipDao(connection);
        for(Ship ship : shipList) {
            shipDao.create(ship);
            shipDao.updateCruiseId(ship, cruiseId);
        }
    }
    private void updateShip(List<Ship> shipList, int cruiseId){
        ShipDao shipDao = parentFactory.createShipDao(connection);
        List<Ship> newShip = new ArrayList<>();
        deleteShip(cruiseId);
        for(Ship ship : shipList) {
            if (shipDao.findById(ship.getId()) != null){
                shipDao.delete(ship);
                newShip.add(ship);
            }else {
                newShip.add(ship);
            }
        }
        createShip(newShip, cruiseId);
    }
    private void deleteShip(int cruiseId){
        ShipDao shipDao = parentFactory.createShipDao(connection);
        List<Ship> shipList = shipDao.findByInt("cruise_id", cruiseId);
        for(Ship ship : shipList){
            shipDao.delete(ship);
        }
    }
}

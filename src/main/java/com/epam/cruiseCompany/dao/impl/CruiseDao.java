package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.model.Cruise;
import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.model.entity.Port;
import com.epam.cruiseCompany.model.entity.Ship;
import com.epam.cruiseCompany.model.entity.ticket.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//comply
public class CruiseDao extends AbstractDao<Cruise> {
    private static final String SQL_INSERT = "INSERT INTO cruise(name, cityDeparture, startCruise, durationCruise," +
                                            " idShip) VALUES(?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM cruise";
    private static final String SQL_UPDATE = "UPDATE cruise SET name = ?, cityDeparture = ?, startCruise = ?," +
                                            " durationCruise = ?, idShip = ? WHERE id = ?";
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
            preparedStatement.setInt(5, object.getShip().getId());

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
            preparedStatement.setInt(5, object.getShip().getId());
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
        AbstractDao<Ticket> ticketDao = parentFactory.createTicketDao(connection);
        AbstractDao<Port> portDao = parentFactory.createPortDao(connection);
        AbstractDao<Ship> shipDao = parentFactory.createShipDao(connection);
        while(resultSet.next()){
            Cruise tempCruise = new Cruise();
            tempCruise.setId(resultSet.getInt("id"));
            tempCruise.setName(resultSet.getString("name"));
            tempCruise.setPorts(portDao.findByInt("idCruise", resultSet.getInt("id")));
            tempCruise.setCityDeparture(resultSet.getString("cityDeparture"));
            tempCruise.setStartCruise(resultSet.getDate("startCruise").toInstant());
            tempCruise.setDurationCruise(resultSet.getDate("durationCruise").toInstant());
            tempCruise.setShip(shipDao.findById(resultSet.getInt("idShip")));
            tempCruise.setPassengers(ticketDao.findByInt("idCruise", resultSet.getInt("id")));
            cruiseList.add(tempCruise);
        }
        return cruiseList;
    }
    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}
}

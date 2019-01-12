package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.model.entity.Ship;
import com.epam.cruiseCompany.model.entity.people.Crew;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipDao extends AbstractDao<Ship>{
    private static final String SQL_INSERT = "INSERT INTO ship(id, name, passenger_capacity) VALUES(?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM ship";
    private static final String SQL_UPDATE = "UPDATE ship SET name = ?, passenger_capacity = ? WHERE id = ?";
    private static final String SQL_UPDATE_CRUISE_ID = "UPDATE ship SET cruise_id = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM ship WHERE id = ?";

    private DaoFactory parentFactory = MySqlDaoFactory.getInstance();
    private AbstractDao<Crew> crewDao = parentFactory.createCrewDao(connection);

    public ShipDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Ship> findAll() {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);

            return parseSet(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Ship findById(int id) {
        if(isExistShip(id)) {
            return findByInt("id", id).get(0);
        }
        return null;
    }
    private boolean isExistShip(int id){
        return !findByInt("id", id).isEmpty();
    }
    @Override
    public List<Ship> findByString(String type, String value) {
        String currentSql = getSelectQuery(type);

        try(PreparedStatement preparedStatement = connection.prepareStatement(currentSql)) {
            preparedStatement.setString(1, value);

            ResultSet resultSet = preparedStatement.executeQuery();

            return parseSet(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ship> findByInt(String type, int value) {
        String currentSql = getSelectQuery(type);

        try(PreparedStatement preparedStatement = connection.prepareStatement(currentSql)) {
            preparedStatement.setInt(1, value);

            ResultSet resultSet = preparedStatement.executeQuery();

            return parseSet(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(Ship object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setInt(1, object.getId());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setInt(3, object.getPassengerCapacity());

            preparedStatement.execute();

            createCrew(object.getCrew(),object.getId());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Ship update(Ship object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)){
            preparedStatement.setString(1, object.getName());
            preparedStatement.setInt(2, object.getPassengerCapacity());
            preparedStatement.setInt(3, object.getId());

            preparedStatement.execute();

            updateCrew(object.getCrew(),object.getId());

            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Ship object) {
        return delete(object.getId());
    }

    @Override
    public boolean delete(int id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            deleteCrew(id);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCruiseId(Ship ship, int cruiseId){
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_CRUISE_ID)) {
            preparedStatement.setInt(1, cruiseId);
            preparedStatement.setInt(2, ship.getId());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Ship> parseSet(ResultSet resultSet) throws SQLException{
        List<Ship> shipList = new ArrayList<>();

        while(resultSet.next()){
            shipList.add(fillShip(resultSet));
        }

        return shipList;
    }
    private Ship fillShip(ResultSet resultSet) throws SQLException{
        Ship tempShip = new Ship();

        tempShip.setId(resultSet.getInt("id"));
        tempShip.setName(resultSet.getString("name"));
        tempShip.setPassengerCapacity(resultSet.getInt("passenger_capacity"));
        tempShip.setCrew(crewDao.findByInt("ship_id", resultSet.getInt("id")));

        return tempShip;
    }
    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}

    private void createCrew(List<Crew> crewList, int shipId){
        for(Crew crew : crewList) {
            crewDao.create(crew);
            ((CrewDao)crewDao).updateShipId(crew, shipId);
        }
    }
    private void updateCrew(List<Crew> crewList, int shipId){
        deleteCrew(shipId);
        createCrew(crewList, shipId);
    }
    private void deleteCrew(int shipId){
        List<Crew> crewList = crewDao.findByInt("ship_id", shipId);

        for(Crew crew : crewList){
            crewDao.delete(crew);
        }
    }
}

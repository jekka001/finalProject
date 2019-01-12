package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.model.entity.Excursion;
import com.epam.cruiseCompany.model.entity.Port;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PortDao extends AbstractDao<Port>{
    private static final String SQL_INSERT = "INSERT INTO port(id, name) VALUES(?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM port INNER JOIN cruise_port " +
            "ON port.id = cruise_port.port_id";
    private static final String SQL_UPDATE = "UPDATE port SET name = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM port WHERE id = ?";

    private DaoFactory parentFactory = MySqlDaoFactory.getInstance();
    private AbstractDao<Excursion> excursionDao = parentFactory.createExcursionDao(connection);

    public PortDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Port> findAll() {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);

            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Port findById(int id) {
        if(isExistPort(id)) {
            return findByInt("id", id).get(0);
        }
        return null;
    }
    private boolean isExistPort(int id){
        return !findByInt("id", id).isEmpty();
    }
    @Override
    public List<Port> findByString(String type, String value) {
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
    public List<Port> findByInt(String type, int value) {
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
    public boolean create(Port object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setInt(1, object.getId());
            preparedStatement.setString(2, object.getName());

            preparedStatement.execute();

            createExcursion(object.getExcursions(),object.getId());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Port update(Port object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)){
            preparedStatement.setString(1, object.getName());
            preparedStatement.setInt(2, object.getId());
            preparedStatement.execute();

            updateExcursion(object.getExcursions(),object.getId());

            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Port object) {
        return delete(object.getId());
    }

    @Override
    public boolean delete(int id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            deleteExcursion(id);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Port> parseSet(ResultSet resultSet) throws SQLException {
        List<Port> shipList = new ArrayList<>();

        while(resultSet.next()){
            shipList.add(fillPort(resultSet));
        }

        return shipList;
    }
    private Port fillPort(ResultSet resultSet) throws SQLException{
        Port tempPort = new Port();

        tempPort.setId(resultSet.getInt("id"));
        tempPort.setName(resultSet.getString("name"));
        tempPort.setExcursions(excursionDao.findByInt("port_id", resultSet.getInt("id")));

        return tempPort;
    }
    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}

    private void createExcursion(List<Excursion> excursionList, int portId){
        for(Excursion excursion : excursionList) {
            excursionDao.create(excursion);
            ((ExcursionDao)excursionDao).updatePortId(excursion, portId);
        }
    }
    private void updateExcursion(List<Excursion> excursionList, int portId){
        deleteExcursion(portId);
        createExcursion(excursionList, portId);
    }
    private void deleteExcursion(int portId){
        List<Excursion> excursionList = excursionDao.findByInt("port_id", portId);

        for(Excursion excursion : excursionList){
            excursionDao.delete(excursion);
        }
    }

}

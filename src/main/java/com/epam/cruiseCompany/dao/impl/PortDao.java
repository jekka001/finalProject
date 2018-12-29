package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.model.entity.Excursion;
import com.epam.cruiseCompany.model.entity.Port;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//+
public class PortDao extends AbstractDao<Port>{
    private static final String SQL_INSERT = "INSERT INTO port(name) VALUES(?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM port";
    private static final String SQL_UPDATE = "UPDATE port SET name = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM port WHERE id = ?";
    private DaoFactory parentFactory = MySqlDaoFactory.getInstance();

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
        return findByInt("id", id).get(0);
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
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getName());

            preparedStatement.execute();

            ResultSet generatedkeys = preparedStatement.getGeneratedKeys();
            if(generatedkeys.next()){
                object.setId(generatedkeys.getInt("id"));
            }
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
        AbstractDao<Excursion> excursionDao = parentFactory.createExcursionDao(connection);
        List<Port> shipList = new ArrayList<>();
        while(resultSet.next()){
            Port tempPort = new Port();
            tempPort.setId(resultSet.getInt("id"));
            tempPort.setName(resultSet.getString("name"));
            tempPort.setExcursions(excursionDao.findByInt("port_id", resultSet.getInt("id")));
            shipList.add(tempPort);
        }
        return shipList;
    }

    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}

    private void createExcursion(List<Excursion> excursionList, int portId){
        ExcursionDao excursionDao = parentFactory.createExcursionDao(connection);
        for(Excursion excursion : excursionList) {
            excursionDao.create(excursion);
            excursionDao.updatePortId(excursion, portId);
        }
    }
    private void updateExcursion(List<Excursion> excursionList, int portId){
        ExcursionDao excursionDao = parentFactory.createExcursionDao(connection);
        List<Excursion> newExcursion = new ArrayList<>();
        deleteExcursion(portId);
        for(Excursion excursion : excursionList) {
            if (excursionDao.findById(excursion.getId()) != null){
                excursionDao.delete(excursion);
                newExcursion.add(excursion);
            }else {
                newExcursion.add(excursion);
            }
        }
        createExcursion(newExcursion, portId);
    }
    private void deleteExcursion(int portId){
        ExcursionDao excursionDao = parentFactory.createExcursionDao(connection);
        List<Excursion> excursionList = excursionDao.findByInt("port_id", portId);
        for(Excursion excursion : excursionList){
            excursionDao.delete(excursion);
        }
    }
}

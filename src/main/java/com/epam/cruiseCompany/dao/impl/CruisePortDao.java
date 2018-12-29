package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.model.Cruise;
import com.epam.cruiseCompany.model.entity.Port;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
//-
public class CruisePortDao extends AbstractDao<Map<Cruise, Port>> {
    private static final String SQL_INSERT = "INSERT INTO cruise_port(cruise_id, port_id) VALUES(?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM cruise_port";
    private static final String SQL_UPDATE = "UPDATE cruise_port SET port_id WHERE cruise_id = ?";
    private static final String SQL_DELETE = "DELETE FROM cruise_port WHERE cruise_id = ?";
    private DaoFactory parentFactory = MySqlDaoFactory.getInstance();

    public CruisePortDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Map<Cruise, Port>> findAll() {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);
            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<Cruise, Port> findById(int id) {
        return findByInt("id", id).get(0);
    }

    @Override
    public List<Map<Cruise, Port>> findByString(String type, String value) {
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
    public List<Map<Cruise, Port>> findByInt(String type, int value) {
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
    public boolean create(Map<Cruise, Port> object) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
                for(Map.Entry<Cruise, Port> map : object.entrySet()) {
                    preparedStatement.setInt(1, map.getKey().getId());
                    preparedStatement.setInt(2, map.getValue().getId());
                    preparedStatement.execute();
                }
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return false;
    }

    @Override
    public Map<Cruise, Port> update(Map<Cruise, Port> object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            for(Map.Entry<Cruise, Port> map : object.entrySet()) {
                preparedStatement.setInt(1, map.getValue().getId());
                preparedStatement.setInt(2, map.getKey().getId());
                preparedStatement.execute();
            }
            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Map<Cruise, Port> object) {
        for(Map.Entry<Cruise, Port> map : object.entrySet()){
            delete(map.getKey().getId());
        }
        return true;
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


    public boolean updateCruiseId(Port object, int cruiseId){
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setInt(1, object.getId());
            preparedStatement.setInt(2, cruiseId);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }//do it later



    private List<Map<Cruise, Port>> parseSet(ResultSet resultSet) throws SQLException {
        AbstractDao<Cruise> cruiseDao = parentFactory.createCruiseDao(connection);
        AbstractDao<Port> portDao = parentFactory.createPortDao(connection);
        List<Map<Cruise, Port>> cruisePortList = new ArrayList<>();
        while(resultSet.next()){
            Cruise tempCruise = cruiseDao.findById(resultSet.getInt("cruise_id"));
            Port tempPort = portDao.findById(resultSet.getInt("port_id"));
            Map<Cruise, Port> tempMap = new LinkedHashMap<>();
            tempMap.put(tempCruise, tempPort);
            cruisePortList.add(tempMap);
        }
        return cruisePortList;
    }

    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}
}

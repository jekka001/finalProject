package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.model.entity.people.Crew;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrewDao extends AbstractDao<Crew> {
    private static final String SQL_INSERT = "INSERT INTO crew(id, name, surname, position) " +
            "VALUES(?, ?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM crew";
    private static final String SQL_UPDATE = "UPDATE crew SET name = ?, surname = ?, position = ? " +
            "WHERE id = ?";
    private static final String SQL_UPDATE_SHIP_ID = "UPDATE crew SET ship_id = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM crew WHERE id = ?";

    public CrewDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Crew> findAll() {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);

            return parseSet(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Crew findById(int id) {
        if(isExistCrew(id)) {
            return findByInt("id", id).get(0);
        }
        return null;
    }
    private boolean isExistCrew(int id){
        return !findByInt("id", id).isEmpty();
    }
    @Override
    public List<Crew> findByString(String type, String value) {
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
    public List<Crew> findByInt(String type, int value) {
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
    public boolean create(Crew object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setInt(1, object.getId());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setString(3, object.getSurname());
            preparedStatement.setString(4, object.getPosition());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Crew update(Crew object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)){
            preparedStatement.setString(1, object.getName());
            preparedStatement.setString(2, object.getSurname());
            preparedStatement.setString(3, object.getPosition());
            preparedStatement.setInt(4, object.getId());

            preparedStatement.execute();

            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Crew object) {
        return delete(object.getId());
    }

    @Override
    public boolean delete(int id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateShipId(Crew object, int shipId){
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_SHIP_ID)) {
            preparedStatement.setInt(1, shipId);
            preparedStatement.setInt(2, object.getId());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Crew> parseSet(ResultSet resultSet) throws SQLException {
        List<Crew> crewList = new ArrayList<>();

        while(resultSet.next()){
            crewList.add(fillCrew(resultSet));
        }

        return crewList;
    }
    private Crew fillCrew(ResultSet resultSet) throws SQLException{
        Crew tempCrew = new Crew();

        tempCrew.setId(resultSet.getInt("id"));
        tempCrew.setName(resultSet.getString("name"));
        tempCrew.setSurname(resultSet.getString("surname"));
        tempCrew.setPosition(resultSet.getString("position"));

        return tempCrew;
    }
    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}
}

package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.model.entity.people.Crew;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// maybe comply
public class CrewDao extends AbstractDao<Crew> {
    private static final String SQL_INSERT = "INSERT INTO crew(surName, name, position) " +
            "VALUES(?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM crew";
    private static final String SQL_UPDATE = "UPDATE crew SET surName = ?, name = ?, position = ? " +
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
        return findByInt("id", id).get(0);
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
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getSurName());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setString(3, object.getPosition());

            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                object.setId(generatedKeys.getInt("id"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }//maybe really, but look later

    @Override
    public Crew update(Crew object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)){
            preparedStatement.setString(1, object.getSurName());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setString(3, object.getPosition());
            preparedStatement.setInt(4, object.getId());

            preparedStatement.execute();
            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }//maybe really, but look later

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

    private List<Crew> parseSet(ResultSet resultSet) throws SQLException {
        List<Crew> crewList = new ArrayList<>();

        while(resultSet.next()){
            Crew tempCrew = new Crew();
            tempCrew.setId(resultSet.getInt("id"));
            tempCrew.setSurName(resultSet.getString("surName"));
            tempCrew.setName(resultSet.getString("name"));
            tempCrew.setPosition(resultSet.getString("position"));
            crewList.add(tempCrew);
        }
        return crewList;
    }

    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}
}

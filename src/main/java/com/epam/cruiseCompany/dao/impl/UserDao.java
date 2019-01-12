package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.model.entity.people.Role;
import com.epam.cruiseCompany.model.entity.people.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao<User> {
    private static final String SQL_INSERT = "INSERT INTO user(id, name, surname, email, password, role) " +
                                            "VALUES(?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM user";
    private static final String SQL_UPDATE = "UPDATE user SET name = ?, surname = ?, email = ?, password = ?, role = ? " +
                                            "WHERE id = ?";
    private static final String SQL_UPDATE_SALT = "UPDATE user SET salt = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM user WHERE id = ?";

    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<User> findAll() {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);

            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findById(int id) {
        if(isExistUser(id)) {
            return findByInt("id", id).get(0);
        }
        return null;
    }
    private boolean isExistUser(int id){
        return !findByInt("id", id).isEmpty();
    }
    @Override
    public List<User> findByString(String type, String value) {
        String currentSql = getSelectQuery(type);

        try (PreparedStatement preparedStatement = connection.prepareStatement(currentSql)){
            preparedStatement.setString(1, value);

            ResultSet resultSet = preparedStatement.executeQuery();

            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findByInt(String type, int value) {
        String currentSql = getSelectQuery(type);

        try (PreparedStatement preparedStatement = connection.prepareStatement(currentSql)){
            preparedStatement.setInt(1, value);

            ResultSet resultSet = preparedStatement.executeQuery();

            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(User object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setInt(1, object.getId());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setString(3, object.getSurname());
            preparedStatement.setString(4, object.getEmail());
            preparedStatement.setString(5, object.getPassword());
            preparedStatement.setString(6, object.getRole().name());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User update(User object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, object.getName());
            preparedStatement.setString(2, object.getSurname());
            preparedStatement.setString(3, object.getEmail());
            preparedStatement.setString(4, object.getPassword());
            preparedStatement.setString(5, object.getRole().name());
            preparedStatement.setInt(6, object.getId());

            preparedStatement.execute();

            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(User object) {
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

    private List<User> parseSet(ResultSet resultSet) throws SQLException{
        List<User> userList = new ArrayList<>();

        while(resultSet.next()){
            userList.add(fillUser(resultSet));
        }

        return userList;
    }
    private User fillUser(ResultSet resultSet) throws SQLException{
        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(Role.valueOf(resultSet.getString("role")));

        return user;
    }
    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}

    public boolean updateSalt(int salt, int clientId){
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_SALT)) {
            preparedStatement.setInt(1, salt);
            preparedStatement.setInt(2, clientId);

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int findSalt(int clientId){
        String currentSql = getSelectQuery("id");

        try(PreparedStatement preparedStatement = connection.prepareStatement(currentSql)) {
            preparedStatement.setInt(1, clientId);

            ResultSet resultSet = preparedStatement.executeQuery();

            return parseSetWithSalt(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private int parseSetWithSalt(ResultSet resultSet) throws SQLException{
        resultSet.next();

        return resultSet.getInt("salt");
    }
}

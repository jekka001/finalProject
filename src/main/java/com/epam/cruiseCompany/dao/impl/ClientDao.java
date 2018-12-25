package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.model.entity.people.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//comply
public class ClientDao extends AbstractDao<Client> {
    private static final String SQL_INSERT = "INSERT INTO client(email, surName, name, password) " +
                                            "VALUES(?, ?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM client";
    private static final String SQL_UPDATE = "UPDATE client SET email = ?, surName = ?, name = ?, password = ? " +
                                            "WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM client WHERE id = ?";

    public ClientDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Client> findAll() {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);
            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client findById(int id) {
        return findByInt("id", id).get(0);
    }

    @Override
    public List<Client> findByString(String type, String value) {
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
    public List<Client> findByInt(String type, int value) {
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
    public boolean create(Client object) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, object.getEmail());
            preparedStatement.setString(2, object.getSurName());
            preparedStatement.setString(3, object.getName());
            preparedStatement.setString(4, object.getPassword());

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
    public Client update(Client object) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, object.getEmail());
            preparedStatement.setString(2, object.getSurName());
            preparedStatement.setString(3, object.getName());
            preparedStatement.setString(4, object.getPassword());
            preparedStatement.setInt(5, object.getId());

            preparedStatement.execute();
            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Client object) {
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

    private List<Client> parseSet(ResultSet resultSet) throws SQLException{
        List<Client> clientList = new ArrayList<>();
        while(resultSet.next()){
            Client client = new Client();
            client.setId(resultSet.getInt("id"));
            client.setEmail(resultSet.getString("email"));
            client.setSurName(resultSet.getString("surName"));
            client.setName(resultSet.getString("name"));
            client.setPassword(resultSet.getString("password"));
            clientList.add(client);
        }
        return clientList;
    }
    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}
}

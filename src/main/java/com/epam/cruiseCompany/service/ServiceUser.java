package com.epam.cruiseCompany.service;

import com.epam.cruiseCompany.dao.connection.ConnectionPoolHolder;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.dao.impl.UserDao;
import com.epam.cruiseCompany.model.entity.people.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ServiceUser {
    private UserDao dao;
    private Connection connection;

    public ServiceUser(){
        connection = new ConnectionPoolHolder().getConnection();
        dao = MySqlDaoFactory.getInstance().createClientDao(connection);
    }

    public User isEmpty(String login, String password){
        List<User> listClient = dao.findByString("email", login);
        for(User client : listClient){
           // if(Encryption.checkPassword(password, client.getPassword()))//password need salt.
            if(client.getPassword().equals(password)) {
                return client;
            }
        }
        return null;
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

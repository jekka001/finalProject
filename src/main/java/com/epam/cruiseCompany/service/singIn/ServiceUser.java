package com.epam.cruiseCompany.service.singIn;

import com.epam.cruiseCompany.dao.connection.ConnectionPoolHolder;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.dao.impl.AbstractDao;
import com.epam.cruiseCompany.dao.impl.UserDao;
import com.epam.cruiseCompany.model.entity.people.Role;
import com.epam.cruiseCompany.model.entity.people.User;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceUser {
    private AbstractDao<User> userDao;
    private Connection connection;
    private User user;
    private int salt;
    private String passwordWithSalt;

    public ServiceUser(){
        connection = new ConnectionPoolHolder().getConnection();
        userDao = MySqlDaoFactory.getInstance().createClientDao(connection);
    }

    public User getUser(String login, String password){
        if(isExistEmail(login)) {
            user = findUserInBD(login);
            salt = findSaltInBD();
            passwordWithSalt = addSaltToPassword(salt, password);

            if (checkPassword()) {
                return user;
            }
        }
        return null;
    }
    private boolean checkPassword(){
        return Encryption.checkPassword(passwordWithSalt, user.getPassword());
    }
    private int findSaltInBD(){
        return ((UserDao)userDao).findSalt(user.getId());
    }
    private User findUserInBD(String login){
        return userDao.findByString("email", login).get(0);
    }
    public boolean isExistEmail(String login){
        return !userDao.findByString("email", login).isEmpty();
    }

    public void makingUser(String name, String surname, String email, String password){
        salt = Encryption.generationSalt();
        int idUser = findNumberOfUsers();
        String encryptedPassword = protectPassword(password);
        user = new User(idUser, name, surname, email, encryptedPassword, Role.CLIENT);
        insertDataInBD();
    }
    private int findNumberOfUsers(){
        return userDao.findAll().size() + 1;
    }
    private String addSaltToPassword(int salt, String password){
        return salt + password;
    }
    private String protectPassword(String password){
        passwordWithSalt = addSaltToPassword(salt, password);
        return Encryption.encryption(passwordWithSalt);
    }
    private void insertDataInBD(){
        userDao.create(user);
        ((UserDao)userDao).updateSalt(salt, user.getId());
    }

    public void updateUser(User user){
        userDao.update(user);
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

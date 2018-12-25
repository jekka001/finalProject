package com.epam.cruiseCompany.service;

import com.epam.cruiseCompany.dao.connection.ConnectionPoolHolder;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.dao.impl.ClientDao;
import com.epam.cruiseCompany.model.entity.people.Client;

import java.util.List;

public class ServiceClient {
    private ClientDao dao;

    public ServiceClient(){
        dao = MySqlDaoFactory.getInstance().createClientDao(new ConnectionPoolHolder().getConnection());
    }

    public Client isEmpty(String login, String password){
        List<Client> listClient = dao.findByString("email", login);
        for(Client client : listClient){
            if(Encryption.checkPassword(password, client.getPassword()))//password need salt.
                return client;
        }
        return null;
    }
}

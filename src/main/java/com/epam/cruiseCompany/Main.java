package com.epam.cruiseCompany;

import com.epam.cruiseCompany.dao.connection.ConnectionPoolHolder;
import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.dao.impl.ClientDao;

public class Main {
    public static void main(String[] args) {
       new ConnectionPoolHolder().getConnection();
    }
}

package com.epam.cruiseCompany;

import com.epam.cruiseCompany.dao.connection.ConnectionPoolHolder;

public class Main {
    public static void main(String[] args) {
       new ConnectionPoolHolder().getConnection();
    }
}

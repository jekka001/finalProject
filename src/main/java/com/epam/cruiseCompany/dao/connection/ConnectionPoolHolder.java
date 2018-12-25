package com.epam.cruiseCompany.dao.connection;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;

    private static DataSource getDataSource(){
        if(dataSource == null){
            synchronized (ConnectionPoolHolder.class){
                if(dataSource == null){
                    BasicDataSource basicDataSource = new BasicDataSource();
                    ResourceBundle resourceBundle = ResourceBundle.getBundle("dataBase");
                    basicDataSource.setUrl(resourceBundle.getString("url"));
                    basicDataSource.setUsername(resourceBundle.getString("user"));
                    basicDataSource.setPassword(resourceBundle.getString("pass"));
                    basicDataSource.setMinIdle(Integer.valueOf(resourceBundle.getString("min.idle")));
                    basicDataSource.setMaxIdle(Integer.valueOf(resourceBundle.getString("max.idle")));
                    basicDataSource.setMaxOpenPreparedStatements(Integer.valueOf(resourceBundle.getString("max.open.prepare.statements")));
                    dataSource = basicDataSource;
                }
            }
        }
        return dataSource;
    }

    public Connection getConnection(){
        try{
            return getDataSource().getConnection();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

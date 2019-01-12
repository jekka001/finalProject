package com.epam.cruiseCompany.dao.connection;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;

    private static DataSource getDataSource(){
        if(isExistDataSource(dataSource)){
            synchronized (ConnectionPoolHolder.class){
                if(isExistDataSource(dataSource)){
                    fillDataSource();
                }
            }
        }
        return dataSource;
    }
    private static boolean isExistDataSource(DataSource dataSource){
        return dataSource == null;
    }
    private static void fillDataSource(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("dataBase");
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(resourceBundle.getString("url"));
        basicDataSource.setUsername(resourceBundle.getString("user"));
        basicDataSource.setPassword(resourceBundle.getString("pass"));
        basicDataSource.setMinIdle(Integer.valueOf(resourceBundle.getString("min.idle")));
        basicDataSource.setMaxIdle(Integer.valueOf(resourceBundle.getString("max.idle")));
        basicDataSource.setMaxOpenPreparedStatements(Integer.valueOf(resourceBundle.getString("max.open.prepare.statements")));
        dataSource = basicDataSource;
    }


    public Connection getConnection(){
        try{
            return getDataSource().getConnection();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

package com.epam.cruiseCompany.dao.impl;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractDao<T> {
    protected Connection connection;

    AbstractDao(Connection connection){
        this.connection = connection;
    }

    public abstract List<T> findAll();
    public abstract T findById(int id);
    public abstract List<T> findByString(String type, String value);
    public abstract List<T> findByInt(String type, int value);
    public abstract boolean create(T object);
    public abstract T update(T object);
    public abstract boolean delete(T object);
    public abstract boolean delete(int id);
}

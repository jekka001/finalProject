package com.epam.cruiseCompany.dao.factory;

import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.dao.impl.*;

import java.sql.Connection;

public abstract class DaoFactory {
    private static volatile DaoFactory daoFactory;

    public abstract ClientDao createClientDao(Connection connection);
    public abstract CrewDao createCrewDao(Connection connection);
    public abstract CruiseDao createCruiseDao(Connection connection);
    public abstract ExcursionDao createExcursionDao(Connection connection);
    public abstract PortDao createPortDao(Connection connection);
    public abstract ShipDao createShipDao(Connection connection);
    public abstract TicketDao createTicketDao(Connection connection);

    public static DaoFactory getInstance(){
        if(daoFactory == null){
            synchronized (DaoFactory.class){
                if(daoFactory == null){
                    DaoFactory temp = new MySqlDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
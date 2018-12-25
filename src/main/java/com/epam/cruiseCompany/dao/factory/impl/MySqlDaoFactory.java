package com.epam.cruiseCompany.dao.factory.impl;
import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.impl.*;

import java.sql.Connection;

public class MySqlDaoFactory extends DaoFactory {

    @Override
    public ClientDao createClientDao(Connection connection) {
        return new ClientDao(connection);
    }

    @Override
    public CrewDao createCrewDao(Connection connection) {
        return new CrewDao(connection);
    }

    @Override
    public CruiseDao createCruiseDao(Connection connection) {
        return new CruiseDao(connection);
    }

    @Override
    public ExcursionDao createExcursionDao(Connection connection) {
        return new ExcursionDao(connection);
    }

    @Override
    public PortDao createPortDao(Connection connection) {
        return new PortDao(connection);
    }

    @Override
    public ShipDao createShipDao(Connection connection) {
        return new ShipDao(connection);
    }

    @Override
    public TicketDao createTicketDao(Connection connection) {
        return new TicketDao(connection);
    }
}

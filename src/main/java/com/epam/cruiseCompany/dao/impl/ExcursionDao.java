package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.model.entity.Excursion;
import com.epam.cruiseCompany.model.entity.ticket.ExcursionTicket;
import com.epam.cruiseCompany.model.entity.ticket.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// +
public class ExcursionDao extends AbstractDao<Excursion> {
    private static final String SQL_INSERT = "INSERT INTO excursion(name) VALUES(?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM excursion";
    private static final String SQL_UPDATE = "UPDATE excursion SET name = ?  WHERE id = ?";
    private static final String SQL_UPDATE_PORT_ID = "UPDATE excursion SET port_id = ?  WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM excursion WHERE id = ?";
    private DaoFactory parentFactory = MySqlDaoFactory.getInstance();


    public ExcursionDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Excursion> findAll() {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);
            return parseSet(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Excursion findById(int id) {
        return findByInt("id", id).get(0);
    }

    @Override
    public List<Excursion> findByString(String type, String value) {
        String currentSql = getSelectQuery(type);
        try(PreparedStatement preparedStatement = connection.prepareStatement(currentSql)) {
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseSet(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Excursion> findByInt(String type, int value) {
        String currentSql = getSelectQuery(type);
        try(PreparedStatement preparedStatement = connection.prepareStatement(currentSql)) {
            preparedStatement.setInt(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseSet(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(Excursion object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getName());
            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                object.setId(generatedKeys.getInt("id"));
            }

            createExcursionTicket(object.getTickets(), object.getId());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Excursion update(Excursion object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)){
            preparedStatement.setString(1, object.getName());
            preparedStatement.setInt(2, object.getId());

            preparedStatement.execute();

            updateExcursionTicket(object.getTickets(), object.getId());

            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Excursion object) {
        return delete(object.getId());
    }

    @Override
    public boolean delete(int id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            deleteExcursionTicket(id);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePortId(Excursion excursion, int portId){
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PORT_ID)) {
            preparedStatement.setInt(1, portId);
            preparedStatement.setInt(2, excursion.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Excursion> parseSet(ResultSet resultSet) throws SQLException {
        List<Excursion> excursionList = new ArrayList<>();
        AbstractDao<ExcursionTicket> ticketDao = parentFactory.createExcursionTicketDao(connection);
        while(resultSet.next()){
            Excursion tempExcursion = new Excursion();
            tempExcursion.setId(resultSet.getInt("id"));
            tempExcursion.setName(resultSet.getString("name"));
            tempExcursion.setTickets(ticketDao.findByInt("idExcursion", resultSet.getInt("id")));
            excursionList.add(tempExcursion);
        }
        return excursionList;
    }

    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}

    private void createExcursionTicket(List<ExcursionTicket> ticketList, int excursionId){
        ExcursionTicketDao excursionTicketDao = parentFactory.createExcursionTicketDao(connection);
        for(ExcursionTicket ticket : ticketList) {
            excursionTicketDao.create(ticket);
            excursionTicketDao.updateExcursionId(ticket, excursionId);
        }
    }
    private void updateExcursionTicket(List<ExcursionTicket> ticketList, int excursionId){
        ExcursionTicketDao excursionTicketDao = parentFactory.createExcursionTicketDao(connection);
        List<ExcursionTicket> newExcursionTicket = new ArrayList<>();
        deleteExcursionTicket(excursionId);
        for(ExcursionTicket excursionTicket : ticketList) {
            if (excursionTicketDao.findById(excursionTicket.getId()) != null){
                excursionTicketDao.delete(excursionTicket);
                newExcursionTicket.add(excursionTicket);
            }else {
                newExcursionTicket.add(excursionTicket);
            }
        }
        createExcursionTicket(newExcursionTicket, excursionId);
    }
    private void deleteExcursionTicket(int excursionId){
        ExcursionTicketDao excursionTicketDao = parentFactory.createExcursionTicketDao(connection);
        List<ExcursionTicket> excursionTicketsList = excursionTicketDao.findByInt("excursion_id", excursionId);
        for(ExcursionTicket excursionTicket : excursionTicketsList){
            excursionTicketDao.delete(excursionTicket);
        }
    }

}

package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.model.entity.people.Client;
import com.epam.cruiseCompany.model.entity.ticket.Status;
import com.epam.cruiseCompany.model.entity.ticket.Ticket;
import com.epam.cruiseCompany.model.entity.ticket.TicketClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//maybe comply
public class TicketDao extends AbstractDao<Ticket>{
    private static final String SQL_INSERT = "INSERT INTO ticket(price, status, idClient) VALUES(?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM ticket";
    private static final String SQL_UPDATE = "UPDATE ticket SET price = ?, status = ?, id_Client = ?" +
                                    " WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM ticket WHERE id = ?";
    private DaoFactory parentFactory = MySqlDaoFactory.getInstance();

    public TicketDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Ticket> findAll() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);
            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Ticket findById(int id) {
       return findByInt("id", id).get(0);
    }
    @Override
    public List<Ticket> findByString(String type, String value) {
        String currentSql = getSelectQuery(type);
        try(PreparedStatement preparedStatement = connection.prepareStatement(currentSql)){
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseSet(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ticket> findByInt(String type, int value) {
        String currentSql = getSelectQuery(type);
        try(PreparedStatement preparedStatement = connection.prepareStatement(currentSql)){
            preparedStatement.setInt(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean create(Ticket object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, object.getPrice());
            preparedStatement.setString(2, object.getStatus().toString());
            preparedStatement.setInt(3, object.getClient().getId());
            //preparedStatement.setString(4, object.getTicketClass().toString());
            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                object.setId(generatedKeys.getInt("id"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }//not really, do it later.
    @Override
    public Ticket update(Ticket object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)){
            preparedStatement.setDouble(1, object.getPrice());
            preparedStatement.setString(2, object.getStatus().toString());
            preparedStatement.setInt(3, object.getClient().getId());
            //preparedStatement.setString(4, object.getTicketClass().toString());
            preparedStatement.setInt(5, object.getId());

            preparedStatement.execute();
            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }//not really, do it later.


    @Override
    public boolean delete(Ticket object) {
        return delete(object.getId());
    }
    @Override
    public boolean delete(int id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Ticket> parseSet(ResultSet resultSet) throws SQLException{
        AbstractDao<Client> clientDao = parentFactory.createClientDao(connection);
        List<Ticket> ticketList = new ArrayList<>();
        while (resultSet.next()){
            Ticket tempTicket = new Ticket();
            tempTicket.setId(resultSet.getInt("id"));
            tempTicket.setPrice(resultSet.getDouble("price"));
            tempTicket.setStatus(Status.valueOf(resultSet.getString("status")));
            tempTicket.setClient(clientDao.findById(resultSet.getInt("idClient")));
            tempTicket.setTicketClass(TicketClass.valueOf(resultSet.getString("ticketClass")));
            ticketList.add(tempTicket);
        }
        return ticketList;
    }// not really, do it later

    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}
}

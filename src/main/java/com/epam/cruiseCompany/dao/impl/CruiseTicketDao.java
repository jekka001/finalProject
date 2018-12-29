package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.model.entity.people.User;
import com.epam.cruiseCompany.model.entity.ticket.CruiseTicket;
import com.epam.cruiseCompany.model.entity.ticket.Status;
import com.epam.cruiseCompany.model.entity.ticket.TicketClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//+
public class CruiseTicketDao extends AbstractDao<CruiseTicket>{
    private static final String SQL_INSERT = "INSERT INTO cruise_ticket(price, status, user_id, ticket_class)" +
                                            " VALUES(?, ?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM cruise_ticket";
    private static final String SQL_UPDATE = "UPDATE cruise_ticket SET price = ?, status = ?, user_id = ?, " +
                                            " ticket_class = ? WHERE id = ?";
    private static final String SQL_UPDATE_CRUISE_ID = "UPDATE cruise_ticket SET cruise_id = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM cruise_ticket WHERE id = ?";
    private DaoFactory parentFactory = MySqlDaoFactory.getInstance();

    public CruiseTicketDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<CruiseTicket> findAll() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);
            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public CruiseTicket findById(int id) {
        return findByInt("id", id).get(0);
    }
    @Override
    public List<CruiseTicket> findByString(String type, String value) {
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
    public List<CruiseTicket> findByInt(String type, int value) {
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
    public boolean create(CruiseTicket object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, object.getPrice());
            preparedStatement.setString(2, object.getStatus().toString());
            preparedStatement.setInt(3, object.getUser().getId());
            preparedStatement.setString(4, object.getTicketClass().name());
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
    }

    @Override
    public CruiseTicket update(CruiseTicket object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)){
            preparedStatement.setDouble(1, object.getPrice());
            preparedStatement.setString(2, object.getStatus().toString());
            preparedStatement.setInt(3, object.getUser().getId());
            preparedStatement.setString(4, object.getTicketClass().name());
            preparedStatement.setInt(5, object.getId());

            preparedStatement.execute();
            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(CruiseTicket object) {
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

    public boolean updateCruiseId(CruiseTicket cruiseTicket, int cruiseId){
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_CRUISE_ID)) {
            preparedStatement.setInt(1, cruiseId);
            preparedStatement.setInt(2, cruiseTicket.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<CruiseTicket> parseSet(ResultSet resultSet) throws SQLException{
        AbstractDao<User> userDao = parentFactory.createClientDao(connection);
        List<CruiseTicket> ticketList = new ArrayList<>();
        while (resultSet.next()){
            CruiseTicket tempTicket = new CruiseTicket();
            tempTicket.setId(resultSet.getInt("id"));
            tempTicket.setPrice(resultSet.getDouble("price"));
            tempTicket.setStatus(Status.valueOf(resultSet.getString("status")));
            tempTicket.setUser(userDao.findById(resultSet.getInt("user_id")));
            tempTicket.setTicketClass(TicketClass.valueOf(resultSet.getString("ticket_class")));
            ticketList.add(tempTicket);
        }
        return ticketList;
    }
    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}
}

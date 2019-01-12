package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.model.entity.people.User;
import com.epam.cruiseCompany.model.entity.ticket.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExcursionTicketDao extends AbstractDao<ExcursionTicket>{
    private static final String SQL_INSERT = "INSERT INTO excursion_ticket(id, price, status, user_id) VALUES(?, ?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM excursion_ticket";
    private static final String SQL_UPDATE = "UPDATE excursion_ticket SET price = ?, status = ?, user_id = ? WHERE id = ?";
    private static final String SQL_UPDATE_EXCURSION_ID = "UPDATE excursion_ticket SET excursion_id = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM excursion_ticket WHERE id = ?";

    private DaoFactory parentFactory = MySqlDaoFactory.getInstance();
    private AbstractDao<User> userDao = parentFactory.createClientDao(connection);
    public ExcursionTicketDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<ExcursionTicket> findAll() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);

            return parseSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public ExcursionTicket findById(int id) {
        if(isExistExcursionTicket(id)) {
            return findByInt("id", id).get(0);
        }
        return null;
    }
    private boolean isExistExcursionTicket(int id){
        return !findByInt("id", id).isEmpty();
    }
    @Override
    public List<ExcursionTicket> findByString(String type, String value) {
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
    public List<ExcursionTicket> findByInt(String type, int value) {
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
    public boolean create(ExcursionTicket object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setInt(1, object.getId());
            preparedStatement.setDouble(2, object.getPrice());
            preparedStatement.setString(3, object.getStatus().toString());
            preparedStatement.setInt(4, object.getUser().getId());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public ExcursionTicket update(ExcursionTicket object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)){
            preparedStatement.setDouble(1, object.getPrice());
            preparedStatement.setString(2, object.getStatus().toString());
            preparedStatement.setInt(3, object.getUser().getId());
            preparedStatement.setInt(4, object.getId());

            preparedStatement.execute();

            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(ExcursionTicket object) {
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

    public boolean updateExcursionId(ExcursionTicket excursionTicket, int excursionId){
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_EXCURSION_ID)) {
            preparedStatement.setInt(1, excursionId);
            preparedStatement.setInt(2, excursionTicket.getId());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<ExcursionTicket> parseSet(ResultSet resultSet) throws SQLException{
        List<ExcursionTicket> ticketList = new ArrayList<>();

        while (resultSet.next()){
            ticketList.add(fillExcursionTicket(resultSet));
        }

        return ticketList;
    }
    private ExcursionTicket fillExcursionTicket(ResultSet resultSet) throws SQLException{
        ExcursionTicket tempTicket = new ExcursionTicket();

        tempTicket.setId(resultSet.getInt("id"));
        tempTicket.setPrice(resultSet.getDouble("price"));
        tempTicket.setStatus(Status.valueOf(resultSet.getString("status")));
        tempTicket.setUser(userDao.findById(resultSet.getInt("user_id")));

        return tempTicket;
    }
    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}
}

package com.epam.cruiseCompany.dao.impl;

import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.model.entity.Ship;
import com.epam.cruiseCompany.model.entity.people.Crew;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//comply
public class ShipDao extends AbstractDao<Ship>{
    private static final String SQL_INSERT = "INSERT INTO ship(name, passengerCapacity) VALUES(?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM ship";
    private static final String SQL_UPDATE = "UPDATE ship SET name = ?, passengerCapacity = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM ship WHERE id = ?";
    private DaoFactory parentFactory = MySqlDaoFactory.getInstance();

    public ShipDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Ship> findAll() {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);
            return parseSet(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Ship findById(int id) {
        return findByInt("id", id).get(0);
    }

    @Override
    public List<Ship> findByString(String type, String value) {
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
    public List<Ship> findByInt(String type, int value) {
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
    public boolean create(Ship object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getName());
            preparedStatement.setInt(2, object.getPassengerCapacity());

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
    public Ship update(Ship object) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)){
            preparedStatement.setString(1, object.getName());
            preparedStatement.setInt(2, object.getPassengerCapacity());
            preparedStatement.setInt(3, object.getId());

            preparedStatement.execute();

            return object;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Ship object) {
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

    private List<Ship> parseSet(ResultSet resultSet) throws SQLException{
        List<Ship> shipList = new ArrayList<>();
        AbstractDao<Crew> crewDao = parentFactory.createCrewDao(connection);
        while(resultSet.next()){
            Ship tempShip = new Ship();
            tempShip.setId(resultSet.getInt("id"));
            tempShip.setName(resultSet.getString("name"));
            tempShip.setPassengerCapacity(resultSet.getInt("passengerCapacity"));
            tempShip.setCrew(crewDao.findByInt("idShip", resultSet.getInt("id")));
            shipList.add(tempShip);
        }
        return shipList;
    }

    private String getSelectQuery(String type){return SQL_FIND_ALL + " WHERE " + type + " = ?";}
}

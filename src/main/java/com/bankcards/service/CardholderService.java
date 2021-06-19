package com.bankcards.service;

import com.bankcards.bl.Util;
import com.bankcards.dao.CardholderDao;
import com.bankcards.domain.Cardholder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardholderService extends Util implements CardholderDao {

    Connection connection = null;

    @Override
    public Cardholder get(Long id) throws SQLException {
        connection = getConnection();

        PreparedStatement preparedStatement = null;

        String sql = "SELECT ID, NAME, ADDRESS, PHONENUMBER, BIRTHDAY FROM CARDHOLDER WHERE ID=?";

        Cardholder cardholder = new Cardholder();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.first();
                cardholder.setId(resultSet.getLong("ID"));
                cardholder.setName(resultSet.getString("NAME"));
                cardholder.setAddress(resultSet.getString("ADDRESS"));
                cardholder.setPhonenumber(resultSet.getString("PHONENUMBER"));
                cardholder.setBirthday(resultSet.getDate("BIRTHDAY"));

        }
            catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return cardholder;
    }

    @Override
    public List<Cardholder> getAll() throws SQLException{
        List<Cardholder> cardholders = new ArrayList<>();
        connection = getConnection();
        String sql = "SELECT * FROM CARDHOLDER";

        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Cardholder cardholder = new Cardholder();
                cardholder.setId(resultSet.getLong("ID"));
                cardholder.setName(resultSet.getString("NAME"));
                cardholder.setAddress(resultSet.getString("ADDRESS"));
                cardholder.setPhonenumber(resultSet.getString("PHONENUMBER"));
                cardholder.setBirthday(resultSet.getDate("BIRTHDAY"));

                cardholders.add(cardholder);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return cardholders;

    }

    @Override
    public Cardholder update(Long id) throws SQLException {
        Cardholder cardholder = new Cardholder();
        Scanner scanner = new Scanner(System.in);
        PreparedStatement preparedStatement = null;

        System.out.println("Данные выбранного пользователя:\n" + get(id).toString());
        connection = getConnection();
        System.out.println("Введите новые днные пользователя: ");
        System.out.print("Имя: ");
        String name = scanner.nextLine();
        System.out.print("Адрес: ");
        String address = scanner.nextLine();
        System.out.print("Номер телефона: ");
        String phonenumber = scanner.nextLine();
        System.out.print("Дата рождения (YYYY-MM-DD): ");
        Date birthday = Date.valueOf(scanner.nextLine());

        String sql = "UPDATE CARDHOLDER SET NAME=?, ADDRESS=?, PHONENUMBER=?, BIRTHDAY=? WHERE ID=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, phonenumber);
            preparedStatement.setDate(4, birthday);
            preparedStatement.setLong(5, id);

            preparedStatement.executeUpdate();
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }

        return get(id);
    }

    @Override
    public void create(Cardholder something) throws SQLException {
        PreparedStatement preparedStatement = null;
        connection = getConnection();
        String sql ="INSERT INTO CARDHOLDER (ID, NAME, ADDRESS, PHONENUMBER, BIRTHDAY) VALUES(?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, something.getId());
            preparedStatement.setString(2, something.getName());
            preparedStatement.setString(3, something.getAddress());
            preparedStatement.setString(4, something.getPhonenumber());
            preparedStatement.setDate(5, something.getBirthday());

            preparedStatement.executeUpdate();
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public Cardholder delete(Long id) throws SQLException {
        PreparedStatement preparedStatement = null;

        Cardholder cardholder = get(id);
        connection = getConnection();
        String setNullForAllCreditCardsForCurrentOwner = "UPDATE CREDITCARD SET OWNER = NULL WHERE ID = ?";
        try {
            preparedStatement = connection.prepareStatement(setNullForAllCreditCardsForCurrentOwner);
            preparedStatement.setLong(1, id);
            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("Owner key set NULL");
            }
            else System.out.println("Nothing was removed");
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        String sql = "DELETE FROM CARDHOLDER WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("Row was removed");
            }
            else System.out.println("Nothing was removed");
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        return cardholder;
    }
}

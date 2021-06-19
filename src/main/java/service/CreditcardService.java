package service;

import bl.Util;
import dao.CreditcardDao;
import domain.Creditcard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreditcardService extends Util implements CreditcardDao {
    Connection connection = null;

    @Override
    public Creditcard get(Long id) throws SQLException {
        connection = getConnection();

        PreparedStatement preparedStatement = null;

        String sql = "SELECT * FROM CREDITCARD WHERE ID=?";

        Creditcard creditcard = new Creditcard();
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.first();
            creditcard.setId(resultSet.getLong("ID"));
            creditcard.setCardNumber(resultSet.getLong("CARDNUMBER"));
            creditcard.setExpdate(resultSet.getString("EXPDATE"));
            creditcard.setCvv(resultSet.getInt("CVV"));
            creditcard.setOwner(resultSet.getLong("OWNER"));

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
        return creditcard;
    }

    @Override
    public List<Creditcard> getAll() throws SQLException {
        List<Creditcard> creditcards = new ArrayList<>();

        String sql = "SELECT * FROM CREDITCARD";

        connection = getConnection();

        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Creditcard creditcard = new Creditcard();
                creditcard.setId(resultSet.getLong("ID"));
                creditcard.setCardNumber(resultSet.getLong("CARDNUMBER"));
                creditcard.setExpdate(resultSet.getString("EXPDATE"));
                creditcard.setCvv(resultSet.getInt("CVV"));
                creditcard.setOwner(resultSet.getLong("OWNER"));

                creditcards.add(creditcard);
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
        return creditcards;
    }

    @Override
    public Creditcard update(Long id) throws SQLException {

        Creditcard creditcard = new Creditcard();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Данные выбранной карты:\n" + get(id).toString());
        connection = getConnection();
        System.out.println("Введите новые данные кредитной карты: ");
        System.out.print("Номер карты: ");
        long cardnumber = scanner.nextLong();
        System.out.print("ExpDate (mm/YY): ");
        scanner.nextLine();
        String expdate = scanner.nextLine();
        System.out.print("CVV: ");
        int cvv = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Owner ID: ");
        long ownerId = scanner.nextLong();

        PreparedStatement preparedStatement = null;

        String sql = "UPDATE CREDITCARD SET CARDNUMBER=?, EXPDATE=?, CVV=?, OWNER=? WHERE ID=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, cardnumber);
            preparedStatement.setString(2, expdate);
            preparedStatement.setInt(3, cvv);
            preparedStatement.setLong(4, ownerId);
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
    public void create(Creditcard something) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql ="INSERT INTO CREDITCARD (ID, CARDNUMBER, EXPDATE, CVV, OWNER) VALUES(?, ?, ?, ?, ?)";

        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, something.getId());
            preparedStatement.setLong(2, something.getCardNumber());
            preparedStatement.setString(3, something.getExpdate());
            preparedStatement.setInt(4, something.getCvv());
            preparedStatement.setLong(5, something.getOwner());

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
    public Creditcard delete(Long id) throws SQLException {
        PreparedStatement preparedStatement = null;
        Creditcard creditcard = get(id);
        connection = getConnection();

        String sql = "DELETE FROM CREDITCARD WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("The row was removed");
            }
            else System.out.println("Nothing was removed");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }

        return creditcard;
    }
}

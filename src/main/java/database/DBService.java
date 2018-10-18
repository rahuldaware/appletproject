package database;


import model.BookingModel;
import model.TransactionModel;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;

public class DBService {

    static Connection connection;

    static Statement statement;



    public DBService() {
        MariaDBConnector mariaDBConnector = new MariaDBConnector();
        connection = mariaDBConnector.getConnection();
        statement = mariaDBConnector.getStatement();
    }

    public Boolean isUserExist(String username) {
        String query = "SELECT username FROM users" + " WHERE  username = '" + username + "'";
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean isUserAuthorized(String username, String password) {
        String query = "SELECT password FROM users" + " WHERE  username = '" + username + "'";
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                String pass = resultSet.getString("password");
                if(pass.equals(password)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String insertNewUser(String username, String password) {
        String query = "INSERT INTO users " + "VALUES ('" + username + "', '" + password + "', '" + new java.sql.Timestamp(new java.util.Date().getTime())+"')";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Account successfully created";
    }

    public Boolean bookTickets(String transactionId, String source, String destination) {
        String query =  "INSERT INTO booking " + "VALUES ('" + transactionId + "', '" + source + "', '" + destination + "')";
        try {
            statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean doTransaction(String transactionId , ArrayList<String> firstNames, ArrayList<String> lastNames,
                               ArrayList<String> ages, ArrayList<String> genders, String creditCardNo, float price) {
        float singlePrice = price / (firstNames.size());
        for(int i=0; i<firstNames.size(); i++) {
            String query = "INSERT INTO transaction " + "VALUES ('" + transactionId + "', '" + firstNames.get(i) + "', '"
                    + lastNames.get(i) + "', '" + ages.get(i) + "', '" + genders.get(i) + "', '" + creditCardNo + "', '" + singlePrice + "')";
            try {
                statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    public BookingModel getBookingModel(String bookingID) {

        String query = "SELECT * FROM booking WHERE booking_id = '" + bookingID + "'";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                String source = resultSet.getString("source");
                String destination = resultSet.getString("destination");
                return new BookingModel(bookingID, source, destination);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public TransactionModel getTransactionModel(String bookingID) {
        String query = "SELECT * FROM transaction WHERE booking_id = '" + bookingID + "'";
        try {
         ResultSet resultSet = statement.executeQuery(query);
         TransactionModel transactionModel = new TransactionModel();
         while(resultSet.next()) {
             transactionModel.setBookingId(bookingID);
             transactionModel.addFirstName(resultSet.getString("first_name"));
             transactionModel.addLastName(resultSet.getString("last_name"));
             transactionModel.addAge(resultSet.getString("age"));
             transactionModel.addGender(resultSet.getString("gender"));
             transactionModel.setCc_no(resultSet.getString("cc_no"));
             transactionModel.setPrice(resultSet.getString("price"));
         }
         return transactionModel;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean isBookingIDValid(String bookingId) {
        String query = "SELECT booking_id FROM booking" + " WHERE  booking_id = '" + bookingId + "'";
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void cancelTicket(String bookingId) {
        String bookingQuery = "DELETE FROM booking WHERE booking_id = '" + bookingId + "'";
        String transactionQuery = "DELETE FROM transaction WHERE booking_id = '" + bookingId + "'";
        try {
            statement.executeQuery(bookingQuery);
            statement.executeQuery(transactionQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

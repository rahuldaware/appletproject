package database;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

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
}

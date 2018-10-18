package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MariaDBConnector {

    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String MARIA_DB_URL = "jdbc:mariadb://localhost:3306";
    static final String DB_NAME = "reservations";

    static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users(username VARCHAR(20), password VARCHAR(20), signup_date DATE, PRIMARY KEY(username))";
    static final String CREATE_TRANSACTION_TABLE = "CREATE TABLE IF NOT EXISTS transaction(booking_id VARCHAR(36)," +
                                            " first_name VARCHAR(20), last_name VARCHAR(20)," +
                                            " age VARCHAR(3), gender char, cc_no VARCHAR(16), price VARCHAR(10), PRIMARY KEY(booking_id, first_name, last_name))";
    static final String CREATE_BOOKING_TABLE = "CREATE TABLE IF NOT EXISTS booking(booking_id VARCHAR(36)," +
                                                " source VARCHAR(20), destination VARCHAR(20))";
    static final String USER = "rdaware";
    static final String PASS = "password";

    Connection connection = null;
    Statement statement = null;

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void createTables() {
        try {
            statement.executeQuery(CREATE_USERS_TABLE);
            statement.executeQuery(CREATE_BOOKING_TABLE);
            statement.executeQuery(CREATE_TRANSACTION_TABLE);
            System.out.println("Users Table verified");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MariaDBConnector() {
        try {
            connection = DriverManager.getConnection(MARIA_DB_URL + "?user=" + USER+"&password=" + PASS);
            statement = connection.createStatement();
            int result = statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            if(result == 0) System.out.println("Database verified");
            connection = DriverManager.getConnection(MARIA_DB_URL +"/" + DB_NAME +"?user=" + USER+"&password=" + PASS);
            System.out.println("Connection to Database successful");
            statement = connection.createStatement();
            createTables();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

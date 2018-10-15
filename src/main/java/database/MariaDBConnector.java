package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MariaDBConnector {

    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://localhost:3306/reservations";

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

    public MariaDBConnector() {
        try {
            connection = DriverManager.getConnection(DB_URL + "?user=" + USER+"&password=" + PASS);
            System.out.println("Connection to DB successful");
            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

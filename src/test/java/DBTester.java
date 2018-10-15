import database.MariaDBConnector;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTester {

    @Test
    public void testDBConnection() {
        MariaDBConnector mariaDBConnector = new MariaDBConnector();
        Statement statement = mariaDBConnector.getStatement();
        String showTables = "SELECT * FROM users";

        try {
            ResultSet resultSet = statement.executeQuery(showTables);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            ResultSet copyResultSet = resultSet;
           for(int i=1;i<= columnsNumber; i++) {
               System.out.print(rsmd.getColumnName(i) + "\t");
           }
           System.out.println();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {

                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + "\t");
                }
                System.out.println("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

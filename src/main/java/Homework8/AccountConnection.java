package Homework8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountConnection {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setConnectionDb() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            connection =
                    DriverManager.getConnection("jdbc:h2:mem:test");
            try {
                Statement statement = connection.createStatement();
                String sql = "SELECT COUNT(*) FROM ACCOUNTS";
                statement.execute(sql);
                statement.close();
            } catch (SQLException e) {
                connection = DriverManager.getConnection("jdbc:h2:mem:test;" +
                        "INIT=RUNSCRIPT FROM './schema.sql'\\" +
                        ";RUNSCRIPT FROM './data.sql'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

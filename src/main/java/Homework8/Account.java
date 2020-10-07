package Homework8;


import java.io.*;
import java.sql.*;

public class Account {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        public Account() throws IOException {
            try {
                Class.forName("org.h2.Driver");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            try {  connection =
                    DriverManager.getConnection("jdbc:h2:mem:test");
                try {
                    statement = connection.createStatement();
                    String sql = "SELECT COUNT(*) FROM ACCOUNTS";
                    statement.execute(sql);
                }
                catch (SQLException e){
                    connection = DriverManager.getConnection("jdbc:h2:mem:test;" +
                            "INIT=RUNSCRIPT FROM './schema.sql'\\" +
                            ";RUNSCRIPT FROM './data.sql'");
//                    System.out.println(connection.isValid(0));
//                    connection.commit();
//                    System.out.println("Ebanuli");
//                    statement = connection.createStatement();
//                    String sql = "CREATE TABLE IF NOT EXISTS ACCOUNTS\n" +
//                            "(\n" +
//                            "id int NOT NULL,\n" +
//                            "holder VARCHAR(50) NOT NULL,\n" +
//                            "amount int NOT NULL\n" +
//                            ");";
//                    String sql1 = "INSERT INTO ACCOUNTS VALUES (11, 'Rodger', 100);";
//                    System.out.println(statement.execute(sql));
//                    System.out.println(statement.execute(sql1));
//                    connection.commit();
//                    String sql2 = "SELECT * FROM ACCOUNTS";
//                    ResultSet rs = statement.executeQuery(sql2);
//                    rs.next();
//                    System.out.println(rs.getObject(1));
                }
              finally {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }




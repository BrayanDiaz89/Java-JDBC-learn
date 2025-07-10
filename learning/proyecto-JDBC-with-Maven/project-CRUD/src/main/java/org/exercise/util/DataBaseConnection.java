package org.exercise.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/project";
    private static final String USERNAME = "root";
    private static final String PWD = "1526aA89!";
    private static Connection connect;

    public DataBaseConnection() {}

    public static Connection getConnectionToDB() throws SQLException {
        if (connect == null) {
            connect = DriverManager.getConnection(URL, USERNAME, PWD);
        }
        return connect;
    }
}

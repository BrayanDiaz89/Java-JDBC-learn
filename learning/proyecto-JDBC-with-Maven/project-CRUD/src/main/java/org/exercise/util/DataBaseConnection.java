package org.exercise.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String URL = "jdbc:mysql://server:#port/project";
    private static final String USERNAME = "";
    private static final String PWD = "";
    private static BasicDataSource pool;

    public DataBaseConnection() {}

    public static BasicDataSource getInstance() throws SQLException {
        if (pool == null) {
           pool = new BasicDataSource();
           pool.setUrl(URL);
           pool.setUsername(USERNAME);
           pool.setPassword(PWD);

           pool.setInitialSize(3);
           pool.setMinIdle(3);
           pool.setMaxIdle(6);
           pool.setMaxTotal(6);
        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }


}

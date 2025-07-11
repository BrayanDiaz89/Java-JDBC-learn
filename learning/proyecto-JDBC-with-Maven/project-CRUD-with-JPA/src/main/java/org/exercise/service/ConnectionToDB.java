package org.exercise.service;

import java.sql.*;

public class ConnectionToDB {
    Connection connect = null;

    public ConnectionToDB(){}

    public Connection connectToDB (String url, String username, String password){
        try {
            connect = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión exitosa.");
            return connect;
        } catch (SQLException ex){
            System.out.println("Falló la conexión con la base de datos. Algo salió mal.");
            throw new RuntimeException(ex);
        }
    }
}

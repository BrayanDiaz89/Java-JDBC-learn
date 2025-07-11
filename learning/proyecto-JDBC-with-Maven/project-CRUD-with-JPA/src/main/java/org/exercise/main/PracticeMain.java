package org.exercise.main;

import org.exercise.service.ConnectionToDB;

import java.sql.SQLException;

public class PracticeMain {
    public static final String URL = "jdbc:mysql://server:#Port/nameBD";
    public static final String USERNAME = "";
    public static final String PWD = "";

    public static void main(String[] args) {
        //Conexión a la base de datos, con clase service
        ConnectionToDB connect = new ConnectionToDB();
        String sqlDelete = """
                DELETE FROM employees
                WHERE id = ?
                """;
        String sqlSelect = "SELECT * FROM employees";
        //Utilización del recurso.
        try (var connection = connect.connectToDB(URL, USERNAME, PWD)) {

            try (var deleteStatement = connection.prepareStatement(sqlDelete)) {
                deleteStatement.setInt(1, 8);
                int rowsAffected = deleteStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Usuario eliminado exitosamente");
                } else {
                    System.out.println("Usuario no existente, nada se eliminó.");
                }
            }

            try (var selectStatement = connection.prepareStatement(sqlSelect);
                 var resultSet = selectStatement.executeQuery()) {

                while (resultSet.next()) {
                    String name = resultSet.getString("first_name");
                    String email = resultSet.getString("email");
                    System.out.println(name + " | " + email);
                }
            }

        } catch (SQLException ex) {
            System.out.println("¡Algo salió mal!");
            throw new RuntimeException(ex);
        }
    }
}

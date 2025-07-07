import java.sql.*;

public class ExampleTwo {

    public static void main(String[] args) {
        Connection myConn = null;
        //Recibir parámetros
        PreparedStatement myStat = null;
        ResultSet resultSet = null;

        try{
            myConn = DriverManager.getConnection("jdbc:mysql://nameServer:#Port/nameBD",
                    "","");
            String intertSQL = """
                    INSERT INTO employees (first_name, pa_surname)
                    VALUES (?, ?)
                    """;
            //Ahora debemos poner PreparedStatement
            myStat = myConn.prepareStatement(intertSQL);
            myStat.setString(1, "Brayan");
            myStat.setString(2, "Díaz");

            int rowsAffected = myStat.executeUpdate();

            if (rowsAffected > 0){
                System.out.println("Se ha creado un nuevo empleado.");
            } else {
                System.out.println("Nada cambió");
            }
        } catch (SQLException e){
            System.out.println("La conexión falló.");
            throw new RuntimeException(e);
        }

    }

}

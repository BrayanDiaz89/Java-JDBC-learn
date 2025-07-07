import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExampleThree {
    public static void main(String[] args) {
        Connection myConn = null;
        PreparedStatement myStatement = null;

        try {
            myConn = DriverManager.getConnection("jdbc:mysql://nameServer:#Port/nameBD",
                    "", "");
            String sqlCode = """
                    UPDATE employees
                    SET ma_surname = ?, email = ?, salary = ?
                    WHERE id = ?  
                    """;
            myStatement = myConn.prepareStatement(sqlCode);
            myStatement.setString(1, "Angarita");
            myStatement.setString(2, "brayan@gmail.co");
            myStatement.setDouble(3, 45333);
            myStatement.setInt(4, 6);
            int rowsAffected = myStatement.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Empleado actualizado");
            }else{
                System.out.println("Nada cambió.");
            }

        }catch (SQLException e){
            System.out.println("La conexión falló.");
            throw new RuntimeException(e.getMessage());
        }


    }
}

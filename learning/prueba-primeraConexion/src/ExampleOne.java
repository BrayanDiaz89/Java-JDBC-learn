import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExampleOne {
    public static void main(String[] args) {
        Connection myConn = null;
        Statement myStamt = null;
        ResultSet resultSet = null;

        try{
            myConn = DriverManager.getConnection("jdbc:mysql://NameServidor:#Puerto/project",
                    "", "");
            System.out.println("Se creó la conexión");

            myStamt = myConn.createStatement();
            resultSet = myStamt.executeQuery("SELECT * FROM employees");

            List<String> namesEmployees = new ArrayList<>();

            while(resultSet.next()){
                String name = resultSet.getString("first_name");
                namesEmployees.add(name);
            }
            System.out.println(namesEmployees);

        } catch (SQLException e){
            System.out.println("La conexión falló.");
            throw new RuntimeException(e);
        }
    }
}
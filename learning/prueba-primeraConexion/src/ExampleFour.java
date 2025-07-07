import java.sql.*;
import java.util.HashMap;

public class ExampleFour {

    public static void main(String[] args) {
        Connection myConn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        HashMap<String, String> employees = new HashMap<>();

        try{
            myConn = DriverManager.getConnection("jdbc:mysql://nameServer:#Port/nameBD",
                    "", "");
            statement = myConn.createStatement();


            int rowsAffected = statement.executeUpdate("""
                                                      UPDATE employees
                                                      SET email = "brayand@gmail.com"
                                                      WHERE id = 6
                                                      """);
            resultSet = statement.executeQuery("SELECT * FROM employees WHERE id = 6");


            while(resultSet.next()){
                System.out.println(resultSet.getString("first_name")+
                                   " | " +
                                   resultSet.getString("email"));

                employees.put(resultSet.getString("first_name"),
                              resultSet.getString("email"));
            }
            System.out.println("HashMap= " + employees);

        }catch (SQLException ex){
            System.out.println("Falló la conexión");
            throw new RuntimeException(ex.getMessage());
        }

    }

}

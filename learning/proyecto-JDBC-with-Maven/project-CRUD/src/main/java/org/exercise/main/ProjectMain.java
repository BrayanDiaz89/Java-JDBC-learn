package org.exercise.main;

import org.exercise.model.Employee;
import org.exercise.repository.EmployeeRepository;
import org.exercise.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class ProjectMain {
    public static void main(String[] args) {

        Connection connection = null;

        try (var keyboard = new Scanner(System.in);){

            connection = DataBaseConnection.getConnectionToDB();

            //Se debe asignar false al commit inicial, para luego pasar los valores.
            if(connection.getAutoCommit()){
                connection.setAutoCommit(false);
            }

            String sql = "SELECT * FROM employees";
            try(var statement = connection.createStatement();
                var resultSet = statement.executeQuery(sql)){

                while (resultSet.next()){
                    System.out.println(resultSet.getString("first_name"));
                }
            }

            EmployeeRepository repository = new EmployeeRepository(connection);
            System.out.println(repository.findAllEmployees());

            System.out.println("Digite el id del empleado: ");
            var idEmployee = keyboard.nextInt();
            var employee = repository.getEmployeeById(idEmployee);
            System.out.println(employee);
            keyboard.nextLine();

            //Creando un empleado:
            System.out.println("Sección de creación de empleado: ");
            System.out.println("Digite el nombre: ");
            String nombre = keyboard.nextLine();
            System.out.println("Digite el primer apellido: ");
            String apellido1 = keyboard.nextLine();
            System.out.println("Digite el segundo apellido: ");
            String apellido2 = keyboard.nextLine();
            System.out.println("Digite el email: ");
            String email  = keyboard.nextLine();
            System.out.println("Digite el salario: ");
            Float salary = keyboard.nextFloat();
            String curp = email + Random.from(new Random(100000));

            Employee employee1 = new Employee(nombre,
                    apellido1, apellido2, email, salary, true, curp);
            repository.saveEmployee(employee1);
            //Se agregan los commits luego de cada transacción
            connection.commit();
            System.out.println(repository.getEmployeeById(employee1.getId()));

            //Eliminación lógica de un empleado
            System.out.print("Digite el id del empleadoa a eliminar: ");
            idEmployee = keyboard.nextInt();
            //Se agregan los commits luego de cada transacción
            connection.commit();
            repository.deleteEmployee(idEmployee);

        }catch (SQLException ex){
            if(connection != null){
                try{
                    connection.rollback();
                    System.out.println("Rollback ejecutado por error en el programa.");
                } catch (SQLException e){
                    e.getMessage();
                }
            }
            throw new RuntimeException("Error conectandose a la base de datos." + ex);
        }
    }
}

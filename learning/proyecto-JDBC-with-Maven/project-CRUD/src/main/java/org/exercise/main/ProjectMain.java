package org.exercise.main;

import org.exercise.model.Employee;
import org.exercise.repository.EmployeeRepository;
import org.exercise.util.DataBaseConnection;

import java.sql.SQLException;
import java.util.Scanner;

public class ProjectMain {
    public static void main(String[] args) {

        try (var connection = DataBaseConnection.getConnection();
             var keyboard = new Scanner(System.in);){
            String sql = "SELECT * FROM employees";
            try(var statement = connection.createStatement();
                var resultSet = statement.executeQuery(sql)){

                while (resultSet.next()){
                    System.out.println(resultSet.getString("first_name"));
                }
            }

            EmployeeRepository repository = new EmployeeRepository();
            repository.findAllEmployees().forEach(System.out::println);

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

            Employee employee1 = new Employee(nombre,
                    apellido1, apellido2, email, salary, true);
            repository.saveEmployee(employee1);
            System.out.println(repository.getEmployeeById(employee1.getId()));

            //Eliminación lógica de un empleado
            System.out.print("Digite el id del empleadoa a eliminar: ");
            idEmployee = keyboard.nextInt();
            repository.deleteEmployee(idEmployee);

        }catch (SQLException ex){
            throw new RuntimeException("Error conectandose a la base de datos." + ex);
        }
    }
}

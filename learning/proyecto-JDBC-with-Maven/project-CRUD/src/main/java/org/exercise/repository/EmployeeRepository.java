package org.exercise.repository;

import org.exercise.model.Employee;
import org.exercise.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Repository<Employee>{

    private static final String SELECT_All_EMPLOYEES = "SELECT * FROM employees WHERE active = true";
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE id = ? ";
    private static final String INSERT_INTO_EMPLOYEES = """
                                                        INSERT INTO employees
                                                        (first_name, pa_surname, ma_surname, email, salary, active, curp)
                                                        VALUES (?, ?, ?, ?, ?, ?, ?)
                                                        """;
    private static final String DELETE_EMPLOYEE_BY_ID = """
                                                        UPDATE employees
                                                        SET active = ?
                                                        WHERE id = ?
                                                        """;

    //Trabajando con Transaction
    private Connection connection;

    public EmployeeRepository(Connection connection) {
        this.connection = connection;
    }
    /*
    Metodo sin transaction - Se reemplaza todos los metodos donde se llama getConnection(), por la variable connection de arriba
    private Connection getConnectionToDB() throws SQLException{
        return DataBaseConnection.getConnectionToDB();
    }*/

    @Override
    public List<Employee> findAllEmployees() throws SQLException {
        try (var statement = connection.createStatement();
             var resultSet = statement.executeQuery(SELECT_All_EMPLOYEES)){
            List<Employee> employees = new ArrayList<>();

            while (resultSet.next()){
                Employee employee = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("pa_surname"),
                        resultSet.getString("ma_surname"),
                        resultSet.getString("email"),
                        resultSet.getFloat("salary"),
                        resultSet.getBoolean("active"),
                        resultSet.getString("curp")
                );
                employees.add(employee);
            }
        return employees;
        }
    }

    @Override
    public Employee getEmployeeById(Integer id) throws SQLException {
        try(var statement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID)) {
            statement.setInt(1, id);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    return new Employee(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("pa_surname"),
                            resultSet.getString("ma_surname"),
                            resultSet.getString("email"),
                            resultSet.getFloat("salary"),
                            resultSet.getBoolean("active"),
                            resultSet.getString("curp"));
                } else{
                    System.out.println("Empleado no existe.");
                    return null;
                }
            }
        }
    }

    @Override
    public void saveEmployee(Employee employee) throws SQLException {
        //Guardar en base de datos.
        try(var statement = connection.prepareStatement(INSERT_INTO_EMPLOYEES, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, employee.getFirst_name());
            statement.setString(2, employee.getPa_surname());
            statement.setString(3, employee.getMa_surname());
            statement.setString(4, employee.getEmail());
            statement.setFloat(5, employee.getSalary());
            statement.setBoolean(6, employee.isActive());
            statement.setString(7, employee.getCurp());
            var rowsAffected = statement.executeUpdate();

            if(rowsAffected > 0){
                try(var resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        int generateId = resultSet.getInt(1);
                        employee.setId(generateId);
                    }
                }
                System.out.println("Usuario creado exitosamente");
                System.out.println("El usuario es: ");
            } else {
                System.out.println("No se pudo crear el usuario.");
            }
        }
    }

    @Override
    public void deleteEmployee(Integer id) throws SQLException {
        try(var statement = connection.prepareStatement(DELETE_EMPLOYEE_BY_ID)){
            statement.setBoolean(1, false);
            statement.setInt(2, id);
            var rowsAffected = statement.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Usuario eliminado con éxito!.");
            } else {
                System.out.println("No fue posible eliminar a ningún usuario. Id no encontrado.");
            }
        }
    }
}

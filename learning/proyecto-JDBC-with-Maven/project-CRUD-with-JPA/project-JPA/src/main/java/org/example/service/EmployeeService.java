package org.example.service;

import jakarta.persistence.EntityManager;
import org.example.entity.Employee;
import org.example.util.UtilEntity;

import java.util.List;
import java.util.Scanner;

public class EmployeeService {

    EntityManager em = UtilEntity.getEntityManager();

    public EmployeeService(){}

    public List<Employee> listarEmpleadosActivos(){
        List<Employee> employees = em.createQuery("SELECT e FROM Employee e WHERE e.active=true", Employee.class).getResultList();
        return employees;
    }

    public void findEmployee(Scanner keyboard){
        System.out.println("Digite el id del empleado: ");
        int employeeId = keyboard.nextInt();
        keyboard.nextLine();
        Employee employee = em.find(Employee.class, employeeId);
        if(employee != null) {
            System.out.println("Empleado encontrado...\n" + employee);
        } else {
            System.out.println("No existe un empleado con el id: " + employeeId);
        }
    }


}

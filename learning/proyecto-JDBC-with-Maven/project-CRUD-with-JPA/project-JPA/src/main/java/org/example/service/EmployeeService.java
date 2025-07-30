package org.example.service;

import jakarta.persistence.EntityManager;
import org.example.enums.TransactionType;
import org.example.entity.Employee;
import org.example.util.UtilEntity;

import java.util.List;
import java.util.Scanner;

public class EmployeeService {

    public EmployeeService() {}

    EntityManager em = UtilEntity.getEntityManager();

    public List<Employee> getEmployeeListIfActiveTrue(){
        return em.createQuery("SELECT e FROM Employee e WHERE e.active=true", Employee.class).getResultList();
    }

    public Employee findEmployeeById(Scanner keyboard){
        System.out.println("Digite el id del empleado: ");
        int employeeId = keyboard.nextInt();
        Employee employee = em.find(Employee.class, employeeId);
        if(employee != null) {
            System.out.println("Empleado encontrado.\n" + employee);
            return employee;
        } else {
            System.out.printf("No existe un empleado con el id: %d.\n", employeeId);
            return null;
        }
    }

    public void createEmployee(Scanner keyboard){
        System.out.println("Digite el nombre del empleado: ");
        String firstName = keyboard.nextLine();
        System.out.println("Digite el apellido paterno del empleado: ");
        String paSurname = keyboard.nextLine();
        System.out.println("Digite el apellido materno del empleado: ");
        String maSurname = keyboard.nextLine();
        System.out.println("Digite el correo del empleado: ");
        String email = keyboard.nextLine();
        System.out.println("Digite el salario del empleado: ");
        Double salary = keyboard.nextDouble();
        Employee newEmployee = new Employee(firstName, paSurname, maSurname, email, salary);

        em.getTransaction().begin();
        em.persist(newEmployee);
        em.getTransaction().commit();

        System.out.println("Nuevo empleado creado...\n"+ newEmployee);
    }

    public void updateEmployeeById(Scanner keyboard){
        var employeeUpdate = findEmployeeById(keyboard);
        if(employeeUpdate != null) {
            int opcionUser = -1;
            do{
                System.out.println("""
                    Escoge una opción:
                    1) Actualizar email.
                    2) Actualizar salario.
                    3) Todo lo anterior.
                    0) Ya hice mis actualizaciones, quiero salir.
                    """);
                opcionUser = keyboard.nextInt();
                keyboard.nextLine();
                switch (opcionUser){
                    case 1:
                        System.out.print("Digite el nuevo email: ");
                        String emailUpdate = keyboard.nextLine();
                        keyboard.nextLine();
                        employeeUpdate.setEmail(emailUpdate);
                        confirmateTransaction(keyboard, employeeUpdate, TransactionType.UPDATE);
                        break;
                    case 2:
                        System.out.print("Digite el nuevo salario: ");
                        Double salaryUpdate = keyboard.nextDouble();
                        keyboard.nextLine();
                        employeeUpdate.setSalary(salaryUpdate);
                        confirmateTransaction(keyboard, employeeUpdate, TransactionType.UPDATE);
                        break;
                    case 3:
                        System.out.print("Digite el nuevo email: ");
                        emailUpdate = keyboard.nextLine();
                        employeeUpdate.setEmail(emailUpdate);

                        System.out.print("Digite el nuevo salario: ");
                        salaryUpdate = keyboard.nextDouble();
                        employeeUpdate.setSalary(salaryUpdate);
                        keyboard.nextLine();
                        confirmateTransaction(keyboard, employeeUpdate, TransactionType.UPDATE);
                        break;
                    case 0:
                        System.out.println("Saliendo del menú de actualización.");
                }

            } while (opcionUser != 0);
        } else {
            System.out.println("No fue posible actualizar al empleado, porque el id ingresado no es válido.");
        }
    }

    public void deleteLogiccallyEmployee(Scanner keyboard){
        var employeeDeleteLogically = findEmployeeById(keyboard);
        if(employeeDeleteLogically != null) {
            confirmateTransaction(keyboard, employeeDeleteLogically, TransactionType.DELETE_LOGIC);
        } else {
            System.out.println("No fue posible eliminar al empleado, porque el id ingresado no es válido.");
        }
    }

    public void deleteEmployeeFromDataBase(Scanner keyboard){
        var employeeDeleteFromDB = findEmployeeById(keyboard);
        if(employeeDeleteFromDB != null) {
            confirmateTransaction(keyboard, employeeDeleteFromDB, TransactionType.DELETE_FROM_DB);
            em.close();
        } else {
            System.out.println("No fue posible eliminar al empleado, porque el id ingresado no es válido.");
        }
    }

    public void confirmateTransaction(Scanner keyboard, Employee employee, TransactionType type){
        String optionUser;
        switch (type){
            case UPDATE:
                System.out.printf("¿Está seguro que desea actualizar estos datos del empleado %s? (Y/y) para confirmar, (N/n) para denegar. ",
                        employee.getFirstName());
                optionUser = keyboard.nextLine();
                if(optionUser.equalsIgnoreCase("y")){
                    em.getTransaction().begin();
                    em.merge(employee);
                    em.getTransaction().commit();
                    System.out.println("Empleado actualizado correctamente.\n"+ employee);
                    break;
                } else{
                    System.out.println("Operación cancelada.\n"+ employee);
                    break;
                }
            case DELETE_LOGIC:
                keyboard.nextLine();
                System.out.printf("¿Está seguro que desea desactivar al empleado %s? (Y/y) para confirmar, (N/n) para denegar. ",
                        employee.getFirstName());
                optionUser = keyboard.nextLine();
                if(optionUser.equalsIgnoreCase("y")){
                    employee.desactivarEmployee();
                    em.getTransaction().begin();
                    em.merge(employee);
                    em.getTransaction().commit();
                    System.out.println("Empleado desactivado correctamente.\n"+ employee);
                    break;
                } else{
                    System.out.println("Operación cancelada.\n"+ employee);
                    break;
                }
            case DELETE_FROM_DB:
                keyboard.nextLine();
                System.out.printf("¿Está seguro que desea eliminar de la base de datos al empleado %s? (Y/y) para confirmar, (N/n) para denegar. ",
                        employee.getFirstName());
                optionUser = keyboard.nextLine();
                if(optionUser.equalsIgnoreCase("y")){
                    em.getTransaction().begin();
                    em.remove(employee);
                    em.getTransaction().commit();
                    System.out.println("Empleado eliminado correctamente.\n"+ employee);
                    break;
                } else{
                    System.out.println("Operación cancelada.\n"+ employee);
                    break;
                }
        }
    }

}

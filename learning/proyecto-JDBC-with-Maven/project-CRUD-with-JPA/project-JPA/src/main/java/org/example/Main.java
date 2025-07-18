package org.example;

import jakarta.persistence.EntityManager;
import org.example.entity.Employee;
import org.example.util.UtilEntity;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        var keyboard = new Scanner(System.in);

        EntityManager em = UtilEntity.getEntityManager();
        List<Employee> employees = em.createQuery("SELECT e FROM Employee e WHERE e.active=true", Employee.class).getResultList();
        System.out.println("-------LISTADO DE EMPLEADOS-------");
        employees.forEach(System.out::println);
        System.out.println("-------BUSCAR A UN EMPLEADO-------");
        System.out.println("Digite el id del empleado: ");
        int employeeId = keyboard.nextInt();
        keyboard.nextLine();
        Employee employee = em.find(Employee.class, employeeId);
        if(employee != null) {
            System.out.println("Empleado encontrado...\n" + employee);
        } else {
            System.out.println("No existe un empleado con el id: " + employeeId);
        }
        System.out.println("-------CREAR UN EMPLEADO-------");
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
        System.out.println("Nuevo empleado creado...\n" + newEmployee);

        System.out.println("-------ACTUALIZAR UN EMPLEADO-------");
        System.out.println("Digite el id del empleado: ");
        employeeId = keyboard.nextInt();
        keyboard.nextLine();
        employee = em.find(Employee.class, employeeId);
        if(employee != null) {
            System.out.printf("Empleado encontrado, nombre: %s.\n", employee.getFirstName());
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
                        employee.setEmail(emailUpdate);
                        em.getTransaction().begin();
                        em.merge(employee);
                        em.getTransaction().commit();
                        System.out.println("Empleado actualizado correctamente.\n"+ employee);
                        break;
                    case 2:
                        System.out.print("Digite el nuevo salario: ");
                        Double salaryUpdate = keyboard.nextDouble();
                        employee.setSalary(salaryUpdate);
                        em.getTransaction().begin();
                        em.merge(employee);
                        em.getTransaction().commit();
                        System.out.println("Empleado actualizado correctamente.\n"+ employee);
                        break;
                    case 3:
                        System.out.print("Digite el nuevo email: ");
                        emailUpdate = keyboard.nextLine();
                        employee.setEmail(emailUpdate);

                        System.out.print("Digite el nuevo salario: ");
                        salaryUpdate = keyboard.nextDouble();
                        employee.setSalary(salaryUpdate);
                        em.getTransaction().begin();
                        em.merge(employee);
                        em.getTransaction().commit();
                        System.out.println("Empleado actualizado correctamente.\n"+ employee);
                        break;
                    case 0:
                        System.out.println("Saliendo del menú de actualización.");
                }

            } while (opcionUser != 0);
        } else {
            System.out.println("No existe un empleado con el id: " + employeeId);
        }

        System.out.println("-------ELIMINAR (DESACTIVAR) UN EMPLEADO-------");
        System.out.println("Digite el id del empleado: ");
        employeeId = keyboard.nextInt();
        keyboard.nextLine();
        employee = em.find(Employee.class, employeeId);
        if(employee != null) {
            System.out.println("Empleado encontrado...");
            System.out.printf("¿Está seguro que desea eliminar a el empleado %s? (Y/y) para confirmar, (N/n) para denegar.", employee.getFirstName());
            String opcionUser = keyboard.nextLine();
            if(opcionUser.equalsIgnoreCase("y")){
                employee.desactivarEmployee();
                em.getTransaction().begin();
                em.merge(employee);
                em.getTransaction().commit();
                System.out.println("Empleado desactivado.\n"+ employee);
            } else{
                System.out.println("Operación cancelada.\n"+ employee);
            }
        } else {
            System.out.printf("No existe un empleado con el id: %d", employeeId);
        }
        System.out.println("-------ELIMINAR UN EMPLEADO FROM BD-------");
        System.out.println("Digite el id del empleado: ");
        employeeId = keyboard.nextInt();
        keyboard.nextLine();
        employee = em.find(Employee.class, employeeId);
        if(employee != null) {
            System.out.println("Empleado encontrado...");
            System.out.printf("¿Está seguro que desea eliminar de la base de datos a el empleado %s? (Y/y) para confirmar, (N/n) para denegar.", employee.getFirstName());
            String opcionUser = keyboard.nextLine();
            if(opcionUser.equalsIgnoreCase("y")){
                em.getTransaction().begin();
                em.remove(employee);
                em.getTransaction().commit();
                em.close();
                System.out.println("Empleado eliminado de la base de datos.\n"+ employee);
            } else{
                System.out.println("Operación cancelada.\n"+ employee);
            }
        } else {
            System.out.printf("No existe un empleado con el id: %d", employeeId);
        }
    }
}
package org.example;

import org.example.service.EmployeeService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        var service = new EmployeeService();
        var keyboard = new Scanner(System.in);

        System.out.println("-------LISTADO DE EMPLEADOS-------");
        service.getEmployeeListIfActiveTrue().forEach(System.out::println);
        System.out.println("-------BUSCAR A UN EMPLEADO-------");
        service.findEmployeeById(keyboard);
        System.out.println("-------CREAR UN EMPLEADO-------");
        service.createEmployee(keyboard);
        System.out.println("-------ACTUALIZAR UN EMPLEADO-------");
        service.updateEmployeeById(keyboard);
        System.out.println("-------ELIMINAR (DESACTIVAR) UN EMPLEADO-------");
        service.deleteLogiccallyEmployee(keyboard);
        System.out.println("-------ELIMINAR UN EMPLEADO FROM BD-------");
        service.deleteEmployeeFromDataBase(keyboard);

    }
}
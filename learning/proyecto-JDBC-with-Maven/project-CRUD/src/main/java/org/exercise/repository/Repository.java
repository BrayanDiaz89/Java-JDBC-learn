package org.exercise.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository <T>{

    List<T> findAllEmployees() throws SQLException;

    T getEmployeeById(Integer id) throws SQLException;

    void saveEmployee(T t) throws SQLException;

    void deleteEmployee(Integer id);

}

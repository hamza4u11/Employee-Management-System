package com.hamza.employeemangementsystem.domain;

import com.hamza.employeemangementsystem.data.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    Employee getEmployeeById(String id);
    List<Employee> getAllEmployees();
    boolean updateEmployee(Employee employee);
    boolean insertEmployee(Employee employee);
    boolean deleteEmployee(String id);

}

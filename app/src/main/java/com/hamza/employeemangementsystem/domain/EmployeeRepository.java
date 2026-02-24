package com.hamza.employeemangementsystem.domain;

import com.hamza.employeemangementsystem.core.ResultCallback;
import com.hamza.employeemangementsystem.data.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    Employee getEmployeeById(String id);
    List<Employee> getAllEmployees(ResultCallback<List<Employee>> callback);
    void getAllEmp(ResultCallback<List<Employee>> callback);

    void updateEmployee(Employee employee);
    void insertEmployee(Employee employee);
    boolean deleteEmployee(String id);

}

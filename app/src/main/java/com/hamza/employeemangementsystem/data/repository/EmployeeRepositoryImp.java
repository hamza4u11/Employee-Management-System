package com.hamza.employeemangementsystem.data.repository;

import android.content.Context;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.domain.EmployeeRepository;
import com.hamza.employeemangementsystem.ui.EmployeeConverter;

import java.util.Collections;
import java.util.List;

public class EmployeeRepositoryImp implements EmployeeRepository {
    DBHandler dbHandler ;
    public EmployeeRepositoryImp(DBHandler dbHandler){

        this.dbHandler= dbHandler;
    }
    @Override
    public Employee getEmployeeById(String id) {
        EmployeeConverter employeeConverter=new EmployeeConverter();
        return (Employee) dbHandler.getRecordById(id, employeeConverter);
    }
    public List<Employee> getEmployeeByManager(String id) {
        EmployeeConverter employeeConverter=new EmployeeConverter();
        String criteria = "magager_id = "+ id;
        return  dbHandler.getRecordByCriteria(criteria, null ,employeeConverter);
    }


    @Override
    public List<Employee> getAllEmployees() {
        EmployeeConverter employeeConverter = new EmployeeConverter();
        return dbHandler.getAllRecords(employeeConverter);
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return false;
    }

    @Override
    public boolean insertEmployee(Employee employee) {
        return false;
    }

    @Override
    public boolean deleteEmployee(String id) {
        return false;
    }
}

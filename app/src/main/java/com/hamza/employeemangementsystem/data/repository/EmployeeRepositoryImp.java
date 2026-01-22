package com.hamza.employeemangementsystem.data.repository;

import android.content.Context;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.domain.EmployeeRepository;
import com.hamza.employeemangementsystem.ui.EmployeeConverter;

import java.time.LocalDateTime;
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
        String criteria = "managerId = "+ id;
        return  dbHandler.getRecordByCriteria(criteria, null ,employeeConverter);
    }


    @Override
    public List<Employee> getAllEmployees() {
        EmployeeConverter employeeConverter = new EmployeeConverter();
        return dbHandler.getAllRecords(employeeConverter);
    }

    public List<Employee> getAllManagers() {
        EmployeeConverter employeeConverter = new EmployeeConverter();
        String value ="manager";
        String criteria = "designation = 'manager'";
        return  dbHandler.getRecordByCriteria(criteria, null ,employeeConverter);    }


    @Override
    public void updateEmployee(Employee employee) {
        EmployeeConverter employeeConverter = new EmployeeConverter();
        dbHandler.updateRecord(employee.id, employee,employeeConverter);
    }

    @Override
    public void insertEmployee(Employee employee) {
        EmployeeConverter employeeConverter = new EmployeeConverter();
        dbHandler.createRecord(employee,employeeConverter);
    }

    @Override
    public boolean deleteEmployee(String id) {
        return false;
    }

}

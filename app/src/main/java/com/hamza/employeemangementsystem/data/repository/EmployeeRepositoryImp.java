package com.hamza.employeemangementsystem.data.repository;

import android.content.Context;

import com.hamza.employeemangementsystem.data.database.local.AppDatabaseHelper;
import com.hamza.employeemangementsystem.data.database.local.SQLiteLocalDataSource;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.domain.EmployeeRepository;
import com.hamza.employeemangementsystem.ui.EmployeeConverter;
import com.hamza.employeemangementsystem.ui.view.MainActivity;

import java.util.List;

public class EmployeeRepositoryImp implements EmployeeRepository {
    AppDatabaseHelper appDatabaseHelper;

    SQLiteLocalDataSource<Employee> sqLiteLocalDataSource = new SQLiteLocalDataSource<>(appDatabaseHelper);
    public EmployeeRepositoryImp(AppDatabaseHelper appDatabaseHelper){

        this.appDatabaseHelper = appDatabaseHelper;
    }
    @Override
    public Employee getEmployeeById(String id) {
        EmployeeConverter employeeConverter=new EmployeeConverter();
//        return (Employee) appDatabaseHelper.getRecordById(id, employeeConverter);
        return (Employee) sqLiteLocalDataSource.getRecordById(id,employeeConverter);
    }
    public List<Employee> getEmployeeByManager(String id) {
        EmployeeConverter employeeConverter=new EmployeeConverter();
        String criteria = "managerId = "+ id;
        return  sqLiteLocalDataSource.getRecordByCriteria(criteria, null ,employeeConverter);
    }


    @Override
    public List<Employee> getAllEmployees() {
        EmployeeConverter employeeConverter = new EmployeeConverter();
        return sqLiteLocalDataSource.getAllRecords(employeeConverter);
    }

    public List<Employee> getAllManagers() {
        EmployeeConverter employeeConverter = new EmployeeConverter();
        String value ="manager";
        String criteria = "designation = 'manager'";
        return  sqLiteLocalDataSource.getRecordByCriteria(criteria, null ,employeeConverter);    }


    @Override
    public void updateEmployee(Employee employee) {
        EmployeeConverter employeeConverter = new EmployeeConverter();
        sqLiteLocalDataSource.updateRecord(String.valueOf(employee.id), employee,employeeConverter);
    }

    @Override
    public void insertEmployee(Employee employee) {
        EmployeeConverter employeeConverter = new EmployeeConverter();
        sqLiteLocalDataSource.insertRecord(employee,employeeConverter);
    }

    @Override
    public boolean deleteEmployee(String id) {
        return false;
    }

}

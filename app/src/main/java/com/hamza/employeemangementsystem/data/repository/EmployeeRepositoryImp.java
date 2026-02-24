package com.hamza.employeemangementsystem.data.repository;

import android.content.Context;

import com.hamza.employeemangementsystem.core.ResultCallback;
import com.hamza.employeemangementsystem.data.database.DbHandler;
import com.hamza.employeemangementsystem.data.database.local.AppDatabaseHelper;
import com.hamza.employeemangementsystem.data.database.local.SQLiteLocalDataSource;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.domain.EmployeeRepository;
import com.hamza.employeemangementsystem.ui.EmployeeConverter;
import com.hamza.employeemangementsystem.ui.view.MainActivity;

import java.util.Collections;
import java.util.List;

public class EmployeeRepositoryImp implements EmployeeRepository {
    AppDatabaseHelper<Employee> appDatabaseHelper;
    SQLiteLocalDataSource<Employee> sqLiteLocalDataSource;
    DbHandler<Employee> dbHandler;

    public EmployeeRepositoryImp(DbHandler<Employee>  dbHandler, Context context){
//      sqLiteLocalDataSource = new SQLiteLocalDataSource<>(appDatabaseHelper,context);
        this.sqLiteLocalDataSource=new SQLiteLocalDataSource<>(appDatabaseHelper,context);
        this.dbHandler =  dbHandler;

//        this.appDatabaseHelper = appDatabaseHelper;
    }
    @Override
    public Employee getEmployeeById(String id) {
        EmployeeConverter employeeConverter=new EmployeeConverter();
//        return (Employee) appDatabaseHelper.getRecordById(id, employeeConverter);
        return (Employee) dbHandler.getRecordById(id);
    }

    @Override
    public List<Employee> getAllEmployees(ResultCallback<List<Employee>> callback) {
         dbHandler.getAllAsync(callback);
         return null;
    }

    @Override
    public void getAllEmp(ResultCallback<List<Employee>> callback) {
         dbHandler.getAllAsync(callback);
    }

//    @Override
//    public List<Employee> getAllEmployees() {
//        // EmployeeConverter employeeConverter = new EmployeeConverter();
//        return dbHandler.getAllAsync(ResultCallback<List<Employee>> callback);
//    }
    public List<Employee> getEmployeeByManager(String id) {
        EmployeeConverter employeeConverter=new EmployeeConverter();
        String criteria = "managerId = "+ id;
        return  dbHandler.getRecordByCriteria("*",criteria, null );
    }



    public List<Employee> getAllManagers() {
        EmployeeConverter employeeConverter = new EmployeeConverter();
        String value ="manager";
        String criteria = "designation = 'manager'";
        return  dbHandler.getRecordByCriteria("*",criteria, null);    }


    @Override
    public void updateEmployee(Employee employee) {
        EmployeeConverter employeeConverter = new EmployeeConverter();
        dbHandler.updateRecord(String.valueOf(employee.id), employee);
    }

    @Override
    public void insertEmployee(Employee employee) {
        EmployeeConverter employeeConverter = new EmployeeConverter();
        //     dbHandler.insertRecord(employee,employeeConverter);
        dbHandler.insertRecord(employee);

    }

    @Override
    public boolean deleteEmployee(String id) {
        return false;
    }

}

package com.hamza.employeemangementsystem.data.repository;

import static com.hamza.employeemangementsystem.core.DataSourceMode.LOCAL_ONLY;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.hamza.employeemangementsystem.core.DataSourceMode;
import com.hamza.employeemangementsystem.core.ResultCallback;
import com.hamza.employeemangementsystem.data.database.DbHandler;
import com.hamza.employeemangementsystem.data.database.local.AppDatabaseHelper;
import com.hamza.employeemangementsystem.data.database.local.SQLiteLocalDataSource;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.domain.EmployeeRepository;
import com.hamza.employeemangementsystem.ui.EmployeeConverter;
import com.hamza.employeemangementsystem.ui.view.MainActivity;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class EmployeeRepositoryImp implements EmployeeRepository {
    AppDatabaseHelper<Employee> appDatabaseHelper;
    SQLiteLocalDataSource<Employee> sqLiteLocalDataSource;
    DbHandler<Employee> dbHandler;

    public EmployeeRepositoryImp(DbHandler<Employee>  dbHandler, Context context){
        this.sqLiteLocalDataSource=new SQLiteLocalDataSource<>(appDatabaseHelper,context);
        this.dbHandler =  dbHandler;
    }
    @Override
    public Employee getEmployeeById(String id) {
        Type type = new TypeToken<List<Employee>>() {}.getType();
        Employee employee = dbHandler.getRecordById(id,type);
        if(employee != null){
            return employee;
        }
        return null;
    }

//    @Override
//    public List<Employee> getAllEmployees(ResultCallback<List<Employee>> callback) {
//         dbHandler.getAllAsync(callback);
//         return null;
//    }

    public void getAllEmp(ResultCallback<List<Employee>> callback,Type type) {
         dbHandler.getAllAsync(callback,type);
    }

    public List<Employee> getAllEmployees() {
        // EmployeeConverter employeeConverter = new EmployeeConverter();
        Type type = new TypeToken<List<Employee>>() {}.getType();

        return dbHandler.getAllRecords(type);
    }
    public List<Employee> getEmployeeByManager(String id) {
        EmployeeConverter employeeConverter=new EmployeeConverter();
        String criteria = "managerId = "+ id;
        return  dbHandler.getRecordByCriteria("*",criteria, null ,null);
    }
    @Override
    public List<Employee> getAllManagers() {
        EmployeeConverter employeeConverter = new EmployeeConverter();
        Type type = new TypeToken<List<Employee>>() {}.getType();

        if(dbHandler.getMode()==LOCAL_ONLY){
            String value = "manager";
            String criteria = "designation='manager'";
            List<Employee> getManagersFromLocal = dbHandler.getRecordByCriteria("*", criteria, null, null);
            return getManagersFromLocal;
        }else {

            List<Employee> managers = dbHandler.getRecordByCriteria(null,null,null,type);
           return managers;
        }
    }
    @Override
    public void updateEmployee(Employee employee) {
        Log.d("UPDATE EMPLOYEE", "Id: " + employee.id);
        Log.d("UPDATE EMPLOYEE", "Name: " + employee.name);
        Log.d("UPDATE EMPLOYEE", "Designation: " + employee.designation);
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

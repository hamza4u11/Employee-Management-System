package com.hamza.employeemangementsystem.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp;
//import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp_old;

import java.util.List;

public class EmployeeViewModel extends ViewModel {
    private final MutableLiveData<List<Employee>> employees = new MutableLiveData<>();
    private final MutableLiveData<List<Employee>> filteredEmployees = new MutableLiveData<>();



    private EmployeeRepositoryImp repository;

    public EmployeeViewModel(@NonNull DBHandler dbHandler) {
        super();
//        repository = new EmployeeRepositoryImp_old(application);
        repository = new EmployeeRepositoryImp(dbHandler);

        loadEmployees();
    }
    private void loadEmployees() {

        employees.setValue(repository.getAllEmployees());
    }
    public Employee getEmployeeById(String id){
        return repository.getEmployeeById(id);

    }

    public LiveData<List<Employee>> getAllEmployees() {
          return employees;
    }
    public LiveData<List<Employee>> getFilteredEmployees(){
        return filteredEmployees;
    }
    public void getEmployeesByManager(String managerIdParam) {
        filteredEmployees.setValue( repository.getEmployeeByManager(managerIdParam));

    }
}

package com.hamza.employeemangementsystem.ui.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hamza.employeemangementsystem.data.database.local.AppDatabaseHelper;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp;
import com.hamza.employeemangementsystem.ui.view.MainActivity;
//import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp_old;

import java.util.List;

public class SelectProfileViewModel extends ViewModel {
    private final MutableLiveData<List<Employee>> employees = new MutableLiveData<>();
    private final MutableLiveData<List<Employee>> filteredEmployees = new MutableLiveData<>();
    private final MutableLiveData<List<Employee>> managers = new MutableLiveData<>();
    private EmployeeRepositoryImp repository;

    public SelectProfileViewModel(@NonNull AppDatabaseHelper appDatabaseHelper, Context context) {
        super();
        AppDatabaseHelper<Employee> appDatabaseHelper1 = new AppDatabaseHelper<>(context);
        repository = new EmployeeRepositoryImp(appDatabaseHelper);

        loadEmployees();
       // getAllManagers();
    }
    public LiveData<List<Employee>> getAllEmployees() {
        return employees;
    }
    private void loadEmployees() {

        employees.setValue(repository.getAllEmployees());
    }

    public Employee getEmployeeById(String id){
        return repository.getEmployeeById(id);

    }
    public LiveData<List<Employee>> getFilteredEmployees(){
        return filteredEmployees;
    }
    public void getEmployeesByManager(String managerIdParam) {
        filteredEmployees.setValue( repository.getEmployeeByManager(managerIdParam));
    }
    public void saveEmployee(Employee emp) {
        repository.insertEmployee(emp);
    }
    public void updateEmployee(Employee emp) {
        repository.updateEmployee(emp);
    }
}

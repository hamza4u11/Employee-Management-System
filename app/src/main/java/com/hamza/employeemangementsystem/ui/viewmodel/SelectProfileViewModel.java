package com.hamza.employeemangementsystem.ui.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hamza.employeemangementsystem.core.ResultCallback;
import com.hamza.employeemangementsystem.data.database.local.AppDatabaseHelper;
import com.hamza.employeemangementsystem.data.database.local.SQLiteLocalDataSource;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp;
import com.hamza.employeemangementsystem.ui.view.MainActivity;
//import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp_old;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SelectProfileViewModel extends ViewModel {
    private final MutableLiveData<List<Employee>> employees = new MutableLiveData<>();
    private final MutableLiveData<List<Employee>> filteredEmployees = new MutableLiveData<>();
    private final MutableLiveData<List<Employee>> managers = new MutableLiveData<>();
    private EmployeeRepositoryImp repository;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    public SelectProfileViewModel(@NonNull EmployeeRepositoryImp repository, Context context) {
        super();
        this.repository = repository;
        loadEmployees();
    }
    public LiveData<List<Employee>> getAllEmployees() {
        return employees;
    }
    private void loadEmployees() {
        executorService.execute(() -> {
            // ✅ This runs in background thread
            List<Employee> result =
                    repository.getAllEmployees(
                    );
            // ✅ Update LiveData from background thread
            employees.postValue(result);
        });
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

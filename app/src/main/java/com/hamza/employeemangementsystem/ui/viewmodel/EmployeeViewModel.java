package com.hamza.employeemangementsystem.ui.viewmodel;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hamza.employeemangementsystem.data.model.EmployeeModel;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepository;

import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {
    private MutableLiveData<List<EmployeeModel>> employees = new MutableLiveData<>();
//    private final MutableLiveData<EmployeeModel> loginState = new MutableLiveData<>();



    private EmployeeRepository repository;

    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        repository = new EmployeeRepository(application);
        loadEmployees();
    }
    private void loadEmployees() {

        employees.setValue(repository.getAllEmployees());
    }
//    public LiveData<EmployeeModel> getEmployeeById(int id) {
//        return repository.getEmployeeById(id);
//    }
public List<EmployeeModel> getEmployeeById(int id) {
    return repository.getEmployeeById(id);
}
    public String login(int id, String pin){
        Log.d("Id", String.valueOf(id));
        Log.d("Pin",  pin );
       return repository.login(id,pin);
    }

//    public void isPinCorrect(int employeeId, String pin) {
//
//        String role = repository.checkEmployeePin(employeeId, pin);
//
//        if ("admin".equalsIgnoreCase(role)) {
//            Log.d("Role", "ADMIN");
//        } else if ("manager".equalsIgnoreCase(role)) {
//            Log.d("Role", "MANAGER");
//        } else {
//            Log.d("Role", "EMPLOYEE");
//        }
//    }
//    public boolean isPinCorrect(int employeeId, String enteredPin) {
//        Log.d("View", "MOdel");
//        return repository.checkEmployeePin(employeeId, enteredPin);
//    }



    //    public void insertEmployee(EmployeeModel model){
//        repository.insertEmployee(model);
//    }
//    public void delete(int id){
//        repository.deleteEmployee(id);
//    }
        public LiveData<List<EmployeeModel>> getAllEmployees() {
          return employees;
}
//    public void getEmployeeById(int id){
//        Cursor cursor = repository.getEmployeeById(id);
//    }
//    public void updateEmployee(EmployeeModel model){
//        repository.updateEmployee(model);
//    }
}

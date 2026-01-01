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
//    private final MutableLiveData<EmployeeModel> loginState = new MutableLiveData<>();



    private EmployeeRepositoryImp repository;
   // private EmployeeRepositoryImp repositoryImp;

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
//    public LiveData<EmployeeModel> getEmployeeById(int id) {
//        return repository.getEmployeeById(id);
//    }
//public List<Employee> getEmployeeById(int id) {
//    return repository.getEmployeeById(id);
//}
//    public String login(int id, String pin){
////        Log.d("Id", String.valueOf(id));
////        Log.d("Pin",  pin );
//       return repository.getEmployeeById(id);
//    }

//    public void getEmployeeByIdEx(int id){
//      repository.getEmployeeByIdEx(id);
//
//    }

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
        public LiveData<List<Employee>> getAllEmployees() {
          return employees;
}
//    public void getEmployeeById(int id){
//        Cursor cursor = repository.getEmployeeById(id);
//    }
//    public void updateEmployee(EmployeeModel model){
//        repository.updateEmployee(model);
//    }
}

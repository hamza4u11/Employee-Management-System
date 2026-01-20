package com.hamza.employeemangementsystem.ui.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp;
//import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp_old;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

import javax.xml.namespace.QName;

public class EmployeeViewModel extends ViewModel {
    private final MutableLiveData<List<Employee>> employees = new MutableLiveData<>();
    private final MutableLiveData<List<Employee>> filteredEmployees = new MutableLiveData<>();
    private final MutableLiveData<List<Employee>> managers = new MutableLiveData<>();




    private EmployeeRepositoryImp repository;

    public EmployeeViewModel(@NonNull DBHandler dbHandler) {
        super();
        repository = new EmployeeRepositoryImp(dbHandler);

        loadEmployees();
        getAllManagers();
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

//    private void loadManagers() {
//
//        managers.setValue(repository.getAllManagers());
//    }

//    public LiveData<List<Employee>> getAllManagers(){
//        return managers;
//    }
    public LiveData<List<Employee>> getManagers(){

        return managers;
    }
    public void getAllManagers() {

        managers.setValue( repository.getAllManagers());

    }

    public LiveData<List<Employee>> getFilteredEmployees(){

        return filteredEmployees;
    }
    public void getEmployeesByManager(String managerIdParam) {

        filteredEmployees.setValue( repository.getEmployeeByManager(managerIdParam));

    }

    public void saveEmployee(Employee emp) {
        if (emp != null) {
            Log.d("ATTENDANCE", String.valueOf(emp));
        } else {
            Log.d("ATTENDANCE", "attendance is NULL");
        }
        repository.insertEmployee(emp);
    }

    public void updateEmployee(Employee emp) {
        repository.updateEmployee(emp);
    }


//    public Boolean saveBtn(String saveParam) {
//         Boolean isSaveBtn= Objects.equals(saveParam, "add");
//         return isSaveBtn;
//
//    }


}

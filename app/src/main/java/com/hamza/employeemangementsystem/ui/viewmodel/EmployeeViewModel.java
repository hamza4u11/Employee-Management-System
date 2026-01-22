package com.hamza.employeemangementsystem.ui.viewmodel;


import android.os.Build;
import android.util.Log;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp;

import java.time.LocalDateTime;
import java.util.List;

public class EmployeeViewModel extends ViewModel {
    EmployeeRepositoryImp repository;
    private final MutableLiveData<List<Employee>> managers = new MutableLiveData<>();
    String empId;
    Employee employee;
    public EmployeeViewModel(@NonNull DBHandler dbHandler,String employeeId){
        super();
        repository = new EmployeeRepositoryImp(dbHandler);
        getAllManagers();

        if(employeeId!= null) {
            empId=employeeId;
            employee = repository.getEmployeeById(employeeId);
        }else{
            employee = new Employee();
        }
    }
    public int getId() {
        return employee.id;
    }

    public void setId(String id) {
        employee.name = id;
    }
    public String getName() {
        return employee.name;
    }

    public void setName(String name) {
        employee.name = name;
    }

    public String getDesignation() {
        return employee.designation;
    }

    public void setDesignation(String designation) {
        employee.designation = designation;
    }

    public String getPhoneNo() {
        return employee.phone_no;
    }

    public void setPhoneNo(String phoneNo) {
        employee.phone_no = phoneNo;
    }

    public String getAddress() {
        return employee.address;
    }

    public void setAddress(String address) {
        employee.address = address;
    }

    public String getStatus() {
        return employee.status;
    }

    public void setStatus(String status) {
        employee.status = status;
    }

    public String getPaymentType() {
        return employee.paymentType;
    }

    public void setPaymentType(String paymentType) {
        employee.paymentType = paymentType;
    }

    public String getAllowHoliday() {
        return employee.allowHoliday;
    }

    public void setAllowHoliday(String allowHoliday) {
        employee.allowHoliday = allowHoliday;
    }

    public String getOverTimeAllow() {
        return employee.overTimeAllow;
    }

    public void setAllowOverTime(String overTimeAllow) {
        employee.overTimeAllow = overTimeAllow;
    }

    public String getPin() {
        return employee.pin;
    }

    public void setPin(String pin) {
        employee.pin = pin;
    }
    public String getCheckIn() {
        return employee.checkIn;
    }

    public void setCheckIn(String checkIn) {
        employee.checkIn = checkIn;
    }

    public String getManagerId(){
       return employee.managerId;
    }
    public void setManagerId(String managerId){
        employee.managerId = managerId;
    }

    public LiveData<List<Employee>> getManagers(){

        return managers;
    }
    public void getAllManagers() {
        managers.setValue( repository.getAllManagers());
    }
    public void updateEmployee(){
          Employee employee1 = employee;
            if(empId == null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setCheckIn(String.valueOf(LocalDateTime.now()));
                }
                repository.insertEmployee(employee);

            }else {
                repository.updateEmployee(employee);
            }

    }


}

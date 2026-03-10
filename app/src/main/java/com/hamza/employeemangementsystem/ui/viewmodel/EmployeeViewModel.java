package com.hamza.employeemangementsystem.ui.viewmodel;


import android.content.Context;
import android.os.Build;
import android.telephony.ClosedSubscriberGroupInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hamza.employeemangementsystem.core.ResultCallback;
import com.hamza.employeemangementsystem.data.database.local.AppDatabaseHelper;
import com.hamza.employeemangementsystem.data.database.local.SQLiteLocalDataSource;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp;
import com.hamza.employeemangementsystem.domain.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EmployeeViewModel extends ViewModel {
    EmployeeRepository repository;
    private final MutableLiveData<List<Employee>> managers = new MutableLiveData<>();
    private MutableLiveData<String> result = new MutableLiveData<>();
    private MutableLiveData<Employee> employeeLiveData = new MutableLiveData<>();
    String empId;
    Employee employee;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    public EmployeeViewModel(EmployeeRepository employeeRepository, String employeeId){
        super();
        repository = employeeRepository;
        getAllManagers();
        if(employeeId!= null) {
            empId=employeeId;
            employee=loadEmployee();
        }else{
            employee = new Employee();
        }
    }
    public LiveData<Employee> getEmployee() {
        return employeeLiveData;
    }
    public Employee loadEmployee() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Employee> future = executor.submit(() -> {
            return repository.getEmployeeById(empId);
        });
        try {
            return future.get(); // waits until API finishes
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getId() {
        return employee.id;
    }
    public void setId(String id) {
        employee.id = Integer.parseInt(id) ;
    }
    public String getName() {
        Log.d("EMPLOYEE", "Employee is " + employee);
        return employee.name;
    }
    public void setName(String name) throws Exception {
        if(name.isEmpty()){
            throw (new Exception("Name cannot be empty"));
        }
        if(name.length() < 3){
            throw (new Exception("Name cannot be less than 3 char"));
        }
        employee.name = name;
    }
    public String getDesignation() {
        return employee.designation;
    }
    public void setDesignation(String designation) throws Exception {
        if(designation.isEmpty()){
            throw  (new Exception("Designation cannot be empty"));
        }
        employee.designation = designation;
    }

    public String getPhoneNo() {
        return employee.phone_no;
    }

    public void setPhoneNo(String phoneNo) throws Exception {
        if(phoneNo.isEmpty()){
            throw (new Exception("Phone Number cannot be empty"));
        }
        employee.phone_no = phoneNo;
    }

    public String getAddress() {
        return employee.address;
    }

    public void setAddress(String address) throws Exception {
        if(address.isEmpty()){
            throw (new Exception("Address cannot be empty"));
        }

        employee.address = address;
    }

    public String getStatus() {
        return employee.status;
    }

    public void setStatus(String status) throws Exception {
        if(status.isEmpty()){
            throw (new Exception("Status cannot be empty"));
        }
        employee.status = status;
    }

    public String getPaymentType() {
        return employee.paymentType;
    }

    public void setPaymentType(String paymentType) throws Exception {
        if(paymentType.isEmpty()){
            throw (new Exception("Payment Type cannot be empty"));
        }
        employee.paymentType = paymentType;
    }

    public String getAllowHoliday() {
        return employee.allowHoliday == null? "": employee.allowHoliday;
    }

    public void setAllowHoliday(String allowHoliday) {

        employee.allowHoliday = allowHoliday;
    }

    public String getOverTimeAllow() {
        return employee.overTimeAllow == null? "": employee.overTimeAllow;
    }

    public void setAllowOverTime(String overTimeAllow) {

        employee.overTimeAllow = overTimeAllow;
    }

    public String getPin() {
        return employee.pin;
    }

    public void setPin(String pin) throws Exception {
        if(pin.isEmpty()){
            throw (new Exception("Pin cannot be empty"));
        }
        if(pin.length() < 4){
            throw (new Exception("Pin cannot be less than 4 digits"));
        }
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

        executorService.execute(() -> {

            // ✅ This runs in background thread
            List<Employee> result =
                    repository.getAllManagers(
                    );
            // ✅ Update LiveData from background thread
            managers.postValue(result);

        });

       // managers.setValue(repository.getAllManagers());
    }
    public LiveData<String> getResult() {
        return result;
    }
    public void updateEmployee() {
        executorService.execute(() -> {
            Employee employee1 = employee;
            if (empId == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setCheckIn(String.valueOf(LocalDateTime.now()));
                }
                repository.insertEmployee(employee1);
            } else {
                repository.updateEmployee(employee1);
            }
        });
    }

}
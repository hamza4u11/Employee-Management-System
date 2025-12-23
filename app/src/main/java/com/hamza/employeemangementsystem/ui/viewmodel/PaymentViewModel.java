package com.hamza.employeemangementsystem.ui.viewmodel;

import android.app.Application;
import android.content.ContentUris;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.hamza.employeemangementsystem.data.model.EmployeeModel;
import com.hamza.employeemangementsystem.data.model.PaymentModel;
import com.hamza.employeemangementsystem.data.repository.PaymentRepository;

public class PaymentViewModel extends AndroidViewModel {
     private PaymentRepository repository;


    public PaymentViewModel(@NonNull Application application) {
        super(application);
        repository = new PaymentRepository(application);
    }
    public void insertPayment(PaymentModel model){
        repository.insertPayment(model);
    }
    public  void delete(int id){
        repository.delete(id);
    }
    public void getPayments(){
        Cursor cursor = repository.getAllPayments();
    }
    public void update(PaymentModel model){
        repository.updatePayment(model);
    }
}

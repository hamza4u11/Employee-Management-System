package com.hamza.employeemangementsystem.ui.viewmodel;

import android.app.Application;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.hamza.employeemangementsystem.data.model.AttendenceModel;
import com.hamza.employeemangementsystem.data.repository.AttendenceRepository;

public class AttendenceViewModel extends AndroidViewModel {
    private AttendenceRepository repository;
//    private MutableLiveData<Cursor> reportsLiveData = new MutableLiveData<>();

    public AttendenceViewModel(@NonNull Application application) {
        super(application);
        repository = new AttendenceRepository(application);
    }
    public void insertReport(AttendenceModel model){
        repository.insertReport(model);
    }
    public  void deleteReport(int id){
        repository.delete(id);
    }
    public void allReports(){
        Cursor cursor = repository.getAllReports();
    }
}

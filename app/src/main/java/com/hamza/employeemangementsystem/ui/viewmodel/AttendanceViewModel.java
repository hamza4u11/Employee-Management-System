package com.hamza.employeemangementsystem.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.repository.AttendanceRepository;

import java.util.List;

public class AttendanceViewModel extends ViewModel {
private  AttendanceRepository repository;
    public AttendanceViewModel(@NonNull DBHandler dbHandler) {
        super();
        repository = new AttendanceRepository(dbHandler);
    }
           public int isCheckedIn(String id ){
             List record= repository.getLastAttendance(id);
                    int count= record.size();
                Log.d("Record",String.valueOf(count));
               return count;

            }


    }

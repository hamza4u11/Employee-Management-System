package com.hamza.employeemangementsystem.ui.viewmodel;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.repository.AttendanceRepository;

import java.time.LocalDateTime;
import java.util.List;

public class AttendanceViewModel extends ViewModel {
private  AttendanceRepository repository;
    public AttendanceViewModel(@NonNull DBHandler dbHandler) {
        super();
        repository = new AttendanceRepository(dbHandler);
    }
    public List<Attendance> isCheckedUser(String id ) {
        List<Attendance> record = repository.getLastAttendance(id);
        return record;
    }
    public List<Attendance> isCheckedIn(String id ){
        List<Attendance> record= repository.getLastAttendance(id);
        String isCheckInTime= record.get(0).checkInTime;
        String isCheckOutTime= record.get(0).checkOutTime;

        if (isCheckInTime != null && isCheckOutTime != null) {
        Attendance attendance = new Attendance();
        attendance.empId= Integer.parseInt(id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            attendance.checkInTime= LocalDateTime.now().toString();
        }
//        attendance.overTime=100;
            repository.insertAttendance(attendance);
            
        } else if (isCheckInTime != null && isCheckOutTime == null) {
            Attendance attendance = new Attendance();
                attendance.checkInTime= isCheckInTime;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                attendance.checkOutTime= LocalDateTime.now().toString();
            }
//        attendance.overTime=100;
            repository.updateAttendance(Integer.parseInt(id),attendance);
            Log.d("Update Function Id goes",id );



        }


        return record;
            }


    }

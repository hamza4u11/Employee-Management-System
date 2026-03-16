package com.hamza.employeemangementsystem.domain;

import android.os.BatteryManager;

import com.hamza.employeemangementsystem.data.model.Attendance;

import java.util.List;

public interface AttendanceRepository {
    Attendance getLastAttendance(String empId);
    void updateAttendance(Attendance attendance);
    void insertAttendance(Attendance attendance);
    List<Attendance> getAttendanceByCriteria(String startDate,String endDate,String employeeId, String loginId);
}

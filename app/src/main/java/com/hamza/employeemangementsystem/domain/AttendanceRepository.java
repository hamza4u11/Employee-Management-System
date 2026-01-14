package com.hamza.employeemangementsystem.domain;

import com.hamza.employeemangementsystem.data.model.Attendance;

import java.util.List;

public interface AttendanceRepository {
    Attendance getLastAttendance(String empId);
    boolean updateAttendance(Attendance attendance);
    boolean insertAttendance(Attendance attendance);
}

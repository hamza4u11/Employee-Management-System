package com.hamza.employeemangementsystem.domain;

import com.hamza.employeemangementsystem.data.model.Attendance;

import java.util.List;

public interface AttendanceRepository {
    Attendance getAttendanceByEmpId(String id);
    List<Attendance> getAllAttendances();
    boolean updateAttendance(Attendance attendance);
    boolean insertAttendance(Attendance attendance);
    boolean deleteAttendance(String id);
}

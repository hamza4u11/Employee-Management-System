package com.hamza.employeemangementsystem.data.repository;

import android.net.DnsResolver;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.ui.AttendanceConverter;

import java.util.List;

public class AttendanceRepository {
        DBHandler dbHandler;


    public AttendanceRepository(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public List<Attendance> getAllAttendance(){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        return dbHandler.getAllRecords(attendanceConverter);
    }
    public Attendance getAttendanceByEmpId(String id ){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        return  (Attendance) dbHandler.getRecordById(id ,attendanceConverter);
    }
    public List<Attendance> getLastAttendance(String id){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        String criteria = " empId = "+ id +" ORDER BY checkInTime DESC LIMIT 1";
        return dbHandler.getRecordByCriteria(criteria, attendanceConverter);
    }
//    public Attendance getAttendanceByCriteria(String id){
//        AttendanceConverter attendanceConverter = new AttendanceConverter();
//        String criteria = " empId = "+ id +" AND checkIntime NOT NULL AND checkOutTime IS NULL ORDER BY checkInTime DESC LIMIT 1";
//        return (Attendance) dbHandler.getRecordByCriteria(criteria, attendanceConverter);
//    }
    public  void insertAttendance(Attendance attendance){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
         dbHandler.createRecord(attendance,attendanceConverter);

    }
    public void updateAttendance(int id, Attendance attendance){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        dbHandler.updateRecord(id, attendance, attendanceConverter);



    }

}

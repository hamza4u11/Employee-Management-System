package com.hamza.employeemangementsystem.data.repository;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.domain.AttendanceRepository;
import com.hamza.employeemangementsystem.ui.AttendanceConverter;

import java.util.List;

public class AttendanceRepositoryImp implements AttendanceRepository {
        DBHandler dbHandler;


    public AttendanceRepositoryImp(DBHandler dbHandler) {
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
    @Override
    public Attendance getLastAttendance(String empId){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
      String criteria = " empId = "+ empId +" ";
      String orderBy = " checkInTime DESC LIMIT 1";
      List<Attendance> attendanceList= dbHandler.getRecordByCriteria(criteria,orderBy, attendanceConverter);
      if (attendanceList!= null && attendanceList.size()==1){
          return attendanceList.get(0);
      }
      return null;
    }

    @Override
    public boolean updateAttendance(Attendance attendance) {
        AttendanceConverter attendanceConverter = new AttendanceConverter();

        dbHandler.updateRecord(attendance.id, attendance, attendanceConverter);
        return true;
    }
    @Override
    public boolean insertAttendance(Attendance attendance) {
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        dbHandler.createRecord(attendance,attendanceConverter);
        return true;
    }

    //    public Attendance getAttendanceByCriteria(String id){
//        AttendanceConverter attendanceConverter = new AttendanceConverter();
//        String criteria = " empId = "+ id +" AND checkIntime NOT NULL AND checkOutTime IS NULL ORDER BY checkInTime DESC LIMIT 1";
//        return (Attendance) dbHandler.getRecordByCriteria(criteria, attendanceConverter);
//    }
//    public  void insertAttendance(Attendance attendance){
//        AttendanceConverter attendanceConverter = new AttendanceConverter();
//         dbHandler.createRecord(attendance,attendanceConverter);
//
//    }
//    @Override
//    public void updateAttendance(int id, Attendance attendance){
//        AttendanceConverter attendanceConverter = new AttendanceConverter();
//        dbHandler.updateRecord(id, attendance, attendanceConverter);
//
//
//
//    }

}

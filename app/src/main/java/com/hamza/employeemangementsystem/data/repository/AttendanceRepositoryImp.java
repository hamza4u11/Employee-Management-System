package com.hamza.employeemangementsystem.data.repository;

import android.util.Log;

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

        public List<Attendance> getAttendanceByCriteria(String startDate, String endDate, String employeeId, String loginId){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        String selectClause = "* , (Select name from employees where id = attendance.empId ) as \"name\", (Select status from employees where id = attendance.empId ) as \"status\"";
        String criteria = " empId = "+ employeeId +" AND date BETWEEN '"+ startDate + "' AND '" + endDate +"'";
//        int reportSize=  dbHandler.getRecordByCriteria(criteria,null, attendanceConverter).size();
//            Log.d("ReportSize Repo",  String.valueOf(reportSize));
        return  dbHandler.getRecordByCriteria( selectClause,criteria,null, attendanceConverter);
    }


}

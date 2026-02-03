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

        public List<Attendance> getAttendanceByCriteria(String startDate, String endDate, String employeeId, String loginId){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        String criteria = " empId = "+ employeeId +" AND date BETWEEN '"+ startDate + "' AND '" + endDate +"'";
//            SELECT *
//                    FROM attendance
//            WHERE empId = '2' AND date  BETWEEN '2026-01-20' AND '2026-01-29'
        return  dbHandler.getRecordByCriteria(criteria,null, attendanceConverter);
    }


}

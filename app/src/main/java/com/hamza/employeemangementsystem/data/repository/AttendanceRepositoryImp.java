package com.hamza.employeemangementsystem.data.repository;

import android.util.Log;

import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.local.AppDatabaseHelper;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.domain.AttendanceRepository;
import com.hamza.employeemangementsystem.domain.EmployeeRepository;
import com.hamza.employeemangementsystem.ui.AttendanceConverter;

import java.util.List;

public class AttendanceRepositoryImp implements AttendanceRepository {
        AppDatabaseHelper appDatabaseHelper;


    public AttendanceRepositoryImp(AppDatabaseHelper appDatabaseHelper) {

        this.appDatabaseHelper = appDatabaseHelper;
    }

    public List<Attendance> getAllAttendance(){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        return appDatabaseHelper.getAllRecords(attendanceConverter);
    }
    public Attendance getAttendanceByEmpId(String id ){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        return  (Attendance) appDatabaseHelper.getRecordById(id ,attendanceConverter);
    }
    @Override
    public Attendance getLastAttendance(String empId){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
      String criteria = " empId = "+ empId +" ";
      String orderBy = " checkInTime DESC LIMIT 1";
      List<Attendance> attendanceList= appDatabaseHelper.getRecordByCriteria(criteria,orderBy, attendanceConverter);
      if (attendanceList!= null && attendanceList.size()==1){
          return attendanceList.get(0);
      }
      return null;
    }

    @Override
    public boolean updateAttendance(Attendance attendance) {
        AttendanceConverter attendanceConverter = new AttendanceConverter();

        appDatabaseHelper.updateRecord(attendance.id, attendance, attendanceConverter);
        return true;
    }
    @Override
    public boolean insertAttendance(Attendance attendance) {
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        appDatabaseHelper.createRecord(attendance,attendanceConverter);
        return true;
    }
    public List<Attendance> getAttendanceByCriteria(String startDate, String endDate, String employeeId, String loginId){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        String selectClause = null, criteria = null, orderBy = null;
        EmployeeRepository employeeRepository = new EmployeeRepositoryImp(appDatabaseHelper);
        Employee employee=employeeRepository.getEmployeeById(loginId);
         Globals.getShared().setEmployee(employee);
         Log.d("Employee Name", " "+ Globals.getShared().getEmployee().name );
        selectClause = "* , (SELECT e.name FROM employees e WHERE e.id = attendance.empId) AS name ,(SELECT e.status FROM employees e WHERE e.id = attendance.empId ) AS status ";
        String dateCriteria = " date BETWEEN '" + startDate + "' AND '" + endDate +"'ORDER BY date DESC";
        String employeeFilterCriteria = (employeeId.isEmpty()?"":" empId ="+employeeId);
        String employeeCriteria="";
        if(employee.designation.equals("admin")){
            employeeCriteria = employeeFilterCriteria;
        }else if(employee.designation.equals("manager")){
            employeeCriteria = " empId IN(Select id from employees where managerId = "+ loginId+ ")" + (!employeeFilterCriteria.isEmpty()?" AND "+employeeFilterCriteria:"")  ;
        }else{
            employeeCriteria = " empId = " + loginId + " ";
        }
        criteria = employeeCriteria + (!employeeCriteria.isEmpty()?" AND ":"") + dateCriteria;
      return  appDatabaseHelper.getRecordByCriteria( selectClause,criteria,orderBy, attendanceConverter);
    }



}

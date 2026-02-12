package com.hamza.employeemangementsystem.data.repository;

import android.opengl.EGLObjectHandle;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;

import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.domain.AttendanceRepository;
import com.hamza.employeemangementsystem.domain.EmployeeRepository;
import com.hamza.employeemangementsystem.ui.AttendanceConverter;

import java.util.List;
import java.util.Objects;

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

      /*  public List<Attendance> getAttendanceByCriteria(String startDate, String endDate, String employeeId, String loginId){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        String selectClause = null, criteria = null, orderBy = null;
//        String designation = Globals.getShared().getEmployee().designation;
            if(loginId.equals("1")){
                selectClause = "* , (SELECT e.name FROM employees e WHERE e.id = attendance.empId) AS name ,(SELECT e.status FROM employees e WHERE e.id = attendance.empId ) AS status ";
//             criteria = " empId IN(Select id from employees where managerId = "+ loginId+ ") AND date = '"+ startDate + "'";
                criteria = " date = '"+ startDate + "'";

                Log.d("SelectClause", " "+ selectClause);
             Log.d("Criteria", " " + criteria);
            }else {
                selectClause = "* , (SELECT e.name FROM employees e WHERE e.id = attendance.empId) AS name ,(SELECT e.status FROM employees e WHERE e.id = attendance.empId ) AS status ";
             criteria = " empId IN(Select id from employees where managerId = "+ loginId+ ") AND date = '"+ startDate + "'";
            }
//        if(startDate != null && endDate == null && employeeId== null ){
//             selectClause = "* , (SELECT e.name FROM employees e WHERE e.id = attendance.empId) AS name ,(SELECT e.status FROM employees e WHERE e.id = attendance.empId ) AS status ";
//             criteria = " empId IN(Select id from employees where managerId = "+ loginId+ ") AND date = '"+ startDate + "'";
//             Log.d("SelectClause", " "+ selectClause);
//             Log.d("Criteria", " " + criteria);
//        } else if (startDate != null && endDate != null && employeeId!=null && loginId==null) {
//             selectClause = "* , (Select name from employees where id = attendance.empId LIMIT 1) as name , (Select status from employees where id = attendance.empId ) as status ";
//             criteria = " empId = "+ employeeId +" AND date BETWEEN '"+ startDate + "' AND '" + endDate +"'";
//             orderBy = " Date DESC";
//            Log.d("SelectClause", " "+ selectClause);
//            Log.d("Criteria", " " + criteria);
//            Log.d("orderBy", " " + orderBy);
//        } else if ( startDate ==null && endDate == null && employeeId!= null){
//
//            }
            return  dbHandler.getRecordByCriteria( selectClause,criteria,orderBy, attendanceConverter);
    }

       */
    public List<Attendance> getAttendanceByCriteria(String startDate, String endDate, String employeeId, String loginId){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        String selectClause = null, criteria = null, orderBy = null;
        EmployeeRepository employeeRepository = new EmployeeRepositoryImp(dbHandler);
        Employee employee=employeeRepository.getEmployeeById(loginId);
         Globals.getShared().setEmployee(employee);
         Log.d("Employee Name", " "+ Globals.getShared().getEmployee().name );
        selectClause = "* , (SELECT e.name FROM employees e WHERE e.id = attendance.empId) AS name ,(SELECT e.status FROM employees e WHERE e.id = attendance.empId ) AS status ";
        String dateCriteria = " date BETWEEN '" + startDate + "' AND '" + endDate +"'";
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
      return  dbHandler.getRecordByCriteria( selectClause,criteria,orderBy, attendanceConverter);
    }



}

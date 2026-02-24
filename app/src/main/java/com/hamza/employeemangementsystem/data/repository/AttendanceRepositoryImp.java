package com.hamza.employeemangementsystem.data.repository;

import static com.hamza.employeemangementsystem.core.DataSourceMode.LOCAL_ONLY;

import android.content.Context;
import android.util.Log;

import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.core.ResultCallback;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DbHandler;
import com.hamza.employeemangementsystem.data.database.local.AppDatabaseHelper;
import com.hamza.employeemangementsystem.data.database.local.SQLiteLocalDataSource;
import com.hamza.employeemangementsystem.data.database.remote.RemoteDataSource;
import com.hamza.employeemangementsystem.data.database.remote.RemoteDataSourceClass;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.domain.AttendanceRepository;
import com.hamza.employeemangementsystem.domain.EmployeeRepository;
import com.hamza.employeemangementsystem.ui.AttendanceConverter;
import com.hamza.employeemangementsystem.ui.EmployeeConverter;
import com.hamza.employeemangementsystem.ui.view.MainActivity;

import java.sql.SQLOutput;
import java.util.List;

public class AttendanceRepositoryImp implements AttendanceRepository {
        AppDatabaseHelper<Attendance> appDatabaseHelper;
        Context context;
        DbHandler<Attendance> dbHandler;



    public AttendanceRepositoryImp(DbHandler<Attendance> DbHandler, Context context) {
//        this.sqLiteLocalDataSource= new SQLiteLocalDataSource<>(appDatabaseHelper,context);

        this.context = context;
        this.dbHandler = DbHandler;
    }

    public List<Attendance> getAllAttendance(){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
            // dbHandler.getAllAsync(attendanceConverter);
        return null;
    }
    public void getAllAtt(ResultCallback<List<Attendance>> callback){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        dbHandler.getAllAsync(callback);
    }
    public Attendance getAttendanceByEmpId(String id ){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        return  (Attendance) dbHandler.getRecordById(id);
    }
    @Override
    public Attendance getLastAttendance(String empId){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
      String criteria = " empId = "+ empId +" ";
      String orderBy = " checkInTime DESC LIMIT 1";
      List<Attendance> attendanceList= dbHandler.getRecordByCriteria("*",criteria,orderBy);
      if (attendanceList!= null && attendanceList.size()==1){
          return attendanceList.get(0);
      }
      return null;
    }

    @Override
    public boolean updateAttendance(Attendance attendance) {
        AttendanceConverter attendanceConverter = new AttendanceConverter();

        dbHandler.updateRecord(String.valueOf(attendance.id), attendance);
        return true;
    }
    @Override
    public boolean insertAttendance(Attendance attendance) {
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        dbHandler.insertRecord(attendance);
        return true;
    }
    public List<Attendance> getAttendanceByCriteria(String startDate, String endDate, String employeeId, String loginId){
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        String selectClause = null, criteria = null, orderBy = null;
        SQLiteLocalDataSource<Attendance> sqLiteLocalDataSource = new SQLiteLocalDataSource<>(appDatabaseHelper,context);
        RemoteDataSourceClass<Employee> remoteDataSource = new RemoteDataSourceClass<>();
        AppDatabaseHelper<Employee> employeeAppDatabaseHelper1 = new AppDatabaseHelper<>(context);
        SQLiteLocalDataSource<Employee> sqLiteLocalDataSource1 = new SQLiteLocalDataSource<>(employeeAppDatabaseHelper1,context);
        IConvertHelper<Employee> convertHelper = new EmployeeConverter();
        DbHandler<Employee> dbHandler = new DbHandler<>(sqLiteLocalDataSource1,remoteDataSource, convertHelper, LOCAL_ONLY);

        EmployeeRepository employeeRepository = new EmployeeRepositoryImp(dbHandler,context);
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
      return  sqLiteLocalDataSource.getRecordByCriteria(selectClause,criteria,orderBy, attendanceConverter);
    }



}

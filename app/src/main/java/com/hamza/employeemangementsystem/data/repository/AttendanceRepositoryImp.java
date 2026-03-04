package com.hamza.employeemangementsystem.data.repository;

import static com.hamza.employeemangementsystem.core.DataSourceMode.LOCAL_ONLY;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.reflect.TypeToken;
import com.hamza.employeemangementsystem.core.ApiResultCallback;
import com.hamza.employeemangementsystem.core.DataSourceMode;
import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.core.ResultCallback;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DbHandler;
import com.hamza.employeemangementsystem.data.database.local.AppDatabaseHelper;
import com.hamza.employeemangementsystem.data.database.local.SQLiteLocalDataSource;
import com.hamza.employeemangementsystem.data.database.remote.MockRemoteDataSource;
import com.hamza.employeemangementsystem.data.database.remote.RemoteDataSource;
import com.hamza.employeemangementsystem.data.database.remote.RemoteDataSourceClass;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.domain.AttendanceRepository;
import com.hamza.employeemangementsystem.domain.EmployeeRepository;
import com.hamza.employeemangementsystem.ui.AttendanceConverter;
import com.hamza.employeemangementsystem.ui.EmployeeConverter;
import com.hamza.employeemangementsystem.ui.view.MainActivity;

import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AttendanceRepositoryImp implements AttendanceRepository {
        AppDatabaseHelper<Attendance> appDatabaseHelper;
        Context context;
        DbHandler<Attendance> dbHandler;
        EmployeeRepositoryImp employeeRepositoryImp;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    public AttendanceRepositoryImp(EmployeeRepositoryImp employeeRepositoryImp, DbHandler<Attendance> DbHandler, Context context) {
//        this.sqLiteLocalDataSource= new SQLiteLocalDataSource<>(appDatabaseHelper,context);
        this.employeeRepositoryImp = employeeRepositoryImp;
        this.context = context;
        this.dbHandler = DbHandler;
    }
    public void getAllAtt(ResultCallback<List<Attendance>> callback){
//        AttendanceConverter attendanceConverter = new AttendanceConverter();
        dbHandler.getAllAsync(callback);
    }
    public Attendance getAttendanceByEmpId(String id ){
        AttendanceConverter attendanceConverter = new AttendanceConverter(employeeRepositoryImp);
        return  (Attendance) dbHandler.getRecordById(id);
    }
    @Override
    public Attendance getLastAttendance(String empId){
        AttendanceConverter attendanceConverter = new AttendanceConverter(employeeRepositoryImp);
      String criteria = " empId = "+ empId +" ";
      String orderBy = " checkInTime DESC LIMIT 1";
      List<Attendance> attendanceList= dbHandler.getRecordByCriteria("*",criteria,orderBy,null);
      if (attendanceList!= null && attendanceList.size()==1){
          return attendanceList.get(0);
      }
      return null;
    }
    @Override
    public boolean updateAttendance(Attendance attendance) {
        AttendanceConverter attendanceConverter = new AttendanceConverter(employeeRepositoryImp);

        dbHandler.updateRecord(String.valueOf(attendance.id), attendance);
        return true;
    }
    @Override
    public boolean insertAttendance(Attendance attendance) {
        AttendanceConverter attendanceConverter = new AttendanceConverter(employeeRepositoryImp);
        dbHandler.insertRecord(attendance);
        return true;
    }
    @Override
    public List<Attendance> getAttendanceByCriteria(String startDate, String endDate, String employeeId, String loginId){
        String selectClause = null, criteria = null, orderBy = null;

        Log.d("Mode", " " + dbHandler.getMode());
        Employee employee= employeeRepositoryImp.getEmployeeById(loginId);
        Globals.getShared().setEmployee(employee);
        selectClause = "* , (SELECT e.name FROM employees e WHERE e.id = attendance.empId) AS name ,(SELECT e.status FROM employees e WHERE e.id = attendance.empId ) AS status ";
        String dateCriteria =startDate + "' AND '" + endDate +"'ORDER BY date DESC" ;
        String employeeFilterCriteria = (employeeId.isEmpty()?"":" empId ="+employeeId);
        String employeeCriteria="";
        if (dbHandler.getMode()==LOCAL_ONLY){
            if(employee.designation.equals("admin")){
                employeeCriteria = employeeFilterCriteria;
            }else if(employee.designation.equals("manager")){
                employeeCriteria = " empId IN(Select id from employees where managerId = "+ loginId+ ")" + (!employeeFilterCriteria.isEmpty()?" AND "+employeeFilterCriteria:"")  ;
            }else{
                employeeCriteria = " empId = " + loginId + " ";
            }
            criteria = employeeCriteria + (!employeeCriteria.isEmpty()?" AND ":"") + dateCriteria;
            return  dbHandler.getRecordByCriteria(selectClause,criteria, orderBy,null);

        }else {
            if(employee.designation.equals("admin") ||employee.designation.equals("manager")) {
                loginId = employeeId;
            }
            Type type = new TypeToken<List<Attendance>>() {}.getType();
            criteria = "empId=" + Integer.parseInt(loginId) + "&startDate=" + startDate + "&endDate=" + endDate ;
            return  dbHandler.getRecordByCriteria(selectClause,criteria, orderBy,type);
        }

    }
}

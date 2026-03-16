package com.hamza.employeemangementsystem.data.repository;

import static com.hamza.employeemangementsystem.core.DataSourceMode.LOCAL_ONLY;
import static com.hamza.employeemangementsystem.core.DataSourceMode.REMOTE_ONLY;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.reflect.TypeToken;
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
    public void getAllAtt(ResultCallback<List<Attendance>> callback,Type type){
//        AttendanceConverter attendanceConverter = new AttendanceConverter();
        dbHandler.getAllAsync(callback,type);
    }
    public Attendance getAttendanceByEmpId(String id ){
        Type type = new TypeToken<List<Attendance>>() {}.getType();
        AttendanceConverter attendanceConverter = new AttendanceConverter(employeeRepositoryImp);
        return  (Attendance) dbHandler.getRecordById(id,type);
    }
    @Override
    public Attendance getLastAttendance(String empId){
        Log.d("Attendance Mode", ""+dbHandler.getMode());
        Type type = new TypeToken<List<Attendance>>() {}.getType();

        AttendanceConverter attendanceConverter = new AttendanceConverter(employeeRepositoryImp);
        if(dbHandler.getMode()==LOCAL_ONLY){
            String criteria = " empId = "+ empId +" ";
            String orderBy = " checkInTime DESC LIMIT 1";
            List<Attendance> attendanceList= dbHandler.getRecordByCriteria("*",criteria,orderBy,null);
            if (attendanceList!= null && attendanceList.size()==1){
                return attendanceList.get(0);
            }
        }else if (dbHandler.getMode()==REMOTE_ONLY){
            Attendance lastAttendance =  dbHandler.getLastRecord(empId,type);
            return lastAttendance;
        }
        return null;

    }
    @Override
    public void updateAttendance(Attendance attendance) {
        AttendanceConverter attendanceConverter = new AttendanceConverter(employeeRepositoryImp);
        Log.d("Attendance" , attendance.empId +  " " + attendance.checkOutTime);
        dbHandler.updateRecord(String.valueOf(attendance.id), attendance);
    }
    @Override
    public void insertAttendance(Attendance attendance) {
        AttendanceConverter attendanceConverter = new AttendanceConverter(employeeRepositoryImp);
        dbHandler.insertRecord(attendance);
    }
    @Override
    public List<Attendance> getAttendanceByCriteria(String startDate, String endDate, String employeeId, String loginId){
        String selectClause = null, criteria = null, orderBy = null;
        Log.d("Mode", " " + dbHandler.getMode());
        Log.d("Login Id", loginId);
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
               loginId= employeeId==""?loginId:employeeId;
            }
            Type type = new TypeToken<List<Attendance>>() {}.getType();
            criteria = "empId=" + Integer.parseInt(loginId) + "&startDate=" + startDate + "&endDate=" + endDate ;
            return  dbHandler.getRecordByCriteria(selectClause,criteria, orderBy,type);
        }
    }
}

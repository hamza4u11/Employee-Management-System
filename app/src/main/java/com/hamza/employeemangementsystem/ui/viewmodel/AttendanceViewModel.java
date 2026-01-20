package com.hamza.employeemangementsystem.ui.viewmodel;

import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.repository.AttendanceRepositoryImp;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp;
import com.hamza.employeemangementsystem.utils.DateTimeUtlis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class AttendanceViewModel extends ViewModel {
    private AttendanceRepositoryImp repository;
    private EmployeeRepositoryImp employeeRepo;
    private String statusText= "";
    private String seesionText="";
    private String seesionLabel="";
    private Date time;
    private String checkInOutText;
    private boolean ifUserCheckedIn = false;
    private boolean isLayoutEnabled = false;
    private final MutableLiveData<Boolean> openSelectProfile = new MutableLiveData<>();
    private Boolean isAdmin = false;


    public LiveData<Boolean> openSelectProfile() {
        return openSelectProfile;
    }


    public AttendanceViewModel(@NonNull DBHandler dbHandler) {
        super();
        repository = new AttendanceRepositoryImp(dbHandler);
        employeeRepo = new EmployeeRepositoryImp(dbHandler);
    }
    public Attendance loadUserStatus(String id ) {
        Log.d("Android" ,"Attendance LoadUserStatus");
        Employee employee = employeeRepo.getEmployeeById(id);
        Log.d("Employee Designation", employee.designation);
        Attendance record = repository.getLastAttendance(id);
//        Log.d("Checkinn time","" +record.checkInTime );
//        Log.d("Checkout time","" +record.checkOutTime );
        statusText = "";
        seesionText="";
        checkInOutText="";
        ifUserCheckedIn=false;
        isLayoutEnabled = false;
        isAdmin= false;
        seesionLabel="";
        if (record != null ){
            time = (record.checkOutTime==null || record.checkOutTime.isEmpty() ) ?  DateTimeUtlis.getShared().convertStringToDateTime(record.checkInTime):DateTimeUtlis.getShared().convertStringToDateTime(record.checkOutTime);
            Log.d("Time" , " " +time);
            seesionLabel= (record.checkInTime != null && record.checkOutTime == null )? "Current Seeion Duration" : "Last Seesion Duration";
            checkInOutText = (record.checkOutTime == null || record.checkOutTime.isEmpty() )? "Last Check In Time" : "Last Check out Time";
            statusText= DateTimeUtlis.getShared().formatDateTime(time, Globals.getShared().getDateTimeFormat());
            seesionText=DateTimeUtlis.getShared().calculateDurationBetween(record.checkInTime,record.checkOutTime==null|| record.checkOutTime.isEmpty() ? DateTimeUtlis.getShared().getNow() :record.checkOutTime);
            ifUserCheckedIn = record.checkOutTime == null || record.checkOutTime.isEmpty();
            isLayoutEnabled = Objects.equals(employee.designation, "manager") || Objects.equals(employee.designation, "admin");
            isAdmin= Objects.equals(employee.designation, "admin");
        }else{
            time = null;
            checkInOutText= "Welcome";
            statusText = employee.name;
            seesionText="Please Check INN";
            ifUserCheckedIn=false;
            isLayoutEnabled=false;
            isAdmin=false;
            seesionLabel="Start Seesion";

        }




        return record;
    }
    public Boolean getIsAdmin(){
        return isAdmin;
    }
    public Boolean getIfUserCheckedIn(){

        return ifUserCheckedIn;
    }
    public Boolean isLayoutEnabled(){

        return isLayoutEnabled;
    }


    public String getStatusText() {

        return statusText;
    }

    public String getSeesionText() {

        return seesionText;
    }
    public String getSeesionLabel() {
        return seesionLabel;
    }
    public String getCheckInOutText() {

        return checkInOutText;
    }
    public void checkIn(String id){
        Attendance attendance = new Attendance();
        attendance.empId = Integer.parseInt(id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            attendance.date= LocalDate.now().toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            attendance.checkInTime = LocalDateTime.now().toString();
        }
        repository.insertAttendance(attendance);
    }
    public void checkOut(String id) {
       Attendance record = repository.getLastAttendance(id);
        Attendance attendance = new Attendance();
        attendance.id =record.id;
        attendance.empId=Integer.parseInt(id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            attendance.date= LocalDate.now().toString();
        }
        attendance.checkInTime = record.checkInTime;
        Log.d("checkInTime",record.checkInTime);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            attendance.checkOutTime = LocalDateTime.now().toString();
        }
        repository.updateAttendance(attendance);
    }
    public void logout(){
        Globals.getShared().setEmployee(null);
        openSelectProfile.setValue(true);
    }



}

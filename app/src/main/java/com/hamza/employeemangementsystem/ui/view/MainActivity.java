package com.hamza.employeemangementsystem.ui.view;

import static com.hamza.employeemangementsystem.utils.DateTimeUtlis.getShared;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.ui.view.fragment.SelectProfileFragment;
import com.hamza.employeemangementsystem.ui.viewmodel.AttendanceViewModel;
import com.hamza.employeemangementsystem.utils.DateTimeUtlis;

import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private AttendanceViewModel attendanceViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RecyclerView.Adapter adapter = new myAdapter(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, new SelectProfileFragment())
                .commit();
        DBHandler<Attendance> attendanceDBHandler = new DBHandler<>(this);
        attendanceViewModel = new AttendanceViewModel(attendanceDBHandler);

//        DBHandler<Attendance> attendanceDBHandler= new DBHandler<>(this);
//         Attendance attendance = new Attendance();
//        attendance.empId= 1;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            attendance.checkInTime= LocalDateTime.now().toString();
//        }
//        attendance.overTime= 2;
//
//        attendanceDBHandler.createRecord(attendance,new AttendanceConverter());

//        SQLiteDatabase db = openOrCreateDatabase("ems.db", MODE_PRIVATE, null);
//        db.execSQL("DROP TABLE IF EXISTS attendance");
//        db.close();


//        DBHandler<Employee> EmployeeDBHandler= new DBHandler<>(this);
//        EmployeeDBHandler.deleteRecord(6,new EmployeeConverter());

//        DBHandler <Attendance> AttendanceDBHandler = new DBHandler<>(this);
//        AttendanceDBHandler.deleteRecord(6,new AttendanceConverter());

//        DBHandler<Attendance> AttendanceDBHandler = new DBHandler<>(this);
//        Attendance attendance = new Attendance();
//        attendance.empId=5;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            attendance.checkInTime= LocalDateTime.now().toString();
//        }
//        attendance.overTime= 5;
//        AttendanceDBHandler.updateRecord(5,attendance,new AttendanceConverter());

//       DBHandler<Attendance> AttendanceDBHandler = new DBHandler<>(this);
//        AttendanceRepository attendanceRepository = new AttendanceRepository(AttendanceDBHandler);
//        int record = attendanceRepository.getAttendanceByEmpId("100").overTime;
//        Log.d("TAG",String.valueOf(record));


//        DBHandler<Attendance> AttendanceDBHandler = new DBHandler<>(this);
//        AttendanceRepository attendanceRepository = new AttendanceRepository(AttendanceDBHandler);
//        int record = attendanceRepository.getAllAttendance().size();
//        Log.d("TAG",String.valueOf(record));

//
//        SQLiteDatabase db = openOrCreateDatabase("ems.db", MODE_PRIVATE, null);
//
//        db.execSQL(
//                "CREATE TABLE IF NOT EXISTS attendance (" +
//                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                        "empId INTEGER NOT NULL, " +
//                        "checkInTime TEXT NOT NULL, " +
//                        "checkOutTime TEXT, " +
//                        "overTime INTEGER DEFAULT 0)"
//        );
//
//        db.close();

//        DBHandler<Attendance> AttendanceDBHandler = new DBHandler<>(this);
//        AttendanceRepository attendanceRepository = new AttendanceRepository(AttendanceDBHandler);
//        Attendance attendance = new Attendance();
//        attendance.empId= 100;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            attendance.checkInTime= LocalDateTime.now().toString();
//        }
//        attendance.overTime=100;
//
//        attendanceRepository.updateAttendance(5 ,attendance);


//        DBHandler<Attendance> attendanceDBHandler = new DBHandler<>(this);
//        AttendanceConverter attendanceConverter = new AttendanceConverter();
//        int record= attendanceDBHandler.getLastRecord("1",attendanceConverter).overTime;
//        Log.d("Attendance Records",String.valueOf(record));


//        DBHandler<Attendance> attendanceDBHandler = new DBHandler<>(this);
////        AttendanceRepository attendanceRepository = new AttendanceRepository(attendanceDBHandler);
////        int record=  attendanceDBHandler.getLastAttendance("1").size();
////        Log.d("Record", String.valueOf(record));
//        List<Attendance> list =attendanceDBHandler.getRecordByCriteria("empId = 1 ","checkInTime DESC LIMIT 1" , new AttendanceConverter());
//       Log.d("Size"," " +list.size());
//        Log.d("CheckInTime"," " +list.get(0).checkInTime);

//        DBHandler<Attendance> attendanceDBHandler = new DBHandler<>(this);
//        AttendanceRepository attendanceRepository = new AttendanceRepository(attendanceDBHandler);
//        List<Attendance> list =attendanceRepository.getLastAttendance("1");
//       Log.d("Size"," " +list.size());
//        Log.d("CheckInTime"," " +list.get(0).checkInTime);
        Log.d("I am from MainActiviy", " Hamza" );
        testFunctionForDateTime();
        durationTestFunction("2026-01-08T17:19:19.441779", "2026-01-09T17:15:19.441779");
//        testCheckInFunction();



    }
    private void testCheckInFunction(){
        attendanceViewModel.checkIn(String.valueOf(5));

    }

    private void testFunctionForDateTime(){
        Date date ;
       date= DateTimeUtlis.getShared().convertStringToDateTime("2026-01-08T17:19:19.441779");
        Log.d("DateCoversionTest", DateTimeUtlis.getShared().formatDateTime(date, Globals.getShared().getTimeFormat()));

    }
    private void durationTestFunction(String date1 , String date2){
      Log.d("Duration Test Function",DateTimeUtlis.getShared().calculateDurationBetween(date1,date2));
    }
}

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

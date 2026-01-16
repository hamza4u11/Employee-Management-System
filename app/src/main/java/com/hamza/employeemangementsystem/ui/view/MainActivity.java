package com.hamza.employeemangementsystem.ui.view;

import static com.hamza.employeemangementsystem.utils.DateTimeUtlis.getShared;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.ui.view.fragment.DashboardFragment;
import com.hamza.employeemangementsystem.ui.view.fragment.SelectProfileFragment;
import com.hamza.employeemangementsystem.ui.viewmodel.AttendanceViewModel;
import com.hamza.employeemangementsystem.ui.viewmodel.EmployeeViewModel;
import com.hamza.employeemangementsystem.utils.DateTimeUtlis;

import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private AttendanceViewModel attendanceViewModel;

    String pinOne, pinSecond,pinThird,pinFourth,pin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SelectProfileFragment profileFragment = SelectProfileFragment.newInstance(null, null);
        Log.d("ID_CHECK", String.valueOf(R.id.fragmentContainer));
        profileFragment.setListener(new SelectProfileFragment.MyOnClickListener (){
            @Override
            public void OnItemClick(Employee employee) {
                Log.d("Employee ", employee.name);
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialogue_pin);
                dialog.setCancelable(false);
                EditText pin1 = dialog.findViewById(R.id.pin1);
                EditText pin2 = dialog.findViewById(R.id.pin2);
                EditText pin3 = dialog.findViewById(R.id.pin3);
                EditText pin4 = dialog.findViewById(R.id.pin4);
                Button loginBtn = dialog.findViewById(R.id.btnLogin);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                loginBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pinOne = pin1.getText().toString().trim();
                        pinSecond = pin2.getText().toString().trim();
                        pinThird = pin3.getText().toString().trim();
                        pinFourth = pin4.getText().toString().trim();
                        pin = "" + pinOne + pinSecond + pinThird + pinFourth;
                        Log.d("PIN", pin);
                        Log.d("PIN", employee.pin);
                        int duration = Toast.LENGTH_SHORT;
                        if (Integer.parseInt(pin) == Integer.parseInt(employee.pin)) {
                            Log.d("Designation", employee.designation);
                            Globals.getShared().setEmployee(employee);
                            Fragment fragment= new DashboardFragment();
                            MainActivity.this
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragmentContainer, fragment)
                                    .addToBackStack(null)
                                    .commit();
                            dialog.cancel();


                        } else {
                            Toast.makeText(MainActivity.this, "INVALID USER", duration).show();
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                Toast.makeText(MainActivity.this,"Employee Clicked :"+employee.name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnAddClick() {
                Toast.makeText(MainActivity.this,"Add clicked", Toast.LENGTH_SHORT).show();
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer,profileFragment)
                .commit();

//        Log.d("ID_CHECK", String.valueOf(R.id.fragmentContainer));

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

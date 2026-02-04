package com.hamza.employeemangementsystem.ui.view;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_SHORT;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.model.Report;
import com.hamza.employeemangementsystem.data.repository.AttendanceRepositoryImp;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp;
import com.hamza.employeemangementsystem.ui.AttendanceConverter;
import com.hamza.employeemangementsystem.ui.adopter.myAdapter.ItemClickHandler;
import com.hamza.employeemangementsystem.ui.view.fragment.DashboardFragment;
import com.hamza.employeemangementsystem.ui.view.fragment.EmployeeFragment;
import com.hamza.employeemangementsystem.ui.view.fragment.ListFragment;
import com.hamza.employeemangementsystem.ui.view.fragment.ReportFragment;
import com.hamza.employeemangementsystem.ui.view.fragment.SelectProfileFragment;
import com.hamza.employeemangementsystem.ui.viewmodel.DashboardViewModel;
import com.hamza.employeemangementsystem.ui.viewmodel.EmployeeViewModel;
import com.hamza.employeemangementsystem.ui.viewmodel.ReportViewModel;
import com.hamza.employeemangementsystem.ui.viewmodel.SelectProfileViewModel;
import com.hamza.employeemangementsystem.utils.DateTimeUtlis;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private static final String TABLE_EMPLOYEE = "employees";
    private DashboardViewModel dashboardViewModel;
    private SelectProfileViewModel selectProfileViewModel;
    private EmployeeRepositoryImp employeeRepositoryImp;
    private EmployeeViewModel employeeViewModel;


    ArrayList<String> list;


    private DBHandler dbHandler;
    String pinOne, pinSecond,pinThird,pinFourth,pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        list.add("Hamza");
        list.add("Abdullah");
        list.add("Salman");
        list.add("Rabeet");
        list.add("Ahmad Raza");
        list.add("Asif");
       // testinglistFargment( list ,"Students");
      testingReportFragment("2026-01-15","2026-01-29","2",null);
      //  testingCriteriaFunctionDb("* , (Select name from employees where id = attendance.empId ) as \"name\", (Select status from employees where id = attendance.empId ) as \"status\"", "empId= 2 and date Between '2026-01-15' and '2026-01-29'", null);
      //  testDateFunction("2026-01-19T16:40:27.548762");
       // testingReportFragment(null,null,null,null);
       // openSelectProfileScreen(null, null);
       // openEmployeeScreen();
//        DBHandler<Attendance> attendanceDBHandler = new DBHandler<>(this);
//        AttendanceRepositoryImp attendanceRepositoryImp = new AttendanceRepositoryImp(attendanceDBHandler);

//      ReportViewModel viewModel = new ReportViewModel(attendanceDBHandler,"2026-01-15","2026-01-29","2",null);
//      viewModel.getAllReports();
//        int reportsSize=viewModel.getReportsByCriteria("2026-01-15","2026-01-29","2",null).size();
//        Log.d("TAG", String.valueOf(reportsSize));

        }

    private void testinglistFargment(ArrayList<String> list, String title) {
        ListFragment fragment = ListFragment.newInstance(list,title);
        fragment.setListener(new ItemClickHandler (){
            @Override
            public void ViewReportClick(String itemName) {
             Toast.makeText(MainActivity.this,itemName,LENGTH_SHORT).show();

//                if(Objects.equals(itemName, "Today Attendance")){
//                    Toast.makeText(MainActivity.this,"Today",LENGTH_SHORT).show();
//
//                }else if (Objects.equals(itemName, "Attendance By Employee")){
//                    Toast.makeText(MainActivity.this,"Employee",LENGTH_SHORT).show();
//
//                }else if (Objects.equals(itemName, "All Month Attendance")){
//                    Toast.makeText(MainActivity.this,"Month",LENGTH_SHORT).show();
//
//                }else if (Objects.equals(itemName,"One Week Attendance")){
//                    Toast.makeText(MainActivity.this,"Week",LENGTH_SHORT).show();
//
//                }else if (Objects.equals(itemName, "Manager Attendance")){
//                    Toast.makeText(MainActivity.this,"Manager",LENGTH_SHORT).show();
//
//                }else if(Objects.equals(itemName,"Attendance By Date")){
//                    Toast.makeText(MainActivity.this,"Date",LENGTH_SHORT).show();
//
//                }

                //openSelectProfileScreen(null,null);
            }
        });
        MainActivity.this
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void openEmployeeScreen(String mode , String employeeId){
            Log.d("openEmployeeScreen", mode );
        EmployeeFragment fragment =  EmployeeFragment.newInstance(mode,employeeId);
        fragment.setListener(new EmployeeFragment.OnEventClickListener(){
            @Override
            public void OnBackClick() {
                Log.d("Click from employee fragment", "clicked ");
                String screenMode= Objects.equals(Globals.getShared().getEmployee().designation, "admin") ? "add":null;
                String _managerId= null ;
                openSelectProfileScreen(screenMode,_managerId);

            }
        });
            MainActivity.this
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    private void openDashboardScreen() {

        DashboardFragment fragment= new DashboardFragment();
        fragment.setListener( new DashboardFragment.OnEventClickListener() {
            @Override
            public void OnManageEmployeesClick(String mode, String managerId) {
                 openManageEmployeesScreen(mode,managerId);
            }
            @Override
            public void OnReportsClick() {
              //  openReportsScreen();
            }
            @Override
            public void OnLogoutClick() {
                openSelectProfileScreen(null,null);
            }
        });
        MainActivity.this
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void openReportsScreen(ArrayList<String> criteria) {

        ListFragment fragment = ListFragment.newInstance(criteria,null);
        fragment.setListener(new ItemClickHandler (){
            @Override
            public void ViewReportClick(String itemName) {
                if(Objects.equals(itemName, "Today Attendance")){
                    Toast.makeText(MainActivity.this,"Today",LENGTH_SHORT).show();

                }else if (Objects.equals(itemName, "Attendance By Employee")){
                    Toast.makeText(MainActivity.this,"Employee",LENGTH_SHORT).show();

                }else if (Objects.equals(itemName, "All Month Attendance")){
                    Toast.makeText(MainActivity.this,"Month",LENGTH_SHORT).show();

                }else if (Objects.equals(itemName,"One Week Attendance")){
                    Toast.makeText(MainActivity.this,"Week",LENGTH_SHORT).show();

                }else if (Objects.equals(itemName, "Manager Attendance")){
                    Toast.makeText(MainActivity.this,"Manager",LENGTH_SHORT).show();

                }else if(Objects.equals(itemName,"Attendance By Date")){
                    Toast.makeText(MainActivity.this,"Date",LENGTH_SHORT).show();

                }

                //openSelectProfileScreen(null,null);
            }
        });
        MainActivity.this
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void openManageEmployeesScreen( String mode,String adminOrManager) {
        Log.d("Manage Employees for admin", "openManageEmployeesScreen");
        openSelectProfileScreen(mode, adminOrManager);
    }

    private void openSelectProfileScreen( String mode,String managerId ) {
        SelectProfileFragment profileFragment;
        profileFragment = SelectProfileFragment.newInstance(mode,managerId);
        Log.d("ID_CHECK", String.valueOf(R.id.fragmentContainer));
        profileFragment.setListener(new SelectProfileFragment.MyOnClickListener (){
            @Override
            public void OnItemClick(Employee employee) {
                Log.d("Employee ", employee.name);
                if (Globals.getShared().getEmployee()==null){
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
                            int duration = LENGTH_SHORT;
                            if (Integer.parseInt(pin) == Integer.parseInt(employee.pin)) {
                                Log.d("Designation", employee.designation);
                                Globals.getShared().setEmployee(employee);

                                openDashboardScreen();
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
                    Toast.makeText(MainActivity.this,"Employee Clicked :"+employee.name, LENGTH_SHORT).show();
                }else{
                      openEmployeeScreen("edit",String.valueOf(employee.id));
                }
            }
            @Override
            public void OnAddClick() {
                String mode = Objects.equals(Globals.getShared().getEmployee().designation, "admin") ? "add" : "edit";
                openEmployeeScreen(mode,null);


            }
        });        MainActivity.this
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, profileFragment)
                .addToBackStack(null)
                .commit();
    }
    private void testingReportFragment(String startDate, String endDate, String employeeId, String loginId){
        ReportFragment reportFragment = ReportFragment.newInstance(startDate,endDate,employeeId,loginId);
        MainActivity.this
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer,reportFragment)
                .addToBackStack(null)
                .commit();
    }
    private void testCheckInFunction(){
        dashboardViewModel.checkIn(String.valueOf(5));

    }
    private void testFunctionForDateTime(){
        Date date ;
       date= DateTimeUtlis.getShared().convertStringToDateTime("2026-01-08T17:19:19.441779");
        Log.d("DateCoversionTest", DateTimeUtlis.getShared().formatDateTime(date, Globals.getShared().getTimeFormat()));

    }
    private void durationTestFunction(String date1 , String date2){
      Log.d("Duration Test Function",DateTimeUtlis.getShared().calculateDurationBetween(date1,date2));
    }
    private void checkAllTables() {

        dbHandler = new DBHandler(this);
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table'",
                null
        );

        if (c.moveToFirst()) {
            do {
                String tableName = c.getString(0);
                Log.d(TAG, tableName);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
    }
    private void insertEmployee(SQLiteDatabase db) {

//        ContentValues values = new ContentValues();
//        values.put("name", "Hamza");
//        values.put("designation", "Admin");
//
//        long id = db.insert(DBHandler.TABLE_EMPLOYEE, null, values);

//        Log.d("DB_INSERT", "Inserted row id = " + id);
    }
    private void checkTables(SQLiteDatabase db) {

        Cursor c = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table'",
                null
        );

        while (c.moveToNext()) {
            Log.d("DB_TABLE", c.getString(0));
        }

        c.close();
    }
    public void testGetAllManagers(){
        int size =employeeRepositoryImp.getAllManagers().size();
        Log.d("testGetAllManagers: ", String.valueOf(size));


    }
    public void testDateFunction(String date){
       Date date1 =  DateTimeUtlis.getShared().convertStringToDateTime(date);
       String time = DateTimeUtlis.getShared().formatDateTime(date1, "h:mm a ");
        Log.d("Date", ""+ date1);
        Log.d("Time", " " + time);
    }
    private void testingCriteriaFunctionDb(String select, String criteria , String orderBy){
        DBHandler<Attendance> dbHandler1= new DBHandler<>(this);
        AttendanceConverter attendanceConverter = new AttendanceConverter();
        int size = dbHandler1.getRecordByCriteria(select, criteria,orderBy,attendanceConverter).size();
        Log.d("Size of Records"," " + size);




    }


}

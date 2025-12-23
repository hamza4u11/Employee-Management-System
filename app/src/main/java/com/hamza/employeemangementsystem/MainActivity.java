package com.hamza.employeemangementsystem;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.AttendenceModel;
import com.hamza.employeemangementsystem.data.model.EmployeeModel;
import com.hamza.employeemangementsystem.data.model.PaymentModel;
import com.hamza.employeemangementsystem.data.repository.AttendenceRepository;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepository;
import com.hamza.employeemangementsystem.data.repository.PaymentRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DBHandler db = new DBHandler(this);
        EmployeeRepository employeeRepository = new EmployeeRepository(this);
       //employeeRepository.getEmployeeById(2);
       // EmployeeModel emp = new EmployeeModel();

        //employeeRepository.getAllEmployees();


     //employeeRepository.updateEmployee(new EmployeeModel(7,"Rabeet Akbar", "USer", "123456","Lahore","weekly","2","2","in-active","0000","true","employee","0"));
       // employeeRepository.deleteEmployee(6);
       PaymentRepository PaymentRepository = new PaymentRepository(this);
       //PaymentRepository.insertPayment(new PaymentModel(224949, "07-03-2000", "March ","2000","1"));
      // PaymentRepository.insertPayment(new PaymentModel(150000, "18-04-1999", "April","2025","0"));
       //PaymentRepository.updatePayment(new PaymentModel(20,22000000 ,"12-02-24", "Februry","2015","1"));
               // PaymentRepository.getAllPayments();
       //PaymentRepository.delete(20);
        AttendenceRepository report = new AttendenceRepository(this);
//        report.insertReport(new AttendenceModel(1,"9:00","5:00",0));
//        report.insertReport(new AttendenceModel(2,"9:00","7:00",2));
//        report.insertReport(new AttendenceModel(3,"9:00","8:00",3));
            report.getAllReports();
       // report.delete(3);

    }
}

package com.hamza.employeemangementsystem.ui.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.ui.viewmodel.EmployeeViewModel;

public class SelectProfileActivity extends AppCompatActivity {
    private EmployeeViewModel employeeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);
        DBHandler<Employee> employeeDBHandler= new DBHandler<>(this);
        employeeViewModel = new EmployeeViewModel(employeeDBHandler);





    }

}

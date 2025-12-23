package com.hamza.employeemangementsystem.ui.view;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.ui.viewmodel.EmployeeViewModel;

public class EmployeeActivity extends AppCompatActivity {
    private EmployeeViewModel employeeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
    }
}

package com.hamza.employeemangementsystem.ui.view;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.model.AttendenceModel;
import com.hamza.employeemangementsystem.data.repository.AttendenceRepository;
import com.hamza.employeemangementsystem.ui.viewmodel.AttendenceViewModel;

public class AttendenceActivity extends AppCompatActivity {
    private AttendenceViewModel attendenceViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        attendenceViewModel = new ViewModelProvider(this).get(AttendenceViewModel.class);


        attendenceViewModel.insertReport(new AttendenceModel(1,"9:00","5:00",0));
    }
}

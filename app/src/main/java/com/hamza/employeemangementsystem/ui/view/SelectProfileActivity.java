package com.hamza.employeemangementsystem.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.model.EmployeeModel;
import com.hamza.employeemangementsystem.data.repository.AttendenceRepository;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepository;
import com.hamza.employeemangementsystem.ui.adopter.myAdopter.myAdapter;
import com.hamza.employeemangementsystem.ui.viewmodel.EmployeeViewModel;

public class SelectProfileActivity extends AppCompatActivity {
    private EmployeeViewModel employeeViewModel;
//    LinearLayout selectProfile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        RecyclerView selectProfile= findViewById(R.id.selectProfile);
        myAdapter adapter = new myAdapter();
        selectProfile.setLayoutManager(new LinearLayoutManager(this));
        selectProfile.setAdapter(adapter);
        employeeViewModel.getAllEmployees().observe(this, employees -> {
            adapter.setList(employees);
        });





//EmployeeRepository employeeRepository = new EmployeeRepository(this);
//        employeeRepository.insertEmployee(new EmployeeModel("Rabeet ", "Manager", "11111","Lahore","weekly","2","2","in-active","0000","true","employee","0"));
//        employeeRepository.insertEmployee(new EmployeeModel("Rabeet Iqbal", "Employee", "11111","Lahore","weekly","2","2","in-active","0000","true","employee","0"));
//        employeeRepository.insertEmployee(new EmployeeModel("Hamza Bilal", "Admin", "11111","Lahore","weekly","2","2","in-active","0000","true","employee","0"));
//        employeeRepository.insertEmployee(new EmployeeModel("Abdullah Khan", "Employee", "11111","Lahore","weekly","2","2","in-active","0000","true","employee","0"));
//        employeeRepository.insertEmployee(new EmployeeModel("Ali Akbar Iqbal", "Employee", "11111","Lahore","weekly","2","2","in-active","0000","true","employee","0"));


//
//
//       selectProfile.setLayoutManager(new LinearLayoutManager(this));
//        employeeViewModel.getAllEmployees().observe(this, list -> {
//            adapter.setList(list);
//        });
      // String employees[];
//       selectProfile.setAdapter(new myAdapter(employees));

    }

}

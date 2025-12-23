package com.hamza.employeemangementsystem.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.model.EmployeeModel;
import com.hamza.employeemangementsystem.ui.viewmodel.EmployeeViewModel;

public class LoginPinActivity extends AppCompatActivity {
    private EmployeeViewModel employeeViewModel;
    TextView profileName, headerProfileName;
    Button login;
    EditText pin1, pin2, pin3 , pin4;
    String pin, pinOne, pinSecond, pinThird , pinFourth;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pin);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        String name = getIntent().getStringExtra("name");
        String id =getIntent().getStringExtra("id");
        profileName = findViewById(R.id.profileName);
        profileName.setText(name);
        headerProfileName = findViewById(R.id.headerProfileName);
        headerProfileName.setText(name);
        pin1 = findViewById(R.id.pin1);
        pin2 = findViewById(R.id.pin2);
        pin3 = findViewById(R.id.pin3);
        pin4 = findViewById(R.id.pin4);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int id =Integer.parseInt(getIntent().getStringExtra("id"));

//                int employeeId = getIntent().getIntExtra("EMP_ID", -1);
                 pinOne= pin1.getText().toString().trim();
                 pinSecond = pin2.getText().toString().trim();
                 pinThird= pin3.getText().toString().trim();
                 pinFourth = pin4.getText().toString().trim();
                pin = "" +  pinOne + pinSecond + pinThird + pinFourth;
                Log.d("PIN", pin );
//                Log.d("Id", id);

             String desgination = employeeViewModel.login( id,pin);
                if (desgination == null) {
                    Toast.makeText(LoginPinActivity.this,"PIN Not Matched ✅",Toast.LENGTH_SHORT).show();                    return;
                }
                switch (desgination){
                    case "admin":
                        Log.d("desgination","ADMIN");
                        break;
                    case "manager":
                        Log.d("desgination","Manager");
                        break;
                    case "employee":
                        Log.d("desgination","Employee");
                        break;

                }


//                Boolean isCorrect = employeeViewModel.isPinCorrect(id, pin );


//                if (isCorrect) {
//                    Log.d("Pin is ","Correct");
//                    Toast.makeText(LoginPinActivity.this,
//                            "PIN Matched ✅",
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.d("Pin is ","InCorrect");
//
//                    Toast.makeText(LoginPinActivity.this,
//                            "PIN Not Matched ✅",
//                            Toast.LENGTH_SHORT).show();
//                }


            }
        });








    }
}

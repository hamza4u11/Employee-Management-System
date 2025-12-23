package com.hamza.employeemangementsystem.ui.view;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.ui.viewmodel.PaymentViewModel;

public class PaymentActivity extends AppCompatActivity {
    private PaymentViewModel paymentViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        paymentViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);


    }
}

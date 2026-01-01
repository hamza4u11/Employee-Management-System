package com.hamza.employeemangementsystem.ui.view;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.ui.adopter.myAdopter.myAdapter;
import com.hamza.employeemangementsystem.ui.view.fragment.SelectProfileFragment;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RecyclerView.Adapter adapter = new myAdapter(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, new SelectProfileFragment())
                .commit();









    }
}

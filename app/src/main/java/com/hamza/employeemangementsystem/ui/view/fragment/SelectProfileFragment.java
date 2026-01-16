package com.hamza.employeemangementsystem.ui.view.fragment;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.ui.adopter.myAdapter.EmployeeClickHandler;
import com.hamza.employeemangementsystem.ui.adopter.myAdapter.myAdapter;
import com.hamza.employeemangementsystem.ui.viewmodel.AttendanceViewModel;
import com.hamza.employeemangementsystem.ui.viewmodel.EmployeeViewModel;

import java.security.PublicKey;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "modeParam";
    private static final String ARG_PARAM2 = "mangerIdParam";
    private EmployeeViewModel employeeViewModel;
    RecyclerView recyclerView;
    TextView txtName, txtDesignation;
    LinearLayout employeeId;
    Button btnLogin;
    String pinOne, pinSecond,pinThird,pinFourth,pin;
    private AttendanceViewModel attendanceViewModel; ;
    Button addBtn ;

    public interface MyOnClickListener{
        void OnItemClick(Employee employee);
        void OnAddClick();
    }
    private MyOnClickListener listener;

    public void setListener(MyOnClickListener listener) {
        this.listener = listener;
    }

    public MyOnClickListener getListener() {
        return listener;
    }

    // TODO: Rename and change types of parameters
    private String modeParam;
    private String managerIdParam;

    public SelectProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param modeParam Parameter 1.
     * @param managerIdParam Parameter 2.
     * @return A new instance of fragment SelectProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectProfileFragment newInstance(String modeParam, String managerIdParam) {
        SelectProfileFragment fragment = new SelectProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, modeParam);
        args.putString(ARG_PARAM2, managerIdParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modeParam = getArguments().getString(ARG_PARAM1);
            managerIdParam = getArguments().getString(ARG_PARAM2);
        }
        DBHandler<Employee> employeeDBHandler= new DBHandler<>(getActivity());
        employeeViewModel = new EmployeeViewModel(employeeDBHandler);
        DBHandler<Attendance> attendanceDBHandler= new DBHandler<>(getActivity());
        attendanceViewModel = new AttendanceViewModel(attendanceDBHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_select_profile, container, false);
        addBtn= view.findViewById(R.id.addBtn);
        addBtn.setVisibility(view.GONE);
        if(Objects.equals(modeParam, "add")) {
            addBtn.setVisibility(view.VISIBLE);
        }

        RecyclerView selectProfile = view.findViewById(R.id.selectProfile);
        EmployeeClickHandler employeeClickHandler = new EmployeeClickHandler() {
            @Override
            public void onItemClick(Employee employee) {
                if(listener!=null){
                    listener.OnItemClick(employee);
                    return;
                }
                Log.d("Employee ", employee.name);
                Dialog dialog = new Dialog(getActivity());
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
                                    requireActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragmentContainer, fragment)
                                    .addToBackStack(null)
                                    .commit();
                            dialog.cancel();


                        } else {
                            Toast.makeText(getActivity(), "INVALID USER", duration).show();
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
            }
        };
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.OnAddClick();
                }
            }
        });
        myAdapter adapter = new myAdapter(employeeClickHandler);
        selectProfile.setLayoutManager(new LinearLayoutManager(getActivity()));
        selectProfile.setAdapter(adapter);
        if (managerIdParam != null ){
            Log.d("ManagerId param", "Manager ID" );
            employeeViewModel.getEmployeesByManager(managerIdParam);

        }else{
            employeeViewModel.getAllEmployees().observe(getActivity(), employees -> {
                adapter.setList(employees);
            });
        }
        employeeViewModel.getFilteredEmployees().observe(getActivity(), employees -> {
            adapter.setList(employees);
        });


        return view;
    }
}
package com.hamza.employeemangementsystem.ui.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.ui.adopter.myAdapter.EmployeeClickHandler;
import com.hamza.employeemangementsystem.ui.adopter.myAdapter.myAdapter;
import com.hamza.employeemangementsystem.ui.viewmodel.DashboardViewModel;
import com.hamza.employeemangementsystem.ui.viewmodel.SelectProfileViewModel;

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
    private SelectProfileViewModel selectProfileViewModel;
    private DashboardViewModel dashboardViewModel; ;
    Button addBtn ;

    public interface MyOnClickListener{
        void OnItemClick(Employee employee);
        void OnAddClick();
    }
    private MyOnClickListener listener;

    public void setListener(MyOnClickListener listener) {
        this.listener = listener;
    }

    public MyOnClickListener getListener()
    {
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
        selectProfileViewModel = new SelectProfileViewModel(employeeDBHandler);
        DBHandler<Attendance> attendanceDBHandler= new DBHandler<>(getActivity());
        dashboardViewModel = new DashboardViewModel(attendanceDBHandler);
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
                Fragment fragment= EmployeeFragment.newInstance(String.valueOf(employee.id),null);
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();

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
            selectProfileViewModel.getEmployeesByManager(managerIdParam);

        }else{
            selectProfileViewModel.getAllEmployees().observe(getActivity(), employees -> {
                adapter.setList(employees);
            });
        }
        selectProfileViewModel.getFilteredEmployees().observe(getActivity(), employees -> {
            adapter.setList(employees);
        });


        return view;
    }
}
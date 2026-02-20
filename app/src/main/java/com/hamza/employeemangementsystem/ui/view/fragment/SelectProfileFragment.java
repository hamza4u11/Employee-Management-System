package com.hamza.employeemangementsystem.ui.view.fragment;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.local.AppDatabaseHelper;
import com.hamza.employeemangementsystem.data.database.local.SQLiteLocalDataSource;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.ui.adopter.myAdapter.EmployeeClickHandler;
import com.hamza.employeemangementsystem.ui.adopter.myAdapter.myAdapter;
import com.hamza.employeemangementsystem.ui.viewmodel.DashboardViewModel;
import com.hamza.employeemangementsystem.ui.viewmodel.SelectProfileViewModel;
import com.hamza.employeemangementsystem.utils.DateTimeUtlis;
import com.hamza.employeemangementsystem.utils.DynEditTextDateTimePicker;

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
    private static final String ARG_PARAM3 = "title";

    private SelectProfileViewModel selectProfileViewModel;
    private DashboardViewModel dashboardViewModel; ;
    Button addBtn ;
    LinearLayout startDateEndDateLayout, header;
    EditText etStartDate, etEndDate;
    TextView titleTxt, txtName;
    String startDate, endDate;
    ImageView imageView;

    public interface MyOnClickListener{
        void OnItemClick(Employee employee);
        void OnAddClick();
        void onItemClickWithDate(Employee employee, String startDate, String endDate);

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
    private String title;


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
    public static SelectProfileFragment newInstance(String modeParam, String managerIdParam, String title) {
        SelectProfileFragment fragment = new SelectProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, modeParam);
        args.putString(ARG_PARAM2, managerIdParam);
        args.putString(ARG_PARAM3, title);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modeParam = getArguments().getString(ARG_PARAM1);
            managerIdParam = getArguments().getString(ARG_PARAM2);
            title = getArguments().getString(ARG_PARAM3);

        }
//        SQLiteLocalDataSource<Employee> sqLiteLocalDataSource = new SQLiteLocalDataSource<>(getActivity());
        AppDatabaseHelper<Employee> employeeAppDatabaseHelper = new AppDatabaseHelper<>(getActivity());
        selectProfileViewModel = new SelectProfileViewModel(employeeAppDatabaseHelper,getContext());
        AppDatabaseHelper<Attendance> attendanceAppDatabaseHelper = new AppDatabaseHelper<>(getActivity());
        dashboardViewModel = new DashboardViewModel(attendanceAppDatabaseHelper);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_select_profile, container, false);
        addBtn= view.findViewById(R.id.addBtn);
        startDateEndDateLayout= view.findViewById(R.id.startDateEndDateLayout);
        header = view.findViewById(R.id.header);
        header.setVisibility(View.GONE);
        etStartDate = view.findViewById(R.id.etStartDate);
        etEndDate = view.findViewById(R.id.etEndDate);
        startDate= DateTimeUtlis.getShared().todayDate().toString();
        endDate = DateTimeUtlis.getShared().todayDate().toString();
        imageView= view.findViewById(R.id.imageView);
        titleTxt = view.findViewById(R.id.titleTxt);
        titleTxt.setText(title);
        txtName= view.findViewById(R.id.txtName);
        txtName.setText(Globals.getShared().getEmployee()  == null ? " " :  Globals.getShared().getEmployee().name );
        addBtn.setVisibility(view.GONE);
        if(Objects.equals(modeParam, "add")) {
            addBtn.setVisibility(view.VISIBLE);
        }
        if(Objects.equals(modeParam, "add") || Objects.equals(modeParam,"edit") || Objects.equals(modeParam, "report") && Objects.equals(managerIdParam, null) || !Objects.equals(managerIdParam, null)) {
            header.setVisibility(view.VISIBLE);
            imageView.setVisibility(View.GONE);
        }
        if(Objects.equals(modeParam, "report")) {
            startDateEndDateLayout.setVisibility(view.VISIBLE);
            DynEditTextDateTimePicker  dynEditTextDateTimePickerStartDate =new DynEditTextDateTimePicker(getContext(),etStartDate,startDate);
            dynEditTextDateTimePickerStartDate.setOnlyDate(true);
            DynEditTextDateTimePicker  dynEditTextDateTimePickerEndDate =new DynEditTextDateTimePicker(getContext(),etEndDate,endDate);
            dynEditTextDateTimePickerEndDate.setOnlyDate(true);
        }

        RecyclerView selectProfile = view.findViewById(R.id.selectProfile);
        EmployeeClickHandler employeeClickHandler = new EmployeeClickHandler() {
            @Override
            public void onItemClick(Employee employee) {
                startDate = etStartDate.getText().toString();
                endDate = etEndDate.getText().toString();
                Log.d("Start", startDate);
                Log.d("End", endDate);


                if(listener!=null){
                    if (Objects.equals(modeParam,"report")){
                        listener.onItemClickWithDate(employee,startDate,endDate);

                    }else {
                        listener.OnItemClick(employee);
                    }


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
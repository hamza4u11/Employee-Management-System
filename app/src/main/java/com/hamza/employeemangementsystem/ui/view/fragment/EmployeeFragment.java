package com.hamza.employeemangementsystem.ui.view.fragment;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.core.DataSourceMode;
import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DbHandler;
import com.hamza.employeemangementsystem.data.database.local.AppDatabaseHelper;
import com.hamza.employeemangementsystem.data.database.local.SQLiteLocalDataSource;
import com.hamza.employeemangementsystem.data.database.remote.RemoteDataSourceClass;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp;
import com.hamza.employeemangementsystem.ui.EmployeeConverter;
import com.hamza.employeemangementsystem.ui.viewmodel.EmployeeViewModel;
import com.hamza.employeemangementsystem.ui.viewmodel.SelectProfileViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeFragment extends Fragment {
    private EmployeeViewModel employeeViewModel;
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "employeeId";
    TextView addEditEmployee, txtName, selectManager;
    Button updateBtn, cancelBtn;
    EditText etName, etDesignation, etPhoneNo, etPin, etAddress, etPaymentType, etAllowHoliday, etOverTimeAllow, etStatus;
    SelectProfileViewModel selectProfileViewModel;
    Spinner spinner;
    ProgressBar loader;


    private int selectedManagerId = 0;

    public interface OnEventClickListener {
        void OnBackClick();
    }

    private OnEventClickListener listener;

    public OnEventClickListener getListener() {
        return listener;
    }

    public void setListener(OnEventClickListener listener) {
        this.listener = listener;
    }


    // TODO: Rename and change types of parameters
    private String mode;
    private String employeeId;

    public EmployeeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mode    Parameter 1.
     * @param mParam2 Parameter 2.
     * @return A new instance of fragment EmployyeeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeeFragment newInstance(String mode, String mParam2) {
        EmployeeFragment fragment = new EmployeeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mode);
        args.putString(ARG_PARAM2, mParam2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mode = getArguments().getString(ARG_PARAM1);
            employeeId = getArguments().getString(ARG_PARAM2);
        }
        AppDatabaseHelper<Employee> employeeAppDatabaseHelper = new AppDatabaseHelper<>(getActivity());
        SQLiteLocalDataSource<Employee> sqLiteLocalDataSource = new SQLiteLocalDataSource<>(employeeAppDatabaseHelper,getActivity());
        RemoteDataSourceClass<Employee> remoteDataSource = new RemoteDataSourceClass<>();
        IConvertHelper<Employee> convertHelper = new EmployeeConverter();
        DbHandler<Employee> dbHandler = new DbHandler<>(sqLiteLocalDataSource,remoteDataSource,convertHelper, DataSourceMode.REMOTE_ONLY);
        EmployeeRepositoryImp employeeRepository = new EmployeeRepositoryImp(dbHandler, getContext());
        selectProfileViewModel = new SelectProfileViewModel(employeeRepository,getContext());
        employeeViewModel = new EmployeeViewModel(employeeRepository, employeeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee, container, false);
        addEditEmployee = view.findViewById(R.id.addEditEmployee);
        txtName = view.findViewById(R.id.txtName);
        txtName.setText(Globals.getShared().getEmployee().name);
        updateBtn = view.findViewById(R.id.updateBtn);
        etName = view.findViewById(R.id.name);
        etDesignation = view.findViewById(R.id.designation);
        etPhoneNo = view.findViewById(R.id.phoneNo);
        etAddress = view.findViewById(R.id.address);
        etPaymentType = view.findViewById(R.id.paymentType);
        etAllowHoliday = view.findViewById(R.id.allowHolidays);
        etPin = view.findViewById(R.id.pin);
        etOverTimeAllow = view.findViewById(R.id.allowOverTime);
        etStatus = view.findViewById(R.id.status);
        selectManager = view.findViewById(R.id.selectManager);
        spinner = view.findViewById(R.id.spinner);
        cancelBtn = view.findViewById(R.id.cancelBtn);
        loader =  view.findViewById(R.id.pBar);
        loadScreenData();
        employeeViewModel.getIsLoading().observe(getActivity(), isLoading -> {
            if (isLoading) {
                loader.setVisibility(View.VISIBLE);
            } else {
                loader.setVisibility(View.GONE);
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    employeeViewModel.setName(etName.getText().toString());
                    employeeViewModel.setDesignation(etDesignation.getText().toString());
                    employeeViewModel.setPhoneNo(etPhoneNo.getText().toString());
                    employeeViewModel.setAddress(etAddress.getText().toString());
                    employeeViewModel.setPaymentType(etPaymentType.getText().toString());
                    employeeViewModel.setAllowHoliday(etAllowHoliday.getText().toString());
                    employeeViewModel.setAllowOverTime(etOverTimeAllow.getText().toString());
                    employeeViewModel.setStatus(etStatus.getText().toString());
                    employeeViewModel.setPin(etPin.getText().toString());
                    selectedManagerId = Objects.equals(mode, "add") ? selectedManagerId : Integer.parseInt(employeeViewModel.getManagerId());
                    employeeViewModel.setManagerId(String.valueOf(selectedManagerId));
                    employeeViewModel.updateEmployee();
                    if (listener != null) {
                        listener.OnBackClick();
                    }
                }catch (Exception e){
                    showErrorMessage(e.getMessage());
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnBackClick();
                }
            }
        });
        employeeViewModel.getEmployee().observe(getViewLifecycleOwner(), employee -> {
            if(employeeViewModel.getEmployee() != null){
               // txtName.setText(employee.getName());
            }
        });
        return view;
    }
    private void loadScreenData() {
        addEditEmployee.setText(Objects.equals(mode, "add") ? "Add Employee" : "Edit Employee");
        etName.setText(employeeViewModel.getName());
        etDesignation.setText(employeeViewModel.getDesignation());
        etPhoneNo.setText(employeeViewModel.getPhoneNo());
        etAddress.setText(employeeViewModel.getAddress());
        etStatus.setText(employeeViewModel.getStatus());
        etPaymentType.setText(employeeViewModel.getPaymentType());
        etAllowHoliday.setText(employeeViewModel.getAllowHoliday());
        etOverTimeAllow.setText(employeeViewModel.getOverTimeAllow());
        selectManager.setVisibility(
                Objects.equals(mode, "add") ? View.VISIBLE : View.GONE
        );
        spinner.setVisibility(
                Objects.equals(mode, "add") ? View.VISIBLE : View.GONE
        );
        etPin.setText(employeeViewModel.getPin());
        setupManagerSpinner();
        formValidations();
        if(employeeId!= null){
            ifManagerLogin();
        }
    }

    private void setupManagerSpinner() {
        Employee selectManager = new Employee();
        selectManager.setId(0);
        selectManager.setName("Select Manager");
        ArrayAdapter<Employee> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                new ArrayList<>()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        employeeViewModel.getManagers().observe(getViewLifecycleOwner(), employees -> {
            List<Employee> list = new ArrayList<>();
            list.add(selectManager);
//            if(employees !=null) {
                list.addAll(employees);
//            }
            adapter.clear();
            adapter.addAll(list);
            adapter.notifyDataSetChanged();
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Employee selected = (Employee) parent.getItemAtPosition(position);

                selectedManagerId =
                        (selected != null && selected.getId() != 0)
                                ? selected.getId()
                                : 0;
                Log.d("Spinner", "Selected Manager ID = " + selectedManagerId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedManagerId = 0;
            }
        });
    }
    private void formValidations() {

        etName.setOnFocusChangeListener((v, hasFocus) -> {
            try{
                if (!hasFocus) {
                    employeeViewModel.setName(etName.getText().toString());
                }
            }catch (Exception e){
                showErrorMessage(e.getMessage());
                etName.setError(e.getMessage());
            }
        });

        etDesignation.setOnFocusChangeListener((v, hasFocus) -> {
            try{
                if (!hasFocus) {
                    employeeViewModel.setDesignation(etDesignation.getText().toString());
                }
            }catch (Exception e){
                showErrorMessage(e.getMessage());
                etDesignation.setError(e.getMessage());

            }
        });
        etPhoneNo.setOnFocusChangeListener((v, hasFocus) -> {
            try{
                if (!hasFocus) {
                    employeeViewModel.setPhoneNo(etPhoneNo.getText().toString());
                }
            }catch (Exception e){
                showErrorMessage(e.getMessage());
                etPhoneNo.setError(e.getMessage());

            }
        });
        etAddress.setOnFocusChangeListener((v, hasFocus) -> {
            try{
                if (!hasFocus) {
                    employeeViewModel.setAddress(etAddress.getText().toString());
                }
            }catch (Exception e){
                showErrorMessage(e.getMessage());
                etAddress.setError(e.getMessage());

            }
        });
        etStatus.setOnFocusChangeListener((v, hasFocus) -> {
            try{
                if (!hasFocus) {
                    employeeViewModel.setStatus(etStatus.getText().toString());
                }
            }catch (Exception e){
                showErrorMessage(e.getMessage());
                etStatus.setError(e.getMessage());

            }
        });

        etPaymentType.setOnFocusChangeListener((v, hasFocus) -> {
            try{
                if (!hasFocus) {
                    employeeViewModel.setPaymentType(etPaymentType.getText().toString());
                }
            }catch (Exception e){
                showErrorMessage(e.getMessage());
                etPaymentType.setError(e.getMessage());

            }
        });


        etPin.setOnFocusChangeListener((v, hasFocus) -> {
            try{
                if (!hasFocus) {
                    employeeViewModel.setPin(etPin.getText().toString());
                }
            }catch (Exception e){
                showErrorMessage(e.getMessage());
                etPin.setError(e.getMessage());
            }
        });
    }

    private void showErrorMessage(String message) {
        Toast.makeText(requireActivity(), message, LENGTH_SHORT).show();
    }
    private void ifManagerLogin(){
        etName.setText(employeeViewModel.getName());
        etDesignation.setText(employeeViewModel.getDesignation());
        etPhoneNo.setText(employeeViewModel.getPhoneNo());
        etAddress.setText(employeeViewModel.getAddress());
        etStatus.setText(employeeViewModel.getStatus());
        etPaymentType.setText(employeeViewModel.getPaymentType());
        etAllowHoliday.setText(employeeViewModel.getAllowHoliday());
        etOverTimeAllow.setText(employeeViewModel.getOverTimeAllow());
        selectManager.setVisibility(
                Objects.equals(mode, "add") ? View.VISIBLE : View.GONE
        );
        spinner.setVisibility(
                Objects.equals(mode, "add") ? View.VISIBLE : View.GONE
        );
        etPin.setText(employeeViewModel.getPin());
        addEditEmployee.setText(employeeId!=null && !employeeId.isEmpty() ? "Employee Details":null);
//        updateBtn.setVisibility(View.GONE);
    }

}

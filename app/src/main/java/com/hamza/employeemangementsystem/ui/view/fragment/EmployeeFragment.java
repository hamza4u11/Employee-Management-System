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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.ui.view.MainActivity;
import com.hamza.employeemangementsystem.ui.viewmodel.EmployeeViewModel;
import com.hamza.employeemangementsystem.ui.viewmodel.SelectProfileViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeFragment extends Fragment {
    private EmployeeViewModel viewModel;
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "employeeId";
    TextView addEditEmployee, txtName, selectManager;
    Button updateBtn, cancelBtn;
    EditText etName, etDesignation, etPhoneNo, etPin, etAddress, etPaymentType, etAllowHoliday, etOverTimeAllow, etStatus;
    SelectProfileViewModel selectProfileViewModel;
    Spinner spinner;
    Employee selectManagerId;

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
        DBHandler<Employee> employeeDBHandler = new DBHandler<>(getActivity());
        selectProfileViewModel = new SelectProfileViewModel(employeeDBHandler);
        //DBHandler<Attendance> attendanceDBHandler = new DBHandler<>(getActivity());
        viewModel = new EmployeeViewModel(employeeDBHandler, employeeId);
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

        loadScreenData();
//        if (Objects.equals(mode, "edit") && employeeId!=null){
//            List<EditText> editTextList = Arrays.asList(
//                    etName, etDesignation, etPhoneNo, etPin, etAddress, etPaymentType, etAllowHoliday, etOverTimeAllow, etStatus
//            );
//            for (EditText et : editTextList) {
//                et.setFocusable(false);
//                et.setFocusableInTouchMode(false);
//                et.setClickable(true);
//            }
//        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    viewModel.setName(etName.getText().toString());
                    viewModel.setDesignation(etDesignation.getText().toString());
                    viewModel.setPhoneNo(etPhoneNo.getText().toString());
                    viewModel.setAddress(etAddress.getText().toString());
                    viewModel.setPaymentType(etPaymentType.getText().toString());
                    viewModel.setAllowHoliday(etAllowHoliday.getText().toString());
                    viewModel.setAllowOverTime(etOverTimeAllow.getText().toString());
                    viewModel.setStatus(etStatus.getText().toString());
                    viewModel.setPin(etPin.getText().toString());
                    selectedManagerId = Objects.equals(mode, "add") ? selectedManagerId : Integer.parseInt(viewModel.getManagerId());
                    viewModel.setManagerId(String.valueOf(selectedManagerId));
                    viewModel.updateEmployee();
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
        return view;
    }

    private void loadScreenData() {
        addEditEmployee.setText(Objects.equals(mode, "add") ? "Add Employee" : "Edit Employee");
        etName.setText(viewModel.getName());
        etDesignation.setText(viewModel.getDesignation());
        etPhoneNo.setText(viewModel.getPhoneNo());
        etAddress.setText(viewModel.getAddress());
        etStatus.setText(viewModel.getStatus());
        etPaymentType.setText(viewModel.getPaymentType());
        etAllowHoliday.setText(viewModel.getAllowHoliday());
        etOverTimeAllow.setText(viewModel.getOverTimeAllow());
        selectManager.setVisibility(
                Objects.equals(mode, "add") ? View.VISIBLE : View.GONE
        );
        spinner.setVisibility(
                Objects.equals(mode, "add") ? View.VISIBLE : View.GONE
        );
        etPin.setText(viewModel.getPin());
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
        viewModel.getManagers().observe(getViewLifecycleOwner(), employees -> {
            List<Employee> list = new ArrayList<>();
            list.add(selectManager);
            list.addAll(employees);

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
                    viewModel.setName(etName.getText().toString());
                }
            }catch (Exception e){
                showErrorMessage(e.getMessage());
                etName.setError(e.getMessage());
              //  etName.requestFocus();

            }
        });

        etDesignation.setOnFocusChangeListener((v, hasFocus) -> {
            try{
                if (!hasFocus) {
                    viewModel.setDesignation(etDesignation.getText().toString());
                }
            }catch (Exception e){
                showErrorMessage(e.getMessage());
                etDesignation.setError(e.getMessage());

            }
        });
        etPhoneNo.setOnFocusChangeListener((v, hasFocus) -> {
            try{
                if (!hasFocus) {
                    viewModel.setPhoneNo(etPhoneNo.getText().toString());
                }
            }catch (Exception e){
                showErrorMessage(e.getMessage());
                etPhoneNo.setError(e.getMessage());

            }
        });
        etAddress.setOnFocusChangeListener((v, hasFocus) -> {
            try{
                if (!hasFocus) {
                    viewModel.setAddress(etAddress.getText().toString());
                }
            }catch (Exception e){
                showErrorMessage(e.getMessage());
                etAddress.setError(e.getMessage());

            }
        });
        etStatus.setOnFocusChangeListener((v, hasFocus) -> {
            try{
                if (!hasFocus) {
                    viewModel.setStatus(etStatus.getText().toString());
                }
            }catch (Exception e){
                showErrorMessage(e.getMessage());
                etStatus.setError(e.getMessage());

            }
        });

        etPaymentType.setOnFocusChangeListener((v, hasFocus) -> {
            try{
                if (!hasFocus) {
                    viewModel.setPaymentType(etPaymentType.getText().toString());
                }
            }catch (Exception e){
                showErrorMessage(e.getMessage());
                etPaymentType.setError(e.getMessage());

            }
        });


        etPin.setOnFocusChangeListener((v, hasFocus) -> {
            try{
                if (!hasFocus) {
                    viewModel.setPin(etPin.getText().toString());
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
        etName.setText(viewModel.getName());
        etDesignation.setText(viewModel.getDesignation());
        etPhoneNo.setText(viewModel.getPhoneNo());
        etAddress.setText(viewModel.getAddress());
        etStatus.setText(viewModel.getStatus());
        etPaymentType.setText(viewModel.getPaymentType());
        etAllowHoliday.setText(viewModel.getAllowHoliday());
        etOverTimeAllow.setText(viewModel.getOverTimeAllow());
        selectManager.setVisibility(
                Objects.equals(mode, "add") ? View.VISIBLE : View.GONE
        );
        spinner.setVisibility(
                Objects.equals(mode, "add") ? View.VISIBLE : View.GONE
        );
        etPin.setText(viewModel.getPin());
        addEditEmployee.setText(employeeId!=null && !employeeId.isEmpty() ? "Employee Details":null);
//        updateBtn.setVisibility(View.GONE);



    }


}

package com.hamza.employeemangementsystem.ui.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

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

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.ui.viewmodel.AttendanceViewModel;
import com.hamza.employeemangementsystem.ui.viewmodel.EmployeeViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeFragment extends Fragment {
    private AttendanceViewModel attendanceViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "addOrEditParam";
    private static final String ARG_PARAM2 = "editParam";
   TextView addEmployee,txtName, selectManager;
   Button saveBtn, updateBtn;
   EditText name ,designation, phoneNo,pin, address, paymentType, allowHolidays, allowOverTime, status;
   EmployeeViewModel employeeViewModel;
   Spinner spinner;
   public interface OnEventClickListener{
       void OnAddEmployeeClick();
       void OnUpdateEmployeeClick();

    }
    private  OnEventClickListener listener;

    public OnEventClickListener getListener() {
        return listener;
    }

    public void setListener(OnEventClickListener listener) {
        this.listener = listener;
    }




    // TODO: Rename and change types of parameters
    private String addOrEditParam;
    private String editParam;

    public EmployeeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param addParam Parameter 1.
     * @param editParam Parameter 2.
     * @return A new instance of fragment EmployyeeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeeFragment newInstance(String addParam, String editParam) {
        EmployeeFragment fragment = new EmployeeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, addParam);
        args.putString(ARG_PARAM2, editParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            addOrEditParam = getArguments().getString(ARG_PARAM1);
            editParam = getArguments().getString(ARG_PARAM2);
        }
        DBHandler<Employee>  employeeDBHandler = new DBHandler<>(getActivity());
        employeeViewModel = new EmployeeViewModel(employeeDBHandler);
        DBHandler<Attendance> attendanceDBHandler = new DBHandler<>(getActivity());
        attendanceViewModel = new AttendanceViewModel(attendanceDBHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee, container, false);


        addEmployee= view.findViewById(R.id.addEmployee);
        txtName= view.findViewById(R.id.txtName);
        saveBtn = view.findViewById(R.id.saveBtn);
        updateBtn = view.findViewById(R.id.updateBtn);
        name= view.findViewById(R.id.name);
        designation = view.findViewById(R.id.designation);
        phoneNo = view.findViewById(R.id.phoneNo);
        address = view.findViewById(R.id.address);
        paymentType = view.findViewById(R.id.paymentType);
        allowHolidays=view.findViewById(R.id.allowHolidays);
        pin = view.findViewById(R.id.pin);
        allowOverTime = view.findViewById(R.id.allowOverTime);
        status = view.findViewById(R.id.status);
        selectManager= view.findViewById(R.id.selectManager);
        spinner= view.findViewById(R.id.spinner);
        refresh();
        setSpinner();
        ifUpdateSetText();
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                if(listener != null){
//                    listener.OnAddEmployeeClick();
//                    Toast.makeText(getActivity(),"On ADD Employee Click Listener:", Toast.LENGTH_SHORT).show();
//                    Log.d("onClick: ", "Listener");
//                }
//                Log.d("onClick: ", "Save Button Function");
                Employee emp= new Employee();
                emp.name = name.getText().toString();
                emp.designation = designation.getText().toString();
                emp.phone_no = phoneNo.getText().toString();
                emp.address = address.getText().toString();
                emp.paymentType= paymentType.getText().toString();
                emp.allowHoliday = allowHolidays.getText().toString();
                emp.overTimeAllow = allowOverTime.getText().toString();
                emp.status= status.getText().toString();
                emp.pin = pin.getText().toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    emp.checkIn = LocalDateTime.now().toString();
                }
                employeeViewModel.saveEmployee(emp);
                SelectProfileFragment profileFragment = SelectProfileFragment.newInstance("add", null);
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, profileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener (){

            @Override
            public void onClick(View v) {
//                if(listener != null){
//                    listener.OnUpdateEmployeeClick();
//
//                }
                Employee emp= new Employee();
                emp.name = name.getText().toString();
                emp.designation = designation.getText().toString();
                emp.phone_no = phoneNo.getText().toString();
                emp.address = address.getText().toString();
                emp.paymentType= paymentType.getText().toString();
                emp.allowHoliday = allowHolidays.getText().toString();
                emp.overTimeAllow = allowOverTime.getText().toString();
                emp.status= status.getText().toString();
                emp.pin= pin.getText().toString() ;
                employeeViewModel.updateEmployee(emp);
//                if(listener!=null){
//                    listener.OnManageEmployeesClick(addBtnForAdmin, adminOrManager);
//                }
                SelectProfileFragment profileFragment = SelectProfileFragment.newInstance("add", null);
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, profileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
            private void refresh(){
                addEmployee.setVisibility(View.GONE);
                updateBtn.setVisibility(View.GONE);
                Employee loginEmployee= Globals.getShared().getEmployee();
                String name = Globals.getShared().getEmployee().name;
                txtName.setText(name);
                String employee = Objects.equals(addOrEditParam, "add") ? "Add" :"Edit";
                addEmployee.setText(employee+" Employee" );
                addEmployee.setVisibility(View.VISIBLE);
                saveBtn.setVisibility(
                        Objects.equals(addOrEditParam, "add") ? View.VISIBLE : View.GONE

                );

                updateBtn.setVisibility(
                        Objects.equals(addOrEditParam, "add") ? View.GONE : View.VISIBLE
                );
                selectManager.setVisibility(
                        Objects.equals(addOrEditParam, "add") ? View.VISIBLE : View.GONE

                );
                spinner.setVisibility(
                        Objects.equals(addOrEditParam, "add") ? View.VISIBLE : View.GONE

                );
            }
            private void setSpinner (){
                Employee selectManager = new Employee();
                selectManager.setId(-1);
                selectManager.setName("Select Manager");
                ArrayAdapter<Employee> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        new ArrayList<>()
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                employeeViewModel.getManagers().observe(getViewLifecycleOwner(), employees -> {
                    List<Employee> spinnerList = new ArrayList<>();
                    spinnerList.add(selectManager);
                    spinnerList.addAll(employees);
                    adapter.clear();
                    adapter.addAll(spinnerList);
                    adapter.notifyDataSetChanged();
                });
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Employee selected = (Employee) parent.getItemAtPosition(position);

                        if (selected.getId() == -1) {
                            // "Select Manager" selected → do nothing
                            return;
                        }

                        // ✅ Real manager selected
                        Log.d("Spinner", "Manager: " + selected.getName());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });


            }
            private void ifUpdateSetText(){
                if (!Objects.equals(addOrEditParam, "add")){
                    Employee emp =employeeViewModel.getEmployeeById(addOrEditParam);
                    name.setText(emp.name);
                    designation.setText(emp.designation);
                    phoneNo.setText(emp.phone_no);
                    address.setText(emp.address);
                    status.setText(emp.status);
                    paymentType.setText(emp.paymentType);
                    allowHolidays.setText(emp.allowHoliday);
                    allowOverTime.setText(emp.overTimeAllow);
                    pin.setText(emp.pin);
                }
            }

}
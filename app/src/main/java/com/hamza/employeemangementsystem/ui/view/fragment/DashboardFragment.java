package com.hamza.employeemangementsystem.ui.view.fragment;



import static androidx.constraintlayout.widget.ConstraintSet.VISIBLE;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.ui.viewmodel.AttendanceViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AttendanceViewModel attendanceViewModel;

    TextView seesion, label, status;
    LinearLayout hideAdminButtons ;
    Button checkInButton, checkOutButton, logoutBtn, manageEmployeesBtn ;





    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        DBHandler<Attendance> attendanceDBHandler = new DBHandler<>(getActivity());
        attendanceViewModel = new AttendanceViewModel(attendanceDBHandler);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        seesion = view.findViewById(R.id.seesion);
        label = view.findViewById(R.id.label);
        status = view.findViewById(R.id.status);
        hideAdminButtons = view.findViewById(R.id.hideAdminButtons);
        checkInButton = view.findViewById(R.id.checkInButton);
        checkOutButton = view.findViewById(R.id.checkOutbutton);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        manageEmployeesBtn= view.findViewById(R.id.manageEmployeesBtn);




        Employee loginEmployee= Globals.getShared().getEmployee();
        String loginEmployeeId = String.valueOf(loginEmployee.id);
        refresh();
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceViewModel.checkIn(loginEmployeeId);
                refresh();
            }
        });
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceViewModel.checkOut(loginEmployeeId);
                refresh();
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceViewModel.logout();
                attendanceViewModel.openSelectProfile().observe(getViewLifecycleOwner(), open -> {
                if (open != null && open) {
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, new SelectProfileFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });
            }
        });
        manageEmployeesBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               String adminOrManager= attendanceViewModel.getIsAdmin() ? null : loginEmployeeId;
               String addBtnForAdmin = attendanceViewModel.getIsAdmin() ? "add" : null;
                SelectProfileFragment profileFragment = SelectProfileFragment.newInstance(addBtnForAdmin, adminOrManager);
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
        Employee loginEmployee= Globals.getShared().getEmployee();
        String loginEmployeeId = String.valueOf(loginEmployee.id);
        attendanceViewModel.loadUserStatus(loginEmployeeId);
        status.setText(attendanceViewModel.getStatusText());
        seesion.setText(attendanceViewModel.getSeesionText());
        label.setText(attendanceViewModel.getCheckInOutText());
        hideAdminButtons.setVisibility(View.GONE);
        checkInButton.setVisibility(View.GONE);
        checkOutButton.setVisibility(View.GONE);
        checkOutButton.setVisibility(attendanceViewModel.getIfUserCheckedIn() ? View.VISIBLE : View.GONE);
        checkInButton.setVisibility(attendanceViewModel.getIfUserCheckedIn()? View.GONE : View.VISIBLE);
        hideAdminButtons.setVisibility(attendanceViewModel.isLayoutEnabled()? View.VISIBLE: View.GONE);
    }


}
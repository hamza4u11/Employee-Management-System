package com.hamza.employeemangementsystem.ui.view.fragment;



import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.ui.viewmodel.DashboardViewModel;

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

    private DashboardViewModel dashboardViewModel;

    TextView txtName,seesion, label, status ,seeionLabel;
    LinearLayout hideAdminButtons ;
    Button checkInButton, checkOutButton, logoutBtn, manageEmployeesBtn ;

    public interface  OnEventClickListener{
       void OnManageEmployeesClick( String adminOrManager,  String addBtnForAdmin);
       void OnReportsClick();
       void OnLogoutClick();
    }
    private OnEventClickListener listener;

    public OnEventClickListener getListener() {

        return listener;
    }

    public void setListener(OnEventClickListener listener) {

        this.listener = listener;
    }

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
        dashboardViewModel = new DashboardViewModel(attendanceDBHandler);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        txtName = view.findViewById(R.id.txtName);
        seesion = view.findViewById(R.id.seesion);
        label = view.findViewById(R.id.label);
        status = view.findViewById(R.id.status);
        seeionLabel = view.findViewById(R.id.seeionLabel);
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
                dashboardViewModel.checkIn(loginEmployeeId);
                refresh();
            }
        });
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardViewModel.checkOut(loginEmployeeId);
                refresh();
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardViewModel.logout();
                if(listener!=null){
                    listener.OnLogoutClick();
                }


            }
        });
        String addBtnForAdmin = dashboardViewModel.getIsAdmin() ? "add" : null;
        String adminOrManager= dashboardViewModel.getIsAdmin() ? null : loginEmployeeId;
        manageEmployeesBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.OnManageEmployeesClick(addBtnForAdmin,adminOrManager);
                }
            }
        });



        return view;
    }
    private void refresh(){
        Employee loginEmployee= Globals.getShared().getEmployee();
        String name = Globals.getShared().getEmployee().name;
        String loginEmployeeId = String.valueOf(loginEmployee.id);
        txtName.setText(name);
        dashboardViewModel.loadUserStatus(loginEmployeeId);
        status.setText(dashboardViewModel.getStatusText());
        seesion.setText(dashboardViewModel.getSeesionText());
        seeionLabel.setText(dashboardViewModel.getSeesionLabel());
        label.setText(dashboardViewModel.getCheckInOutText());
        hideAdminButtons.setVisibility(View.GONE);
        checkInButton.setVisibility(View.GONE);
        checkOutButton.setVisibility(View.GONE);
        checkOutButton.setVisibility(dashboardViewModel.getIfUserCheckedIn() ? View.VISIBLE : View.GONE);
        checkInButton.setVisibility(dashboardViewModel.getIfUserCheckedIn()? View.GONE : View.VISIBLE);
        hideAdminButtons.setVisibility(dashboardViewModel.isLayoutEnabled()? View.VISIBLE: View.GONE);

    }


}
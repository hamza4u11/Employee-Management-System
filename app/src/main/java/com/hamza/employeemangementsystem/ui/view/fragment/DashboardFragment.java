package com.hamza.employeemangementsystem.ui.view.fragment;


import static com.hamza.employeemangementsystem.utils.DateTimeUtlis.calculateDurationBetween;
import static com.hamza.employeemangementsystem.utils.DateTimeUtlis.calculateTimeFromNow;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;
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
import com.hamza.employeemangementsystem.utils.DateTimeUtlis;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    Button manageEmployees, attendenceReports;
    Object values;
    private AttendanceViewModel attendanceViewModel;
    ;


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
        DBHandler<Employee> attendanceDBHandler = new DBHandler<>(getActivity());
        attendanceViewModel = new AttendanceViewModel(attendanceDBHandler);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        manageEmployees = view.findViewById(R.id.manageEmployees);
        LinearLayout hideAdminButtons = view.findViewById(R.id.hideAdminButtons);
        Button checkInButton = view.findViewById(R.id.checkInButton);
        Button checkOutButton = view.findViewById(R.id.checkOutbutton);
        TextView seesion = view.findViewById(R.id.seesion);
        String id = getArguments().getString("id");
        TextView status = view.findViewById(R.id.status);
        String checkInTime = getArguments().getString("checkInTime");
        String checkOutTime = getArguments().getString("checkOutTime");
        List<Attendance> isCheckedIn = attendanceViewModel.isCheckedIn(id);


        if (isCheckedIn.get(0).checkInTime != null && isCheckedIn.get(0).checkOutTime != null) {
            Date date = DateTimeUtlis.convertStringToDateTime(checkOutTime);
            String format = Globals.shared.getDateTimeFormat();
            if (date != null) {
                SimpleDateFormat output =
                        new SimpleDateFormat(format, Locale.getDefault());

                String formatted = output.format(date);
                status.setText("CheckOut Time " + formatted);
            }
            String duration = calculateDurationBetween(checkInTime, checkOutTime);
            seesion.setText("Seesion Time" + duration);
            checkOutButton.setEnabled(false);


        } else if (isCheckedIn.get(0).checkInTime != null && isCheckedIn.get(0).checkOutTime == null) {
            Date date = DateTimeUtlis.convertStringToDateTime(checkInTime);
            String format = Globals.shared.getDateTimeFormat();
            if (date != null) {
                SimpleDateFormat output =
                        new SimpleDateFormat(format, Locale.getDefault());

                String formatted = output.format(date);
                status.setText("CheckIn Time " + formatted);
            }
            String duration = calculateTimeFromNow(checkInTime);
            seesion.setText("Seesion Time" + duration);

            Log.d("checkInTime", " " + checkInTime);

            checkInButton.setVisibility(View.GONE);
        } else {
            status.setText("Status:");
        }


        if (getArguments() != null) {

            String designation = getArguments().getString("designation");
            String name = getArguments().getString("name");
            TextView txtName = view.findViewById(R.id.txtName);
            txtName.setText(name);
            if ("Admin".equalsIgnoreCase(designation)) {
                Toast.makeText(getActivity(),
                        "Welcome Admin", Toast.LENGTH_SHORT).show();
            } else if ("Manager".equalsIgnoreCase(designation)) {
                Toast.makeText(getActivity(),
                        "Welcome Manager", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),
                        "Welcome Employee   ", Toast.LENGTH_SHORT).show();
                hideAdminButtons.setVisibility(View.GONE); // or INVISIBLE
            }
        }
        manageEmployees = view.findViewById(R.id.manageEmployees);
        manageEmployees.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new ManageEmployeesFragment();

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String id = getArguments().getString("id");
                Log.d("ID from CheckInn BUtton", "" + id);
                List<Attendance> isCheckedIn = attendanceViewModel.isCheckedIn(id);
                String checkInTime = isCheckedIn.get(0).checkInTime;
                String checkOutTime = isCheckedIn.get(0).checkOutTime;
            }
        });
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Attendance> isCheckedIn = attendanceViewModel.isCheckedIn(id);
                checkOutButton.setVisibility(View.GONE);



            }
        });


        return view;
    }

}
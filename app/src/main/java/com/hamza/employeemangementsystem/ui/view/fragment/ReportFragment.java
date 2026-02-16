package com.hamza.employeemangementsystem.ui.view.fragment;

import android.os.Bundle;

import androidx.annotation.StringDef;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.model.Report;
import com.hamza.employeemangementsystem.ui.adopter.myAdapter.EmployeeClickHandler;
import com.hamza.employeemangementsystem.ui.adopter.myAdapter.ListAdapter;
import com.hamza.employeemangementsystem.ui.adopter.myAdapter.ReportAdapter;
import com.hamza.employeemangementsystem.ui.adopter.myAdapter.myAdapter;
import com.hamza.employeemangementsystem.ui.viewmodel.ReportViewModel;

import org.w3c.dom.Text;

import java.sql.ClientInfoStatus;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "startDate";
    private static final String ARG_PARAM2 = "endDate";
    private static final String ARG_PARAM3 = "employeeId";
    private static final String ARG_PARAM4 = "loginEmployeeId";
    private static final String ARG_PARAM5 = "attendanceCriteria";
    RecyclerView ReportRecyclerView;
    ReportAdapter adapter;
    ReportViewModel reportViewModel;
    TextView title,txtName;





    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;

    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2, String param3, String param4, String param5) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);

        }
        DBHandler<Attendance> dbHandler = new DBHandler<>(getActivity());
         reportViewModel = new ReportViewModel(dbHandler,mParam1,mParam2, mParam3, mParam4);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        title = view.findViewById(R.id.title);
        title.setText(mParam5);
        txtName= view.findViewById(R.id.txtName);
        txtName.setText(Globals.getShared().getEmployee().name);
        ReportRecyclerView = view.findViewById(R.id.reportsRecyclerView);
        ReportAdapter adapter = new ReportAdapter(getContext());
        ReportRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ReportRecyclerView.setAdapter(adapter);
        reportViewModel.getAllReports().observe(getActivity(), reports -> {
            adapter.setList(reports);
        });
        return view;


    }


}
package com.hamza.employeemangementsystem.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Report;
import com.hamza.employeemangementsystem.data.repository.AttendanceRepositoryImp;
import com.hamza.employeemangementsystem.data.repository.ReportsRepositoryImp;
import com.hamza.employeemangementsystem.domain.ReportRepository;

import java.util.List;

public class ReportViewModel extends ViewModel {
    ReportsRepositoryImp  repositoryImp;


    public ReportViewModel(@NonNull DBHandler dbHandler) {
        super();
        repositoryImp = new ReportsRepositoryImp(dbHandler);

    }
    public List<String> getAllReports(){
       return  repositoryImp.getReports();
    }
}

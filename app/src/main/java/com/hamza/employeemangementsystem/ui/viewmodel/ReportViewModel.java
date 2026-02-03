package com.hamza.employeemangementsystem.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.repository.AttendanceRepositoryImp;

import java.util.List;

public class ReportViewModel extends ViewModel {
    AttendanceRepositoryImp repositoryImp;
    public  interface OnEventClickListener{
        void ViewReportClick();
    }
    private OnEventClickListener listener;

    public OnEventClickListener getListener() {
        return listener;
    }
    public void setListener(OnEventClickListener listener) {
        this.listener = listener;
    }

    public ReportViewModel(@NonNull DBHandler dbHandler) {
        super();
        repositoryImp = new AttendanceRepositoryImp(dbHandler);
    }
    public List<Attendance> getReportsByCriteria(String startDate, String endDate, String employeeId, String loginEmployeeId){
         return repositoryImp.getAttendanceByCriteria(startDate,endDate,employeeId,loginEmployeeId);


    }
}

package com.hamza.employeemangementsystem.ui.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hamza.employeemangementsystem.core.DataSourceMode;
import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.core.ResultCallback;
import com.hamza.employeemangementsystem.data.database.DbHandler;
import com.hamza.employeemangementsystem.data.database.local.AppDatabaseHelper;
import com.hamza.employeemangementsystem.data.database.local.SQLiteLocalDataSource;
import com.hamza.employeemangementsystem.data.database.remote.RemoteDataSourceClass;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.repository.AttendanceRepositoryImp;
import com.hamza.employeemangementsystem.ui.AttendanceConverter;

import java.util.List;

public class ReportViewModel extends ViewModel {
    AttendanceRepositoryImp attendanceRepositoryImp;
    private final MutableLiveData<List<Attendance>> reports = new MutableLiveData<>();
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

    public ReportViewModel(@NonNull AttendanceRepositoryImp repositoryImp, String startDate, String endDate, String employeeId, String loginEmployeeId, Context context) {
        super();

        attendanceRepositoryImp = repositoryImp;
        loadReports();
    }
    private void loadReports(){
        attendanceRepositoryImp.getAllAtt(new ResultCallback<List<Attendance>>() {
            @Override
            public void onSuccess(List<Attendance> result) {
                reports.setValue(result);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        //reports.setValue(repositoryImp.getAttendanceByCriteria(startDate,endDate,employeeId,loginEmployeeId));
    }
    public LiveData<List<Attendance>> getAllReports() {
        return reports;
    }
}

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReportViewModel extends ViewModel {
    AttendanceRepositoryImp attendanceRepositoryImp;
    String startDate, endDate,employeeId,loginEmployeeId;
    private final MutableLiveData<List<Attendance>> reports = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

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

        this.attendanceRepositoryImp = repositoryImp;
        this.startDate= startDate;
        this.endDate= endDate;
        this.employeeId= employeeId;
        this.loginEmployeeId=loginEmployeeId;
        loadReports();
    }
    private void loadReports(){
        {
            startLoading();
            executorService.execute(() -> {
                // ✅ This runs in background thread
                List<Attendance> result =
                        attendanceRepositoryImp.getAttendanceByCriteria(
                                startDate,
                                endDate,
                                employeeId,
                                loginEmployeeId
                        );

                // ✅ Update LiveData from background thread
                reports.postValue(result);
                stopLoading();
            });
        }
        //reports.setValue(attendanceRepositoryImp.getAttendanceByCriteria(startDate,endDate,employeeId,loginEmployeeId));
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public void startLoading() {
        isLoading.setValue(true);
    }
    private void stopLoading() {
        isLoading.postValue(false); // ✅ safe from background thread
    }
    public LiveData<List<Attendance>> getAllReports() {
        return reports;
    }
}

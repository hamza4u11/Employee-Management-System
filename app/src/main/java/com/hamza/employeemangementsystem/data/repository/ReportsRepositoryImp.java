package com.hamza.employeemangementsystem.data.repository;
import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.domain.ReportRepository;
import java.util.ArrayList;
import java.util.List; public class ReportsRepositoryImp implements ReportRepository {
    DBHandler dbHandler;
    public ReportsRepositoryImp(DBHandler dbHandler) {
        this.dbHandler = dbHandler; }
    @Override
    public List<String> getReports() {
        ArrayList<String> critera = new ArrayList<>();
        critera.add("Today Attendance");
        critera.add("Attendance By Employee");
        critera.add("All Month Attendance");
        critera.add("One Week Attendance");
        critera.add("Manager Attendance");
        critera.add("Attendance By Date");
        return critera;
    }

}
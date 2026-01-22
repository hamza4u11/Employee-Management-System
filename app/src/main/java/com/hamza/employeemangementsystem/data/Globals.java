package com.hamza.employeemangementsystem.data;

import com.hamza.employeemangementsystem.data.model.Employee;

public class Globals {
    public static String loginUserId="";
    private static Globals shared= new Globals();
    private String userId="";
    private String dateTimeFormat = "EEEE, dd MMM yyyy h:mm a";
    private String timeFormat = "h:mm";
    private Employee employee = null;
    public Globals(){

    }

    public static Globals getShared() {

         return shared;
    }

    public static void setShared(Globals shared) {
        Globals.shared = shared;
    }

    public Employee getEmployee() {

        return employee;
    }

    public void setEmployee(Employee employee) {

        this.employee = employee;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }
    public String getDateTimeFormat()
    {
        return dateTimeFormat;
    }

    public void getDateTimeFormat(String format)
    {
        this.dateTimeFormat = dateTimeFormat;
    }
    public String getTimeFormat()
    {
        return timeFormat;
    }

    public void getTimeFormat(String format)
    {
        this.timeFormat = timeFormat;
    }
}

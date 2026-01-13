package com.hamza.employeemangementsystem.data;

public class Globals {
    public static String loginUserId="";
    public static Globals shared= new Globals();
    private String userId="";
    private String dateTimeFormat = "EEEE, dd MMM yyyy h:mm a";
    private String timeFormat = "h:mm";
    public Globals(){

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

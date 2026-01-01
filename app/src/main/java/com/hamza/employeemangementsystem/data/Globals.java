package com.hamza.employeemangementsystem.data;

public class Globals {
    public static String loginUserId="";
    public static Globals shared= new Globals();
    private String userId="";
    public Globals(){

    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

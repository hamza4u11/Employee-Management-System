package com.hamza.employeemangementsystem.data.model;

import java.security.PublicKey;

public class Attendance {
    public int id;
    public int empId;
    public String date;
    public String  checkInTime;
    public String checkOutTime;
    public int overTime;
    public String name ;
    public String status;

    public Attendance(int empId, String checkInTime, String date, String checkOutTime, int overTime){
        this.empId=empId;
        this.date=date;
        this.checkInTime=checkInTime;
        this.checkOutTime=checkOutTime;
        this.overTime=overTime;
        this.name= null;
        this.status=null;

    }
    public Attendance(int id, int empId, String checkInTime, String date,String checkOutTime, int overTime){
        this.id=id;
        this.empId=empId;
        this.date=date;
        this.checkInTime=checkInTime;
        this.checkOutTime=checkOutTime;
        this.overTime=overTime;
        this.name= null;
        this.status=null;
    }

    public Attendance(){

    }





}

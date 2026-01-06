package com.hamza.employeemangementsystem.data.model;

public class Attendance {
    public int id;
    public int empId;
    public String  checkInTime;
    public String checkOutTime;
    public int overTime;


    public Attendance(int id, int empId, String checkInTime, String checkOutTime, int overTime){
        this.id=id;
        this.empId=empId;
        this.checkInTime=checkInTime;
        this.checkOutTime=checkOutTime;
        this.overTime=overTime;
    }
    public Attendance(int empId, String checkInTime, String checkOutTime, int overTime){
        this.empId=empId;
        this.checkInTime=checkInTime;
        this.checkOutTime=checkOutTime;
        this.overTime=overTime;

    }
    public Attendance(){

    }





}

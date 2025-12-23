package com.hamza.employeemangementsystem.data.model;

public class AttendenceModel {
    public int id;
    public int empId;
    public String  checkInTime;
    public String checkOutTime;
    public int overTime;


    public AttendenceModel(int id, int empId, String checkInTime,String checkOutTime,int overTime){
        this.id=id;
        this.empId=empId;
        this.checkInTime=checkInTime;
        this.checkOutTime=checkOutTime;
        this.overTime=overTime;

    }
    public AttendenceModel( int empId, String checkInTime,String checkOutTime,int overTime){
        this.empId=empId;
        this.checkInTime=checkInTime;
        this.checkOutTime=checkOutTime;
        this.overTime=overTime;

    }





}

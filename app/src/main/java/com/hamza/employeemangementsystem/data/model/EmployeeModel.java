package com.hamza.employeemangementsystem.data.model;

public class EmployeeModel {

    public int id;
    public String name;
    public String designation;
    public String phone_no;
    public String address;
    public String paymentType;
    public String allowHoliday;
    public String overTimeAllow;
    public String status;
    public String pin;
    public String checkIn;
    public String roles;
    public String managerId;

    public EmployeeModel(String name, String designation, String phone_no,
                         String address, String paymentType, String allowHoliday,
                         String overTimeAllow, String status, String pin,
                         String checkIn, String roles, String managerId) {
        this.name = name;
        this.designation = designation;
        this.phone_no = phone_no;
        this.address = address;
        this.paymentType = paymentType;
        this.allowHoliday = allowHoliday;
        this.overTimeAllow = overTimeAllow;
        this.status = status;
        this.pin = pin;
        this.checkIn = checkIn;
        this.roles = roles;
        this.managerId = managerId;
    }

    public EmployeeModel(int id,String name, String designation, String phone_no,
                         String address, String paymentType, String allowHoliday,
                         String overTimeAllow, String status, String pin,
                         String checkIn, String roles, String managerId) {
        this.id=id;
        this.name = name;
        this.designation = designation;
        this.phone_no = phone_no;
        this.address = address;
        this.paymentType = paymentType;
        this.allowHoliday = allowHoliday;
        this.overTimeAllow = overTimeAllow;
        this.status = status;
        this.pin = pin;
        this.checkIn = checkIn;
        this.roles = roles;
        this.managerId = managerId;
    }

    public EmployeeModel(int anInt) {
    }
}

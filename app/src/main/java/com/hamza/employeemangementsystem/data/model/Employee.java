package com.hamza.employeemangementsystem.data.model;

public class Employee {

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
    public String managerId;

    public Employee(String name, String checkIn,String designation, String phone_no,
                    String address, String paymentType, String allowHoliday,
                    String overTimeAllow, String status, String pin,
                   String managerId) {
        this.name = name;
        this.designation = designation;
        this.checkIn = checkIn;
        this.phone_no = phone_no;
        this.address = address;
        this.paymentType = paymentType;
        this.allowHoliday = allowHoliday;
        this.overTimeAllow = overTimeAllow;
        this.status = status;
        this.pin = pin;
        this.checkIn = checkIn;
        this.managerId = managerId;
    }

    public Employee(int id, String name, String checkIn,String designation, String phone_no,
                    String address, String paymentType, String allowHoliday,
                    String overTimeAllow, String status, String pin,
                   String managerId) {
        this.id=id;
        this.name = name;
        this.designation = designation;
        this.phone_no = phone_no;
        this.checkIn = checkIn;
        this.address = address;
        this.paymentType = paymentType;
        this.allowHoliday = allowHoliday;
        this.overTimeAllow = overTimeAllow;
        this.status = status;
        this.pin = pin;
        this.checkIn = checkIn;
        this.managerId = managerId;
    }

    public Employee() {
    }
    @Override
    public String toString() {
        return name; // or getName()
    }

    public int setId(int i) {
        return i;
    }

    public String setName(String selectManager) {
        return selectManager;
    }

    public int getId() {
        return 0;
    }

    public String getName() {
        return "0";
    }
}

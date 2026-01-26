package com.hamza.employeemangementsystem.data.model;

public class  Payment {

    public int id;
    public int empId;
    public String  paymentMonth;
    public String paymentYear;
    public String paymentDate;
    public String status;



    public Payment(int empId, String paymentDate, String paymentMonth, String paymentYear, String status) {
        this.empId = empId;
        this.paymentDate= paymentDate;
        this.paymentMonth=paymentMonth;
        this.paymentYear=paymentYear;
        this.status=status;

    }
    public Payment(int id, int empId, String paymentDate, String paymentMonth, String paymentYear, String status) {
        this.id = id;
        this.empId = empId;
        this.paymentDate= paymentDate;
        this.paymentMonth=paymentMonth;
        this.paymentYear=paymentYear;
        this.status=status;

    }

}

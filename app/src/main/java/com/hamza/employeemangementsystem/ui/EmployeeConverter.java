package com.hamza.employeemangementsystem.ui;

import android.content.ContentValues;
import android.database.Cursor;

import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.data.model.Employee;

public class EmployeeConverter implements IConvertHelper<Employee> {


    @Override
    public String getIdFieldName() {
        return "id";
    }

    @Override
    public String getEntityName() {
        return "employees";
    }

    @Override
    public ContentValues fromModel(Employee model) {
        ContentValues values = new ContentValues();
        values.put("id",model.id);
        values.put("name", model.name);
        values.put("desgination",model.designation);
        values.put("phone_no", model.phone_no);
        values.put("address", model.address);
        values.put("paymentType", model.paymentType);
        values.put("allowHoliday",model.allowHoliday);
        values.put("overTimeAllow", model.overTimeAllow);
        values.put("status",model.status);
        values.put("pin",model.pin);
        values.put("checkIn", model.checkIn);
        values.put("roles", model.roles);
        values.put("managerId",model.managerId);
        return values;
    }

    @Override
    public Employee toModel(Cursor cursor) {
        Employee emp = new Employee();
        emp.id= cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        emp.name= cursor.getString(cursor.getColumnIndexOrThrow("name"));
        emp.designation= cursor.getString(cursor.getColumnIndexOrThrow("designation"));
        emp.phone_no= cursor.getString(cursor.getColumnIndexOrThrow("phone_no"));
        emp.address= cursor.getString(cursor.getColumnIndexOrThrow("address"));
        emp.paymentType= cursor.getString(cursor.getColumnIndexOrThrow("payment_type"));
        emp.allowHoliday= cursor.getString(cursor.getColumnIndexOrThrow("allow_holidays"));
        emp.overTimeAllow= cursor.getString(cursor.getColumnIndexOrThrow("over_time_allow"));
        emp.status= cursor.getString(cursor.getColumnIndexOrThrow("status"));
        emp.pin= cursor.getString(cursor.getColumnIndexOrThrow("pin"));
        emp.checkIn= cursor.getString(cursor.getColumnIndexOrThrow("check_in"));
        emp.roles= cursor.getString(cursor.getColumnIndexOrThrow("roles"));
        emp.managerId= cursor.getString(cursor.getColumnIndexOrThrow("magager_id"));

        return emp;
    }
}

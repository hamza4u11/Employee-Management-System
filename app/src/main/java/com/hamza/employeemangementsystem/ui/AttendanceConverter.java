package com.hamza.employeemangementsystem.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.local.AppDatabaseHelper;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AttendanceConverter implements IConvertHelper<Attendance> {
    EmployeeRepositoryImp employeeRepositoryImp;
    public AttendanceConverter(EmployeeRepositoryImp employeeRepositoryImp) {
        this.employeeRepositoryImp = employeeRepositoryImp;
    }

    @Override
    public String getIdFieldName() {
        return "empId";
    }
    public String getFirstFieldName() {
        return "checkInTime";
    }
    public String getSecondFieldName() {
        return "checkOutTime";
    }
    @Override
    public String getEntityName() {
        return "attendance";
    }

    @Override
    public ContentValues fromModel(Attendance model) {

        ContentValues values = new ContentValues();
        values.put("empId",model.empId);
        values.put("date",model.date);
        values.put("checkInTime", model.checkInTime);
        values.put("checkOutTime", model.checkOutTime);
        values.put("overTime",model.overTime);
        return values;
    }

    @Override
    public Attendance toModel(Cursor cursor) {
        Attendance attendance = new Attendance();
        attendance.id= cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        attendance.empId= cursor.getInt(cursor.getColumnIndexOrThrow("empId"));
        attendance.date= cursor.getString(cursor.getColumnIndexOrThrow("date"));
        attendance.checkInTime=cursor.getString(cursor.getColumnIndexOrThrow("checkInTime"));
        attendance.checkOutTime=cursor.getString(cursor.getColumnIndexOrThrow("checkOutTime"));
        attendance.overTime= cursor.getInt(cursor.getColumnIndexOrThrow("overTime"));
        if (cursor.getColumnIndex("name")>0){
            attendance.name= cursor.getString(cursor.getColumnIndexOrThrow("name"));
        }
        if (cursor.getColumnIndex("status")>0){
            attendance.status= cursor.getString(cursor.getColumnIndexOrThrow("status"));
        }
        return attendance;
    }

    @Override
    public List<Attendance> fromJson(String response) {
    Gson gson = new Gson();
    Type type = new TypeToken<List<Attendance>>(){}.getType();
        return gson.fromJson(response, type);
    }

    @Override
    public String toJson(Attendance object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}

package com.hamza.employeemangementsystem.domain;

import android.graphics.drawable.Icon;

import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.core.ModeMapper;
import com.hamza.employeemangementsystem.data.model.Employee;

import java.util.List;

import kotlin.jvm.internal.IntCompanionObject;

public interface NetworkDataSource <T> {
    T getRecordById(String id, IConvertHelper<T> mapper);
    List<T> getAllRecords( IConvertHelper<T> mapper);
    T getLastRecord(String id);
    List<T> getRecordByCriteria(String criteria);
    void updateRecord( String id ,T model );
    void insertRecord( T model);
    void deleteRecord(String id);


}

package com.hamza.employeemangementsystem.domain;

import android.view.Display;

import androidx.annotation.OpenForTesting;

import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.core.ModeMapper;
import com.hamza.employeemangementsystem.data.model.Employee;

import java.util.List;

public interface LocalDataSource<T> {
    T getRecordById(String id,IConvertHelper<T> mapper);
    List<T> getAllRecords(IConvertHelper<T> mapper);
    T getLastRecord(String id, IConvertHelper<T> mapper);
    List<T> getRecordByCriteria(String criteria, String orderBy, IConvertHelper<T> mapper);
    void updateRecord(String id,T model,  IConvertHelper<T> mapper);
    void insertRecord( T Mode, IConvertHelper<T> mapper);
    void deleteRecord(String id, IConvertHelper<T> mapper);


}

package com.hamza.employeemangementsystem.domain;

import android.graphics.drawable.Icon;

import com.hamza.employeemangementsystem.core.ApiResultCallback;
import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.core.ModeMapper;
import com.hamza.employeemangementsystem.data.model.Employee;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import kotlin.jvm.internal.IntCompanionObject;

public interface NetworkDataSource <T> {
    T getRecordById(String id, IConvertHelper<T> mapper);
    List<T> getAllRecords( IConvertHelper<T> mapper);
    T getLastRecord(String id, IConvertHelper<T> mapper);
    List<T> getRecordByCriteriaSync(String criteria, IConvertHelper<T> mapper, Type type);
    void updateRecord( String id ,T model );
    void insertRecord( T model);
    void deleteRecord(String id);


}

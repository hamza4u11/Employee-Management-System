package com.hamza.employeemangementsystem.domain;

import android.graphics.drawable.Icon;

import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.data.model.Employee;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public interface NetworkDataSource <T> {
    T getRecordByIdSync(String id, IConvertHelper<T> mapper);
    List<T> getAllRecordsSync( IConvertHelper<T> mapper, Type type);
    List<T> getLastRecordSync(String id, IConvertHelper<T> mapper,Type type);
    List<T> getRecordByCriteriaSync(String criteria, IConvertHelper<T> mapper, Type type);
    void updateRecordSync( String id ,T model, IConvertHelper<T>  mapper);
    void insertRecordSync( T model, IConvertHelper<T> mapper);
    void deleteRecordSync(String id,IConvertHelper<T> mapper);


}

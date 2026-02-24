package com.hamza.employeemangementsystem.data.database.remote;

import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.domain.LocalDataSource;
import com.hamza.employeemangementsystem.domain.NetworkDataSource;

import java.util.Collections;
import java.util.List;

public class RemoteDataSourceClass<T> implements NetworkDataSource<T> {

    @Override
    public T getRecordById(String id, IConvertHelper<T> mapper) {
        return null;
    }

    @Override
    public List<T> getAllRecords(IConvertHelper<T> mapper) {
        return Collections.emptyList();
    }

    @Override
    public T getLastRecord(String id) {
        return null;
    }

    @Override
    public List<T> getRecordByCriteria(String criteria) {
        return Collections.emptyList();
    }

    @Override
    public void updateRecord(String id, T model) {

    }


    @Override
    public void insertRecord(T model) {

    }

    @Override
    public void deleteRecord(String id) {

    }


}


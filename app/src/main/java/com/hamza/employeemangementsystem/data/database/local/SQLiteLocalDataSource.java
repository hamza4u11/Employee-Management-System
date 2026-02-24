package com.hamza.employeemangementsystem.data.database.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.core.ModeMapper;
import com.hamza.employeemangementsystem.domain.LocalDataSource;

import java.util.Collections;
import java.util.List;

public class SQLiteLocalDataSource<T>  implements LocalDataSource<T> {

        private final AppDatabaseHelper<T> appDatabaseHelper;

    public SQLiteLocalDataSource(AppDatabaseHelper<T> dbHelper, Context context) {
      // dbHelper = new AppDatabaseHelper(context);
        this.appDatabaseHelper = new AppDatabaseHelper(context);
    }

    @Override
    public T getRecordById(String id,  IConvertHelper<T> mapper) {
        return appDatabaseHelper.getRecordById(id, mapper);
    }

    @Override
    public List<T> getAllRecords(IConvertHelper<T> mapper) {
        return appDatabaseHelper.getAllRecords(mapper);
    }

    @Override
    public T getLastRecord(String id, IConvertHelper<T> mapper) {
        return appDatabaseHelper.getLastRecord(id, mapper);
    }

    @Override
    public List<T> getRecordByCriteria(String selectClause, String criteria,String orderBy, IConvertHelper<T> mapper) {
        return appDatabaseHelper.getRecordByCriteria(selectClause,criteria, orderBy,mapper);
    }

    @Override
    public List<T> getRecordByCriteria(String criteria, String orderBy, IConvertHelper<T> mapper) {
        return appDatabaseHelper.getRecordByCriteria(criteria, orderBy,mapper );
    }

    @Override
    public void updateRecord(String id, T model, IConvertHelper<T> mapper) {
        appDatabaseHelper.updateRecord(Integer.parseInt(id),model,mapper);

    }

    @Override
    public void insertRecord(T model, IConvertHelper<T> mapper) {
        appDatabaseHelper.createRecord(model,mapper);
    }

    @Override
    public void insertRecords(List<T> model, IConvertHelper<T> mapper) {

    }

    @Override
    public void deleteRecord(String id, IConvertHelper<T> mapper) {
        appDatabaseHelper.deleteRecord(Integer.parseInt(id), mapper);
    }
}

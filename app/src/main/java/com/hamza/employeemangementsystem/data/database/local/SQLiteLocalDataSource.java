package com.hamza.employeemangementsystem.data.database.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.core.ModeMapper;
import com.hamza.employeemangementsystem.domain.LocalDataSource;

import java.util.Collections;
import java.util.List;

public class SQLiteLocalDataSource<T>  implements LocalDataSource<T> {

        private final AppDatabaseHelper<T> dbHelper;

    public SQLiteLocalDataSource(AppDatabaseHelper<T> dbHelper) {
      // dbHelper = new AppDatabaseHelper(context);
        this.dbHelper = dbHelper;
    }
//public SQLiteLocalDataSource(Context context) {
//    dbHelper = new AppDatabaseHelper(context);
//}
    @Override
    public T getRecordById(String id,  IConvertHelper<T> mapper) {
        return dbHelper.getRecordById(id, mapper);
    }

    @Override
    public List<T> getAllRecords(IConvertHelper<T> mapper) {
        return dbHelper.getAllRecords(mapper);
    }

    @Override
    public T getLastRecord(String id, IConvertHelper<T> mapper) {
        return dbHelper.getLastRecord(id, mapper);
    }

    @Override
    public List<T> getRecordByCriteria(String criteria,String orderBy, IConvertHelper<T> mapper) {
        return dbHelper.getRecordByCriteria(criteria, orderBy,mapper);
    }

    @Override
    public void updateRecord(String id, T model, IConvertHelper<T> mapper) {
        dbHelper.updateRecord(Integer.parseInt(id),model,mapper);

    }

    @Override
    public void insertRecord(T model, IConvertHelper<T> mapper) {
        dbHelper.createRecord(model,mapper);
    }
    @Override
    public void deleteRecord(String id, IConvertHelper<T> mapper) {
        dbHelper.deleteRecord(Integer.parseInt(id), mapper);

    }
}

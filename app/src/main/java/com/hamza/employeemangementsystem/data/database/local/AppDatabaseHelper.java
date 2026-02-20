
package com.hamza.employeemangementsystem.data.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hamza.employeemangementsystem.core.IConvertHelper;

import java.util.ArrayList;
import java.util.List;

public class AppDatabaseHelper<T> extends SQLiteOpenHelper {

    private static final String DB_NAME = "ems.db";
    private static final int DB_VERSION = 10;
    private static final String TABLE_NAME = "employees";
    public static final String CREATE_EMPLOYEE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "designation TEXT, " +
                    "phone_no TEXT, " +
                    "address TEXT, " +
                    "paymentType TEXT, " +
                    "allowHoliday INTEGER, " +     // 0 = false, 1 = true
                    "overTimeAllow INTEGER, " +    // 0 = false, 1 = true
                    "status INTEGER, " +           // 0 = inactive, 1 = active
                    "pin TEXT, " +
                    "checkIn TEXT, " +             // datetime as TEXT
                    "managerId INTEGER" +
                    ");";

    public static final String CREATE_ATTENDANCE_TABLE =
            "CREATE TABLE IF NOT EXISTS attendance (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "empId INTEGER NOT NULL, " +
        "date TEXT NOT NULL, " +
        "checkInTime TEXT, " +
        "checkOutTime TEXT, " +
        "overTime INTEGER DEFAULT 0" +
        ")";


    public AppDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_EMPLOYEE_TABLE);
        db.execSQL(CREATE_ATTENDANCE_TABLE);
        Log.d("Tables","Created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
//        db.execSQL("DROP TABLE IF EXISTS employees");
        db.execSQL("DROP TABLE IF EXISTS attendance");
        onCreate(db);
    }
    public void createRecord(T object, IConvertHelper convertHelper){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = convertHelper.fromModel(object);
        db.insert(convertHelper.getEntityName(),null, values);

    }
    public void updateRecord (int id ,T object, IConvertHelper convertHelper){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = convertHelper.fromModel(object);
        db.update(convertHelper.getEntityName(),values, "id = ?",new String[]{String.valueOf(id)}  );

    }
    public void deleteRecord(int id, IConvertHelper convertHelper){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + convertHelper.getEntityName() + " WHERE id = " + id);

    }

    public T getLastRecord(String id, IConvertHelper convertHelper) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT *  FROM " + convertHelper.getEntityName() + " WHERE " + convertHelper.getIdFieldName() + "=? AND " +convertHelper.getFirstFieldName()+" NOT NULL AND " + convertHelper.getSecondFieldName()+ " IS NULL ORDER BY " +  convertHelper.getFirstFieldName() + " DESC LIMIT 1" ;


        Cursor cursor = db.rawQuery(
                query,
                new String[]{id}
        );
        T model = null;
        if (cursor.moveToFirst()) {
            model = (T) convertHelper.toModel(cursor);
        }
        return model;
    }
    public List<T>  getRecordByCriteria(String selectClause , String criteria,String orderBy,  IConvertHelper convertHelper) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + selectClause + "  FROM " + convertHelper.getEntityName() + " WHERE " + criteria ;
        if(orderBy != null && !orderBy.isEmpty()){
            query= query + " ORDER BY " +  orderBy;
        }
        Cursor cursor = db.rawQuery(
                query,
                null
        );
        T model = null;
        ArrayList<T> models = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                model = (T) convertHelper.toModel(cursor);
                models.add(model);
            }while(cursor.moveToNext());
        }
        return models;
    }
    public List<T> getRecordByCriteria(String criteria,String orderBy,  IConvertHelper convertHelper) {
            return  getRecordByCriteria(" * " , criteria ,orderBy , convertHelper);
    }

    public T getRecordById(String id, IConvertHelper convertHelper) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT *  FROM " + convertHelper.getEntityName() + " WHERE " + convertHelper.getIdFieldName() + "=?";


        Cursor cursor = db.rawQuery(
                query,
                new String[]{id} // ✅ String[]
        );
        T model = null;
        if (cursor.moveToFirst()) {
            model = (T) convertHelper.toModel(cursor);
        }
        return model;
    }


    public List<T> getAllRecords(IConvertHelper convertHelper) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<T> records = new ArrayList<T>();
        String query = "SELECT * FROM " + convertHelper.getEntityName();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
              T  model = (T) convertHelper.toModel(cursor);
              records.add(model);


            } while (cursor.moveToNext());
        }
        return records;
    }
//    public boolean isTableExists(IConvertHelper convertHelper, String tableName) {
//        Cursor cursor = db.rawQuery(
//                "SELECT * FROM sqlite_master WHERE type='table' AND name=?",
//                new String[]{tableName}
//        );
//
//        boolean exists = cursor.getCount() > 0;
//        cursor.close();
//        return exists;
//    }



}



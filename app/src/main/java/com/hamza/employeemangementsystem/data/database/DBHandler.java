//package com.hamza.employeemangementsystem.data.database;//package com.hamza.employeemangementsystem.data.database;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.telephony.mbms.StreamingServiceInfo;
//
//import androidx.annotation.Nullable;
//
//
//
//public class DBHandler extends SQLiteOpenHelper {
//
//    private static final String DATABASE_NAME = "employee_management_system";
//    private static final int DATABASE_VERSION = 1;
//    private static final String EMPLOYEE_TABLE="employees";
//    private static final String KEY_ID = "id";
//    private static final String KEY_NAME= "name";
//    private static final String KEY_DESIGNATION ="designation";
//    private static final String KEY_PHONE_NO = "phone_no";
//    private static final String KEY_ADDRESS ="address";
//    private static final String KEY_EMPLOYEE_TYPE= "employee_type";
//    private static final String KEY_PAYMENT_TYPE = "payment_type";
//    private static final String KEY_ALLOW_HOLIDAYS= "allow_holidays";
//    private static final String KEY_STATUS= "status";
//    private static final String KEY_OVER_TIME_ALLOW = "over_time_allow";
//    private static final String KEY_PIN = "pin";
//    private static final String KEY_CHECK_IN= "check_in";
//    private static final String KEY_ROLES = "roles";
//    private static final String KEY_MANAGER_ID= "manager_id";
//    private static final String DATABASE_ATTENDANCE_REPORT_TABLE = "attendance_reports";
//    private static final String KEY_ATTENDANCE_REPORT_TABLE_ID = "id";
//    private static final String KEY_EMPLOYEE_ID="employee_id";
//    private static final String KEY_CHECK_IN_TIME = "check_in_time";
//    private static final String KEY_CHECK_OUT_TIME = "check_out_time";
//    private static final String KEY_OVER_TIME_HOURS = "over_time_hours";
//    private static final String DATABASE_PAYMENTS_TABLE ="payments";
//    private static final String KEY_PAYMENTS_TABLE_EMPLOYEE_ID= "employee_id";
//    private static final String KEY_PAYMENT_MONTH = "payment_month";
//    private static final String KEY_PAYMENT_YEAR = "payment_year";
//    private static final String KEY_PAYMENTS_DATE= "payment_date";
//    private static final String KEY_PAYMENTS_ID_STATUS = "status";
//
//
//    public DBHandler( Context context) {
//
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//
//        }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL("DROP TABLE IF EXISTS "+EMPLOYEE_TABLE);
//            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_ATTENDANCE_REPORT_TABLE);
//            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_PAYMENTS_TABLE);
//            onCreate(db);
//
//
//
//    }
//    public void createTable(SQLiteDatabase db) {
////        if(content.value.tableName= EMPLOYEE_TABLE){
////                db.execSQL("CREATE TABLE " + DATABASE_TABLE + "("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
////                KEY_NAME+ " TEXT," +  KEY_DESIGNATION + "TEXT NOT NULL," + KEY_PHONE_NO + "TEXT NOT NULL," +
////                KEY_ADDRESS +"TEXT NOT NULL," + KEY_EMPLOYEE_TYPE +" TEXT NOT NULL, " + KEY_PAYMENT_TYPE + "TEXT NOT NULL, "+
////                KEY_ALLOW_HOLIDAYS + "INTEGER NOT NULL," + KEY_STATUS + "TEXT NOT NULL," +  KEY_OVER_TIME_ALLOW +
////                "TEXT NOT NULL,"+ KEY_PIN + "INTEGER NOT NULL," + KEY_CHECK_IN + "TEXT NOT NULL," + KEY_ROLES + " TEXT NOT NULL, " +
////                KEY_MANAGER_ID + " INTEGER NOT NULL)" );
////                } else if (content.values.tableName = DATABASE_ATTENDANCE_REPORT_TABLE) {
////                db.execSQL("CREATE TABLE " + DATABASE_ATTENDANCE_REPORT_TABLE + "("+ KEY_ATTENDANCE_REPORT_TABLE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
////                KEY_EMPLOYEE_ID + "INTEGER," +  KEY_CHECK_IN_TIME + "DATETIME NOT NULL," + KEY_CHECK_OUT_TIME + "DATETIME NOT NULL," +
////                KEY_OVER_TIME_HOURS +" INTEGER NOT NULL )" );
////                }else if (content.values.tableName = DATABASE_PAYMENTS_TABLE){
////                db.execSQL("CREATE TABLE " + DATABASE_PAYMENTS_TABLE + "("+ KEY_ATTENDANCE_REPORT_TABLE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
////                KEY_PAYMENTS_TABLE_EMPLOYEE_ID + "INTEGER," +  KEY_PAYMENT_MONTH + "TEXR NOT NULL," + KEY_PAYMENT_YEAR + "DATETIME NOT NULL," +  KEY_PAYMENTS_DATE + "DATETIME NOT NULL," +
////                KEY_PAYMENTS_ID_STATUS + "TEXT NOT NULL)");
////                }else {
////                system.out.println("Enter correct table name");
////                }
//    }
//
//
//
//
//
//
//}
package com.hamza.employeemangementsystem.data.database;

import static android.app.DownloadManager.COLUMN_ID;

import android.animation.TypeConverter;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.ColorSpace;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;

import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.ui.view.fragment.SelectProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class DBHandler <T> extends SQLiteOpenHelper {

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


    public DBHandler(Context context) {
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
    public List<T> getRecordByCriteria(String selectClause , String criteria,String orderBy,  IConvertHelper convertHelper) {
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



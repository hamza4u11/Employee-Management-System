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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;
import java.util.zip.Checksum;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "ems.db";
    private static final int DB_VERSION = 3;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS employee");
        onCreate(db);    }

    public boolean isTableExists(String databaseTable) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                new String[]{databaseTable}
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

//    public void createTable(String databaseTableCreateSql) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(databaseTableCreateSql);
//    }


//    public void createTable(List attendenceList){
//        String column_sql = ", ".join(attendenceList);
//         SQLiteDatabase db = this.getWritableDatabase();
//        query = ;
//
//
//    }


    public void update(String sql, Object[] bindArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql, bindArgs);
    }


    public boolean insert(String sql, Object[] params) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(sql, params);
            return true;
        } catch (Exception e) {
            Log.e("DB", "Insert Error: " + e.getMessage());
            return false;
        }
    }

    public void delete(String sql, Object[] bindArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql, bindArgs);   // Correct usage
    }
    public Cursor getAllRecords(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }
//    public Cursor getRecordById(String query, String[] params) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery(query, params);
//    }
//    public Cursor getRecordById(String query, int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery(query, id);
//    }
public Cursor getRecordById(String query, int id) {
    SQLiteDatabase db = this.getReadableDatabase();
    return db.rawQuery(
            query,
            new String[]{ String.valueOf(id) } // ✅ String[]
    );
}

    public String login(String query, int employeeId, String enteredPin) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("DB", "Handler");
        Log.d("PIN_CHECK", "EmployeeId: " + employeeId);
        Log.d("PIN_CHECK", "EnteredPin: [" + enteredPin + "]");
        Cursor cursor = db.rawQuery(
                query,
                new String[]{
                        String.valueOf(employeeId),
                        enteredPin
                }
        );
        String desgination = null;

        if (cursor != null && cursor.moveToFirst()) {
            desgination = cursor.getString(cursor.getColumnIndexOrThrow("designation"));
            Log.d("DB_HANDLER", "desgination fetched: " + desgination);        }
        Log.d("DBhandler " , "The desgination is " + desgination);

        if (cursor != null) cursor.close();
        return desgination;



    }


}





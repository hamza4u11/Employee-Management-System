package com.hamza.employeemangementsystem.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.AttendenceModel;

import java.util.ArrayList;
import java.util.List;

public class AttendenceRepository {
    private static final String DATABASE_TABLE = "attendance_reports";
    private static final String COL_ID = "id";
    private static final String KEY_EMPLOYEE_ID="employee_id";
    private static final String KEY_CHECK_IN_TIME = "check_in_time";
    private static final String KEY_CHECK_OUT_TIME = "check_out_time";
    private static final String KEY_OVER_TIME_HOURS = "over_time_hours";
    private static final String DATABASE_TABLE_CREATE_SQL =
            "CREATE TABLE " +  DATABASE_TABLE +
                    "("+ COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                    KEY_EMPLOYEE_ID+ " INTEGER," +
                    KEY_CHECK_IN_TIME +" TEXT NOT NULL," +
                    KEY_CHECK_OUT_TIME +
                    " TEXT NOT NULL," +
                    KEY_OVER_TIME_HOURS + " INTEGER ) ";

    private static final String RECORD_UPDATE_SQL=
            "UPDATE " + DATABASE_TABLE + " SET " +
                    KEY_EMPLOYEE_ID + " = ?, " +
                    KEY_CHECK_IN_TIME + " = ?, " +
                    KEY_CHECK_OUT_TIME + " = ?, " +
                    KEY_OVER_TIME_HOURS + " = ?, " +
                    "WHERE " + COL_ID + " = ?";
    private static final String RECORD_INSERT_SQL =
            "INSERT INTO " + DATABASE_TABLE + " (" +
                    KEY_EMPLOYEE_ID + ", " +
                    KEY_CHECK_IN_TIME + ", " +
                    KEY_CHECK_OUT_TIME + ", " +
                    KEY_OVER_TIME_HOURS +
                    ") VALUES (?, ?, ?, ? )";

    private static final String  RECORD_DELETE_SQL= "" +
            " DELETE FROM "+DATABASE_TABLE +
            " WHERE " + COL_ID +
            " = ? ";
    private static final String GET_ALL_RECORDS= "SELECT * FROM " + DATABASE_TABLE;

    private DBHandler dbHandler;

    public AttendenceRepository(Context context){
        dbHandler = new DBHandler(context);
        if (!dbHandler.isTableExists(DATABASE_TABLE)) {
           // dbHandler.createTable(DATABASE_TABLE_CREATE_SQL);
           // dbHandler.createTable(attendenceList);
        }
    }
    List<AttendenceModel>  attendenceList = new ArrayList<>();

//        public void testing(){
//        dbHandler.createTable(attendenceList);
//    }
    public void insertReport(AttendenceModel report) {
        Object[] params = new Object[]{
                report.empId,
                report.checkInTime,
                report.checkOutTime,
                report.overTime,
        };

        dbHandler.insert(RECORD_INSERT_SQL, params);

    }
    public Cursor getAllReports() {
        // Acquire the cursor from your DB handler (assumes this method exists)
        Cursor cursor = dbHandler.getAllRecords(GET_ALL_RECORDS);

        // Null-check and optional logging (do NOT close the cursor here if you're returning it)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                int employee_id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_EMPLOYEE_ID));
                String checkInTime = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CHECK_IN_TIME));
                String checkOutTime = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CHECK_OUT_TIME));
                int overTime = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_OVER_TIME_HOURS));


                Log.d("Reports", "ID: " + id +  "| empId: " + employee_id + " | checkInTime: " + checkInTime + " | checkOutTime: " + checkOutTime
                        + "| overTime: " + overTime);

            } while (cursor.moveToNext());

            // move cursor back to first so caller sees it at the start
            cursor.moveToFirst();
        }

        // DO NOT close cursor here — caller must close it when done
        return cursor;
    }
    public void delete(int id)
    {
        Object[] args= new Object[]{
                id
        };
        dbHandler.delete(RECORD_DELETE_SQL,args);
    }
    }

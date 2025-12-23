package com.hamza.employeemangementsystem.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.PaymentModel;

public class PaymentRepository {
    private static final String DATABASE_TABLE ="payments";
    private static final String COL_ID="id";
    private static final String COL_PAYMENTS_TABLE_EMPLOYEE_ID= "employee_id";
    private static final String COL_PAYMENT_MONTH = "payment_month";
    private static final String COL_PAYMENT_YEAR = "payment_year";
    private static final String COL_PAYMENT_DATE= "payment_date";
    private static final String COL_STATUS = "status";
    private static final String DATABASE_TABLE_CREATE_SQL =
            "CREATE TABLE " +  DATABASE_TABLE +
                    "("+ COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                    COL_PAYMENTS_TABLE_EMPLOYEE_ID+ " INTEGER," +
                    COL_PAYMENT_DATE +" TEXT NOT NULL," +
                    COL_PAYMENT_MONTH +
                    " TEXT NOT NULL," +
                    COL_PAYMENT_YEAR + " TEXT NOT NULL," +
                    COL_STATUS +" TEXT NOT NULL )";

    private static final String RECORD_UPDATE_SQL=
            "UPDATE " + DATABASE_TABLE + " SET " +
                    COL_PAYMENTS_TABLE_EMPLOYEE_ID + " = ?, " +
                    COL_PAYMENT_DATE + " = ?, " +
                    COL_PAYMENT_MONTH + " = ?, " +
                    COL_PAYMENT_YEAR + " = ?, " +
                    COL_STATUS + " = ? "+
                    "WHERE " + COL_ID + " = ?";
    private static final String RECORD_INSERT_SQL =
            "INSERT INTO " + DATABASE_TABLE + " (" +
                    COL_PAYMENTS_TABLE_EMPLOYEE_ID + ", " +
                    COL_PAYMENT_DATE + ", " +
                    COL_PAYMENT_MONTH + ", " +
                    COL_PAYMENT_YEAR + ", " +
                    COL_STATUS  +
                    ") VALUES (?, ?, ?, ?, ? )";

    private static final String  RECORD_DELETE_SQL= "" +
            " DELETE FROM "+DATABASE_TABLE +
            " WHERE " + COL_ID +
            " = ? ";
    private static final String GET_ALL_RECORDS= "SELECT * FROM " + DATABASE_TABLE;


    private DBHandler dbHandler;

    public PaymentRepository(Context context) {

        dbHandler = new DBHandler(context);
       // Cursor getPayments=getAllPayments();

        if (!dbHandler.isTableExists(DATABASE_TABLE)) {
//            dbHandler.createTable(DATABASE_TABLE_CREATE_SQL);
        }
    }

    public void insertPayment(PaymentModel payment){
        Object[] params = new Object[]{
                payment.empId,
                payment.paymentDate,
                payment.paymentMonth,
                payment.paymentYear,
                payment.status
        };

        dbHandler.insert(RECORD_INSERT_SQL,params);

    }
    public void updatePayment(PaymentModel payment){
        Object[] params =  new Object[]{

                payment.empId,
                payment.paymentDate,
                payment.paymentMonth,
                payment.paymentYear,
                payment.status,
                payment.id,
        };
        dbHandler.update(RECORD_UPDATE_SQL,params);
    }

    public void delete(int id)
    {
        Object[] args= new Object[]{
                id
        };
        dbHandler.delete(RECORD_DELETE_SQL,args);
    }
    public Cursor getAllPayments() {
        // Acquire the cursor from your DB handler (assumes this method exists)
        Cursor cursor = dbHandler.getAllRecords(GET_ALL_RECORDS);

        // Null-check and optional logging (do NOT close the cursor here if you're returning it)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                int employee_id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PAYMENTS_TABLE_EMPLOYEE_ID));
                String paymentDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_PAYMENT_DATE));
                String paymentMonth = cursor.getString(cursor.getColumnIndexOrThrow(COL_PAYMENT_MONTH));
                String paymentYear = cursor.getString(cursor.getColumnIndexOrThrow(COL_PAYMENT_MONTH));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS));


                Log.d("PATMENTS", "ID: " + id +  "| empId: " + employee_id + " | paymentDate: " + paymentDate + " | paymentMonth: " + paymentMonth
                        + "| paymentYear: " + paymentYear +  " | status: " + status);

            } while (cursor.moveToNext());

            // move cursor back to first so caller sees it at the start
            cursor.moveToFirst();
        }

        // DO NOT close cursor here — caller must close it when done
        return cursor;
    }




}

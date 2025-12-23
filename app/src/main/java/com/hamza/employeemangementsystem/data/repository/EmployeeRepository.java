//package com.hamza.employeemangementsystem.data.repository;//package com.hamza.employeemangementsystem.data.repository;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.hamza.employeemangementsystem.data.database.DBHandler;
//
//public class EmployeeRepo {
//
//        private DBHandler dbHandler;
//        public String employeeQuery;
//
//    public EmployeeRepo(Context context){
//         dbHandler = new DBHandler(context);
//    }
//
//    private static final String DATABASE_TABLE="employees";
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
//    private static final String KEY_PAYMENT_DATA = "payment_date";
//    private static final String KEY_PAYMENTS_ID_STATUS = "status";
//
//
//    public void createTable( ){
//
//
//
//
//
//
//
//
//
//    }
//
//
//
//
//}
package com.hamza.employeemangementsystem.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.hamza.employeemangementsystem.data.database.DBHandler;
import com.hamza.employeemangementsystem.data.model.EmployeeModel;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private static final String DATABASE_TABLE = "employees";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_DESIGNATION = "designation";
    public  static final String COL_PHONE = "phone_no";
    private static final String COL_ADDRESS = "address";
    private static final String COL_PAYMENT_TYPE = "payment_type";
    private static final String COL_ALLOW_HOLIDAYS = "allow_holidays";
    private static final String COL_STATUS ="status";
    private static final String COL_OVER_TIME_ALLOW = "over_time_allow";
    private static final String COL_PIN = "pin";
    private static final String COL_CHECK_IN = "check_in";
    private static final String COL_ROLES = "roles";
    private static final String COL_MANAGER_ID = "magager_id";
    private static final String DATABASE_TABLE_CREATE_SQL =
            "CREATE TABLE " +  DATABASE_TABLE +
                    "("+ COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                   COL_NAME+ " TEXT," +
                    COL_DESIGNATION +
                    " TEXT NOT NULL," +
                    COL_PHONE + " TEXT NOT NULL," +
                  COL_ADDRESS +" TEXT NOT NULL," +
                    COL_PAYMENT_TYPE +" TEXT NOT NULL, "+
                  COL_ALLOW_HOLIDAYS + " INTEGER NOT NULL," +
                    COL_STATUS + " TEXT NOT NULL," +
                    COL_OVER_TIME_ALLOW +
                    " TEXT NOT NULL,"+
                    COL_PIN + " TEXT NOT NULL," +
                    COL_CHECK_IN + " TEXT NOT NULL," +
                    COL_ROLES + " TEXT NOT NULL, " +
                   COL_MANAGER_ID + " INTEGER NOT NULL )"  ;


    private static final String RECORD_UPDATE_SQL=
            "UPDATE " + DATABASE_TABLE + " SET " +
            COL_NAME + " = ?, " +
            COL_DESIGNATION + " = ?, " +
            COL_PHONE + " = ?, " +
            COL_ADDRESS + " = ?, " +
            COL_PAYMENT_TYPE + " = ?, " +
            COL_ALLOW_HOLIDAYS + " = ?, " +
            COL_STATUS + " = ?, " +
            COL_OVER_TIME_ALLOW + " = ?, " +
            COL_PIN + " = ?, " +
            COL_CHECK_IN + " = ?, " +
            COL_ROLES + " = ?, " +
            COL_MANAGER_ID + " = ? " +
            "WHERE " + COL_ID + " = ?";
    private static final String RECORD_INSERT_SQL =
            "INSERT INTO " + DATABASE_TABLE + " (" +
                    COL_NAME + ", " +
                    COL_DESIGNATION + ", " +
                    COL_PHONE + ", " +
                    COL_ADDRESS + ", " +
                    COL_PAYMENT_TYPE + ", " +
                    COL_ALLOW_HOLIDAYS + ", " +
                    COL_STATUS + ", " +
                    COL_OVER_TIME_ALLOW + ", " +
                    COL_PIN + ", " +
                    COL_CHECK_IN + ", " +
                    COL_ROLES + ", " +
                    COL_MANAGER_ID +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    private static final String  RECORD_DELETE_SQL= "" +
            " DELETE FROM "+DATABASE_TABLE +
            " WHERE " + COL_ID +
            " = ? ";
    private static final String GET_ALL_RECORDS= "SELECT * FROM " + DATABASE_TABLE;
    private static final String GET_RECORD="SELECT * FROM " + DATABASE_TABLE + " WHERE " + COL_ID + " = ?";
    public static final String CHECK_PIN_QUERY =
            "SELECT " +COL_DESIGNATION+ " FROM " +  DATABASE_TABLE + " WHERE id = ? AND pin = ?";


    private DBHandler dbHandler;

    public EmployeeRepository(Context context) {
        dbHandler = new DBHandler(context);
//        Cursor allEmployees = getAllEmployees();
        if(!dbHandler.isTableExists(DATABASE_TABLE)){
//           dbHandler.createTable(DATABASE_TABLE_CREATE_SQL);
       }
    }

    public void insertEmployee(EmployeeModel emp) {
        //Log.d("EMP_LOG", emp.toString());
//        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Object[] params = new Object[]{
                emp.name,
                emp.designation,
                emp.phone_no,
                emp.address,
                emp.paymentType,
                emp.allowHoliday,
                emp.status,
                emp.overTimeAllow,
                emp.pin,
                emp.checkIn,
                emp.roles,
                emp.managerId
        };
        dbHandler.insert(RECORD_INSERT_SQL, params);
    }
    public List<EmployeeModel> getAllEmployees() {
        List<EmployeeModel> list = new ArrayList<>();

//        Log.d("TAG", "getAllEmployees: ");
        // Acquire the cursor from your DB handler (assumes this method exists)
        Cursor cursor = dbHandler.getAllRecords(GET_ALL_RECORDS);

        // Null-check and optional logging (do NOT close the cursor here if you're returning it)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                EmployeeModel employee = new EmployeeModel(
               cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
               cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
               cursor.getString(cursor.getColumnIndexOrThrow(COL_DESIGNATION)),
               cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)),
            cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS)),
             cursor.getString(cursor.getColumnIndexOrThrow(COL_PAYMENT_TYPE)),
             cursor.getString(cursor.getColumnIndexOrThrow(COL_ALLOW_HOLIDAYS)),
              cursor.getString(cursor.getColumnIndexOrThrow(COL_OVER_TIME_ALLOW)),
             cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_PIN)),
              cursor.getString(cursor.getColumnIndexOrThrow(COL_CHECK_IN)),
           cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLES)),
             cursor.getString(cursor.getColumnIndexOrThrow(COL_MANAGER_ID))
  );
                list.add(employee);
//                Log.d("EMPLOYEE", "ID: " + id + " | Name: " + name + " | Designation: " + designation +  "| Phone: " + phone + " | Address: " + address + " | PaymentType: " + paymentType
//                + "| allowHoliday: " + allowHoliday + " | overTimeAllow: " + overTimeAllow + " | checkIn: " + checkIn +  "| roles: " + roles + " | managerId: " + managerId +
//                        " | pin: " + pin + " | status: " + status);

            } while (cursor.moveToNext());

            // move cursor back to first so caller sees it at the start
            cursor.moveToFirst();
        }

        // DO NOT close cursor here — caller must close it when done
        return list;
    }

//    public String checkEmployeePin(int employeeId, String pin) {
//        return dbHandler.isPinValid(CHECK_PIN_QUERY,employeeId, pin);
//    }

    public String login(int id, String pin){

       return dbHandler.login(CHECK_PIN_QUERY,id,pin);

    }


//     do {
//        EmployeeModel employee = new EmployeeModel(
//        int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
//        String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
//        String designation = cursor.getString(cursor.getColumnIndexOrThrow(COL_DESIGNATION));
//        String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));
//        String address = cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS));
//        String paymentType = cursor.getString(cursor.getColumnIndexOrThrow(COL_PAYMENT_TYPE));
//        String allowHoliday = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALLOW_HOLIDAYS));
//        String overTimeAllow = cursor.getString(cursor.getColumnIndexOrThrow(COL_OVER_TIME_ALLOW));
//        String status = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS));
//        String pin = cursor.getString(cursor.getColumnIndexOrThrow(COL_PIN));
//        String checkIn = cursor.getString(cursor.getColumnIndexOrThrow(COL_CHECK_IN));
//        String roles = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLES));
//        String managerId = cursor.getString(cursor.getColumnIndexOrThrow(COL_MANAGER_ID));
//  );
//        list.add(employee);
//        Log.d("EMPLOYEE", "ID: " + id + " | Name: " + name + " | Designation: " + designation +  "| Phone: " + phone + " | Address: " + address + " | PaymentType: " + paymentType
//                + "| allowHoliday: " + allowHoliday + " | overTimeAllow: " + overTimeAllow + " | checkIn: " + checkIn +  "| roles: " + roles + " | managerId: " + managerId +
//                " | pin: " + pin + " | status: " + status);
//
//    } while (cursor.moveToNext());
//
//    // move cursor back to first so caller sees it at the start
//            cursor.moveToFirst();
//}


//public Cursor getEmployeeById(int employeeId) {
//        Log.d("TAG", "This is my simple text message");
//
//
//        Cursor cursor = dbHandler.getRecordById(GET_RECORD, new String[]{String.valueOf(employeeId)});
////        Object[] args = new Object[]{
////                employeeId
////        };
//
//        if (cursor != null && cursor.moveToFirst()) {
//
//            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
//            String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
//            String designation = cursor.getString(cursor.getColumnIndexOrThrow(COL_DESIGNATION));
//            String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));
//            String address = cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS));
//            String paymentType = cursor.getString(cursor.getColumnIndexOrThrow(COL_PAYMENT_TYPE));
//            String allowHoliday = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALLOW_HOLIDAYS));
//            String overTimeAllow = cursor.getString(cursor.getColumnIndexOrThrow(COL_OVER_TIME_ALLOW));
//            String status = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS));
//            String pin = cursor.getString(cursor.getColumnIndexOrThrow(COL_PIN));
//            String checkIn = cursor.getString(cursor.getColumnIndexOrThrow(COL_CHECK_IN));
//            String roles = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLES));
//            String managerId = cursor.getString(cursor.getColumnIndexOrThrow(COL_MANAGER_ID));
//
//            Log.d("EMPLOYEE_BY_ID",
//                    "ID: " + id +
//                            " | Name: " + name +
//                            " | Designation: " + designation +
//                            " | Phone: " + phone +
//                            " | Address: " + address +
//                            " | PaymentType: " + paymentType +
//                            " | AllowHoliday: " + allowHoliday +
//                            " | OverTime: " + overTimeAllow +
//                            " | CheckIn: " + checkIn +
//                            " | Roles: " + roles +
//                            " | ManagerId: " + managerId +
//                            " | Pin: " + pin +
//                            " | Status: " + status
//            );
//        }
//
//        return cursor;
//    }
//public LiveData<EmployeeModel> getEmployeeById(int id) {
//    String[] iD = {String.valueOf(id)};
//
//    return dbHandler.getRecordById(GET_RECORD, iD);
//}
//public List<EmployeeModel> getEmployeeById(int id) {
//    List<EmployeeModel> list = new ArrayList<>();
//
////        Log.d("TAG", "getAllEmployees: ");
//    // Acquire the cursor from your DB handler (assumes this method exists)
//    Cursor cursor = dbHandler.getRecordById(GET_RECORD, id);
//
//    // Null-check and optional logging (do NOT close the cursor here if you're returning it)
//    if (cursor != null && cursor.moveToFirst()) {
//        do {
//            EmployeeModel employee = new EmployeeModel(
//                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COL_DESIGNATION)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PAYMENT_TYPE)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ALLOW_HOLIDAYS)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COL_OVER_TIME_ALLOW)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PIN)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COL_CHECK_IN)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLES)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COL_MANAGER_ID))
//            );
//            list.add(employee);
////                Log.d("EMPLOYEE", "ID: " + id + " | Name: " + name + " | Designation: " + designation +  "| Phone: " + phone + " | Address: " + address + " | PaymentType: " + paymentType
////                + "| allowHoliday: " + allowHoliday + " | overTimeAllow: " + overTimeAllow + " | checkIn: " + checkIn +  "| roles: " + roles + " | managerId: " + managerId +
////                        " | pin: " + pin + " | status: " + status);
//
//        } while (cursor.moveToNext());
//
//        // move cursor back to first so caller sees it at the start
//        cursor.moveToFirst();
//    }
//
//    // DO NOT close cursor here — caller must close it when done
//    return list;
//}


public List<EmployeeModel> getEmployeeById(int id) {

    List<EmployeeModel> list = new ArrayList<>();

    Cursor cursor = dbHandler.getRecordById(GET_RECORD, id);

    if (cursor != null && cursor.moveToFirst()) {
        do {
            EmployeeModel employee = new EmployeeModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_DESIGNATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PAYMENT_TYPE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ALLOW_HOLIDAYS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_OVER_TIME_ALLOW)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PIN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_CHECK_IN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLES)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_MANAGER_ID))
            );
            list.add(employee);

        } while (cursor.moveToNext());

        cursor.close(); // ✅ CLOSE HERE
    }

    return list;
}




    public void updateEmployee(EmployeeModel emp) {
        Object[] args = new Object[]{
                emp.name,
                emp.designation,
                emp.phone_no,
                emp.address,
                emp.paymentType,
                emp.allowHoliday,
                emp.status,
                emp.overTimeAllow,
                emp.pin,
                emp.checkIn,
                emp.roles,
                emp.managerId,
                emp.id
        };

        dbHandler.update(RECORD_UPDATE_SQL, args);
    }

    public void deleteEmployee(int id) {
        Object[] args = new Object[]{
                id
        };

        dbHandler.delete(RECORD_DELETE_SQL, args);
    }

}

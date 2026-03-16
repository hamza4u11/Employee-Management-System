package com.hamza.employeemangementsystem.data.database;

import android.content.ContentValues;
import android.credentials.RegisterCredentialDescriptionRequest;
import android.os.Looper;

import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.hamza.employeemangementsystem.core.DataSourceMode;
import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.core.ResultCallback;
import com.hamza.employeemangementsystem.data.Globals;
import com.hamza.employeemangementsystem.data.database.local.SQLiteLocalDataSource;
import com.hamza.employeemangementsystem.data.database.remote.RemoteDataSourceClass;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.data.repository.EmployeeRepositoryImp;
import com.hamza.employeemangementsystem.domain.LocalDataSource;
import com.hamza.employeemangementsystem.ui.AttendanceConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.JsonReader;
import android.util.Log;

import androidx.annotation.StringDef;

import org.json.JSONObject;

import kotlinx.coroutines.selects.SelectClause;

public class DbHandler<T> {
    private final LocalDataSource<T> local;
    private final RemoteDataSourceClass<T> remote;
    private final IConvertHelper<T> mapper;
    private final DataSourceMode mode;
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public DbHandler(LocalDataSource<T> local, RemoteDataSourceClass<T> remote, IConvertHelper
            <T> mapper, DataSourceMode mode) {
        this.local = local;
        this.remote = remote;
        this.mapper = mapper;
        this.mode = mode;
    }
    public void getAllAsync(ResultCallback<List<T>>  callBack, Type type) {
        executor.execute(() -> {
            try{
                List<T> result;
                switch (mode) {
                    case LOCAL_ONLY:
                        result = local.getAllRecords(mapper);
                        break;
                    case REMOTE_ONLY:
                        result = remote.getAllRecordsSync(mapper, type);
                        break;
                    case HYBRID_REMOTE_FIRST:
                        try {
                            result = remote.getAllRecordsSync(mapper,type);
                            if (result != null) {
                                for (T item : result) {
                                    local.insertRecord(item, mapper);
                                }
                            }
                        } catch (Exception e) {
                            result = local.getAllRecords(mapper);
                        }

                        break;
                    case HYBRID_LOCAL_FIRST:
                        result = local.getAllRecords(mapper);

                        try {
                            if ((result == null || result.isEmpty())) {
                                result = remote.getAllRecordsSync(mapper,type);
                                if (result != null) {
                                    for (T item : result) {
                                        local.insertRecord(item, mapper);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            result = new ArrayList<>();
                        }
                        break;
                    default:
                        result = new ArrayList<>();
                }
                List<T> finalResult = result;
                mainHandler.post(() -> callBack.onSuccess(finalResult));
            } catch (RuntimeException e) {
                mainHandler.post(()-> callBack.onError(e));
            }
        });
    }
    public T loginSync(String id, String pin) {
        switch (mode) {
            case LOCAL_ONLY:
                T empLocal = local.getRecordById(id, mapper);
                if (empLocal!=null && ((Employee) empLocal).pin.equals(pin)){
                    return empLocal;
                }
                return null;
            case REMOTE_ONLY:
                T empRemote = remote.getRecordByIdSync(id,mapper,null);
                if (empRemote!= null && ((Employee) empRemote).pin.equals(pin)){
                    return empRemote;
                }
            case HYBRID_REMOTE_FIRST:
                try {
                    T empRemoteFirst = remote.getRecordByIdSync(id, mapper,null);
                    if (empRemoteFirst != null && ((Employee) empRemoteFirst).pin.equals(pin)) {
                        local.insertRecord(empRemoteFirst, mapper);

                    }
                }catch (Exception ignore ){

                }
                T empLocalFallBack = local.getRecordById(id, mapper);
                if (empLocalFallBack != null && ((Employee) empLocalFallBack).pin.equals(pin)){
                    return empLocalFallBack;
                }

                return null;
            case HYBRID_LOCAL_FIRST:
                T empLocalFirst =  local.getRecordById(id, mapper);
                if(empLocalFirst!= null && ((Employee) empLocalFirst).pin.equals(pin)){
                    return empLocalFirst;
                }
                try{
                    T empRemoteSecond = local.getRecordById(id,mapper);
                    if(empRemoteSecond != null && ((Employee) empRemoteSecond).pin.equals(pin));
                    local.insertRecord(empRemoteSecond,mapper);
                    return empRemoteSecond;
                }catch (Exception e){
                    return null;
                }
            default:
                return null;
        }
    }
    public T getRecordById(String id, Type type) {
        switch (mode) {
            case LOCAL_ONLY:
                Log.d("Mode", "Local Only");
                T empLocal = local.getRecordById(id, mapper);
                if (empLocal!=null ){
                    return empLocal;
                }
            case REMOTE_ONLY:
                T empRemote = remote.getRecordByIdSync(id,mapper,type);
                if (empRemote!= null){
                    return empRemote;
                }
//            case HYBRID_REMOTE_FIRST:
//                Log.d("Mode","HYBRID_REMOTE_FIRST Id="+ id);
//                try {
//                    T empRemoteFirst = remote.getRecordByIdSync(id, mapper,null);
//                    if (empRemoteFirst != null ) {
//                        local.insertRecord(empRemoteFirst, mapper);
//
//                    }
//                }catch (Exception ignore ){
//
//                }
//                T empLocalFallBack = local.getRecordById(id, mapper);
//                if (empLocalFallBack != null ){
//                    return empLocalFallBack;
//                }
//
////                return null;
//            case HYBRID_LOCAL_FIRST:
//                T empLocalFirst =  local.getRecordById(id, mapper);
//                if(empLocalFirst!= null){
//                    return empLocalFirst;
//                }
//                try{
//                    T empRemoteSecond = local.getRecordById(id,mapper);
//                    if(empRemoteSecond != null);
//                    local.insertRecord(empRemoteSecond,mapper);
//                    return empRemoteSecond;
//                }catch (Exception e){
//                    return null;
//                }

            default:
        }
        return null;

    }

    public List<T> getAllRecords( Type type) {
        switch (mode) {
//            case LOCAL_ONLY:
//            List<T> empLocal = local.getAllRecords(mapper );
//                if (empLocal!=null ){
//                    return empLocal;
//                }
            case REMOTE_ONLY:
                List<T> empRemote = remote.getAllRecordsSync(mapper, type);
                if (empRemote!= null){
                    return empRemote;
                }
//            case HYBRID_REMOTE_FIRST:
//                try {
//                    List<T> empRemoteFirst = remote.getAllRecordsSync(mapper,type);
//                    if (empRemoteFirst != null ) {
//                        //local.insertRecord(empRemoteFirst, mapper);
//
//                    }
//                }catch (Exception ignore ){
//
//                }
//                List<T> empLocalFallBack = local.getAllRecords( mapper);
//                if (empLocalFallBack != null ){
//                    return empLocalFallBack;
//                }
//
//                return null;
//            case HYBRID_LOCAL_FIRST:
//                List<T> empLocalFirst =  local.getAllRecords( mapper);
//                if(empLocalFirst!= null){
//                    return empLocalFirst;
//                }
//                try{
//                    List<T> empRemoteSecond = local.getAllRecords(mapper);
//                    if(empRemoteSecond != null);
//                    //  local.insertRecord(empRemoteSecond,mapper);
//                    return empRemoteSecond;
//                }catch (Exception e){
//                    return null;
               // }
            default:
                return null;
        }
    }
    public void loginAsync(String id , String pin, ResultCallback<T> callback){
        executor.execute(()->{
            try {
                T result = loginSync(id, pin);
                if (result != null) {
                    mainHandler.post(() -> callback.onSuccess(result));
                } else {
                    mainHandler.post(() -> callback.onError(new Exception("Invalid Credentials")));
                }
            }catch (Exception e ) {
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }
    public void  insertRecord(T object ){
        String jsonObject = new Gson().toJson(object);
        Log.d("Insert Object", jsonObject);
        switch ( mode){
            case LOCAL_ONLY:
               local.insertRecord(object,mapper);
               break;
            case REMOTE_ONLY:
                remote.insertRecordSync(object,mapper);
                break;
            case HYBRID_LOCAL_FIRST:
                local.insertRecord(object,mapper);
                remote.insertRecordSync(object,mapper);
                break;
            case HYBRID_REMOTE_FIRST:
                remote.insertRecordSync(object,mapper);
                local.insertRecord(object, mapper);
                break;
            default:
        }
    }
    public void updateRecord(String id, T object){
        String jsonObject = new Gson().toJson(object);
        Log.d("Insert Object", jsonObject);
        switch (mode){
            case LOCAL_ONLY:
                Log.d("UPDATE RECORD MODE", "LOCAL ONLY");
                local.updateRecord(id, object,mapper);
                break;
            case REMOTE_ONLY:
                Log.d("UPDATE RECORD MODE","REMOTE ONLY");
                remote.updateRecordSync(id,object,mapper);
                break;
            case HYBRID_LOCAL_FIRST:
                local.updateRecord(id,object,mapper);
                remote.updateRecordSync(id, object,mapper);
                break;
            case HYBRID_REMOTE_FIRST:
                remote.updateRecordSync(id,object,mapper);
                local.updateRecord(id,object,mapper);
                break;
            default:
        }
    }
    public void deleteRecordSync(String id){
        switch (mode){
            case LOCAL_ONLY:
                local.deleteRecord(id,mapper);
                break;
            case REMOTE_ONLY:
                remote.deleteRecordSync(id,mapper);
                break;
            case HYBRID_LOCAL_FIRST:
                local.deleteRecord(id,mapper);
                remote.deleteRecordSync(id,mapper);
                break;
            case HYBRID_REMOTE_FIRST:
                remote.deleteRecordSync(id,mapper);
                local.deleteRecord(id,mapper);
                break;
            default:
        }
    }
    public T getLastRecord(String id, Type type){
        switch (mode){
            case LOCAL_ONLY:
                T recLocal = local.getLastRecord(id,mapper);
                if(recLocal != null){
                    return recLocal;
                }
                break;
            case REMOTE_ONLY:
                T recRemote = remote.getLastRecordSync(id, mapper,type);

                if(recRemote != null){
                    return recRemote;
                }
                break;
            case HYBRID_LOCAL_FIRST:
                T recLocalFirst = local.getLastRecord(id,mapper);
                if (recLocalFirst != null){
                    return recLocalFirst;
                }
                T recRemoteSecond = remote.getLastRecordSync(id, mapper,type);
                if (recRemoteSecond != null){
                    local.insertRecord(recRemoteSecond,mapper);
                    return recRemoteSecond;
                }
                break;
            case HYBRID_REMOTE_FIRST:
                T recRemoteFirst = remote.getLastRecordSync(id,mapper,type);
                if(recRemoteFirst != null){
                    local.insertRecord(recRemoteFirst,mapper);
                    return recRemoteFirst;
                }
                T recLocalSecond = local.getLastRecord(id,mapper);
                if(recLocalSecond != null){
                    return recLocalSecond;
                }
                break;
            default:
                return null;
        }
        return null;
    }
    public List<T> getRecordByCriteria(String selectClause, String criteria, String orderBy, Type type){
        switch(mode){
            case LOCAL_ONLY:
                List<T> recLocal = local.getRecordByCriteria(selectClause,criteria,orderBy,mapper);
                if(recLocal!= null){
                    return recLocal;
                }
                break;
            case REMOTE_ONLY:

                Log.d("Remote Only", "Get Record By Criteria");
                List<T> recRemote = remote.getRecordByCriteriaSync(criteria,mapper,type);
                if (recRemote != null){
                        return recRemote;
                }
                break;
//            case HYBRID_LOCAL_FIRST:
//                List<T> recLocalFirst = local.getRecordByCriteria(selectClause, criteria, orderBy, mapper);
//                if (recLocalFirst != null){
//                    return recLocalFirst;
//                }else{
//                    List<T> recRemoteSecond = remote.getRecordByCriteriaSync(criteria,mapper,type);
//                    if(recRemoteSecond != null){
//                        local.insertRecords(recRemoteSecond, mapper);
//                        return recRemoteSecond;
//                    }
//                }
//                break;
//            case HYBRID_REMOTE_FIRST:
//                List<T> recRemoteFirst = remote.getRecordByCriteriaSync(criteria,mapper,type);
//                if (recRemoteFirst != null){
//                    return recRemoteFirst;
//                }else{
//                    List<T> recLocalSecond = local.getRecordByCriteria(selectClause,criteria,orderBy, mapper);
//                    if(recLocalSecond != null){
//                        return recLocalSecond;
//                    }
//                }
//                break;
        }
        return null;
    }

    public  DataSourceMode getMode(){
        DataSourceMode mode1;
        Log.d("Mode" , "" + mode);
        return   mode1 = mode;
    }



}





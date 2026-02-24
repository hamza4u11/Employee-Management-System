package com.hamza.employeemangementsystem.data.database;

import android.content.ContentValues;
import android.os.Looper;

import com.google.android.material.appbar.AppBarLayout;
import com.hamza.employeemangementsystem.core.DataSourceMode;
import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.core.ResultCallback;
import com.hamza.employeemangementsystem.data.database.remote.RemoteDataSourceClass;
import com.hamza.employeemangementsystem.data.model.Employee;
import com.hamza.employeemangementsystem.domain.LocalDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.telephony.mbms.StreamingServiceInfo;

import androidx.annotation.StringDef;

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
//    public void getAllAsync(ResultCallback<List<T>>  callBack) {
//        executor.execute(() -> {
//            try{
//                List<T> result;
//                switch (mode) {
//                    case LOCAL_ONLY:
//                        result = local.getAllRecords(mapper);
//                        break;
//                    case REMOTE_ONLY:
//                        result = remote.getAllRecords(mapper);
//                        break;
//                    case HYBRID_REMOTE_FIRST:
//                        try {
//                            result = remote.getAllRecords(mapper);
//                            if (result != null) {
//                                for (T item : result) {
//                                    local.insertRecord(item, mapper);
//                                }
//                            }
//                        } catch (Exception e) {
//                            result = local.getAllRecords(mapper);
//                        }
//
//                        break;
//                    case HYBRID_LOCAL_FIRST:
//                        result = local.getAllRecords(mapper);
//
//                        try {
//                            if ((result == null || result.isEmpty())) {
//                                result = remote.getAllRecords(mapper);
//                                if (result != null) {
//                                    for (T item : result) {
//                                        local.insertRecord(item, mapper);
//                                    }
//                                }
//                            }
//                        } catch (Exception e) {
//                            result = new ArrayList<>();
//                        }
//                        break;
//                    default:
//                        result = new ArrayList<>();
//                }
//                List<T> finalResult = result;
//                mainHandler.post(() -> callBack.onSuccess(finalResult));
//
//
//            } catch (RuntimeException e) {
//                mainHandler.post(()-> callBack.onError(e));
//            }
//        });
//    }

    public void getAllAsync(ResultCallback<List<T>>  callBack) {
        executor.execute(() -> {
            try{
                List<T> result;
                switch (mode) {
                    case LOCAL_ONLY:
                        result = local.getAllRecords(mapper);
                        break;
                    case REMOTE_ONLY:
                        result = remote.getAllRecords(mapper);
                        break;
                    case HYBRID_REMOTE_FIRST:
                        try {
                            result = remote.getAllRecords(mapper);
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
                                result = remote.getAllRecords(mapper);
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
                T empRemote = remote.getRecordById(id,mapper);
                if (empRemote!= null && ((Employee) empRemote).pin.equals(pin)){
                    return empRemote;
                }
            case HYBRID_REMOTE_FIRST:
                try {
                    T empRemoteFirst = remote.getRecordById(id, mapper);
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


    public T getRecordById(String id) {
        switch (mode) {
            case LOCAL_ONLY:
            T empLocal = local.getRecordById(id, mapper);
                if (empLocal!=null ){
                    return empLocal;
                }
                return null;
            case REMOTE_ONLY:
                T empRemote = remote.getRecordById(id,mapper);
                if (empRemote!= null){
                    return empRemote;
                }
            case HYBRID_REMOTE_FIRST:
                try {
                    T empRemoteFirst = remote.getRecordById(id, mapper);
                    if (empRemoteFirst != null ) {
                        local.insertRecord(empRemoteFirst, mapper);

                    }
                }catch (Exception ignore ){

                }
                T empLocalFallBack = local.getRecordById(id, mapper);
                if (empLocalFallBack != null ){
                    return empLocalFallBack;
                }

                return null;
            case HYBRID_LOCAL_FIRST:
                T empLocalFirst =  local.getRecordById(id, mapper);
                if(empLocalFirst!= null){
                    return empLocalFirst;
                }
                try{
                    T empRemoteSecond = local.getRecordById(id,mapper);
                    if(empRemoteSecond != null);
                    local.insertRecord(empRemoteSecond,mapper);
                    return empRemoteSecond;
                }catch (Exception e){
                    return null;
                }
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
        switch ( mode){
            case LOCAL_ONLY:
               local.insertRecord(object,mapper);
               break;
            case REMOTE_ONLY:
                remote.insertRecord(object);
                break;
            case HYBRID_LOCAL_FIRST:
                local.insertRecord(object,mapper);
                remote.insertRecord(object);
                break;
            case HYBRID_REMOTE_FIRST:
                remote.insertRecord(object);
                local.insertRecord(object, mapper);
                break;
            default:
        }
    }
    public void updateRecord(String id, T object){
        switch (mode){
            case LOCAL_ONLY:
                local.updateRecord(id, object,mapper);
                break;
            case REMOTE_ONLY:
                remote.updateRecord(id,object);
                break;
            case HYBRID_LOCAL_FIRST:
                local.updateRecord(id,object,mapper);
                remote.updateRecord(id, object);
                break;
            case HYBRID_REMOTE_FIRST:
                remote.updateRecord(id,object);
                local.updateRecord(id,object,mapper);
                break;
            default:
        }
    }
    public void deleteRecord(String id){
        switch (mode){
            case LOCAL_ONLY:
                local.deleteRecord(id,mapper);
                break;
            case REMOTE_ONLY:
                remote.deleteRecord(id);
                break;
            case HYBRID_LOCAL_FIRST:
                local.deleteRecord(id,mapper);
                remote.deleteRecord(id);
                break;
            case HYBRID_REMOTE_FIRST:
                remote.deleteRecord(id);
                local.deleteRecord(id,mapper);
                break;
            default:
        }
    }
    public T getLastRecord(String id){
        switch (mode){
            case LOCAL_ONLY:
                T recLocal = local.getLastRecord(id,mapper);
                if(recLocal != null){
                    return recLocal;
                }
                break;
            case REMOTE_ONLY:
                T recRemote = remote.getLastRecord(id);
                if(recRemote != null){
                    return recRemote;
                }
                break;
            case HYBRID_LOCAL_FIRST:
                T recLocalFirst = local.getLastRecord(id,mapper);
                if (recLocalFirst != null){
                    return recLocalFirst;
                }
                T recRemoteSecond = remote.getLastRecord(id);
                if (recRemoteSecond != null){
                    local.insertRecord(recRemoteSecond,mapper);
                    return recRemoteSecond;
                }
                break;
            case HYBRID_REMOTE_FIRST:
                T recRemoteFirst = remote.getLastRecord(id);
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
    public List<T> getRecordByCriteria(String selectClause, String criteria, String orderBy){
        switch(mode){
            case LOCAL_ONLY:
                List<T> recLocal = local.getRecordByCriteria(selectClause,criteria,orderBy,mapper);
                if(recLocal!= null){
                    return recLocal;
                }
                break;
            case REMOTE_ONLY:
                List<T> recRemote = remote.getRecordByCriteria(criteria);
                if (recRemote!=null){
                    return recRemote;
                }
                break;
            case HYBRID_LOCAL_FIRST:
                List<T> recLocalFirst = local.getRecordByCriteria(selectClause,criteria,orderBy, mapper);
                if (recLocalFirst != null){
                    return recLocalFirst;
                }
                List<T> recRemoteSecond = remote.getRecordByCriteria(criteria);
                if(recRemoteSecond != null){
                    local.insertRecords(recRemoteSecond, mapper);
                    return recRemoteSecond;
                }
                break;
            case HYBRID_REMOTE_FIRST:

                break;
        }
        return null;
    }

}





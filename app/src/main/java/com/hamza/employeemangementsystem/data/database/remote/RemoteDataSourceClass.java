package com.hamza.employeemangementsystem.data.database.remote;

import android.os.Handler;
import android.os.Looper;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;

import androidx.annotation.OpenForTesting;
import androidx.loader.content.CursorLoader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamza.employeemangementsystem.core.IConvertHelper;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.domain.NetworkDataSource;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RemoteDataSourceClass<T> implements NetworkDataSource<T> {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public T getRecordByIdSync(String id, IConvertHelper<T> mapper) {
        Type type = new TypeToken<List<T>>() {}.getType();

        String url = "http://172.20.2.167:5000/get-" + mapper.getEntityName() +"s-by-id/" + id;
        Log.d("URL", url);
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            String json = response.body().string();
            Log.d("JSON_RESPONSE", json);
            Gson gson = new Gson();
            T list = gson.fromJson(json, type);
            return list;   // ✅ RETURN LIST
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> getAllRecordsSync(IConvertHelper<T> mapper) {

        Type type = new TypeToken<List<T>>() {}.getType();

            String url = "http://172.20.2.167:5000/get-all" + mapper.getEntityName() +"s";
            Log.d("URL", url);
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response);
                String json = response.body().string();
                Log.d("JSON_RESPONSE", json);
                Gson gson = new Gson();
                List<T> list = gson.fromJson(json, type);
                return list;   // ✅ RETURN LIST
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public T getLastRecordSync(String id, IConvertHelper<T> mapper) {
        Type type = new TypeToken<List<T>>() {}.getType();

        String url = "http://172.20.2.167:5000/get-last-" + mapper.getEntityName() +"-by-employee/" +id;
        Log.d("URL", url);
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            String json = response.body().string();
            Log.d("JSON_RESPONSE", json);
            Gson gson = new Gson();
            T list = gson.fromJson(json, type);
            return list;   // ✅ RETURN LIST
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> getRecordByCriteriaSync(String criteria, IConvertHelper<T> mapper, Type type) {
        String url = "http://172.20.2.167:5000/" + mapper.getEntityName() + "ByCriteria?" + criteria;
        Log.d("URL", url);
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            String json = response.body().string();
            Log.d("JSON_RESPONSE", json);
            Gson gson = new Gson();
            List<T> list = gson.fromJson(json, type);
            return list;   // ✅ RETURN LIST
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateRecordSync(String id, T model) {

    }

    @Override
    public void insertRecordSync(T model, IConvertHelper<T> mapper) {
//        Type type = new TypeToken<List<T>>() {}.getType();
        String url = "http://172.20.2.167:5000/insert-" + mapper.getEntityName();
        Log.d("URL", url);
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
           // String json = response.body().string();
            //Log.d("JSON_RESPONSE", json);
            //Gson gson = new Gson();
//            List<T> list = gson.fromJson(json, type);
//            return list;   // ✅ RETURN LIST
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRecordSync(String id, IConvertHelper<T> mapper) {
        String url = "http://172.20.2.167:5000/delete-" + mapper.getEntityName()+"/"+ id;
        Log.d("URL", url);
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            // String json = response.body().string();
            //Log.d("JSON_RESPONSE", json);
            //Gson gson = new Gson();
//            List<T> list = gson.fromJson(json, type);
//            return list;   // ✅ RETURN LIST
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


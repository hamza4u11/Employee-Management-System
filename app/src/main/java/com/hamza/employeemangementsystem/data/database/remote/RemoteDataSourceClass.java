package com.hamza.employeemangementsystem.data.database.remote;

import android.health.connect.datatypes.Record;
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

import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RemoteDataSourceClass<T> implements NetworkDataSource<T> {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public T getRecordByIdSync(String id, IConvertHelper<T> mapper, Type type) {
        String url="http://172.20.2.167:5000/get-"+ mapper.getEntityName()+ "-by-id/"+id;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                Log.d("Record By Id JSON",json) ;
                Gson gson = new Gson();
               List<T> list = gson.fromJson(json, type);
                if (list != null && !list.isEmpty()) {
                    Log.d("List First Element", ""+ list.get(0));
                    return list.get(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<T> getAllRecordsSync(IConvertHelper<T> mapper, Type type) {
//        Type type = new TypeToken<List<T>>() {}.getType();
            String url = "http://172.20.2.167:5000/get-all-" + mapper.getEntityName();
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
//    public T getLastRecordAsync(String id, IConvertHelper<T> mapper) {
//// Inside RemoteDataSourceClass.java
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String body = response.body().string();
//                    // Remember: This is still on a background thread!
//                    // Use a LiveData .postValue() or a Handler to get back to the UI.
//                }
//            }
//        });
//    }

        @Override
    public T getLastRecordSync(String id, IConvertHelper<T> mapper,Type type) {
        String url = "http://172.20.2.167:5000/get-last-" + mapper.getEntityName() +"-by-employee/" +id;
        Log.d("URL", url);
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            String json = response.body().string();
            Log.d("JSON_RESPONSE", json);
            Gson gson = new Gson();
            List<T> list = gson.fromJson(json, type);
            if (list != null && !list.isEmpty()) {
                return list.get(0); // Here is your first index!
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<T> getRecordByCriteriaSync(String criteria, IConvertHelper<T> mapper, Type type) {
        String url = "http://172.20.2.167:5000/get-" + mapper.getEntityName() + "-by-criteria?" + criteria;
        Log.d("getRecordByCriteriaSync", url);
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            String json = response.body().string();
            Log.d("JSON_RESPONSE", json);
            Gson gson = new Gson();
            List<T> list = gson.fromJson(json, type);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    public List<T> getRecordByCriteriaSyncEmployee(IConvertHelper<T> mapper, Type type) {
//        String url = "http://172.20.2.167:5000/get-managers";
//        Log.d("Get Managers URL", url);
//        Request request = new Request.Builder()
//                .url(url)
//                .get()
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                throw new IOException("Unexpected code " + response);
//            }
//            if (response.body() == null) {
//                return new ArrayList<>();
//            }
//            String json = response.body().string();
//            Log.d("JSON_RESPONSE", json);
//            Gson gson = new Gson();
//            List<T> list = gson.fromJson(json, type);
//            return list ;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }
    @Override
    public void updateRecordSync(String id, T model, IConvertHelper<T> mapper) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String json = mapper.toJson(model);
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://172.20.2.167:5000/update-"+mapper.getEntityName()+"/"+id )
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                System.out.println(responseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void insertRecordSync(T model, IConvertHelper<T> mapper) {
        String url = "http://172.20.2.167:5000/insert-" + mapper.getEntityName();
        Log.d("URL", url);
        try {
            Gson gson = new Gson();
            String json = gson.toJson(model);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                String responseBody = response.body() != null ? response.body().string() : "";
                Log.d("INSERT_RESPONSE", responseBody);
            }
        } catch (Exception e) {
            Log.e("API_ERROR", "Insert failed", e);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


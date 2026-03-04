package com.hamza.employeemangementsystem.data.database.remote;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.OpenForTesting;
import androidx.loader.content.CursorLoader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamza.employeemangementsystem.core.ApiResultCallback;
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
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public T getRecordById(String id, IConvertHelper<T> mapper) {
        return null;
    }

    @Override
    public List<T> getAllRecords(IConvertHelper<T> mapper) {
        return Collections.emptyList();
    }

    @Override
    public T getLastRecord(String id, IConvertHelper<T> mapper) {
        return null;
    }

//    public List<T> getRecordByCriteriaSync(String criteria, IConvertHelper<T> mapper) {
//        String url="http://172.20.2.152:5000/"+ mapper.getEntityName()+"?"+criteria;
//        Log.d("Url", url);
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            final List<T> responseBody = response.body();
//
//
//
//            // Update the UI on the main thread using the Handler
//            mainHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    //textView.setText(responseBody);
//                }
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            mainHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                  //  textView.setText("Error: " + e.getMessage());
//                }
//            });
//        }
//        return null;
//
//    }
    @Override
    public List<T> getRecordByCriteriaSync(
            String criteria,
            IConvertHelper<T> mapper,
            Type type
    ) {


        String url = "http://172.20.2.167:5000/"
                + mapper.getEntityName()
                + "?"
                + criteria;

        Log.d("URL", url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            // ✅ 1️⃣ Convert response body to String
            String json = response.body().string();

            Log.d("JSON_RESPONSE", json);

            // ✅ 2️⃣ Convert JSON → List<T>
            Gson gson = new Gson();
            List<T> list = gson.fromJson(json, type);

            return list;   // ✅ RETURN LIST

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public void updateRecord(String id, T model) {

    }


    @Override
    public void insertRecord(T model) {

    }

    @Override
    public void deleteRecord(String id) {

    }
//    public <T> void fetchData(
//            String baseUrl,
//            String endpoint,
//            String queryString,
//            Type typeOfT) {
//        Log.d("Base URl" , " " + baseUrl);
//        Log.d("endpoint URl" , " " + endpoint);
//        Log.d("Query" , " " + queryString);
//
//
//        OkHttpClient client = new OkHttpClient();
//
//        String fullUrl = baseUrl + endpoint;
//
//        if (queryString != null && !queryString.isEmpty()) {
//            fullUrl = fullUrl + "?" + queryString;
//        }
//
//        Request request = new Request.Builder()
//                .url(fullUrl)
//                .build();

       // client.newCall(request).enqueue(new Callback() {



//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                if (response.isSuccessful() && response.body() != null) {
//
//                    String json = response.body().string();
//
//                    Gson gson = new Gson();
//                    List<T> result = gson.fromJson(json, typeOfT);
//
//
//                } else {
//                }
//            }
//        });
//    }
}


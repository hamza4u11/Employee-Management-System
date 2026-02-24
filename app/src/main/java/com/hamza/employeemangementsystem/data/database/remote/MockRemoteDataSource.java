package com.hamza.employeemangementsystem.data.database.remote;


import com.hamza.employeemangementsystem.core.IConvertHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MockRemoteDataSource<T> implements RemoteDataSource<T> {

    @Override
    public T getById(String endpoint, String id, IConvertHelper<T> mapper) throws Exception {
        return null;
    }

    @Override
    public List<T> getAll(String endpoint, IConvertHelper<T> mapper) throws Exception {
        String json = "[{\"id\":\"1\",\"name\":\"Alice\",\"email\":\"alice@example.com\",\"pin\":\"1234\"}," +
                "{\"id\":\"2\",\"name\":\"Bob\",\"email\":\"bob@example.com\",\"pin\":\"1234\"}]";
        JSONArray array = new JSONArray(json);
        List<T> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            list.add(mapper.fromJson(obj));
        }
        return list;
    }
    @Override
    public T create(String endpoint, T model, IConvertHelper<T> mapper) throws Exception {
        return null;
    }

    @Override
    public T update(String endpoint, String id, T model, IConvertHelper<T> mapper) throws Exception {
        return null;
    }

    @Override
    public boolean delete(String endpoint, String id) throws Exception {
        return false;
    }
}
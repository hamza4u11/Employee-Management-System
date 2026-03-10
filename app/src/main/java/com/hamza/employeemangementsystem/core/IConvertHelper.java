package com.hamza.employeemangementsystem.core;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface IConvertHelper<T> {
    String getIdFieldName();
    String getEntityName();
    String getFirstFieldName();
    String getSecondFieldName();
    ContentValues fromModel(T model);
    T toModel(Cursor cursor);
    List<T> fromJson(String response);
    String toJson(T model);

}

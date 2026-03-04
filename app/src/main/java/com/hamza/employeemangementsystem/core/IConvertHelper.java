package com.hamza.employeemangementsystem.core;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONObject;

import java.util.ArrayList;

public interface IConvertHelper<T> {
    String getIdFieldName();
    String getEntityName();
    String getFirstFieldName();
    String getSecondFieldName();
    ContentValues fromModel(T model);
    T toModel(Cursor cursor);
    T fromJson(JSONObject json);
    JSONObject toJson(T model);

}

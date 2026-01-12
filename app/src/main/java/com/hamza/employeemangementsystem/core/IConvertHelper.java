package com.hamza.employeemangementsystem.core;

import android.content.ContentValues;
import android.database.Cursor;

public interface IConvertHelper<T> {
    String getIdFieldName();
    String getEntityName();
    String getFirstFieldName();
    String getSecondFieldName();
    ContentValues fromModel(T model);
    T toModel(Cursor cursor);
}

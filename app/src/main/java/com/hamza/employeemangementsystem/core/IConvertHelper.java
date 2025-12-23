package com.hamza.employeemangementsystem.core;

import android.content.ContentValues;

public interface IConvertHelper<T> {
    ContentValues fromModel(T model);
    T toModel(ContentValues contentValues);
}

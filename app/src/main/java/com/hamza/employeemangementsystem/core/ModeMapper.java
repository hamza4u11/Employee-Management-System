package com.hamza.employeemangementsystem.core;

import android.content.ContentValues;
import android.database.Cursor;

public interface ModeMapper <T>{
    T fromCursor (Cursor cursor);
    ContentValues toContentValues(T model);
}

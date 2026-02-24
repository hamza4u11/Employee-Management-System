package com.hamza.employeemangementsystem.core;

public interface ResultCallback<T> {
    void onSuccess(T result);
    void onError(Exception e);
}

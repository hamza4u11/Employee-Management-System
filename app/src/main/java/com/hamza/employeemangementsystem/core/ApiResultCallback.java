package com.hamza.employeemangementsystem.core;


import java.util.List;

public interface ApiResultCallback<T> {
    void onSuccess(T result);
    void onError(String e);

}

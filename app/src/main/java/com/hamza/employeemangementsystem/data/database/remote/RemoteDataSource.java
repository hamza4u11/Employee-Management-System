package com.hamza.employeemangementsystem.data.database.remote;

import com.hamza.employeemangementsystem.core.IConvertHelper;

import java.util.List;

public interface RemoteDataSource<T> {
    T getById(String endpoint, String id, IConvertHelper<T> mapper) throws Exception;
    List<T> getAll(String endpoint, IConvertHelper<T> mapper) throws Exception;
    T create(String endpoint, T model, IConvertHelper<T> mapper) throws Exception;
    T update(String endpoint, String id, T model, IConvertHelper<T> mapper) throws Exception;
    boolean delete(String endpoint, String id) throws Exception;
}

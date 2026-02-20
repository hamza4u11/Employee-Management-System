package com.hamza.employeemangementsystem.domain;

import com.hamza.employeemangementsystem.core.ModeMapper;
import com.hamza.employeemangementsystem.data.model.Employee;

import java.util.List;

public interface NetworkDataSource <T> {
    T getRecordById(String id, String table, ModeMapper<T> mapper);
    List<T> getAllRecords(String table, ModeMapper<T> mapper);
    T getLastRecord(String Table, String id, ModeMapper<T> mapper);
    List<T> getRecordByCriteria(String criteria);
    void updateRecord(String Table, T model, String where, String[] args, ModeMapper<T> mapper);
    void insertRecord(String table, T Mode, ModeMapper<T> mapper);
    void deleteRecord(String table, String where, String[] args);


}

package com.hamza.employeemangementsystem.data.database.remote;

import com.hamza.employeemangementsystem.core.ModeMapper;
import com.hamza.employeemangementsystem.domain.NetworkDataSource;

import java.util.Collections;
import java.util.List;

public class RemoteDataSource  implements NetworkDataSource {

    @Override
    public Object getRecordById(String id, String table, ModeMapper mapper) {
        return null;
    }

    @Override
    public List getAllRecords(String table, ModeMapper mapper) {
        return Collections.emptyList();
    }

    @Override
    public Object getLastRecord(String Table, String id, ModeMapper mapper) {
        return null;
    }

    @Override
    public List getRecordByCriteria(String criteria) {
        return Collections.emptyList();
    }

    @Override
    public void updateRecord(String Table, Object model, String where, String[] args, ModeMapper mapper) {

    }

    @Override
    public void insertRecord(String table, Object Mode, ModeMapper mapper) {

    }

    @Override
    public void deleteRecord(String table, String where, String[] args) {

    }
}

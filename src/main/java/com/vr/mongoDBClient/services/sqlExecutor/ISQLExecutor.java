package com.vr.mongoDBClient.services.sqlExecutor;

import java.text.ParseException;

public interface ISQLExecutor {
    public void executeSQLQuery(String query) throws ParseException;
    public SQLResult returnResult();
}

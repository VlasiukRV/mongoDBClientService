package com.vr.mongoDBClient.services.sqlExecutor;

import java.text.ParseException;

public interface ISQLRuner {
    public SQLResult runQuery (String query) throws ParseException;
}

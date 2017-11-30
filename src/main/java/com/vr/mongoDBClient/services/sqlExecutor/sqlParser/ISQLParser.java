package com.vr.mongoDBClient.services.sqlExecutor.sqlParser;

import java.text.ParseException;

public interface ISQLParser {
    public void compileSQLQuery(String query)throws ParseException;
}

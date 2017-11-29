package com.vr.mongoDBClient.services.sqlExecuter;

import java.text.ParseException;
import java.util.List;

import org.bson.Document;

public interface ISQLExecuter {
    public List<Document> executeSQLQuery(String query) throws ParseException;
}

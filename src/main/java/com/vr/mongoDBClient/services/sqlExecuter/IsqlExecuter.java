package com.vr.mongoDBClient.services.sqlExecuter;

import java.util.ArrayList;

import org.bson.Document;

public interface ISQLExecuter {
    public ArrayList<Document> executeSQLQuery(String databaseName, String query);
}

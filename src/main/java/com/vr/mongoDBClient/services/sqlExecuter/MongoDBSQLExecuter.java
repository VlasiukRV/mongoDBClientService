package com.vr.mongoDBClient.services.sqlExecuter;

import java.text.ParseException;
import java.util.ArrayList;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoDBSQLExecuter implements ISQLExecuter {
    
    @Autowired
    MongoDBSQLExecuterSelect mongoDBSQLExecuterSelect; 
    
    @Override
    public ArrayList<Document> executeSQLQuery(String databaseName, String query) throws ParseException {
	return executeSQLQuerySelect(databaseName, query);
    }
    
    private ArrayList<Document> executeSQLQuerySelect(String databaseName, String query) throws ParseException {
	if (mongoDBSQLExecuterSelect.isCuurentCommand(query)){
	    return mongoDBSQLExecuterSelect.executeSQLQuery(databaseName, query);
	}
	return null;
    }

}

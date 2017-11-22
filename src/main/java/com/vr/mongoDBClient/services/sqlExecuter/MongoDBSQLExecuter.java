package com.vr.mongoDBClient.services.sqlExecuter;

import java.util.ArrayList;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoDBSQLExecuter implements IsqlExecuter {
    
    @Autowired
    MongoDBSQLExecuterSelect mongoDBSQLExecuterSelect; 
    
    @Override
    public ArrayList<Document> executeSQLQuery(String databaseName, String query) {
	return executeSQLQuerySelect(databaseName, query);
    }
    
    private ArrayList<Document> executeSQLQuerySelect(String databaseName, String query) {
	if (mongoDBSQLExecuterSelect.isCuurentCommand(query)){
	    return mongoDBSQLExecuterSelect.executeSQLQuery(databaseName, query);
	}
	return null;
    }

}

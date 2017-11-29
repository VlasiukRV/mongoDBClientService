package com.vr.mongoDBClient.services.sqlExecuter;

import java.text.ParseException;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoDBSQLExecuter implements ISQLExecuter {
    
    @Autowired
    private MongoDBSQLExecuterSelect mongoDBSQLExecuterSelect; 
    
    public MongoDBSQLExecuter() {
	
    }
    
    @Override
    public List<Document> executeSQLQuery(String query) throws ParseException {
	return executeSQLQuerySelect(query);
    }
    
    private List<Document> executeSQLQuerySelect(String query) throws ParseException {
	if (mongoDBSQLExecuterSelect.isCuurentCommand(query)){
	    return mongoDBSQLExecuterSelect.executeSQLQuery(query);
	}
	return null;
    }

}

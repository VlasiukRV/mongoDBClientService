package com.vr.mongoDBClient.services.sqlExecuter;

import java.util.ArrayList;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import com.vr.mongoDBClient.services.MongoDBService;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLParserSelect;

@Component
public class MongoDBSQLExecuterSelect {
    
    @Autowired
    MongoDBService mongoDBService;
    
    @Autowired
    SQLParserSelect sqlParserSelect;
    
    public boolean isCuurentCommand (String query) {
	return sqlParserSelect.isCurrentCommand(query);
    }
    
    public ArrayList<Document> executeSQLQuery(String databaseName, String query) {
	sqlParserSelect.compileSQLQuery(query);
	
	String collectionName = sqlParserSelect.getTarget().getTarget();
	if(collectionName.equals("")) {
	    return new ArrayList<Document>();
	}
	
	MongoCollection<Document> collection = mongoDBService.getCollection(databaseName, collectionName);
	
	ArrayList<Document> result = collection.find()
		.projection(fields(include(sqlParserSelect.getProjections().getFields()), excludeId()))
		.into(new ArrayList<Document>());
	
	return result;
    }


}

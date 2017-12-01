package com.vr.mongoDBClient.services.mongoDBService;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import lombok.Getter;
import lombok.Setter;

@Component
public class MongoDBService implements IMongoDBService{
        
    @Value("${spring.data.mongodb.host}")
    private @Setter @Getter String host;
    @Value("${spring.data.mongodb.port}")
    private @Setter @Getter int port;
    @Value("${spring.data.mongodb.database}")
    private @Setter @Getter String databaseName;
        
    public MongoDBService() {
	
    }
    
    @Override
    public MongoClient getMongoClient() {
	return new MongoClient(this.host, this.port);
    }
    
    @Override
    public List<String> getDataBasesList() {
	List<String> databasesList = new ArrayList<>();
	
	MongoClient mongoClient = getMongoClient();
	for (String name: mongoClient.listDatabaseNames()) {
	    databasesList.add(name);
	}
	
	return databasesList;
    }
    
    @Override
    public MongoDatabase createDataBase(String dataBaseName) {
	if(dataBaseName.equals("")) {
	    return null;
	}
	
	setDatabaseName(dataBaseName);
	MongoClient mongoClient = getMongoClient();
	return mongoClient.getDatabase(dataBaseName);	
    }
    
    @Override
    public boolean dropDataBase(String dataBaseName) {
	MongoClient mongoClient = getMongoClient();
	MongoDatabase database = mongoClient.getDatabase(dataBaseName);
	try {
	    database.drop();
	}
	catch (Exception e) {
	    System.err.println(e);
	}

	setDatabaseName("");
	return true;
    }    
    
    @Override
    public MongoCollection<Document> getCollection(String collectionName) {
	MongoClient mongoClient = getMongoClient();
	
	MongoDatabase database = mongoClient.getDatabase(databaseName);
	return database.getCollection(collectionName);	
    }
    
    @Override
    public ArrayList<Document> getDocumentListByAgregate(String collectionName, List<Bson> aggregateList){	
	return getCollection(collectionName).aggregate(aggregateList).into(new ArrayList<Document>());	
    }
    
    @Override
    public Document testMongoDbConnection() {
	
	MongoClient mongoClient = getMongoClient();
	
	MongoDatabase database = mongoClient.getDatabase("testdb");
	return database.runCommand(new Document("buildInfo", 1));	    	    	
    }
    
}

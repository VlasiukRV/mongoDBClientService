package com.vr.mongoDBClient.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PreDestroy;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vr.mongoDBClient.services.runtimeProcessRuner.RuntimeProcessByteArrayListiner;
import com.vr.mongoDBClient.services.runtimeProcessRuner.IRuntimeProcessListiner;
import com.vr.mongoDBClient.services.runtimeProcessRuner.RuntimeProcessRuner;

@Component
public class MongoDBService {
    private String mongodPath = "C:\\progra~1\\MongoDB\\Server\\3.4\\bin\\";
    private String dbPath = "E:\\mongodb\\data\\";
    private String host = "localhost";
    private int port = 27017;   
    private RuntimeProcessByteArrayListiner  runtimeProcessByteArrayListiner;    
    private RuntimeProcessRuner runtimeProcessRuner;
    
    public String getMongodPath() {
        return mongodPath;
    }

    public void setMongodPath(String mongodPath) {
        this.mongodPath = mongodPath;
    }

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
	this.dbPath = dbPath;
    }

    public MongoClient getMongoClient() {
	return new MongoClient(this.host, this.port);
    }
    
    public void startMongoDBServer() throws Exception {
	Set<IRuntimeProcessListiner> processListiners = new HashSet<IRuntimeProcessListiner>();
	startMongoDBServer(this.mongodPath, this.dbPath, processListiners);
    }

    public void startMongoDBServer(Set<IRuntimeProcessListiner> processListiners) throws Exception {
	startMongoDBServer(this.mongodPath, this.dbPath, processListiners);
    }

    public void startMongoDBServer(String mongodPath, String dbpath, Set<IRuntimeProcessListiner> processListiners) throws Exception {
	
	runtimeProcessByteArrayListiner = new RuntimeProcessByteArrayListiner();
	processListiners.add(runtimeProcessByteArrayListiner);
	
	String command = String.format(
		      "%smongod.exe --dbpath=%s",
		      mongodPath,
		      dbpath);
	
	runtimeProcessRuner = new RuntimeProcessRuner();
	runtimeProcessRuner.setCommand(command);
	for (IRuntimeProcessListiner runtimeProcessListiner : processListiners) {
	    runtimeProcessRuner.addProcessListiner(runtimeProcessListiner);
	}
	runtimeProcessRuner.startTask();
    }
    
    public String getServerLog() {
	if(runtimeProcessByteArrayListiner != null) {
	    return runtimeProcessByteArrayListiner.getOutputStream().toString();
	}	
	return "";
    }
    
    @PreDestroy
    public void stopMongoDBServer() throws Exception {
	runtimeProcessRuner.stopTask();
    }
    
    public List<String> getDataBasesList() {
	List<String> databasesList = new ArrayList<String>();
	MongoClient mongoClient = getMongoClient();
	for (String name: mongoClient.listDatabaseNames()) {
	    databasesList.add(name);
	}
	
	return databasesList;
    }
    
    public MongoDatabase createDataBase(String dataBaseName) {
	if(dataBaseName.equals("")) {
	    return null;
	}
	MongoClient mongoClient = getMongoClient();
	MongoDatabase database = mongoClient.getDatabase(dataBaseName);
	return database;	
    }
    
    public boolean dropDataBase(String dataBaseName) {
	MongoClient mongoClient = getMongoClient();
	MongoDatabase database = mongoClient.getDatabase(dataBaseName);
	try {
	    database.drop();
	}
	catch (Exception e) {
	    System.out.println(e);
	}
		    
	return true;
    }    
    
    public MongoCollection<Document> getCollection(String databaseName, String collectionName ){
	MongoClient mongoClient = getMongoClient();
	
	MongoDatabase database = mongoClient.getDatabase(databaseName);
	return database.getCollection(collectionName);	
    }
    
    public Document testMongoDbConnection() {
	
	MongoClient mongoClient = getMongoClient();
	
	MongoDatabase database = mongoClient.getDatabase("testdb");
	Document buildInfo = database.runCommand(new Document("buildInfo", 1));
	    
	MongoCollection<Document> collection = database.getCollection("test");
	// insert a document
	Document document = new Document("name", "MongoDB")
			.append("type", "database")
			.append("count", 1)
			.append("info", new Document("x", 203).append("y", 102));
	collection.insertOne(document);
	    
	// find documents
	List<Document> foundDocument = collection.find().into(new ArrayList<Document>());
	System.out.println(foundDocument);
	
	return buildInfo;
    }       
    
}

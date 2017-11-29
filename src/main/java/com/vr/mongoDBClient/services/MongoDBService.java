package com.vr.mongoDBClient.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PreDestroy;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vr.mongoDBClient.services.runtimeProcessRuner.RuntimeProcessByteArrayListiner;
import com.vr.mongoDBClient.services.runtimeProcessRuner.IRuntimeProcessListiner;
import com.vr.mongoDBClient.services.runtimeProcessRuner.RuntimeProcessRuner;

import lombok.Getter;
import lombok.Setter;

@Component
public class MongoDBService {
    
    @Autowired
    Environment environment;
    
    @Value("${mongo.useCustomMongoServer}")
    private boolean useCustomMongoServer;    
    @Value("${mongo.customMongoServer.mongodPath}")
    private @Setter @Getter String mongodPath;
    @Value("${mongo.customMongoServer.dbPath}")
    private @Setter @Getter String dbPath;
    @Value("${mongo.host}")
    private @Setter @Getter String host;
    @Value("${mongo.port}")
    private @Setter @Getter int port;
    @Value("${mongo.db.name}")
    private @Setter @Getter String databaseName;
    
    private RuntimeProcessByteArrayListiner  runtimeProcessByteArrayListiner;    
    private RuntimeProcessRuner runtimeProcessRuner;
    
    public MongoDBService() {
	
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

	if (useCustomMongoServer) {
	    runtimeProcessByteArrayListiner = new RuntimeProcessByteArrayListiner();
	    processListiners.add(runtimeProcessByteArrayListiner);

	    String command = String.format("%smongod.exe --dbpath=%s", mongodPath, dbpath);

	    runtimeProcessRuner = new RuntimeProcessRuner();
	    runtimeProcessRuner.setCommand(command);
	    for (IRuntimeProcessListiner runtimeProcessListiner : processListiners) {
		runtimeProcessRuner.addProcessListiner(runtimeProcessListiner);
	    }
	    runtimeProcessRuner.startTask();
	}
    }
    
    public String getServerLog() {
	if(runtimeProcessByteArrayListiner != null) {
	    return runtimeProcessByteArrayListiner.getOutputStream().toString();
	}	
	return "";
    }
    
    @PreDestroy
    public void stopMongoDBServer() throws Exception {
	if (useCustomMongoServer) {
	    runtimeProcessRuner.stopTask();
	}
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
	
	setDatabaseName(dataBaseName);
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

	setDatabaseName("");
	return true;
    }    
    
    public MongoCollection<Document> getCollection(String collectionName){
	MongoClient mongoClient = getMongoClient();
	
	MongoDatabase database = mongoClient.getDatabase(databaseName);
	return database.getCollection(collectionName);	
    }
    
    public ArrayList<Document> getDocumentListByAgregate(String collectionName, List<Bson> aggregateList){	
	return getCollection(collectionName).aggregate(aggregateList).into(new ArrayList<Document>());	
    }
    
    public Document testMongoDbConnection() {
	
	MongoClient mongoClient = getMongoClient();
	
	MongoDatabase database = mongoClient.getDatabase("testdb");
	Document buildInfo = database.runCommand(new Document("buildInfo", 1));
	    	    	
	return buildInfo;
    }
    
}

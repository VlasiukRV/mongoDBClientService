package com.vr.mongoDBClient.services.mongoDBService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PreDestroy;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vr.mongoDBClient.services.mongoDBService.runtimeProcessRuner.RuntimeProcessByteArrayListener;
import com.vr.mongoDBClient.services.mongoDBService.runtimeProcessRuner.RuntimeProcessRuner;

import lombok.Getter;
import lombok.Setter;

@Component
public class MongoDBService implements IMongoDBService{
        
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
    
    private RuntimeProcessByteArrayListener  runtimeProcessByteArrayListener;    
    private RuntimeProcessRuner runtimeProcessRuner;
    
    public MongoDBService() {
	
    }
    
    @Override
    public MongoClient getMongoClient() {
	return new MongoClient(this.host, this.port);
    }
    
    @Override
    public void startMongoDBServer() throws Exception {
	Set<IRuntimeProcessListener> processListeners = new HashSet<>();
	startMongoDBServer(this.mongodPath, this.dbPath, processListeners);
    }

    @Override
    public void startMongoDBServer(Set<IRuntimeProcessListener> processListeners) throws Exception {
	startMongoDBServer(this.mongodPath, this.dbPath, processListeners);
    }
    
    @PreDestroy
    @Override
    public void stopMongoDBServer() throws Exception {
	if (useCustomMongoServer) {
	    runtimeProcessRuner.stopTask();
	}
    }
    
    @Override
    public String getServerLog() {
	if(runtimeProcessByteArrayListener != null) {
	    return runtimeProcessByteArrayListener.getOutputStream().toString();
	}	
	return "";
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
	    System.out.println(e);
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
    
    private void startMongoDBServer(String mongodPath, String dbpath, Set<IRuntimeProcessListener> processListeners) throws Exception {

	if (useCustomMongoServer) {
	    runtimeProcessByteArrayListener = new RuntimeProcessByteArrayListener();
	    processListeners.add(runtimeProcessByteArrayListener);

	    String command = String.format("%smongod.exe --dbpath=%s", mongodPath, dbpath);

	    runtimeProcessRuner = new RuntimeProcessRuner();
	    runtimeProcessRuner.setCommand(command);
	    for (IRuntimeProcessListener runtimeProcessListener : processListeners) {
		runtimeProcessRuner.addProcessListener(runtimeProcessListener);
	    }
	    runtimeProcessRuner.startTask();
	}
    }

}

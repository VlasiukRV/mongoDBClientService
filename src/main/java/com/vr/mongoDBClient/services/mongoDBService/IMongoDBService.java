package com.vr.mongoDBClient.services.mongoDBService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public interface IMongoDBService {
    public MongoClient getMongoClient();
    public void startMongoDBServer() throws Exception;
    public void startMongoDBServer(Set<IRuntimeProcessListener> processListeners) throws Exception;
    public void stopMongoDBServer() throws Exception;
    public String getServerLog();
    public List<String> getDataBasesList();
    public MongoDatabase createDataBase(String dataBaseName);
    public boolean dropDataBase(String dataBaseName);
    public MongoCollection<Document> getCollection(String collectionName);
    public ArrayList<Document> getDocumentListByAgregate(String collectionName, List<Bson> aggregateList);
    public Document testMongoDbConnection();
}

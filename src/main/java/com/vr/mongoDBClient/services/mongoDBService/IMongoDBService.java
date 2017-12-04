package com.vr.mongoDBClient.services.mongoDBService;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public interface IMongoDBService {
    public MongoClient getMongoClient();
    public List<String> getDataBasesList();
    public MongoDatabase createDataBase(String dataBaseName);
    public boolean dropDataBase(String dataBaseName);
    public MongoCollection<Document> getCollection(String collectionName);
    public ArrayList<Document> getDocumentListByAgregate(String collectionName, List<Bson> aggregateList);
    public Document testMongoDbConnection();
}

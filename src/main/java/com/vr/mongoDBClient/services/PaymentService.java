package com.vr.mongoDBClient.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.vr.mongoDBClient.entity.Customer;
import com.vr.mongoDBClient.entity.Payment;
import com.vr.mongoDBClient.services.sqlExecuter.MongoDBSQLExecuter;

@Component
public class PaymentService {
    
    private String databaseName = "testbd";
    private String collectionName = "payment";
    
    @Autowired
    MongoDBService mongoDBService;
    
    @Autowired
    MongoDBSQLExecuter sqlExecuter;
    
    public void generateTestPayments() {
	
	Payment payment1 = new Payment("payment1", 100, 6);
	payment1.setCustomer(new Customer("Customer1"));
	payment1.setId("1");
	addPayment(payment1);
	
	Payment payment2 = new Payment("payment1", 200, 10);
	payment2.setCustomer(new Customer("Customer2"));
	payment2.setId("2");
	addPayment(payment2);
    }    
    
    public List<Document> getPayments() {
	return getpaymentCollection().find().into(new ArrayList<Document>());
    }
    
    public void updatePayment() {
	 Document updateQuery = new Document("id", "1");
	 getpaymentCollection().updateOne(updateQuery, new Document("$set", new Document("description", "car arenda")));
    }
    
    public List<Document> executeQuery(String query) {
	
	return getpaymentCollection().find().into(new ArrayList<Document>());
    }
    
    private void addPayment(Payment payment) {
	Document documentCustomer = new Document("name", payment.getCustomer().getName());
	    
	Document documentPayment = new Document("id", payment.getId())
			.append("description", payment.getDescription())			
			.append("customer", documentCustomer)
			.append("amount", payment.getAmount())
			.append("commission", payment.getCommission());
	
	getpaymentCollection().insertOne(documentPayment);	
    }
    
    private MongoCollection<Document> getpaymentCollection(){
	MongoClient mongoClient = mongoDBService.getMongoClient();
	
	MongoDatabase database = mongoClient.getDatabase(databaseName);
	return database.getCollection(collectionName);	
    }
}

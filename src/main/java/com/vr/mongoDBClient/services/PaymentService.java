package com.vr.mongoDBClient.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;
import com.vr.mongoDBClient.entity.Customer;
import com.vr.mongoDBClient.entity.Payment;
import com.vr.mongoDBClient.services.mongoDBService.MongoDBService;

import lombok.Getter;
import lombok.Setter;

/**
 * Payment service
 *
 * @author Roman Vlasiuk
 */
@Component
public class PaymentService {
      
    @Autowired
    private MongoDBService mongoDBService;
        
    private @Getter @Setter String collectionName = "payment";
    
    public PaymentService() {
	
    }  
    
    public void generateTestPayments() {
	
	List<Payment> payments = new ArrayList<>();
	Random random = new Random();
	for (int i = 1; i < 100; i++) {
	    int amount = random.nextInt(1000000);
	    int commission = Math.round(amount * random.nextInt(100) / 100);
	    int customerIndex = random.nextInt(6);
	    Payment payment1 = new Payment("payment" + i, amount, commission);
	    payment1.setCustomer(new Customer("Customer" + customerIndex));
	    payments.add(payment1);
	}
	addPayments(payments);
    }    
        
    public void updatePayment() {
	 Document updateQuery = new Document("id", "1");
	 getpPaymentCollection().updateOne(updateQuery, new Document("$set", new Document("description", "car arenda")));
    }
        
    public List<Document> getPayments() {
	return getpPaymentCollection().find().into(new ArrayList<Document>());
    }
    
    public void addPayments(List<Payment> payments) {
	List<Document> documetsList = new ArrayList<>();
	for (Payment payment : payments) {
	    Document paymentDocument = getNewPaymentDocument(payment);
	    documetsList.add(paymentDocument);
	}
	getpPaymentCollection().insertMany(documetsList);
    }
    
    private void addPayment(Payment payment) {
	getpPaymentCollection().insertOne(getNewPaymentDocument(payment));	
    }
        
    private MongoCollection<Document> getpPaymentCollection(){
	return mongoDBService.getCollection(collectionName);
    }
    
    private Document getNewPaymentDocument(Payment payment) {
	Document documentCustomer = new Document("name", payment.getCustomer().getName());
	    
	return new Document("description", payment.getDescription())			
			.append("customer", documentCustomer)
			.append("amount", payment.getAmount())
			.append("commission", payment.getCommission());
    }
    
}

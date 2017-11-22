package com.vr.mongoDBClient.entity;

public class Payment {
    private String id;
    private String description;
    private int amount;
    private Customer customer;
    
    public Payment(String description, int amount) {
	super();
	this.description = description;
	this.amount = amount;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }    
}

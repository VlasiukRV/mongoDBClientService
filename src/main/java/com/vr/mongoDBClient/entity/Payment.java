package com.vr.mongoDBClient.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class Payment {
    
    private @Getter @Setter String description;
    private @Getter @Setter int amount;
    private @Getter @Setter int commission;
    private @Getter @Setter Customer customer;
    
    public Payment(String description, int amount, int commission) {
	super();
	this.description = description;
	this.amount = amount;
	this.commission = commission;
    }
    
}

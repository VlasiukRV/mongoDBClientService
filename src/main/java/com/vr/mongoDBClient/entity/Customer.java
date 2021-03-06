package com.vr.mongoDBClient.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class Customer {
    private @Setter @Getter String name;
    private @Setter @Getter String accountNumber;
    
    public Customer() {}

    public Customer(String name) {
        this.name = name;
    }
           
    @Override
    public String toString() {
        return String.format(
                "Customer[name='%s']",
                name);
    }
}

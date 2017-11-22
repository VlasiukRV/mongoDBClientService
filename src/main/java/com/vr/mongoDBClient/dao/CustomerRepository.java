package com.vr.mongoDBClient.dao;

import java.util.List;

import com.vr.mongoDBClient.entity.Customer;

public interface CustomerRepository /*extends MongoRepository<Customer, String>*/ {
    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);
}

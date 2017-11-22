package com.vr.mongoDBClient.entity;


/*import org.springframework.data.annotation.Id;*/

public class Customer {
    /*@Id*/
    private String id;
    private String name;
    
    public Customer() {}

    public Customer(String name) {
        this.name = name;
    }
       
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, name='%s']",
                id, name);
    }
}

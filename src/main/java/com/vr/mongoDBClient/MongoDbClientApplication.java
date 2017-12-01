package com.vr.mongoDBClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.vr.mongoDBClient")
public class MongoDbClientApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MongoDbClientApplication.class);
    }
    
    public static void main(String[] args) {
	SpringApplication.run(MongoDbClientApplication.class, args);
    }
}

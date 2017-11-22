package com.vr.mongoDBClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.vr.mongoDBClient")
public class MongoDbClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoDbClientApplication.class, args);
	}
}

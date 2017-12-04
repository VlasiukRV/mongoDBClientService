package com.vr.mongoDBClient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vr.mongoDBClient.services.sqlExecutor.ISQLRuner;
import com.vr.mongoDBClient.services.sqlExecutor.mongo.MongoDBSQLRuner;

@Configuration
public class ConfigAppBean {
    @Bean 
    public ISQLRuner getSQLRuner() {
	return new MongoDBSQLRuner();
    }

}

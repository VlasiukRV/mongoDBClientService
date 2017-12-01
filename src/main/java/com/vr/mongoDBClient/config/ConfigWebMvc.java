package com.vr.mongoDBClient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.vr.mongoDBClient.services.sqlExecutor.ISQLRuner;
import com.vr.mongoDBClient.services.sqlExecutor.mongo.MongoDBSQLRuner;

@Configuration
public class ConfigWebMvc extends WebMvcConfigurerAdapter{
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/").setViewName("index.html");
    	registry.addViewController("/mongoDbManagment").setViewName("mongoDbManagment.html");
    	registry.addViewController("/paymentManagment").setViewName("paymentManagment.html");
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
        	.addResourceHandler("")
        	.addResourceLocations("/resources/")
                .setCachePeriod(3600)
                .resourceChain(true);
    }
    
    @Bean 
    public ISQLRuner getSQLRuner() {
	return new MongoDBSQLRuner();
    }
        
}

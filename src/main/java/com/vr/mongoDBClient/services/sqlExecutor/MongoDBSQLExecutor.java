package com.vr.mongoDBClient.services.sqlExecutor;

import java.text.ParseException;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vr.mongoDBClient.services.mongoDBService.MongoDBService;
import com.vr.mongoDBClient.services.sqlExecutor.mongo.MongoDBSQLExecutorSelect;

@Component
public class MongoDBSQLExecutor {
    
    @Autowired
    private MongoDBService mongoDBService;
    
    public MongoDBSQLExecutor() {
	
    }
    
    public SQLResult runSqlQuery (String query) throws ParseException{
	ISQLExecutor sqlExecutor = getSqlExecutor(query);
	if(sqlExecutor != null) {
	    return sqlExecutor.returnResult();
	}
	return null;
    }
    
    private ISQLExecutor getSqlExecutor(String query) {
	if (isSelectQuery(query)) {
	    return new MongoDBSQLExecutorSelect(mongoDBService);
	}	
	return null;
    }
    
    public boolean isSelectQuery(String query) {
	return true;
    }
}

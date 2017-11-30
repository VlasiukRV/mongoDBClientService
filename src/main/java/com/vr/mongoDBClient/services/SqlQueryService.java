package com.vr.mongoDBClient.services;

import java.text.ParseException;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.vr.mongoDBClient.services.sqlExecutor.MongoDBSQLExecutor;
import com.vr.mongoDBClient.services.sqlExecutor.SQLResult;

@Component
public class SqlQueryService {

    @Autowired
    MongoDBSQLExecutor sqlExecutor;

    public SqlQueryService() {
	
    }
    
    public SQLResult runSqlQuery(@RequestParam String query) throws ParseException {
	return sqlExecutor.runSqlQuery(query);
    }
    
}

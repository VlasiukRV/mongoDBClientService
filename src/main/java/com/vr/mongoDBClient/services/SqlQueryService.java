package com.vr.mongoDBClient.services;

import java.text.ParseException;
import java.util.ArrayList;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.vr.mongoDBClient.services.sqlExecuter.MongoDBSQLExecuter;

@Component
public class SqlQueryService {

    private String databaseName = "testbd";
    
    @Autowired
    MongoDBSQLExecuter sqlExecuter;

    public ArrayList<Document> runSqlQuery(@RequestParam String query) throws ParseException {
	return sqlExecuter.executeSQLQuery(databaseName, query);
    }
    
}

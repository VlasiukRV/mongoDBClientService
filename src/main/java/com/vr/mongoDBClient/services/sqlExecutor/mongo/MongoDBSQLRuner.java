package com.vr.mongoDBClient.services.sqlExecutor.mongo;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.vr.mongoDBClient.services.mongoDBService.MongoDBService;
import com.vr.mongoDBClient.services.sqlExecutor.ISQLExecutor;
import com.vr.mongoDBClient.services.sqlExecutor.ISQLRuner;
import com.vr.mongoDBClient.services.sqlExecutor.SQLResult;
import com.vr.mongoDBClient.services.sqlExecutor.SQLRunerUtil;

public class MongoDBSQLRuner implements ISQLRuner{
    
    @Autowired
    private MongoDBService mongoDBService;
    
    public MongoDBSQLRuner() {
	
    }
    
    @Override
    public SQLResult runQuery (String query) throws ParseException{
	ISQLExecutor sqlExecutor = getSqlExecutor(query);
	if(sqlExecutor != null) {
	    sqlExecutor.executeSQLQuery(query);
	    return sqlExecutor.returnResult();
	}
	return new SQLResult<String>();
    }
    
    private ISQLExecutor getSqlExecutor(String query) {
	if (SQLRunerUtil.isSelectQuery(query)) {
	    return new MongoDBSQLExecutorSelect(mongoDBService);
	}	
	return null;
    }
    
}

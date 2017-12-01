package com.vr.mongoDBClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vr.mongoDBClient.controllers.SqlQueryController;
import com.vr.mongoDBClient.services.PaymentService;
import com.vr.mongoDBClient.services.mongoDBService.IMongoDBServer;
import com.vr.mongoDBClient.services.mongoDBService.MongoDBService;
import com.vr.mongoDBClient.services.sqlExecutor.ISQLRuner;
import com.vr.mongoDBClient.services.sqlExecutor.SQLResult;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLLiteral;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLParserSelect;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection.FieldSorting;
import com.vr.mongoDBClient.sqlParser.sqlSelect.SQLParserSelectTestQuery;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDbClientApplicationTests {

    @Autowired
    private IMongoDBServer mongodbServer;

    @Autowired
    private MongoDBService mongodbService;
    
    @Autowired
    private PaymentService paymentService; 
    
    @Autowired
    ISQLRuner sqlExecutor;
    
    @Autowired
    private SqlQueryController sqlQueryController;
        
    @Value("${mongo.useCustomMongoServer}")
    private boolean useCustomMongoServer;    
    
    private List<SQLParserSelectTestQuery>  testQueries = new ArrayList<>();
    
    @Before
    public void makeTestQuery() { 
	testQueries.add(getTestQuery1());
	testQueries.add(getTestQuery2());
    }
    
    @Test
    public void contextLoads() throws Exception {
	assertThat(sqlQueryController).isNotNull();
    }
    
    @Test
    public void testProjections() throws Exception {        
	
	for (SQLParserSelectTestQuery testQuery : testQueries) {
	    SQLParserSelect sqlParserSelect = new SQLParserSelect();
	    sqlParserSelect.compileSQLQuery(testQuery.getQuery());
	    List<String> parserFieldList = sqlParserSelect.getProjections().getFields();
	    assertEquals(testQuery.getFieldList(), parserFieldList);
	}
				
    }

    @Test
    public void testTarget() throws Exception {        
	
	for (SQLParserSelectTestQuery testQuery : testQueries) {
	    SQLParserSelect sqlParserSelect = new SQLParserSelect();
	    sqlParserSelect.compileSQLQuery(testQuery.getQuery());
	    String target = sqlParserSelect.getTarget().getTarget();
	    assertEquals(testQuery.getTarget(), target);
	}
				
    }

    @Test
    public void testOrderBy() throws Exception {        
	
	for (SQLParserSelectTestQuery testQuery : testQueries) {
	    SQLParserSelect sqlParserSelect = new SQLParserSelect();
	    sqlParserSelect.compileSQLQuery(testQuery.getQuery());
	    List<FieldSorting> fieldsOrderBy = sqlParserSelect.getOrderByField().getFields();
	    assertEquals(testQuery.getFieldsOrderBy(), fieldsOrderBy);
	}
				
    }
    
    @Test
    public void testMongoDBSQLEecuterSelect() {
	
	if (useCustomMongoServer) {
	    try {
		mongodbServer.startMongoDBServer();
		Thread.sleep(3000);
	    } catch (Exception e1) {
		e1.printStackTrace();
		return;
	    }
	}

	mongodbService.setDatabaseName("testDB");
	paymentService.generateTestPayments();
	List<Document> documents1 = paymentService.getPayments();
	List<Document> documents2 = new ArrayList<>();

	try {
	    SQLResult<List<Document>> sqlResult = sqlExecutor
		    .runQuery("SELECT _id, amount, commission, customer, description FROM " + paymentService.getCollectionName() + ";");
	    documents2 = sqlResult.getResult();
	    mongodbService.dropDataBase(mongodbService.getDatabaseName());
	    Thread.sleep(3000);
	    if (useCustomMongoServer) {
		mongodbServer.stopMongoDBServer();
	    }
	} catch (ParseException | InterruptedException | IOException e) {
	    e.printStackTrace();
	}
	assertEquals(documents1, documents2);
	
    }
    
    private SQLParserSelectTestQuery getTestQuery1() {
	List<String> fieldList = new ArrayList<>();
	List<FieldSorting> fieldsOrderBy = new ArrayList<>();
	
	SQLParserSelectTestQuery testQuery = new SQLParserSelectTestQuery();		
	testQuery.setFieldList(fieldList);	
	testQuery.setFieldsOrderBy(fieldsOrderBy);
	
	testQuery.setQuery("SELECT field1, field2 FROM table ORDER BY field1, field2 DESC;");
	testQuery.setTarget("table");
	fieldList.add("field1");
	fieldList.add("field2");			
	fieldsOrderBy.add(new FieldSorting("field1", SQLLiteral.ASC));
	fieldsOrderBy.add(new FieldSorting("field2", SQLLiteral.DESC));
	
	return testQuery;
    }

    private SQLParserSelectTestQuery getTestQuery2() {
	List<String> fieldList = new ArrayList<>();
	List<FieldSorting> fieldsOrderBy = new ArrayList<>();
	
	SQLParserSelectTestQuery testQuery = new SQLParserSelectTestQuery();		
	testQuery.setFieldList(fieldList);	
	testQuery.setFieldsOrderBy(fieldsOrderBy);
	
	testQuery.setQuery("SELECT * FROM table1;");	
	testQuery.setTarget("table1");
		
	return testQuery;
    }
    
    
}

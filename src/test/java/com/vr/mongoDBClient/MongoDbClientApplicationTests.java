package com.vr.mongoDBClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vr.mongoDBClient.controllers.SqlQueryController;
import com.vr.mongoDBClient.entity.Customer;
import com.vr.mongoDBClient.entity.Payment;
import com.vr.mongoDBClient.services.MongoDBService;
import com.vr.mongoDBClient.services.PaymentService;
import com.vr.mongoDBClient.services.sqlExecuter.MongoDBSQLExecuterSelect;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiteral;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLParserSelect;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.FieldSorting;
import com.vr.mongoDBClient.sqlParser.sqlSelect.SQLParserSelectTestQuery;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDbClientApplicationTests {

    @Autowired
    private MongoDBService mongodbService;
    
    @Autowired
    private PaymentService paymentService; 
    
    @Autowired
    private MongoDBSQLExecuterSelect sqlExecuterSelect;
    
    @Autowired
    private SqlQueryController sqlQueryController;
        
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
	
	try {
	    mongodbService.startMongoDBServer();
	    Thread.sleep(3000);
	} catch (Exception e1) {
	    e1.printStackTrace();
	    return;
	}
	
	mongodbService.setDatabaseName("testDB");
	paymentService.generateTestPayments();
	List<Document> documents1 = paymentService.getPayments();
	List<Document> documents2 = new ArrayList<>();
	
	try {
	    documents2 = sqlExecuterSelect.executeSQLQuery("SELECT _id, amount, commission, customer, description FROM "+paymentService.getCollectionName()+";");
	    mongodbService.dropDataBase(mongodbService.getDatabaseName());
	    Thread.sleep(3000);
	    mongodbService.stopMongoDBServer();
	} catch (Exception e) {
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

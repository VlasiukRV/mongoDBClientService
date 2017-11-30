package com.vr.mongoDBClient.services.sqlExecutor.mongo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Aggregates;
import com.vr.mongoDBClient.services.mongoDBService.MongoDBService;
import com.vr.mongoDBClient.services.sqlExecutor.ISQLExecutor;
import com.vr.mongoDBClient.services.sqlExecutor.SQLResult;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLLiteral;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLParserSelect;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection.IConditionalExpression;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection.SimpleExpression;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection.TreeExpression;

public class MongoDBSQLExecutorSelect implements ISQLExecutor {

    private MongoDBService mongoDBService;

    private SQLParserSelect sqlParserSelect;    
    private String tableName;
    private List<Bson> aggregateList = new ArrayList<>();
        
    public MongoDBSQLExecutorSelect(MongoDBService mongoDBService) {
	this.mongoDBService = mongoDBService;
    }
    
    @Override
    public void executeSQLQuery(String query) throws ParseException {
	compileSQLQuery(query)
	.setUpTableName()
	.addMatcherToAggregateList()
	.addProjectionToAggregateList()
	.addSortToAggregateList()
	.addSkipToAggregateList()
	.addLimitToAggregateList();
    }

    public SQLResult<List<Document>> returnResult() {	
	return new SQLResult<>(getDocumentList());
    }
    
    private List<Document> getDocumentList() {
	if (mongoDBService == null || tableName.equals("")) {
	    return new ArrayList<>();
	}	
	return mongoDBService.getDocumentListByAgregate(tableName, aggregateList);
    }
    
    private MongoDBSQLExecutorSelect compileSQLQuery(String query) throws ParseException{
	sqlParserSelect = new SQLParserSelect();
	sqlParserSelect.compileSQLQuery(query);	
	return this;
    }
    
    private MongoDBSQLExecutorSelect setUpTableName() {
	tableName = sqlParserSelect.getTarget().getTarget();
	return this;
    }
    
    private MongoDBSQLExecutorSelect addMatcherToAggregateList() {	
	if(sqlParserSelect.getCondition().isUsed()) {
	    this.aggregateList.add(Aggregates.match(getFilterExpression(sqlParserSelect.getCondition().getTreeExpression())));
	}	
	return this;
    }
    
    private MongoDBSQLExecutorSelect addProjectionToAggregateList() {
	List<Bson> projections = new ArrayList<>();
	projections.add(Projections.excludeId());	
	if(sqlParserSelect.getProjections().isUsed()) {
	    projections.add(Projections.include(sqlParserSelect.getProjections().getFields()));
	    
	}
	this.aggregateList.add(Aggregates.project(Projections.fields(projections)));	
	return this;
    }

    private MongoDBSQLExecutorSelect addSortToAggregateList() {	
	if (sqlParserSelect.getOrderByField().isUsed()) {
	    List<Bson> sorts = new ArrayList<>();
	    
	    List<String> ascFieldsStr = sqlParserSelect.getOrderByField().getASCFields();
	    if (!ascFieldsStr.isEmpty()) {
		sorts.add(Sorts.ascending(ascFieldsStr));
	    }
	    List<String> descFieldsStr = sqlParserSelect.getOrderByField().getDESCFields();
	    if (!descFieldsStr.isEmpty()) {
		sorts.add(Sorts.descending(descFieldsStr));
	    }
	    
	    this.aggregateList.add(Aggregates.sort(Sorts.orderBy(sorts)));
	}	
	return this;
    }
     
    private MongoDBSQLExecutorSelect addSkipToAggregateList() {	
	if(sqlParserSelect.getSkipRecords().isUsed()) {
	    this.aggregateList.add(Aggregates.skip(sqlParserSelect.getSkipRecords().getSkip()));    
	}	
	return this;
    }
    
    private MongoDBSQLExecutorSelect addLimitToAggregateList() {
	if(sqlParserSelect.getMaxRecords().isUsed()) {
	    this.aggregateList.add(Aggregates.limit(sqlParserSelect.getMaxRecords().getLimit()));
	}	
	return this;
    }
        
    private Bson getFilterExpression(IConditionalExpression conditionalExpression) {
	if(conditionalExpression != null) {

	    if(conditionalExpression.isSimpleExpression()) {
		SimpleExpression simpleExpression = (SimpleExpression) conditionalExpression;
		String leftValue = simpleExpression.getValueLeft();
		Integer rightValue = Integer.parseInt(simpleExpression.getValueRight());
		/*String rightValue = simpleExpression.getValueRight();*/
		SQLLiteral operator = simpleExpression.getOperator();

		switch (operator) {
		case EQ:
		    return Filters.eq(leftValue, rightValue);
		case GT:
		    return Filters.gt(leftValue, rightValue);
		case GTE:
		    return Filters.gte(leftValue, rightValue);
		case LT:
		    return Filters.lt(leftValue, rightValue);
		case LTE:
		    return Filters.lte(leftValue, rightValue);
		default:
		    break;
		}
	    }else {	    
		TreeExpression simpleExpression = (TreeExpression) conditionalExpression;
		Bson leftValue = getFilterExpression(simpleExpression.getValueLeft());
		Bson rightValue = getFilterExpression(simpleExpression.getValueRight());
		SQLLiteral operator = simpleExpression.getOperator();

		switch (operator) {
		case AND:
		    return Filters.and(leftValue, rightValue);
		case OR:
		    return Filters.or(leftValue, rightValue);
		default:
		    break;
		}
	    }
	}	
	return new Document();		
    }
}

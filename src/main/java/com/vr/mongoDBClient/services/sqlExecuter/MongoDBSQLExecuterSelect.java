package com.vr.mongoDBClient.services.sqlExecuter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Aggregates;

import com.vr.mongoDBClient.services.MongoDBService;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiteral;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLParserSelect;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.IConditionalExpression;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.SimpleExpression;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.TreeExpression;

@Component
public class MongoDBSQLExecuterSelect {

    @Autowired
    MongoDBService mongoDBService;

    @Autowired
    SQLParserSelect sqlParserSelect;

    String testStr = "";
    
    private List<Bson> aggregateList = new ArrayList<>();
    
    public boolean isCuurentCommand(String query) {
	// TODO
	return true;
	/*return sqlParserSelect.isCurrentCommand(query);*/
    }

    public ArrayList<Document> executeSQLQuery(String databaseName, String query) throws ParseException {
	sqlParserSelect.compileSQLQuery(query);

	String collectionName = getCollectionName();
	if (collectionName.equals("")) {
	    return new ArrayList<Document>();
	}
	this.aggregateList = new ArrayList<>();
	addMatcherToAggregateList();
	addProjectionToAggregateList();
	addSortToAggregateList();
	addSkipToAggregateList();
	addLimitToAggregateList();
	/*,Aggregates.group("$stars", Accumulators.sum("count", 1))*/
	
	return mongoDBService.getDocumentListByAgregate(databaseName, collectionName, aggregateList);
    }

    private String getCollectionName() {
	return sqlParserSelect.getTarget().getTarget();
    }

    private void addMatcherToAggregateList() {	
	if(sqlParserSelect.getCondition().isUsed()) {
	    this.aggregateList.add(Aggregates.match(getFilterExpression(sqlParserSelect.getCondition().getTreeExpression())));
	}	
    }
    
    private void addProjectionToAggregateList() {
	List<Bson> projections = new ArrayList<>();
	projections.add(Projections.excludeId());	
	if(sqlParserSelect.getProjections().isUsed()) {
	    projections.add(Projections.include(sqlParserSelect.getProjections().getFields()));
	    
	}
	this.aggregateList.add(Aggregates.project(Projections.fields(projections)));
    }

    private void addSortToAggregateList() {
	
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
    }
     
    private void addSkipToAggregateList() {	
	if(sqlParserSelect.getSkipRecords().isUsed()) {
	    this.aggregateList.add(Aggregates.skip(sqlParserSelect.getSkipRecords().getSkip()));    
	}	
    }
    
    private void addLimitToAggregateList() {
	if(sqlParserSelect.getMaxRecords().isUsed()) {
	    this.aggregateList.add(Aggregates.limit(sqlParserSelect.getMaxRecords().getLimit()));
	}
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

package com.vr.mongoDBClient.services.sqlExecuter;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

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

    public boolean isCuurentCommand(String query) {
	return sqlParserSelect.isCurrentCommand(query);
    }

    public ArrayList<Document> executeSQLQuery(String databaseName, String query) {
	sqlParserSelect.compileSQLQuery(query);

	String collectionName = getCollectionName();
	if (collectionName.equals("")) {
	    return new ArrayList<Document>();
	}

	ArrayList<Document> result = getCollection(databaseName, collectionName)
		.find(getFilter())		
		.projection(getProjection())
		.sort(getSort())
		.skip(getDocumentsToSkip())
		.limit(getMaxRecords())
		.into(new ArrayList<Document>());

	return result;
    }

    MongoCollection<Document> getCollection(String databaseName, String collectionName){
	return mongoDBService.getCollection(databaseName, collectionName);
    }

    private String getCollectionName() {
	return sqlParserSelect.getTarget().getTarget();
    }

    private Bson getFilter() {
	return getFilterExpression(sqlParserSelect.getCondition().getTreeExpression());
    }
        
    private int getDocumentsToSkip() {
	return sqlParserSelect.getSkipRecords().getSkip();
    }
    
    private int getMaxRecords() {
	return sqlParserSelect.getMaxRecords().getLimit();
    }
    
    private Bson getProjection() {
	return Projections.fields(Projections.include(getProjectionList()), Projections.excludeId());
    }

    private Bson getSort() {
	List<Bson> sorts = new ArrayList<>();
	
	List<String> ascFieldsStr = sqlParserSelect.getOrderByField().getASCFields();
	if(!ascFieldsStr.isEmpty()) {
	    sorts.add(Sorts.ascending(ascFieldsStr));
	}
	List<String> descFieldsStr = sqlParserSelect.getOrderByField().getDESCFields();
	if(!descFieldsStr.isEmpty()) {
	    sorts.add(Sorts.descending(descFieldsStr));
	}
	
	return Sorts.orderBy(sorts);
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

    private List<String> getProjectionList() {
	return sqlParserSelect.getProjections().getFields();
    }

}

package com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import com.vr.mongoDBClient.services.sqlExecutor.SQLRunerUtil;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLLiteral;

import lombok.Getter;
import lombok.Setter;

public class SQLSectionWhere extends SQLSection {
    private @Getter String sectionRegex = ".*(?<=)WHERE(.+)(?=)@NEXT_COMMAND@.*";
    
    private String startExpressionBlockRegex = "\\(";
    private String stopExpressionBlockRegex = "\\)";    
    private String operationsRegex = "AND|OR|<>|>=|<=|>|<|=";
    
    private @Getter @Setter IConditionalExpression treeExpression;
    
    public SQLSectionWhere() {
	this.sqlLiteral = SQLLiteral.WHERE;
    }
    
    @Override
    public void compileSection() {
	treeExpression = null;
	treeExpression = buildTreeExpression(this.sectionValue);
    }

    @Override
    public boolean isUsed() {
	return treeExpression != null;
    }
    
    private IConditionalExpression buildTreeExpression(String expression) {
	
	Map<String, Object> expressionBlock = getExpressionBlock(expression);
	if(expressionBlock.get("expressionBlock") != "") {
	    
		SQLLiteral operator = (SQLLiteral)expressionBlock.get("expressionOperator");			
		IConditionalExpression valueLeft = buildTreeExpression((String)expressionBlock.get("expressionLeft"));
		IConditionalExpression valueRight = buildTreeExpression((String)expressionBlock.get("expressionRight"));
		
		TreeExpression treeExpression = new TreeExpression(operator, valueLeft, valueRight);
		return treeExpression;	    
	}
		
	Matcher operationsMatcher = SQLRunerUtil.getMatcher(expression, operationsRegex);
	if(operationsMatcher.find()) {
		String valueLeft = expression.substring(0, operationsMatcher.start());
		String valueRight = expression.substring(operationsMatcher.end()+1, expression.length());
		SQLLiteral operator = getSQLLiteralByName(expression.substring(operationsMatcher.start(), operationsMatcher.end()+1));
		
		IConditionalExpression treeExpression = new SimpleExpression(operator, valueLeft, valueRight);
		return treeExpression;
		
	}
	
	return null;
    }
        
    private Map<String, Object> getExpressionBlock(String expression) {
	Map<String, Object> expressionBlock = new HashMap<>();
	expressionBlock.put("expressionBlock", "");
	expressionBlock.put("expressionLeft", "");
	expressionBlock.put("expressionRight", "");
	expressionBlock.put("expressionOperator", "");
	
	Matcher startSectionMatcher = SQLRunerUtil.getMatcher(expression, startExpressionBlockRegex);
	Matcher stopSectionMatcher = SQLRunerUtil.getMatcher(expression, stopExpressionBlockRegex);
	
	if (startSectionMatcher.find()) {
	    int leftExpresionBegin = startSectionMatcher.start();
	    int leftExpresionEnd = getEndIndexExpressionBlock(expression, startSectionMatcher, stopSectionMatcher);
	    if(leftExpresionBegin == leftExpresionEnd) {
		return expressionBlock;
	    }
	    expressionBlock.put("expressionLeft", expression.substring(leftExpresionBegin+1, leftExpresionEnd));
	    
	    int rightExpresionBegin = leftExpresionEnd;
	    while(leftExpresionEnd >= rightExpresionBegin) {
		if (startSectionMatcher.find()) {
		    rightExpresionBegin = startSectionMatcher.start();
		}else {
		    break;
		}
	    }
	    int rightExpresionEnd = getEndIndexExpressionBlock(expression, startSectionMatcher, stopSectionMatcher);
	    if(leftExpresionBegin == leftExpresionEnd) {
		// TODO
		return expressionBlock;
	    }	    
	    expressionBlock.put("expressionRight", expression.substring(rightExpresionBegin+1, rightExpresionEnd));
	    expressionBlock.put("expressionOperator", getSQLLiteralByName(expression.substring(leftExpresionEnd+1, rightExpresionBegin)));
	    
	    expressionBlock.put("expressionBlock", expression.substring(leftExpresionBegin, rightExpresionEnd+1));
	}
	
	return expressionBlock;
    }
    
    private int getEndIndexExpressionBlock(String expression, Matcher startSectionMatcher, Matcher stopSectionMatcher){
	int startIndex = startSectionMatcher.start();
	int stopIndex = startIndex;
	
	if(stopSectionMatcher.find()) {
	    stopIndex = stopSectionMatcher.start();
	    
	    String expressionBlock = expression.substring(startIndex, stopIndex+1);
	    	    	
	    Matcher startSectionBlockMatcher = SQLRunerUtil.getMatcher(expressionBlock, startExpressionBlockRegex);
	    int i = getMatchCount(startSectionBlockMatcher);
	    
	    Matcher stopSectionBlockMatcher = SQLRunerUtil.getMatcher(expressionBlock, stopExpressionBlockRegex);
	    int ii = getMatchCount(stopSectionBlockMatcher);
	    
	    if(i != ii) {
		stopIndex = getEndIndexExpressionBlock(expression, startSectionMatcher, stopSectionMatcher);
	    }
	}
	
	return stopIndex;
    }
    
    private int getMatchCount(Matcher m){
	int counter = 0;
	while(m.find()) {
	    counter++;
	}
	
	return counter;
    }
    
    private SQLLiteral getSQLLiteralByName(String stringLiteral) {
	stringLiteral = stringLiteral.replaceAll(" ", "").toUpperCase();
	switch (stringLiteral) {
	case "AND":
	    return SQLLiteral.AND;
	case "OR":
	    return SQLLiteral.OR;
	case "=":
	    return SQLLiteral.EQ;
	case ">":
	    return SQLLiteral.GT;
	case ">=":
	    return SQLLiteral.GTE;
	case "<":
	    return SQLLiteral.LT;
	case "<=":
	    return SQLLiteral.LTE;
	default:
	    break;
	}	
	return null;
    }
}

package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiterals;

import lombok.Getter;
import lombok.Setter;

public class SQLSectionWhere extends SQLSection {
    private @Getter String sectionRegex = ".*(?<=)WHERE(.+)(?=)GROUP BY.*";
    
    private String startSectionParamRegex = "\\(";
    private String stopSectionParamRegex = "\\)";    
    private String operationsRegex = "AND|OR|=|<>|>|>=|<|<=";
    
    private @Getter @Setter TreeExpression treeExpression;
    
    public SQLSectionWhere() {
	this.sqlLiteral = SQLLiterals.WHERE;
    }
    
    @Override
    public void compileSection() {
	treeExpression = getTreeExpression(this.sectionValue);
    }

    @Override
    public boolean sectionIsUsed() {
	return false;
    }
    
    private TreeExpression getTreeExpression(String expression) {
	Pattern startSectionPattern = Pattern.compile(startSectionParamRegex);
	Pattern stopSectionPattern = Pattern.compile(stopSectionParamRegex);
	
	String expressionLeft = "";
	String expressionRight = "";
	String expressionOperatorStr = "";
	Matcher startSectionMatcher = startSectionPattern.matcher(expression);
	Matcher stopSectionMatcher = stopSectionPattern.matcher(expression);
	if (startSectionMatcher.find()) {
	    if(stopSectionMatcher.find()) {
		int expressionOperatorBegin = stopSectionMatcher.start()+1;
		expressionLeft = expression.substring(startSectionMatcher.start()+1, stopSectionMatcher.start());
				
		if (startSectionMatcher.find()) {
		    if(stopSectionMatcher.find()) {
			int expressionOperatorEnd = startSectionMatcher.start();
			expressionRight = expression.substring(startSectionMatcher.start()+1, stopSectionMatcher.start());
			expressionOperatorStr = expression.substring(expressionOperatorBegin, expressionOperatorEnd);			
			SQLLiterals expressionOperator = getSQLLiteralByName(expressionOperatorStr);
			
			TreeExpression treeExpression = new TreeExpression();
			treeExpression.setOperator(expressionOperator);			
			treeExpression.setValueLeft(getTreeExpression(expressionLeft));
			treeExpression.setValueRight(getTreeExpression(expressionRight));
			return treeExpression;
		    }
		}		
	    }
	}
	
	Pattern operationsPattern = Pattern.compile(operationsRegex);
	Matcher operationsMatcher = operationsPattern.matcher(expression);
	if(operationsMatcher.find()) {
		String valueLeft = expression.substring(0, operationsMatcher.start());
		String valueRight = expression.substring(operationsMatcher.start(), operationsMatcher.end());
		String operatorStr = expression.substring(operationsMatcher.end(), expression.length());
		
		TreeExpression treeExpression = new TreeExpression();
		treeExpression.setOperator(getSQLLiteralByName(operatorStr));			
		treeExpression.setValueLeft(valueLeft);
		treeExpression.setValueRight(valueRight);
		return treeExpression;
		
	}
	
	return null;
    }
    
    private SQLLiterals getSQLLiteralByName(String stringLiteral) {
	stringLiteral = stringLiteral.replaceAll(" ", "").toUpperCase();
	switch (stringLiteral) {
	case "AND":
	    return SQLLiterals.AND;
	case "OR":
	    return SQLLiterals.OR;
	default:
	    break;
	}	
	return null;
    }
}

package com.vr.mongoDBClient.services.sqlExecutor.sqlParser;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection.SQLSectionFrom;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection.SQLSectionGroupBy;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection.SQLSectionLimit;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection.SQLSectionOrderBy;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection.SQLSectionSelect;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection.SQLSectionSkip;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection.SQLSectionWhere;

@Component
public class SQLParserSelect extends SQLParser {
        
    public SQLParserSelect(){
	super();
    }
    
    @Override
    protected void buildSQLQuerySpecification() {
	super.buildSQLQuerySpecification();
	
	signQuerySection(Arrays.asList(
		SQLSectionSelect.class,
		SQLSectionFrom.class,
		SQLSectionWhere.class,
		SQLSectionGroupBy.class,
		SQLSectionOrderBy.class,
		SQLSectionSkip.class,
		SQLSectionLimit.class
		));
    }
    
    @Override
    protected String getSQLComandRegex() {
	return queryTreeSections.get(SQLLiteral.SELECT).getSectionRegex();
    }
    
    public SQLSectionFrom getTarget() {
	return (SQLSectionFrom) queryTreeSections.get(SQLLiteral.FROM);
    }
    
    public SQLSectionSelect getProjections() {
	return (SQLSectionSelect) queryTreeSections.get(SQLLiteral.SELECT);
    }
    
    public SQLSectionWhere getCondition() {
	return (SQLSectionWhere) queryTreeSections.get(SQLLiteral.WHERE);
    }
    
    public SQLSectionGroupBy getGroupByField() {
	return (SQLSectionGroupBy) queryTreeSections.get(SQLLiteral.GROUPBY);
    }
    
    public SQLSectionOrderBy getOrderByField() {
	return (SQLSectionOrderBy) queryTreeSections.get(SQLLiteral.ORDERBY);
    }
    
    public SQLSectionSkip getSkipRecords() {
	return (SQLSectionSkip) queryTreeSections.get(SQLLiteral.SKIP);
    }
    
    public SQLSectionLimit getMaxRecords() {
	return (SQLSectionLimit) queryTreeSections.get(SQLLiteral.LIMIT);		
    }
}

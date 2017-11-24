package com.vr.mongoDBClient.services.sqlExecuter.sqlParser;

import org.springframework.stereotype.Component;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.SQLSectionFrom;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.SQLSectionGroupBy;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.SQLSectionLimit;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.SQLSectionOrderBy;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.SQLSectionSelect;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.SQLSectionSkip;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.SQLSectionWhere;

@Component
public class SQLParserSelect extends SQLParser {
        
    public SQLParserSelect(){
	super();
    }
    
    @Override
    protected void buildSQLQuerySpecification() {
	super.buildSQLQuerySpecification();
	
	signQuerySection(SQLSectionSelect.class);
	signQuerySection(SQLSectionFrom.class);
	signQuerySection(SQLSectionWhere.class);
	signQuerySection(SQLSectionGroupBy.class);
	signQuerySection(SQLSectionOrderBy.class);
	signQuerySection(SQLSectionSkip.class);
	signQuerySection(SQLSectionLimit.class);		
    }
    
    @Override
    protected String getSQLComandRegex() {
	return queryTreeSections.get(SQLLiterals.SELECT).getSectionRegex();
    }
    
    public SQLSectionFrom getTarget() {
	return (SQLSectionFrom) queryTreeSections.get(SQLLiterals.FROM);
    }
    
    public SQLSectionSelect getProjections() {
	return (SQLSectionSelect) queryTreeSections.get(SQLLiterals.SELECT);
    }
    
    public SQLSectionWhere getCondition() {
	return (SQLSectionWhere) queryTreeSections.get(SQLLiterals.WHERE);
    }
    
    public SQLSectionGroupBy getGroupByField() {
	return (SQLSectionGroupBy) queryTreeSections.get(SQLLiterals.GROUPBY);
    }
    
    public SQLSectionOrderBy getOrderByField() {
	return (SQLSectionOrderBy) queryTreeSections.get(SQLLiterals.ORDERBY);
    }
    
    public SQLSectionSkip getSkipRecords() {
	return (SQLSectionSkip) queryTreeSections.get(SQLLiterals.SKIP);
    }
    
    public SQLSectionLimit getMaxRecords() {
	return (SQLSectionLimit) queryTreeSections.get(SQLLiterals.LIMIT);		
    }
}

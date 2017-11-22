package com.vr.mongoDBClient.services.sqlExecuter.sqlParser;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.AbstractSQLSection;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractSQLParser implements IsqlParser{
    protected @Getter @Setter String query = "";
    protected @Getter @Setter Map<SQLLiterals, AbstractSQLSection>queryTreeSections = new TreeMap<>();

    public AbstractSQLParser() {
	buildSQLQuerySpecification();
    }
    
    @Override
    public void compileSQLQuery(String query) {
	this.query = query;	
	buildQueryTreeSections();
    }   
    
    public boolean isCurrentCommand(String query) {
	String sqlComandRegex = getSQLComandRegex();
	if(sqlComandRegex.equals("")) {
	    return false;
	}
	
	Pattern pattern = Pattern.compile(sqlComandRegex);
	Matcher matcher = pattern.matcher(query);	 
	return matcher.find();	
    }

    protected abstract String getSQLComandRegex();
    
    protected void buildSQLQuerySpecification() {
	queryTreeSections.clear();
    }
    
    protected void buildQueryTreeSections() {
	queryTreeSections.forEach((sqlSection, section)->{
	    Pattern pattern = Pattern.compile(section.getSectionRegex());
	    Matcher matcher = pattern.matcher(query);
	    if (matcher.find()) {
		section.setSectionValue(matcher.group(1));
		section.compileSection();
	    }
	});	
    }        

    protected void signQuerySection(Class<? extends AbstractSQLSection> className) {	
	try {
	    AbstractSQLSection section;
	    section = (AbstractSQLSection)className.newInstance();
	    queryTreeSections.put(section.getSqlLiteral(), section);
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	}	
    }
    
}

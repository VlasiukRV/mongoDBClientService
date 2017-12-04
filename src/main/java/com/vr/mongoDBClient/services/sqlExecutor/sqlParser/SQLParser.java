package com.vr.mongoDBClient.services.sqlExecutor.sqlParser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;

import com.vr.mongoDBClient.services.sqlExecutor.SQLRunerUtil;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection.SQLSection;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstract SQL parser
 *
 * @author Roman Vlasiuk
 */
public abstract class SQLParser implements ISQLParser{
    protected @Getter @Setter String query = "";    
    protected @Getter @Setter Map<SQLLiteral, SQLSection>queryTreeSections = new TreeMap<>();
    
    private ArrayList<String> sectionsSequence;
    private String sectionsregexp;
    
    public SQLParser() {
	buildSQLQuerySpecification();
    }
    
    @Override
    public void compileSQLQuery(String query) throws ParseException{
	this.query = query;	
	buildQueryTreeSections();
    }   
    
    public boolean isCurrentCommand(String query) {
	String sqlComandRegex = getSQLComandRegex();
	if(sqlComandRegex.equals("")) {
	    return false;
	}
	
	return SQLRunerUtil.getMatcher(query, sqlComandRegex).find();	
    }

    protected abstract String getSQLComandRegex();
    
    protected void buildSQLQuerySpecification() {
	queryTreeSections.clear();
    }

    private void buildSectionsSequence() {	
	sectionsSequence = new ArrayList<>();
	Matcher matcher = SQLRunerUtil.getMatcher(query, sectionsregexp);
	while (matcher.find()) {
	    sectionsSequence.add(matcher.group(1).toUpperCase());
	}
	sectionsSequence.add(";");
    }
    
    private String getNextSectionCommand(SQLLiteral literal) {
	    int currenttSectionIndex = sectionsSequence.indexOf(literal.getName());
	    return sectionsSequence.get(currenttSectionIndex+1);
    }
    
    protected void buildQueryTreeSections() throws ParseException{
	buildSQLQuerySpecification();
	buildSectionsSequence();

	queryTreeSections.forEach((sqlSection, section) -> {
	    String nextSectionCommand = getNextSectionCommand(section.getSqlLiteral());
	    String currenSectionRegex = section.getSectionRegex().replaceAll("@NEXT_COMMAND@", nextSectionCommand);

	    Matcher matcher = SQLRunerUtil.getMatcher(query, currenSectionRegex);
	    if (matcher.find()) {
		section.setSectionValue(matcher.group(1));
		try {
		    section.compileSection();
		} catch (ParseException ex) {
		    throw new RuntimeException(ex);
		}
	    }
	});

    }
    
    protected void signQuerySection(List<Class<? extends SQLSection>> classList) {
	sectionsregexp = "";
	for (Class<? extends SQLSection> claz : classList) {
	    signQuerySection(claz);
	}
	if(!sectionsregexp.equals("")) {
	    sectionsregexp = "\\s*("+sectionsregexp+")\\s*";
	}
    }
    
    private void signQuerySection(Class<? extends SQLSection> className) {	
	try {
	    SQLSection section;
	    section = (SQLSection)className.newInstance();
	    queryTreeSections.put(section.getSqlLiteral(), section);
	    if(!sectionsregexp.equals("")) {
		sectionsregexp = sectionsregexp + "|" + section.getSqlLiteral().getName();
	    }else {
		sectionsregexp = section.getSqlLiteral().getName();
	    }
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	}	
    }
    
}

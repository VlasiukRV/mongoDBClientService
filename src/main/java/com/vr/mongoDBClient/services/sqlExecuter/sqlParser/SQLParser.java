package com.vr.mongoDBClient.services.sqlExecuter.sqlParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingConstants;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.SQLSection;

import lombok.Getter;
import lombok.Setter;

public abstract class SQLParser implements ISQLParser{
    protected @Getter @Setter String query = "";    
    protected @Getter @Setter Map<SQLLiteral, SQLSection>queryTreeSections = new TreeMap<>();
    
    private ArrayList<String> sectionsSequence;
    private String sectionsregexp;
    
    public SQLParser() {
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

    private void buildSectionsSequence() {	
	sectionsSequence = new ArrayList<>();
	Pattern pattern = Pattern.compile(sectionsregexp);
	Matcher matcher = pattern.matcher(query);
	while (matcher.find()) {
	    sectionsSequence.add(matcher.group(1));
	}
	sectionsSequence.add(";");
    }
    
    private String getNextSectionCommand(SQLLiteral literal) {
	    int currenttSectionIndex = sectionsSequence.indexOf(literal.getName());
	    return sectionsSequence.get(currenttSectionIndex+1);
    }
    
    protected void buildQueryTreeSections() {
	buildSectionsSequence();

	queryTreeSections.forEach((sqlSection, section) -> {
	    String nextSectionCommand = getNextSectionCommand(section.getSqlLiteral());
	    String currenSectionRegex = section.getSectionRegex().replaceAll("@NEXT_COMMAND@", nextSectionCommand);

	    Pattern pattern = Pattern.compile(currenSectionRegex);
	    Matcher matcher = pattern.matcher(query);
	    if (matcher.find()) {
		section.setSectionValue(matcher.group(1));
		section.compileSection();
	    }
	});

    }
    
    protected void _buildQueryTreeSections() {
	queryTreeSections.forEach((sqlSection, section)->{
	    Pattern pattern = Pattern.compile(section.getSectionRegex());
	    Matcher matcher = pattern.matcher(query);
	    if (matcher.find()) {
		section.setSectionValue(matcher.group(1));
		section.compileSection();
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

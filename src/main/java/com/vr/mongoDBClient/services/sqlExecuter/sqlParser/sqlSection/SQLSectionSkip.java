package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiterals;

import lombok.Getter;

public class SQLSectionSkip extends AbstractSQLSection {
    private @Getter String sectionRegex = ".*(?<=)SKIP(.+)(?=)LIMIT.*";
    private @Getter String sectionParamRegex = "";
    
    public SQLSectionSkip() {
	this.sqlLiteral = SQLLiterals.SKIP;
    }
    
    @Override
    public void compileSection() {
	Pattern pattern = Pattern.compile(sectionParamRegex);
	Matcher matcher = pattern.matcher(this.sectionValue);
	while (matcher.find()) {
	    
	}	
    }
    
    @Override
    public boolean sectionIsUsed() {
	return false;
    }
    
}

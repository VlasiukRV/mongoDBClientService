package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiterals;

import lombok.Getter;

public class SQLSectionWhere extends SQLSection {
    private @Getter String sectionRegex = ".*(?<=)WHERE(.+)(?=)GROUP BY.*";
    private @Getter String sectionParamRegex = "";

    public SQLSectionWhere() {
	this.sqlLiteral = SQLLiterals.WHERE;
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

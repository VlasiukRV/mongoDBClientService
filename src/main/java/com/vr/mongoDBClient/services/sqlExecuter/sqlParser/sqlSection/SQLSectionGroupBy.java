package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiterals;

import lombok.Getter;

public class SQLSectionGroupBy extends SQLSection {
    private @Getter String sectionRegex = ".*(?<=)GROUP BY(.+)(?=)ORDER BY.*";
    private @Getter String sectionParamRegex = "";

    public SQLSectionGroupBy() {
	this.sqlLiteral = SQLLiterals.GROUPBY;
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

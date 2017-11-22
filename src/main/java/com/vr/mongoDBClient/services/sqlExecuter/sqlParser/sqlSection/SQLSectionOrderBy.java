package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiterals;

import lombok.Getter;

public class SQLSectionOrderBy extends AbstractSQLSection{
    private @Getter String sectionRegex = "\".*(?<=)FROM(.+)(?=)WHERE.*";
    private @Getter String sectionParamRegex = "";

    public SQLSectionOrderBy() {
	this.sqlLiteral = SQLLiterals.ORDERBY;
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

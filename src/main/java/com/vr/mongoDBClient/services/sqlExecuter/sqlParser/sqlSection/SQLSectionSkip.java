package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiteral;

import lombok.Getter;

public class SQLSectionSkip extends SQLSection {
    private @Getter String sectionRegex = ".*(?<=)SKIP(.+)(?=)@NEXT_COMMAND@.*";
    private @Getter String sectionParamRegex = "\\d+";
    
    private @Getter int skip = 0;
    
    public SQLSectionSkip() {
	this.sqlLiteral = SQLLiteral.SKIP;
    }
    
    @Override
    public void compileSection() {
	Pattern pattern = Pattern.compile(sectionParamRegex);
	Matcher matcher = pattern.matcher(this.sectionValue);
	if (matcher.find()) {
	    skip = Integer.parseInt(matcher.group().replaceAll(" ", ""));
	}	
    }
    
    @Override
    public boolean isUsed() {
	return skip != 0;
    }
    
}

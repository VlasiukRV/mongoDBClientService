package com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection;

import java.text.ParseException;
import java.util.regex.Matcher;

import com.vr.mongoDBClient.services.sqlExecutor.SQLRunerUtil;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLLiteral;

import lombok.Getter;

public class SQLSectionLimit extends SQLSection {
    private @Getter String sectionRegex = ".*(?<=)LIMIT(.+)(?=)@NEXT_COMMAND@.*";
    private @Getter String sectionParamRegex = "\\d+";

    private @Getter Integer limit = 0;
    
    public SQLSectionLimit() {
	this.sqlLiteral = SQLLiteral.LIMIT;
    }
    
    @Override
    public void compileSection() throws ParseException {
	Matcher matcher = SQLRunerUtil.getMatcher(this.sectionValue, sectionParamRegex);
	if (matcher.find()) {
	    limit = Integer.parseInt(matcher.group().replaceAll(" ", ""));
	}else {
	    throw new ParseException("Missing LIMIT rows", 0);
	}	
    }
    
    @Override
    public boolean isUsed() {
	return limit != 0;
    }
    
}

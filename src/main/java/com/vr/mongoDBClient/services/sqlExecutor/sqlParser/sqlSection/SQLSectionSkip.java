package com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection;

import java.text.ParseException;
import java.util.regex.Matcher;

import com.vr.mongoDBClient.services.sqlExecutor.SQLRunerUtil;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLLiteral;

import lombok.Getter;

public class SQLSectionSkip extends SQLSection {
    private @Getter String sectionRegex = ".*(?<=)SKIP(.+)(?=)@NEXT_COMMAND@.*";
    private @Getter String sectionParamRegex = "\\d+";
    
    private @Getter int skip = 0;
    
    public SQLSectionSkip() {
	this.sqlLiteral = SQLLiteral.SKIP;
    }
    
    @Override
    public void compileSection() throws ParseException {
	Matcher matcher = SQLRunerUtil.getMatcher(this.sectionValue, sectionParamRegex);
	if (matcher.find()) {
	    skip = Integer.parseInt(matcher.group().replaceAll(" ", ""));
	}else {
	    throw new ParseException("Missing SKIP rows", 0);
	}
    }
    
    @Override
    public boolean isUsed() {
	return skip != 0;
    }
    
}

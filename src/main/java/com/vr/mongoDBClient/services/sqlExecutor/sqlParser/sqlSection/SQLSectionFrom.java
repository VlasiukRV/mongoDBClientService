package com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection;

import java.text.ParseException;
import java.util.regex.Matcher;

import com.vr.mongoDBClient.services.sqlExecutor.SQLRunerUtil;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLLiteral;

import lombok.Getter;

public class SQLSectionFrom extends SQLSection {
    private @Getter String sectionRegex = ".*(?<=)FROM(.+)(?=)@NEXT_COMMAND@.*";
    private @Getter String sectionParamRegex = ".*[^;]+";
    
    private @Getter String target = "";
    
    public SQLSectionFrom() {
	this.sqlLiteral = SQLLiteral.FROM;
    }
    
    @Override
    public void compileSection() throws ParseException {
	target = "";
	Matcher matcher = SQLRunerUtil.getMatcher(this.sectionValue, sectionParamRegex);
	if (matcher.find()) {
	    target = matcher.group().replaceAll(" ", "");
	}
	if(target.equals("")) {
	    throw new ParseException("Table name Missing", 0);
	}
    }
    
    @Override
    public boolean isUsed() {
	return ! target.equals("");
    }
}

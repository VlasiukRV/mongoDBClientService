package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiteral;

import lombok.Getter;

public class SQLSectionLimit extends SQLSection {
    private @Getter String sectionRegex = ".*(?<=)LIMIT(.+)(?=)@NEXT_COMMAND@.*";
    private @Getter String sectionParamRegex = "\\d+";

    private @Getter Integer limit = 0;
    
    public SQLSectionLimit() {
	this.sqlLiteral = SQLLiteral.LIMIT;
    }
    
    @Override
    public void compileSection() {
	Pattern pattern = Pattern.compile(sectionParamRegex);
	Matcher matcher = pattern.matcher(this.sectionValue);
	if (matcher.find()) {
	    limit = Integer.parseInt(matcher.group().replaceAll(" ", ""));
/*	    if(limit == 0) {
		limit = 0;
	    }
*/	}	
    }
    
    @Override
    public boolean isUsed() {
	return limit != 0;
    }
    
}

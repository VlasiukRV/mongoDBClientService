package com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection;

import java.util.regex.Matcher;

import com.vr.mongoDBClient.services.sqlExecutor.SQLRunerUtil;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLLiteral;

import lombok.Getter;

public class SQLSectionGroupBy extends SQLSection {
    private @Getter String sectionRegex = ".*(?<=)GROUP BY(.+)(?=)@NEXT_COMMAND@.*";
    private @Getter String sectionParamRegex = "";

    public SQLSectionGroupBy() {
	this.sqlLiteral = SQLLiteral.GROUPBY;
    }
    
    @Override
    public void compileSection() {
	Matcher matcher = SQLRunerUtil.getMatcher(this.sectionValue, sectionParamRegex);
	while (matcher.find()) {
	    
	}	
    }

    @Override
    public boolean isUsed() {
	return false;
    }
    
}

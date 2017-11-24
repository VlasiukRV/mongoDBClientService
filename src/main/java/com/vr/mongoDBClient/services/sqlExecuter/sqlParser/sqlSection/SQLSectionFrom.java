package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiterals;

import lombok.Getter;

public class SQLSectionFrom extends SQLSection {
    private @Getter String sectionRegex = ".*(?<=)FROM(.+)(?=)WHERE.*";
    private @Getter String sectionParamRegex = ".*[^;]+";
    
    private @Getter String target = "";
    
    public SQLSectionFrom() {
	this.sqlLiteral = SQLLiterals.FROM;
    }
    
    @Override
    public void compileSection() {
	target = "";
	Pattern pattern = Pattern.compile(sectionParamRegex);
	Matcher matcher = pattern.matcher(this.sectionValue);
	if (matcher.find()) {
	    target = matcher.group().replaceAll(" ", "");
	}	
    }
    
    @Override
    public boolean sectionIsUsed() {
	return ! target.equals("");
    }
}

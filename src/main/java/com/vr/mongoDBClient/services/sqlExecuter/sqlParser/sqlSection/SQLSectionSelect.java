package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiterals;

import lombok.Getter;
import lombok.Setter;

public class SQLSectionSelect extends SQLSection{
    private @Getter String sectionRegex = "\\s*(?<=)SELECT(.+)(?=)FROM.*";
    private @Getter String sectionParamRegex = "(\\s*[//*\\.\\w]*\\s*,*)";
    
    private @Getter @Setter List<String> fields = new ArrayList<>();
    
    public SQLSectionSelect() {
	this.sqlLiteral = SQLLiterals.SELECT;
    }
    
    @Override
    public void compileSection() {
	Pattern pattern = Pattern.compile(sectionParamRegex);
	Matcher matcher = pattern.matcher(this.sectionValue);
	fields.clear();
	while (matcher.find()) {	    
	    String field = matcher.group(1);
	    field = field.replaceAll(",", "").replaceAll(" ", "");
	    if(!field.equals("")) {
		fields.add(field);
	    }
	}	
    }
    
    @Override
    public boolean sectionIsUsed() {
	return !fields.isEmpty();
    }
}

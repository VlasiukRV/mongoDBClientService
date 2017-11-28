package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiteral;
import lombok.Getter;
import lombok.Setter;

public class SQLSectionOrderBy extends SQLSection{
    private @Getter String sectionRegex =  ".*(?<=)ORDER BY(.+)(?=)@NEXT_COMMAND@.*";
    private String sectionParamRegex = "((,|^)\\s*\\w+\\s*\\w*)";
    
    private String sortingModifierRegex = "(ASC|DESC)";
    private @Getter @Setter List<FieldSorting> fields = new ArrayList<>();
    
    public SQLSectionOrderBy() {
	this.sqlLiteral = SQLLiteral.ORDERBY;
    }
    
    @Override
    public void compileSection() throws ParseException {
	Pattern pattern = Pattern.compile(sectionParamRegex);
	Matcher matcher = pattern.matcher(this.sectionValue);
	fields.clear();
	if (this.sectionValue.trim().length() == 0) {
	    throw new ParseException("Missing ordering fields", 0);
	} else {
	    while (matcher.find()) {
		String fieldStr = matcher.group(1).replaceAll(",", "").replaceAll(" ", "");
		if (!fieldStr.equals("")) {
		    String sortingModifierStr = "";
		    Pattern patternModifier = Pattern.compile(sortingModifierRegex);
		    Matcher matcherModifier = patternModifier.matcher(fieldStr);
		    if (matcherModifier.find()) {
			sortingModifierStr = matcherModifier.group(1).replaceAll(" ", "");
		    }
		    String fieldName = fieldStr.replaceAll(sortingModifierStr, "");

		    FieldSorting field = new FieldSorting();
		    field.setFieldName(fieldName);
		    field.setSortingModifier(getSortingModifierLiteralByStr(sortingModifierStr));
		    fields.add(field);
		}
	    }
	}
		
    }
    
    @Override
    public boolean isUsed() {
	return ! fields.isEmpty();
    }
    
    public List<String> getASCFields() {
	List<String> resFields = new ArrayList<>();
	fields.stream()
		.filter(
			(fieldSotring) -> 
			fieldSotring.getSortingModifier() == SQLLiteral.ASC)
		.forEach((fieldSotring) ->
			resFields.add(fieldSotring.getFieldName()));		
	return resFields;
    }
    
    public List<String> getDESCFields() {
	List<String> resFields = new ArrayList<>();
	fields.stream()
		.filter(
			(fieldSotring) -> 
			fieldSotring.getSortingModifier() == SQLLiteral.DESC)
		.forEach((fieldSotring) ->
			resFields.add(fieldSotring.getFieldName()));		
	return resFields;	
    }
    
    private SQLLiteral getSortingModifierLiteralByStr(String stringLiteral) {
	stringLiteral = stringLiteral.replaceAll(" ", "").toUpperCase();
	switch (stringLiteral) {
	case "ASC":
	    return SQLLiteral.ASC;
	case "DESC":
	    return SQLLiteral.DESC;
	default:
	    return SQLLiteral.ASC;
	}	
    }
            
}

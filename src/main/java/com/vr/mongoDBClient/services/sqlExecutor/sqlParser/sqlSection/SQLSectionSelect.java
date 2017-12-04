package com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.vr.mongoDBClient.services.sqlExecutor.SQLRunerUtil;
import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLLiteral;

import lombok.Getter;
import lombok.Setter;

/**
 * SQL section SELECT
 *
 * @author Roman Vlasiuk
 */
public class SQLSectionSelect extends SQLSection{
    private @Getter @Setter String sectionRegex = "\\s*(?<=)SELECT(.+)(?=)@NEXT_COMMAND@.*";
    private @Getter String sectionParamRegex = "([^|,]\\s*[//*,\\w]*\\s*)";
    
    private @Getter @Setter List<String> fields = new ArrayList<>();
    
    public SQLSectionSelect() {
	this.sqlLiteral = SQLLiteral.SELECT;
    }
    
    @Override    
    public void compileSection() throws ParseException{
	Matcher matcher = SQLRunerUtil.getMatcher(this.sectionValue, sectionParamRegex);
	fields.clear();
	if (this.sectionValue.equals("")) {
	    throw new ParseException("Missing field description", 0);
	} else {
	    while (matcher.find()) {
		String field = matcher.group(1);
		field = field.replaceAll(" ", "").replace(",", "");
		if (!field.equals("") && !field.equals("*")) {
		    fields.add(field);
		} else {
		    if (field.equals("")) {
			throw new ParseException("Missing field description", matcher.start());
		    }
		}
	    }
	}
    }
    
    @Override
    public boolean isUsed() {
	return !fields.isEmpty();
    }
}

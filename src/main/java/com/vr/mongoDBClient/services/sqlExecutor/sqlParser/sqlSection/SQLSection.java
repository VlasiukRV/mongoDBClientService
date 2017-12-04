package com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection;

import java.text.ParseException;

import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLLiteral;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Abstract SQL section from SQL query
 *
 * @author Roman Vlasiuk
 */
@NoArgsConstructor
public abstract class SQLSection {
    protected @Getter @Setter SQLLiteral sqlLiteral;
    protected @Getter @Setter String sectionRegex = "";
    
    
    protected @Getter String sectionValue = "";
    
    public void setSectionValue(String sectionValue) {
	if(sectionValue.trim().length() == 0){
	    this.sectionValue = "";
	}else {
	    this.sectionValue = sectionValue;
	}	
    }
    
    public abstract void compileSection() throws ParseException;
    public abstract boolean isUsed();
}

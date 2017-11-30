package com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection;

import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLLiteral;

import lombok.Getter;
import lombok.Setter;

public class FieldSorting extends Field{
    private @Getter @Setter SQLLiteral sortingModifier;
        
    public FieldSorting() {
	
    }
    
    public FieldSorting(String fieldName, SQLLiteral sortingModifier) {
	super();
	setSortingModifier(sortingModifier);
	setFieldName(fieldName);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FieldSorting other = (FieldSorting) obj;
	if (!getFieldName().equals(other.getFieldName()))
	    return false;
	if (sortingModifier != other.sortingModifier)
	    return false;	
	return true;
    }
    
    
}

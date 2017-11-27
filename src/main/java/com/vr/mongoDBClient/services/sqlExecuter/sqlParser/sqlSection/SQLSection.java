package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import java.util.List;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiteral;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class SQLSection {
    protected @Getter @Setter SQLLiteral sqlLiteral;
    protected @Getter @Setter String sectionRegex = "";
    
    
    protected @Getter @Setter String sectionValue = "";
    
    public abstract void compileSection();
    public abstract boolean isUsed();
}

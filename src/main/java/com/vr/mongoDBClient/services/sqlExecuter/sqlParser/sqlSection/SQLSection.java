package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiterals;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class SQLSection {
    protected @Getter @Setter SQLLiterals sqlLiteral;
    protected @Getter String sectionRegex = "";
    protected @Getter String sectionParamRegex = "";
    
    protected @Getter @Setter String sectionValue = "";
    
    public abstract void compileSection();
    public abstract boolean sectionIsUsed();
}

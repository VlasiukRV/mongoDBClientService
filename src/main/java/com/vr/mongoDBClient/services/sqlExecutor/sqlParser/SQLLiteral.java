package com.vr.mongoDBClient.services.sqlExecutor.sqlParser;

public enum SQLLiteral {
    SELECT("SELECT"),
    FROM("FROM"),
    WHERE("WHERE"),
    GROUPBY("GROUP BY"),
    ORDERBY("ORDER BY"),
    SKIP("SKIP"),
    LIMIT("LIMIT"),
    
    AND("AND"),
    OR("OR"),    
    EQ("EQ"),
    GT("GT"),
    GTE("GTE"),
    LT("LT"),
    LTE("LTE"),
    
    ASC("ASC"),
    DESC("DESC");
    
    private String name;
    
    SQLLiteral(String name) {
        this.name = name;
    }    
    public String getName() {
        return name;
    }
}

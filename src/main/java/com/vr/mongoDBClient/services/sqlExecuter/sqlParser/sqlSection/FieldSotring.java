package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiteral;

import lombok.Getter;
import lombok.Setter;

public class FieldSotring extends Field{
    private @Getter @Setter SQLLiteral SortingModifier;
}

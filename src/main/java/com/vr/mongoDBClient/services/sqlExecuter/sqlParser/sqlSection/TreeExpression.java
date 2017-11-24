package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiterals;

import lombok.Getter;
import lombok.Setter;

public class TreeExpression {
    private @Getter @Setter SQLLiterals operator;
    private @Getter @Setter Object valueLeft;
    private @Getter @Setter Object valueRight;
}

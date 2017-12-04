package com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection;

import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLLiteral;

public interface IConditionalExpression <T> {
    public boolean isSimpleExpression();
    public void setOperator(SQLLiteral operator);
    public SQLLiteral getOperator();
    public void setValueLeft(T valueLeft);
    public T getValueLeft();
    public void setValueRight(T valueRight);
    public T getValueRight();
}

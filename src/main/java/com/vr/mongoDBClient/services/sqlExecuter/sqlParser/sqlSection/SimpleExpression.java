package com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.SQLLiteral;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class SimpleExpression implements IConditionalExpression{

    private @Getter @Setter SQLLiteral operator;
    private @Getter String valueLeft;
    private @Getter String valueRight;
    
    public SimpleExpression(SQLLiteral operator, String valueLeft, String valueRight) {
	setOperator(operator);
	setValueLeft(valueLeft);
	setValueRight(valueRight);
    }    
        
    public void setValueLeft(String valueLeft) {
	this.valueLeft = valueLeft.replace(" ", "");
    }
    
    public void setValueRight(String valueRight) {
	this.valueRight = valueRight.replace(" ", "");
    }
    
    @Override
    public boolean isSimpleExpression() {
	return true;
    }
    
}

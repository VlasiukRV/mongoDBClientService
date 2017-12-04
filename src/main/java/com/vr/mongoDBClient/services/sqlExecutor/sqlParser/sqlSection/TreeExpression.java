package com.vr.mongoDBClient.services.sqlExecutor.sqlParser.sqlSection;

import com.vr.mongoDBClient.services.sqlExecutor.sqlParser.SQLLiteral;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Tree of logical expression
 *
 * @author Roman Vlasiuk
 */
@NoArgsConstructor
@AllArgsConstructor
public class TreeExpression implements IConditionalExpression<IConditionalExpression> {
    private @Getter @Setter SQLLiteral operator;
    private @Getter @Setter IConditionalExpression valueLeft;
    private @Getter @Setter IConditionalExpression valueRight;
    
    @Override
    public boolean isSimpleExpression() {
	return false;
    }
        
}

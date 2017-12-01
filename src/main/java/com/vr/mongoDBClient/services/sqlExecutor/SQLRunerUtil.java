package com.vr.mongoDBClient.services.sqlExecutor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLRunerUtil {
    private static Pattern patternSelectSelectQuery = Pattern.compile("(SELECT|FROM)", Pattern.CASE_INSENSITIVE);

    public static Matcher getMatcher(String str, String regexp) {
	Pattern patertn = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);	
	return patertn.matcher(str);	
    }
    
    public static boolean isSelectQuery(String query) {
	Matcher matcher = patternSelectSelectQuery.matcher(query);
	if (matcher.find()) {
	    return true;
	}
	
	return true;
    }

}

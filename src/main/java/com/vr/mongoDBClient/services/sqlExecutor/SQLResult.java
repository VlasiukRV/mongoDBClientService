package com.vr.mongoDBClient.services.sqlExecutor;

public class SQLResult<T> {
    private T result;
    private boolean empty; 
    
    public SQLResult() {
	this.empty = true;
    }
    
    public SQLResult(T result) {
	this.result = result;
	this.empty = true;
	if(result != null) {
	    this.empty = false;
	}
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
    
    public boolean isEmpty() {
	return empty;
    }
}

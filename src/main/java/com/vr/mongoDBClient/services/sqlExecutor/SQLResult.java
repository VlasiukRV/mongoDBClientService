package com.vr.mongoDBClient.services.sqlExecutor;

public class SQLResult<T> {
    private T result;
    
    public SQLResult(T result) {
	this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }     
}

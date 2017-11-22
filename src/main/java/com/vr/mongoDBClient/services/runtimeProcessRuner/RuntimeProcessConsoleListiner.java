package com.vr.mongoDBClient.services.runtimeProcessRuner;

import java.io.BufferedReader;

import com.vr.mongoDBClient.services.serviceTask.AbstractServiceTask;

public class RuntimeProcessConsoleListiner extends AbstractServiceTask implements RuntimeProcessListiner {
    private BufferedReader input;
    
    @Override
    public void setInput(BufferedReader input) {
        this.input = input;
    }
    
    @Override
    public BufferedReader getInput() {
	return this.input;
    }
    
    @Override
    protected boolean doTask() {
	String line;    	   
	try {
	    while ((line = input.readLine()) != null) { 
		System.out.println(line);
	    }
	}catch (Exception e){
	    System.out.println(e.getMessage());
	}
	return true;
    }
        
}

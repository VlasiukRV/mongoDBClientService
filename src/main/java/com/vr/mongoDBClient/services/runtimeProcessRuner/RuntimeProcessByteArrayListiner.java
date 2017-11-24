package com.vr.mongoDBClient.services.runtimeProcessRuner;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.vr.mongoDBClient.services.serviceTask.ServiceTask;

public class RuntimeProcessByteArrayListiner extends ServiceTask implements IRuntimeProcessListiner {
    private BufferedReader input;
    
    private ByteArrayOutputStream output;
    
    public RuntimeProcessByteArrayListiner(){
	this.output = new ByteArrayOutputStream();
    }
    
    public ByteArrayOutputStream getOutputStream() {
	return this.output;
    }
    
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
		output.write(line.getBytes());
	    }
	    
	}catch (Exception e){
	    System.out.println(e.getMessage());
	}
	return true;
    }
    
    @Override
    public void stopTask(){	
	try {
	    this.output.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	super.stopTask();
    }
    
}

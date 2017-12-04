package com.vr.mongoDBClient.services.mongoDBService.runtimeProcessRuner;

import java.io.BufferedReader;

import com.vr.mongoDBClient.services.mongoDBService.IRuntimeProcessListener;
import com.vr.mongoDBClient.services.serviceTask.ServiceTask;

/**
 * Task provide console reader in runtime
 *
 * @author Roman Vlasiuk
 */
public class RuntimeProcessConsoleListener extends ServiceTask implements IRuntimeProcessListener {
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

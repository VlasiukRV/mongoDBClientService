package com.vr.mongoDBClient.services.mongoDBService.runtimeProcessRuner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.vr.mongoDBClient.services.mongoDBService.IRuntimeProcessListener;
import com.vr.mongoDBClient.services.serviceTask.ServiceTask;

public class RuntimeProcessRuner extends ServiceTask {
    private String command;
    private Process process;
    private Set<IRuntimeProcessListener> processListeners = new HashSet<>();
    
    public void setCommand(String command) {
        this.command = command;
    }

    public void addProcessListener(IRuntimeProcessListener processListener) {
	processListeners.add(processListener);
    }
    
    @Override
    protected boolean doTask()throws IOException, InterruptedException  {
	process = Runtime.getRuntime().exec(command);
	Thread.sleep(1000);
	
	runProcessListeners(process.getInputStream());
	
	int waitFor = process.waitFor();
	System.out.println("waitFor:: "+waitFor);

	stopProcessListeners();

	return false;	
    }
    
    public void stopTask(){
	process.destroy();
	try {
	    stopProcessListeners();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	super.stopTask();
    }
        
    @Override
    public void run(){
	System.out.println("ProcessRuntimeRuner start");
	
	try {
	    doTask();
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}	
        
        System.out.println("ProcessRuntimeRuner stop");
    }    
 
    private void runProcessListeners(InputStream processInputStream) {
	for (IRuntimeProcessListener runtimeProcessListener : processListeners) {
	    BufferedReader input = new BufferedReader(new InputStreamReader(processInputStream));
	    runtimeProcessListener.setInput(input);
	    runtimeProcessListener.startTask();
	}	
    }
    
    private void stopProcessListeners() throws IOException, InterruptedException {
	for (IRuntimeProcessListener runtimeProcessListener : processListeners) {
	    runtimeProcessListener.stopTask();
	    Thread.sleep(1000);
	    BufferedReader input = runtimeProcessListener.getInput();
	    input.close();	       
	}	
    }
}

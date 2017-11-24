package com.vr.mongoDBClient.services.runtimeProcessRuner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.vr.mongoDBClient.services.serviceTask.ServiceTask;

public class RuntimeProcessRuner extends ServiceTask {
    private String command;
    private Process process;
    private Set<IRuntimeProcessListiner> processListiners = new HashSet<IRuntimeProcessListiner>();
    
    public void setCommand(String command) {
        this.command = command;
    }

    public void addProcessListiner(IRuntimeProcessListiner processListiner) {
	processListiners.add(processListiner);
    }
    
    @Override
    protected boolean doTask()throws Exception {
	process = Runtime.getRuntime().exec(command);
	Thread.sleep(1000);
	
	runProcessListiners(process.getInputStream());
	
	int waitFor = process.waitFor();
	System.out.println("waitFor:: "+waitFor);

	stopProcessListiners();

	return false;	
    }
    
    public void stopTask(){
	process.destroy();
	try {
	    stopProcessListiners();
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
 
    private void runProcessListiners(InputStream processInputStream) {
	for (IRuntimeProcessListiner runtimeProcessListiner : processListiners) {
	    BufferedReader input = new BufferedReader(new InputStreamReader(processInputStream));
	    runtimeProcessListiner.setInput(input);
	    runtimeProcessListiner.startTask();
	}	
    }
    
    private void stopProcessListiners() throws IOException, InterruptedException {
	for (IRuntimeProcessListiner runtimeProcessListiner : processListiners) {
	    runtimeProcessListiner.stopTask();
	    Thread.sleep(1000);
	    BufferedReader input = runtimeProcessListiner.getInput();
	    input.close();	       
	}	
    }
}

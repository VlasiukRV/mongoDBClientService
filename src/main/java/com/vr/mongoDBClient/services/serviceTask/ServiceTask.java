package com.vr.mongoDBClient.services.serviceTask;

import java.io.IOException;

public abstract class ServiceTask extends Thread implements IServiceTask{
    protected String taskName = "";
    
    @Override
    public void startTask(){
        this.start();
    }

    @Override
    public void stopTask(){
        this.interrupt();
    }

    protected abstract boolean doTask() throws IOException, InterruptedException;
    
    @Override
    public void run() {
        System.out.println(taskName+ " start");
        do {
            if (Thread.interrupted())
            {
                break;
            }

            try {
        	doTask();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                break;
            }
        }
        while (true);
        System.out.println(taskName+ " stop");
    }
    
}

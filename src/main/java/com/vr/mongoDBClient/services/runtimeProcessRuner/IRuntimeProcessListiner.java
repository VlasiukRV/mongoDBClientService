package com.vr.mongoDBClient.services.runtimeProcessRuner;

import java.io.BufferedReader;

import com.vr.mongoDBClient.services.serviceTask.IServiceTask;

public interface IRuntimeProcessListiner extends IServiceTask{
    
    public void setInput(BufferedReader input);
    public BufferedReader getInput();
}

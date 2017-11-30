package com.vr.mongoDBClient.services.mongoDBService;

import java.io.BufferedReader;

import com.vr.mongoDBClient.services.serviceTask.IServiceTask;

public interface IRuntimeProcessListener extends IServiceTask{
    
    public void setInput(BufferedReader input);
    public BufferedReader getInput();
}

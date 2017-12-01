package com.vr.mongoDBClient.services.mongoDBService;

import java.io.IOException;
import java.util.Set;

public interface IMongoDBServer {
    public void startMongoDBServer() throws Exception;
    public void startMongoDBServer(Set<IRuntimeProcessListener> processListeners) throws Exception;
    public void stopMongoDBServer() throws IOException, InterruptedException;
    public String getServerLog();
}

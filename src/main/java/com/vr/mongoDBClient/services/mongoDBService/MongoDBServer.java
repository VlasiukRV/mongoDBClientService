package com.vr.mongoDBClient.services.mongoDBService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vr.mongoDBClient.services.mongoDBService.runtimeProcessRuner.RuntimeProcessByteArrayListener;
import com.vr.mongoDBClient.services.mongoDBService.runtimeProcessRuner.RuntimeProcessRuner;

import lombok.Getter;
import lombok.Setter;

@Component
public class MongoDBServer implements IMongoDBServer{

    @Value("${mongo.useCustomMongoServer}")
    private boolean useCustomMongoServer;    
    @Value("${mongo.customMongoServer.mongo}")
    private @Setter @Getter String mongo;
    @Value("${mongo.customMongoServer.dbPath}")
    private @Setter @Getter String dbPath;
    
    private RuntimeProcessByteArrayListener  runtimeProcessByteArrayListener;    
    private RuntimeProcessRuner runtimeProcessRuner;
    
    @Override
    public void startMongoDBServer() throws Exception {
	Set<IRuntimeProcessListener> processListeners = new HashSet<>();
	startMongoDBServer(this.mongo, this.dbPath, processListeners);
    }

    @Override
    public void startMongoDBServer(Set<IRuntimeProcessListener> processListeners) throws Exception {
	startMongoDBServer(this.mongo, this.dbPath, processListeners);
    }
    
    @PreDestroy
    @Override
    public void stopMongoDBServer() throws IOException, InterruptedException {
	if (useCustomMongoServer) {
	    runtimeProcessRuner.stopTask();
	}
    }
    
    @Override
    public String getServerLog() {
	if(runtimeProcessByteArrayListener != null) {
	    return runtimeProcessByteArrayListener.getOutputStream().toString();
	}	
	return "";
    }
    
    private void startMongoDBServer(String mongodPath, String dbpath, Set<IRuntimeProcessListener> processListeners) throws Exception {

	if (useCustomMongoServer) {
	    runtimeProcessByteArrayListener = new RuntimeProcessByteArrayListener();
	    processListeners.add(runtimeProcessByteArrayListener);

	    String command = String.format("%smongod.exe --dbpath=%s", mongodPath, dbpath);

	    runtimeProcessRuner = new RuntimeProcessRuner();
	    runtimeProcessRuner.setCommand(command);
	    for (IRuntimeProcessListener runtimeProcessListener : processListeners) {
		runtimeProcessRuner.addProcessListener(runtimeProcessListener);
	    }
	    runtimeProcessRuner.startTask();
	}
    }
    
}

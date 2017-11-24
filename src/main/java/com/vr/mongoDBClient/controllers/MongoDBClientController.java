package com.vr.mongoDBClient.controllers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoDatabase;
import com.vr.mongoDBClient.services.MongoDBService;
import com.vr.mongoDBClient.services.runtimeProcessRuner.RuntimeProcessConsoleListiner;
import com.vr.mongoDBClient.services.runtimeProcessRuner.IRuntimeProcessListiner;

@RestController
@RequestMapping(value = "/mongodbservice")
public class MongoDBClientController {

    @Autowired
    MongoDBService mongoDBServices;
    
    @RequestMapping(value = "/startDBServer", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public Map<String, Object> startMongoDBServer() {	
	try {
	    
	    Set<IRuntimeProcessListiner> processListiners = new HashSet<IRuntimeProcessListiner>();
	    processListiners.add(new RuntimeProcessConsoleListiner());	    
	    mongoDBServices.startMongoDBServer(processListiners);
	    
	}catch (Exception ex) {
	    return AjaxResponse.errorResponse(ex.getMessage());
	}
	return AjaxResponse.successResponse("Server mongoDB started");
    }

    @RequestMapping(value = "/getServerLog", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public Map<String, Object> getMongoDBServerLog() {
	String serverLog = mongoDBServices.getServerLog();
	if(serverLog.equals("")) {
	    return AjaxResponse.emptyResponse();
	}
	return AjaxResponse.successResponse(serverLog);
    }
    
    @RequestMapping(value = "/stopDBServer", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public Map<String, Object> stopMongoDBServer() {
	try {
	    mongoDBServices.stopMongoDBServer();
	}catch (Exception ex) {
	    return AjaxResponse.errorResponse(ex.getMessage());
	}
	return AjaxResponse.successResponse("Server mongoDB stop");
    }

    @RequestMapping(value = "/testDBServer", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public Map<String, Object> testMongoDbConnection() {
	return AjaxResponse.successResponse(mongoDBServices.testMongoDbConnection());
    }
        
    @RequestMapping(value = "/getDataBasesList", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public Map<String, Object> getDataBasesList() {
	return AjaxResponse.successResponse(mongoDBServices.getDataBasesList());
    }
        
    @RequestMapping(value = "/createDataBase", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Map<String, Object> createDataBase(@RequestParam String dataBaseName) {
	MongoDatabase db = mongoDBServices.createDataBase(dataBaseName);
	if(db!=null) {
	    return AjaxResponse.successResponse(""+ dataBaseName+" created");
	}
	return AjaxResponse.errorResponse("error creating database " + dataBaseName);
    }

    @RequestMapping(value = "/dropDataBase", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Map<String, Object> dropDataBase(@RequestParam String dataBaseName) {
	return AjaxResponse.successResponse(mongoDBServices.dropDataBase(dataBaseName));
    }
    
}

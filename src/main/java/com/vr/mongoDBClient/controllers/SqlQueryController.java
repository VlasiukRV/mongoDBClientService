package com.vr.mongoDBClient.controllers;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vr.mongoDBClient.services.SqlQueryService;

@RestController
@RequestMapping(value = "/api/sqlQuery")
public class SqlQueryController {

    @Autowired
    SqlQueryService sqlQueryService;
    
    @RequestMapping(value = "/run", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public Map<String, Object> runSqlQuery(@RequestParam String query) {
	try {
	    return AjaxResponse.successResponse(sqlQueryService.runSqlQuery(query));
	} catch (Exception ex) {
	    System.out.println(ex.getMessage());
	    return AjaxResponse.errorResponse(ex.getMessage(), new ArrayList<>());
	}
    }
    
}

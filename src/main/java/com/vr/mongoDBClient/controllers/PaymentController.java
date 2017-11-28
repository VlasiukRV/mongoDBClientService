package com.vr.mongoDBClient.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vr.mongoDBClient.services.PaymentService;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    
    @RequestMapping(value = "/generateTestPayments", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public Map<String, Object> generateTestPayments() {
	paymentService.generateTestPayments();
	return AjaxResponse.successResponse("Payments generated");
    }
 
    @RequestMapping(value = "/getTestPayments", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public Map<String, Object> getTestPayments() {
	return AjaxResponse.successResponse(paymentService.getPayments());
    }
    
}

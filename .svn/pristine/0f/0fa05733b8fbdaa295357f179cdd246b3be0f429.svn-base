package com.velotech.fanselection.admin.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.admin.service.JsonMasterService;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/admin/jsonMasterEvaluation")
public class JsonEvaluationController {

	@Autowired
	private JsonMasterService service;

	@RequestMapping(value = "/jsonEvaluate", method = RequestMethod.POST)
	public ApplicationResponse addRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

	
		return service.jsonEvaluate(requestPayload);
	}
}

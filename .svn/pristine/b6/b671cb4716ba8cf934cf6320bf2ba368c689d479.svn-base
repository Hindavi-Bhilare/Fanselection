package com.velotech.fanselection.design.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.design.service.CentrifugalSpeedDataService;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.MasterSericeUtil;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping("/design/centrifugalspeeddata")
public class CentrifugalSpeedDataController {
	
	static String serviceName = "CentrifugalSpeedData";

	@Autowired
	private MasterSericeUtil serviceUtil;

	@Autowired
	private CentrifugalSpeedDataService service;
	
	@PostMapping("/add")
	public ApplicationResponse addRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.addRecord(requestPayload);
	}

	@PutMapping("/update")
	public ApplicationResponse updateRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.updateRecord(requestPayload);
	}

	@DeleteMapping("/delete")
	public ApplicationResponse deleteRecords(RequestWrapper requestWrapper) {

		return service.deleteRecords(requestWrapper.getIds());
	}

	@GetMapping("/getrecords")
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		return service.getRecords(requestWrapper);
	}

	

}

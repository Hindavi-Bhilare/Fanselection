package com.velotech.fanselection.design.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.design.service.CentrifugalModelMasterService;
import com.velotech.fanselection.design.service.ModelMasterService;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.MasterSericeUtil;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping("/design/centrifugalmodelmaster")
public class CentrifugalModelMasterController {
	
	static String serviceName = "CentrifugalModelMaster";

	@Autowired
	private MasterSericeUtil serviceUtil;

	@Autowired
	private CentrifugalModelMasterService service;
	
	@PostMapping("/add")
	public ApplicationResponse addRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		MasterService service = serviceUtil.getService(serviceName);
		return service.addRecord(requestPayload);
	}

	@PutMapping("/update")
	public ApplicationResponse updateRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		MasterService service = serviceUtil.getService(serviceName);
		return service.updateRecord(requestPayload);
	}

	@DeleteMapping("/delete")
	public ApplicationResponse deleteRecords(RequestWrapper requestWrapper) {

		MasterService service = serviceUtil.getService(serviceName);
		return service.deleteRecords(requestWrapper.getIds());
	}

	@GetMapping("/getrecords")
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		MasterService service = serviceUtil.getService(serviceName);
		return service.getRecords(requestWrapper);
	}

	@GetMapping(value = "/downloadexcel")
	public void downloadExcel(RequestWrapper requestWrapper) {

		MasterService service = serviceUtil.getService(serviceName);
		service.downloadExcel(requestWrapper);
	}

	@PostMapping(value = "/copymodelmaster")
	public ApplicationResponse copyModelMaster(@RequestParam Integer modelmasterid, @RequestParam(value = "pumpModel") String pumpModel)
			throws JsonParseException, JsonMappingException, IOException {

		return service.copyModelMaster(modelmasterid, pumpModel);
	}
	@GetMapping("/getmodelmasterdetails")
	public ApplicationResponse getModelMasterDetails(RequestWrapper requestWrapper) throws Exception {

		return service.getModelMasterDetails(requestWrapper);
	}
	

	@PostMapping(value = "/upload")
	public ApplicationResponse upload(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.upload(requestPayload);
	}

}

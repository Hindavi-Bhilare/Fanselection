package com.velotech.fanselection.design.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.velotech.fanselection.design.service.ParameterValueService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping("/design/parametervalue")
public class ParameterValueController {

	@Autowired 
	ParameterValueService service;
	
	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecords(RequestWrapper requestWrapper,@RequestParam(value="uuid",required=false)String uuid) throws Exception {
    
		return service.getRecords(requestWrapper,uuid);
     }
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApplicationResponse addRecord(@RequestBody String requestPayload,@RequestParam(value="uuid",required=false)String uuid) throws Exception {

		return service.addRecord(requestPayload,uuid);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ApplicationResponse updateRecord(@RequestBody String requestPayload,@RequestParam(value="uuid",required=false)String uuid) throws Exception {

		return service.updateRecord(requestPayload,uuid);
	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApplicationResponse deleteRecords(RequestWrapper requestWrapper){

		return service.deleteRecords(requestWrapper.getIds());
	}
	
	@RequestMapping(value = "/getWithoutUuidrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecordsWithoutUuid(RequestWrapper requestWrapper,@RequestParam(value="uuid",required=false)String uuid) throws Exception {
    
		return service.getRecords(requestWrapper,null);
     }
	@RequestMapping(value = "/downloadexcel", method = RequestMethod.GET)
	public void downloadExcel(RequestWrapper requestWrapper,@RequestParam(value="uuid",required=false) String uuid) {

		service.downloadExcel(requestWrapper,uuid);
	}
	
	
}

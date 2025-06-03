package com.velotech.fanselection.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.velotech.fanselection.admin.service.ParameterMasterService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping("/admin/parameterMaster")
public class ParameterMasterController {
	
	@Autowired
	private ParameterMasterService service;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApplicationResponse addRecord(@RequestBody String requestPayload) throws Exception {

		return service.addRecord(requestPayload);
	}
	
	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {
    
		return service.getRecords(requestWrapper);
     }
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApplicationResponse deleteRecords(@RequestParam(value="parameter")List<String> parameter)throws Exception {

		return service.deleteRecords(parameter);
	}


}

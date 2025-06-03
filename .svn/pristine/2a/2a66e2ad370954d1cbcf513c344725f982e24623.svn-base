package com.velotech.fanselection.admin.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.velotech.fanselection.admin.service.JsonMasterService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping("/admin/jsonMaster")
public class JsonMasterController {
	
	@Autowired 
    private	JsonMasterService service;
	
	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {
          
		return service.getRecords(requestWrapper);
}
	@RequestMapping(value = "/addrecords", method = RequestMethod.POST)
	public ApplicationResponse addRecord(@RequestBody String requestPayload)  throws Exception {

		return service.addRecord(requestPayload);
	}
	@RequestMapping(value = "/updaterecords", method = RequestMethod.POST)
	public ApplicationResponse updateRecord(@RequestBody String requestPayload)  throws Exception{

		return service.updateRecord(requestPayload);
}
	@RequestMapping(value = "/deleterecords", method = RequestMethod.POST)
	public ApplicationResponse deleteRecords(RequestWrapper requestWrapper){

		return service.deleteRecords(requestWrapper.getIds());
	}
}

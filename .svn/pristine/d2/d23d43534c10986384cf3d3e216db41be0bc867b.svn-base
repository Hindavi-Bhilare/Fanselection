package com.velotech.fanselection.design.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.MasterSericeUtil;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/design/nrvmaster")
public class NrvMasterController {
	static String serviceName = "NrvMaster";

	@Autowired
	private MasterSericeUtil serviceUtil;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApplicationResponse addRecord(@RequestBody String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {

		MasterService service = serviceUtil.getService(serviceName);
		return service.addRecord(requestPayload);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ApplicationResponse updateRecord(@RequestBody String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {

		MasterService service = serviceUtil.getService(serviceName);
		return service.updateRecord(requestPayload);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApplicationResponse deleteRecords(RequestWrapper requestWrapper) {

		MasterService service = serviceUtil.getService(serviceName);
		return service.deleteRecords(requestWrapper.getIds());
	}

	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		MasterService service = serviceUtil.getService(serviceName);
		return service.getRecords(requestWrapper);
	}

	@RequestMapping(value = "/downloadexcel", method = RequestMethod.GET)
	public void downloadExcel(RequestWrapper requestWrapper) {

		MasterService service = serviceUtil.getService(serviceName);
		service.downloadExcel(requestWrapper);
	}
}

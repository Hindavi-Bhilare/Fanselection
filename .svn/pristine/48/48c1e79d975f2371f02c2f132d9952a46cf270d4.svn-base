
package com.velotech.fanselection.admin.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.admin.service.DocumentMasterService;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.MasterSericeUtil;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/admin/documentmaster")
public class DocumentMasterController {

	static String serviceName = "DocumentMaster";

	@Autowired
	private MasterSericeUtil serviceUtil;
	
	@Autowired
	private DocumentMasterService service;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ApplicationResponse addRecord(@RequestParam(value = "file", required = false) MultipartFile file) {

		return service.uploadFile(file);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApplicationResponse add(@RequestParam(value = "file", required = false) MultipartFile file)  {

		return service.addRecord(file);
	}


	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ApplicationResponse updateRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

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
	
	@RequestMapping(value = "/getFilePath", method = RequestMethod.POST)
	public ApplicationResponse getFilePath(RequestWrapper requestWrapper) throws Exception {
		return service.getFilePath(requestWrapper);
	}
	
	
	@RequestMapping(value = "/getComboRecords", method = RequestMethod.GET)
	public ApplicationResponse getComboboxRecords( RequestWrapper requestWrapper) throws JsonParseException, JsonMappingException, IOException {

		return service.getComboboxRecords(requestWrapper);
	}
	
}

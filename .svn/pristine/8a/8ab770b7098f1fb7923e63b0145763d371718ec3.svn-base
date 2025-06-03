
package com.velotech.fanselection.design.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.design.service.TemplateMasterSerive;
import com.velotech.fanselection.dxf.util.OfferDrawingBlock;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/design/templatemaster")
public class TemplateMasterController {

	static String serviceName = "TemplateMaster";

	@Autowired
	private OfferDrawingBlock offerDrawingBlock;

	@Autowired
	private TemplateMasterSerive service;

	static Logger log = LogManager.getLogger(TemplateMasterController.class.getName());

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApplicationResponse addRecord( RequestWrapper requestWrapper)
			throws JsonParseException, JsonMappingException, IOException {

		return service.addRecord( requestWrapper);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ApplicationResponse updateRecord(RequestWrapper requestWrapper) throws JsonParseException, JsonMappingException, IOException {

		return service.updateRecord(requestWrapper);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApplicationResponse deleteRecords(RequestWrapper requestWrapper) {

		return service.deleteRecords(requestWrapper.getIds());
	}

	@RequestMapping(value = "/viewexistingtemplate", method = RequestMethod.POST)
	public ApplicationResponse templateView(@RequestParam(value = "id") Integer id) {

		ApplicationResponse applicationResponse = null;
		try {

			applicationResponse = service.viewExistingTemplate(id);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		return service.getRecords(requestWrapper);
	}

	@RequestMapping(value = "/downloadexcel", method = RequestMethod.GET)
	public void downloadExcel(RequestWrapper requestWrapper) {

		service.downloadExcel(requestWrapper);
	}

	@RequestMapping(value = "/uploadtemplate", method = RequestMethod.POST)
	public ApplicationResponse overWriteTemplate(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "id") Integer id) throws JsonParseException, JsonMappingException, IOException {

		return service.overWriteTemplate(file, id);
	}

	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ApplicationResponse upload(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.upload(requestPayload);
	}
}


package com.velotech.fanselection.offer.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.offer.service.BomService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/offer/bom")
public class BomController {

	static String serviceName = "Bom";

	@Autowired
	private BomService service;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApplicationResponse addRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.addRecord(requestPayload);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ApplicationResponse updateRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.updateRecord(requestPayload);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApplicationResponse deleteRecords(RequestWrapper requestWrapper) {

		return service.deleteRecords(requestWrapper.getIds());
	}

	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecords(@RequestParam(value = "offerFanId") Integer offerPumpId) throws Exception {

		return service.getRecords(offerPumpId);
	}

	@RequestMapping(value = "/resetbom", method = RequestMethod.GET)
	public ApplicationResponse resetBom(@RequestParam(value = "offerFanId") Integer offerPumpId) {

		return service.resetBom(offerPumpId);
	}
}

package com.velotech.fanselection.offer.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.offer.service.OfferShareService;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/offer/offershare")
public class OfferShareController {

	@Autowired
	private OfferShareService service;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApplicationResponse addRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.addRecord(requestPayload);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ApplicationResponse updateRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.updateRecord(requestPayload);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApplicationResponse deleteRecords(@RequestParam(value = "ids") List<Integer> ids) {

		return service.deleteRecords(ids);
	}

	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecords(@RequestParam(value = "offerRevId") Integer offerRevId) throws Exception {

		return service.getRecords(offerRevId);
	}

}

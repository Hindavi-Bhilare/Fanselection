
package com.velotech.fanselection.offer.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.offer.service.OfferRevTermsAndConditionsService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/offer/termsandconditions")
public class OfferRevTermsAndConditionsController {

	@Autowired
	private OfferRevTermsAndConditionsService service;

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);

		// true passed to CustomDateEditor constructor means convert empty
		// String to null
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApplicationResponse addTermsCondition(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.addTermsCondition(requestPayload);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ApplicationResponse updateTermsCondition(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.updateTermsCondition(requestPayload);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApplicationResponse deleteTermsConditions(RequestWrapper requestWrapper) {

		return service.deleteTermsConditions(requestWrapper.getIds());
	}

	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getTermsConditions(@RequestParam(value = "offerRevId") Integer offerRevId) throws Exception {

		return service.getTermsConditions(offerRevId);
	}

	@RequestMapping(value = "/loaddefaultrecords", method = RequestMethod.GET)
	public ApplicationResponse loadDefaultRecords(@RequestParam(value = "offerRevId") Integer offerRevId) throws Exception {

		return service.loadDefaultRecords(offerRevId);
	}
}

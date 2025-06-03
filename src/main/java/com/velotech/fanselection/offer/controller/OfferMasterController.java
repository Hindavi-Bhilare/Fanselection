
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
import com.velotech.fanselection.offer.service.OfferMasterService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/offermaster")
public class OfferMasterController {

	@Autowired
	private OfferMasterService service;

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);

		// true passed to CustomDateEditor constructor means convert empty
		// String to null
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApplicationResponse addRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.addRecord(requestPayload);
	}

	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecords(@RequestParam(value = "id") Integer id, @RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "endDate") Date endDate, @RequestParam(value = "offerNo") String offerNo,
			@RequestParam(value = "customerName") String customerName, @RequestParam(value = "salesPersonName") String salesPersonName,
			@RequestParam(value = "organisationDetails") String organisationDetails, @RequestParam(value = "project") String project,
			@RequestParam(value = "status") String status, @RequestParam(value = "active") boolean active, RequestWrapper requestWrapper) {

		return service.getRecords(id,startDate, endDate, offerNo, customerName, salesPersonName, project, active, status, requestWrapper,
				organisationDetails);
	}

	@RequestMapping(value = "/getrecord", method = RequestMethod.GET)
	public ApplicationResponse getRecord(@RequestParam(value = "offerRevId") Integer offerRevId) {

		return service.getRecord(offerRevId);
	}

	@RequestMapping(value = "/getofferpumps", method = RequestMethod.GET)
	public ApplicationResponse getOfferPumps(@RequestParam(value = "offerRevId") Integer offerRevId) {

		return service.getOfferPumps(offerRevId);
	}

	@RequestMapping(value = "/getofferstatushistory", method = RequestMethod.GET)
	public ApplicationResponse getOfferStatusHistory(@RequestParam(value = "offerRevId") Integer offerRevId) {

		return service.getOfferStatusHistory(offerRevId);
	}

	@RequestMapping(value = "/updateofferstatus", method = RequestMethod.POST)
	public ApplicationResponse updateOfferStatus(@RequestBody String requestPayload) {

		return service.updateOfferStatus(requestPayload);
	}

}


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
import com.velotech.fanselection.offer.service.OfferPriceService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/offerprice")
public class OfferPriceController {

	@Autowired
	private OfferPriceService service;

	@RequestMapping(value = "/getoffertotal", method = RequestMethod.GET)
	public ApplicationResponse getOfferTotal(@RequestParam(value = "offerRevId") Integer offerRevId) throws Exception {

		return service.getOfferTotal(offerRevId);
	}

	@RequestMapping(value = "/getofferpumpprices", method = RequestMethod.GET)
	public ApplicationResponse getOfferPumpPrices(@RequestParam(value = "offerRevId") Integer offerRevId) throws Exception {

		return service.getOfferPumpPrices(offerRevId);
	}

	@RequestMapping(value = "/getaddons", method = RequestMethod.GET)
	public ApplicationResponse getAddons(@RequestParam(value = "offerRevId") Integer offerRevId) throws Exception {

		return service.getAddons(offerRevId);
	}

	@RequestMapping(value = "/saveaddons", method = RequestMethod.POST)
	public ApplicationResponse saveAddons(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.saveAddons(requestPayload);
	}

	@RequestMapping(value = "/deleteaddons", method = RequestMethod.POST)
	public ApplicationResponse deleteAddons(RequestWrapper requestWrapper) {

		return service.deleteAddons(requestWrapper.getIds());
	}

	@RequestMapping(value = "/getfactors", method = RequestMethod.GET)
	public ApplicationResponse getFactors(@RequestParam(value = "offerRevId") Integer offerRevId) throws Exception {

		return service.getFactors(offerRevId);
	}

	@RequestMapping(value = "/savefactors", method = RequestMethod.POST)
	public ApplicationResponse saveFactors(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.saveFactors(requestPayload);
	}

	@RequestMapping(value = "/deletefactors", method = RequestMethod.POST)
	public ApplicationResponse deleteFactors(RequestWrapper requestWrapper) {

		return service.deleteFactors(requestWrapper.getIds());
	}

}


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
import com.velotech.fanselection.offer.service.OfferPumpPriceService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/offerpumpprice")
public class OfferPumpPriceController {

	@Autowired
	private OfferPumpPriceService service;

	@RequestMapping(value = "/getofferfactor", method = RequestMethod.GET)
	public ApplicationResponse getOfferFactor(@RequestParam(value = "offerRevId") Integer offerRevId) throws Exception {

		return service.getOfferFactor(offerRevId);
	}

	@RequestMapping(value = "/getspareparts", method = RequestMethod.GET)
	public ApplicationResponse getSpareParts(@RequestParam(value = "offerFanId") Integer offerPumpId) throws Exception {

		return service.getSpareParts(offerPumpId);
	}

	@RequestMapping(value = "/savesparepartspricing", method = RequestMethod.POST)
	public ApplicationResponse saveSparePartsPricing(@RequestParam(value = "offerFanId") Integer offerPumpId, RequestWrapper requestWrapper) {

		return service.saveSparePartsPricing(offerPumpId, requestWrapper.getIds());
	}

	@RequestMapping(value = "/getselectedpricings", method = RequestMethod.GET)
	public ApplicationResponse getSelectedPricings(@RequestParam(value = "offerFanId") Integer offerPumpId) throws Exception {

		return service.getSelectedPricings(offerPumpId);
	}

	@RequestMapping(value = "/saveselectedpricing", method = RequestMethod.POST)
	public ApplicationResponse saveSelectedPricing(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.saveSelectedPricing(requestPayload);
	}

	@RequestMapping(value = "/deleteselectedpricings", method = RequestMethod.POST)
	public ApplicationResponse deleteSelectedPricings(RequestWrapper requestWrapper) {

		return service.deleteSelectedPricings(requestWrapper.getIds());
	}

	@RequestMapping(value = "/getpricingdetails", method = RequestMethod.GET)
	public ApplicationResponse getPricingDetails(@RequestParam(value = "selectedPricingId") Integer selectedPricingId) throws Exception {

		return service.getPricingDetails(selectedPricingId);
	}

	@RequestMapping(value = "/savepricingdetails", method = RequestMethod.POST)
	public ApplicationResponse savePricingDetails(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.savePricingDetails(requestPayload);
	}

	@RequestMapping(value = "/deletepricingdetails", method = RequestMethod.POST)
	public ApplicationResponse deletePricingDetails(RequestWrapper requestWrapper) {

		return service.deletePricingDetails(requestWrapper.getIds());
	}

	@RequestMapping(value = "/getaddons", method = RequestMethod.GET)
	public ApplicationResponse getAddons(@RequestParam(value = "offerFanId") Integer offerPumpId) throws Exception {

		return service.getAddons(offerPumpId);
	}

	@RequestMapping(value = "/saveaddons", method = RequestMethod.POST)
	public ApplicationResponse saveAddons(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.saveAddons(requestPayload);
	}

	@RequestMapping(value = "/deleteaddons", method = RequestMethod.POST)
	public ApplicationResponse deleteAddons(RequestWrapper requestWrapper) {

		return service.deleteAddons(requestWrapper.getIds());
	}

}

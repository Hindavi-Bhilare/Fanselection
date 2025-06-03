
package com.velotech.fanselection.combogrid.controller;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.velotech.fanselection.combogrid.service.CombogridService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/combogrid")
public class CombogridController {

	static Logger log = LogManager.getLogger(CombogridController.class.getName());

	@Autowired
	private CombogridService service;

	@RequestMapping(value = "/getprimemovermaster", method = RequestMethod.GET)
	public ApplicationResponse getPrimemoverMaster(RequestWrapper requestWrapper) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getPrimemoverMaster(requestWrapper);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/getoffermasters", method = RequestMethod.GET)
	public ApplicationResponse getOfferMasters(@RequestParam(value = "offerNo", required = false) String offerNo,
			@RequestParam(value = "customerName", required = false) String customerName, RequestWrapper requestWrapper) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getOfferMasters(offerNo, customerName, requestWrapper);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/getcustomermasters", method = RequestMethod.GET)
	public ApplicationResponse getCustomerMasters(RequestWrapper requestWrapper) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getCustomerMasters(requestWrapper);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}
	@RequestMapping(value = "/getframemaster", method = RequestMethod.GET)
	public ApplicationResponse getFrameMaster(RequestWrapper requestWrapper) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getFrameMaster(requestWrapper);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}
}

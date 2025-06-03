
package com.velotech.fanselection.selection.controller;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.velotech.fanselection.selection.service.CentrifugalFanSelectionService;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/centrifugalselection")
@Scope("prototype")
public class CentrifugalFanSelectionController {

	static Logger log = LogManager.getLogger(CentrifugalFanSelectionController.class.getName());

	@Autowired
	private CentrifugalFanSelectionService service;

	@RequestMapping(value = "/getfantypes")
	public ApplicationResponse getPumpTypes(@RequestParam(value = "searchValue") String searchValue) {

		ApplicationResponse applicationResponse = null;
		try {
			applicationResponse = service.getFanTypes(searchValue);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@RequestMapping(value = "/getfanselection")
	public ApplicationResponse getPumpSelection(@RequestBody String requestPayload) {

		ApplicationResponse applicationResponse = null;
		try {

			applicationResponse = service.getFanSelection(requestPayload);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@RequestMapping(value = "/addtooffer")
	public ApplicationResponse addToOffer(@RequestBody String requestPayload) {

		ApplicationResponse applicationResponse = null;
		try {
			applicationResponse = service.addToOffer(requestPayload);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@RequestMapping(value = "/isuniquetagno")
	public ApplicationResponse isUniqueTagNumber(@RequestParam(value = "tagNo", required = true) String tagNo,
			@RequestParam(value = "offerRevId", required = true) Integer offerRevId) {

		return service.isUniqueTagNumber(tagNo, offerRevId);
	}

	@RequestMapping(value = "/getjsonparameterdatauserinput", method = RequestMethod.GET)
	public ApplicationResponse getJsonParameterDataUserInput(
			@RequestParam(value = "selectedFanId") Integer selectedFanId) {

		return service.getJsonParameterDataUserInput(selectedFanId);
	}

	@RequestMapping(value = "/getperformancegraph")
	public ApplicationResponse getPerformanceGraph(@RequestParam(value = "selectedFanId") Integer selectedFanId) {

		return service.getPerformanceGraph(selectedFanId);
	}

	@RequestMapping(value = "/getquickaccess")
	public ApplicationResponse getQuickAccess(@RequestBody String requestPayload) {

		ApplicationResponse applicationResponse = null;
		try {
			applicationResponse = service.quickDocument(requestPayload);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@RequestMapping(value = "/getmountings")
	public ApplicationResponse getMountings(
			@RequestParam(value = "primeMoverType", required = false) String primeMoverType) {

		ApplicationResponse applicationResponse = null;
		try {
			applicationResponse = service.getMountings(primeMoverType);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@RequestMapping(value = "/geteffclasses")
	public ApplicationResponse getEffClasses() {

		ApplicationResponse applicationResponse = null;
		try {
			applicationResponse = service.getEffClasses();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@RequestMapping(value = "/getspecifications")
	public ApplicationResponse getSpecifications(
			@RequestParam(value = "primeMoverType", required = false) String primeMoverType) {

		ApplicationResponse applicationResponse = null;
		try {
			applicationResponse = service.getSpecifications(primeMoverType);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@RequestMapping(value = "/getambienttemps")
	public ApplicationResponse getAmbientTemps() {

		ApplicationResponse applicationResponse = null;
		try {
			applicationResponse = service.getAmbientTemps();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@RequestMapping(value = "/gettempriseclasses")
	public ApplicationResponse getTempRiseClasses() {

		ApplicationResponse applicationResponse = null;
		try {
			applicationResponse = service.getTempRiseClasses();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@RequestMapping(value = "/getmanufacturers")
	public ApplicationResponse getManufacturers(
			@RequestParam(value = "primeMoverType", required = false) String primeMoverType) {

		ApplicationResponse applicationResponse = null;
		try {
			applicationResponse = service.getManufacturers(primeMoverType);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@RequestMapping(value = "/getpmseries")
	public ApplicationResponse getPmseries(
			@RequestParam(value = "primeMoverType", required = false) String primeMoverType) {

		ApplicationResponse applicationResponse = null;
		try {
			applicationResponse = service.getPmseries(primeMoverType);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@RequestMapping(value = "/getpmpoles")
	public ApplicationResponse getPmPoles() {

		ApplicationResponse applicationResponse = null;
		try {
			applicationResponse = service.getPmPoles();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}

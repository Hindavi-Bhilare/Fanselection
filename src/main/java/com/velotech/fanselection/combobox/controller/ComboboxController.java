
package com.velotech.fanselection.combobox.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.velotech.fanselection.combobox.service.ComboboxService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/combobox")
public class ComboboxController {

	static Logger log = LogManager.getLogger(ComboboxController.class.getName());

	@Autowired
	private ComboboxService service;

	@RequestMapping(value = "/getcomborecords", method = RequestMethod.GET)
	public ApplicationResponse getComboRecords(RequestWrapper requestWrapper) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getComboRecords(requestWrapper);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/getpumptypes", method = RequestMethod.GET)
	public ApplicationResponse getPumpTypes() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getPumpTypes();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/gettablelist", method = RequestMethod.GET)
	public ApplicationResponse getTableList() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getTableList();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}
	@RequestMapping(value = "/getpumptypeswithall", method = RequestMethod.GET)
	public ApplicationResponse getPumpTypesWithAll() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getPumpTypesWithAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/getoffers", method = RequestMethod.GET)
	public ApplicationResponse getOffers(@RequestParam(value = "query", required = false) String query, RequestWrapper requestWrapper) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getOffers(query, requestWrapper);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/getoffertags", method = RequestMethod.GET)
	public ApplicationResponse getOfferTags(@RequestParam(value = "offerRevId") Integer offerRevId) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getOfferTags(offerRevId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/getusercompanies", method = RequestMethod.GET)
	public ApplicationResponse getUserCompanies() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getUserCompanies();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/getprimemover", method = RequestMethod.GET)
	public ApplicationResponse getPrimeMover() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getPrimeMover();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}
/*	
	@RequestMapping(value = "/getprimemovercombo", method = RequestMethod.GET)
	public ApplicationResponse getPrimeMoverCombo(@RequestParam(value = "motorSeriesId",required = false) Integer motorSeriesId) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getPrimeMoverBombo(motorSeriesId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}*/
	@RequestMapping(value = "/getprimemovercombo", method = RequestMethod.GET)
	public ApplicationResponse getPrimeMoverCombo(@RequestParam(value = "motorSeriesId",required = false) Integer motorSeriesId,
			@RequestParam(value = "power",required = false) Double power) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getPrimeMoverBombo(motorSeriesId,power);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}
	

	
	@RequestMapping(value = "/getmotordescriptioncombo", method = RequestMethod.GET)
	public ApplicationResponse getMotorDescription(@RequestParam(value = "series",required = false) String motorSeriesId) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getMotorDescription(motorSeriesId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	/*@RequestMapping(value = "/getcanopy", method = RequestMethod.GET)
	public ApplicationResponse getCanopy() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getCanopy();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}*/

	/*@RequestMapping(value = "/getdrainrim", method = RequestMethod.GET)
	public ApplicationResponse getDrainRim() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getDrainRim();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}*/

	/*@RequestMapping(value = "/getearthing", method = RequestMethod.GET)
	public ApplicationResponse getEarthing() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getEarthing();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}*/

	/*@RequestMapping(value = "/getliftinglug", method = RequestMethod.GET)
	public ApplicationResponse getLiftingLug() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getLiftingLug();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}*/

	/*@RequestMapping(value = "/getstandard", method = RequestMethod.GET)
	public ApplicationResponse getStandard() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getStandard();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}*/

	@RequestMapping(value = "/getmocstd", method = RequestMethod.GET)
	public ApplicationResponse getMocStd(@RequestParam(value = "modelmasterid") Integer modelmasterid) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getMocStd(modelmasterid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	/*
	 * @RequestMapping(value = "/getperformancecurve", method = RequestMethod.GET)
	 * public ApplicationResponse getPerformanceCurve(@RequestParam(value =
	 * "searchValue") Integer modelmasterid) {
	 * 
	 * ApplicationResponse controllerResponse = null; try { controllerResponse =
	 * service.getPerformanceCurve(modelmasterid); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return controllerResponse; }
	 */
	@RequestMapping(value = "/getsegmentmaster", method = RequestMethod.GET)
	public ApplicationResponse getSegmentMaster() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getSegmentMaster();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/getusermaster", method = RequestMethod.GET)
	public ApplicationResponse getUserMaster() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getUserMaster();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/getstatus", method = RequestMethod.GET)
	public ApplicationResponse getStatus() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getStatus();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/getcategory", method = RequestMethod.GET)
	public ApplicationResponse getCategory() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getCategory();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/gettype", method = RequestMethod.GET)
	public ApplicationResponse getType() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getType();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	/*@RequestMapping(value = "/getmodelnrv", method = RequestMethod.GET)
	public ApplicationResponse getModelNrv(@RequestParam(value = "pumpModelId") Integer pumpModelId) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getModelNrv(pumpModelId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}*/

	@RequestMapping(value = "/getstartingmethod", method = RequestMethod.GET)
	public ApplicationResponse getStartingMethod(@RequestParam(value = "primeMoverId") Integer primeMoverId) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getStartingMethod(primeMoverId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	/*@RequestMapping(value = "/getselectiontype", method = RequestMethod.GET)
	public ApplicationResponse getSelectionType() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getSelectionType();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}*/

	@RequestMapping(value = "/getmotorseries", method = RequestMethod.GET)
	public ApplicationResponse getMotorSeries() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getMotorSeries();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

//	@RequestMapping(value = "/getqap", method = RequestMethod.GET)
//	public ApplicationResponse getQap(@RequestParam(value = "offerPumpId") Integer offerPumpId) {
//
//		ApplicationResponse controllerResponse = null;
//		try {
//			controllerResponse = service.getQap(offerPumpId);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return controllerResponse;
//	}

//	@RequestMapping(value = "/getqapforpump", method = RequestMethod.GET)
//	public ApplicationResponse getQapForPump(@RequestParam(value = "offerPumpId") Integer offerPumpId) {
//
//		ApplicationResponse controllerResponse = null;
//		try {
//			controllerResponse = service.getQapForPump(offerPumpId);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return controllerResponse;
//	}

	@RequestMapping(value = "/getsalesperson", method = RequestMethod.GET)
	public ApplicationResponse getSalesPerson() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getSalesPerson();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/getsalespersonbyorganisation", method = RequestMethod.GET)
	public ApplicationResponse getSalesPerson(@RequestParam(value = "organisationId") Integer organisationId) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getSalesPerson(organisationId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}

	@RequestMapping(value = "/getemailids", method = RequestMethod.GET)
	public ApplicationResponse getEmailIds() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getEmailIds();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}
	
	/*@RequestMapping(value = "/getstartingmethodfromwi", method = RequestMethod.GET)
	public ApplicationResponse getStartingMethod() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getStartingMethodfromWi();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}*/
	
	@RequestMapping(value = "/getModelPerformancesCombo", method = RequestMethod.GET)
	public ApplicationResponse getModelPerformancesCombo() {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getModelPerformancesCombo();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;
	}
	
	/*
	 * @RequestMapping(value = "/getIdentityCombo", method = RequestMethod.GET)
	 * public ApplicationResponse getIdentityCombo(@RequestParam(value =
	 * "pumpModelId") Integer pumpModelId) {
	 * 
	 * ApplicationResponse controllerResponse = null; try { controllerResponse =
	 * service.getIdentityCombo(pumpModelId); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return controllerResponse; }
	 */
	
	
}

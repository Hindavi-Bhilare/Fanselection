
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
import com.velotech.fanselection.offer.dto.OfferFanDpDto;
import com.velotech.fanselection.offer.service.OfferDetailsService;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/offer")
public class OfferDetailsController {

	@Autowired
	private OfferDetailsService service;

	@RequestMapping(value = "/updateofferrevision", method = RequestMethod.POST)
	public ApplicationResponse updateOfferRevision(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.updateOfferRevision(requestPayload);
	}

	@RequestMapping(value = "/getoffertree", method = RequestMethod.GET)
	public ApplicationResponse getOfferTree(@RequestParam(value = "offerRevId") Integer offerRevId) {

		return service.getOfferTree(offerRevId);
	}

	@RequestMapping(value = "/copyoffer", method = RequestMethod.POST)
	public ApplicationResponse copyOffer(@RequestParam(value = "offerRevisionId") Integer offerRevisionId,
			@RequestParam(value = "newRevision") String newRevision, @RequestParam(value = "newofferNo") String newofferNo) {

		return service.copyOffer(offerRevisionId, newRevision, newofferNo);
	}

	@RequestMapping(value = "/copyofferrev", method = RequestMethod.POST)
	public ApplicationResponse copyOfferRevision(@RequestParam(value = "offerRevisionId") Integer offerRevisionId,
			@RequestParam(value = "newRevision") String newRevision) {

		return service.copyOfferRevision(offerRevisionId, newRevision);
	}

	@RequestMapping(value = "/copyofferpump", method = RequestMethod.POST)
	public ApplicationResponse copyOfferFan(@RequestParam(value = "offerFanId") Integer offerFanId,
			@RequestParam(value = "newTagNo") String newTagNo) {

		return service.copyOfferFan(offerFanId, newTagNo);
	}

	@RequestMapping(value = "/getrequirements", method = RequestMethod.GET)
	public ApplicationResponse getRequirements(@RequestParam(value = "offerFanId") Integer offerFanId) {

		return service.getRequirements(offerFanId);
	}

	@RequestMapping(value = "/getselectedpumpdetails", method = RequestMethod.GET)
	public ApplicationResponse getSelectedFanDetails(@RequestParam(value = "offerFanId") Integer offerFanId) {

		return service.getSelectedFanDetails(offerFanId);
	}
/*
	@RequestMapping(value = "/getprimemoverdetails", method = RequestMethod.GET)
	public ApplicationResponse getPrimeMoverDetails(@RequestParam(value = "offerFanId") Integer offerFanId) {

		return service.getPrimeMoverDetails(offerFanId);
	}
*/	
	/*@RequestMapping(value = "/getpumpsetga", method = RequestMethod.POST)
	public ApplicationResponse getPumpSetgaDrawing(@RequestParam(value = "offerPumpId") Integer offerPumpId,
			@RequestParam(value = "download") Boolean download) throws JsonParseException, JsonMappingException, IOException {

		return service.getPumpSetgaDrawing(offerPumpId, download);
	}*/

//
//	@RequestMapping(value = "/getselectedpumpqap", method = RequestMethod.GET)
//	public ApplicationResponse getSelectedPumpQap(@RequestParam(value = "offerPumpId") Integer offerPumpId) {
//
//		return service.getSelectedPumpQap(offerPumpId);
//	}
//
//	@RequestMapping(value = "/deleteselectedpumpqap", method = RequestMethod.GET)
//	public ApplicationResponse deleteSelectedPumpQap(@RequestParam(value = "offerPumpId") Integer offerPumpId) {
//
//		return service.deleteSelectedPumpQap(offerPumpId);
//	}
//
//	@RequestMapping(value = "/deleteselectedmotorqap", method = RequestMethod.GET)
//	public ApplicationResponse deleteSelectedMotorQap(@RequestParam(value = "offerPumpId") Integer offerPumpId) {
//
//		return service.deleteSelectedMotorQap(offerPumpId);
//	}
//
//	@RequestMapping(value = "/saveselectedpumpqap", method = RequestMethod.GET)
//	public ApplicationResponse saveSelectedPumpQap(@RequestParam(value = "offerPumpId") Integer offerPumpId,
//			@RequestParam(value = "motorQapId") Integer motorQapId, @RequestParam(value = "pumpQapId") Integer pumpQapId) {
//
//		return service.saveSelectedPumpQap(offerPumpId, motorQapId, pumpQapId);
//	}
//
//	@RequestMapping(value = "/checkissavepumpqap", method = RequestMethod.GET)
//	public ApplicationResponse checkIsSavePumpQap(@RequestParam(value = "offerPumpId") Integer offerPumpId) {
//
//		return service.checkIsSavePumpQap(offerPumpId);
//	}
//
//	@RequestMapping(value = "/checkissavemotorqap", method = RequestMethod.GET)
//	public ApplicationResponse checkIsSaveMotorQap(@RequestParam(value = "offerPumpId") Integer offerPumpId) {
//
//		return service.checkIsSaveMotorQap(offerPumpId);
//	}
//
//	@RequestMapping(value = "/showselectedpumpqap", method = RequestMethod.GET)
//	public ApplicationResponse showSelectedPumpQap(@RequestParam(value = "offerPumpId") Integer offerPumpId,
//			@RequestParam(value = "qapId") Integer qapId) {
//
//		return service.showSelectedPumpQap(qapId);
//	}
	/*@RequestMapping(value = "/getselectedpumpqap", method = RequestMethod.GET)
	public ApplicationResponse getSelectedPumpQap(@RequestParam(value = "offerPumpId") Integer offerPumpId) {

		return service.getSelectedPumpQap(offerPumpId);
	}*/

	/*@RequestMapping(value = "/deleteselectedpumpqap", method = RequestMethod.GET)
	public ApplicationResponse deleteSelectedPumpQap(@RequestParam(value = "offerPumpId") Integer offerPumpId) {

		return service.deleteSelectedPumpQap(offerPumpId);
	}*/

	/*@RequestMapping(value = "/saveselectedpumpqap", method = RequestMethod.GET)
	public ApplicationResponse saveSelectedPumpQap(@RequestParam(value = "offerPumpId") Integer offerPumpId,
			@RequestParam(value = "qapId") Integer qapId) {

		return service.saveSelectedPumpQap(offerPumpId, qapId);
	}*/

	/*@RequestMapping(value = "/showselectedpumpqap", method = RequestMethod.GET)
	public ApplicationResponse showSelectedPumpQap(@RequestParam(value = "offerPumpId") Integer offerPumpId,
			@RequestParam(value = "qapId") Integer qapId) {

		return service.showSelectedPumpQap(offerPumpId, qapId);
	}*/


	/*@RequestMapping(value = "/getbarepumpga", method = RequestMethod.POST)
	public ApplicationResponse getBarePumpgaDrawing(@RequestParam(value = "offerPumpId") Integer offerPumpId,
			@RequestParam(value = "download") Boolean download) throws JsonParseException, JsonMappingException, IOException {

		return service.getBarePumpDrawing(offerPumpId, download);
	}*/

	@RequestMapping(value = "/getcsd", method = RequestMethod.POST)
	public ApplicationResponse getCsd(@RequestParam(value = "offerFanId") Integer offerFanId, @RequestParam(value = "download") Boolean download)
			throws JsonParseException, JsonMappingException, IOException {

		return service.getCsdDrawing(offerFanId, download);
	}

	@RequestMapping(value = "/getprimemoverdrawing", method = RequestMethod.POST)
	public ApplicationResponse getPrimeMoverDrawing(@RequestParam(value = "offerFanId") Integer offerFanId,
			@RequestParam(value = "download") Boolean download) throws JsonParseException, JsonMappingException, IOException {

		return service.getPrimeMoverDrawing(offerFanId, download);
	}

	
	  @RequestMapping(value = "/getdatasheet", method = RequestMethod.GET) public
	  ApplicationResponse getDataSheet(@RequestParam(value = "offerFanId") Integer offerFanId) {
	  
	  return service.getDataSheet(offerFanId); 
	  }
	 

	@RequestMapping(value = "/deleteofferrevision", method = RequestMethod.POST)
	public ApplicationResponse deleteOfferRevision(@RequestParam(value = "offerRevisionId") Integer offerRevisionId) {

		return service.deleteOfferRevision(offerRevisionId);
	}

	@RequestMapping(value = "/deleteofferpump", method = RequestMethod.POST)
	public ApplicationResponse deleteOfferFan(@RequestParam(value = "offerFanId") Integer offerFanId) {

		return service.deleteOfferFan(offerFanId);
	}

	@RequestMapping(value = "/renameofferpumptagno", method = RequestMethod.POST)
	public ApplicationResponse renameOfferFanTagNo(@RequestParam(value = "offerFanId") Integer offerFanId,
			@RequestParam(value = "newTagNo") String newTagNo) {

		return service.renameOfferFanTagNo(offerFanId, newTagNo);
	}

	@RequestMapping(value = "/transferoffer", method = RequestMethod.POST)
	public ApplicationResponse transferOffer(@RequestParam(value = "offerRevisionId") Integer offerRevisionId,
			@RequestParam(value = "userMasterId") String userMasterId) {

		return service.transferOffer(offerRevisionId, userMasterId);
	}

	@RequestMapping(value = "/getquotation", method = RequestMethod.POST)
	public ApplicationResponse getQuotation(@RequestParam(value = "offerRevId") Integer offerRevId)
			throws JsonParseException, JsonMappingException, IOException {

		return service.getQuotation(offerRevId);
	}

	@RequestMapping(value = "/getsummarysheet", method = RequestMethod.POST)
	public ApplicationResponse getSummarySheet(@RequestParam(value = "offerRevId") Integer offerRevId)
			throws JsonParseException, JsonMappingException, IOException {

		return service.getSummarySheet(offerRevId);
	}

	@RequestMapping(value = "/getcosting", method = RequestMethod.POST)
	public ApplicationResponse getCosting(@RequestParam(value = "offerRevId") Integer offerRevId)
			throws JsonParseException, JsonMappingException, IOException {

		return service.getCosting(offerRevId);
	}

	@RequestMapping(value = "/addperformancechartdata", method = RequestMethod.POST)
	public ApplicationResponse addPerformanceChartData(OfferFanDpDto dto) {

		return service.addPerformanceChartData(dto);
	}

	
	  @RequestMapping(value = "/getperformancechart", method = RequestMethod.GET)
	  public ApplicationResponse getPerformanceChart(@RequestParam(value =
	  "offerFanId") Integer offerFanId) {
	  
	  return service.getPerformanceChart(offerFanId); }
	 
//	@RequestMapping(value = "/getofferpumpdpdata", method = RequestMethod.GET)
//	public ApplicationResponse getOfferFanDpData(@RequestParam(value = "offerFanId") Integer offerFanId) {
//
//		return service.getOfferFanDpData(offerFanId);
//	}

	@RequestMapping(value = "/getapprangecalcultion", method = RequestMethod.POST)
	public ApplicationResponse getAppRangeCalculation(OfferFanDpDto dto) {

		return service.getAppRangeCalculation(dto);
	}

	
	@RequestMapping(value = "/getjsonuserinput", method = RequestMethod.GET)
	public ApplicationResponse getJsonUserInput(@RequestParam(value = "offerFanId") Integer offerFanId) {

		return service.getJsonUserInput(offerFanId);
	}
	@RequestMapping(value = "/getjsonparameterdatauser", method = RequestMethod.GET)
	public ApplicationResponse getJsonParameterDataUser(@RequestParam(value = "offerFanId") Integer offerFanId) {

		return service.getJsonParameterDataUser(offerFanId);
	}
	@RequestMapping(value = "/saveuserinput")
	public ApplicationResponse saveJsonUserInput(@RequestBody String requestPayload,@RequestParam(value = "offerFanId") Integer offerFanId) {

		return service.saveJsonUserInput(requestPayload,offerFanId);
		
	}
	
	

}

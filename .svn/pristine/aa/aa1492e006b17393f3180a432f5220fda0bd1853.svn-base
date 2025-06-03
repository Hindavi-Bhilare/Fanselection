package com.velotech.fanselection.offer.service;

import java.io.IOException;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.offer.dto.OfferFanDpDto;
import com.velotech.fanselection.utils.ApplicationResponse;

public interface OfferDetailsService {

	ApplicationResponse getOfferTree(Integer offerRevId);

	ApplicationResponse updateOfferRevision(String requestPayload) throws JsonParseException, JsonMappingException, IOException;

	ApplicationResponse copyOffer(Integer offerRevisionId, String newRevision, String newofferNo);

	ApplicationResponse copyOfferRevision(Integer offerRevisionId, String newRevision);

	ApplicationResponse copyOfferFan(Integer offerFanId, String newTagNo);

	ApplicationResponse getRequirements(Integer offerFanId);

	ApplicationResponse getSelectedFanDetails(Integer offerFanId);

	ApplicationResponse getPrimeMoverDetails(Integer offerFanId);

	//ApplicationResponse getSelectedPumpQap(Integer offerPumpId);

	//ApplicationResponse deleteSelectedPumpQap(Integer offerPumpId);

	//ApplicationResponse saveSelectedPumpQap(Integer offerPumpId, Integer motorQapId, Integer pumpQapId);

	//ApplicationResponse showSelectedPumpQap(Integer qapId);
	//ApplicationResponse saveSelectedPumpQap(Integer offerPumpId, Integer qapId);

	//ApplicationResponse showSelectedPumpQap(Integer offerPumpId, Integer qapId);

	//ApplicationResponse getBarePumpDrawing(Integer offerPumpId, boolean download);

	ApplicationResponse getCsdDrawing(Integer offerFanId, boolean download);

	ApplicationResponse getDataSheet(Integer offerFanId);

	ApplicationResponse deleteOfferRevision(Integer offerRevisionId);

	ApplicationResponse deleteOfferFan(Integer offerFanId);

	ApplicationResponse renameOfferFanTagNo(Integer offerFanId, String newTagNo);

	ApplicationResponse transferOffer(Integer offerRevisionId, String userMasterId);

	ApplicationResponse getQuotation(Integer offerRevId);

	ApplicationResponse getSummarySheet(Integer offerRevId);

	ApplicationResponse getCosting(Integer offerRevId);

	ApplicationResponse getPerformanceChart(Integer offerFanId);

	ApplicationResponse addPerformanceChartData(OfferFanDpDto dpDto);

	ApplicationResponse getAppRangeCalculation(OfferFanDpDto dpDto);

	ApplicationResponse getPrimeMoverDrawing(Integer offerPumpId, Boolean download);

	ApplicationResponse deleteSelectedMotorQap(Integer offerFanId);

	ApplicationResponse checkIsSaveFanQap(Integer offerFanId);

	ApplicationResponse checkIsSaveMotorQap(Integer offerFanId);

	
	
	ApplicationResponse saveJsonUserInput(String requestPayload,Integer offerFanId);

	ApplicationResponse getJsonUserInput(Integer offerFanId);

	ApplicationResponse getJsonParameterDataUserInput(Integer selectedPumpId);
	
	ApplicationResponse getJsonParameterDataUser(Integer offerFanId);

	//ApplicationResponse getPumpSetgaDrawing(Integer offerPumpId, Boolean download);

}


package com.velotech.fanselection.offer.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface SpareOfferMasterService {

	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException;

	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException;

	public ApplicationResponse deleteRecords(List<Integer> ids);

	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception;

	public void downloadExcel(RequestWrapper requestWrapper);

	ApplicationResponse getReport(int spareOfferMasterId);

}

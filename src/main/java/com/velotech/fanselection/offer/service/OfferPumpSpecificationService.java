
package com.velotech.fanselection.offer.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.utils.ApplicationResponse;

public interface OfferPumpSpecificationService {

	ApplicationResponse getRecords(Integer offerPumpId) throws Exception;

	ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException;

	ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException;

	ApplicationResponse deleteRecords(List<Integer> ids);

}

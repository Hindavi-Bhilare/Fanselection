
package com.velotech.fanselection.offer.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.utils.ApplicationResponse;

public interface OfferShareService {

	ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException;

	ApplicationResponse deleteRecords(List<Integer> ids);

	ApplicationResponse getRecords(Integer offerRevId) throws Exception;

	ApplicationResponse updateRecord(String requestPayload);

}

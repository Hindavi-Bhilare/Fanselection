
package com.velotech.fanselection.dxf.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.utils.ApplicationResponse;

public interface DxfService {

	ApplicationResponse getAddtionalParamter(Integer seriesId, Integer modelId, Integer mocId);

	ApplicationResponse getVariants(Integer seriesId, Integer modelId);

	ApplicationResponse getUserCompany();

	ApplicationResponse showDxf(String requestPayload, boolean download) throws JsonParseException, JsonMappingException, IOException;

}


package com.velotech.fanselection.admin.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface UserMasterService {

	ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException;

	ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException;

	ApplicationResponse deleteRecords(List<String> ids);

	ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception;

	void downloadExcel(RequestWrapper requestWrapper);

}

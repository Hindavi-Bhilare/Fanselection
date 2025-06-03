
package com.velotech.fanselection.admin.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface OrganizationRelationService {

	ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException;

	ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException;

	ApplicationResponse deleteRecords(List<Integer> ids);

	ApplicationResponse getRecords(Integer parentId, String organisationCode, RequestWrapper requestWrapper) throws Exception;

	void downloadExcel(RequestWrapper requestWrapper);

	ApplicationResponse getOrganizationtRelationRecord(Integer organizationId);

}

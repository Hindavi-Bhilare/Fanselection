
package com.velotech.fanselection.design.service;

import java.util.List;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface ParameterValueService {

	ApplicationResponse getRecords(RequestWrapper requestWrapper,String uuid);

	ApplicationResponse addRecord(String requestPayload,String uuid);

	ApplicationResponse updateRecord(String requestPayload,String uuid);

	ApplicationResponse deleteRecords(List<Integer> ids);
	
	public void downloadExcel(RequestWrapper requestWrapper,String uuid);

	Boolean deleteRecordsUuid(List<String> uuids);

	Boolean copyRecords(String oldUuid, String newUuid);

	

}

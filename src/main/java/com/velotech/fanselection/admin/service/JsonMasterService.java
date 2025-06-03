package com.velotech.fanselection.admin.service;

import java.util.List;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface JsonMasterService {

	ApplicationResponse getRecords(RequestWrapper requestWrapper);

	ApplicationResponse addRecord(String requestPayload);

	ApplicationResponse updateRecord(String requestPayload);

	ApplicationResponse deleteRecords(List<Integer> ids);

	ApplicationResponse jsonEvaluate(String requestPayload);

}

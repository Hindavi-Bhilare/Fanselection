
package com.velotech.fanselection.design.service;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface PrimeMoverMasterService {

	ApplicationResponse copyRecords(String requestPayload);
	
	ApplicationResponse getRecords(String frameMaster, Double powermin,Double powermax,Double speed, Integer motorTypeId, RequestWrapper requestWrapper);

	void downloadExcel(String frameMaster, 
			Double powermin, Double powermax, Double speed,Integer motorTypeId, RequestWrapper requestWrapper);


}


package com.velotech.fanselection.design.service;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface ModelMasterService {

	ApplicationResponse copyModelMaster(Integer modelmasterid, String fanModel);

	ApplicationResponse getModelMasterDetails(RequestWrapper requestWrapper);
	
	ApplicationResponse upload(String requestPayload);

}

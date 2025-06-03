
package com.velotech.fanselection.admin.service;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface LoginHistoryService {

	ApplicationResponse getRecords(RequestWrapper requestWrapper);

	void downloadExcel(RequestWrapper requestWrapper);

	ApplicationResponse getcountrecords(RequestWrapper requestWrapper);

}

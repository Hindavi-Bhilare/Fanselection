
package com.velotech.fanselection.admin.service;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface EmailLogService {

	ApplicationResponse getRecords(RequestWrapper requestWrapper);

}

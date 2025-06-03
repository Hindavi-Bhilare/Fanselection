
package com.velotech.fanselection.offer.service;

import java.util.Date;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface OfferMasterService {

	ApplicationResponse addRecord(String requestPayload);

	ApplicationResponse getRecords(Integer id, Date startDate, Date endDate, String offerNo, String customerName, String salesPersonName, String project,
			boolean active, String status, RequestWrapper requestWrapper, String organisationDetails);

	ApplicationResponse getRecord(Integer offerRevId);

	ApplicationResponse getOfferPumps(Integer offerRevId);

	ApplicationResponse getOfferStatusHistory(Integer offerRevId);

	ApplicationResponse updateOfferStatus(String requestPayload);

}

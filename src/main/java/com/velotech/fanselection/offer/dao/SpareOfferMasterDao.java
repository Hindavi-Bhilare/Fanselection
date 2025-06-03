
package com.velotech.fanselection.offer.dao;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface SpareOfferMasterDao {

	int getNewLogNumber(Integer companyId);

	ApplicationResponse getSpareOfferMasterData(RequestWrapper requestWrapper);

}

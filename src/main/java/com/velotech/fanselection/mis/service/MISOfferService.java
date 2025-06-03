
package com.velotech.fanselection.mis.service;

import com.velotech.fanselection.mis.dtos.MISOfferSearchDto;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface MISOfferService {

	public ApplicationResponse getRecords(MISOfferSearchDto searchDto);

	public void downloadExcel(RequestWrapper requestWrapper);
}


package com.velotech.fanselection.generic.service;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface GenericService {

	public ApplicationResponse getMetaData(RequestWrapper requestWrapper);

	public ApplicationResponse addRecord(RequestWrapper requestWrapper);

	public ApplicationResponse updateRecord(RequestWrapper requestWrapper);

	public ApplicationResponse deleteRecords(RequestWrapper requestWrapper);

	public ApplicationResponse getRecords(RequestWrapper requestWrapper);

	public ApplicationResponse getRecordsWithPagination(RequestWrapper requestWrapper);

	public ApplicationResponse getComboRecords(RequestWrapper requestWrapper);
}

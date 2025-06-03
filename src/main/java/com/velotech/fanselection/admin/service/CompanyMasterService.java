
package com.velotech.fanselection.admin.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface CompanyMasterService {

	public ApplicationResponse addRecord(MultipartFile logoFile, RequestWrapper requestWrapper)
			throws JsonParseException, JsonMappingException, IOException;

	public ApplicationResponse updateRecord(MultipartFile logoFile, RequestWrapper requestWrapper)
			throws JsonParseException, JsonMappingException, IOException;

	public ApplicationResponse deleteRecords(List<Integer> ids);

	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception;

	public void downloadExcel(RequestWrapper requestWrapper);

}

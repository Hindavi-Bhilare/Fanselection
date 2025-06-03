
package com.velotech.fanselection.design.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface TemplateMasterSerive {

	ApplicationResponse addRecord( RequestWrapper requestWrapper) throws JsonParseException, JsonMappingException, IOException;

	ApplicationResponse updateRecord(RequestWrapper requestWrapper) throws JsonParseException, JsonMappingException, IOException;

	ApplicationResponse deleteRecords(List<Integer> ids);

	ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception;

	void downloadExcel(RequestWrapper requestWrapper);

	public ApplicationResponse viewExistingTemplate(Integer id);

	ApplicationResponse overWriteTemplate(MultipartFile file, Integer id);

	ApplicationResponse upload(String requestPayload);

}


package com.velotech.fanselection.dxf.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.dxf.service.DxfService;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/dxf")
public class DxfController {

	@Autowired
	private DxfService service;

	@RequestMapping(value = "/getAdditionalAttributes", method = RequestMethod.POST)
	public ApplicationResponse getAddtionalParamter(@RequestParam(value = "seriesId") Integer seriesId,
			@RequestParam(value = "modelId") Integer modelId, @RequestParam(value = "mocId") Integer mocId) throws Exception {

		ApplicationResponse applicationResponse = service.getAddtionalParamter(seriesId, modelId, mocId);
		return applicationResponse;
	}

	@RequestMapping(value = "/getVariants", method = RequestMethod.POST)
	public ApplicationResponse getVariants(@RequestParam(value = "seriesId") Integer seriesId, @RequestParam(value = "modelId") Integer modelId)
			throws Exception {

		ApplicationResponse applicationResponse = service.getVariants(seriesId, modelId);
		return applicationResponse;
	}

	@RequestMapping(value = "/getusercompanies", method = RequestMethod.GET)
	public ApplicationResponse getUserCompanies() throws Exception {

		ApplicationResponse applicationResponse = service.getUserCompany();
		return applicationResponse;
	}

	@RequestMapping(value = "/showdxf", method = RequestMethod.POST)
	public ApplicationResponse showDxf(@RequestBody String requestPayload, @RequestParam(value = "download") boolean download)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = service.showDxf(requestPayload, download);
		return applicationResponse;
	}
}

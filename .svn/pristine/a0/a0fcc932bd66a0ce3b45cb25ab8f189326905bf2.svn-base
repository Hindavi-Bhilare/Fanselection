
package com.velotech.fanselection.admin.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.admin.service.PerformanceSpeedtermsService;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/admin/performancespeedterms")
public class PerformanceSpeedtermsController {

	@Autowired
	private PerformanceSpeedtermsService service;

	@RequestMapping(value = "/getseries", method = RequestMethod.GET)
	public ApplicationResponse getSeries() throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		applicationResponse = service.getSeries();
		return applicationResponse;
	}

	@RequestMapping(value = "/getpumpmodel", method = RequestMethod.GET)
	public ApplicationResponse getfanmodel(@RequestParam(value = "seriesId") String seriesId)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		applicationResponse = service.getFanModel(seriesId);
		return applicationResponse;
	}


	@RequestMapping(value = "/getperformancecurvespeed", method = RequestMethod.GET)
	public ApplicationResponse getperformancecurvedia(@RequestParam(value = "fanModelId") String fanModelId)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		applicationResponse = service.getPerformanceCurveSpeed(fanModelId);
		return applicationResponse;
	}

	@RequestMapping(value = "/updateperformancespeedterms", method = RequestMethod.POST)
	public ApplicationResponse upatePerformanceDiaterms(@RequestParam(value = "series") String series,
			@RequestParam(value = "fanModel") String fanModel, 
			@RequestParam(value = "perfCurveSpeed") String perfSpeed) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		applicationResponse = service.upatePerformanceSpeedterms(series, fanModel, perfSpeed);
		return applicationResponse;
	}
}


package com.velotech.fanselection.centrifugalFamilyChart.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.centrifugalFamilyChart.dtos.CentrifugalFamilyChartDto;
import com.velotech.fanselection.centrifugalFamilyChart.service.CentrifugalFamilyChartService;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/centrifugalfamilychart")
public class CentrifugalFamilyChartController {

	static Logger log = LogManager.getLogger(CentrifugalFamilyChartController.class.getName());	

	@Autowired
	private CentrifugalFamilyChartService service;

	@RequestMapping(value = "/getfamilychart", method = RequestMethod.POST)
	public ApplicationResponse addRecord(CentrifugalFamilyChartDto dto) throws JsonParseException, JsonMappingException, IOException {

		return service.getFamilyChart(dto);
	}
	
	@RequestMapping(value = "/getallfamilychart", method = RequestMethod.POST)
	
	public ApplicationResponse addAllPerformancecurve(@RequestParam(value = "seriesId") Integer seriesId, @RequestParam(value = "flow", required = false) String flow,
			@RequestParam(value = "flowUnit", required = false) String flowUnit, @RequestParam(value = "head", required = false) String head,
			@RequestParam(value = "headUnit", required = false) String headUnit, @RequestParam(value = "rpm", required = false) double rpm)
			throws JsonParseException, JsonMappingException, IOException {		

		return service.getAllSeriesFamilyChart(seriesId,flow,flowUnit,head,headUnit,rpm);
	}

	@RequestMapping(value = "/getpumpmodel", method = RequestMethod.GET)
	public ApplicationResponse getpumpmodel(@RequestParam(value = "seriesId") Integer seriesId)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		applicationResponse = service.getFanModel(seriesId);
		return applicationResponse;
	}
	
	@RequestMapping(value = "/getseriesfamilychart", method = RequestMethod.GET)
	public ApplicationResponse getSeries()
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		applicationResponse = service.getSeries();
		return applicationResponse;
	}
}
	


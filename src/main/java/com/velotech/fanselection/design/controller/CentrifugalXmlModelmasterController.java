
package com.velotech.fanselection.design.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.velotech.fanselection.design.service.CentrifugalXmlModelmasterService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/admin/centrifugalxmlmodelmaster")
public class CentrifugalXmlModelmasterController {

	@Autowired
	private CentrifugalXmlModelmasterService service;

	@RequestMapping(value = "/copy", method = RequestMethod.POST)
	public ApplicationResponse copyRecord(@RequestParam(value = "data") List<Integer> data) {

		return service.copyRecord(data);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApplicationResponse deleteRecords(@RequestParam(value = "ids") List<Integer> performanceCurveNo) {

		return service.deleteRecords(performanceCurveNo);
	}

	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		return service.getRecords(requestWrapper);
	}

}


package com.velotech.fanselection.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.velotech.fanselection.admin.service.UtilityService;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/admin/utility")
public class UtilityController {

	@Autowired
	private UtilityService service;

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ApplicationResponse exportRecord(@RequestParam(value = "pumpTypeId", required = true) Integer pumpTypeId,
			@RequestParam(value = "motorSeriesId", required = true) Integer motorSeriesId) {

		return service.exportRecord(pumpTypeId, motorSeriesId);
	}
}

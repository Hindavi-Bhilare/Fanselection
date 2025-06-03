
package com.velotech.fanselection.admin.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.admin.service.OrganizationRelationService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/admin/organizationrelation")
public class OrganizationRelationController {

	@Autowired
	private OrganizationRelationService service;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApplicationResponse addRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.addRecord(requestPayload);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ApplicationResponse updateRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		return service.updateRecord(requestPayload);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApplicationResponse deleteRecords(@RequestParam(value = "ids") List<Integer> ids) {

		return service.deleteRecords(ids);
	}

	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecords(@RequestParam(value = "parentId") Integer parentId,
			@RequestParam(value = "organisationCode", required = false) String organisationCode, RequestWrapper requestWrapper) throws Exception {

		return service.getRecords(parentId, organisationCode, requestWrapper);
	}

	@RequestMapping(value = "/downloadexcel", method = RequestMethod.GET)
	public void downloadExcel(RequestWrapper requestWrapper) {

		service.downloadExcel(requestWrapper);
	}

	@RequestMapping(value = "/getorganizationrelationrecord", method = RequestMethod.GET)
	public ApplicationResponse getOrganizationtRelationRecord(@RequestParam(value = "organizationId") Integer organizationId) {

		return service.getOrganizationtRelationRecord(organizationId);
	}

}

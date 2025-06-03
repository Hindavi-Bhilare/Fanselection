
package com.velotech.fanselection.design.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.MasterSericeUtil;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/design/variantmaster")
public class VariantMasterController {

	static String serviceName = "VariantMaster";

	@Autowired
	private MasterSericeUtil serviceUtil;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApplicationResponse addRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		MasterService service = serviceUtil.getService(serviceName);
		return service.addRecord(requestPayload);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ApplicationResponse updateRecord(@RequestBody String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		MasterService service = serviceUtil.getService(serviceName);
		return service.updateRecord(requestPayload);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApplicationResponse deleteRecords(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = null;
		try {
			MasterService service = serviceUtil.getService(serviceName);
			applicationResponse = service.deleteRecords(requestWrapper.getIds());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (applicationResponse == null)
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, "Data could not deleted.Delete child item first");
		return applicationResponse;
	}

	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		MasterService service = serviceUtil.getService(serviceName);
		return service.getRecords(requestWrapper);
	}

	@RequestMapping(value = "/downloadexcel", method = RequestMethod.GET)
	public void downloadExcel(RequestWrapper requestWrapper) {

		MasterService service = serviceUtil.getService(serviceName);
		service.downloadExcel(requestWrapper);
	}

}

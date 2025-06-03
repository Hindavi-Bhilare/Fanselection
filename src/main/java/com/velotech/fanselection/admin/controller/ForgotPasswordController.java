package com.velotech.fanselection.admin.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.admin.service.ForgotPasswordService;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/forgotpassword")
public class ForgotPasswordController {

	@Autowired
	private ForgotPasswordService service;

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ApplicationResponse forgotRecord(@RequestBody String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {

		return service.getRecords(requestPayload);
	}

}

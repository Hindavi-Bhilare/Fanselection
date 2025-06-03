
package com.velotech.fanselection.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.velotech.fanselection.admin.service.LoginHistoryService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/admin/loginhistory")
public class LoginHistoryController {

	static String serviceName = "LoginHistory";

	@Autowired
	private LoginHistoryService service;

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);

		// true passed to CustomDateEditor constructor means convert empty
		// String to null
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		return service.getRecords(requestWrapper);
	}

	@RequestMapping(value = "/downloadexcel", method = RequestMethod.GET)
	public void downloadExcel(RequestWrapper requestWrapper) {

		service.downloadExcel(requestWrapper);
	}

	@RequestMapping(value = "/getcountrecords", method = RequestMethod.GET)
	public ApplicationResponse getCountRecords(RequestWrapper requestWrapper) {

		return service.getcountrecords(requestWrapper);
	}

}


package com.velotech.fanselection.dashboard.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.velotech.fanselection.dashboard.service.DashboardService;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService service;

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);

		// true passed to CustomDateEditor constructor means convert empty
		// String to null
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/getlast10dayssearchcount")
	public ApplicationResponse getLast10DaysSearchCount() {

		return service.getLast10DaysSearchCount();
	}

	@RequestMapping(value = "/getofferscountstatuswise")
	public ApplicationResponse getOffersCountStatusWise(@RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "endDate") Date endDate) {

		return service.getOffersCountStatusWise(startDate, endDate);
	}

	@RequestMapping(value = "/getlast10daysoffers")
	public ApplicationResponse getLast10DaysOffers() {

		return service.getLast10DaysOffers();
	}

	@RequestMapping(value = "/getlast12monthsofferscount")
	public ApplicationResponse getLast12MonthsOffersCount() {

		return service.getLast12MonthsOffersCount();
	}
}

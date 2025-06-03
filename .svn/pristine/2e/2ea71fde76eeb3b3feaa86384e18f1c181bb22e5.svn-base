
package com.velotech.fanselection.offer.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.velotech.fanselection.offer.service.DownloadOfferService;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/downloadoffer")
public class DownloadOfferController {

	@Autowired
	private DownloadOfferService service;

	@Autowired
	private HttpServletResponse response;

	@RequestMapping(value = "/getdata", method = RequestMethod.GET)
	public ApplicationResponse getDownloadOfferData(@RequestParam(value = "offerRevId") Integer offerRevId) throws Exception {

		return service.getDownloadOfferData(offerRevId);
	}

	@RequestMapping(value = "/downloadoffer", method = RequestMethod.POST)
	public void downloadOffer(@RequestBody String requestPayload) throws Exception {

		PrintWriter out;
		try {
			String path = service.downloadOfferMaster(requestPayload, false);
			out = response.getWriter();
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "no-store");
			out.print(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/getofferpumpdata", method = RequestMethod.GET)
	public ApplicationResponse getDownloadOfferFanData(@RequestParam(value = "offerFanId") Integer offerFanId) throws Exception {

		return service.getDownloadOfferFanData(offerFanId);
	}

	@RequestMapping(value = "/downloadofferpump", method = RequestMethod.POST)
	public void downloadOfferFan(@RequestBody String requestPayload) throws Exception {

		service.downloadOfferFan(requestPayload);

	}

	@RequestMapping(value = "/getuseremailstatus", method = RequestMethod.GET)
	public ApplicationResponse getUserEmailStatus() throws Exception {

		return service.getUserEmailStatus();
	}

	@RequestMapping(value = "/sendemail", method = RequestMethod.POST)
	public ApplicationResponse sendEmail(@RequestBody String requestPayload) throws Exception {

		return service.sendEmail(requestPayload);

	}
}

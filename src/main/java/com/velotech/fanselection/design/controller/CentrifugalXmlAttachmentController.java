package com.velotech.fanselection.design.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.velotech.fanselection.design.service.CentrifugalXmlAttachmentService;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/admin/centrifugalxmlattachment")
public class CentrifugalXmlAttachmentController {

	@Autowired
	private CentrifugalXmlAttachmentService service;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApplicationResponse xmlDataAttachment1(
			@RequestParam(value = "headAttachment", required = false) MultipartFile headAttachment,
			@RequestParam(value = "powerAttachment", required = false) MultipartFile powerAttachment,
			@RequestParam(value = "headDeg", required = false) Integer headDeg,
			@RequestParam(value = "powerDeg", required = false) Integer powerDeg,
			@RequestParam(value = "excelCheck", required = false) boolean excelCheck,
			@RequestParam(value = "id", required = false) Integer fanModelId) {
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			applicationResponse = service.uploadXmlAttachment(headAttachment, powerAttachment, headDeg, powerDeg, excelCheck,
					fanModelId);

		} catch (Exception e) {
		}
		return applicationResponse;
	}
	
}

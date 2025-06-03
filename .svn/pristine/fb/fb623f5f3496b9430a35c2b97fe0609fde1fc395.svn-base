package com.velotech.fanselection.admin.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.admin.service.FileUploadService;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@RestController
@RequestMapping(value = "/admin/fileutility")
public class FileUploadController {
	
	@Autowired
private FileUploadService service;
	
	@RequestMapping(value = "/getrecords", method = RequestMethod.GET)
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {
		return service.getAllFiles(requestWrapper);
	}
	
	@RequestMapping(value = "/getFilePath", method = RequestMethod.POST)
	public ApplicationResponse getFilePath(RequestWrapper requestWrapper) throws Exception {
		return service.getFilePath(requestWrapper);
	}
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ApplicationResponse uploadFile(@RequestParam(value = "file", required = false) MultipartFile file
		) throws JsonParseException, JsonMappingException, IOException {

		return service.uploadFile(file);
	}
	
	@RequestMapping(value = "/getComboRecords", method = RequestMethod.GET)
	public ApplicationResponse getComboboxRecords( RequestWrapper requestWrapper) throws JsonParseException, JsonMappingException, IOException {

		return service.getComboboxRecords(requestWrapper);
	}

}

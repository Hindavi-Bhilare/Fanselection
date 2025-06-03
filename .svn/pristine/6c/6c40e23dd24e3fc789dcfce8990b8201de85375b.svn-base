
package com.velotech.fanselection.File.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.velotech.fanselection.File.service.FileService;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/file")
public class FileController {

	static Logger log = LogManager.getLogger(FileController.class.getName());

	@Autowired
	private FileService service;

	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public ApplicationResponse FileUpload(@RequestParam(value = "recordId") Integer recordId, @RequestParam(value = "tableName") String tableName,
			@RequestParam(value = "file") MultipartFile multipartFile, @RequestParam(value = "columnName") String columnName) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.uploadFile(recordId, tableName, multipartFile, columnName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;

	}

	@RequestMapping(value = "/getfile", method = RequestMethod.POST)
	public ApplicationResponse getFile(@RequestParam(value = "documentId") Integer documentId) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.getFile(documentId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;

	}

	@RequestMapping(value = "/deletefile", method = RequestMethod.POST)
	public ApplicationResponse deleteFile(@RequestParam(value = "documentId") Integer documentId, @RequestParam(value = "recordId") Integer recordId,

			@RequestParam(value = "tableName") String tableName, @RequestParam(value = "columnName") String columnName) {

		ApplicationResponse controllerResponse = null;
		try {
			controllerResponse = service.deleteFile(documentId, recordId, tableName, columnName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return controllerResponse;

	}

}

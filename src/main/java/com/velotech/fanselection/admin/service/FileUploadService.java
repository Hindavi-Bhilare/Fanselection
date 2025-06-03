package com.velotech.fanselection.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

@Service
public interface FileUploadService {
	
	ApplicationResponse getAllFiles(RequestWrapper requestWrapper);

	ApplicationResponse getFilePath(RequestWrapper requestWrapper);

	ApplicationResponse uploadFile(MultipartFile file);

	ApplicationResponse getComboboxRecords(RequestWrapper requestWrapper);

}

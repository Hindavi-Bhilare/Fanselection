package com.velotech.fanselection.design.service;

import org.springframework.web.multipart.MultipartFile;

import com.velotech.fanselection.utils.ApplicationResponse;

public interface CentrifugalXmlAttachmentService { 
	
	ApplicationResponse uploadXmlAttachment(MultipartFile headAttachment, MultipartFile powerAttachment,
			Integer headDeg, Integer powerDeg, boolean excelCheck, Integer fanModelId);


}

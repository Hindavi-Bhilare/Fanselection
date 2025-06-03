
package com.velotech.fanselection.File.service;

import org.springframework.web.multipart.MultipartFile;

import com.velotech.fanselection.utils.ApplicationResponse;

public interface FileService {

	ApplicationResponse uploadFile(Integer recordId, String tableName, MultipartFile multipartFile, String columnName);

	ApplicationResponse getFile(Integer documentId);

	ApplicationResponse deleteFile(Integer documentId, Integer recordId, String tableName, String columnName);

}

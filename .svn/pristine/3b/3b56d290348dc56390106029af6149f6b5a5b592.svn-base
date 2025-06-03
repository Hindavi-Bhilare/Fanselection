
package com.velotech.fanselection.File.service;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl90DocumentMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.FileUtil;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class FileServiceImpl implements FileService {

	static Logger log = LogManager.getLogger(FileServiceImpl.class.getName());

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private GenericDao genericDao;

	@Override
	public ApplicationResponse uploadFile(Integer recordId, String tableName, MultipartFile multipartFile, String columnName) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl90DocumentMaster tbl90DocumentMaster = new Tbl90DocumentMaster();
			String fileName = multipartFile.getOriginalFilename();
			String folderPath = getPathForFile(tableName);
			tbl90DocumentMaster.setLocation(folderPath);
			tbl90DocumentMaster.setReferenceTable(tableName);
			genericDao.save(tbl90DocumentMaster);

			String query = "UPDATE pumpManagement_v5_submersible.dbo." + tableName + " SET " + columnName + " =" + tbl90DocumentMaster.getId() + " WHERE id="
					+ recordId;
			genericDao.executeUpdate(query);
			String[] fileNameArray = fileName.split("\\.");
			String duplicateFileName = String.join(".", (fileNameArray[0] + "_" + tbl90DocumentMaster.getId()), fileNameArray[1]);
			tbl90DocumentMaster.setFileName(duplicateFileName);
			if (tableName.equals("tbl_03_qap_master")) {
				File destFile = new File(velotechUtil.getCompanyDocumentPath(), duplicateFileName);
				if (!destFile.exists())
					FileUtil.convertMultipartFile(multipartFile, velotechUtil.getCompanyDocumentPath(), duplicateFileName);

			} else {
				FileUtil.uploadFile(folderPath, duplicateFileName, multipartFile);
			}
			genericDao.update(tbl90DocumentMaster);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private String getPathForFile(String tableName) {

		if (tableName.equals("tbl_03_qap_master"))
			return velotechUtil.getCompanyDocumentPath();
		else
			return velotechUtil.getCompanyId() + "/" + tableName;
	}

	@Override
	public ApplicationResponse getFile(Integer documentId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Object finalPath = "";
			Tbl90DocumentMaster tbl90DocumentMaster = (Tbl90DocumentMaster) genericDao.getRecordById(Tbl90DocumentMaster.class, documentId);
			String fileName = tbl90DocumentMaster.getFileName();
			String path = tbl90DocumentMaster.getLocation();
			String tableName = tbl90DocumentMaster.getReferenceTable();
			if (tableName.equals("tbl_03_qap_master")) {
				finalPath = velotechUtil.getCompanyDocumentPath();
				File destFile = new File(path, fileName);
				if (destFile.exists())
					finalPath = finalPath + fileName;
			} else
				finalPath = FileUtil.showFile(path, fileName, fileName, false);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, "success", finalPath);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteFile(Integer documentId, Integer recordId, String tableName, String columnName) {

		ApplicationResponse applicationResponse = null;
		boolean result = false;
		try {
			String query = "UPDATE pumpManagement_v5_submersible.dbo." + tableName + " SET " + columnName + " =" + "null" + " WHERE id=" + recordId;
			genericDao.executeUpdate(query);

			Tbl90DocumentMaster tbl90DocumentMaster = (Tbl90DocumentMaster) genericDao.getRecordById(Tbl90DocumentMaster.class, documentId);
			String fileName = tbl90DocumentMaster.getFileName();
			String path = tbl90DocumentMaster.getLocation();
			if (tableName.equals("tbl_03_qap_master")) {
				File file = new File(path + "/" + fileName);
				if (file.exists())
					file.delete();
			} else
				FileUtil.deleteFile(path, fileName);
			genericDao.delete(tbl90DocumentMaster);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(result, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}

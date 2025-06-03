package com.velotech.fanselection.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.velotech.fanselection.dtos.FileUploadTreeDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.ComboBox;
import com.velotech.fanselection.utils.FileUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	static Logger log = LogManager.getLogger(FileUploadServiceImpl.class.getName());

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private GenericDao genericDao;

	@Override
	public ApplicationResponse getAllFiles(RequestWrapper requestWrapper) {
		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<Object> files = new ArrayList<>();
		try {
			FileUtil fileUtil = new FileUtil();
			files = fileUtil.loadFilesWithExtension((String) requestWrapper.getSearchValue());

			Object data = getTreeData(files);

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applicationResponse;
	}

	private Object getTreeData(List<Object> files) {

		FileUploadTreeDto mainBranch = new FileUploadTreeDto();
		mainBranch.setText("Documents");
		mainBranch.setExpanded(true);
		mainBranch.setIconCls("x-fa fa-briefcase blue");
		// mainBranch.setCard("od-offerrevision");
		List<Object> mainsChildren = new ArrayList<>();
		try {
			for (Object node : files) {

				FileUploadTreeDto branch = new FileUploadTreeDto();
				branch.setText((String) node);
				branch.setIconCls("x-fa fa-briefcase dark-grey");
				// branch.setCard("od-offerpump");
				branch.setLeaf(true);
				mainsChildren.add(branch);
			}
			mainBranch.setData(mainsChildren);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mainBranch;
	}

	@Override
	public ApplicationResponse getFilePath(RequestWrapper requestWrapper) {
		ApplicationResponse applicationResponse = new ApplicationResponse();
		String path = null;
		try {
			VelotechUtil fileUtil = new VelotechUtil();
			if (requestWrapper.getSearchProperty().contains(".dxf")) {

				// Tbl80TemplateMaster templateMaster = new
				// Tbl80TemplateMaster();
				// //
				// templateMaster=genericDao.getRecords(Tbl80TemplateMaster.class,
				// // restrictions)

				path = fileUtil.getCompanyContextPathDxf(requestWrapper);

			} else if (requestWrapper.getSearchProperty().contains(".jasper")) {
				path = fileUtil.getBlankJsonReport(requestWrapper.getSearchProperty(), "jasper");
			} else {

				path = fileUtil.getCompanyContextPathOthers();
			}

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, path);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse uploadFile(MultipartFile file) {
		ApplicationResponse applicationResponse = new ApplicationResponse();

		String fileName = file.getOriginalFilename();
		FileUtil.convertMultipartFile(file, velotechUtil.getCompanyDocumentPath(), fileName);

		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, "File Uploaded Successfully.");

		return applicationResponse;
	}

	@Override
	public ApplicationResponse getComboboxRecords(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			FileUtil fileUtil = new FileUtil();

			List<Object> files = fileUtil.loadFilesWithExtension((String) requestWrapper.getSearchValue());
			// long total = applicationResponse.getTotal();

			List<ComboBox> dtos = new ArrayList<>();
			for (Object file : files) {
				ComboBox dto = new ComboBox();
				dto.setLabel(file);
				dto.setValue(file);

				dtos.add(dto);
			}

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}

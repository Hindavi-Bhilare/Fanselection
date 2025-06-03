
package com.velotech.fanselection.admin.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.admin.dao.DocumentMasterDao;
import com.velotech.fanselection.dtos.DocumentMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl90DocumentMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.ComboBox;
import com.velotech.fanselection.utils.FileUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class DocumentMasterServiceImpl implements MasterService, DocumentMasterService {

	static Logger log = LogManager.getLogger(DocumentMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private DocumentMasterDao dao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(MultipartFile file) {

		ApplicationResponse applicationResponse = null;
		Tbl90DocumentMaster model = new Tbl90DocumentMaster();
		// DocumentMasterDto dto = new DocumentMasterDto();
		// ObjectMapper mapper = new ObjectMapper();
		// dto = mapper.readValue(requestPayload, DocumentMasterDto.class);

		String fileName = file.getOriginalFilename();

		List<Tbl90DocumentMaster> models = (List<Tbl90DocumentMaster>) genericDao.findByParam(Tbl90DocumentMaster.class,
				"fileName", fileName);
		if (models.size() < 1) {
			model.setFileName(fileName);
			model.setCompany(velotechUtil.getCompany());
			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.INSERT_SUCCESS_MSG, "true");
		} else
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.RECORD_EXISTS_MSG, "false");

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		DocumentMasterDto dto = new DocumentMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, DocumentMasterDto.class);
		Tbl90DocumentMaster model = (Tbl90DocumentMaster) genericDao.getRecordById(Tbl90DocumentMaster.class,
				dto.getId());
		BeanUtils.copyProperties(dto, model);
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl90DocumentMaster.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();

		applicationResponse = dao.getRecords(requestWrapper);

		List<Tbl90DocumentMaster> models = (List<Tbl90DocumentMaster>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<DocumentMasterDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
				ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	private List<DocumentMasterDto> getData(List<Tbl90DocumentMaster> models) {

		List<DocumentMasterDto> dtos = new ArrayList<>();
		for (Tbl90DocumentMaster model : models) {
			DocumentMasterDto dto = new DocumentMasterDto();
			BeanUtils.copyProperties(model, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "Application Master" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> criterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(),
						requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			@SuppressWarnings("unchecked")
			List<Tbl90DocumentMaster> models = (List<Tbl90DocumentMaster>) genericDao.getRecords(
					Tbl90DocumentMaster.class, GenericUtil.getConjuction(Tbl90DocumentMaster.class, criterias));
			List<DocumentMasterDto> dtos = getData(models);

			Field[] propertyFields = DocumentMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (DocumentMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@Override
	public ApplicationResponse uploadFile(MultipartFile file) {
		ApplicationResponse applicationResponse = new ApplicationResponse();

		String fileName = file.getOriginalFilename();

		try {
			FileUtil.convertMultipartFile(file, velotechUtil.getCompanyDocumentPath(), fileName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, "File Uploaded Successfully.");

		return applicationResponse;
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
	public ApplicationResponse addRecord(String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationResponse getComboboxRecords(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			// FileUtil fileUtil = new FileUtil();
			requestWrapper.setDisplayField("Combo");
			applicationResponse = dao.getRecords(requestWrapper);

			List<Tbl90DocumentMaster> documentMasters = new ArrayList<>();
			documentMasters = (List<Tbl90DocumentMaster>) applicationResponse.getData();
			List<ComboBox> dtos = new ArrayList<>();
			if (!requestWrapper.getSearchProperty().equals(".dxf")) {
				ComboBox dto = new ComboBox();
				dto.setLabel("None");
				dto.setValue(null);
				dtos.add(dto);
			}
			for (Tbl90DocumentMaster file : documentMasters) {
				ComboBox dto1 = new ComboBox();
				dto1.setLabel(file.getFileName());
				dto1.setValue(file.getId());

				dtos.add(dto1);
			}

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}

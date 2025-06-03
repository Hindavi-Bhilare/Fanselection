
package com.velotech.fanselection.admin.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Conjunction;
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
import com.velotech.fanselection.dtos.CompanyMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.FileUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class CompanyMasterServiceImpl implements CompanyMasterService {

	static Logger log = LogManager.getLogger(CompanyMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(MultipartFile logoFile, RequestWrapper requestWrapper)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl28CompanyMaster model = new Tbl28CompanyMaster();
		CompanyMasterDto dto = new CompanyMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			dto = requestWrapper.getCompanyMasterDto();
			boolean ans = uploadCompanyLogoFile(logoFile);
			if (ans) {
				List<Tbl28CompanyMaster> models = (List<Tbl28CompanyMaster>) genericDao.findByParam(Tbl28CompanyMaster.class, "companyName",
						dto.getCompanyName());
				if (models.size() < 1) {
					BeanUtils.copyProperties(dto, model);
					model.setCompany(velotechUtil.getCompany());
					model.setLogo(logoFile.getOriginalFilename());
					genericDao.save(model);
					applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
				} else
					applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.RECORD_EXISTS_MSG);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(MultipartFile logoFile, RequestWrapper requestWrapper)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		CompanyMasterDto dto = new CompanyMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			dto = requestWrapper.getCompanyMasterDto();
			boolean ans = updateCompanyLogoFile(logoFile, dto.getLogo());

			Tbl28CompanyMaster model = (Tbl28CompanyMaster) genericDao.getRecordById(Tbl28CompanyMaster.class, dto.getId());
			BeanUtils.copyProperties(dto, model);
			model.setLogo(logoFile.getOriginalFilename());
			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl28CompanyMaster.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<SearchCriteria> searchCriterias = new ArrayList<>();
		if (requestWrapper.getSearchValue() != "") {
			SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
			searchCriterias.add(searchCriteria);
		}

		Conjunction conjunction = GenericUtil.getConjuction(Tbl28CompanyMaster.class, searchCriterias);
		applicationResponse = genericDao.getRecords(Tbl28CompanyMaster.class, conjunction, requestWrapper.getPagination());

		List<Tbl28CompanyMaster> models = (List<Tbl28CompanyMaster>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<CompanyMasterDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	private List<CompanyMasterDto> getData(List<Tbl28CompanyMaster> models) {

		List<CompanyMasterDto> dtos = new ArrayList<>();
		for (Tbl28CompanyMaster model : models) {
			CompanyMasterDto dto = new CompanyMasterDto();
			BeanUtils.copyProperties(model, dto);
			dto.setLogoPath(velotechUtil.getCompanyDocumentContextPath()+ System.getProperty("file.separator") + model.getLogo());
			dtos.add(dto);
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "Company Master" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> criterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			List<Tbl28CompanyMaster> models = (List<Tbl28CompanyMaster>) genericDao.getRecords(Tbl28CompanyMaster.class,
					GenericUtil.getConjuction(Tbl28CompanyMaster.class, criterias));

			List<CompanyMasterDto> dtos = getData(models);

			Field[] propertyFields = CompanyMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (CompanyMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public boolean uploadCompanyLogoFile(MultipartFile logoFile) {

		boolean confirmation = false;
		//String realPath = velotechUtil.getCompanyPath() + "logo" + System.getProperty("file.separator");
		try {
			File destFile = new File(velotechUtil.getCompanyDocumentPath(), logoFile.getOriginalFilename());
			FileUtil.convertMultipartFile(logoFile, velotechUtil.getCompanyDocumentPath(), logoFile.getOriginalFilename());
			confirmation = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return confirmation;
	}

	public boolean updateCompanyLogoFile(MultipartFile logoFile, String logoName) {

		boolean confirmation = false;
		//String realPath = velotechUtil.getCompanyPath() + "logo" + System.getProperty("file.separator");
		try {
			File destFile = new File(velotechUtil.getCompanyDocumentPath(), logoName);
			if (destFile.exists())
				destFile.delete();
			String path = velotechUtil.getCompanyDocumentPath() + logoFile.getOriginalFilename();
			File file = new File(path);
			FileUtil.convertMultipartFile(logoFile, velotechUtil.getCompanyDocumentPath(), logoFile.getOriginalFilename());
			confirmation = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return confirmation;
	}
}

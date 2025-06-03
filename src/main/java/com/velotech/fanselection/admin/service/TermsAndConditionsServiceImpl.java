
package com.velotech.fanselection.admin.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.TermsAndConditionsDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl90Termsandconditions;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class TermsAndConditionsServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(TermsAndConditionsServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl90Termsandconditions model = new Tbl90Termsandconditions();
		TermsAndConditionsDto dto = new TermsAndConditionsDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, TermsAndConditionsDto.class);

		List<Tbl90Termsandconditions> models = (List<Tbl90Termsandconditions>) genericDao.findByParam(Tbl90Termsandconditions.class, "termType",
				dto.getTermType());

		if (models.size() < 1) {
			BeanUtils.copyProperties(dto, model);
			model.setCompany(velotechUtil.getCompany());
			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} else
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.RECORD_EXISTS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		TermsAndConditionsDto dto = new TermsAndConditionsDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, TermsAndConditionsDto.class);
		Tbl90Termsandconditions model = (Tbl90Termsandconditions) genericDao.getRecordById(Tbl90Termsandconditions.class, dto.getId());
		BeanUtils.copyProperties(dto, model);
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl90Termsandconditions.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<SearchCriteria> searchCriterias = new ArrayList<>();
		if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
			SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
			searchCriterias.add(searchCriteria);
		}

		Conjunction conjunction = GenericUtil.getConjuction(Tbl90Termsandconditions.class, searchCriterias);
		applicationResponse = genericDao.getRecords(Tbl90Termsandconditions.class, conjunction, requestWrapper.getPagination());

		List<Tbl90Termsandconditions> models = (List<Tbl90Termsandconditions>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<TermsAndConditionsDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	private List<TermsAndConditionsDto> getData(List<Tbl90Termsandconditions> models) {

		List<TermsAndConditionsDto> dtos = new ArrayList<>();
		for (Tbl90Termsandconditions model : models) {
			TermsAndConditionsDto dto = new TermsAndConditionsDto();
			BeanUtils.copyProperties(model, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "DocumentsTypeMaster" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}

			List<Tbl90Termsandconditions> models = (List<Tbl90Termsandconditions>) genericDao.getRecords(Tbl90Termsandconditions.class,
					GenericUtil.getConjuction(Tbl90Termsandconditions.class, searchCriterias));
			List<TermsAndConditionsDto> dtos = getData(models);

			Field[] propertyFields = TermsAndConditionsDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (TermsAndConditionsDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}

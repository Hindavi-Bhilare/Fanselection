
package com.velotech.fanselection.admin.service;

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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.RoleMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl54Rolemaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class RoleMasterServiceImpl implements MasterService {

	static Logger log =LogManager.getLogger(RoleMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl54Rolemaster model = new Tbl54Rolemaster();
		RoleMasterDto dto = new RoleMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, RoleMasterDto.class);

		List<Tbl54Rolemaster> models = (List<Tbl54Rolemaster>) genericDao.findByParam(Tbl54Rolemaster.class, "role", dto.getRole());
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
		RoleMasterDto dto = new RoleMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, RoleMasterDto.class);
		Tbl54Rolemaster model = (Tbl54Rolemaster) genericDao.getRecordById(Tbl54Rolemaster.class, dto.getId());
		BeanUtils.copyProperties(dto, model);
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl54Rolemaster.class, ids);
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

		Conjunction conjunction = GenericUtil.getConjuction(Tbl54Rolemaster.class, searchCriterias);
		applicationResponse = genericDao.getRecords(Tbl54Rolemaster.class, conjunction, requestWrapper.getPagination());

		List<Tbl54Rolemaster> models = (List<Tbl54Rolemaster>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<RoleMasterDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	private List<RoleMasterDto> getData(List<Tbl54Rolemaster> models) {

		List<RoleMasterDto> dtos = new ArrayList<>();
		for (Tbl54Rolemaster model : models) {
			RoleMasterDto dto = new RoleMasterDto();
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
			response.setHeader("Content-Disposition", "attachment; filename=" + "Role Master" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> criterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			List<Tbl54Rolemaster> models = (List<Tbl54Rolemaster>) genericDao.getRecords(Tbl54Rolemaster.class,
					GenericUtil.getConjuction(Tbl54Rolemaster.class, criterias));
			List<RoleMasterDto> dtos = getData(models);

			Field[] propertyFields = RoleMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (RoleMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}

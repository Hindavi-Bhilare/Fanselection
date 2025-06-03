
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
import com.velotech.fanselection.dtos.DropdownlistDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl09Dropdownlist;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class DropdownlistServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(DropdownlistServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl09Dropdownlist model = new Tbl09Dropdownlist();
		DropdownlistDto dto = new DropdownlistDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, DropdownlistDto.class);

		List<SearchCriteria> searchCriterias = new ArrayList<>();
		SearchCriteria description = new SearchCriteria("description", dto.getDescription());
		SearchCriteria value = new SearchCriteria("value", dto.getValue());
		searchCriterias.add(description);
		searchCriterias.add(value);

		List<Tbl09Dropdownlist> models = (List<Tbl09Dropdownlist>) genericDao.getRecords(Tbl09Dropdownlist.class,
				GenericUtil.getConjuction(Tbl09Dropdownlist.class, searchCriterias));

		if (models.size() == 0) {
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
		DropdownlistDto dto = new DropdownlistDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, DropdownlistDto.class);
		Tbl09Dropdownlist model = (Tbl09Dropdownlist) genericDao.getRecordById(Tbl09Dropdownlist.class, dto.getId());
		BeanUtils.copyProperties(dto, model);
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl09Dropdownlist.class, ids);
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

		Conjunction conjunction = GenericUtil.getConjuction(Tbl09Dropdownlist.class, searchCriterias);
		applicationResponse = genericDao.getRecords(Tbl09Dropdownlist.class, conjunction, requestWrapper.getPagination());

		List<Tbl09Dropdownlist> models = (List<Tbl09Dropdownlist>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<DropdownlistDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	private List<DropdownlistDto> getData(List<Tbl09Dropdownlist> models) {

		List<DropdownlistDto> dtos = new ArrayList<>();
		for (Tbl09Dropdownlist model : models) {
			DropdownlistDto dto = new DropdownlistDto();
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
			response.setHeader("Content-Disposition", "attachment; filename=" + "Drop Down List" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}

			List<Tbl09Dropdownlist> models = (List<Tbl09Dropdownlist>) genericDao.getRecords(Tbl09Dropdownlist.class,
					GenericUtil.getConjuction(Tbl09Dropdownlist.class, searchCriterias));

			List<DropdownlistDto> dtos = getData(models);

			Field[] propertyFields = DropdownlistDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (DropdownlistDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}

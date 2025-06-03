
package com.velotech.fanselection.design.service;

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
import com.velotech.fanselection.dtos.FrameMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl14FrameMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class FrameMasterServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(FrameMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl14FrameMaster model = new Tbl14FrameMaster();
		FrameMasterDto dto = new FrameMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, FrameMasterDto.class);

		List<SearchCriteria> searchCriterias = new ArrayList<>();
		SearchCriteria frameSize = new SearchCriteria("frameSize", dto.getFrameSize());
		SearchCriteria pole = new SearchCriteria("pole", dto.getPole());
		SearchCriteria shaftDia = new SearchCriteria("shaftDia", dto.getShaftDia());
		//SearchCriteria description = new SearchCriteria("description", dto.getShaftDia());

		searchCriterias.add(frameSize);
		searchCriterias.add(pole);
		searchCriterias.add(shaftDia);

		List<Tbl14FrameMaster> models = (List<Tbl14FrameMaster>) genericDao.getRecords(Tbl14FrameMaster.class,
				GenericUtil.getConjuction(Tbl14FrameMaster.class, searchCriterias));

		if (models.size() == 0) {
			BeanUtils.copyProperties(dto, model);
			model.setCompany(velotechUtil.getCompany());
			model.setUuid(velotechUtil.getUuid());
			//model.setDescription(dto.getFrameSize()+"-"+dto.getPole());
			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} else
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.RECORD_EXISTS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		FrameMasterDto dto = new FrameMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, FrameMasterDto.class);
		Tbl14FrameMaster model = (Tbl14FrameMaster) genericDao.getRecordById(Tbl14FrameMaster.class, dto.getId());
		BeanUtils.copyProperties(dto, model);
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl14FrameMaster.class, ids);
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

		Conjunction conjunction = GenericUtil.getConjuction(Tbl14FrameMaster.class, searchCriterias);
		Pagination pagination = requestWrapper.getPagination();
		applicationResponse = genericDao.getRecords(Tbl14FrameMaster.class, conjunction, pagination);

		List<Tbl14FrameMaster> models = (List<Tbl14FrameMaster>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<FrameMasterDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	private List<FrameMasterDto> getData(List<Tbl14FrameMaster> models) {

		List<FrameMasterDto> dtos = new ArrayList<>();
		for (Tbl14FrameMaster model : models) {
			FrameMasterDto dto = new FrameMasterDto();
			BeanUtils.copyProperties(model, dto);
			dto.setDescription(model.getFrameSize()+"-"+model.getPole());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "FrameMaster" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}
			List<Tbl14FrameMaster> models = (List<Tbl14FrameMaster>) genericDao.getRecords(Tbl14FrameMaster.class,
					GenericUtil.getConjuction(Tbl14FrameMaster.class, searchCriterias));
			List<FrameMasterDto> dtos = getData(models);

			Field[] propertyFields = FrameMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (FrameMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
}

package com.velotech.fanselection.design.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.design.dao.CentrifugalSpeedDataDao;
import com.velotech.fanselection.dtos.CentrifugalSpeedDataDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.models.Tbl01CentrifugalFanSpeed;
import com.velotech.fanselection.models.Tbl01CentrifugalSpeedData;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.CentrifugalSpeedTermsUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class CentrifugalSpeedDataServiceImpl implements CentrifugalSpeedDataService{ 

	static Logger log = LogManager.getLogger(CentrifugalFanSpeedServiceImpl.class.getName());
	
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private CentrifugalSpeedDataDao dao;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private CentrifugalSpeedTermsUtil centrifugalSpeedTermsUtil;
	
	
	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		try {
			Tbl01CentrifugalFanSpeed centrifugalFanSpeed = new Tbl01CentrifugalFanSpeed();
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<CentrifugalSpeedDataDto> dtos = Arrays.asList(mapper.readValue(requestPayload, CentrifugalSpeedDataDto[].class));
			for (CentrifugalSpeedDataDto dto : dtos) {
				Tbl01CentrifugalSpeedData model = new Tbl01CentrifugalSpeedData();
				BeanUtils.copyProperties(dto, model);
				model.setCompany(velotechUtil.getCompany());
				model.setCreatedBy(velotechUtil.getUsername());
				model.setCreatedDate(new Date());
				model.setModifiedDate(new Date());
				model.setModifiedBy(velotechUtil.getUsername());
				if (dto.getFanSpeedId() != null)
					model.setTbl01CentrifugalFanSpeed(new Tbl01CentrifugalFanSpeed(dto.getFanSpeedId()));
				genericDao.saveOrUpdate(model);

			}
			centrifugalFanSpeed = (Tbl01CentrifugalFanSpeed) genericDao.getRecordById(Tbl01CentrifugalFanSpeed.class, dtos.get(0).getFanSpeedId());
			centrifugalFanSpeed = centrifugalSpeedTermsUtil.termsFromData(centrifugalFanSpeed);
			genericDao.update(centrifugalFanSpeed);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		try {
			CentrifugalSpeedDataDto dto = new CentrifugalSpeedDataDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, CentrifugalSpeedDataDto.class);
			Tbl01CentrifugalSpeedData model = (Tbl01CentrifugalSpeedData) genericDao.getRecordById(Tbl01CentrifugalSpeedData.class, dto.getFanSpeedId());
			BeanUtils.copyProperties(dto, model);
			if (dto.getFanSpeedId() != null)
				model.setTbl01CentrifugalFanSpeed(new Tbl01CentrifugalFanSpeed(dto.getFanSpeedId()));

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
		try {
			genericDao.deleteAll(Tbl01CentrifugalSpeedData.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}

			Conjunction conjunction = GenericUtil.getConjuction(Tbl01CentrifugalSpeedData.class, searchCriterias);
			//applicationResponse = genericDao.getRecords(Tbl11PerformanceData.class, conjunction, requestWrapper.getPagination());
			applicationResponse = dao.getCentrifugalSpeedData(conjunction);

			List<Tbl01CentrifugalSpeedData> models = (List<Tbl01CentrifugalSpeedData>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<CentrifugalSpeedDataDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<CentrifugalSpeedDataDto> getData(List<Tbl01CentrifugalSpeedData> models) {

		List<CentrifugalSpeedDataDto> dtos = new ArrayList<>();
		try {
			for (Tbl01CentrifugalSpeedData model : models) {
				CentrifugalSpeedDataDto dto = new CentrifugalSpeedDataDto();
				BeanUtils.copyProperties(model, dto);
				if (model.getTbl01CentrifugalFanSpeed() != null)
					dto.setFanSpeedId(model.getTbl01CentrifugalFanSpeed().getId());

				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dtos;
	}

}
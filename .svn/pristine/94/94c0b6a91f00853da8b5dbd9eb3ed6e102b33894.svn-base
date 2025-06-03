
package com.velotech.fanselection.offer.service;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.OfferFanSpecificationDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl28OfferFanSpecification;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;

@Service
@Transactional
public class OfferPumpSpecificationServiceImpl implements OfferPumpSpecificationService {

	static Logger log = LogManager.getLogger(OfferPumpSpecificationServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Override
	public ApplicationResponse getRecords(Integer offerPumpId) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl28OfferFanSpecification> models = (List<Tbl28OfferFanSpecification>) genericDao
					.getRecordsByParentId(Tbl28OfferFanSpecification.class, "tbl26OfferPump.id", offerPumpId);
			long total = models.size();

			List<OfferFanSpecificationDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<OfferFanSpecificationDto> getData(List<Tbl28OfferFanSpecification> models) {

		List<OfferFanSpecificationDto> dtos = new ArrayList<>();
		try {
			for (Tbl28OfferFanSpecification model : models) {
				OfferFanSpecificationDto dto = new OfferFanSpecificationDto();
				BeanUtils.copyProperties(model, dto);
				dto.setOfferFanId(model.getTbl26OfferFan().getId());
				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dtos;
	}

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl28OfferFanSpecification model = new Tbl28OfferFanSpecification();
			OfferFanSpecificationDto dto = new OfferFanSpecificationDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, OfferFanSpecificationDto.class);
			BeanUtils.copyProperties(dto, model);
			model.setTbl26OfferFan(new Tbl26OfferFan(dto.getOfferFanId()));
			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			OfferFanSpecificationDto dto = new OfferFanSpecificationDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, OfferFanSpecificationDto.class);
			Tbl28OfferFanSpecification model = (Tbl28OfferFanSpecification) genericDao.getRecordById(Tbl28OfferFanSpecification.class,
					dto.getId());
			BeanUtils.copyProperties(dto, model);
			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			genericDao.deleteAll(Tbl28OfferFanSpecification.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}

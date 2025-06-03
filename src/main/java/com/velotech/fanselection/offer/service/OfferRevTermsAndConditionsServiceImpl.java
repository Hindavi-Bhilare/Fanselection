
package com.velotech.fanselection.offer.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.OfferRevTermsAndConditionsDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl23OfferRevTermsAndConditions;
import com.velotech.fanselection.models.Tbl90Termsandconditions;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;

@Service
@Transactional
public class OfferRevTermsAndConditionsServiceImpl implements OfferRevTermsAndConditionsService {

	static Logger log = LogManager.getLogger(OfferRevTermsAndConditionsServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Override
	public ApplicationResponse addTermsCondition(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl23OfferRevTermsAndConditions model = new Tbl23OfferRevTermsAndConditions();
			OfferRevTermsAndConditionsDto dto = new OfferRevTermsAndConditionsDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, OfferRevTermsAndConditionsDto.class);
			BeanUtils.copyProperties(dto, model);

			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, dto.getOfferRevId());
			model.setTbl23OfferRev(tbl23OfferRev);
			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateTermsCondition(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			OfferRevTermsAndConditionsDto dto = new OfferRevTermsAndConditionsDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, OfferRevTermsAndConditionsDto.class);
			Tbl23OfferRevTermsAndConditions model = (Tbl23OfferRevTermsAndConditions) genericDao.getRecordById(Tbl23OfferRevTermsAndConditions.class,
					dto.getId());
			BeanUtils.copyProperties(dto, model);
			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.UPDATE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteTermsConditions(List<Integer> ids) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			genericDao.deleteAll(Tbl23OfferRevTermsAndConditions.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getTermsConditions(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			List<Tbl23OfferRevTermsAndConditions> models = (List<Tbl23OfferRevTermsAndConditions>) genericDao
					.getRecordsByParentId(Tbl23OfferRevTermsAndConditions.class, "tbl23OfferRev.id", offerRevId);
			long total = models.size();

			List<OfferRevTermsAndConditionsDto> dtos = getOfferRevTermsAndConditionsData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<OfferRevTermsAndConditionsDto> getOfferRevTermsAndConditionsData(List<Tbl23OfferRevTermsAndConditions> models) {

		List<OfferRevTermsAndConditionsDto> dtos = new ArrayList<>();
		try {
			for (Tbl23OfferRevTermsAndConditions model : models) {
				OfferRevTermsAndConditionsDto dto = new OfferRevTermsAndConditionsDto();
				BeanUtils.copyProperties(model, dto);
				dto.setOfferRevId(model.getTbl23OfferRev().getId());
				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse loadDefaultRecords(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			List<Tbl23OfferRevTermsAndConditions> tbl23OfferRevTermsAndConditions = (List<Tbl23OfferRevTermsAndConditions>) genericDao
					.getRecordsByParentId(Tbl23OfferRevTermsAndConditions.class, "tbl23OfferRev.id", offerRevId);
			genericDao.deleteAll(tbl23OfferRevTermsAndConditions);

			List<Tbl90Termsandconditions> tbl90Termsandconditions = (List<Tbl90Termsandconditions>) genericDao
					.getRecords(Tbl90Termsandconditions.class);
			for (Tbl90Termsandconditions tbl90Termsandcondition : tbl90Termsandconditions) {
				Tbl23OfferRevTermsAndConditions model = new Tbl23OfferRevTermsAndConditions();
				BeanUtils.copyProperties(tbl90Termsandcondition, model);
				model.setTbl23OfferRev(new Tbl23OfferRev(offerRevId));
				genericDao.save(model);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}
}

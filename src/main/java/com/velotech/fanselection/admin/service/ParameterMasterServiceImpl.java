package com.velotech.fanselection.admin.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.admin.dao.ParameterMasterDao;
import com.velotech.fanselection.dtos.ParameterMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.models.Tbl90ParameterMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;


@Service
@Transactional
public class ParameterMasterServiceImpl implements ParameterMasterService {

	static Logger log = LogManager.getLogger(ParameterMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private ParameterMasterDao dao;

	
	@Override
	public ApplicationResponse addRecord(String requestPayload) {
		ApplicationResponse applicationResponse = null;
		try {
			Tbl90ParameterMaster model = new Tbl90ParameterMaster();
			ParameterMasterDto dto = new ParameterMasterDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, ParameterMasterDto.class);

			BeanUtils.copyProperties(dto, model);
			// model.setCompany(velotechUtil.getCompany());
			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;

	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) {
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(),
						requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}

			Conjunction conjunction = GenericUtil.getConjuction(Tbl90ParameterMaster.class, searchCriterias);

			applicationResponse = genericDao.getRecords(Tbl90ParameterMaster.class, conjunction,
					requestWrapper.getPagination());
			List<Tbl90ParameterMaster> models = (List<Tbl90ParameterMaster>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<ParameterMasterDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<ParameterMasterDto> getData(List<Tbl90ParameterMaster> models) {

		List<ParameterMasterDto> dtos = new ArrayList<>();
		try {
			for (Tbl90ParameterMaster model : models) {
				ParameterMasterDto dto = new ParameterMasterDto();
				BeanUtils.copyProperties(model, dto);

				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return dtos;

	}

	@Override
	public ApplicationResponse deleteRecords(List<String> parameter) {
		ApplicationResponse applicationResponse = null;
		try {
			dao.deleteAll(parameter);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;

	}

}

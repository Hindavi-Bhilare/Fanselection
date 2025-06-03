
package com.velotech.fanselection.generic.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.FieldMetaData;
import com.velotech.fanselection.utils.JavaUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;

@Service
@Transactional
public class GenericServiceImpl implements GenericService {

	@Autowired
	private GenericDao genericDao;

	static Logger log = LogManager.getLogger(GenericServiceImpl.class.getName());

	@Override
	public ApplicationResponse addRecord(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = null;
		try {
			String model = requestWrapper.getModelName();
			String bodyPaylod = requestWrapper.getRequestPayload();

			Class<?> modelClass = Class.forName("com.velotech.applicationmodels.model." + model);
			Object modelObject = modelClass.newInstance();
			ObjectMapper mapper = new ObjectMapper();
			modelObject = mapper.readValue(bodyPaylod, modelClass);
			genericDao.save(modelObject);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = null;
		try {
			String model = requestWrapper.getModelName();
			String bodyPaylod = requestWrapper.getRequestPayload();

			ObjectMapper mapper = new ObjectMapper();
			Class<?> modelClass = Class.forName("com.velotech.applicationmodels.model." + model);
			Object object = modelClass.newInstance();
			object = mapper.readValue(bodyPaylod, modelClass);
			genericDao.update(object);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = null;
		try {
			String model = requestWrapper.getModelName();
			List<Integer> ids = requestWrapper.getIds();

			Class<?> modelClass = Class.forName("com.velotech.applicationmodels.model." + model);
			genericDao.deleteAll(modelClass, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getMetaData(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String model = requestWrapper.getModelName();

			Class<?> modelClass = Class.forName("com.velotech.applicationmodels.model." + model);
			List<Field> fields = JavaUtil.getPrivateFields(modelClass);
			List<FieldMetaData> records = new ArrayList<>();
			for (Field field : fields) {
				FieldMetaData metaData = new FieldMetaData();
				metaData.setName(field.getName());
				metaData.setType(field.getType().getSimpleName());
				records.add(metaData);
			}
			long total = applicationResponse.getTotal();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, records, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String model = requestWrapper.getModelName();
			Class<?> modelClass = Class.forName("com.velotech.applicationmodels.model." + model);
			List<?> models = new ArrayList<>();
			models = (List<?>) genericDao.getRecords(modelClass, null);
			long total = applicationResponse.getTotal();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecordsWithPagination(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String model = requestWrapper.getModelName();
			Pagination pagination = requestWrapper.getPagination();
			List<SearchCriteria> searchCriterias = requestWrapper.getSearchCriterias();

			Class<?> modelClass = Class.forName("com.velotech.applicationmodels.model." + model);
			List<?> models = new ArrayList<>();
			applicationResponse = genericDao.getRecords(modelClass, GenericUtil.getConjuction(modelClass, searchCriterias), pagination);
			models = (List<?>) applicationResponse.getData();
			long total = applicationResponse.getTotal();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getComboRecords(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String model = requestWrapper.getModelName();
			String displayField = requestWrapper.getDisplayField();
			String valueField = requestWrapper.getValueField();

			Class<?> modelClass = Class.forName("com.velotech.applicationmodels.model." + model);
			List<?> models = new ArrayList<>();

			applicationResponse = genericDao.getComboRecords(modelClass, displayField, valueField, null);
			models = (List<?>) applicationResponse.getData();
			long total = applicationResponse.getTotal();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}

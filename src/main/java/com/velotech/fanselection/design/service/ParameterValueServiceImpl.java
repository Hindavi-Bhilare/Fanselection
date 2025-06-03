
package com.velotech.fanselection.design.service;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.ParameterValueDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.models.Tbl90ParameterValue;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class ParameterValueServiceImpl implements ParameterValueService {

	static Logger log = LogManager.getLogger(ParameterValueServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private VelotechUtil velotechUtil;

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper, String uuid) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(),
						requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}
			if (uuid != null && uuid != "") {
				SearchCriteria searchCriteria1 = new SearchCriteria();
				searchCriteria1.setSearchProperty("uuid");
				searchCriteria1.setSearchValue(uuid);
				searchCriterias.add(searchCriteria1);
			}
			Conjunction conjunction = GenericUtil.getConjuction(Tbl90ParameterValue.class, searchCriterias);

			applicationResponse = genericDao.getRecords(Tbl90ParameterValue.class, conjunction,
					requestWrapper.getPagination());
			List<Tbl90ParameterValue> models = (List<Tbl90ParameterValue>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<ParameterValueDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;

	}

	private List<ParameterValueDto> getData(List<Tbl90ParameterValue> models) {

		List<ParameterValueDto> dtos = new ArrayList<>();
		try {
			for (Tbl90ParameterValue model : models) {
				ParameterValueDto dto = new ParameterValueDto();
				BeanUtils.copyProperties(model, dto);

				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return dtos;

	}

	@Override
	public ApplicationResponse addRecord(String requestPayload, String uuid) {

		ApplicationResponse applicationResponse = null;
		try {
			Tbl90ParameterValue model = new Tbl90ParameterValue();
			ParameterValueDto dto = new ParameterValueDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, ParameterValueDto.class);

			BeanUtils.copyProperties(dto, model);
			if(uuid != null){
			model.setUuid(uuid);
			}else{
				model.setUuid(velotechUtil.getUuid());
			}
			
			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;

	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload, String uuid) {

		ApplicationResponse applicationResponse = null;
		try {
			ParameterValueDto dto = new ParameterValueDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, ParameterValueDto.class);
			Tbl90ParameterValue model = (Tbl90ParameterValue) genericDao.getRecordById(Tbl90ParameterValue.class,
					dto.getId());
			BeanUtils.copyProperties(dto, model,"uuid");
			//model.setUuid(uuid);
			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.UPDATE_SUCCESS_MSG);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;

	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		try {
			genericDao.deleteAll(Tbl90ParameterValue.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public Boolean copyRecords(String oldUuid, String newUuid) {

		Boolean ans = false;
		try {
			List<Tbl90ParameterValue> parameterValuesOldList = (List<Tbl90ParameterValue>) genericDao
					.findByParam(Tbl90ParameterValue.class, "uuid", oldUuid);

			List<Tbl90ParameterValue> parameterValuesNewList = new ArrayList<>();
			for (Tbl90ParameterValue parameterValuesOld : parameterValuesOldList) {
				Tbl90ParameterValue tbl90ParameterValue = new Tbl90ParameterValue();
				BeanUtils.copyProperties(parameterValuesOld, tbl90ParameterValue, new String[] { "uuid" });
				tbl90ParameterValue.setUuid(newUuid);
				parameterValuesNewList.add(tbl90ParameterValue);
			}
			ans = genericDao.saveAll(parameterValuesNewList);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;

	}

	@Override
	public Boolean deleteRecordsUuid(List<String> uuids) {

		Boolean ans = false;
		try {
			List<Tbl90ParameterValue> parameterValuesOldList = (List<Tbl90ParameterValue>) genericDao
					.findByParam(Tbl90ParameterValue.class, "uuid", uuids);
			ans = genericDao.deleteAll(parameterValuesOldList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper,String uuid) {
		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "ParameterValue" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			List<SearchCriteria> criterias = new ArrayList<>();

			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(),
						requestWrapper.getSearchValue());
				criterias.add(criteria);
			}
			if(uuid!= null){
				SearchCriteria criteria1=new SearchCriteria();
				criteria1.setSearchProperty("uuid");
				criteria1.setSearchValue(uuid);
				criterias.add(criteria1);
			}

			List<Tbl90ParameterValue> models = (List<Tbl90ParameterValue>) genericDao.getRecords(
					Tbl90ParameterValue.class, GenericUtil.getConjuction(Tbl90ParameterValue.class, criterias));
			List<ParameterValueDto> dtos = getData(models);

			Field[] propertyFields = ParameterValueDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (ParameterValueDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

}

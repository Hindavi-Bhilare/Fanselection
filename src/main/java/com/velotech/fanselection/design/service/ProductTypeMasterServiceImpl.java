
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
import com.velotech.fanselection.dtos.ProductTypeMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl01ProductTypeMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class ProductTypeMasterServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(ProductTypeMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl01ProductTypeMaster model = new Tbl01ProductTypeMaster();
		ProductTypeMasterDto dto = new ProductTypeMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			dto = mapper.readValue(requestPayload, ProductTypeMasterDto.class);
			BeanUtils.copyProperties(dto, model);
			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		ProductTypeMasterDto dto = new ProductTypeMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			dto = mapper.readValue(requestPayload, ProductTypeMasterDto.class);
			Tbl01ProductTypeMaster model = (Tbl01ProductTypeMaster) genericDao.getRecordById(Tbl01ProductTypeMaster.class, dto.getId());
			BeanUtils.copyProperties(dto, model);
			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		try {
			genericDao.deleteAll(Tbl01ProductTypeMaster.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			SearchCriteria searchCriteria = new SearchCriteria();
			Conjunction conjunction = new Conjunction();
			if (requestWrapper.getSearchValue() != "" && requestWrapper.getSearchValue() != null) {
				searchCriteria.setSearchProperty(requestWrapper.getSearchProperty());
				searchCriteria.setSearchValue(requestWrapper.getSearchValue());
				conjunction = (Conjunction) GenericUtil.getConjuction(Tbl01ProductTypeMaster.class, searchCriteria);
			}
			Pagination pagination = requestWrapper.getPagination();
			applicationResponse = genericDao.getRecords(Tbl01ProductTypeMaster.class, conjunction, pagination);

			List<Tbl01ProductTypeMaster> models = (List<Tbl01ProductTypeMaster>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<ProductTypeMasterDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	private List<ProductTypeMasterDto> getData(List<Tbl01ProductTypeMaster> models) {

		List<ProductTypeMasterDto> dtos = new ArrayList<>();
		for (Tbl01ProductTypeMaster model : models) {
			ProductTypeMasterDto dto = new ProductTypeMasterDto();
			BeanUtils.copyProperties(model, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "ProductTypeMaster" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			SearchCriteria searchCriteria = new SearchCriteria();
			Conjunction conjunction = new Conjunction();
			if (requestWrapper.getSearchValue() != "" && requestWrapper.getSearchValue() != null) {
				searchCriteria.setSearchProperty(requestWrapper.getSearchProperty());
				searchCriteria.setSearchValue(requestWrapper.getSearchValue());
				conjunction = (Conjunction) GenericUtil.getConjuction(Tbl01ProductTypeMaster.class, searchCriteria);
			}
			List<Tbl01ProductTypeMaster> models = (List<Tbl01ProductTypeMaster>) genericDao.getRecords(Tbl01ProductTypeMaster.class, conjunction);
			List<ProductTypeMasterDto> dtos = getData(models);

			Field[] propertyFields = ProductTypeMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (ProductTypeMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}

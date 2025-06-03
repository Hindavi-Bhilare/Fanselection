
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
import com.velotech.fanselection.dtos.VariantLineDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl01VariantLine;
import com.velotech.fanselection.models.Tbl01VariantMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class VariantLineServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(VariantLineServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl01VariantLine model = new Tbl01VariantLine();
		VariantLineDto dto = new VariantLineDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, VariantLineDto.class);
		BeanUtils.copyProperties(dto, model);
		model.setCompany(velotechUtil.getCompany());
		if (dto.getVariantMasterId() != null)
			model.setTbl01VariantMaster(new Tbl01VariantMaster(dto.getVariantMasterId()));
		genericDao.save(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		VariantLineDto dto = new VariantLineDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, VariantLineDto.class);
		Tbl01VariantLine model = (Tbl01VariantLine) genericDao.getRecordById(Tbl01VariantLine.class, dto.getId());
		BeanUtils.copyProperties(dto, model);
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl01VariantLine.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<SearchCriteria> searchCriterias = new ArrayList<>();

			Conjunction conjunction = GenericUtil.getConjuction(Tbl01VariantLine.class, requestWrapper.getSearchCriterias());
			applicationResponse = genericDao.getRecords(Tbl01VariantLine.class, conjunction, requestWrapper.getPagination());

			List<Tbl01VariantLine> models = (List<Tbl01VariantLine>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<VariantLineDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<VariantLineDto> getData(List<Tbl01VariantLine> models) {

		List<VariantLineDto> dtos = new ArrayList<>();
		for (Tbl01VariantLine model : models) {
			VariantLineDto dto = new VariantLineDto();
			BeanUtils.copyProperties(model, dto);
			if (dto.getVariantMasterId() != null)
				model.setTbl01VariantMaster(new Tbl01VariantMaster(dto.getVariantMasterId()));
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "VariantLine" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			List<SearchCriteria> criterias = new ArrayList<>();
			String searchProperty = "tbl01VariantMaster.id";
			Object searchValue = requestWrapper.getParentId();
			SearchCriteria searchCriteria = new SearchCriteria(searchProperty, searchValue);
			criterias.add(searchCriteria);

			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			List<Tbl01VariantLine> models = (List<Tbl01VariantLine>) genericDao.getRecords(Tbl01VariantLine.class,
					GenericUtil.getConjuction(Tbl01VariantLine.class, criterias));
			List<VariantLineDto> dtos = getData(models);

			Field[] propertyFields = VariantLineDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (VariantLineDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
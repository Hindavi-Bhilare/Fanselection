
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
import com.velotech.fanselection.dtos.GenericBomDataDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl03GenericBom;
import com.velotech.fanselection.models.Tbl03GenericBomData;
import com.velotech.fanselection.models.Tbl03ItemMaster;
import com.velotech.fanselection.models.Tbl03MaterialMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class GenericBomDataServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(GenericBomDataServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl03GenericBomData model = new Tbl03GenericBomData();
		GenericBomDataDto dto = new GenericBomDataDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, GenericBomDataDto.class);
		BeanUtils.copyProperties(dto, model);
		model.setCompany(velotechUtil.getCompany());
		model.setTbl03GenericBom(new Tbl03GenericBom(dto.getGenericBomId()));
		if (dto.getItemMasterId() != null)
			model.setTbl03ItemMaster(new Tbl03ItemMaster(dto.getItemMasterId()));
		if (dto.getMaterialMasterId() != null)
			model.setTbl03MaterialMaster(new Tbl03MaterialMaster(dto.getMaterialMasterId()));
		genericDao.save(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		GenericBomDataDto dto = new GenericBomDataDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, GenericBomDataDto.class);
		Tbl03GenericBomData model = (Tbl03GenericBomData) genericDao.getRecordById(Tbl03GenericBomData.class, dto.getGenericBomDataId());
		BeanUtils.copyProperties(dto, model);
		if (dto.getItemMasterId() != null)
			model.setTbl03ItemMaster(new Tbl03ItemMaster(dto.getItemMasterId()));
		if (dto.getMaterialMasterId() != null)
			model.setTbl03MaterialMaster(new Tbl03MaterialMaster(dto.getMaterialMasterId()));


		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl03GenericBomData.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Conjunction conjunction = GenericUtil.getConjuction(Tbl03GenericBomData.class, requestWrapper.getSearchCriterias());
			Pagination pagination = requestWrapper.getPagination();
			applicationResponse = genericDao.getRecords(Tbl03GenericBomData.class, conjunction, pagination);

			List<Tbl03GenericBomData> models = (List<Tbl03GenericBomData>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<GenericBomDataDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	private List<GenericBomDataDto> getData(List<Tbl03GenericBomData> models) {

		List<GenericBomDataDto> dtos = new ArrayList<>();
		for (Tbl03GenericBomData model : models) {
			GenericBomDataDto dto = new GenericBomDataDto();
			BeanUtils.copyProperties(model, dto);
			if (model.getTbl03GenericBom() != null)
				dto.setGenericBomId(model.getTbl03GenericBom().getGenericBomId());
			if (model.getTbl03ItemMaster() != null) {
				dto.setItemMasterId(model.getTbl03ItemMaster().getItemId());
				dto.setItemMaster(model.getTbl03ItemMaster().getDescription());
			}
			if (model.getTbl03MaterialMaster() != null) {
				dto.setMaterialMasterId(model.getTbl03MaterialMaster().getMaterialId());
				dto.setMaterialMaster(model.getTbl03MaterialMaster().getSpecification());
			}

			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "GenericBomData" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> criterias = new ArrayList<>();
			String searchProperty = "tbl03GenericBom.genericBomId";
			Object searchValue = requestWrapper.getParentId();
			SearchCriteria searchCriteria = new SearchCriteria(searchProperty, searchValue);
			criterias.add(searchCriteria);

			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			List<Tbl03GenericBomData> models = (List<Tbl03GenericBomData>) genericDao.getRecords(Tbl03GenericBomData.class,
					GenericUtil.getConjuction(Tbl03GenericBomData.class, criterias));
			List<GenericBomDataDto> dtos = getData(models);

			Field[] propertyFields = GenericBomDataDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (GenericBomDataDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
}


package com.velotech.fanselection.design.service;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.MaterialMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl03MaterialMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class MaterialMasterServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(MaterialMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl03MaterialMaster model = new Tbl03MaterialMaster();
		MaterialMasterDto dto = new MaterialMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, MaterialMasterDto.class);

		List<SearchCriteria> searchCriterias = new ArrayList<>();
		SearchCriteria specification = new SearchCriteria("specification", dto.getSpecification());
		SearchCriteria nameIs = new SearchCriteria("nameIs", dto.getNameIs());
		searchCriterias.add(specification);
		searchCriterias.add(nameIs);

		List<Tbl03MaterialMaster> models = (List<Tbl03MaterialMaster>) genericDao.getRecords(Tbl03MaterialMaster.class,
				GenericUtil.getConjuction(Tbl03MaterialMaster.class, searchCriterias));

		if (models.size() < 1) {
			BeanUtils.copyProperties(dto, model);
			model.setCompany(velotechUtil.getCompany());
			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} else
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.Material_Master_SAME_RECORD);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		MaterialMasterDto dto = new MaterialMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, MaterialMasterDto.class);

		List<SearchCriteria> searchCriterias = new ArrayList<>();
		SearchCriteria specification = new SearchCriteria("specification", dto.getSpecification());
		SearchCriteria nameIs = new SearchCriteria("nameIs", dto.getNameIs());
		searchCriterias.add(specification);
		searchCriterias.add(nameIs);

		List<Tbl03MaterialMaster> models = (List<Tbl03MaterialMaster>) genericDao.getRecords(Tbl03MaterialMaster.class,
				GenericUtil.getConjuction(Tbl03MaterialMaster.class, searchCriterias));
		if (models.size() < 1) {
			Tbl03MaterialMaster model = (Tbl03MaterialMaster) genericDao.getRecordById(Tbl03MaterialMaster.class, dto.getMaterialId());
			BeanUtils.copyProperties(dto, model);
			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} else
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.Material_Master_SAME_RECORD);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl03MaterialMaster.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<SearchCriteria> searchCriterias = new ArrayList<>();
		if (requestWrapper.getSearchValue() != "") {
			SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
			searchCriterias.add(searchCriteria);
		}

		Conjunction conjunction = GenericUtil.getConjuction(Tbl03MaterialMaster.class, searchCriterias);
		applicationResponse = genericDao.getRecords(Tbl03MaterialMaster.class, conjunction, requestWrapper.getPagination());

		List<Tbl03MaterialMaster> models = (List<Tbl03MaterialMaster>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<MaterialMasterDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	private List<MaterialMasterDto> getData(List<Tbl03MaterialMaster> models) {

		List<MaterialMasterDto> dtos = new ArrayList<>();
		for (Tbl03MaterialMaster model : models) {
			MaterialMasterDto dto = new MaterialMasterDto();
			BeanUtils.copyProperties(model, dto);
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "MaterialMaster" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}

			Conjunction conjunction = GenericUtil.getConjuction(Tbl03MaterialMaster.class, searchCriterias);
			List<Tbl03MaterialMaster> models = (List<Tbl03MaterialMaster>) genericDao.getRecords(Tbl03MaterialMaster.class, conjunction);
			List<MaterialMasterDto> dtos = getData(models);

			Field[] propertyFields = MaterialMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (MaterialMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}

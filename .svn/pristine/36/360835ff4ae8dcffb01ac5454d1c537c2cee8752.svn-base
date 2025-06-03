
package com.velotech.fanselection.design.service;

import java.io.IOException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.velotech.fanselection.design.dao.FanTypeDao;
import com.velotech.fanselection.dtos.FanTypeDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl03MocMaster;
//import com.velotech.fanselection.models.Tbl05ShaftGroupMaster;
//import com.velotech.fanselection.models.Tbl90ApplicationMaster;
import com.velotech.fanselection.models.Tbl90DocumentMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.ComboBox;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class FanTypeServiceImpl implements FanTypeService, MasterService {

	static Logger log = LogManager.getLogger(FanTypeServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private ParameterValueService parameterValue;

	@Autowired
	private FanTypeDao fanTypeDao;

	@Override
	public ApplicationResponse addRecord(String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		FanTypeDto dto = new FanTypeDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, FanTypeDto.class);
		Tbl01Fantype model = new Tbl01Fantype();
		BeanUtils.copyProperties(dto, model);

		model.setUuid(velotechUtil.getUuid());
		model.setCompany(velotechUtil.getCompany());

		if (dto.getImageId() != null) {
			model.setDocumentMasterImage(new Tbl90DocumentMaster(dto.getImageId()));
		}

		genericDao.save(model);

		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;

	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		FanTypeDto dto = new FanTypeDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, FanTypeDto.class);
		Tbl01Fantype model = (Tbl01Fantype) genericDao.getRecordById(Tbl01Fantype.class, dto.getId());
		BeanUtils.copyProperties(dto, model);
		if (dto.getImageId() != null) {
			model.setDocumentMasterImage(new Tbl90DocumentMaster(dto.getImageId()));
		} else {
			model.setDocumentMasterImage(null);
		}


		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {
		ApplicationResponse applicationResponse = null;

		List<Tbl01Fantype> list = (List<Tbl01Fantype>) genericDao.getRecordByIds(Tbl01Fantype.class, ids);

		List<String> uuids = list.stream().map(Tbl01Fantype::getUuid).collect(Collectors.toList());

		for (Tbl01Fantype master : list) {

			List<Tbl03MocMaster> tbl03MocMaster = (List<Tbl03MocMaster>) genericDao
					.getRecordsByParentId(Tbl03MocMaster.class, "tbl01fantype.id", master.getId());

			if (tbl03MocMaster.size() == 0) {
				Boolean delete = genericDao.deleteAll(list);
				if (delete)
					parameterValue.deleteRecordsUuid(uuids);
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
						ApplicationConstants.DELETE_SUCCESS_MSG);
			} else {
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(false,
						ApplicationConstants.DELETE_FAIL_MSG);
			}
		}

		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<SearchCriteria> searchCriterias = new ArrayList<>();
		if (requestWrapper.getSearchValue() != null) {
			SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(),
					requestWrapper.getSearchValue());
			searchCriterias.add(searchCriteria);
		}
		if (requestWrapper.getParentId() != null) {
			SearchCriteria s1 = new SearchCriteria();
			s1.setSearchProperty("id");
			s1.setSearchValue(requestWrapper.getParentId());
			searchCriterias.add(s1);
		}

		Conjunction conjunction = GenericUtil.getConjuction(Tbl01Fantype.class, searchCriterias);
		applicationResponse = genericDao.getRecords(Tbl01Fantype.class, conjunction, requestWrapper.getPagination());

		List<Tbl01Fantype> models = (List<Tbl01Fantype>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<FanTypeDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
				ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	private List<FanTypeDto> getData(List<Tbl01Fantype> models) {

		List<FanTypeDto> dtos = new ArrayList<>();
		for (Tbl01Fantype model : models) {
			FanTypeDto dto = new FanTypeDto();
			BeanUtils.copyProperties(model, dto);

			if (model.getDocumentMaster() != null) {
				dto.setDocumentId(model.getDocumentMaster().getId());
				dto.setFileName(model.getDocumentMaster().getFileName());
			}

			if (model.getDocumentMasterImage() != null) {
				dto.setImageId(model.getDocumentMasterImage().getId());
			}

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "FanType" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> criterias = new ArrayList<>();

			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(),
						requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			List<Tbl01Fantype> models = (List<Tbl01Fantype>) genericDao.getRecords(Tbl01Fantype.class,
					GenericUtil.getConjuction(Tbl01Fantype.class, criterias));
			List<FanTypeDto> dtos = getData(models);

			Field[] propertyFields = FanTypeDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (FanTypeDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}


	@Override
	public ApplicationResponse getFanTypeDetails(RequestWrapper requestWrapper) {
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			List<Tbl01Fantype> series = fanTypeDao.getSeries(requestWrapper);
			List<FanTypeDto> dtos = new ArrayList<>();
			for (Tbl01Fantype s1 : series) {
				FanTypeDto dto = new FanTypeDto();
				dto.setSeries(s1.getSeries());
				dto.setId(s1.getId());
				dto.setUuid(s1.getUuid());
				dtos.add(dto);
			}

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse upload(String requestPayload) {
		ApplicationResponse applicationResponse = null;
		try {
			FanTypeDto dto = new FanTypeDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, FanTypeDto.class);
			Tbl01Fantype model = (Tbl01Fantype) genericDao.getRecordById(Tbl01Fantype.class, dto.getId());
			if (dto.getDocumentId() != null)
				model.setDocumentMaster(new Tbl90DocumentMaster(dto.getDocumentId()));
			else
				model.setDocumentMaster(null);

			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}

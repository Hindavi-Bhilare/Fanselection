
package com.velotech.fanselection.design.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.MocMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl03MocMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class MocMasterServiceImpl implements MasterService,MocMasterService  {

	static Logger log = LogManager.getLogger(MocMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl03MocMaster model = new Tbl03MocMaster();
		MocMasterDto dto = new MocMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, MocMasterDto.class);
		BeanUtils.copyProperties(dto, model);
		model.setTbl01fantype(new Tbl01Fantype(dto.getFantypeId()));
		model.setCompany(velotechUtil.getCompany());
		genericDao.save(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;

	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		MocMasterDto dto = new MocMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, MocMasterDto.class);
		Tbl03MocMaster model = (Tbl03MocMaster) genericDao.getRecordById(Tbl03MocMaster.class, dto.getMocMasterId());
		BeanUtils.copyProperties(dto, model);
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl03MocMaster.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Conjunction conjunction = GenericUtil.getConjuction(Tbl03MocMaster.class, requestWrapper.getSearchCriterias());
			Pagination pagination = requestWrapper.getPagination();
			applicationResponse = genericDao.getRecords(Tbl03MocMaster.class, conjunction, pagination);

			List<Tbl03MocMaster> models = (List<Tbl03MocMaster>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<MocMasterDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	private List<MocMasterDto> getData(List<Tbl03MocMaster> models) {

		List<MocMasterDto> dtos = new ArrayList<>();
		for (Tbl03MocMaster model : models) {
			MocMasterDto dto = new MocMasterDto();
			BeanUtils.copyProperties(model, dto);
			if (model.getTbl01fantype() != null)
				dto.setFantypeId(model.getTbl01fantype().getId());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "MocMaster" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> criterias = new ArrayList<>();
			String searchProperty = "tbl01Fantype.id";
			Object searchValue = requestWrapper.getParentId();
			SearchCriteria searchCriteria = new SearchCriteria(searchProperty, searchValue);
			criterias.add(searchCriteria);

			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			List<Tbl03MocMaster> models = (List<Tbl03MocMaster>) genericDao.getRecords(Tbl03MocMaster.class,
					GenericUtil.getConjuction(Tbl03MocMaster.class, criterias));
			List<MocMasterDto> dtos = getData(models);

			Field[] propertyFields = MocMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (MocMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public ApplicationResponse addRecords(String requestPayload) {

		ApplicationResponse applicationResponse = null;
		try {
			Tbl03MocMaster moc = new Tbl03MocMaster();
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<MocMasterDto> dtos = Arrays.asList(mapper.readValue(requestPayload, MocMasterDto[].class));
			for (MocMasterDto dto : dtos) {
				Tbl03MocMaster model = new Tbl03MocMaster();
				BeanUtils.copyProperties(dto, model);
				model.setCompany(velotechUtil.getCompany());
				model.setCreatedBy(velotechUtil.getUsername());
				model.setCreatedDate(new Date());
				model.setModifiedDate(new Date());
				model.setModifiedBy(velotechUtil.getUsername());
				model.setTbl01fantype(new Tbl01Fantype(dto.getFantypeId()));
				
				genericDao.saveOrUpdate(model);

			}
//			moc = (Tbl10PerformanceVane) genericDao.getRecordById(Tbl10PerformanceVane.class, dtos.get(0).getPerformanceDiaId());
//			moc = performanceDiaTermUtil.termsFromData(performanceDia);
//			genericDao.update(performanceDia);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}
}

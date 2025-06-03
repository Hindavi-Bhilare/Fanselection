
package com.velotech.fanselection.admin.service;

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
import com.velotech.fanselection.dtos.SegmentMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl80SegmentMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class SegmentMasterServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(SegmentMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl80SegmentMaster model = new Tbl80SegmentMaster();
		SegmentMasterDto dto = new SegmentMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, SegmentMasterDto.class);

		List<Tbl80SegmentMaster> tbl80SegmentMaster = (List<Tbl80SegmentMaster>) genericDao.findByParam(Tbl80SegmentMaster.class, "segment",
				dto.getSegment());

		if (tbl80SegmentMaster.size() < 1) {
			BeanUtils.copyProperties(dto, model);
			model.setTbl52Usermaster(new Tbl52Usermaster(dto.getLoginId()));
			model.setCompany(velotechUtil.getCompany());
			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} else {
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.RECORD_EXISTS_MSG);
		}

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		SegmentMasterDto dto = new SegmentMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, SegmentMasterDto.class);
		Tbl80SegmentMaster model = (Tbl80SegmentMaster) genericDao.getRecordById(Tbl80SegmentMaster.class, dto.getId());
		BeanUtils.copyProperties(dto, model);
		model.setTbl52Usermaster(new Tbl52Usermaster(dto.getLoginId()));
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl80SegmentMaster.class, ids);
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

		Conjunction conjunction = GenericUtil.getConjuction(Tbl80SegmentMaster.class, searchCriterias);
		applicationResponse = genericDao.getRecords(Tbl80SegmentMaster.class, conjunction, requestWrapper.getPagination());

		List<Tbl80SegmentMaster> models = (List<Tbl80SegmentMaster>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<SegmentMasterDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	private List<SegmentMasterDto> getData(List<Tbl80SegmentMaster> models) {

		List<SegmentMasterDto> dtos = new ArrayList<>();
		for (Tbl80SegmentMaster model : models) {
			SegmentMasterDto dto = new SegmentMasterDto();
			BeanUtils.copyProperties(model, dto);

			if (model.getTbl52Usermaster() != null) {
				dto.setLoginId(model.getTbl52Usermaster().getLoginId());
				dto.setUserName(model.getTbl52Usermaster().getUserName());
			}

			dtos.add(dto);
		}

		return dtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "SegmentMaster" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}

			Conjunction conjunction = GenericUtil.getConjuction(Tbl80SegmentMaster.class, searchCriterias);
			List<Tbl80SegmentMaster> models = (List<Tbl80SegmentMaster>) genericDao.getRecords(Tbl80SegmentMaster.class, conjunction);
			List<SegmentMasterDto> dtos = getData(models);

			Field[] propertyFields = SegmentMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (SegmentMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}

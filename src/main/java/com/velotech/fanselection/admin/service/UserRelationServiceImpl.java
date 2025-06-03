
package com.velotech.fanselection.admin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.admin.dao.UserRelationDao;
import com.velotech.fanselection.dtos.UserRelationDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl55Userrelation;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class UserRelationServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(UserRelationServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private UserRelationDao dao;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl55Userrelation model = new Tbl55Userrelation();
		UserRelationDto dto = new UserRelationDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, UserRelationDto.class);
		BeanUtils.copyProperties(dto, model);
		model.setCompany(velotechUtil.getCompany());

		if (dto.getLoginId() != null)
			model.setTbl52UsermasterByLoginId(new Tbl52Usermaster(dto.getLoginId()));

		if (dto.getReportingToId() != null)
			model.setTbl52UsermasterByReportingTo(new Tbl52Usermaster(dto.getReportingToId()));

		genericDao.save(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		UserRelationDto dto = new UserRelationDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, UserRelationDto.class);
		Tbl55Userrelation model = (Tbl55Userrelation) genericDao.getRecordById(Tbl55Userrelation.class, dto.getId());
		BeanUtils.copyProperties(dto, model);

		if (dto.getReportingToId() != null)
			model.setTbl52UsermasterByLoginId(new Tbl52Usermaster(dto.getReportingToId()));

		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl55Userrelation.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String searchProperty = requestWrapper.getSearchProperty();
			String searchValue = (String) requestWrapper.getSearchValue();
			Pagination pagination = requestWrapper.getPagination();
			applicationResponse = dao.getRecords(searchProperty, searchValue, pagination);

			List<Tbl55Userrelation> models = (List<Tbl55Userrelation>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<UserRelationDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	private List<UserRelationDto> getData(List<Tbl55Userrelation> models) {

		List<UserRelationDto> dtos = new ArrayList<>();
		for (Tbl55Userrelation model : models) {
			UserRelationDto dto = new UserRelationDto();
			BeanUtils.copyProperties(model, dto);

			if (model.getTbl52UsermasterByLoginId() != null)
				dto.setLoginId(model.getTbl52UsermasterByLoginId().getLoginId());

			if (model.getTbl52UsermasterByReportingTo() != null) {
				dto.setReportingToId(model.getTbl52UsermasterByLoginId().getLoginId());
				dto.setReportingTo((model.getTbl52UsermasterByReportingTo().getUserName()));
			}

			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}

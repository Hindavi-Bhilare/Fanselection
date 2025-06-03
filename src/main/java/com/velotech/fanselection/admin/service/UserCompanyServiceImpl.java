
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
import com.velotech.fanselection.admin.dao.UserCompanyDao;
import com.velotech.fanselection.dtos.UserCompanyDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl58UserCompany;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class UserCompanyServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(UserCompanyServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private UserCompanyDao dao;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl58UserCompany model = new Tbl58UserCompany();
		UserCompanyDto dto = new UserCompanyDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, UserCompanyDto.class);
		BeanUtils.copyProperties(dto, model);
		model.setCompany(velotechUtil.getCompany());

		if (dto.getUsermasterloginId() != null)
			model.setTbl52Usermaster(new Tbl52Usermaster(dto.getUsermasterloginId()));

		if (dto.getCompanyMasterId() != null)
			model.setTbl28CompanyMaster(new Tbl28CompanyMaster(dto.getCompanyMasterId()));

		genericDao.save(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		UserCompanyDto dto = new UserCompanyDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, UserCompanyDto.class);
		Tbl58UserCompany model = (Tbl58UserCompany) genericDao.getRecordById(Tbl58UserCompany.class, dto.getId());
		BeanUtils.copyProperties(dto, model);

		if (dto.getCompanyMasterId() != null)
			model.setTbl28CompanyMaster(new Tbl28CompanyMaster(dto.getCompanyMasterId()));

		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl58UserCompany.class, ids);
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

			List<Tbl58UserCompany> models = (List<Tbl58UserCompany>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<UserCompanyDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	private List<UserCompanyDto> getData(List<Tbl58UserCompany> models) {

		List<UserCompanyDto> dtos = new ArrayList<>();
		for (Tbl58UserCompany model : models) {
			UserCompanyDto dto = new UserCompanyDto();
			BeanUtils.copyProperties(model, dto);

			if (model.getTbl52Usermaster() != null)
				dto.setUsermasterloginId(model.getTbl52Usermaster().getLoginId());

			if (model.getTbl28CompanyMaster() != null) {
				dto.setCompanyMasterId(model.getTbl28CompanyMaster().getId());
				dto.setCompanyName(model.getTbl28CompanyMaster().getCompanyName());
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

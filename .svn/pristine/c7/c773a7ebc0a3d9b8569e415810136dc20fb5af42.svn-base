
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
import com.velotech.fanselection.admin.dao.UserRoleDao;
import com.velotech.fanselection.dtos.UserroleDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl53Userrole;
import com.velotech.fanselection.models.Tbl54Rolemaster;
import com.velotech.fanselection.models.Tbl56Department;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class UserroleServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(UserroleServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private UserRoleDao dao;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl53Userrole model = new Tbl53Userrole();
		UserroleDto dto = new UserroleDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, UserroleDto.class);
		BeanUtils.copyProperties(dto, model);
		model.setCompany(velotechUtil.getCompany());

		if (dto.getUserMasterloginId() != null)
			model.setTbl52Usermaster(new Tbl52Usermaster(dto.getUserMasterloginId()));

		if (dto.getRoleMasterid() != null)
			model.setTbl54Rolemaster(new Tbl54Rolemaster(dto.getRoleMasterid()));

		if (dto.getDepartmentid() != null)
			model.setTbl56Department(new Tbl56Department(dto.getDepartmentid()));

		genericDao.save(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		UserroleDto dto = new UserroleDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, UserroleDto.class);
		Tbl53Userrole model = (Tbl53Userrole) genericDao.getRecordById(Tbl53Userrole.class, dto.getId());
		BeanUtils.copyProperties(dto, model);

		if (dto.getRoleMasterid() != null)
			model.setTbl54Rolemaster(new Tbl54Rolemaster(dto.getRoleMasterid()));

		if (dto.getDepartmentid() != null)
			model.setTbl56Department(new Tbl56Department(dto.getDepartmentid()));

		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl53Userrole.class, ids);
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

			List<Tbl53Userrole> models = (List<Tbl53Userrole>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<UserroleDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	private List<UserroleDto> getData(List<Tbl53Userrole> models) {

		List<UserroleDto> dtos = new ArrayList<>();
		for (Tbl53Userrole model : models) {
			UserroleDto dto = new UserroleDto();
			BeanUtils.copyProperties(model, dto);

			if (model.getTbl52Usermaster() != null) {
				dto.setUserMasterloginId(model.getTbl52Usermaster().getLoginId());
			}
			if (model.getTbl54Rolemaster() != null) {
				dto.setRoleMasterid(model.getTbl54Rolemaster().getId());
				dto.setRoleMasterrole(model.getTbl54Rolemaster().getRole());
			}
			if (model.getTbl56Department() != null) {
				dto.setDepartmentid(model.getTbl56Department().getId());
				dto.setDepartment(model.getTbl56Department().getDepartment());
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

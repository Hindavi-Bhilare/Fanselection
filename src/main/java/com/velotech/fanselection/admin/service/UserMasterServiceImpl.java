
package com.velotech.fanselection.admin.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.admin.dao.UserMasterDao;
import com.velotech.fanselection.dtos.UserMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.models.Tbl50Usertype;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl59Organisation;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.ResourceUtil;
import com.velotech.fanselection.utils.StringEncryptor;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class UserMasterServiceImpl implements UserMasterService {

	static Logger log = LogManager.getLogger(UserMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private UserMasterDao dao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl52Usermaster model = new Tbl52Usermaster();
		UserMasterDto dto = new UserMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, UserMasterDto.class);

		List<Tbl52Usermaster> models = (List<Tbl52Usermaster>) genericDao.findByParam(Tbl52Usermaster.class, "loginId", dto.getLoginId());

		if (models.size() < 1) {
			BeanUtils.copyProperties(dto, model);
			StringEncryptor stringEncrypter = new StringEncryptor(new ResourceUtil().getPropertyValues("encryptionScheme"),
					new ResourceUtil().getPropertyValues("encryptionKey"));
			model.setPassword(stringEncrypter.encrypt(model.getPassword()));
			model.setCompany(velotechUtil.getCompany());

			if (dto.getUsertypeid() != null)
				model.setTbl50Usertype(new Tbl50Usertype(dto.getUsertypeid()));
			if (dto.getOrganizationid() != null)
				model.setTbl59Organisation(new Tbl59Organisation(dto.getOrganizationid()));

			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} else
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.RECORD_EXISTS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		UserMasterDto dto = new UserMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, UserMasterDto.class);

		List<Tbl52Usermaster> models = (List<Tbl52Usermaster>) genericDao.findByParam(Tbl52Usermaster.class, "loginId", dto.getLoginId());
		Tbl52Usermaster model = models.get(0);

		BeanUtils.copyProperties(dto, model);
		StringEncryptor stringEncrypter = new StringEncryptor(new ResourceUtil().getPropertyValues("encryptionScheme"),
				new ResourceUtil().getPropertyValues("encryptionKey"));
		model.setPassword(stringEncrypter.encrypt(model.getPassword()));

		if (dto.getUsertypeid() != null)
			model.setTbl50Usertype(new Tbl50Usertype(dto.getUsertypeid()));

		if (dto.getOrganizationid() != null)
			model.setTbl59Organisation(new Tbl59Organisation(dto.getOrganizationid()));
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<String> ids) {

		ApplicationResponse applicationResponse = null;

		/*
		 * for (String loginId : ids) { Tbl52Usermaster tbl52Usermaster = new
		 * Tbl52Usermaster(loginId); genericDao.delete(tbl52Usermaster); }
		 */

		List<Tbl52Usermaster> models = (List<Tbl52Usermaster>) genericDao.findByParam(Tbl52Usermaster.class, "loginId", ids);
		genericDao.deleteAll(models);

		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			String searchProperty = null;
			String searchValue = null;
			if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
				searchProperty = requestWrapper.getSearchProperty();
				searchValue = (String) requestWrapper.getSearchValue();
			}

			Pagination pagination = requestWrapper.getPagination();
			applicationResponse = dao.getRecords(searchProperty, searchValue, pagination);

			List<Tbl52Usermaster> models = (List<Tbl52Usermaster>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<UserMasterDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	private List<UserMasterDto> getData(List<Tbl52Usermaster> models) {

		List<UserMasterDto> dtos = new ArrayList<>();
		for (Tbl52Usermaster model : models) {
			UserMasterDto dto = new UserMasterDto();
			BeanUtils.copyProperties(model, dto);

			if (model.getTbl50Usertype() != null) {
				dto.setUsertypeid(model.getTbl50Usertype().getId());
				dto.setUserType(model.getTbl50Usertype().getUserType());
			}

			if (model.getTbl59Organisation() != null) {
				dto.setOrganizationid(model.getTbl59Organisation().getId());
				dto.setOrganisationDetails(model.getTbl59Organisation().getOrganisationDetails());
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
			response.setHeader("Content-Disposition", "attachment; filename=" + "User Master" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			String searchProperty = null;
			String searchValue = null;
			if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
				searchProperty = requestWrapper.getSearchProperty();
				searchValue = (String) requestWrapper.getSearchValue();
			}

			List<Tbl52Usermaster> models = (List<Tbl52Usermaster>) dao.getRecords(searchProperty, searchValue, null).getData();
			List<UserMasterDto> dtos = getData(models);

			Field[] propertyFields = UserMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (UserMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

}

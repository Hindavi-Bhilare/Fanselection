
package com.velotech.fanselection.admin.service;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.UsertypeSelectiontypeMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl50Usertype;
import com.velotech.fanselection.models.Tbl51UsertypeSelectiontypeMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class UsertypeSelectiontypeMasterServiceImpl implements MasterService

{

	static Logger log = LogManager.getLogger(UsertypeSelectiontypeMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl51UsertypeSelectiontypeMaster model = new Tbl51UsertypeSelectiontypeMaster();
		UsertypeSelectiontypeMasterDto dto = new UsertypeSelectiontypeMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, UsertypeSelectiontypeMasterDto.class);
		BeanUtils.copyProperties(dto, model);

		if (dto.getUserTypeId() != null)
			model.setTbl50Usertype(new Tbl50Usertype(dto.getUserTypeId()));
		/*if (dto.getSelectiontypeMasterId() != null)
			model.setTbl00SelectiontypeMaster(new Tbl00SelectiontypeMaster(dto.getSelectiontypeMasterId()));*/
		genericDao.save(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		UsertypeSelectiontypeMasterDto dto = new UsertypeSelectiontypeMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, UsertypeSelectiontypeMasterDto.class);
		Tbl51UsertypeSelectiontypeMaster model = (Tbl51UsertypeSelectiontypeMaster) genericDao.getRecordById(Tbl51UsertypeSelectiontypeMaster.class,
				dto.getId());
		BeanUtils.copyProperties(dto, model);
		/*if (dto.getSelectiontypeMasterId() != null)
			model.setTbl00SelectiontypeMaster(new Tbl00SelectiontypeMaster(dto.getSelectiontypeMasterId()));*/
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl51UsertypeSelectiontypeMaster.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}
			Conjunction conjunction = GenericUtil.getConjuction(Tbl51UsertypeSelectiontypeMaster.class, searchCriterias);
			Pagination pagination = requestWrapper.getPagination();
			applicationResponse = genericDao.getRecords(Tbl51UsertypeSelectiontypeMaster.class, conjunction, pagination);

			List<Tbl51UsertypeSelectiontypeMaster> models = (List<Tbl51UsertypeSelectiontypeMaster>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<UsertypeSelectiontypeMasterDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	private List<UsertypeSelectiontypeMasterDto> getData(List<Tbl51UsertypeSelectiontypeMaster> models) {

		List<UsertypeSelectiontypeMasterDto> dtos = new ArrayList<>();
		for (Tbl51UsertypeSelectiontypeMaster model : models) {
			UsertypeSelectiontypeMasterDto dto = new UsertypeSelectiontypeMasterDto();
			BeanUtils.copyProperties(model, dto);

			/*if (model.getTbl00SelectiontypeMaster() != null) {
				dto.setSelectiontypeMasterId(model.getTbl00SelectiontypeMaster().getId());
				dto.setGroup(model.getTbl00SelectiontypeMaster().getGroup());
			}*/
			if (model.getTbl50Usertype() != null) {
				dto.setUserTypeId(model.getTbl50Usertype().getId());
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

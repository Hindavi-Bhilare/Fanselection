
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
import com.velotech.fanselection.admin.dao.OrganizationRelationDao;
import com.velotech.fanselection.dtos.OrganisationRelationDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.models.Tbl59Organisation;
import com.velotech.fanselection.models.Tbl59OrganisationRelation;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.ComboBox;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class OrganizationRelationServiceImpl implements OrganizationRelationService {

	static Logger log = LogManager.getLogger(OrganizationRelationServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private OrganizationRelationDao dao;

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl59OrganisationRelation model = new Tbl59OrganisationRelation();
		OrganisationRelationDto dto = new OrganisationRelationDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, OrganisationRelationDto.class);

		List<SearchCriteria> searchCriterias = new ArrayList<>();

		SearchCriteria organisationId = new SearchCriteria("tbl59OrganisationByOrganisationId.id", dto.getOrganizationId());
		SearchCriteria parentOrganisationId = new SearchCriteria("tbl59OrganisationByParentOrganisationId.id", dto.getParentOrganizationId());
		searchCriterias.add(organisationId);
		searchCriterias.add(parentOrganisationId);

		List<Tbl59OrganisationRelation> models = (List<Tbl59OrganisationRelation>) genericDao.getRecords(Tbl59OrganisationRelation.class,
				GenericUtil.getConjuction(Tbl59OrganisationRelation.class, searchCriterias));

		if (models.size() < 1) {
			BeanUtils.copyProperties(dto, model);
			model.setCompany(velotechUtil.getCompany());

			if (dto.getParentOrganizationId() != null)
				model.setTbl59OrganisationByParentOrganisationId(new Tbl59Organisation(dto.getParentOrganizationId()));

			if (dto.getOrganizationId() != null)
				model.setTbl59OrganisationByOrganisationId(new Tbl59Organisation(dto.getOrganizationId()));
			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} else
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.RECORD_EXISTS_MSG);

		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		OrganisationRelationDto dto = new OrganisationRelationDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, OrganisationRelationDto.class);

		Tbl59OrganisationRelation model = (Tbl59OrganisationRelation) genericDao.getRecordById(Tbl59OrganisationRelation.class, dto.getId());
		BeanUtils.copyProperties(dto, model);

		if (dto.getParentOrganizationId() != null)
			model.setTbl59OrganisationByParentOrganisationId(new Tbl59Organisation(dto.getParentOrganizationId()));

		if (dto.getOrganizationId() != null)
			model.setTbl59OrganisationByOrganisationId(new Tbl59Organisation(dto.getOrganizationId()));

		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl59OrganisationRelation.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getRecords(Integer parentId, String organisationCode, RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		applicationResponse = dao.getTbl59OrganisationRelationData(parentId, organisationCode, requestWrapper.getPagination());
		List<Tbl59OrganisationRelation> models = (List<Tbl59OrganisationRelation>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<OrganisationRelationDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	private List<OrganisationRelationDto> getData(List<Tbl59OrganisationRelation> models) {

		List<OrganisationRelationDto> dtos = new ArrayList<>();
		for (Tbl59OrganisationRelation model : models) {
			OrganisationRelationDto dto = new OrganisationRelationDto();
			BeanUtils.copyProperties(model, dto);

			if (model.getTbl59OrganisationByParentOrganisationId() != null) {
				dto.setParentOrganizationId(model.getTbl59OrganisationByParentOrganisationId().getId());
				dto.setParentOrganizationCode(model.getTbl59OrganisationByParentOrganisationId().getOrganisationCode());
			}

			if (model.getTbl59OrganisationByOrganisationId() != null) {
				dto.setOrganizationId(model.getTbl59OrganisationByOrganisationId().getId());
				dto.setOrganizationCode(model.getTbl59OrganisationByOrganisationId().getOrganisationCode());
			}
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "Client Master" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> criterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			@SuppressWarnings("unchecked")
			List<Tbl59OrganisationRelation> models = (List<Tbl59OrganisationRelation>) genericDao.getRecords(Tbl59OrganisationRelation.class,
					GenericUtil.getConjuction(Tbl59OrganisationRelation.class, criterias));
			List<OrganisationRelationDto> dtos = getData(models);

			Field[] propertyFields = OrganisationRelationDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (OrganisationRelationDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@Override
	public ApplicationResponse getOrganizationtRelationRecord(Integer organizationId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();

		List<Tbl59Organisation> tbl59OrganisationList = dao.getLoginIdForRelation(organizationId);
		List<String> tbl59OrganisationRealtionList = dao.getRelation1(organizationId);
		List<ComboBox> dtos = new ArrayList<>();
		for (Tbl59Organisation master : tbl59OrganisationList) {
			if (!tbl59OrganisationRealtionList.contains(master.getId())) {
				ComboBox dto = new ComboBox();
				dto.setLabel(master.getOrganisationCode());
				dto.setValue(master.getId());
				dtos.add(dto);
			}
		}

		long total = applicationResponse.getTotal();
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

}

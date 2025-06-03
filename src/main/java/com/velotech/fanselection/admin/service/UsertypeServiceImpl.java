
package com.velotech.fanselection.admin.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.admin.dao.UsertypeDao;
import com.velotech.fanselection.dtos.UsertypeDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl50Usertype;
import com.velotech.fanselection.models.Tbl51UsertypeFanType;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class UsertypeServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(UsertypeServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private UsertypeDao dao;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl50Usertype tbl50Usertype = new Tbl50Usertype();

		UsertypeDto dto = new UsertypeDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, UsertypeDto.class);

		List<Tbl50Usertype> models = (List<Tbl50Usertype>) genericDao.findByParam(Tbl50Usertype.class, "userType", dto.getUserType());

		if (models.size() < 1) {
			BeanUtils.copyProperties(dto, tbl50Usertype);
			genericDao.save(tbl50Usertype);
			tbl50Usertype.setCompany(velotechUtil.getCompany());

			List<Integer> pumptypeids = dto.getFanTypeIds();
			for (Integer id : pumptypeids) {
				Tbl51UsertypeFanType tbl51UsertypeFantype = new Tbl51UsertypeFanType();
				tbl51UsertypeFantype.setTbl50Usertype(tbl50Usertype);
				tbl51UsertypeFantype.setTbl01Fantype(new Tbl01Fantype(id));
				genericDao.save(tbl51UsertypeFantype);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} else
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.RECORD_EXISTS_MSG);

		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		UsertypeDto dto = new UsertypeDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, UsertypeDto.class);

		dao.deleteUsertypePumptype(dto.getId());

		Tbl50Usertype tbl50Usertype = new Tbl50Usertype(dto.getId());

		List<Integer> pumptypeids = dto.getFanTypeIds();
		for (Integer id : pumptypeids) {
			Tbl51UsertypeFanType usertypeFanType = new Tbl51UsertypeFanType();
			usertypeFanType.setTbl50Usertype(tbl50Usertype);
			usertypeFanType.setTbl01Fantype(new Tbl01Fantype(id));
			genericDao.save(usertypeFanType);
		}

		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl50Usertype.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<SearchCriteria> searchCriterias = new ArrayList<>();
		if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
			SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
			searchCriterias.add(searchCriteria);
		}

		Conjunction conjunction = GenericUtil.getConjuction(Tbl50Usertype.class, searchCriterias);
		applicationResponse = genericDao.getRecords(Tbl50Usertype.class, conjunction, requestWrapper.getPagination());

		List<Tbl50Usertype> models = (List<Tbl50Usertype>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<UsertypeDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	private List<UsertypeDto> getData(List<Tbl50Usertype> models) {

		List<UsertypeDto> dtos = new ArrayList<>();
		for (Tbl50Usertype model : models) {
			UsertypeDto dto = new UsertypeDto();

			@SuppressWarnings("unchecked")
			List<Tbl51UsertypeFanType> usertypeFanType = (List<Tbl51UsertypeFanType>) genericDao
					.getRecordsByParentId(Tbl51UsertypeFanType.class, "tbl50Usertype.id", model.getId());

			List<Integer> pumpTypeIds = usertypeFanType.stream().map(x -> x.getTbl01Fantype().getId()).collect(Collectors.toList());
			List<String> pumpSeries = usertypeFanType.stream().map(x -> x.getTbl01Fantype().getSeries()).collect(Collectors.toList());

			BeanUtils.copyProperties(model, dto);
			dto.setFanTypeIds(pumpTypeIds);
			dto.setFanSeries(pumpSeries);
			dtos.add(dto);
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "User Type" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> criterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			List<Tbl50Usertype> models = (List<Tbl50Usertype>) genericDao.getRecords(Tbl50Usertype.class,
					GenericUtil.getConjuction(Tbl50Usertype.class, criterias));
			List<UsertypeDto> dtos = getData(models);

			Field[] propertyFields = UsertypeDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (UsertypeDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}

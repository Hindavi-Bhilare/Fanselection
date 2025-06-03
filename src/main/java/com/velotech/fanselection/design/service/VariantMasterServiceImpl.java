
package com.velotech.fanselection.design.service;

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
import com.velotech.fanselection.dtos.VariantMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl01VariantMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class VariantMasterServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(VariantMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl01VariantMaster model = new Tbl01VariantMaster();
		VariantMasterDto dto = new VariantMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			dto = mapper.readValue(requestPayload, VariantMasterDto.class);
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			SearchCriteria pumpTypeId = new SearchCriteria("tbl01Fantype.id", dto.getFantypeId());
			SearchCriteria tempVariant = new SearchCriteria("variant", dto.getVariant());
			searchCriterias.add(pumpTypeId);
			searchCriterias.add(tempVariant);

			List<Tbl01VariantMaster> models = (List<Tbl01VariantMaster>) genericDao.getRecords(Tbl01VariantMaster.class,
					GenericUtil.getConjuction(Tbl01VariantMaster.class, searchCriterias));
			if (models.size() == 0) {
				BeanUtils.copyProperties(dto, model);
				model.setTbl01Fantype(new Tbl01Fantype(dto.getFantypeId()));
				model.setCompany(velotechUtil.getCompany());
				genericDao.save(model);
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
			} else
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.RECORD_EXIST_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		VariantMasterDto dto = new VariantMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, VariantMasterDto.class);
		Tbl01VariantMaster model = (Tbl01VariantMaster) genericDao.getRecordById(Tbl01VariantMaster.class, dto.getId());
		BeanUtils.copyProperties(dto, model);
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		try {
			genericDao.deleteAll(Tbl01VariantMaster.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<SearchCriteria> searchCriterias = new ArrayList<>();
		if (requestWrapper.getSearchValue() != null & requestWrapper.getSearchValue() != "") {
			SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
			searchCriterias.add(searchCriteria);
		}

		Conjunction conjunction = GenericUtil.getConjuction(Tbl01VariantMaster.class, searchCriterias);
		applicationResponse = genericDao.getRecords(Tbl01VariantMaster.class, conjunction, requestWrapper.getPagination());

		List<Tbl01VariantMaster> models = (List<Tbl01VariantMaster>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<VariantMasterDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	private List<VariantMasterDto> getData(List<Tbl01VariantMaster> models) {

		List<VariantMasterDto> dtos = new ArrayList<>();
		for (Tbl01VariantMaster model : models) {
			VariantMasterDto dto = new VariantMasterDto();
			BeanUtils.copyProperties(model, dto);
			dto.setFantypeId(model.getTbl01Fantype().getId());
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "VariantMaster" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> criterias = new ArrayList<>();

			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			List<Tbl01VariantMaster> models = (List<Tbl01VariantMaster>) genericDao.getRecords(Tbl01VariantMaster.class,
					GenericUtil.getConjuction(Tbl01VariantMaster.class, criterias));
			List<VariantMasterDto> dtos = getData(models);

			Field[] propertyFields = VariantMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (VariantMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}

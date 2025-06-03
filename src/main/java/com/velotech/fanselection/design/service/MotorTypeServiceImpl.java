package com.velotech.fanselection.design.service;

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
import com.velotech.fanselection.dtos.MotorTypeDto;
import com.velotech.fanselection.dtos.MotorTypeTreeDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl1401Motortype;
import com.velotech.fanselection.models.Tbl80TemplateMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.SqlOrder;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class MotorTypeServiceImpl implements MasterService,MotorTypeService {

	static Logger log = LogManager.getLogger(MotorTypeServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private ParameterValueService parameterValue;

	@Override
	public ApplicationResponse addRecord(String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl1401Motortype model = new Tbl1401Motortype();
		MotorTypeDto dto = new MotorTypeDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			dto = mapper.readValue(requestPayload, MotorTypeDto.class);
			BeanUtils.copyProperties(dto, model);
			if (dto.getCsdTemplateId() != null)
				model.setTbl80TemplateMasterByCsdTemplateId(new Tbl80TemplateMaster(dto.getCsdTemplateId()));
			model.setUuid(velotechUtil.getUuid());
			//model.setDescription(dto.getManufacturer()+"-"+dto.getSeries()+"-"+dto.getPhase()+"PH-"+dto.getPole()+"P-"+dto.getFrequency()+"Hz");
			model.setDescription(dto.getSeries()+"-"+dto.getPhase()+"PH");

			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		MotorTypeDto dto = new MotorTypeDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			dto = mapper.readValue(requestPayload, MotorTypeDto.class);
			Tbl1401Motortype model = (Tbl1401Motortype) genericDao.getRecordById(Tbl1401Motortype.class, dto.getId());
			BeanUtils.copyProperties(dto, model);
			if (dto.getCsdTemplateId() != null)
				model.setTbl80TemplateMasterByCsdTemplateId(new Tbl80TemplateMaster(dto.getCsdTemplateId()));
		
			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {
		ApplicationResponse applicationResponse = null;
		List<Tbl1401Motortype> list = (List<Tbl1401Motortype>) genericDao.getRecordByIds(Tbl1401Motortype.class, ids);
		List<String> uuids = list.stream().map(Tbl1401Motortype::getUuid).collect(Collectors.toList());

		Boolean delete = genericDao.deleteAll(list);
		if (delete)
			parameterValue.deleteRecordsUuid(uuids);

		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<SearchCriteria> searchCriterias = new ArrayList<>();
		try {

			if (requestWrapper.getSearchValue() != "") {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(),
						requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}
			SqlOrder sqlOrder = new SqlOrder("id", "DESC");

			Conjunction conjunction = GenericUtil.getConjuction(Tbl1401Motortype.class, searchCriterias);
			applicationResponse = genericDao.getRecords(Tbl1401Motortype.class, conjunction,
					requestWrapper.getPagination(),sqlOrder);

			List<Tbl1401Motortype> models = (List<Tbl1401Motortype>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<MotorTypeDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<MotorTypeDto> getData(List<Tbl1401Motortype> models) {

		List<MotorTypeDto> dtos = new ArrayList<>();
		for (Tbl1401Motortype model : models) {
			MotorTypeDto dto = new MotorTypeDto();
			BeanUtils.copyProperties(model, dto);
	
			if (model.getTbl80TemplateMasterByCsdTemplateId() != null)
				dto.setCsdTemplateId(model.getTbl80TemplateMasterByCsdTemplateId().getId());
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "MotorType" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> criterias = new ArrayList<>();

			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(),
						requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			List<Tbl1401Motortype> models = (List<Tbl1401Motortype>) genericDao.getRecords(Tbl1401Motortype.class,
					GenericUtil.getConjuction(Tbl1401Motortype.class, criterias));
			List<MotorTypeDto> dtos = getData(models);

			Field[] propertyFields = MotorTypeDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (MotorTypeDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Override
	public Object getMotorTypeTree(RequestWrapper requestWrapper) {
		Object response = new Object();
		List<SearchCriteria> searchCriterias = new ArrayList<>();
		try {

			response = genericDao.getRecords(Tbl1401Motortype.class);

			List<Tbl1401Motortype> models = (List<Tbl1401Motortype>) response;
		
			Object dtos =  getTreeData(models);
			response = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}
	
	private Object getTreeData( List<Tbl1401Motortype> tbl1401Motortype ) {

		MotorTypeTreeDto mainBranch = new MotorTypeTreeDto();
		mainBranch.setText("Motor Series");
		mainBranch.setExpanded(true);		
		mainBranch.setCard("motortype");
		List<Object> mainsChildren = new ArrayList<>();
		try {
			for (Tbl1401Motortype node : tbl1401Motortype) {
				MotorTypeTreeDto branch = new MotorTypeTreeDto();
				branch.setMotorTypeId(node.getId());
				branch.setText(node.getDescription());
				branch.setIconCls("x-fa fa-briefcase dark-grey");
				branch.setCard("motortype");
				mainsChildren.add(branch);
			}
			mainBranch.setData(mainsChildren);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mainBranch;
	}
	

}

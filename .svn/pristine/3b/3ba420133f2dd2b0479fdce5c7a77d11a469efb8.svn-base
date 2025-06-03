
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
import com.velotech.fanselection.design.dao.PrimeMoverMasterDao;
import com.velotech.fanselection.dtos.PrimeMoverMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl01ProductMaster;
import com.velotech.fanselection.models.Tbl01ProductTypeMaster;
import com.velotech.fanselection.models.Tbl1401Motortype;
import com.velotech.fanselection.models.Tbl14FrameMaster;
//import com.velotech.fanselection.models.Tbl14PrimemoverCurrent;
//import com.velotech.fanselection.models.Tbl14PrimemoverData;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class PrimeMoverMasterServiceImpl implements MasterService, PrimeMoverMasterService {

	static Logger log = LogManager.getLogger(PrimeMoverMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private ParameterValueService parameterValue;
	
	@Autowired
	private PrimeMoverMasterDao dao;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl14PrimemoverMaster model = new Tbl14PrimemoverMaster();
		PrimeMoverMasterDto dto = new PrimeMoverMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, PrimeMoverMasterDto.class);
		BeanUtils.copyProperties(dto, model);

		List<Tbl01ProductTypeMaster> productTypeMasters = (List<Tbl01ProductTypeMaster>) genericDao.findByParam(Tbl01ProductTypeMaster.class,
				"productTypeDescription", "PrimeMover");
		Tbl01ProductMaster productMaster = new Tbl01ProductMaster();
		productMaster.setTbl01ProductTypeMaster(new Tbl01ProductTypeMaster(productTypeMasters.get(0).getId()));
		productMaster.setUuid(velotechUtil.getUuid());
		genericDao.save(productMaster);

		if (dto.getMotorSeriesId() != null)
			model.setTbl1401Motortype(new Tbl1401Motortype(dto.getMotorSeriesId()));
		if(dto.getFrameMasterId()!= null)
			model.setTbl14FrameMaster(new Tbl14FrameMaster(dto.getFrameMasterId()));
		model.setUuid(productMaster.getUuid());
		model.setPrimemoverId(productMaster.getId());
		
		Tbl1401Motortype motorSeries=(Tbl1401Motortype) genericDao.getRecordById(Tbl1401Motortype.class,dto.getMotorSeriesId());
		model.setModelName(dto.getModelName()+" "+motorSeries.getSeries()+"-"+dto.getPower()+"kW-"+dto.getVoltage()+"V");
		

		genericDao.save(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		PrimeMoverMasterDto dto = new PrimeMoverMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, PrimeMoverMasterDto.class);
		Tbl14PrimemoverMaster model = (Tbl14PrimemoverMaster) genericDao.getRecordById(Tbl14PrimemoverMaster.class, dto.getPrimemoverId());
		BeanUtils.copyProperties(dto, model);
		
		if (dto.getMotorSeriesId() != null)
			model.setTbl1401Motortype(new Tbl1401Motortype(dto.getMotorSeriesId()));
		if(dto.getFrameMasterId()!= null)
			model.setTbl14FrameMaster(new Tbl14FrameMaster(dto.getFrameMasterId()));

		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

//		ApplicationResponse applicationResponse = null;
//		genericDao.deleteAll(Tbl14PrimemoverMaster.class, ids);
//		genericDao.deleteAll(Tbl01ProductMaster.class,ids);
//		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
//		return applicationResponse;
		ApplicationResponse applicationResponse = null;
		List<Tbl14PrimemoverMaster> list = (List<Tbl14PrimemoverMaster>) genericDao
				.getRecordByIds(Tbl14PrimemoverMaster.class, ids);
		List<String> uuids = list.stream().map(Tbl14PrimemoverMaster::getUuid).collect(Collectors.toList());
		
		List<Tbl01ProductMaster> lists = (List<Tbl01ProductMaster>) genericDao
				.getRecordByIds(Tbl01ProductMaster.class, ids);
		Boolean delete = genericDao.deleteAll(list);
		genericDao.deleteAll(lists);
		
		
		if (delete)
			parameterValue.deleteRecordsUuid(uuids);

		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}
	
	@Override
	public ApplicationResponse getRecords(String frameMaster,  Double powermin,
			Double powermax, Double speed,Integer motorTypeId,RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<SearchCriteria> searchCriterias = new ArrayList<>();
		try {
			if (requestWrapper.getSearchValue() != "") {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(),
						requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}

			Conjunction conjunction = GenericUtil.getConjuction(Tbl14PrimemoverMaster.class, searchCriterias);
		
			
			applicationResponse = dao.getPrimemoverMasterData(frameMaster, powermin, powermax, speed,motorTypeId,
					conjunction,requestWrapper);

			List<Tbl14PrimemoverMaster> models = (List<Tbl14PrimemoverMaster>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<PrimeMoverMasterDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}
	private List<PrimeMoverMasterDto> getData(List<Tbl14PrimemoverMaster> models) {

		List<PrimeMoverMasterDto> dtos = new ArrayList<>();
		for (Tbl14PrimemoverMaster model : models) {
			PrimeMoverMasterDto dto = new PrimeMoverMasterDto();
			BeanUtils.copyProperties(model, dto);
			
			
			if (model.getTbl14FrameMaster() != null) {
				dto.setFrameMasterId(model.getTbl14FrameMaster().getId());
				dto.setFrameMaster(model.getTbl14FrameMaster().getFrameSize() + "-" + model.getTbl14FrameMaster().getPole());
			}
			
			
			if (model.getTbl1401Motortype() != null) {
				dto.setMotorSeriesId(model.getTbl1401Motortype().getId());
				dto.setMotorSeries(model.getTbl1401Motortype().getSeries());
			}
			
			dtos.add(dto);
		}
		return dtos;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void downloadExcel(String frameMaster,  Double powermin,
			Double powermax, Double speed,Integer motorTypeId, RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "PrimemoverMaster" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			List<SearchCriteria> criterias = new ArrayList<>();

			applicationResponse = dao.getPrimemoverMasterData(frameMaster,  powermin, powermax, speed,motorTypeId, null,requestWrapper);

			List<Tbl14PrimemoverMaster> models = (List<Tbl14PrimemoverMaster>) applicationResponse.getData();
			List<PrimeMoverMasterDto> dtos = getData(models);

			Field[] propertyFields = PrimeMoverMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (PrimeMoverMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper){

		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<SearchCriteria> searchCriterias = new ArrayList<>();
		if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
			SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
			searchCriterias.add(searchCriteria);
		}

		Conjunction conjunction = GenericUtil.getConjuction(Tbl14PrimemoverMaster.class, searchCriterias);
		applicationResponse = genericDao.getRecords(Tbl14PrimemoverMaster.class, conjunction, requestWrapper.getPagination());

		List<Tbl14PrimemoverMaster> models = (List<Tbl14PrimemoverMaster>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<PrimeMoverMasterDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "PrimemoverMaster" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			List<SearchCriteria> criterias = new ArrayList<>();

			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			List<Tbl14PrimemoverMaster> models = (List<Tbl14PrimemoverMaster>) genericDao.getRecords(Tbl14PrimemoverMaster.class,
					GenericUtil.getConjuction(Tbl14PrimemoverMaster.class, criterias));
			List<PrimeMoverMasterDto> dtos = getData(models);

			Field[] propertyFields = PrimeMoverMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (PrimeMoverMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse copyRecords(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		/*
		 * try { PrimeMoverMasterDto dto = new PrimeMoverMasterDto(); ObjectMapper
		 * mapper = new ObjectMapper(); List<Tbl01ProductTypeMaster> productTypeMasters
		 * = (List<Tbl01ProductTypeMaster>)
		 * genericDao.findByParam(Tbl01ProductTypeMaster.class,
		 * "productTypeDescription", "PrimeMover"); Tbl01ProductMaster productMaster =
		 * new Tbl01ProductMaster(); productMaster.setTbl01ProductTypeMaster(new
		 * Tbl01ProductTypeMaster(productTypeMasters.get(0).getId()));
		 * productMaster.setUuid(velotechUtil.getUuid());
		 * genericDao.save(productMaster); dto = mapper.readValue(requestPayload,
		 * PrimeMoverMasterDto.class); Tbl14PrimemoverMaster tbl14PrimemoverMasterOld =
		 * (Tbl14PrimemoverMaster) genericDao.getRecordById(Tbl14PrimemoverMaster.class,
		 * dto.getPrimemoverId()); Tbl14PrimemoverMaster tbl14PrimemoverMasterNew = new
		 * Tbl14PrimemoverMaster(); BeanUtils.copyProperties(tbl14PrimemoverMasterOld,
		 * tbl14PrimemoverMasterNew, new String[] { "primemoverId",
		 * "tbl14PrimemoverDatas","uuid" });
		 * tbl14PrimemoverMasterNew.setModelName(dto.getModelName());
		 * tbl14PrimemoverMasterNew.setTbl1401Motortype(new
		 * Tbl1401Motortype(dto.getMotorSeriesId()));
		 * tbl14PrimemoverMasterNew.setPrimemoverId(productMaster.getId());
		 * tbl14PrimemoverMasterNew.setUuid(productMaster.getUuid()); if
		 * (genericDao.save(tbl14PrimemoverMasterNew)) {
		 * parameterValue.copyRecords(tbl14PrimemoverMasterOld.getUuid(),
		 * tbl14PrimemoverMasterNew.getUuid()); List<Tbl14PrimemoverData>
		 * tbl14PrimemoverDatas = (List<Tbl14PrimemoverData>)
		 * genericDao.getRecordsByParentId( Tbl14PrimemoverData.class,
		 * "tbl14PrimemoverMaster.primemoverId",
		 * tbl14PrimemoverMasterOld.getPrimemoverId()); List<Tbl14PrimemoverData>
		 * tbl14PrimemoverDatasList = new ArrayList<>(); for (Tbl14PrimemoverData
		 * tbl14PrimemoverDataOld : tbl14PrimemoverDatas) { Tbl14PrimemoverData
		 * tbl14PrimemoverDataNew = new Tbl14PrimemoverData();
		 * BeanUtils.copyProperties(tbl14PrimemoverDataOld, tbl14PrimemoverDataNew);
		 * tbl14PrimemoverDataNew.setTbl14PrimemoverMaster(tbl14PrimemoverMasterNew);
		 * tbl14PrimemoverDataNew.setFramesizeDataId(0);
		 * tbl14PrimemoverDatasList.add(tbl14PrimemoverDataNew); }
		 * genericDao.saveAll(tbl14PrimemoverDatasList);
		 * 
		 * List<Tbl14PrimemoverCurrent> oldPrimemoverCurrentList =
		 * (List<Tbl14PrimemoverCurrent>) genericDao.getRecordsByParentId(
		 * Tbl14PrimemoverCurrent.class, "tbl14PrimemoverMaster.primemoverId",
		 * tbl14PrimemoverMasterOld.getPrimemoverId()); List<Tbl14PrimemoverCurrent>
		 * newPrimemoverCurrentList = new ArrayList<>(); for (Tbl14PrimemoverCurrent
		 * oldPrimemoverCurrent : oldPrimemoverCurrentList) { Tbl14PrimemoverCurrent
		 * newPrimeMoverCurrent = new Tbl14PrimemoverCurrent();
		 * BeanUtils.copyProperties(oldPrimemoverCurrent, newPrimeMoverCurrent);
		 * newPrimeMoverCurrent.setTbl14PrimemoverMaster(tbl14PrimemoverMasterNew);
		 * newPrimemoverCurrentList.add(newPrimeMoverCurrent); }
		 * genericDao.saveAll(newPrimemoverCurrentList); } applicationResponse =
		 * ApplicationResponseUtil.getResponseToCRUD(true,
		 * ApplicationConstants.COPY_SUCCESS_MSG);
		 * 
		 * } catch (Exception e) { log.error(e.getMessage(), e); }
		 */

		return applicationResponse;
	}

}

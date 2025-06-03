package com.velotech.fanselection.design.service;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.design.dao.CentrifugalModelMasterDao;
import com.velotech.fanselection.dtos.CentrifugalModelMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl01ProductMaster;
import com.velotech.fanselection.models.Tbl01ProductTypeMaster;
import com.velotech.fanselection.models.Tbl02ModelPrice;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.models.Tbl90DocumentMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class CentrifugalModelMasterServiceImpl implements CentrifugalModelMasterService, MasterService{ 
	
	static Logger log = LogManager.getLogger(CentrifugalModelMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private CentrifugalModelMasterDao dao;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private ParameterValueService parameterValue;


	@Override
	public ApplicationResponse addRecord(String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {
		ApplicationResponse applicationResponse = null;
		Tbl01CentrifugalModelMaster model = new Tbl01CentrifugalModelMaster();
		CentrifugalModelMasterDto dto = new CentrifugalModelMasterDto();
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {

			dto = mapper.readValue(requestPayload, CentrifugalModelMasterDto.class);
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			SearchCriteria fanType = new SearchCriteria("tbl01fantype.id", dto.getFanTypeId());
			SearchCriteria fanModel = new SearchCriteria("fanModel", dto.getFanModel());
			searchCriterias.add(fanType);
			searchCriterias.add(fanModel);  

			List<Tbl01CentrifugalModelMaster> models = (List<Tbl01CentrifugalModelMaster>) genericDao.getRecords(Tbl01CentrifugalModelMaster.class,
					GenericUtil.getConjuction(Tbl01CentrifugalModelMaster.class, searchCriterias));
			if (models.size() == 0) {
				List<Tbl01ProductTypeMaster> productTypeMasters = (List<Tbl01ProductTypeMaster>) genericDao
						.findByParam(Tbl01ProductTypeMaster.class, "productTypeDescription", "Fan");
				Tbl01ProductMaster productMaster = new Tbl01ProductMaster();
				productMaster.setTbl01ProductTypeMaster(new Tbl01ProductTypeMaster(productTypeMasters.get(0).getId()));
				productMaster.setUuid(velotechUtil.getUuid());
				genericDao.save(productMaster);
				BeanUtils.copyProperties(dto, model);
				// model.setStageFactor(1d);
				if (dto.getFanTypeId() != null)
					model.setTbl01fantype(new Tbl01Fantype(dto.getFanTypeId()));

				Tbl28CompanyMaster companyMaster = (Tbl28CompanyMaster) genericDao
						.getRecordById(Tbl28CompanyMaster.class, velotechUtil.getCompanyId());
				//model.setMake(companyMaster.getCompanyName());
				model.setCompany(velotechUtil.getCompany());
				model.setId(productMaster.getId());
				model.setUuid(productMaster.getUuid());
				genericDao.save(model);

				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
						ApplicationConstants.INSERT_SUCCESS_MSG);
			} else
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
						ApplicationConstants.RECORD_EXIST_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {
		ApplicationResponse applicationResponse = null;
		CentrifugalModelMasterDto dto = new CentrifugalModelMasterDto();
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		dto = mapper.readValue(requestPayload, CentrifugalModelMasterDto.class);
		Tbl01CentrifugalModelMaster model = (Tbl01CentrifugalModelMaster) genericDao.getRecordById(Tbl01CentrifugalModelMaster.class, dto.getId());
		BeanUtils.copyProperties(dto, model);
		if (dto.getFanTypeId() != null)
			model.setTbl01fantype(new Tbl01Fantype(dto.getFanTypeId()));

		genericDao.update(model);

		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.UPDATE_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {
		// ApplicationResponse applicationResponse = null;
//		genericDao.deleteAll(Tbl01ProductMaster.class, ids); // applicationResponse =
//		ApplicationResponseUtil.getResponseToCRUD(true, //
//		ApplicationConstants.DELETE_SUCCESS_MSG); // return applicationResponse;
		ApplicationResponse applicationResponse = null;
		List<Tbl01CentrifugalModelMaster> list = (List<Tbl01CentrifugalModelMaster>) genericDao.getRecordByIds(Tbl01CentrifugalModelMaster.class, ids);
		List<String> uuids = list.stream().map(Tbl01CentrifugalModelMaster::getUuid).collect(Collectors.toList());

		for (Tbl01CentrifugalModelMaster master : list) {
			/*
			 * List<Tbl12ModelPerformance> tbl12ModelPerformance =
			 * (List<Tbl12ModelPerformance>) genericDao
			 * .getRecordsByParentId(Tbl12ModelPerformance.class, "tbl02Modelmaster.id",
			 * master.getId());
			 */
			List<Tbl02ModelPrice> tbl02ModelPrice = (List<Tbl02ModelPrice>) genericDao
					.getRecordsByParentId(Tbl02ModelPrice.class, "tbl01CentrifugalModelMaster.id", master.getId());
			/*
			 * List<Tbl03StageTemperature> tbl03StageTemperature =
			 * (List<Tbl03StageTemperature>) genericDao
			 * .getRecordsByParentId(Tbl03StageTemperature.class, "tbl02Modelmaster.id",
			 * master.getId());
			 */

			if ( tbl02ModelPrice.size() == 0 ) {
				List<Tbl01ProductMaster> lists = (List<Tbl01ProductMaster>) genericDao
						.getRecordByIds(Tbl01ProductMaster.class, ids);
				Boolean delete = genericDao.deleteAll(list);
				genericDao.deleteAll(lists);

				if (delete)
					parameterValue.deleteRecordsUuid(uuids);
				applicationResponse =

						ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
			} else

			{
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(false,
						ApplicationConstants.DELETE_FAIL_MSG);
			}
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {
		ApplicationResponse applicationResponse = new ApplicationResponse();
		String searchProperty = null;
		String searchValue = null;
		try {
			if (requestWrapper.getSearchValue() != null & requestWrapper.getSearchValue() != "") {
				searchProperty = requestWrapper.getSearchProperty();
				searchValue = (String) requestWrapper.getSearchValue();
			}

			applicationResponse = dao.getCentrifugalModelMasterData(searchProperty, searchValue, requestWrapper.getPagination());
			List<Tbl01CentrifugalModelMaster> models = (List<Tbl01CentrifugalModelMaster>) applicationResponse.getData();
			long total = applicationResponse.getTotal();
			List<CentrifugalModelMasterDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {
		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "ModelMaster" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			String searchProperty = null;
			String searchValue = null;
			if (requestWrapper.getSearchValue() != null & requestWrapper.getSearchValue() != "") {
				searchProperty = requestWrapper.getSearchProperty();
				searchValue = (String) requestWrapper.getSearchValue();
			}

			List<Tbl01CentrifugalModelMaster> models = (List<Tbl01CentrifugalModelMaster>) dao
					.getCentrifugalModelMasterData(searchProperty, searchValue, null).getData();
			List<CentrifugalModelMasterDto> dtos = getData(models);

			Field[] propertyFields = CentrifugalModelMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (CentrifugalModelMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
	private List<CentrifugalModelMasterDto> getData(List<Tbl01CentrifugalModelMaster> models) {

		List<CentrifugalModelMasterDto> dtos = new ArrayList<>();
		for (Tbl01CentrifugalModelMaster model : models) {
			CentrifugalModelMasterDto dto = new CentrifugalModelMasterDto();
			BeanUtils.copyProperties(model, dto);

			if (model.getTbl01fantype() != null) {
				dto.setFanTypeId(model.getTbl01fantype().getId());
				dto.setFanType(model.getTbl01fantype().getSeries());
			}
			if (model.getDocumentMaster() != null) {
				dto.setDocumentId(model.getDocumentMaster().getId());
				//dto.setFileName(model.getDocumentMaster().getFileName());
			}
			//dto.setMake(model.getMake());
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public ApplicationResponse copyModelMaster(Integer modelmasterid, String fanModel) {
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl01CentrifugalModelMaster tbl01CentrifugalModelMasterold = (Tbl01CentrifugalModelMaster) genericDao.getRecordById(Tbl01CentrifugalModelMaster.class,
					modelmasterid);
			Tbl01CentrifugalModelMaster tbl01CentrifugalModelMasterNew = new Tbl01CentrifugalModelMaster();
			BeanUtils.copyProperties(tbl01CentrifugalModelMasterold, tbl01CentrifugalModelMasterNew, new String[] { "uuid" });
			tbl01CentrifugalModelMasterNew.setUuid(velotechUtil.getUuid());
			tbl01CentrifugalModelMasterNew.setFanModel(fanModel);
			List<Tbl01ProductTypeMaster> productTypeMasters = (List<Tbl01ProductTypeMaster>) genericDao
					.findByParam(Tbl01ProductTypeMaster.class, "productTypeDescription", "Fan");
			Tbl01ProductMaster productMaster = new Tbl01ProductMaster();
			productMaster.setTbl01ProductTypeMaster(new Tbl01ProductTypeMaster(productTypeMasters.get(0).getId()));
			productMaster.setUuid(velotechUtil.getUuid());
			genericDao.save(productMaster);
			tbl01CentrifugalModelMasterNew.setId(productMaster.getId());

			if (genericDao.save(tbl01CentrifugalModelMasterNew)) {
				parameterValue.copyRecords(tbl01CentrifugalModelMasterold.getUuid(), tbl01CentrifugalModelMasterNew.getUuid());

				List<Tbl02ModelPrice> tbl02ModelPrices = (List<Tbl02ModelPrice>) genericDao.getRecordsByParentId(
						Tbl02ModelPrice.class, "tbl01CentrifugalModelMaster.id", tbl01CentrifugalModelMasterold.getId());
				List<Tbl02ModelPrice> tbl02ModelPricesList = new ArrayList<>();
				for (Tbl02ModelPrice tbl02ModelPriceOld : tbl02ModelPrices) {
					Tbl02ModelPrice tbl02ModelPriceNew = new Tbl02ModelPrice();
					BeanUtils.copyProperties(tbl02ModelPriceOld, tbl02ModelPriceNew);
					tbl02ModelPriceNew.setTbl01CentrifugalModelMaster(tbl01CentrifugalModelMasterNew);
					tbl02ModelPricesList.add(tbl02ModelPriceNew);
				}
				genericDao.saveAll(tbl02ModelPricesList);

			}

			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.COPY_SUCCESS_MSG);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getModelMasterDetails(RequestWrapper requestWrapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationResponse upload(String requestPayload) {
		ApplicationResponse applicationResponse = null;
		try {
			CentrifugalModelMasterDto dto = new CentrifugalModelMasterDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, CentrifugalModelMasterDto.class);
			Tbl01CentrifugalModelMaster model = (Tbl01CentrifugalModelMaster) genericDao.getRecordById(Tbl01CentrifugalModelMaster.class, dto.getId());

			if (dto.getDocumentId() != null)
				model.setDocumentMaster(new Tbl90DocumentMaster(dto.getDocumentId()));
			else
				model.setDocumentMaster(null);

			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}


}

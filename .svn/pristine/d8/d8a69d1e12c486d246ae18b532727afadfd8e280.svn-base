
package com.velotech.fanselection.design.service;

import java.io.IOException;

import java.lang.reflect.Field;
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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.ModelPriceDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01VariantMaster;
import com.velotech.fanselection.models.Tbl02ModelPrice;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class ModelPriceServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(ModelPriceServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl02ModelPrice model = new Tbl02ModelPrice();
		ModelPriceDto dto = new ModelPriceDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, ModelPriceDto.class);
		BeanUtils.copyProperties(dto, model);
		model.setCompany(velotechUtil.getCompany());
		if (dto.getCentrifugalModelId() != null)
			model.setTbl01CentrifugalModelMaster(new Tbl01CentrifugalModelMaster(dto.getCentrifugalModelId()));
		if (dto.getVariantMasterId() != null)
			model.setTbl01VariantMaster(new Tbl01VariantMaster(dto.getVariantMasterId()));
		if (dto.getPrimeMoverMasterId() != null)
			model.setTbl14PrimemoverMaster(new Tbl14PrimemoverMaster(dto.getPrimeMoverMasterId()));

		genericDao.save(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		ModelPriceDto dto = new ModelPriceDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, ModelPriceDto.class);
		Tbl02ModelPrice model = (Tbl02ModelPrice) genericDao.getRecordById(Tbl02ModelPrice.class, dto.getId());
		BeanUtils.copyProperties(dto, model);
		if (dto.getVariantMasterId() != null)
			model.setTbl01VariantMaster(new Tbl01VariantMaster(dto.getVariantMasterId()));
		if (dto.getPrimeMoverMasterId() != null)
			model.setTbl14PrimemoverMaster(new Tbl14PrimemoverMaster(dto.getPrimeMoverMasterId()));

		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl02ModelPrice.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Conjunction conjunction = GenericUtil.getConjuction(Tbl02ModelPrice.class, requestWrapper.getSearchCriterias());
			Pagination pagination = requestWrapper.getPagination();
			applicationResponse = genericDao.getRecords(Tbl02ModelPrice.class, conjunction, pagination);

			List<Tbl02ModelPrice> models = (List<Tbl02ModelPrice>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<ModelPriceDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	private List<ModelPriceDto> getData(List<Tbl02ModelPrice> models) {

		List<ModelPriceDto> dtos = new ArrayList<>();
		for (Tbl02ModelPrice model : models) {
			ModelPriceDto dto = new ModelPriceDto();
			BeanUtils.copyProperties(model, dto);
			if (model.getTbl01CentrifugalModelMaster() != null) {
				dto.setCentrifugalModelId(model.getTbl01CentrifugalModelMaster().getId());
			}
			if (model.getTbl01VariantMaster() != null) {
				dto.setVariantMasterId(model.getTbl01VariantMaster().getId());
				dto.setVariantMaster(model.getTbl01VariantMaster().getVariant());
			}
			if (model.getTbl14PrimemoverMaster() != null) {
				dto.setPrimeMoverMasterId(model.getTbl14PrimemoverMaster().getPrimemoverId());
				dto.setPrimeMoverModel(model.getTbl14PrimemoverMaster().getModelName());
				
			}

			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "ModelPrice" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> criterias = new ArrayList<>();

			criterias.add(new SearchCriteria("tbl02Modelmaster.id", requestWrapper.getParentId()));

			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				criterias.add(criteria);
			}
			List<Tbl02ModelPrice> models = (List<Tbl02ModelPrice>) genericDao.getRecords(Tbl02ModelPrice.class,
					GenericUtil.getConjuction(Tbl02ModelPrice.class, criterias));
			List<ModelPriceDto> dtos = getData(models);

			Field[] propertyFields = ModelPriceDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (ModelPriceDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}

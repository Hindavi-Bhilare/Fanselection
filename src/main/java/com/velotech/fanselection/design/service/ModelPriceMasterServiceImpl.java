
package com.velotech.fanselection.design.service;

import java.io.IOException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.design.dao.ModelPriceDao;
import com.velotech.fanselection.dtos.ModelPriceDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01VariantMaster;
import com.velotech.fanselection.models.Tbl02ModelPrice;
import com.velotech.fanselection.models.Tbl03MocMaster;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class ModelPriceMasterServiceImpl extends HibernateSession implements MasterService, ModelPriceMasterService {

	static Logger log = LogManager.getLogger(ModelPriceMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private ModelPriceDao modelPriceDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		try {
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
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		try {
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
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		try {
			genericDao.deleteAll(Tbl02ModelPrice.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null) {
				applicationResponse = modelPriceDao.getModelPriceData(requestWrapper);
			} else {
				Conjunction conjunction = GenericUtil.getConjuction(Tbl02ModelPrice.class, searchCriterias);
				Pagination pagination = requestWrapper.getPagination();
				applicationResponse = genericDao.getRecords(Tbl02ModelPrice.class, conjunction, pagination);
			}
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
				dto.setFanModel(model.getTbl01CentrifugalModelMaster().getFanModel());
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

	@SuppressWarnings("unchecked")
	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "Model Price" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
				applicationResponse = modelPriceDao.getModelPriceData(requestWrapper);
			} else {
				Conjunction conjunction = GenericUtil.getConjuction(Tbl02ModelPrice.class, searchCriterias);
				applicationResponse = genericDao.getRecords(Tbl02ModelPrice.class, conjunction, null);
			}
			List<Tbl02ModelPrice> models = (List<Tbl02ModelPrice>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

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

	@Override
	public ApplicationResponse generateRecords(int seriesId) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl02ModelPrice> existingModelPrice = getTbl02ModelPrice(seriesId);
			List<Tbl01VariantMaster> variantMasters = getvariantList(seriesId);

			List<Tbl03MocMaster> mocMasters = (List) getTableRecordListByParentId(Tbl03MocMaster.class, "tbl01fantype.id", seriesId);

			List<Tbl01CentrifugalModelMaster> modelmasters = (List) getTableRecordListByParentId(Tbl01CentrifugalModelMaster.class, "tbl01fantype.id", seriesId);

			/*
			 * for (Tbl02Modelmaster tbl02Modelmaster : modelmasters) { int minStage =
			 * tbl02Modelmaster.getMinStage(); int maxStage =
			 * tbl02Modelmaster.getMaxStage();
			 * 
			 * for (int i = minStage; i <= maxStage; i++) { for (Tbl03MocMaster
			 * tbl03MocMaster : mocMasters) { for (Tbl01VariantMaster tbl01VariantMaster :
			 * variantMasters) { boolean recordExist = checkRecordExist(existingModelPrice,
			 * tbl02Modelmaster.getId(), i, tbl03MocMaster, tbl01VariantMaster.getId()); if
			 * (!recordExist) { Tbl02ModelPrice tbl02ModelPrice = new Tbl02ModelPrice();
			 * tbl02ModelPrice.setTbl02Modelmaster(tbl02Modelmaster);
			 * tbl02ModelPrice.setTbl01VariantMaster(tbl01VariantMaster);
			 * tbl02ModelPrice.setStage(i);
			 * tbl02ModelPrice.setMocStd(tbl03MocMaster.getMocStd());
			 * tbl02ModelPrice.setRotation("CW"); tbl02ModelPrice.setArticleNo("");
			 * tbl02ModelPrice.setPrice(new BigDecimal(0));
			 * genericDao.save(tbl02ModelPrice); } } } } }
			 */
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	private boolean checkRecordExist(List<Tbl02ModelPrice> existingModelPrice, int pumpModelID, int stage, Tbl03MocMaster tbl03MocMaster,
			int variantId) {

		boolean ans = false;
		try {
			for (Tbl02ModelPrice tbl02ModelPrice : existingModelPrice) {
				if (tbl02ModelPrice.getTbl01CentrifugalModelMaster().getId() == pumpModelID 
						&& tbl02ModelPrice.getTbl01VariantMaster().getId() == variantId
						&& tbl02ModelPrice.getMocStd().equals(tbl03MocMaster.getMocStd()))
					return true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	private List<Tbl01VariantMaster> getvariantList(int seriesId) {

		List<Tbl01VariantMaster> variantMasterList = new ArrayList<>();
		try {
			List<SearchCriteria> searchCriterias = new ArrayList<>();

			SearchCriteria searchCriteria = new SearchCriteria("tbl01Fantype.id", seriesId);
			searchCriterias.add(searchCriteria);

			Conjunction conjunction = GenericUtil.getConjuction(Tbl01VariantMaster.class, searchCriterias);
			variantMasterList = modelPriceDao.getRecordss(Tbl01VariantMaster.class, conjunction);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return variantMasterList;
	}

	private List<Tbl02ModelPrice> getTbl02ModelPrice(int seriesid) {

		List<Tbl02ModelPrice> modelPriceList = new ArrayList<>();
		try {

			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Tbl01CentrifugalModelMaster.class);
			detachedCriteria.setProjection(Projections.property("tbl02ModelPrice.id"));
			detachedCriteria.createAlias("tbl01CentrifugalModelMaster", "tbl01CentrifugalModelMaster");
			detachedCriteria.add(Restrictions.eq("tbl01fantype.id", seriesid));

			modelPriceList = modelPriceDao.getRecords(Tbl02ModelPrice.class, detachedCriteria);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return modelPriceList;
	}

	private List<Object> getTableRecordListByParentId(final Class<?> o, final String restriction, Integer id) {

		List<Object> objects = new ArrayList<Object>();
		try {
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			SearchCriteria searchCriteria = new SearchCriteria(restriction, id);
			searchCriterias.add(searchCriteria);

			Conjunction conjunction = GenericUtil.getConjuction(o, searchCriterias);
			objects = this.getRecords(o, conjunction);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return objects;
	}

	public List<Object> getRecords(final Class<?> o, final Object restrictions) {

		List<Object> data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(o);
			if (restrictions != null)
				criteria.add((Conjunction) restrictions);
			List<Object> list = criteria.list();
			long total = list.size();
			data = list;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

}

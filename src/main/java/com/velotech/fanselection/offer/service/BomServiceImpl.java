
package com.velotech.fanselection.offer.service;

import java.io.IOException;



import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.BomDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl01ProductTypeMaster;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl28SelectedFan;
import com.velotech.fanselection.models.Tbl28SelectedFanBom;
import com.velotech.fanselection.selection.service.CentrifugalFanSelectionService;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;

@Service
@Transactional
public class BomServiceImpl implements BomService {

	static Logger log = LogManager.getLogger(BomServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CentrifugalFanSelectionService fanSelecionService;

	@Override
	public ApplicationResponse getRecords(Integer offerFanId) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl28SelectedFanBom> models = (List<Tbl28SelectedFanBom>) genericDao
					.getRecordsByParentId(Tbl28SelectedFanBom.class, "tbl26OfferFan.id", offerFanId);
			long total = models.size();

			List<BomDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<BomDto> getData(List<Tbl28SelectedFanBom> models) {

		List<BomDto> dtos = new ArrayList<>();
		try {
			for (Tbl28SelectedFanBom model : models) {
				BomDto dto = new BomDto();
				BeanUtils.copyProperties(model, dto);
				//dto.setTotal(dto.getQty() /** dto.getPricePerQty()*/);
				dto.setOfferFanId(model.getTbl26OfferFan().getId());
				dto.setProductTypeId(model.getTbl01ProductTypeMaster().getId());
				dto.setProductTypeDescription(model.getTbl01ProductTypeMaster().getProductTypeDescription());
				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dtos;
	}

	@Override
	public ApplicationResponse addRecord(String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl28SelectedFanBom model = new Tbl28SelectedFanBom();
			BomDto dto = new BomDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, BomDto.class);
			BeanUtils.copyProperties(dto, model);
			if (dto.getProductTypeId() != null)
				model.setTbl01ProductTypeMaster(new Tbl01ProductTypeMaster(dto.getProductTypeId()));
			model.setTbl26OfferFan(new Tbl26OfferFan(dto.getOfferFanId()));
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

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			BomDto dto = new BomDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, BomDto.class);
			Tbl28SelectedFanBom model = (Tbl28SelectedFanBom) genericDao.getRecordById(Tbl28SelectedFanBom.class,
					dto.getId());
			BeanUtils.copyProperties(dto, model);
			if (dto.getProductTypeId() != null)
				model.setTbl01ProductTypeMaster(new Tbl01ProductTypeMaster(dto.getProductTypeId()));
			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			genericDao.deleteAll(Tbl28SelectedFanBom.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse resetBom(Integer offerFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			// delete all tbl28SelectedPumpBom
			List<Tbl28SelectedFanBom> boms = (List<Tbl28SelectedFanBom>) genericDao
					.getRecordsByParentId(Tbl28SelectedFanBom.class, "tbl26OfferFan.id", offerFanId);
			genericDao.deleteAll(boms);

			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class,
					offerFanId);
			Tbl28SelectedFan tbl28SelectedFan = tbl26OfferFan.getTbl28SelectedFan();
			String series = tbl28SelectedFan.getSeries();
			Tbl01Fantype tbl01Fantype = (Tbl01Fantype) genericDao.getUniqueRecord(Tbl01Fantype.class, "series",
					series);
			Tbl14PrimemoverMaster primemoverMaster = (Tbl14PrimemoverMaster) genericDao.getRecordById(
					Tbl14PrimemoverMaster.class, tbl26OfferFan.getTbl28SelectedPrimemover().getPrimemoverId());

			Integer fanTypeId = tbl01Fantype.getId();
			//Integer stage = Integer.parseInt(tbl28SelectedFan.getStage());
			String mocStd = tbl28SelectedFan.getMocStd();
			Integer variantId = tbl28SelectedFan.getVariantId();
			//String shaftGroup = tbl28SelectedFan.getShaftGroup();

			List<Tbl28SelectedFanBom> tbl28SelectedFanBoms = /*fanSelecionService.generateGenericBom(tbl26OfferFan,
					fanTypeId, mocStd, variantId,primemoverMaster.getPrimemoverId())*/ new ArrayList<Tbl28SelectedFanBom>();
			genericDao.saveAll(tbl28SelectedFanBoms);

			//pumpSelecionService.saveSelectedMotorBom(primemoverMaster.getTbl1401Motortype().getId(), tbl26OfferPump);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}

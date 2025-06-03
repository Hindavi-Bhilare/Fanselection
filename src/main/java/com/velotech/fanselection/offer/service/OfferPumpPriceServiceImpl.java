
package com.velotech.fanselection.offer.service;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.OfferFanPriceAddonDto;
import com.velotech.fanselection.dtos.SelectedPricingDetailsDto;
import com.velotech.fanselection.dtos.SelectedPricingDto;
import com.velotech.fanselection.dtos.SelectedFanBomDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl28SelectedPricing;
import com.velotech.fanselection.models.Tbl28SelectedFanBom;
import com.velotech.fanselection.models.Tbl28SelectedpricingDetails;
import com.velotech.fanselection.models.Tbl30Offerfanpriceaddon;
import com.velotech.fanselection.offer.dao.OfferPumpPriceDao;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;

@Service
@Transactional
public class OfferPumpPriceServiceImpl implements OfferPumpPriceService {

	static Logger log = LogManager.getLogger(OfferPumpPriceServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private OfferPumpPriceDao dao;

	@Override
	public ApplicationResponse getOfferFactor(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Double> offerFactors = dao.getOfferFactors(offerRevId);
			Double offerFactor = 1d;
			for (Double factor : offerFactors) {
				offerFactor *= factor;
			}

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, offerFactor);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getSpareParts(Integer offerFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl28SelectedFanBom> models = (List<Tbl28SelectedFanBom>) genericDao.getRecordsByParentId(Tbl28SelectedFanBom.class,
					"tbl26OfferFan.id", offerFanId);

			List<SelectedFanBomDto> dtos = new ArrayList<>();

			for (Tbl28SelectedFanBom model : models) {
				SelectedFanBomDto dto = new SelectedFanBomDto();
				BeanUtils.copyProperties(model, dto);
				dto.setSpecification(model.getMaterialDescription());
				dtos.add(dto);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse saveSparePartsPricing(Integer offerFanId, List<Integer> ids) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			List<Tbl28SelectedFanBom> models = (List<Tbl28SelectedFanBom>) genericDao.getRecordByIds(Tbl28SelectedFanBom.class, ids);
			for (Tbl28SelectedFanBom model : models) {
				Tbl28SelectedPricing tbl28SelectedPricing = new Tbl28SelectedPricing();
				tbl28SelectedPricing.setTbl26OfferFan(tbl26OfferFan);
				tbl28SelectedPricing.setSorting(0);
				tbl28SelectedPricing.setItemName(model.getItemNo());
				tbl28SelectedPricing.setDescription(model.getMaterialDescription());
				tbl28SelectedPricing.setArticleNo(model.getArticleNo());
				tbl28SelectedPricing.setApplyFactor(true);
				tbl28SelectedPricing.setGroupClass("ADD_SPAREPARTS");
				tbl28SelectedPricing.setScope("Yes");
				tbl28SelectedPricing.setSource("InHouse");

				genericDao.save(tbl28SelectedPricing);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getSelectedPricings(Integer offerFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl28SelectedPricing> models = (List<Tbl28SelectedPricing>) genericDao.getRecordsByParentId(Tbl28SelectedPricing.class,
					"tbl26OfferFan.id", offerFanId);

			List<SelectedPricingDto> dtos = new ArrayList<>();

			for (Tbl28SelectedPricing model : models) {
				SelectedPricingDto dto = new SelectedPricingDto();
				BeanUtils.copyProperties(model, dto);
				dto.setOfferFanId(offerFanId);
				dtos.add(dto);
			}
			// dtos.sort((SelectedPricingDto dto1, SelectedPricingDto dto2) ->
			// dto1.getSorting() - dto2.getSorting());
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse saveSelectedPricing(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			SelectedPricingDto dto = mapper.readValue(requestPayload, SelectedPricingDto.class);
			if (dto.getId() > 0) {
				Tbl28SelectedPricing model = (Tbl28SelectedPricing) genericDao.getRecordById(Tbl28SelectedPricing.class, dto.getId());
				BeanUtils.copyProperties(dto, model);
				genericDao.update(model);
			} else {
				Tbl28SelectedPricing model = new Tbl28SelectedPricing();
				BeanUtils.copyProperties(dto, model);
				Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, dto.getOfferFanId());
				model.setTbl26OfferFan(tbl26OfferFan);
				genericDao.save(model);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteSelectedPricings(List<Integer> ids) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			genericDao.deleteAll(Tbl28SelectedPricing.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getPricingDetails(Integer selectedPricingId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl28SelectedpricingDetails> models = (List<Tbl28SelectedpricingDetails>) genericDao
					.getRecordsByParentId(Tbl28SelectedpricingDetails.class, "tbl28SelectedPricing.id", selectedPricingId);

			List<SelectedPricingDetailsDto> dtos = new ArrayList<>();

			for (Tbl28SelectedpricingDetails model : models) {
				SelectedPricingDetailsDto dto = new SelectedPricingDetailsDto();
				BeanUtils.copyProperties(model, dto);
				dto.setSelectedPricingId(selectedPricingId);
				dtos.add(dto);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse savePricingDetails(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<SelectedPricingDetailsDto> dtos = Arrays.asList(mapper.readValue(requestPayload, SelectedPricingDetailsDto[].class));
			for (SelectedPricingDetailsDto dto : dtos) {

				if (dto.getId() > 0) {
					Tbl28SelectedpricingDetails model = (Tbl28SelectedpricingDetails) genericDao.getRecordById(Tbl28SelectedpricingDetails.class,
							dto.getId());
					BeanUtils.copyProperties(dto, model);
					genericDao.update(model);
				} else {
					Tbl28SelectedpricingDetails model = new Tbl28SelectedpricingDetails();
					BeanUtils.copyProperties(dto, model);
					Tbl28SelectedPricing tbl28SelectedPricing = (Tbl28SelectedPricing) genericDao.getRecordById(Tbl28SelectedPricing.class,
							dto.getSelectedPricingId());
					model.setTbl28SelectedPricing(tbl28SelectedPricing);
					genericDao.save(model);
				}
			}
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deletePricingDetails(List<Integer> ids) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			genericDao.deleteAll(Tbl28SelectedpricingDetails.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getAddons(Integer offerFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl30Offerfanpriceaddon> models = (List<Tbl30Offerfanpriceaddon>) genericDao.getRecordsByParentId(Tbl30Offerfanpriceaddon.class,
					"tbl26OfferFan.id", offerFanId);

			List<OfferFanPriceAddonDto> dtos = new ArrayList<>();

			for (Tbl30Offerfanpriceaddon model : models) {
				OfferFanPriceAddonDto dto = new OfferFanPriceAddonDto();
				BeanUtils.copyProperties(model, dto);
				dto.setOfferFanId(offerFanId);
				dtos.add(dto);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse saveAddons(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<OfferFanPriceAddonDto> dtos = Arrays.asList(mapper.readValue(requestPayload, OfferFanPriceAddonDto[].class));
			for (OfferFanPriceAddonDto dto : dtos) {

				if (dto.getId() > 0) {
					Tbl30Offerfanpriceaddon model = (Tbl30Offerfanpriceaddon) genericDao.getRecordById(Tbl30Offerfanpriceaddon.class, dto.getId());
					BeanUtils.copyProperties(dto, model);
					genericDao.update(model);
				} else {
					Tbl30Offerfanpriceaddon model = new Tbl30Offerfanpriceaddon();
					BeanUtils.copyProperties(dto, model);
					Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, dto.getOfferFanId());
					model.setTbl26OfferFan(tbl26OfferFan);
					genericDao.save(model);
				}
			}
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteAddons(List<Integer> ids) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			genericDao.deleteAll(Tbl30Offerfanpriceaddon.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateOfferPumpPricing(Integer offerFanId) {

		boolean updated = false;
		try {
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);

			List<Tbl28SelectedPricing> tbl28SelectedPricings = (List<Tbl28SelectedPricing>) genericDao
					.getRecordsByParentId(Tbl28SelectedPricing.class, "tbl26OfferFan.id", offerFanId);

			List<Tbl30Offerfanpriceaddon> tbl30Offerfanpriceaddons = (List<Tbl30Offerfanpriceaddon>) genericDao
					.getRecordsByParentId(Tbl30Offerfanpriceaddon.class, "tbl26OfferFan.id", offerFanId);

			Double lineTotal = tbl28SelectedPricings.stream().filter(model -> model.getSubTotal() != null).mapToDouble(model -> model.getSubTotal())
					.sum();
			Double addonTotal = tbl30Offerfanpriceaddons.stream().filter(model -> model.getPrice() != null).mapToDouble(model -> model.getPrice())
					.sum();
			Double total = lineTotal + addonTotal;
			tbl26OfferFan.setTotal(total);
			updated = genericDao.update(tbl26OfferFan);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return updated;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateSelectedPricings(Integer offerFanId) {

		boolean updated = false;
		try {
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			double totalfactor = tbl26OfferFan.getTbl23OfferRev().getFactorTotal();

			List<Tbl28SelectedPricing> tbl28SelectedPricings = (List<Tbl28SelectedPricing>) genericDao
					.getRecordsByParentId(Tbl28SelectedPricing.class, "tbl26OfferFan.id", offerFanId);
			for (Tbl28SelectedPricing tbl28SelectedPricing : tbl28SelectedPricings) {

				double fac = 0.0, qty = 1, price = 0.0, discount = 0.0, margin = 0, userfactor = 1;
				if (tbl28SelectedPricing.getApplyFactor() == true)
					userfactor = totalfactor;

				qty = tbl28SelectedPricing.getQty() != null ? tbl28SelectedPricing.getQty() : 1;
				price = tbl28SelectedPricing.getPricePerQty() != null ? tbl28SelectedPricing.getPricePerQty() : 0.0;
				discount = tbl28SelectedPricing.getDiscount() != null ? tbl28SelectedPricing.getDiscount() * 100 : 0.0;
				margin = tbl28SelectedPricing.getMargin() != null ? tbl28SelectedPricing.getMargin() : 0.0;

				double result = (qty * price * userfactor) / (1 - (margin / 100.0));
				if (discount != 0.0)
					if (result != 0.0) {
						fac = (result * discount) / 100.0;
						result = result - fac;
					}
				tbl28SelectedPricing.setTotalOfferFactor(totalfactor);
				tbl28SelectedPricing.setSubTotal(result);
				updated = genericDao.update(tbl28SelectedPricing);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return updated;
	}
}

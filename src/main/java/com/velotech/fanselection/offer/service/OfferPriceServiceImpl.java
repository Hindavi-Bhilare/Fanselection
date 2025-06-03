
package com.velotech.fanselection.offer.service;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspose.cad.internal.ay.iF;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.OfferPriceAddonDto;
import com.velotech.fanselection.dtos.OfferPriceFactorDto;
import com.velotech.fanselection.dtos.OfferPumpDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl30OfferPriceAddon;
import com.velotech.fanselection.models.Tbl30OfferPriceFactor;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;

@Service
@Transactional
public class OfferPriceServiceImpl implements OfferPriceService {

	static Logger log = LogManager.getLogger(OfferPriceServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Override
	public ApplicationResponse getOfferTotal(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, offerRevId);
			Double offerTotal = tbl23OfferRev.getOfferTotal();
			
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, offerTotal);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getOfferPumpPrices(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<OfferPumpDto> dtos = new ArrayList<>();
			List<Tbl26OfferFan> tbl26OfferFans = (List<Tbl26OfferFan>) genericDao.getRecordsByParentId(Tbl26OfferFan.class, "tbl23OfferRev.id",
					offerRevId);
			for (Tbl26OfferFan tbl26OfferFan : tbl26OfferFans) {
				OfferPumpDto dto = new OfferPumpDto();
				dto.setId(tbl26OfferFan.getId());
				dto.setTagNo(tbl26OfferFan.getTagNo());
				dto.setTotal(tbl26OfferFan.getTotal());
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
	public ApplicationResponse getAddons(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl30OfferPriceAddon> models = (List<Tbl30OfferPriceAddon>) genericDao.getRecordsByParentId(Tbl30OfferPriceAddon.class,
					"tbl23OfferRev.id", offerRevId);

			List<OfferPriceAddonDto> dtos = new ArrayList<>();

			for (Tbl30OfferPriceAddon model : models) {
				OfferPriceAddonDto dto = new OfferPriceAddonDto();
				BeanUtils.copyProperties(model, dto);
				dto.setOfferRevId(offerRevId);
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
			List<OfferPriceAddonDto> dtos = Arrays.asList(mapper.readValue(requestPayload, OfferPriceAddonDto[].class));
			for (OfferPriceAddonDto dto : dtos) {

				if (dto.getId() > 0) {
					Tbl30OfferPriceAddon model = (Tbl30OfferPriceAddon) genericDao.getRecordById(Tbl30OfferPriceAddon.class, dto.getId());
					BeanUtils.copyProperties(dto, model);
					genericDao.update(model);
				} else {
					Tbl30OfferPriceAddon model = new Tbl30OfferPriceAddon();
					BeanUtils.copyProperties(dto, model);
					Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, dto.getOfferRevId());
					model.setTbl23OfferRev(tbl23OfferRev);
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
			genericDao.deleteAll(Tbl30OfferPriceAddon.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getFactors(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl30OfferPriceFactor> models = (List<Tbl30OfferPriceFactor>) genericDao.getRecordsByParentId(Tbl30OfferPriceFactor.class,
					"tbl23OfferRev.id", offerRevId);

			List<OfferPriceFactorDto> dtos = new ArrayList<>();
			for (Tbl30OfferPriceFactor model : models) {
				OfferPriceFactorDto dto = new OfferPriceFactorDto();
				BeanUtils.copyProperties(model, dto);
				dto.setOfferRevId(offerRevId);
				dtos.add(dto);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	@Override
	public ApplicationResponse saveFactors(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<OfferPriceFactorDto> dtos = Arrays.asList(mapper.readValue(requestPayload, OfferPriceFactorDto[].class));
			for (OfferPriceFactorDto dto : dtos) {

				if (dto.getId() > 0) {
					Tbl30OfferPriceFactor model = (Tbl30OfferPriceFactor) genericDao.getRecordById(Tbl30OfferPriceFactor.class, dto.getId());
					BeanUtils.copyProperties(dto, model);
					genericDao.update(model);
				} else {
					Tbl30OfferPriceFactor model = new Tbl30OfferPriceFactor();
					BeanUtils.copyProperties(dto, model);
					Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, dto.getOfferRevId());
					model.setTbl23OfferRev(tbl23OfferRev);
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
	public ApplicationResponse deleteFactors(List<Integer> ids) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			genericDao.deleteAll(Tbl30OfferPriceFactor.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateOfferPricing(Integer offerRevId) {

		boolean updated = false;
		try {
			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, offerRevId);

			List<Tbl26OfferFan> tbl26OfferFans = (List<Tbl26OfferFan>) genericDao.getRecordsByParentId(Tbl26OfferFan.class, "tbl23OfferRev.id",
					offerRevId);

			List<Tbl30OfferPriceAddon> tbl30OfferPriceAddons = (List<Tbl30OfferPriceAddon>) genericDao
					.getRecordsByParentId(Tbl30OfferPriceAddon.class, "tbl23OfferRev.id", offerRevId);

			Double lineTotal = tbl26OfferFans.stream().filter(model -> model.getTotal() != null).mapToDouble(model -> model.getTotal()).sum();
			Double addonTotal = tbl30OfferPriceAddons.stream().filter(model -> model.getPrice() != null).mapToDouble(model -> model.getPrice()).sum();
			Double offerTotal = lineTotal + addonTotal;
			tbl23OfferRev.setOfferTotal(offerTotal);
			updated = genericDao.update(tbl23OfferRev);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return updated;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateOfferFactor(Integer offerRevId) {

		boolean updated = false;
		try {
			Double totalFactor = 1d;
			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, offerRevId);

			List<Tbl30OfferPriceFactor> tbl30OfferPriceFactors = (List<Tbl30OfferPriceFactor>) genericDao
					.getRecordsByParentId(Tbl30OfferPriceFactor.class, "tbl23OfferRev.id", offerRevId);

			for (Tbl30OfferPriceFactor tbl30OfferPriceFactor : tbl30OfferPriceFactors) {
				totalFactor = totalFactor * tbl30OfferPriceFactor.getFactor();
			}

			tbl23OfferRev.setFactorTotal(totalFactor);
			updated = genericDao.update(tbl23OfferRev);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return updated;
	}

}

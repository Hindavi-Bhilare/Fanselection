
package com.velotech.fanselection.combogrid.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velotech.fanselection.combogrid.dao.CombogridDao;
import com.velotech.fanselection.dtos.CustomerMasterDto;
import com.velotech.fanselection.dtos.FrameMasterDto;
import com.velotech.fanselection.dtos.OfferMasterCombogridDto;
import com.velotech.fanselection.dtos.PrimeMoverMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.models.Tbl14FrameMaster;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl25CustomerMaster;
import com.velotech.fanselection.offer.dao.OfferMasterDao;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.SqlOrder;

@Service
@Transactional
public class CombogridServiceImpl implements CombogridService {

	static Logger log = LogManager.getLogger(CombogridServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CombogridDao dao;

	@Autowired
	private OfferMasterDao offerMasterDao;

	@Override
	public ApplicationResponse getPrimemoverMaster(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			applicationResponse = genericDao.getRecords(Tbl14PrimemoverMaster.class,
					GenericUtil.getConjuction(Tbl14PrimemoverMaster.class, requestWrapper.getSearchCriterias()),
					requestWrapper.getPagination());
			@SuppressWarnings("unchecked")
			List<Tbl14PrimemoverMaster> models = (List<Tbl14PrimemoverMaster>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<PrimeMoverMasterDto> dtos = getPrimemoverMaster(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<PrimeMoverMasterDto> getPrimemoverMaster(List<Tbl14PrimemoverMaster> models) {

		List<PrimeMoverMasterDto> dtos = new ArrayList<>();
		try {

			for (Tbl14PrimemoverMaster model : models) {
				PrimeMoverMasterDto dto = new PrimeMoverMasterDto();
				BeanUtils.copyProperties(model, dto);
				dto.setSpeedPower(model.getSpeed() + " - " + model.getPower());
				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getOfferMasters(String offerNo, String customerName, RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			applicationResponse = offerMasterDao.getOfferRevisions(null, null, null, offerNo, customerName, null, null,
					true, null, requestWrapper.getPagination(), null);

			List<Tbl23OfferRev> models = (List<Tbl23OfferRev>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<OfferMasterCombogridDto> dtos = getOfferMasters(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<OfferMasterCombogridDto> getOfferMasters(List<Tbl23OfferRev> models) {

		List<OfferMasterCombogridDto> dtos = new ArrayList<>();
		try {
			for (Tbl23OfferRev model : models) {
				OfferMasterCombogridDto dto = new OfferMasterCombogridDto();
				dto.setId(model.getId());
				dto.setOfferMasterId(model.getTbl23OfferMaster().getId());
				dto.setOfferNo(model.getTbl23OfferMaster().getOfferNo());
				dto.setOfferDate(model.getOfferdate());
				dto.setRev(model.getRev());
				dto.setOfferNoRev(model.getTbl23OfferMaster().getOfferNo() + "(" + model.getRev() + ")");
				if (model.getTbl25CustomerMaster() != null) {
					dto.setCustomerId(model.getTbl25CustomerMaster().getCustomerId());
					dto.setCustomerName(model.getTbl25CustomerMaster().getCustomerName());
				}
				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getCustomerMasters(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Conjunction conjunction = GenericUtil.getConjuction(Tbl25CustomerMaster.class,
					requestWrapper.getSearchCriterias());
			Pagination pagination = requestWrapper.getPagination();
			SqlOrder sqlOrder = new SqlOrder("customerId", "DESC");

			applicationResponse = genericDao.getRecords(Tbl25CustomerMaster.class, conjunction, pagination, sqlOrder);

			List<Tbl25CustomerMaster> models = (List<Tbl25CustomerMaster>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<CustomerMasterDto> dtos = getCustomerMasterData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<CustomerMasterDto> getCustomerMasterData(List<Tbl25CustomerMaster> models) {

		List<CustomerMasterDto> dtos = new ArrayList<>();
		try {

			for (Tbl25CustomerMaster model : models) {
				CustomerMasterDto dto = new CustomerMasterDto();
				BeanUtils.copyProperties(model, dto);
				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dtos;
	}

	@Override
	public ApplicationResponse getFrameMaster(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			SqlOrder sqlOrder = new SqlOrder("id", "ASC");

			applicationResponse = genericDao.getRecords(Tbl14FrameMaster.class,
					GenericUtil.getConjuction(Tbl14FrameMaster.class, requestWrapper.getSearchCriterias()),
					requestWrapper.getPagination(), sqlOrder);
			@SuppressWarnings("unchecked")
			List<Tbl14FrameMaster> models = (List<Tbl14FrameMaster>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<FrameMasterDto> dtos = getFrameMasterData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}
	private List<FrameMasterDto> getFrameMasterData(List<Tbl14FrameMaster> models) {

		List<FrameMasterDto> dtos = new ArrayList<>();
		try {
			for (Tbl14FrameMaster model : models) {
				FrameMasterDto dto = new FrameMasterDto();
				BeanUtils.copyProperties(model, dto);
				dto.setFrameMaster(model.getFrameSize() + "-" + model.getPole());
				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dtos;
	}


}

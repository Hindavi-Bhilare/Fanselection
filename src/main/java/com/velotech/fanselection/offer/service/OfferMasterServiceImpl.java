
package com.velotech.fanselection.offer.service;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.OfferRevDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl23OfferMaster;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl23OfferStatusHistory;
import com.velotech.fanselection.models.Tbl25CustomerMaster;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl80SegmentMaster;
import com.velotech.fanselection.offer.dao.OfferMasterDao;
import com.velotech.fanselection.offer.dto.OfferFanStatusDto;
import com.velotech.fanselection.offer.dto.OfferStatusHistoryDto;
import com.velotech.fanselection.offer.dto.OfferStatusUpdateDto;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class OfferMasterServiceImpl implements OfferMasterService {

	static Logger log = LogManager.getLogger(OfferMasterServiceImpl.class.getName());

	@Autowired
	private OfferMasterDao dao;

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Override
	public ApplicationResponse addRecord(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			OfferRevDto dto = new OfferRevDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, OfferRevDto.class);

			Tbl23OfferMaster tbl23OfferMaster = new Tbl23OfferMaster();
			tbl23OfferMaster.setOfferNo(dto.getOfferNo());

			List<Tbl23OfferMaster> offerMasterList = (List<Tbl23OfferMaster>) genericDao.findByParam(Tbl23OfferMaster.class, "offerNo",
					dto.getOfferNo());
			if (offerMasterList.size() == 0) {
				genericDao.save(tbl23OfferMaster);

				Tbl23OfferRev tbl23OfferRev = new Tbl23OfferRev();
				tbl23OfferRev.setTbl23OfferMaster(tbl23OfferMaster);
				tbl23OfferRev.setRev(dto.getRev());
				tbl23OfferRev.setActivetFlag(true);
				tbl23OfferRev.setStatus("In Preparation");
				tbl23OfferRev.setOfferdate(dto.getOfferdate());
				tbl23OfferRev.setShowCoveringLetter(false);
				tbl23OfferRev.setShowDiscount(false);
				tbl23OfferRev.setShowPrice(true);
				tbl23OfferRev.setShowPriceBreakUp(true);
				tbl23OfferRev.setShowTagPrice(true);
				tbl23OfferRev.setCoverLetterContent("");
				tbl23OfferRev.setTbl80SegmentMaster(new Tbl80SegmentMaster(dto.getSegmentId()));
				tbl23OfferRev.setTbl25CustomerMaster(new Tbl25CustomerMaster(dto.getCustomerMasterId()));
				tbl23OfferRev.setCurrency(dto.getCurrency());
				tbl23OfferRev.setTbl28CompanyMaster(new Tbl28CompanyMaster(dto.getCompanyMasterId()));
				tbl23OfferRev.setTbl52UsermasterBySalesPerson(new Tbl52Usermaster(dto.getSalesPersonLoginId()));
				tbl23OfferRev.setTbl52UsermasterByLoginId(new Tbl52Usermaster(velotechUtil.getLoginId()));
				tbl23OfferRev.setConsultant(dto.getConsultant());
				tbl23OfferRev.setEndUser(dto.getEndUser());
				tbl23OfferRev.setProject(dto.getProject());
				tbl23OfferRev.setContractor(dto.getContractor());
				tbl23OfferRev.setSuppliedBy(dto.getSuppliedBy());
				tbl23OfferRev.setReference(dto.getReference());
				tbl23OfferRev.setEnquiryDetails(dto.getEnquiryDetails());
				tbl23OfferRev.setEnquiryDate(dto.getEnquiryDate());
				tbl23OfferRev.setTenderDate(dto.getTenderDate());
				tbl23OfferRev.setTenderDueDate(dto.getTenderDueDate());

				genericDao.save(tbl23OfferRev);
				saveOfferStatusHistory(tbl23OfferRev);
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
			} else
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.OFFER_NO_EXIST_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private void saveOfferStatusHistory(Tbl23OfferRev tbl23OfferRev) {

		try {
			Tbl23OfferStatusHistory model = new Tbl23OfferStatusHistory();
			model.setTbl23OfferRev(tbl23OfferRev);
			model.setStatus("Decision Pending");
			genericDao.save(model);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getRecords(Integer id, Date startDate, Date endDate, String offerNo, String customerName, String salesPersonName,
			String project, boolean active, String status, RequestWrapper requestWrapper, String organisationDetails) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			applicationResponse = dao.getOfferRevisions(id, startDate, endDate, offerNo, customerName, salesPersonName, project, active, status,
					requestWrapper.getPagination(), organisationDetails);

			List<Tbl23OfferRev> models = (List<Tbl23OfferRev>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<OfferRevDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecord(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, offerRevId);
			List<Tbl23OfferRev> models = new ArrayList<>();
			models.add(tbl23OfferRev);
			long total = 1;
			List<OfferRevDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<OfferRevDto> getData(List<Tbl23OfferRev> tbl23OfferRevs) {

		List<OfferRevDto> dtos = new ArrayList<>();
		try {
			for (Tbl23OfferRev model : tbl23OfferRevs) {
				OfferRevDto dto = new OfferRevDto();
				BeanUtils.copyProperties(model, dto);

				dto.setOfferMasterId(model.getTbl23OfferMaster().getId());
				dto.setOfferNo(model.getTbl23OfferMaster().getOfferNo());

				if (model.getTbl25CustomerMaster() != null) {
					dto.setCustomerMasterId(model.getTbl25CustomerMaster().getCustomerId());
					dto.setCustomerMasterName(model.getTbl25CustomerMaster().getCustomerName());
				}

				if (model.getTbl28CompanyMaster() != null) {
					dto.setCompanyMasterId(model.getTbl28CompanyMaster().getId());
					dto.setCompanyMasterName(model.getTbl28CompanyMaster().getCompanyName());
				}

				if (model.getTbl52UsermasterBySalesPerson() != null) {
					dto.setSalesPersonLoginId(model.getTbl52UsermasterBySalesPerson().getLoginId());
					dto.setSalesPerson(model.getTbl52UsermasterBySalesPerson().getLoginId());
				}

				if (model.getTbl52UsermasterBySalesPerson().getTbl59Organisation() != null) {
					dto.setOrganisationDetails(model.getTbl52UsermasterBySalesPerson().getTbl59Organisation().getOrganisationDetails());
				}
				if (model.getTbl80SegmentMaster() != null) {
					dto.setSegmentId(model.getTbl80SegmentMaster().getId());
					dto.setSegment(model.getTbl80SegmentMaster().getSegment());
				}

				if (model.getTbl52UsermasterByLoginId() != null)
					dto.setUserName(model.getTbl52UsermasterByLoginId().getUserName());

				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getOfferPumps(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl26OfferFan> models = (List<Tbl26OfferFan>) genericDao.getRecordsByParentId(Tbl26OfferFan.class, "tbl23OfferRev.id",
					offerRevId);
			List<OfferFanStatusDto> dtos = new ArrayList<>();
			for (Tbl26OfferFan model : models) {
				OfferFanStatusDto dto = new OfferFanStatusDto();
				BeanUtils.copyProperties(model, dto);
				dto.setOfferRevId(offerRevId);
				dto.setFanModel(model.getTbl28SelectedFan().getFanModel());
				String flow = model.getTbl27RequirementsDp().getFlow() + " " + model.getTbl27RequirementsDp().getUomFlow().replaceFirst("m3", "m3");
				dto.setFlow(flow);
				String head = model.getTbl27RequirementsDp().getHead() + " " + model.getTbl27RequirementsDp().getUomHead();
				dto.setHead(head);
				dtos.add(dto);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, dtos.size());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getOfferStatusHistory(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl23OfferStatusHistory> models = (List<Tbl23OfferStatusHistory>) genericDao.getRecordsByParentId(Tbl23OfferStatusHistory.class,
					"tbl23OfferRev.id", offerRevId);
			List<OfferStatusHistoryDto> dtos = new ArrayList<>();
			for (Tbl23OfferStatusHistory model : models) {
				OfferStatusHistoryDto dto = new OfferStatusHistoryDto();
				BeanUtils.copyProperties(model, dto);
				dto.setOfferRevId(offerRevId);
				dtos.add(dto);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, dtos.size());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateOfferStatus(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			OfferStatusUpdateDto dto = mapper.readValue(requestPayload, OfferStatusUpdateDto.class);
			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, dto.getOfferRevId());
			tbl23OfferRev.setStatus(dto.getStatus());
			tbl23OfferRev.setOrderNo(dto.getOrderNo());
			genericDao.update(tbl23OfferRev);

			Tbl23OfferStatusHistory tbl23OfferStatusHistory = new Tbl23OfferStatusHistory();
			tbl23OfferStatusHistory.setTbl23OfferRev(tbl23OfferRev);
			tbl23OfferStatusHistory.setStatus(dto.getStatus());
			genericDao.save(tbl23OfferStatusHistory);

			List<OfferFanStatusDto> offerFans = dto.getOfferFans();
			for (OfferFanStatusDto offerFan : offerFans) {
				Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFan.getId());
				tbl26OfferFan.setStatus(offerFan.getStatus());
				tbl26OfferFan.setReason(offerFan.getReason());
				tbl26OfferFan.setLostTo(offerFan.getLostTo());
				tbl26OfferFan.setCustomerOrderRef(offerFan.getCustomerOrderRef());
				genericDao.update(tbl26OfferFan);
			}

			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}

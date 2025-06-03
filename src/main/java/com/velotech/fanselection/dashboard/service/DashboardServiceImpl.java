
package com.velotech.fanselection.dashboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.velotech.fanselection.dashboard.dao.DashboardDao;
import com.velotech.fanselection.dashboard.dto.OfferDto;
import com.velotech.fanselection.dashboard.dto.OfferStatusCount;
import com.velotech.fanselection.dashboard.dto.SearchCount;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

	static Logger log = LogManager.getLogger(DashboardServiceImpl.class.getName());

	@Autowired
	private DashboardDao dao;

	@Override
	public ApplicationResponse getLast10DaysSearchCount() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			List<SearchCount> searchCounts = dao.getLast10DaysSearchCount();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, searchCounts);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getOffersCountStatusWise(Date startDate, Date endDate) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			Map<String, Long> statusCounts = dao.getOffersCountStatusWise(startDate, endDate);
			List<OfferStatusCount> ans = setDefaultStatus(statusCounts);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, ans);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<OfferStatusCount> setDefaultStatus(Map<String, Long> statusCounts) {

		List<OfferStatusCount> ans = new ArrayList<>();
		try {
			ans.add(new OfferStatusCount("In Preparation", 0));
			ans.add(new OfferStatusCount("Won", 0));
			ans.add(new OfferStatusCount("Lost", 0));
			ans.add(new OfferStatusCount("Partially Won", 0));
			ans.add(new OfferStatusCount("Offer Submitted", 0));
			ans.add(new OfferStatusCount("Cancelled", 0));

			for (OfferStatusCount offerStatusCount : ans) {
				if (statusCounts.containsKey(offerStatusCount.getStatus()))
					offerStatusCount.setCount(statusCounts.get(offerStatusCount.getStatus()));
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public ApplicationResponse getLast10DaysOffers() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl23OfferRev> models = dao.getLast10DaysOffers();
			long total = models.size();

			List<OfferDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<OfferDto> getData(List<Tbl23OfferRev> models) {

		List<OfferDto> dtos = new ArrayList<>();
		try {
			for (Tbl23OfferRev model : models) {
				OfferDto dto = new OfferDto();
				dto.setId(model.getId());
				dto.setOfferDate(model.getOfferdate());
				if (model.getTbl23OfferMaster() != null)
					dto.setOfferNo(model.getTbl23OfferMaster().getOfferNo());
				dto.setRev(model.getRev());
				if (model.getTbl25CustomerMaster() != null)
					dto.setCustomer(model.getTbl25CustomerMaster().getCustomerName());
				dto.setProject(model.getProject());
				dto.setValue(model.getOfferTotal());
				dto.setStatus(model.getStatus());
				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dtos;
	}

	@Override
	public ApplicationResponse getLast12MonthsOffersCount() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<SearchCount> searchCounts = dao.getLast12MonthsOffersCount();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, searchCounts);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}

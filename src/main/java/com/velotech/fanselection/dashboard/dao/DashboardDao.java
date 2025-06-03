
package com.velotech.fanselection.dashboard.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.velotech.fanselection.dashboard.dto.SearchCount;
import com.velotech.fanselection.models.Tbl23OfferRev;

public interface DashboardDao {

	long getTotalOffersPrepared();

	long getTotalOfferConvertedToOrder();

	List<SearchCount> getLast10DaysSearchCount();

	List<Tbl23OfferRev> getLast10DaysOffers();

	Map<String, Long> getOffersCountStatusWise(Date startDate, Date endDate);

	List<SearchCount> getLast12MonthsOffersCount();

}

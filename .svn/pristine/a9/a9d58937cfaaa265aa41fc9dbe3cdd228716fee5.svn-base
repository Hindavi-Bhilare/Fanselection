
package com.velotech.fanselection.dashboard.dao;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.dashboard.dto.OfferStatusCount;
import com.velotech.fanselection.dashboard.dto.SearchCount;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl90SearchHistory;
import com.velotech.fanselection.utils.CommonList;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.VelotechUtil;

@Repository
public class DashboardDaoImpl extends HibernateSession implements DashboardDao {

	static Logger log = LogManager.getLogger(DashboardDaoImpl.class.getName());

	@Autowired
	private CommonList commonList;

	@Autowired
	private VelotechUtil velotechUtil;

	private Criteria getOffersCriteria() {

		Criteria criteria = getSessionWFilter().createCriteria(Tbl23OfferRev.class);
		if (!velotechUtil.isAdminUser()) {
			List<String> loginIds = commonList.getSubordinatesIds();
			criteria.add(Restrictions.in("tbl52UsermasterByLoginId.loginId", loginIds));
			List<Integer> offerIds = commonList.getUsersSharedOfferIds();
			if (!offerIds.isEmpty())
				criteria.add(Restrictions.in("id", offerIds));
		}
		return criteria;
	}

	@Override
	public long getTotalOffersPrepared() {

		long totalOffersPrepared = 0;
		try {

			Criteria criteria = getOffersCriteria();
			criteria.setProjection(Projections.count("id"));
			totalOffersPrepared = (long) criteria.uniqueResult();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return totalOffersPrepared;
	}

	@Override
	public long getTotalOfferConvertedToOrder() {

		long totalOfferConvertedToOrder = 0;
		try {

			Criteria criteria = getOffersCriteria();
			criteria.setProjection(Projections.count("id"));
			criteria.add(Restrictions.eq("isOrder", true));
			totalOfferConvertedToOrder = (long) criteria.uniqueResult();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return totalOfferConvertedToOrder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SearchCount> getLast10DaysSearchCount() {

		List<SearchCount> searchCounts = new ArrayList<>();
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			Date startDate = dateFormat.parse(LocalDate.now().minusDays(10L).format(formatter));
			Date endDate = dateFormat.parse(LocalDate.now().format(formatter));

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.groupProperty("createdDate"), "day");
			projectionList.add(Projections.count("id"), "count");

			Criteria criteria = getSessionWFilter().createCriteria(Tbl90SearchHistory.class);
			criteria.add(Restrictions.between("createdDate", startDate, endDate));
			criteria.setProjection(projectionList);
			criteria.setResultTransformer(Transformers.aliasToBean(SearchCount.class));
			searchCounts = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return searchCounts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbl23OfferRev> getLast10DaysOffers() {

		List<Tbl23OfferRev> tbl23OfferRevs = new ArrayList<>();
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			Date startDate = dateFormat.parse(LocalDate.now().minusDays(10L).format(formatter));
			Date endDate = dateFormat.parse(LocalDate.now().format(formatter));

			Criteria criteria = getOffersCriteria();
			criteria.add(Restrictions.between("offerdate", startDate, endDate));
			tbl23OfferRevs = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl23OfferRevs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Long> getOffersCountStatusWise(Date startDate, Date endDate) {

		Map<String, Long> ans = new HashMap<>();
		try {
			List<OfferStatusCount> offerStatusCounts = new ArrayList<>();
			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.groupProperty("status"), "status");
			projectionList.add(Projections.count("id"), "count");

			Criteria criteria = getOffersCriteria();
			criteria.setProjection(projectionList);
			if (startDate != null && endDate != null)
				criteria.add(Restrictions.between("offerdate", startDate, endDate));
			criteria.setResultTransformer(Transformers.aliasToBean(OfferStatusCount.class));
			offerStatusCounts = criteria.list();
			for (OfferStatusCount offerStatusCount : offerStatusCounts) {
				ans.put((String) offerStatusCount.getStatus(), (Long) offerStatusCount.getCount());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SearchCount> getLast12MonthsOffersCount() {

		List<SearchCount> searchCounts = new ArrayList<>();
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String startDate = LocalDate.now().minusMonths(12L).format(formatter);
			String endDate = LocalDate.now().format(formatter);
			String company = velotechUtil.getCompany();
			StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT count (id) AS count,MONTH (createdDate) AS month,YEAR (createdDate) AS year\n");
			queryString.append("FROM tbl_23_offerRev\n");
			queryString.append("WHERE company = '" + company + "' AND createdDate BETWEEN '" + startDate + "' AND '" + endDate + "' \n");
			queryString.append("GROUP BY MONTH (createdDate),YEAR(createdDate)\n");
			queryString.append("ORDER BY YEAR(createdDate),MONTH (createdDate)");

			SQLQuery sqlQuery = getSession().createSQLQuery(queryString.toString());
			sqlQuery.setResultTransformer(Transformers.aliasToBean(SearchCount.class));
			searchCounts = sqlQuery.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return searchCounts;
	}

}

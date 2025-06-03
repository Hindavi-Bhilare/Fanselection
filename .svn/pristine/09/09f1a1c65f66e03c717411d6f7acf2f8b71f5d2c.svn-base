
package com.velotech.fanselection.admin.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl57LoginHistory;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.SqlOrder;

@Repository
public class LoginHistoryDaoImpl extends HibernateSession implements LoginHistoryDao {

	static Logger log = LogManager.getLogger(LoginHistoryDaoImpl.class.getName());

	@SuppressWarnings("unused")
	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) {

		ApplicationResponse response = new ApplicationResponse();
		Tbl57LoginHistory tbl57LoginHistory = null;
		Object data = null;
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl57LoginHistory.class);

			if (requestWrapper.getSearchProperty().equals("userMasterloginId") && !requestWrapper.getSearchValue().equals("")) {
				criteria.add(Restrictions.like("tbl52Usermaster.loginId", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));
				criteria.createAlias("tbl52Usermaster", "tbl52Usermaster");
			}

			if (requestWrapper.getSearchProperty().equals("ipAddress") && !requestWrapper.getSearchValue().equals(""))
				criteria.add(Restrictions.like("ipAddress", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));

			if (requestWrapper.getStartDate() != null && requestWrapper.getEndDate() != null) {
				Date endDate = requestWrapper.getEndDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				calendar.add(Calendar.HOUR_OF_DAY, 23);
				calendar.add(Calendar.SECOND, 59);
				endDate = calendar.getTime();
				criteria.add(Restrictions.between("loginDate", requestWrapper.getStartDate(), endDate));

			}
			SqlOrder sqlOrder = requestWrapper.getSqlOrder();

			if (sqlOrder != null) {
				if (sqlOrder.getSortProperty().equals("userMasterloginId"))
					sqlOrder.setSortProperty("tbl52Usermaster.loginId");

				if (sqlOrder.getSortDirection().equalsIgnoreCase("ASC"))
					criteria.addOrder(Order.asc(sqlOrder.getSortProperty()));
				else
					criteria.addOrder(Order.desc(sqlOrder.getSortProperty()));
			}

			ScrollableResults results = criteria.scroll();
			results.last();
			long total = results.getRowNumber() + 1;

			results.close();
			if (requestWrapper.getPagination() != null)
				criteria.setFirstResult(requestWrapper.getPagination().getStart()).setMaxResults(requestWrapper.getPagination().getLimit());
			data = criteria.list();
			response.setData(data);
			response.setTotal(total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public List<Object> getCountRecords(RequestWrapper requestWrapper) {

		ApplicationResponse response = new ApplicationResponse();
		Tbl57LoginHistory tbl57LoginHistory = null;
		List<Object> data = null;
		try {

			Conjunction ds = Restrictions.conjunction();
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.groupProperty("tbl52Usermaster.loginId"));
			projList.add(Projections.count("tbl52Usermaster.loginId"), "count");

			Criteria criteria = getSessionWFilter().createCriteria(Tbl57LoginHistory.class).add(ds);
			criteria.setProjection(projList);

			if (requestWrapper.getSearchProperty().equals("userMasterloginId") && !requestWrapper.getSearchValue().equals("")) {
				criteria.add(Restrictions.like("tbl52Usermaster.loginId", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));
				criteria.createAlias("tbl52Usermaster", "tbl52Usermaster");

			}

			if (requestWrapper.getSearchProperty().equals("ipAddress") && !requestWrapper.getSearchValue().equals(""))
				criteria.add(Restrictions.like("ipAddress", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));

			if (requestWrapper.getStartDate() != null && requestWrapper.getEndDate() != null) {
				Date endDate = requestWrapper.getEndDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				calendar.add(Calendar.HOUR_OF_DAY, 23);
				calendar.add(Calendar.SECOND, 59);
				endDate = calendar.getTime();
				criteria.add(Restrictions.between("loginDate", requestWrapper.getStartDate(), endDate));
			}

			ScrollableResults results = criteria.scroll();
			results.last();
			long total = results.getRowNumber() + 1;

			results.close();
			if (requestWrapper.getPagination() != null)
				criteria.setFirstResult(requestWrapper.getPagination().getStart()).setMaxResults(requestWrapper.getPagination().getLimit());
			data = criteria.list();
			response.setData(data);
			response.setTotal(total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

	@Override
	public ApplicationResponse getExcelRecords(RequestWrapper requestWrapper) {

		ApplicationResponse response = new ApplicationResponse();
		Tbl57LoginHistory tbl57LoginHistory = null;
		Object data = null;
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl57LoginHistory.class);

			if (requestWrapper.getSearchProperty().equals("userMasterloginId") && !requestWrapper.getSearchValue().equals("")) {
				criteria.add(Restrictions.like("tbl52Usermaster.loginId", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));
				criteria.createAlias("tbl52Usermaster", "tbl52Usermaster");
			}

			if (requestWrapper.getSearchProperty().equals("ipAddress") && !requestWrapper.getSearchValue().equals(""))
				criteria.add(Restrictions.like("ipAddress", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));

			if (requestWrapper.getStartDate() != null && requestWrapper.getEndDate() != null) {
				Date endDate = requestWrapper.getEndDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				calendar.add(Calendar.HOUR_OF_DAY, 23);
				calendar.add(Calendar.SECOND, 59);
				endDate = calendar.getTime();
				criteria.add(Restrictions.between("loginDate", requestWrapper.getStartDate(), endDate));
			}
			data = criteria.list();
			response.setData(data);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}
}

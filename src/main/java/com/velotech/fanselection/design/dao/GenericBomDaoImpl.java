
package com.velotech.fanselection.design.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl03GenericBom;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.SqlOrder;

@Repository
public class GenericBomDaoImpl extends HibernateSession implements GenericBomDao {

	static Logger log = LogManager.getLogger(GenericBomDaoImpl.class.getName());

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl03GenericBom.class);
			criteria.createAlias("tbl01Fantype", "tbl01Fantype");
			if (requestWrapper.getSearchProperty().equals("fanSeries") && !requestWrapper.getSearchValue().equals("")) {
				criteria.add(Restrictions.like("tbl01Fantype.series", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));
			}

			if (requestWrapper.getSearchProperty().equals("mocStd") && !requestWrapper.getSearchValue().equals(""))
				criteria.add(Restrictions.like("mocStd", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));

			SqlOrder sqlOrder = requestWrapper.getSqlOrder();

			if (sqlOrder != null) {
				if (sqlOrder.getSortProperty().equals("fanSeries"))
					sqlOrder.setSortProperty("tbl01Fantype.series");

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
	public ApplicationResponse getExcelRecords(RequestWrapper requestWrapper) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl03GenericBom.class);
			criteria.createAlias("tbl01Fantype", "tbl01Fantype");
			if (requestWrapper.getSearchProperty().equals("fanSeries") && !requestWrapper.getSearchValue().equals("")) {

				criteria.add(Restrictions.like("tbl01Fantype.series", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));
			}
			if (requestWrapper.getSearchProperty().equals("mocStd"))
				criteria.add(Restrictions.like("mocStd", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));

			data = criteria.list();
			response.setData(data);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}

}

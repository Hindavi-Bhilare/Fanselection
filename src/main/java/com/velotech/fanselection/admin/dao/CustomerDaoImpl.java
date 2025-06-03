
package com.velotech.fanselection.admin.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl25CustomerMaster;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.CommonList;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Repository
public class CustomerDaoImpl extends HibernateSession implements CustomerDao {

	static Logger log = LogManager.getLogger(CustomerDaoImpl.class.getName());

	@Autowired
	private VelotechUtil VelotechUtil;

	@Autowired
	private CommonList commonList;

	@Override
	public ApplicationResponse getCustomerRecords(RequestWrapper requestWrapper) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;

		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl25CustomerMaster.class);

			if (requestWrapper.getSearchProperty().equals("customerName") && !requestWrapper.getSearchValue().equals(""))
				criteria.add(Restrictions.like("customerName", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));

			if (requestWrapper.getSearchProperty().equals("city") && !requestWrapper.getSearchValue().equals(""))
				criteria.add(Restrictions.like("city", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));

			if (requestWrapper.getSearchProperty().equals("state") && !requestWrapper.getSearchValue().equals(""))
				criteria.add(Restrictions.like("state", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));

			if (requestWrapper.getSearchProperty().equals("country") && !requestWrapper.getSearchValue().equals(""))
				criteria.add(Restrictions.like("country", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));

			if (requestWrapper.getSearchProperty().equals("pincode") && !requestWrapper.getSearchValue().equals(""))
				criteria.add(Restrictions.like("pincode", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));

			if (requestWrapper.getSearchProperty().equals("type") && !requestWrapper.getSearchValue().equals(""))
				criteria.add(Restrictions.like("type", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));

			if (requestWrapper.getSearchProperty().equals("category") && !requestWrapper.getSearchValue().equals(""))
				criteria.add(Restrictions.like("category", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));

			if (requestWrapper.getSearchProperty().equals("sap_code") && !requestWrapper.getSearchValue().equals(""))
				criteria.add(Restrictions.like("sap_code", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));

			if (!VelotechUtil.isAdminUser()) {
				criteria.add(Restrictions.in("tbl28CompanyMaster.id", commonList.getUserCompanyIds()));
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

}

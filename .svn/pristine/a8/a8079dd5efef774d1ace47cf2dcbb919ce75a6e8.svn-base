
package com.velotech.fanselection.admin.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.Pagination;

@Repository
public class UserMasterDaoImpl extends HibernateSession implements UserMasterDao {

	static Logger log = LogManager.getLogger(UserMasterDaoImpl.class.getName());

	@Override
	public ApplicationResponse getRecords(String searchProperty, String searchValue, Pagination pagination) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl52Usermaster.class);
			if (searchValue != null) {
				if (searchProperty.equals("userType")) {
					criteria.add(Restrictions.like("tbl50Usertype.userType", searchValue, MatchMode.ANYWHERE));
					criteria.createAlias("tbl50Usertype", "tbl50Usertype");
				} else if (searchProperty.equals("organisationDetails")) {
					criteria.add(Restrictions.like("tbl59Organisation.organisationDetails", searchValue, MatchMode.ANYWHERE));
					criteria.createAlias("tbl59Organisation", "tbl59Organisation");
				} else if (searchProperty.equals("email")) {
					criteria.add(Restrictions.like("email", searchValue));
				} else
					criteria.add(Restrictions.like(searchProperty, searchValue, MatchMode.ANYWHERE));
			}
			ScrollableResults results = criteria.scroll();
			results.last();
			long total = results.getRowNumber() + 1;
			results.close();
			if (pagination != null)
				criteria.setFirstResult(pagination.getStart()).setMaxResults(pagination.getLimit());
			data = criteria.list();
			response.setData(data);
			response.setTotal(total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}

}

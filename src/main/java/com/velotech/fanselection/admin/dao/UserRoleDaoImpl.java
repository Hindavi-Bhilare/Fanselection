
package com.velotech.fanselection.admin.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl53Userrole;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.Pagination;

@Repository
public class UserRoleDaoImpl extends HibernateSession implements UserRoleDao {

	static Logger log = LogManager.getLogger(UserRoleDaoImpl.class.getName());

	@Override
	public ApplicationResponse getRecords(String searchProperty, String searchValue, Pagination pagination) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl53Userrole.class);
			criteria.add(Restrictions.eq(searchProperty, searchValue));

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

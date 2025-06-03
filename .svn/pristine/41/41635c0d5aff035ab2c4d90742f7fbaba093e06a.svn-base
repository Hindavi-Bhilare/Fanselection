package com.velotech.fanselection.admin.dao;

import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl90JsonMaster;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.RequestWrapper;

@Repository
public class JsonMasterDaoImpl  extends HibernateSession implements JsonMasterDao {

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) {
		ApplicationResponse response = new ApplicationResponse();
		Criteria criteria = getSessionWFilter().createCriteria(Tbl90JsonMaster.class);
		Conjunction conjunction = Restrictions.conjunction();
		if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
			if (requestWrapper.getSearchProperty() != null && requestWrapper.getSearchProperty().equals("parameterName")) {
				criteria.createAlias("tbl90ParameterMaster", "tTbl90ParameterMaster");
				conjunction.add(Restrictions.like("tbl90ParameterMaster.parameter", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));
			}
		}
		criteria.add(conjunction);

		ScrollableResults results = criteria.scroll();
		results.last();
		long total = results.getRowNumber() + 1;
		results.close();
		
		Object data = criteria.list();
		response.setData(data);
		response.setTotal(total);

		return response;
	}
}
	




package com.velotech.fanselection.admin.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl90DocumentMaster;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.RequestWrapper;

@Repository
public class DocumentMasterDaoImpl extends HibernateSession implements DocumentMasterDao {

	static Logger log = LogManager.getLogger(DocumentMasterDaoImpl.class.getName());

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;

		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl90DocumentMaster.class);

			if (!requestWrapper.getSearchProperty().equals("All")
					&& !requestWrapper.getSearchProperty().equals(".jpg/.png"))
				criteria.add(Restrictions.like("fileName", requestWrapper.getSearchProperty(), MatchMode.END));
			else if (requestWrapper.getSearchProperty().equals(".jpg/.png")) {
				criteria.add(Restrictions.or(Restrictions.like("fileName", ".jpg", MatchMode.END),
						Restrictions.like("fileName", ".png", MatchMode.END)));

			}
			if (requestWrapper.getSearchValue() != null && !requestWrapper.getSearchValue().equals(""))
				criteria.add(
						Restrictions.like("fileName", (String) requestWrapper.getSearchValue(), MatchMode.ANYWHERE));
			long total = 0;
			if (requestWrapper.getDisplayField()==null || !requestWrapper.getDisplayField().equals("Combo")) {
				ScrollableResults results = criteria.scroll();
				results.last();
				total = results.getRowNumber() + 1;

				results.close();
				if (requestWrapper.getPagination() != null)
					criteria.setFirstResult(requestWrapper.getPagination().getStart())
							.setMaxResults(requestWrapper.getPagination().getLimit());
			}
			data = criteria.list();
			response.setData(data);
			response.setTotal(total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}

}

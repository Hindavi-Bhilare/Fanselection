
package com.velotech.fanselection.combogrid.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.CommonList;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.VelotechUtil;

@Repository
public class CombogridDaoImpl extends HibernateSession implements CombogridDao {

	static Logger log = LogManager.getLogger(CombogridDaoImpl.class.getName());

	@Autowired
	private CommonList commonList;

	@Autowired
	private VelotechUtil velotechUtil;

	@Override
	public ApplicationResponse getOfferMasters(String offerNo, String customerName, Pagination pagination) {

		ApplicationResponse response = new ApplicationResponse();
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl23OfferRev.class);
			criteria.add(Restrictions.eq("activetFlag", true));

			if (!velotechUtil.isAdminUser()) {
				List<String> loginIds = commonList.getSubordinatesIds();
				criteria.add(Restrictions.in("tbl52UsermasterByLoginId.loginId", loginIds));
			}

			if (offerNo != null) {
				criteria.createAlias("tbl23OfferMaster", "tbl23OfferMaster");
				criteria.add(Restrictions.like("tbl23OfferMaster.offerNo", offerNo, MatchMode.ANYWHERE));
			}

			if (customerName != null) {
				criteria.createAlias("tbl25CustomerMaster", "tbl25CustomerMaster");
				criteria.add(Restrictions.like("tbl25CustomerMaster.customerName", customerName, MatchMode.ANYWHERE));
			}

			ScrollableResults results = criteria.scroll();
			results.last();
			long total = results.getRowNumber() + 1;
			results.close();

			if (pagination != null)
				criteria.setFirstResult(pagination.getStart()).setMaxResults(pagination.getLimit());

			Object data = criteria.list();
			response.setData(data);
			response.setTotal(total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}
}

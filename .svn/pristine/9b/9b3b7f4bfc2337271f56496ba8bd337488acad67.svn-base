
package com.velotech.fanselection.design.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.RequestWrapper;

@Repository
public class PrimeMoverMasterDaoImpl extends HibernateSession implements PrimeMoverMasterDao {

	static Logger log = LogManager.getLogger(PrimeMoverMasterDaoImpl.class.getName());

	@Override
	public ApplicationResponse getPrimemoverMasterData(String frameMaster, Double powermin, Double powermax,
			Double speed,Integer motorTypeId,Conjunction conjunction,RequestWrapper requestWrapper) {
		ApplicationResponse response = new ApplicationResponse();

		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl14PrimemoverMaster.class);

			if (frameMaster != null && !frameMaster.isEmpty()) {
				criteria.createAlias("tbl14FrameMaster", "tbl14FrameMaster");
				criteria.add(Restrictions.like("tbl14FrameMaster.frameSize", frameMaster, MatchMode.ANYWHERE));
			}

			if (speed != null)
				criteria.add(Restrictions.eq("speed", speed));

			if ((powermin != null) && (powermax != null)) {
				criteria.add(Restrictions.between("power", powermin, powermax));

			}
			if ((motorTypeId != null) ) {
				criteria.createAlias("tbl1401Motortype", "tbl1401Motortype");
				criteria.add(Restrictions.like("tbl1401Motortype.id", motorTypeId));

			}
			if (conjunction != null ) {
				criteria.add((Conjunction)conjunction);
			}

			ScrollableResults results = criteria.scroll();
			results.last();
			long total = results.getRowNumber() + 1;
			results.close();

			criteria.addOrder(Order.desc("primemoverId"));

			if (requestWrapper.getPagination() != null)
				criteria.setFirstResult(requestWrapper.getPagination().getStart()).setMaxResults(requestWrapper.getPagination().getLimit());

			Object data = criteria.list();
			response.setData(data);
			response.setTotal(total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}
}

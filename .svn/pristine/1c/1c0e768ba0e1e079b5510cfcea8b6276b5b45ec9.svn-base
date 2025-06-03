
package com.velotech.fanselection.offer.dao;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
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
public class OfferMasterDaoImpl extends HibernateSession implements OfferMasterDao {

	static Logger log = LogManager.getLogger(OfferMasterDaoImpl.class.getName());

	@Autowired
	private CommonList commonList;

	@Autowired
	private VelotechUtil velotechUtil;

	@Override
	public ApplicationResponse getOfferRevisions(Integer id, Date startDate, Date endDate, String offerNo, String customerName,
			String salesPersonName, String project, boolean active, String status, Pagination pagination, String organisationDetails) {

		ApplicationResponse response = new ApplicationResponse();
		try {
			Disjunction disjunction = Restrictions.disjunction();
			if (!velotechUtil.isAdminUser()) {
				List<String> loginIds = commonList.getSubordinatesIds();
				if (loginIds.size() > 0)
					disjunction.add(Restrictions.in("tbl52UsermasterByLoginId.loginId", loginIds));
				// criteria.add(Restrictions.in("tbl52UsermasterByLoginId.loginId",
				// loginIds));
				List<Integer> offerIds = commonList.getUsersSharedOfferIds();
				if (offerIds != null && !offerIds.isEmpty())
					disjunction.add(Restrictions.in("id", offerIds));
				// criteria.add(Restrictions.in("id", offerIds));

				if (velotechUtil.isMarketingHead()) {
					List<String> loginIdList = commonList.getUserIdsForOrganisation(velotechUtil.getLoginId());
					if (loginIdList.size() > 0)
						disjunction.add(Restrictions.in("tbl52UsermasterByLoginId.loginId", loginIdList));
				}
			}

			Criteria criteria = getSessionWFilter().createCriteria(Tbl23OfferRev.class).add(disjunction);
			if (id != null)
				criteria.add(Restrictions.eq("id", id));
			if (active)
				criteria.add(Restrictions.eq("activetFlag", true));
			if (startDate != null && endDate != null)
				criteria.add(Restrictions.between("offerdate", startDate, endDate));
			if (offerNo != null && !offerNo.isEmpty()) {
				criteria.createAlias("tbl23OfferMaster", "tbl23OfferMaster");
				criteria.add(Restrictions.like("tbl23OfferMaster.offerNo", offerNo, MatchMode.ANYWHERE));
			}
			if (customerName != null && !customerName.isEmpty()) {
				criteria.createAlias("tbl25CustomerMaster", "tbl25CustomerMaster");
				criteria.add(Restrictions.like("tbl25CustomerMaster.customerName", customerName, MatchMode.ANYWHERE));
			}
			criteria.createAlias("tbl52UsermasterBySalesPerson", "tbl52UsermasterBySalesPerson");
			if (salesPersonName != null && !salesPersonName.isEmpty()) {

				criteria.add(Restrictions.like("tbl52UsermasterBySalesPerson.userName", salesPersonName, MatchMode.ANYWHERE));
			}
			if (organisationDetails != null && !organisationDetails.isEmpty()) {

				criteria.createAlias("tbl52UsermasterBySalesPerson.tbl59Organisation", "tbl59Organisation");
				criteria.add(Restrictions.like("tbl59Organisation.organisationDetails", organisationDetails, MatchMode.ANYWHERE));
			}
			if (project != null && !project.isEmpty())
				criteria.add(Restrictions.like("project", project, MatchMode.ANYWHERE));
			if (status != null && !status.isEmpty() && !status.equals("All"))
				criteria.add(Restrictions.eq("status", status));

			ScrollableResults results = criteria.scroll();
			results.last();
			long total = results.getRowNumber() + 1;
			results.close();

			criteria.addOrder(Order.desc("id"));
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

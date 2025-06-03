
package com.velotech.fanselection.mis.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.mis.dtos.MISOfferSearchDto;
import com.velotech.fanselection.models.views.MisOffer;
import com.velotech.fanselection.utils.CommonList;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.VelotechUtil;

@Repository
public class MISOfferDaoImpl extends HibernateSession implements MISOfferDao {

	static Logger log = LogManager.getLogger(MISOfferDaoImpl.class.getName());

	@Autowired
	CommonList commonList;

	@Autowired
	VelotechUtil velotechUtil;

	@SuppressWarnings("unchecked")
	@Override
	public List<MisOffer> getMISOffer(MISOfferSearchDto searchDto) {

		List<MisOffer> data = new ArrayList<MisOffer>();
		try {
			Criteria criteria = getSessionWFilter().createCriteria(MisOffer.class);
			criteria.add(Restrictions.eq("id.activeFlag", true));
			criteria.add(Restrictions.eq("id.standardOffer", false));

			if (!searchDto.getCustomerName().equals(""))
				criteria.add(Restrictions.like("id.customerName", searchDto.getCustomerName(), MatchMode.ANYWHERE));
			if (!searchDto.getProject().equals(""))
				criteria.add(Restrictions.like("id.project", searchDto.getProject(), MatchMode.ANYWHERE));
			if (!searchDto.getSalesPerson().equals(""))
				criteria.add(Restrictions.like("id.salesPerson", searchDto.getSalesPerson(), MatchMode.ANYWHERE));

			if (!searchDto.getOfferStatus().equalsIgnoreCase(""))
				criteria.add(Restrictions.eq("id.offerStatus", searchDto.getOfferStatus()));
			if (!searchDto.getSegment().equalsIgnoreCase(""))
				criteria.add(Restrictions.eq("id.segment", searchDto.getSegment()));

			if (!searchDto.getUser().equalsIgnoreCase(""))
				criteria.add(Restrictions.eq("id.loginId", searchDto.getUser()));
			else {
				if (!velotechUtil.isAdminUser())
					criteria.add(Restrictions.in("id.loginId", commonList.getSubordinatesIds()));
			}

			data = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

}

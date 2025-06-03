package com.velotech.fanselection.offer.dao;

import java.util.ArrayList;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.utils.HibernateSession;

@Repository
public class DownloadOfferDaoImpl extends HibernateSession implements DownloadOfferDao{

	static Logger log = LogManager.getLogger(DownloadOfferDaoImpl.class.getName());
	
	@Override
	public List<Tbl26OfferFan> getDownloadOfferData(Integer offerRevId) {

		List<Tbl26OfferFan> offerpumpList = new ArrayList<Tbl26OfferFan>();
		try {

			ProjectionList proList = Projections.projectionList();
			proList.add(Projections.property("id"), "id");
			proList.add(Projections.property("tagNo"), "tagNo");
			proList.add(Projections.property("tbl27RequirementsDp.system"), "createdBy");

			offerpumpList = getSessionWFilter().createCriteria(Tbl26OfferFan.class).add(Restrictions.eq("tbl23OfferRev.id", offerRevId))
					.createAlias("tbl27RequirementsDp", "tbl27RequirementsDp", Criteria.INNER_JOIN).setProjection(proList)
					.setResultTransformer(Transformers.aliasToBean(Tbl26OfferFan.class)).list();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return offerpumpList;
	}
}

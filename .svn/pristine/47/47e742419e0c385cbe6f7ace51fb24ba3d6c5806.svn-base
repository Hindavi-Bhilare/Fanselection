
package com.velotech.fanselection.offer.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl30OfferPriceFactor;
import com.velotech.fanselection.utils.HibernateSession;

@Repository
public class OfferPumpPriceDaoImpl extends HibernateSession implements OfferPumpPriceDao {

	static Logger log = LogManager.getLogger(OfferPumpPriceDaoImpl.class.getName());

	@Override
	public List<Double> getOfferFactors(Integer offerRevId) {

		List<Double> offerFactors = new ArrayList<>();
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl30OfferPriceFactor.class);
			criteria.add(Restrictions.eq("tbl23OfferRev.id", offerRevId));
			criteria.setProjection(Projections.property("factor"));
			offerFactors = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return offerFactors;
	}
}

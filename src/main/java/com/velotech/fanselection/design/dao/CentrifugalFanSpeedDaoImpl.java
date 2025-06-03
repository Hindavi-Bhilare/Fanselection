package com.velotech.fanselection.design.dao;

import org.apache.logging.log4j.LogManager;


import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl01CentrifugalFanSpeed;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;

@Repository
public class CentrifugalFanSpeedDaoImpl extends HibernateSession implements CentrifugalFanSpeedDao {

	private static Logger log = LogManager.getLogger(CentrifugalFanSpeedDaoImpl.class.getName());
	
	protected Transaction tx = null;

	

	@Override
	public Tbl01CentrifugalFanSpeed getCentrifugalFanSpeedDataForGraph(Integer fanSpeed) {

		Tbl01CentrifugalFanSpeed centrifugalFanSpeed = new Tbl01CentrifugalFanSpeed();
		try {
			centrifugalFanSpeed = (Tbl01CentrifugalFanSpeed) getSessionWFilter().createCriteria(Tbl01CentrifugalFanSpeed.class)
					.add(Restrictions.eq("id", fanSpeed)).setFetchMode("tbl01CentrifugalFanSpeed", FetchMode.EAGER)
					.setFetchMode("tbl01CentrifugalSpeedData", FetchMode.EAGER).uniqueResult();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return centrifugalFanSpeed;
	}
	
	@Override
	public ApplicationResponse getCentrifugalFanSpeed(Conjunction restrictions) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl01CentrifugalFanSpeed.class);
			if (restrictions != null)
				criteria.add((Conjunction) restrictions);
			ScrollableResults results = criteria.scroll();
			results.last();
			long total = results.getRowNumber() + 1;
			results.close();
			
			data = criteria.list();
			response.setData(data);
			response.setTotal(total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}
}




package com.velotech.fanselection.design.dao;

import org.apache.logging.log4j.LogManager;


import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl01CentrifugalSpeedData;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;

@Repository
public class CentrifugalSpeedDataDaoImpl extends HibernateSession implements CentrifugalSpeedDataDao{

	private static Logger log = LogManager.getLogger(CentrifugalSpeedDataDaoImpl.class.getName());
	
	protected Transaction tx = null;

	
	@Override
	public ApplicationResponse getCentrifugalSpeedData(Conjunction restrictions) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl01CentrifugalSpeedData.class);
			if (restrictions != null)
				criteria.add((Conjunction) restrictions);
			ScrollableResults results = criteria.scroll();
			results.last();
			long total = results.getRowNumber() + 1;
			results.close();
			
			data = criteria.list();
			response.setData(data);
			//response.setTotal(total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}
}


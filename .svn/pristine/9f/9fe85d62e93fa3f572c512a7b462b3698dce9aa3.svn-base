
package com.velotech.fanselection.design.dao;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import com.velotech.fanselection.models.Tbl01CentrifugalFanSpeed;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.VelotechUtil;



@Repository
public class PerformancecurvemasterDaoImpl extends HibernateSession implements PerformancecurvemasterDao {

	static Logger log = LogManager.getLogger(PerformancecurvemasterDaoImpl.class.getName());
@Autowired
	VelotechUtil VelotechUtil;
	

	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW)
	public Tbl01CentrifugalFanSpeed getTbl10PerformanceSpeed(Integer id)
	{

		Tbl01CentrifugalFanSpeed tbl10PerformanceVane = null;
		try {
			
			Criteria criteria = getSessionWFilter().createCriteria(Tbl01CentrifugalFanSpeed.class)
					.add(Restrictions.eq("id", id));//.setFetchMode("tbl10PerformanceDias", FetchMode.EAGER);
			tbl10PerformanceVane = (Tbl01CentrifugalFanSpeed) criteria.uniqueResult();
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl10PerformanceVane;
	}
	
	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRED)
	public boolean updateTbl10PerformanceDia(Tbl01CentrifugalFanSpeed tbl01CentrifugalFanSpeed) {

		boolean ans = false;
		try {
			
			getSessionWFilter().merge(tbl01CentrifugalFanSpeed);
			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}


	
}

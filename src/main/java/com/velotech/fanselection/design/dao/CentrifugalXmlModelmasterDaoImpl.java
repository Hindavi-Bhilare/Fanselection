
package com.velotech.fanselection.design.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.velotech.fanselection.models.Tbl01CentrifugalFanSpeed;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01CentrifugalSpeedData;
import com.velotech.fanselection.models.Tbl90XmlCentrifugalFanSpeed;
import com.velotech.fanselection.utils.HibernateSession;

@Repository
public class CentrifugalXmlModelmasterDaoImpl extends HibernateSession implements CentrifugalXmlModelmasterDao {

	static Logger log = LogManager.getLogger(CentrifugalXmlModelmasterDaoImpl.class.getName());


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Tbl90XmlCentrifugalFanSpeed> getTbl90XmlCentrifugalFanSpeed(Integer fanModelId) {

		List<Tbl90XmlCentrifugalFanSpeed> ans = new ArrayList<>();
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl90XmlCentrifugalFanSpeed.class)
					.createAlias("tbl90XmlCentrifugalModelMaster", "tbl90XmlCentrifugalModelMaster")
					.add(Restrictions.eq("tbl90XmlCentrifugalModelMaster.id", fanModelId));
			ans = criteria.list();
			for (Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeed : ans) {
				tbl90XmlCentrifugalFanSpeed.getTbl90XmlCentrifugalPerformanceDataHeads().iterator();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveTbl01CentrifugalFanSpeed(Tbl01CentrifugalFanSpeed tbl01CentrifugalFanSpeed) {

		try {
			getSessionWFilter().save(tbl01CentrifugalFanSpeed);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveTbl01CentrifugalSpeedData(Tbl01CentrifugalSpeedData tbl01CentrifugalSpeedData) {

		try {
			getSessionWFilter().save(tbl01CentrifugalSpeedData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateTbl01CentrifugalFanSpeed(Tbl01CentrifugalFanSpeed tbl01CentrifugalFanSpeed) {

		// TODO Auto-generated method stub
		try {
			getSessionWFilter().merge(tbl01CentrifugalFanSpeed);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Tbl01CentrifugalFanSpeed> getCentrifugalFanSpeed(Integer[] id) {

		List<Tbl01CentrifugalFanSpeed> tbl01CentrifugalFanSpeed = new ArrayList<Tbl01CentrifugalFanSpeed>();
		try {
			tbl01CentrifugalFanSpeed = getSessionWFilter().createCriteria(Tbl01CentrifugalFanSpeed.class)
					.add(Restrictions.in("id", id)).list();
			for (int i = 0; i < tbl01CentrifugalFanSpeed.size(); i++) {
				tbl01CentrifugalFanSpeed.get(i).getTbl01CentrifugalSpeedData().iterator();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl01CentrifugalFanSpeed;
	}

	@Override
	public Tbl01CentrifugalModelMaster getCentrifugalModelMaster(String fanModel) {
		Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster = null;
		try {
			tbl01CentrifugalModelMaster = (Tbl01CentrifugalModelMaster) getSessionWFilter().createCriteria(Tbl01CentrifugalModelMaster.class)
					.add(Restrictions.eq("fanModel", fanModel)).uniqueResult();
	
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl01CentrifugalModelMaster;
	}

}


package com.velotech.fanselection.design.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.velotech.fanselection.models.Tbl90XmlCentrifugalFanSpeed;
import com.velotech.fanselection.models.Tbl90XmlCentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl90XmlCentrifugalPerformanceDataHead;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.VelotechUtil;

@Repository
public class CentrifugalXmlAttachmentDaoImpl extends HibernateSession implements CentrifugalXmlAttachmentDao {

	static Logger log = LogManager.getLogger(CentrifugalXmlAttachmentDaoImpl.class.getName());

	@Autowired
	VelotechUtil VelotechUtil;

	@Override
	public boolean checkCentrifugalModelMaster(String fanModel) {

		boolean perfCurveCheck = false;
		try {
			long perfCurveExist = (Long) getSessionWFilter().createCriteria(Tbl90XmlCentrifugalModelMaster.class)
					.add(Restrictions.eq("fanModel", fanModel))
					.setProjection(Projections.projectionList().add(Projections.count("id"))).uniqueResult();
			if (perfCurveExist > 0) {
				perfCurveCheck = true;
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return perfCurveCheck;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Tbl90XmlCentrifugalFanSpeed checkCentrifugalFanSpeed(Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeed, Integer fanModelId) {

		Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeed1 = new Tbl90XmlCentrifugalFanSpeed();
		try {

			tbl90XmlCentrifugalFanSpeed1 = (Tbl90XmlCentrifugalFanSpeed) getSessionWFilter().createCriteria(Tbl90XmlCentrifugalFanSpeed.class)
					.createAlias("tbl90XmlCentrifugalModelMaster", "tbl90XmlCentrifugalModelMaster")
					.add(Restrictions.eq("speed", tbl90XmlCentrifugalFanSpeed.getSpeed()))
					.add(Restrictions.eq("tbl90XmlCentrifugalModelMaster.id", fanModelId)).uniqueResult();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl90XmlCentrifugalFanSpeed1;
	}

	@Override
	public List<Tbl90XmlCentrifugalPerformanceDataHead> gettbl90XmlCentrifugalPerformanceDataHead(Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeed) {

		List<Tbl90XmlCentrifugalPerformanceDataHead> tbl90XmlCentrifugalPerformanceDataHeadList = null;
		try {
			tbl90XmlCentrifugalPerformanceDataHeadList = getSessionWFilter().createCriteria(Tbl90XmlCentrifugalPerformanceDataHead.class)
					.createAlias("tbl90XmlCentrifugalFanSpeed", "tbl90XmlCentrifugalFanSpeed")
					.add(Restrictions.eq("tbl90XmlCentrifugalFanSpeed.centrifugalFanSpeedId", tbl90XmlCentrifugalFanSpeed.getCentrifugalFanSpeedId())).list();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl90XmlCentrifugalPerformanceDataHeadList;
	}

	@Override
	public List<Tbl90XmlCentrifugalFanSpeed> getTbl90XmlCentrifugalFanSpeed(Integer fanModelId) {

		List<Tbl90XmlCentrifugalFanSpeed> tbl90XmlCentrifugalFanSpeedList = null;
		try {
			tbl90XmlCentrifugalFanSpeedList = getSessionWFilter().createCriteria(Tbl90XmlCentrifugalFanSpeed.class)
					.createAlias("tbl90XmlCentrifugalModelMaster", "tbl90XmlCentrifugalModelMaster")
					.add(Restrictions.eq("tbl90XmlCentrifugalModelMaster.id", fanModelId)).list();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl90XmlCentrifugalFanSpeedList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveTbl90CentrifugalXmlModelmaster(Tbl90XmlCentrifugalModelMaster modelmaster) {

		// TODO Auto-generated method stu
		try {
			getSession().save(modelmaster);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
	}
	
	
	
	

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Tbl90XmlCentrifugalFanSpeed saveTbl90XmlCentrifugalFanSpeed(Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeed) {

		try {
			getSession().save(tbl90XmlCentrifugalFanSpeed);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return tbl90XmlCentrifugalFanSpeed;
	}

}

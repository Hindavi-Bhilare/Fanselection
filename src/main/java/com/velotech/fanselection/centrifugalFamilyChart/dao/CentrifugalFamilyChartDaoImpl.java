

package com.velotech.fanselection.centrifugalFamilyChart.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl51UsertypeFanType;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.VelotechUtil;
//import com.velotech.pumpselection.models.Tbl091VfdList;
//import com.velotech.pumpselection.models.Tbl12ModelPerformance;

@Repository
public class CentrifugalFamilyChartDaoImpl extends HibernateSession implements CentrifugalFamilyChartDao {

	static Logger log = LogManager.getLogger(CentrifugalFamilyChartDaoImpl.class.getName());
	
	@Autowired
	VelotechUtil velotechUtil;

	@Override
	public Tbl01CentrifugalModelMaster getModelPeformance(int fanModelId) {

		Tbl01CentrifugalModelMaster modelPerformance = null;
		try {

			modelPerformance = (Tbl01CentrifugalModelMaster) getSessionWFilter().createCriteria(Tbl01CentrifugalModelMaster.class)
					.add(Restrictions.eq("id", fanModelId))
					.uniqueResult();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return modelPerformance;
	}

	/*
	 * @Override public List<Integer> getvfdSpeed(int perfCurveId, String frequency)
	 * {
	 * 
	 * List<Integer> vfdSpeed = new ArrayList<Integer>(); try {
	 * 
	 * vfdSpeed =
	 * getSessionWFilter().createCriteria(Tbl091VfdList.class).add(Restrictions.eq(
	 * "tbl09Performancecurvemaster.id", perfCurveId))
	 * .add(Restrictions.eq("frequency",
	 * frequency)).setProjection(Projections.property("vfdspeed")).list();
	 * 
	 * } catch (Exception e) { log.error(e.getMessage(), e); } return vfdSpeed; }
	 */

	@Override
	public ApplicationResponse getSeries(){
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Tbl51UsertypeFanType.class);
			detachedCriteria.setProjection(Projections.property("tbl01Fantype.id"));
			detachedCriteria.add(Restrictions.eq("tbl50Usertype.id", velotechUtil.getUsertypeId()));
			
			Criteria criteria = getSessionWFilter().createCriteria(Tbl01Fantype.class)
					.add(Subqueries.propertyIn("id", detachedCriteria));
			
			Object data = criteria.list();
			applicationResponse.setData(data);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}
	
	
	@Override
	public ApplicationResponse getFanModel(int fanTypeId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl01CentrifugalModelMaster.class);
			criteria.createAlias("tbl01fantype", "tbl01fantype");
			criteria.add(Restrictions.eq("tbl01fantype.id", fanTypeId));
			criteria.add(Restrictions.eq("availability", true));
			Object data = criteria.list();
			applicationResponse.setData(data);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}
	 

	@Override
	public List<Tbl01CentrifugalModelMaster> getAllModelPeformance(int seriesId) {
		
		List<Tbl01CentrifugalModelMaster> modelPerformanceList = null;
		try {

			/*
			 * modelPerformanceList =
			 * getSessionWFilter().createCriteria(Tbl02Modelmaster.class)
			 * .add(Restrictions.eq("tbl01fantype.id", seriesId))
			 * .createAlias("tbl02Modelmaster", "tbl02Modelmaster")
			 * .createAlias("tbl02Modelmaster.tbl01fantype", "tbl01Fantype") .list();
			 */

		 modelPerformanceList = getSessionWFilter()
				    .createCriteria(Tbl01CentrifugalModelMaster.class)
				    .createAlias("tbl01fantype", "fantype")  // Creating alias for tbl01fantype
				    .add(Restrictions.eq("fantype.id", seriesId))  // Filtering based on seriesId from tbl01fantype
				    .list();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return modelPerformanceList;
	}
}

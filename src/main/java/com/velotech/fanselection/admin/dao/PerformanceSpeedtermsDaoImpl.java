
package com.velotech.fanselection.admin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl01CentrifugalFanSpeed;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
//import com.velotech.fanselection.models.Tbl12ModelPerformance;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;

@Repository
public class PerformanceSpeedtermsDaoImpl extends HibernateSession implements PerformanceSpeedtermsDao {

	private static Logger log = LogManager.getLogger(PerformanceSpeedtermsDaoImpl.class.getName());

	@Override
	public ApplicationResponse getFanModel(String seriesId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		Criteria criteria = getSessionWFilter().createCriteria(Tbl01CentrifugalModelMaster.class);
		criteria.createAlias("tbl01fantype", "tbl01fantype");
		criteria.add(Restrictions.eq("tbl01fantype.id", Integer.parseInt(seriesId)));
		Object data = criteria.list();
		applicationResponse.setData(data);
		return applicationResponse;
	}

	
	/*
	 * @Override public ApplicationResponse getPerformaneCurve(String modelId) {
	 * 
	 * ApplicationResponse applicationResponse = new ApplicationResponse(); try {
	 * DetachedCriteria detachedCriteria =
	 * DetachedCriteria.forClass(Tbl12ModelPerformance.class);
	 * detachedCriteria.setProjection(Projections.property(
	 * "tbl09Performancecurvemaster.id"));
	 * detachedCriteria.createAlias("tbl02Modelmaster", "tbl02Modelmaster");
	 * detachedCriteria.add(Restrictions.eq("tbl02Modelmaster.id",
	 * Integer.parseInt(modelId)));
	 * 
	 * Criteria criteria =
	 * getSessionWFilter().createCriteria(Tbl09Performancecurvemaster.class);
	 * criteria.add(Subqueries.propertyIn("id", detachedCriteria)); Object data =
	 * criteria.list(); applicationResponse.setData(data); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return applicationResponse; }
	 */
	 

	@Override
	public ApplicationResponse getPerformanceCurveSpeed(String fanModelId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		Criteria criteria = getSessionWFilter().createCriteria(Tbl01CentrifugalFanSpeed.class);
		criteria.add(Restrictions.eq("tbl01CentrifugalModelMaster.id", Integer.parseInt(fanModelId)));
		Object data = criteria.list();
		applicationResponse.setData(data);
		return applicationResponse;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Tbl01CentrifugalFanSpeed> getPerformanceSpeed(String series, String fanModel, String perfSpeed) {

		List<Tbl01CentrifugalFanSpeed> tbl01CentrifugalFanSpeeds = new ArrayList<Tbl01CentrifugalFanSpeed>();
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl01CentrifugalFanSpeed.class);
			criteria.addOrder(Order.asc("id"));

			if (perfSpeed != "" && !perfSpeed.equals("All")) {
				criteria.add(Restrictions.eq("id", Integer.parseInt(perfSpeed)));
				tbl01CentrifugalFanSpeeds = criteria.list();
			}  else if (fanModel != "" && !fanModel.equals("All")) {
				criteria.add(Restrictions.eq("tbl01CentrifugalModelMaster.id",  Integer.parseInt(fanModel)));
				tbl01CentrifugalFanSpeeds= criteria.list();
			} else if (series != "" && !series.equals("All")) {
				criteria.createAlias("tbl01CentrifugalModelMaster", "tbl01CentrifugalModelMaster");
				criteria.createAlias("tbl01CentrifugalModelMaster.tbl01fantype", "tbl01fantype");
				criteria.add(Restrictions.eq("tbl01fantype.id", Integer.parseInt(series)));
				tbl01CentrifugalFanSpeeds = criteria.list();
			} else
				tbl01CentrifugalFanSpeeds = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return tbl01CentrifugalFanSpeeds;
	}
}

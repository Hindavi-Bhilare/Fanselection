
package com.velotech.fanselection.combobox.dao;

import java.util.ArrayList;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl09Dropdownlist;
//import com.velotech.fanselection.models.Tbl09Performancecurvemaster;
//import com.velotech.fanselection.models.Tbl12ModelPerformance;
import com.velotech.fanselection.models.Tbl1401Motortype;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl58UserCompany;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.VelotechUtil;

@Repository
public class ComboboxDaoImpl extends HibernateSession implements ComboboxDao {

	static Logger log = LogManager.getLogger(ComboboxDaoImpl.class.getName());

	@Autowired
	private VelotechUtil velotechUtil;

	@Override
	public ApplicationResponse getOffers(String query, Pagination pagination) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl23OfferRev.class);
			criteria.createAlias("tbl23OfferMaster", "tbl23OfferMaster");

			if (query != null)
				criteria.add(Restrictions.like("tbl23OfferMaster.offerNo", query, MatchMode.ANYWHERE));

			ScrollableResults results = criteria.scroll();
			results.last();
			long total = results.getRowNumber() + 1;
			results.close();

			criteria.addOrder(Order.desc("id"));

			if (pagination != null)
				criteria.setFirstResult(pagination.getStart()).setMaxResults(pagination.getLimit());

			List<?> list = criteria.list();
			data = list;
			response.setData(data);
			response.setTotal(total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public List<Tbl58UserCompany> getUserCompanies() {

		List<Tbl58UserCompany> tbl58UserCompanies = new ArrayList<>();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl58UserCompany.class);
			criteria.add(Restrictions.eq("tbl52Usermaster.loginId", velotechUtil.getLoginId()));

			tbl58UserCompanies = criteria.list();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl58UserCompanies;
	}

	@Override
	public List<Tbl14PrimemoverMaster> getPrimeMover() {

		List<Tbl14PrimemoverMaster> tbl14PrimemoverMasterList = new ArrayList<>();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl14PrimemoverMaster.class);

			tbl14PrimemoverMasterList = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl14PrimemoverMasterList;
	}

	/*
	 * @Override public List<String> getCanopy() {
	 * 
	 * List<String> canopys = new ArrayList<>(); try { Criteria criteria =
	 * getSessionWFilter().createCriteria(Tbl15Pumpsetga.class);
	 * criteria.setProjection(Projections.distinct(Projections.property("canopy")));
	 * canopys = criteria.list(); } catch (Exception e) { log.error(e.getMessage(),
	 * e); } return canopys; }
	 */

	/*
	 * @Override public List<String> getDrainRim() {
	 * 
	 * List<String> drainRim = new ArrayList<>(); try { Criteria criteria =
	 * getSessionWFilter().createCriteria(Tbl15Pumpsetga.class);
	 * criteria.setProjection(Projections.distinct(Projections.property("drainRim"))
	 * ); drainRim = criteria.list(); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return drainRim; }
	 */
	/*
	 * @Override public List<String> getEarthing() {
	 * 
	 * List<String> earthing = new ArrayList<>(); try { Criteria criteria =
	 * getSessionWFilter().createCriteria(Tbl15Pumpsetga.class);
	 * criteria.setProjection(Projections.distinct(Projections.property("earthing"))
	 * ); earthing = criteria.list(); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return earthing; }
	 */

	/*
	 * @Override public List<String> getLiftingLug() {
	 * 
	 * List<String> liftingLug = new ArrayList<>(); try { Criteria criteria =
	 * getSessionWFilter().createCriteria(Tbl15Pumpsetga.class);
	 * criteria.setProjection(Projections.distinct(Projections.property("liftingLug"
	 * ))); liftingLug = criteria.list(); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return liftingLug; }
	 */

	/*
	 * @Override public List<String> getStandard() {
	 * 
	 * List<String> standard = new ArrayList<>(); try { Criteria criteria =
	 * getSessionWFilter().createCriteria(Tbl15Pumpsetga.class);
	 * criteria.setProjection(Projections.distinct(Projections.property("standard"))
	 * ); standard = criteria.list(); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return standard; }
	 */

	/*
	 * @Override public ApplicationResponse getPerformaneCurve(Integer
	 * modelmasterid) {
	 * 
	 * ApplicationResponse applicationResponse = new ApplicationResponse(); try {
	 * DetachedCriteria detachedCriteria =
	 * DetachedCriteria.forClass(Tbl12ModelPerformance.class);
	 * detachedCriteria.setProjection(Projections.property(
	 * "tbl09Performancecurvemaster.id"));
	 * detachedCriteria.add(Restrictions.eq("tbl02Modelmaster.id", modelmasterid));
	 * 
	 * Criteria criteria =
	 * getSessionWFilter().createCriteria(Tbl09Performancecurvemaster.class);
	 * criteria.add(Subqueries.propertyIn("id", detachedCriteria)); Object data =
	 * criteria.list(); applicationResponse.setData(data); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return applicationResponse; }
	 */

	@Override
	public List<Tbl09Dropdownlist> getType() {

		List<Tbl09Dropdownlist> tbl09Dropdownlists = new ArrayList<>();
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl09Dropdownlist.class)
					.add(Restrictions.eq("description", "Type"));
			tbl09Dropdownlists = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl09Dropdownlists;
	}

	@Override
	public List<Tbl09Dropdownlist> getCategory() {

		List<Tbl09Dropdownlist> tbl09Dropdownlists = new ArrayList<>();
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl09Dropdownlist.class)
					.add(Restrictions.eq("description", "Category"));
			tbl09Dropdownlists = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl09Dropdownlists;
	}

	/*
	 * @Override public List<Tbl02ModelNrv> getModelNrv(Integer pumpModelId) {
	 * 
	 * // TODO Auto-generated method stub List<Tbl02ModelNrv> modelNrvList = new
	 * ArrayList(); try { modelNrvList =
	 * getSessionWFilter().createCriteria(Tbl02ModelNrv.class).add(Restrictions.eq(
	 * "tbl02Modelmaster.id", pumpModelId)).list(); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return modelNrvList; }
	 */

	@Override
	public String getStartingMethod(Integer primeMoverId) {

		String startingMethod = null;
		try {
			startingMethod = (String) getSessionWFilter().createCriteria(Tbl14PrimemoverMaster.class)
					.add(Restrictions.eq("primemoverId", primeMoverId))
					.setProjection(Projections.property("startingMethod")).uniqueResult();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return startingMethod;
	}

	@Override
	public Tbl52Usermaster getSalesPerson() {

		Tbl52Usermaster tbl52Usermaster = new Tbl52Usermaster();
		String salesPerson = "";
		try {
			tbl52Usermaster = (Tbl52Usermaster) getSessionWFilter().createCriteria(Tbl52Usermaster.class)
					.add(Restrictions.like("loginId", velotechUtil.getLoginId())).uniqueResult();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl52Usermaster;
	}

	@Override
	public List<Tbl52Usermaster> getSalesPerson(Integer organisationId) {

		List<Tbl52Usermaster> userMasters = new ArrayList();
		try {
			Conjunction conjunction = Restrictions.conjunction();
			if (!velotechUtil.isAdminUser()) {
				conjunction.add(Restrictions.like("loginId", velotechUtil.getLoginId()));

			}
			conjunction.add(Restrictions.eq("tbl59Organisation.id", organisationId));
			Criteria criteria = getSessionWFilter().createCriteria(Tbl52Usermaster.class).add(conjunction);
			userMasters = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return userMasters;
	}

	@Override
	public List<Tbl14PrimemoverMaster> getPrimeMoverCombo(Integer motorSeriesId, Double power) {

		List<Tbl14PrimemoverMaster> tbl14PrimemoverMasterList = new ArrayList<>();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl14PrimemoverMaster.class);
			criteria.add(Restrictions.eq("tbl1401Motortype.id", motorSeriesId)).add(Restrictions.eq("power", power));
			tbl14PrimemoverMasterList = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl14PrimemoverMasterList;
	}

	@Override
	public List<Tbl1401Motortype> getMotorDescription(String motorSeriesId) {
		List<Tbl1401Motortype> tbl1401MotortypeList = new ArrayList<>();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl1401Motortype.class);
			criteria.add(Restrictions.eq("series", motorSeriesId));
			tbl1401MotortypeList = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl1401MotortypeList;
	}

}

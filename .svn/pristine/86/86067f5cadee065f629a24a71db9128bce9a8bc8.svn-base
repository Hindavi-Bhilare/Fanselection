
package com.velotech.fanselection.selection.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl1401Motortype;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.models.Tbl51UsertypeFanType;
import com.velotech.fanselection.models.Tbl58UserCompany;
import com.velotech.fanselection.models.Tbl95TxPumpSelectionModel;
import com.velotech.fanselection.models.Tbl95TxSelectedPumps;
import com.velotech.fanselection.selection.models.CentrifugalFanSelectionModel;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.OrderBySqlFormula;
import com.velotech.fanselection.utils.VelotechUtil;

@Repository
@Scope("prototype")
public class CentrifugalFanSelectionDaoImpl extends HibernateSession implements CentrifugalFanSelectionDao {

	static Logger log = LogManager.getLogger(CentrifugalFanSelectionDaoImpl.class.getName());

	@Autowired
	VelotechUtil velotechUtil;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Tbl01Fantype> getFanType(String fanSeries) {

		List<Tbl01Fantype> ans = new ArrayList<>();
		try {

			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Tbl51UsertypeFanType.class);
			detachedCriteria.setProjection(Projections.property("tbl01Fantype.id"));
			detachedCriteria.add(Restrictions.eq("tbl50Usertype.id", velotechUtil.getUsertypeId()));

			Criteria criteria = getSessionWFilter().createCriteria(Tbl01Fantype.class)
					.add(Subqueries.propertyIn("id", detachedCriteria));
			if (fanSeries != null && fanSeries != "")
				criteria.add(Restrictions.like("series", fanSeries, MatchMode.ANYWHERE));

			ans = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public boolean isUniqueTagNumber(String tagNo, Integer offerRevId) {

		boolean ans = false;
		try {
			List<String> tagNoList = new ArrayList();
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.property("tagNo"), "tagNo");
			Conjunction ds = Restrictions.conjunction();
			ds.add(Restrictions.eq("tagNo", tagNo));
			ds.add(Restrictions.eq("tbl23OfferRev.id", offerRevId));
			Criteria criteria = getSessionWFilter().createCriteria(Tbl26OfferFan.class).add(ds).setProjection(projList);
			tagNoList = criteria.list();

			if (tagNoList.size() == 0)
				ans = true;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveTbl95TxSelectedPumps(Tbl95TxSelectedPumps txSelectedPump) {

		try {
			getSession().save(txSelectedPump);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteTbl95TxSelectedPumps() {

		try {
			// Delete Records from Tbl95TxSelectedPumps for current sessionId
			StringBuilder deleteQuerySelectedPumps = new StringBuilder();
			deleteQuerySelectedPumps.append("DELETE FROM tbl_95_tx_selectedPumps");
			deleteQuerySelectedPumps.append(" WHERE sessionId='");
			deleteQuerySelectedPumps.append(velotechUtil.getSessionId() + "'");
			getSession().createSQLQuery(deleteQuerySelectedPumps.toString()).executeUpdate();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteTbl95TxPumpSelectionModel() {

		try {
			// Delete Records from Tbl95TxPumpSelectionModel for current
			// sessionId
			StringBuilder deleteQuerySelectionModel = new StringBuilder();
			deleteQuerySelectionModel.append("DELETE FROM tbl_95_tx_pumpSelectionModel");
			deleteQuerySelectionModel.append(" WHERE sessionId='");
			deleteQuerySelectionModel.append(velotechUtil.getSessionId() + "'");
			getSession().createSQLQuery(deleteQuerySelectionModel.toString()).executeUpdate();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveTbl95TxPumpSelectionModel(Tbl95TxPumpSelectionModel tbl95TxPumpSelectionModel) {

		try {
			getSession().save(tbl95TxPumpSelectionModel);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Tbl28CompanyMaster getDefaultCompany(String user) {

		Tbl28CompanyMaster tbl28CompanyMaster = new Tbl28CompanyMaster();
		List<Tbl58UserCompany> tbl58UserCompanyList = new ArrayList<Tbl58UserCompany>();
		try {

			tbl58UserCompanyList = getSessionWFilter().createCriteria(Tbl58UserCompany.class)
					.createAlias("tbl28CompanyMaster", "tbl28CompanyMaster")
					.add(Restrictions.eq("tbl52Usermaster.loginId", user)).add(Restrictions.eq("defaultComp", true))
					.list();
			if (!tbl58UserCompanyList.isEmpty())
				tbl28CompanyMaster = tbl58UserCompanyList.get(0).getTbl28CompanyMaster();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl28CompanyMaster;
	}

	@Override
	public List<Tbl01CentrifugalModelMaster> getmodelMaster(CentrifugalFanSelectionModel fanSelectionModel) {

		List<Tbl01CentrifugalModelMaster> ans = new ArrayList<>();

		try {

			List<String> seriesSelected = fanSelectionModel.getSeriesSelected();
			Integer[] seriesArray = new Integer[seriesSelected.size()];

			for (int i = 0; i < seriesSelected.size(); i++)
				seriesArray[i] = Integer.parseInt(seriesSelected.get(i));

			Criteria criteria = getSessionWFilter().createCriteria(Tbl01CentrifugalModelMaster.class)
					.createAlias("tbl01fantype", "tbl01fantype").add(Restrictions.in("tbl01fantype.id", seriesArray))
					.add(Restrictions.eq("availability", true));
			String dpPole = fanSelectionModel.getDp_pole();
			
			  if (dpPole != null && !dpPole.isEmpty() && !dpPole.equals("All")) {
			  criteria.add(Restrictions.eq("pole", Integer.parseInt(dpPole))); }
			 

			ans = criteria.list();
			// ans = getSessionWFilter().createCriteria(Tbl02Modelmaster.class).list();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public List<Tbl14PrimemoverMaster> getPrimeMoverForMotorSelection(double primemoverCal,
			Tbl01CentrifugalModelMaster modelmaster, CentrifugalFanSelectionModel fanSelectionModel, Integer selectedSpeed) {
		List<Tbl14PrimemoverMaster> ans = new ArrayList<>();
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl14PrimemoverMaster.class)
					.add(Restrictions.ge("power", primemoverCal))
					.add(Restrictions.eq("frequency", fanSelectionModel.getDp_frequency())).addOrder(Order.asc("power"))
					.setMaxResults(1);
			//criteria.add(Restrictions.ge("speed", Double.valueOf(modelmaster.getMinSpeed())));
			//criteria.add(Restrictions.le("speed", Double.valueOf(modelmaster.getMaxSpeed())));
			if (!fanSelectionModel.getDp_manufacturer().equals("All"))
				criteria.add(Restrictions.eq("manufacturer", fanSelectionModel.getDp_manufacturer()));

			if (!fanSelectionModel.getDp_specification().equals("All"))
				criteria.add(Restrictions.eq("specification", fanSelectionModel.getDp_specification()));

			if (!fanSelectionModel.getDp_specification().equals("All"))
				criteria.add(Restrictions.eq("effClass", fanSelectionModel.getDp_effClass()));
			
			if (!fanSelectionModel.getDp_pole().isEmpty() && !fanSelectionModel.getDp_pole().equals("All"))
				 criteria.add(Restrictions.eq("pole", Integer.parseInt(fanSelectionModel.getDp_pole())));
			
			criteria.addOrder(OrderBySqlFormula.sqlFormula("abs(speed-" + selectedSpeed + ")"));

			ans = criteria.list();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	/*
	 * Used CriteriaBuilder Instead of Criteria API for Dynamic Query
	 * 
	 * @Override public List<Tbl14PrimemoverMaster> getPrimeMoverForMotorSelection(
	 * double primemoverCal, Tbl01CentrifugalModelMaster modelmaster,
	 * CentrifugalFanSelectionModel fanSelectionModel) {
	 * 
	 * // Step 1: Set up CriteriaBuilder and CriteriaQuery CriteriaBuilder cb =
	 * entityManager.getCriteriaBuilder(); CriteriaQuery<Tbl14PrimemoverMaster>
	 * query = cb.createQuery(Tbl14PrimemoverMaster.class);
	 * 
	 * // Step 2: Define the root of the query Root<Tbl14PrimemoverMaster> root =
	 * query.from(Tbl14PrimemoverMaster.class);
	 * 
	 * // Step 3: Build a list of predicates for conditions List<Predicate>
	 * predicates = new ArrayList<>();
	 * 
	 * // Basic conditions predicates.add(cb.greaterThanOrEqualTo(root.get("power"),
	 * primemoverCal)); predicates.add(cb.equal(root.get("pole"),
	 * modelmaster.getPole())); predicates.add(cb.equal(root.get("frequency"),
	 * fanSelectionModel.getDp_frequency()));
	 * 
	 * // Conditional filters if
	 * (!fanSelectionModel.getDp_manufacturer().equals("All")) {
	 * predicates.add(cb.equal(root.get("manufacturer"),
	 * fanSelectionModel.getDp_manufacturer())); }
	 * 
	 * if (!fanSelectionModel.getDp_specification().equals("All")) {
	 * predicates.add(cb.equal(root.get("specification"),
	 * fanSelectionModel.getDp_specification())); }
	 * 
	 * if (!fanSelectionModel.getDp_effClass().equals("All")) {
	 * predicates.add(cb.equal(root.get("effClass"),
	 * fanSelectionModel.getDp_effClass())); }
	 * 
	 * // Step 4: Apply predicates and set sorting query.select(root)
	 * .where(cb.and(predicates.toArray(new Predicate[0])))
	 * .orderBy(cb.asc(root.get("power")));
	 * 
	 * // Step 5: Execute the query with a max result limit return
	 * entityManager.createQuery(query) .setMaxResults(1) // Limits to one result as
	 * in your original code .getResultList(); // Runs the query and returns the
	 * list } }
	 * 
	 */

	@Override
	public Tbl14PrimemoverMaster getPrimeMoverData(Integer primemoverId) {

		Tbl14PrimemoverMaster tbl14PrimemoverMaster = new Tbl14PrimemoverMaster();
		try {
			tbl14PrimemoverMaster = (Tbl14PrimemoverMaster) getSessionWFilter()
					.createCriteria(Tbl14PrimemoverMaster.class).createAlias("tbl1401Motortype", "tbl1401Motortype")
					.add(Restrictions.eq("primemoverId", primemoverId)).uniqueResult();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl14PrimemoverMaster;
	}

	@Override
	public Tbl1401Motortype gettbl1401Motortype(int id) {
		Tbl1401Motortype ans = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl1401Motortype.class);
			criteria.add(Restrictions.eq("id", id));
			// criteria.add(Restrictions.like(property, values));
			ans = (Tbl1401Motortype) criteria.uniqueResult();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public List<String> getEffclass(String projection, String restType, String restValue) {

		List<String> ans = new ArrayList<>();
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl14PrimemoverMaster.class);
			criteria.setProjection(Projections.distinct(Projections.property(projection)));
			criteria.add(Restrictions.eq(restType, restValue));
			ans = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public List<String> getAmbientemp(String projection, String restType, String restValue) {

		List<String> ans = new ArrayList<>();
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl14PrimemoverMaster.class);
			criteria.setProjection(Projections.distinct(Projections.property(projection)));
			criteria.add(Restrictions.eq(restType, restValue));
			ans = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public List<Double> getTempPrimemoverSpeed(CentrifugalFanSelectionModel selectionModel) {
		List<Double> ans = null;
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl14PrimemoverMaster.class)
					.setProjection(Projections.distinct(Projections.property("speed")));
			// .add(Restrictions.eq("manufacturer",
			// pumpSelectionModel.getDp_manufacturer()));

			if (!selectionModel.getDp_manufacturer().equals("") && !selectionModel.getDp_manufacturer().equals("All"))
				criteria.add(Restrictions.eq("manufacturer", selectionModel.getDp_manufacturer()));

			if (selectionModel.getDp_primemover_type().equals("Motor")) {
				if (!selectionModel.getDp_specification().equals("") && !selectionModel.getDp_specification().equals("All"))
					criteria.add(Restrictions.eq("specification", selectionModel.getDp_specification()));


				if (!selectionModel.getDp_effClass().equals("") && !selectionModel.getDp_effClass().equals("All"))
					criteria.add(Restrictions.eq("effClass", selectionModel.getDp_effClass()));


				if (!selectionModel.getDp_pole().equals(""))
					criteria.add(Restrictions.eq("pole", Integer.parseInt(selectionModel.getDp_pole())));


				if (!selectionModel.getDp_pmSeries().equals(""))
					criteria.add(Restrictions.eq("series", selectionModel.getDp_pmSeries()));
			}

			ans = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

}

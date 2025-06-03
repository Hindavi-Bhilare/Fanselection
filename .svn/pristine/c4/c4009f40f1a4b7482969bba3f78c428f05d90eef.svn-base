
package com.velotech.fanselection.offer.dao;

import java.util.ArrayList;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl28OfferFanSpecification;
import com.velotech.fanselection.models.Tbl28SelectedPricing;
import com.velotech.fanselection.models.Tbl28SelectedPrimemover;
import com.velotech.fanselection.models.Tbl28SelectedFanBom;
import com.velotech.fanselection.models.Tbl61ProjectSummaryQuoteReport;
import com.velotech.fanselection.utils.HibernateSession;

@Repository
public class OfferDetailsDaoImpl extends HibernateSession implements OfferDetailsDao {

	static Logger log = LogManager.getLogger(OfferDetailsDaoImpl.class.getName());

	@SuppressWarnings("unchecked")
	@Override
	public Tbl14PrimemoverMaster getPrimeMover(Integer offerFanId) {

		Tbl14PrimemoverMaster tbl14PrimemoverMaster = new Tbl14PrimemoverMaster();
		try {
			Tbl28SelectedPrimemover tbl28SelectedPrimemover = getSessionWFilter().get(Tbl28SelectedPrimemover.class, offerFanId);
if(tbl28SelectedPrimemover != null) {
	Criteria criteria = getSessionWFilter().createCriteria(Tbl14PrimemoverMaster.class)
			.createAlias("tbl1401Motortype", "tbl1401Motortype")
			.add(Restrictions.eq("primemoverId", tbl28SelectedPrimemover.getPrimemoverId()));

	tbl14PrimemoverMaster = (Tbl14PrimemoverMaster) criteria.uniqueResult();
}
			

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl14PrimemoverMaster;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean deleteQapPricing(Integer offerFanId) {

		boolean deleted = false;
		try {

			List<Tbl28SelectedPricing> tbl28SelectedPricings = getSessionWFilter().createCriteria(Tbl28SelectedPricing.class)
					.add(Restrictions.eq("tbl26OfferFan.id", offerFanId)).add(Restrictions.eq("itemName", "QAP")).list();

			getHibernateTemplate().deleteAll(tbl28SelectedPricings);

			deleted = true;
		} catch (Exception e) {
			deleted = false;
			log.error(e.getMessage(), e);
		}
		return deleted;
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<Tbl03QapPrice> getQapPrices(Integer qapId, String pumpModel) {

		List<Tbl03QapPrice> tbl03QapPrices = new ArrayList<>();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl03QapPrice.class);
			criteria.createAlias("tbl02Modelmaster", "tbl02Modelmaster");
			criteria.add(Restrictions.eq("tbl03QapMaster.id", qapId));
			criteria.add(Restrictions.eq("tbl02Modelmaster.pumpModel", pumpModel));
			tbl03QapPrices = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl03QapPrices;
	}*/

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean deleteProjectSummaryQuote(String sessionId) {

		boolean ans = false;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl61ProjectSummaryQuoteReport.class).add(Restrictions.eq("sessionId", sessionId));
			List<Tbl61ProjectSummaryQuoteReport> list = criteria.list();
			for (Tbl61ProjectSummaryQuoteReport tbl61ProjectSummaryQuoteReport : list) {
				getSession().delete(tbl61ProjectSummaryQuoteReport);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ans = false;
		}
		return ans;
	}

	@Override
	public Tbl26OfferFan getOfferFanForPerformanceChart(Integer offerFanId) {

		Tbl26OfferFan ans = new Tbl26OfferFan();
		try {

			ans = getSessionWFilter().get(Tbl26OfferFan.class, offerFanId);
			ans.getTbl27OfferFanDp();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public List<Tbl28SelectedFanBom> getSelectedBomForDatasheet(int offerFanId, int productTypeId) {

		List<Tbl28SelectedFanBom> ans = new ArrayList<>();
		try {
			ans = getSessionWFilter().createCriteria(Tbl28SelectedFanBom.class).add(Restrictions.eq("tbl26OfferFan.id", offerFanId))
					.add(Restrictions.eq("tbl01ProductTypeMaster.id", productTypeId)).add(Restrictions.eq("reportSummary", true)).list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public List<Tbl28SelectedFanBom> getSelectedBomForDrawing(int offerFanId, int productTypeId) {

		List<Tbl28SelectedFanBom> ans = new ArrayList<>();
		try {
			ans = getSessionWFilter().createCriteria(Tbl28SelectedFanBom.class).add(Restrictions.eq("tbl26OfferFan.id", offerFanId))
					.add(Restrictions.eq("tbl01ProductTypeMaster.id", productTypeId)).list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	/*@Override
	public Tbl15WiringDiagram getWiringDiagram(Integer phase, String startingMethod, String motorType) {

		Tbl15WiringDiagram tbl15WiringDiagram = new Tbl15WiringDiagram();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl15WiringDiagram.class).add(Restrictions.eq("phase", phase))
					.add(Restrictions.eq("startingMethod", startingMethod)).add(Restrictions.like("motorSeries", motorType, MatchMode.ANYWHERE));
			List<Tbl15WiringDiagram> tbl15WiringDiagrams = criteria.list();
			tbl15WiringDiagram = tbl15WiringDiagrams.size() > 0 ? tbl15WiringDiagrams.get(0) : null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl15WiringDiagram;
	}*/

	@Override
	public List<Tbl28OfferFanSpecification> getTbl28OfferFanspecifications(Integer offerFanId) {

		List<Tbl28OfferFanSpecification> tbl28OfferFanSpecifications = new ArrayList();
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl28OfferFanSpecification.class)
					.add(Restrictions.eq("tbl26OfferFan.id", offerFanId)).add(Restrictions.eq("showInDataSheet", true));
			tbl28OfferFanSpecifications = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl28OfferFanSpecifications;
	}

	/*@Override
	public Tbl15StartingMethod getTbl15StartingMethod(Double power, String startingMethod) {
		
		Tbl15StartingMethod tbl15StartingMethod = new Tbl15StartingMethod();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl15StartingMethod.class).add(Restrictions.eq("power", power))
					.add(Restrictions.eq("startingMethod", startingMethod));
			List<Tbl15StartingMethod> tbl15StartingMethods = criteria.list();
			tbl15StartingMethod = tbl15StartingMethods.size() > 0 ? tbl15StartingMethods.get(0) : null;
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl15StartingMethod;
	}*/

}

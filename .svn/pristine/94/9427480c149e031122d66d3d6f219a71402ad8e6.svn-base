
package com.velotech.fanselection.dxf.dao;

import java.util.ArrayList;



import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl01VariantLine;
import com.velotech.fanselection.models.Tbl03GenericBomData;
//import com.velotech.fanselection.models.Tbl05FlangeMaster;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl28SelectedPrimemover;
import com.velotech.fanselection.models.Tbl28SelectedFanVariant;
import com.velotech.fanselection.models.Tbl58UserCompany;
import com.velotech.fanselection.selection.dao.CentrifugalFanSelectionDao;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.VelotechUtil;

@Repository
public class DxfDaoImpl extends HibernateSession implements DxfDao {

	@Autowired
	private VelotechUtil velotechUtil;

	static Logger log = LogManager.getLogger(CentrifugalFanSelectionDao.class.getName());

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<String> getTab4DatasFromFlangeMaster(Integer mocId,
	 * Integer seriesId) {
	 * 
	 * List<String> ans = null; try {
	 * 
	 * ans = getSessionWFilter().createCriteria(Tbl05FlangeMaster.class)
	 * .add(Restrictions.eq("tbl01Pumptype.id", seriesId))
	 * .add(Restrictions.like("mocIdList", "," + mocId + ",", MatchMode.ANYWHERE))
	 * .setProjection(Projections.distinct(Projections.property("specification"))).
	 * list();
	 * 
	 * } catch (Exception e) { log.error(e.getMessage(), e); } return ans; }
	 */

	@Override
	public List<Tbl58UserCompany> getUserCompany() {

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
	public Integer getVariantId(Integer fanTypeId, List<Tbl28SelectedFanVariant> tbl28SelectedFanVariants) {

		Integer ans = 0;
		try {
			int pumptypeVariants = 0;

			Disjunction dis = Restrictions.disjunction();

			for (Tbl28SelectedFanVariant tbl28SelectedFanVariant : tbl28SelectedFanVariants) {
				Conjunction conSub = Restrictions.conjunction();
				conSub.add(Restrictions.eq("parameter", tbl28SelectedFanVariant.getVariant()));
				conSub.add(Restrictions.eq("value", tbl28SelectedFanVariant.getValue()));
				dis.add(conSub);
			}

			ProjectionList proj = Projections.projectionList();
			proj.add(Projections.groupProperty("tbl01VariantMaster.id"));
			proj.add(Projections.count("id"));

			Criteria c = getSessionWFilter().createCriteria(Tbl01VariantLine.class)
					.createAlias("tbl01VariantMaster", "tbl01VariantMaster").add(dis);
			c.add(Restrictions.eq("tbl01VariantMaster.tbl01Pumptype.id", fanTypeId));
			c.setProjection(proj);
			List temp = c.list();
			for (Object object : temp) {
				Object[] obj = (Object[]) object;
				if ((Long) obj[1] == pumptypeVariants) {
					ans = (Integer) obj[0];
					break;
				}

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	/*
	 * @Override public Tbl13Barepumpga getBarePumpga(Tbl02Modelmaster
	 * tbl02Modelmaster, Integer stage, String mocID) {
	 * 
	 * Tbl13Barepumpga tbl13Barepumpga = null; try { Disjunction dc =
	 * Restrictions.disjunction(); dc.add(Restrictions.like("mocStd", "All"));
	 * dc.add(Restrictions.like("mocStd", mocID));
	 * 
	 * DetachedCriteria detachedCriteria =
	 * DetachedCriteria.forClass(Tbl13BarepumpgaModel.class);
	 * detachedCriteria.setProjection(Projections.property(
	 * "tbl13Barepumpga.barepumpgaId"));
	 * detachedCriteria.add(Restrictions.eq("tbl02Modelmaster.id",
	 * tbl02Modelmaster.getId()));
	 * 
	 * Criteria criteria =
	 * getSessionWFilter().createCriteria(Tbl13Barepumpga.class);
	 * criteria.add(Restrictions.eq("tbl01Pumptype.id",
	 * tbl02Modelmaster.getTbl01Pumptype().getId()));
	 * criteria.add(Restrictions.le("minStage", stage));
	 * criteria.add(Restrictions.ge("maxStage", stage));
	 * criteria.add(Subqueries.propertyIn("barepumpgaId", detachedCriteria));
	 * criteria.add(dc);
	 * 
	 * List<Tbl13Barepumpga> tbl13Barepumpgas = criteria.list(); if
	 * (tbl13Barepumpgas.size() > 0) tbl13Barepumpga = tbl13Barepumpgas.get(0);
	 * 
	 * } catch (Exception e) { log.error(e.getMessage(), e); } return
	 * tbl13Barepumpga; }
	 */

	/*
	 * @Override public Tbl15Pumpsetga getPumpsetga(Tbl02Modelmaster
	 * tbl02Modelmaster, Integer stage, String mocID) {
	 * 
	 * Tbl15Pumpsetga tbl15Pumpsetga = new Tbl15Pumpsetga(); try {
	 * 
	 * Disjunction dc = Restrictions.disjunction();
	 * dc.add(Restrictions.like("mocStd", "All"));
	 * dc.add(Restrictions.like("mocStd", mocID));
	 * 
	 * DetachedCriteria detachedCriteria =
	 * DetachedCriteria.forClass(Tbl15PumpsetgaModel.class);
	 * detachedCriteria.setProjection(Projections.property(
	 * "tbl15Pumpsetga.pumpsetGaId"));
	 * detachedCriteria.add(Restrictions.eq("tbl02Modelmaster.id",
	 * tbl02Modelmaster.getId()));
	 * 
	 * Criteria criteria = getSessionWFilter().createCriteria(Tbl15Pumpsetga.class);
	 * criteria.add(Restrictions.eq("tbl01Pumptype.id",
	 * tbl02Modelmaster.getTbl01Pumptype().getId()));
	 * criteria.add(Restrictions.le("minStage", stage));
	 * criteria.add(Restrictions.ge("maxStage", stage));
	 * criteria.add(Subqueries.propertyIn("pumpsetGaId", detachedCriteria));
	 * criteria.add(dc);
	 * 
	 * List<Tbl15Pumpsetga> tbl15Pumpsetgas = criteria.list(); if
	 * (tbl15Pumpsetgas.size() > 0) tbl15Pumpsetga = tbl15Pumpsetgas.get(0);
	 * 
	 * } catch (Exception e) { log.error(e.getMessage(), e); } return
	 * tbl15Pumpsetga; }
	 */

	/*
	 * @Override public Tbl05FlangeMaster getFlangeMaster(Integer pumpTypeId, String
	 * size, String flangetype) {
	 * 
	 * Tbl05FlangeMaster tbl05FlangeMaster = new Tbl05FlangeMaster(); try { Criteria
	 * criteria = getSessionWFilter().createCriteria(Tbl05FlangeMaster.class);
	 * criteria.add(Restrictions.eq("tbl01Pumptype.id", pumpTypeId)); if (size !=
	 * null) criteria.add(Restrictions.eq("size", Integer.valueOf(size)));
	 * criteria.add(Restrictions.eq("specification", flangetype));
	 * List<Tbl05FlangeMaster> tbl05FlangeMasterList = criteria.list(); if
	 * (tbl05FlangeMasterList.size() > 0) tbl05FlangeMaster =
	 * tbl05FlangeMasterList.get(0);
	 * 
	 * } catch (Exception e) { log.error(e.getMessage(), e); }
	 * 
	 * return tbl05FlangeMaster;
	 * 
	 * }
	 */

	/*
	 * @Override public Tbl18Csd getCsd(Tbl02Modelmaster tbl02Modelmaster, Integer
	 * stage, String mocID) {
	 * 
	 * Tbl18Csd tbl18Csd = new Tbl18Csd();
	 * 
	 * try {
	 * 
	 * Disjunction dc = Restrictions.disjunction();
	 * dc.add(Restrictions.like("mocStd", "All"));
	 * dc.add(Restrictions.like("mocStd", mocID));
	 * 
	 * DetachedCriteria detachedCriteria =
	 * DetachedCriteria.forClass(Tbl18CsdModel.class);
	 * detachedCriteria.setProjection(Projections.property("tbl18Csd.csdId"));
	 * detachedCriteria.add(Restrictions.eq("tbl02Modelmaster.id",
	 * tbl02Modelmaster.getId()));
	 * 
	 * Criteria criteria = getSessionWFilter().createCriteria(Tbl18Csd.class);
	 * criteria.add(Restrictions.eq("tbl01Pumptype.id",
	 * tbl02Modelmaster.getTbl01Pumptype().getId()));
	 * criteria.add(Restrictions.le("minStage", stage));
	 * criteria.add(Restrictions.ge("maxStage", stage));
	 * criteria.add(Subqueries.propertyIn("csdId", detachedCriteria));
	 * criteria.add(dc);
	 * 
	 * List<Tbl18Csd> tbl18Csds = new ArrayList<Tbl18Csd>(); tbl18Csds =
	 * criteria.list();
	 * 
	 * if (tbl18Csds.size() > 0) { tbl18Csd = tbl18Csds.get(0); }
	 * 
	 * } catch (Exception e) { log.error(e.getMessage(), e); } return tbl18Csd; }
	 */

	@Override
	public List<Tbl03GenericBomData> getGenericbomData(int seriesId, String shaftGroup, String mocStd, int stage,
			Integer variantId) {

		List<Tbl03GenericBomData> genericBomDatas = new ArrayList<>();

		try {

			Criteria c = getSessionWFilter().createCriteria(Tbl03GenericBomData.class)
					.createAlias("tbl03ItemMaster", "tbl03ItemMaster", Criteria.INNER_JOIN)
					.createAlias("tbl03GenericBom", "tbl03GenericBom")
					.createAlias("tbl03GenericBom.tbl01Pumptype", "tbl01Pumptype")
					.add(Restrictions.eq("tbl01Pumptype.id", seriesId))
					.add(Restrictions.eq("tbl03GenericBom.shaftGroup", shaftGroup))
					.add(Restrictions.ge("tbl03GenericBom.minStage", stage))
					.add(Restrictions.le("tbl03GenericBom.maxStage", stage));

			Criteria c1 = getSessionWFilter().createCriteria(Tbl03GenericBomData.class)
					.createAlias("tbl03ItemMaster", "tbl03ItemMaster", Criteria.INNER_JOIN)
					.createAlias("tbl03GenericBom", "tbl03GenericBom")
					.createAlias("tbl03GenericBom.tbl01Pumptype", "tbl01Pumptype")
					.add(Restrictions.eq("tbl01Pumptype.id", seriesId))
					.add(Restrictions.eq("tbl03GenericBom.shaftGroup", shaftGroup))
					.add(Restrictions.le("tbl03GenericBom.minStage", stage))
					.add(Restrictions.ge("tbl03GenericBom.maxStage", stage));
			c1.add(Restrictions.isNull("tbl03GenericBom.mocStd"));
			c1.add(Restrictions.isNull("tbl03GenericBom.variantId"));
			genericBomDatas = c1.list();

			Criteria c2 = getSessionWFilter().createCriteria(Tbl03GenericBomData.class)
					.createAlias("tbl03ItemMaster", "tbl03ItemMaster", Criteria.INNER_JOIN)
					.createAlias("tbl03GenericBom", "tbl03GenericBom")
					.createAlias("tbl03GenericBom.tbl01Pumptype", "tbl01Pumptype")
					.add(Restrictions.eq("tbl01Pumptype.id", seriesId))
					.add(Restrictions.eq("tbl03GenericBom.shaftGroup", shaftGroup))
					.add(Restrictions.ge("tbl03GenericBom.minStage", stage))
					.add(Restrictions.le("tbl03GenericBom.maxStage", stage));
			c2.add(Restrictions.isNull("tbl03GenericBom.mocStd"));
			c2.add(Restrictions.eq("tbl03GenericBom.variantId", variantId));
			genericBomDatas.addAll(c2.list());

			Criteria c3 = getSessionWFilter().createCriteria(Tbl03GenericBomData.class)
					.createAlias("tbl03ItemMaster", "tbl03ItemMaster", Criteria.INNER_JOIN)
					.createAlias("tbl03GenericBom", "tbl03GenericBom")
					.createAlias("tbl03GenericBom.tbl01Pumptype", "tbl01Pumptype")
					.add(Restrictions.eq("tbl01Pumptype.id", seriesId))
					.add(Restrictions.eq("tbl03GenericBom.shaftGroup", shaftGroup))
					.add(Restrictions.ge("tbl03GenericBom.minStage", stage))
					.add(Restrictions.le("tbl03GenericBom.maxStage", stage));
			c3.add(Restrictions.eq("tbl03GenericBom.mocStd", mocStd));
			c3.add(Restrictions.isNull("tbl03GenericBom.variantId"));
			genericBomDatas.addAll(c3.list());

			Criteria c4 = getSessionWFilter().createCriteria(Tbl03GenericBomData.class)
					.createAlias("tbl03ItemMaster", "tbl03ItemMaster", Criteria.INNER_JOIN)
					.createAlias("tbl03GenericBom", "tbl03GenericBom")
					.createAlias("tbl03GenericBom.tbl01Pumptype", "tbl01Pumptype")
					.add(Restrictions.eq("tbl01Pumptype.id", seriesId))
					.add(Restrictions.eq("tbl03GenericBom.shaftGroup", shaftGroup))
					.add(Restrictions.ge("tbl03GenericBom.minStage", stage))
					.add(Restrictions.le("tbl03GenericBom.maxStage", stage));
			c4.add(Restrictions.eq("tbl03GenericBom.mocStd", mocStd));
			c4.add(Restrictions.eq("tbl03GenericBom.variantId", variantId));
			genericBomDatas.addAll(c4.list());

		} catch (Exception e) {

			log.error(e.getMessage(), e);
		}
		return genericBomDatas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Tbl14PrimemoverMaster getPrimeMover(Tbl28SelectedPrimemover tbl28SelectedPrimemover) {

		Tbl14PrimemoverMaster tbl14PrimemoverMaster = new Tbl14PrimemoverMaster();
		List<Tbl14PrimemoverMaster> tbl14PrimemoverMasters = new ArrayList<Tbl14PrimemoverMaster>();

		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl14PrimemoverMaster.class);
			// criteria.add(Restrictions.eq("pole",
			// tbl28SelectedPrimemover.getPole()));
			criteria.add(Restrictions.eq("series", tbl28SelectedPrimemover.getSeries()));
			// criteria.add(Restrictions.eq("framesize",
			// tbl28SelectedPrimemover.getFrameSizeDim()));
			// criteria.add(Restrictions.eq("moc",
			// tbl28SelectedPrimemover.getMoc()));
			// criteria.add(Restrictions.eq("terminalBoxLoc",
			// tbl28SelectedPrimemover.getTerminalBoxLocation()));
			criteria.add(Restrictions.eq("power", tbl28SelectedPrimemover.getPower()));
			criteria.add(Restrictions.eq("speed", tbl28SelectedPrimemover.getSpeed()));
			// criteria.add(Restrictions.eq("manufacturer",
			// tbl27RequirementsPrimeMover.getManufacturer()));

			tbl14PrimemoverMasters = criteria.list();

			tbl14PrimemoverMaster = tbl14PrimemoverMasters.size() > 0 ? tbl14PrimemoverMasters.get(0) : null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl14PrimemoverMaster;
	}

}

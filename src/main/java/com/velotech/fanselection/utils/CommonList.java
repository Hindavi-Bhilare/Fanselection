
package com.velotech.fanselection.utils;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl23OfferShare;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl58UserCompany;

@Repository("commonList")
public class CommonList extends HibernateSession {

	static Logger log = LogManager.getLogger(CommonList.class.getName());

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private GenericDao genericDao;

	@SuppressWarnings("unchecked")
	public List<String> getSubordinatesIds() {

		List<String> subordinatesIds = new ArrayList<>();
		List<Object> reseultSet = new ArrayList<Object>();
		try {
			String loginId = velotechUtil.getLoginId();
			StringBuilder query = new StringBuilder();
			query.append("WITH");
			query.append("\nSubordinates");
			query.append("\nAS");
			query.append("\n(SELECT e.login_id, e.reporting_to");
			query.append("\nFROM tbl_55_userrelation AS e");
			query.append("\nWHERE e.reporting_to = '" + loginId + "'");
			query.append("\nUNION ALL");
			query.append("\nSELECT e.login_id, e.reporting_to");
			query.append("\nFROM tbl_55_userrelation AS e");
			query.append("\nINNER JOIN Subordinates AS sub ON sub.login_id = e.reporting_to)");
			query.append("\nSELECT s.login_id, s.reporting_to");
			query.append("\nFROM Subordinates AS s");

			reseultSet = getSessionWFilter().createSQLQuery(query.toString()).list();

			for (Object result : reseultSet) {
				subordinatesIds.add((String) ((Object[]) result)[0]);
			}
			subordinatesIds.add(loginId);
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
		}
		return subordinatesIds;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getUserCompanyIds() {

		List<Integer> ids = new ArrayList<>();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl58UserCompany.class);
			criteria.setProjection(Projections.property("tbl28CompanyMaster.id"));
			criteria.add(Restrictions.eq("tbl52Usermaster.loginId", velotechUtil.getLoginId()));

			ids = criteria.list();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ids;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getUsersSharedOfferIds() {

		List<Integer> ids = new ArrayList<>();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl23OfferShare.class);
			criteria.setProjection(Projections.property("tbl23OfferRev.id"));
			criteria.add(Restrictions.eq("tbl52Usermaster.loginId", velotechUtil.getLoginId()));

			ids = criteria.list();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ids;
	}

	public List<Integer> getOrganizationSubordinate(Integer organisationId) {

		HashMap<String, String> organizationMap = new HashMap<String, String>();
		List<Object> newList = new ArrayList<Object>();
		List<Integer> idList = new ArrayList();
		try {
			String query = "WITH Subordinates AS(SELECT e.organisationId, e.parentOrganisationId ";
			query += "FROM tbl_59_organisation_relation AS e ";
			query += "WHERE e.parentOrganisationId = '" + organisationId + "' ";
			query += "UNION ALL ";
			query += "SELECT e.organisationId, e.parentOrganisationId ";
			query += "FROM    tbl_59_organisation_relation AS e ";
			query += "INNER JOIN ";
			query += "Subordinates AS sub ";
			query += "ON sub.organisationId = e.parentOrganisationId)";
			query += "SELECT s.organisationId, s.parentOrganisationId ";
			query += "FROM Subordinates AS s";

			newList = getSessionWFilter().createSQLQuery(query).list();

			for (int i = 0; i < newList.size(); i++) {
				if (newList.get(i) != null) {
					idList.add((Integer) ((Object[]) newList.get(i))[0]);
				}
			}
			idList.add(organisationId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return idList;
	}

	public List<String> getUserIdsForOrganisation(String userId) {

		List<String> loginIdList = new ArrayList<String>();
		try {
			Tbl52Usermaster tbl52Usermaster = (Tbl52Usermaster) genericDao.getRecordById(Tbl52Usermaster.class, userId);
			List<Integer> organisationIds = getOrganizationSubordinate(tbl52Usermaster.getTbl59Organisation().getId());
			loginIdList = getSessionWFilter().createCriteria(Tbl52Usermaster.class).add(Restrictions.in("tbl59Organisation.id", organisationIds))
					.setProjection(Projections.property("loginId")).list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return loginIdList;
	}
}

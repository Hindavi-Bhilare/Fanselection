
package com.velotech.fanselection.admin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl59Organisation;
import com.velotech.fanselection.models.Tbl59OrganisationRelation;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.VelotechUtil;

@Repository
public class OrganizationRelationDaoImpl extends HibernateSession implements OrganizationRelationDao {

	private static Logger log = LogManager.getLogger(OrganizationRelationDaoImpl.class.getName());

	VelotechUtil velo = new VelotechUtil();

	@Override
	@SuppressWarnings("unchecked")
	public List<Tbl59Organisation> getLoginIdForRelation(Integer organizationId) {

		List<Tbl59Organisation> users = null;
		try {
			Conjunction cs = Restrictions.conjunction();
			cs.add(Restrictions.ne("id", organizationId));
			Criteria criteria = getSessionWFilter().createCriteria(Tbl59Organisation.class).add(cs);
			users = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return users;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getRelation1(Integer organizationId) {

		List<String> ans = null;
		try {
			ans = new ArrayList<String>();

			Conjunction cs = Restrictions.conjunction();
			cs.add(Restrictions.eq("tbl59OrganisationByOrganisationId.id", organizationId));

			Criteria criteria = getSessionWFilter().createCriteria(Tbl59OrganisationRelation.class).add(cs)
					.setProjection(Projections.projectionList().add(Projections.property("tbl59OrganisationByOrganisationId.id")));

			ans = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public ApplicationResponse getTbl59OrganisationRelationData(Integer parentId, String organisationCode, Pagination pagination) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl59OrganisationRelation.class);
			criteria.add(Restrictions.eq("tbl59OrganisationByParentOrganisationId.id", parentId));
			if (organisationCode != null && !organisationCode.equals("")) {
				criteria.createAlias("tbl59OrganisationByOrganisationId", "tbl59OrganisationByOrganisationId");
				criteria.add(Restrictions.like("tbl59OrganisationByOrganisationId.organisationCode", organisationCode));
			}
			ScrollableResults results = criteria.scroll();
			results.last();
			long total = results.getRowNumber() + 1;
			results.close();
			if (pagination != null)
				criteria.setFirstResult(pagination.getStart()).setMaxResults(pagination.getLimit());
			data = criteria.list();
			response.setData(data);
			response.setTotal(total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}
}

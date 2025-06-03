package com.velotech.fanselection.design.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.RequestWrapper;

@Repository
public class FanTypeDaoImpl extends HibernateSession implements FanTypeDao {

	static Logger log = LogManager.getLogger(FanTypeDaoImpl.class.getName());

	@Override
	public List<Tbl01Fantype> getSeries(RequestWrapper requestWrapper) {
		List<Tbl01Fantype> ans = new ArrayList<Tbl01Fantype>();
		try {

			ProjectionList proList = Projections.projectionList();
			proList.add(Projections.property("series"), "series");
			proList.add(Projections.property("id"), "id");
			proList.add(Projections.property("uuid"), "uuid");

			Criteria criteria = getSessionWFilter().createCriteria(Tbl01Fantype.class);

			if (requestWrapper.getSearchValue() != "") {
				criteria.add(Restrictions.like("series", requestWrapper.getSearchValue()));
			}
			criteria.setProjection(proList).setResultTransformer(Transformers.aliasToBean(Tbl01Fantype.class));

			ans = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

}

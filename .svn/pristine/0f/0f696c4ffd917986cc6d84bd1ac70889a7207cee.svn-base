
package com.velotech.fanselection.design.dao;

import java.util.ArrayList;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01VariantMaster;
import com.velotech.fanselection.models.Tbl02ModelPrice;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;

@Repository
public class ModelPriceDaoImpl extends HibernateSession implements ModelPriceDao {

	@Override
	public List<Tbl02ModelPrice> getRecords(final Class<?> o, final Object restrictions) {

		List<Tbl02ModelPrice> list = new ArrayList<>();
		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(o);
			if (restrictions != null)
				criteria.add((Conjunction) restrictions);
			list = criteria.list();
			long total = list.size();
			data = list;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public List<Tbl01VariantMaster> getRecordss(Class<?> o, Object restrictions) {

		List<Tbl01VariantMaster> list = new ArrayList<>();
		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(o);
			if (restrictions != null)
				criteria.add((Conjunction) restrictions);
			list = criteria.list();
			long total = list.size();
			data = list;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public ApplicationResponse getModelPriceData(RequestWrapper requestWrapper) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Tbl01CentrifugalModelMaster.class);
			detachedCriteria.add(Restrictions.eq("tbl01Fantype.id", Integer.parseInt(String.valueOf(requestWrapper.getSearchValue()))));
			detachedCriteria.setProjection(Projections.distinct(Projections.property("id")));

			Criteria criteria = getSessionWFilter().createCriteria(Tbl02ModelPrice.class);
			criteria.add(Subqueries.propertyIn("tbl02Modelmaster.id", detachedCriteria));
			ScrollableResults results = criteria.scroll();
			results.last();
			long total = results.getRowNumber() + 1;
			results.close();
			Pagination pagination = requestWrapper.getPagination();
			criteria.setFirstResult(pagination.getStart()).setMaxResults(pagination.getLimit());
			data = criteria.list();
			response.setData(data);
			response.setTotal(total);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return response;
	}

}
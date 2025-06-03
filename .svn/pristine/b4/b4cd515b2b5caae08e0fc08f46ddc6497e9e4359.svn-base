package com.velotech.fanselection.design.dao;

import org.apache.logging.log4j.LogManager;


import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.Pagination;

@Repository
public class CentrifugalModelMasterDaoImpl extends HibernateSession implements CentrifugalModelMasterDao {
	
	private static Logger log = LogManager.getLogger(CentrifugalModelMasterDaoImpl.class.getName());

	@Override
	public ApplicationResponse getCentrifugalModelMasterData(String searchProperty, String searchValue, Pagination pagination) {
		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(Tbl01CentrifugalModelMaster.class);
			if (searchValue != null)
			{
				if(searchProperty.equals("fanType"))
				{
					criteria.add(Restrictions.like("tbl01Fantype.series", searchValue, MatchMode.ANYWHERE));
					criteria.createAlias("tbl01Fantype", "tbl01Fantype");
				}
				else
					criteria.add(Restrictions.like(searchProperty, searchValue, MatchMode.ANYWHERE));
			}
			criteria.addOrder(Order.desc("id"));
			
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

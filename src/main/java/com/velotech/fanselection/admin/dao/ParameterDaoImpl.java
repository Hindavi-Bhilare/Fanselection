package com.velotech.fanselection.admin.dao;



import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl90ParameterMaster;
import com.velotech.fanselection.utils.HibernateSession;


@Repository
public class ParameterDaoImpl extends HibernateSession implements ParameterMasterDao{
	
	static Logger log = Logger.getLogger(ParameterDaoImpl.class.getName());
	

	@Override
	public boolean deleteAll(List<String> parameter) {
		boolean ans = false;
		try {
			Criteria criteria = getSession().createCriteria(Tbl90ParameterMaster.class).add(Restrictions.in("parameter", parameter));
			List<?> list = criteria.list();
			getHibernateTemplate().deleteAll(list);
		//transactionHistory("Delete",list.get(0).getClass().getSimpleName(), list.toString());
			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}
	

}

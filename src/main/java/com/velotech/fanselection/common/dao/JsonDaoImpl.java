
package com.velotech.fanselection.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl90JsonMaster;
import com.velotech.fanselection.models.Tbl90ParameterValue;
import com.velotech.fanselection.utils.HibernateSession;

@Repository
public class JsonDaoImpl extends HibernateSession implements JsonDao {

	static Logger log = LogManager.getLogger(JsonDaoImpl.class.getName());

	@Override
	public Tbl90JsonMaster getJsonMaster(String jsonName) {

		Tbl90JsonMaster ans = null;
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl90JsonMaster.class);
			criteria.add(Restrictions.eq("tbl90ParameterMaster.parameter", jsonName));
			criteria.add(Restrictions.isNull("uuid"));

			List<Tbl90JsonMaster> jsonMasters = criteria.list();
			if (jsonMasters.size() > 0)
				ans = jsonMasters.get(0);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public Tbl90JsonMaster getJsonMaster(String jsonName, String uuid) {

		Tbl90JsonMaster ans = null;
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl90JsonMaster.class);
			criteria.add(Restrictions.eq("tbl90ParameterMaster.parameter", jsonName));
			criteria.add(Restrictions.eq("uuid", uuid));

			List<Tbl90JsonMaster> jsonMasters = criteria.list();
			if (jsonMasters.size() > 0)
				ans = jsonMasters.get(0);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public List<Tbl90ParameterValue> getJsonParameterValue(String uuid) {

		List<Tbl90ParameterValue> ans = new ArrayList<>();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl90ParameterValue.class).addOrder(Order.asc("sequence"));
			criteria.add(Restrictions.eq("uuid", uuid));
			criteria.add(Restrictions.eq("isUserInput", false));
			ans = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public List<Tbl90ParameterValue> getJsonParameterValue(List<String> uuids) {

		List<Tbl90ParameterValue> ans = new ArrayList<>();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl90ParameterValue.class);
			criteria.add(Restrictions.in("uuid", uuids));
			criteria.add(Restrictions.eq("isUserInput", false));
			ans = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public List<Tbl90ParameterValue> getJsonParameterValue() {

		List<Tbl90ParameterValue> ans = new ArrayList<>();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl90ParameterValue.class);
			criteria.add(Restrictions.isNull("uuid"));
			criteria.add(Restrictions.eq("isUserInput", false));
			ans = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}
	
	@Override
	public List<Tbl90ParameterValue> getJsonParameterValueUser() {

		List<Tbl90ParameterValue> ans = new ArrayList<>();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl90ParameterValue.class);
			criteria.add(Restrictions.isNull("uuid"));
			criteria.add(Restrictions.eq("isUserInput", true));
			ans = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}
	
	@Override
	public List<Tbl90ParameterValue> getJsonParameterValueUser(List<String> uuids) {

		List<Tbl90ParameterValue> ans = new ArrayList<>();
		try {

			Criteria criteria = getSessionWFilter().createCriteria(Tbl90ParameterValue.class);
			criteria.add(Restrictions.in("uuid", uuids));
			criteria.add(Restrictions.eq("isUserInput", true));
			ans = criteria.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

}

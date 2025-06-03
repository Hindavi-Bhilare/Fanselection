
package com.velotech.fanselection.generic.dao;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ComboBox;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.JavaUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.SqlOrder;
import com.velotech.fanselection.utils.VelotechUtil;

@Repository
public class GenericDaoImpl extends HibernateSession implements GenericDao {

	private static Logger log = LogManager.getLogger(GenericDaoImpl.class.getName());

	@Autowired
	private VelotechUtil velotechUtil;

	@Override
	public boolean save(Object object) {

		boolean ans = false;
		try {
			Method setCreatedBy = object.getClass().getMethod("setCreatedBy", String.class);
			setCreatedBy.invoke(object, velotechUtil.getLoginId());

			Method setCreatedDate = object.getClass().getMethod("setCreatedDate", Date.class);
			setCreatedDate.invoke(object, new Date());

			Method setCompany = object.getClass().getMethod("setCompany", String.class);
			setCompany.invoke(object, velotechUtil.getCompany());

			getSession().save(object);
			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public boolean saveAll(List<?> objects) {

		boolean ans = false;
		try {
			for (Object object : objects) {
				Method setCreatedBy = object.getClass().getMethod("setCreatedBy", String.class);
				setCreatedBy.invoke(object, velotechUtil.getLoginId());

				Method setCreatedDate = object.getClass().getMethod("setCreatedDate", Date.class);
				setCreatedDate.invoke(object, new Date());

				Method setCompany = object.getClass().getMethod("setCompany", String.class);
				setCompany.invoke(object, velotechUtil.getCompany());

				getSession().save(object);
			}

			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public boolean update(Object object) {

		boolean ans = false;
		try {
			Method setModifiedBy = object.getClass().getMethod("setModifiedBy", String.class);
			setModifiedBy.invoke(object, velotechUtil.getLoginId());

			Method setModifiedDate = object.getClass().getMethod("setModifiedDate", Date.class);
			setModifiedDate.invoke(object, new Date());
			
			Method setCompany = object.getClass().getMethod("setCompany", String.class);
			setCompany.invoke(object, velotechUtil.getCompany());


			getSession().update(object);
			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}
	
	@Override
	public boolean updateAll(List<?> objects) {

		boolean ans = false;
		try {
			for (Object object : objects) {
				Method setModifiedBy = object.getClass().getMethod("setModifiedBy", String.class);
				setModifiedBy.invoke(object, velotechUtil.getLoginId());

				Method setModifiedDate = object.getClass().getMethod("setModifiedDate", Date.class);
				setModifiedDate.invoke(object, new Date());

				getSession().update(object);
			}
			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public boolean saveOrUpdate(Object object) {

		boolean ans = false;
		try {
			Method setCreatedBy = object.getClass().getMethod("setCreatedBy", String.class);
			setCreatedBy.invoke(object, velotechUtil.getLoginId());

			Method setCreatedDate = object.getClass().getMethod("setCreatedDate", Date.class);
			setCreatedDate.invoke(object, new Date());

			Method setModifiedBy = object.getClass().getMethod("setModifiedBy", String.class);
			setModifiedBy.invoke(object, velotechUtil.getLoginId());

			Method setModifiedDate = object.getClass().getMethod("setModifiedDate", Date.class);
			setModifiedDate.invoke(object, new Date());

			Method setCompany = object.getClass().getMethod("setCompany", String.class);
			setCompany.invoke(object, velotechUtil.getCompany());

			getSession().saveOrUpdate(object);
			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public boolean delete(Object object) {

		boolean ans = false;
		try {
			getSession().delete(object);
			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public boolean deleteAll(Object objects) {

		boolean ans = false;
		try {
			Collection<?> collection = (Collection<?>) objects;
			getHibernateTemplate().deleteAll(collection);
			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public boolean deleteAll(final Class<?> o, final List<Integer> ids) {

		boolean ans = false;
		try {
			Criteria criteria = getSession().createCriteria(o).add(Restrictions.in("id", ids));
			List<?> list = criteria.list();
			getHibernateTemplate().deleteAll(list);
			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public Object getRecordById(final Class<?> o, final Integer id) {

		Object object = null;
		try {
			object = getSession().get(o, id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return object;
	}
	@Override
	public Object getRecordById(final Class<?> o, final String id) {

		Object object = null;
		try {
			object = getSession().get(o, id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return object;
	}

	@Override
	public Object getRecordByIds(final Class<?> o, final List<Integer> ids) {

		Object data = null;
		try {
			Criteria criteria = getSession().createCriteria(o).add(Restrictions.in("id", ids));
			List<?> list = criteria.list();
			data = list;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

	@Override
	public Object getRecords(final Class<?> o) {

		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(o);
			List<?> list = criteria.list();
			data = list;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

	@Override
	public Object getRecords(final Class<?> o, final Object restrictions) {

		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(o);
			if (restrictions != null)
				criteria.add((Conjunction) restrictions);
			List<?> list = criteria.list();
			data = list;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

	@Override
	public ApplicationResponse getRecords(final Class<?> o, final Object restrictions, final Pagination pagination) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(o);
			if (restrictions != null)
				criteria.add((Conjunction) restrictions);
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

	@Override
	public ApplicationResponse getRecords(final Class<?> o, final Object restrictions, final Pagination pagination, final SqlOrder sqlOrder) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(o);
			if (restrictions != null)
				criteria.add((Conjunction) restrictions);

			ScrollableResults results = criteria.scroll();
			results.last();
			long total = results.getRowNumber() + 1;
			results.close();

			if (pagination != null)
				criteria.setFirstResult(pagination.getStart()).setMaxResults(pagination.getLimit());

			if (sqlOrder != null) {
				if (sqlOrder.getSortDirection().equalsIgnoreCase("ASC"))
					criteria.addOrder(Order.asc(sqlOrder.getSortProperty()));
				else
					criteria.addOrder(Order.desc(sqlOrder.getSortProperty()));
			}

			data = criteria.list();
			response.setData(data);
			response.setTotal(total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public ApplicationResponse getRecords(final Class<?> o, final Object restrictions, final Pagination pagination, final List<SqlOrder> sqlOrders) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(o);
			if (restrictions != null)
				criteria.add((Conjunction) restrictions);

			ScrollableResults results = criteria.scroll();
			results.last();
			long total = results.getRowNumber() + 1;
			results.close();

			if (pagination != null)
				criteria.setFirstResult(pagination.getStart()).setMaxResults(pagination.getLimit());

			if (sqlOrders != null) {
				for (SqlOrder order : sqlOrders) {
					if (order.getSortDirection().equalsIgnoreCase("ASC"))
						criteria.addOrder(Order.asc(order.getSortProperty()));
					else
						criteria.addOrder(Order.desc(order.getSortProperty()));
				}
			}

			data = criteria.list();
			response.setData(data);
			response.setTotal(total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public ApplicationResponse getComboRecords(Class<?> o, String displayField, String valueField, Object restrictions) {

		ApplicationResponse response = new ApplicationResponse();
		Object data = null;
		try {
			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.distinct(Projections.property(displayField)), "label");
			projectionList.add(Projections.property(valueField), "value");
			Criteria criteria = getSessionWFilter().createCriteria(o);
			criteria.setProjection(projectionList);
			if (restrictions != null)
				criteria.add((Conjunction) restrictions);
			List<?> list = criteria.setResultTransformer(Transformers.aliasToBean(ComboBox.class)).list();
			long total = list.size();
			data = list;
			response.setData(data);
			response.setTotal(total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public Object getRecordsByParentId(Class<?> o, String parentProperty, Object parentId) {

		Object data = null;
		try {
			String[] properties = parentProperty.split("\\.");
			String classSimpleName = JavaUtil.getFieldsClassType(o, properties[0]);
			Class<?> parentClass = Class.forName(classSimpleName);
			String parentFieldType = JavaUtil.getFieldType(parentClass, properties[1]);
			Object parentValue = JavaUtil.getValueOfType(parentFieldType, parentId);

			Criteria criteria = getSession().createCriteria(o);
			criteria.add(Restrictions.eq(parentProperty, parentValue));
			List<?> list = criteria.list();
			data = list;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

	@Override
	public Object findByParam(Class<?> o, String property, String value) {

		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(o);
			criteria.add(Restrictions.eq(property, value));
			List<?> list = criteria.list();
			data = list;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

	@Override
	public Object findByParam(Class<?> o, String property, List<String> values) {

		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(o);
			criteria.add(Restrictions.in(property, values));
			List<?> list = criteria.list();
			data = list;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

	@Override
	public Object getUniqueRecord(Class<?> o, String property, String value) {

		Object data = null;
		try {
			Criteria criteria = getSessionWFilter().createCriteria(o);
			criteria.add(Restrictions.eq(property, value));
			data = criteria.uniqueResult();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

	@Override
	public boolean executeUpdate(String queryString) {

		boolean ans = false;
		try {
			getSession().createSQLQuery(queryString).executeUpdate();
			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public List<String> getTables() {

		List<String> temPList = new ArrayList<String>();
		try {
			@SuppressWarnings("rawtypes")
			Map m = getSessionFactory().getAllClassMetadata();
			List<String> classList = new ArrayList<String>();
			classList = castMapToList(m);
			for (Integer j = 0; j < classList.size(); j++) {
				AbstractEntityPersister aep = (AbstractEntityPersister) m.get(classList.get(j));
				String tableName = aep.getTableName().replace("#", "");
				temPList.add(tableName);
			}
			Collections.sort(temPList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return temPList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<String> castMapToList(Map tempMap) {

		List<String> tempList = new ArrayList<String>();
		try {
			Set<String> atempKeySet = tempMap.keySet();
			for (Iterator it = atempKeySet.iterator(); it.hasNext();) {
				String key = (String) it.next();
				tempList.add(key);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tempList;
	}

}

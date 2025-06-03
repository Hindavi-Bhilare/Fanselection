
package com.velotech.fanselection.utils;

import org.hibernate.Filter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;


public abstract class HibernateSession {

	@Autowired
	private SessionFactory sessionFactory;

	private VelotechUtil velotechUtil = new VelotechUtil();

	protected Session getSessionWFilter() {

		Filter filter = getSessionFactory().getCurrentSession().enableFilter("company");
		filter.setParameter("company", velotechUtil.getCompany());

		return getSessionFactory().getCurrentSession();

	}

	protected Session getSession() {

		return getSessionFactory().getCurrentSession();
	}

	protected HibernateTemplate getHibernateTemplate() {

		return new HibernateTemplate(getSessionFactory());
	}

	protected SessionFactory getSessionFactory() {

		return sessionFactory;
	}
}

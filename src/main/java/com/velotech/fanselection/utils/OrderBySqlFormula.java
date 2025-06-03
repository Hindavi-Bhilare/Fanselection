
package com.velotech.fanselection.utils;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

/**
 * Extends {@link org.hibernate.criterion.Order} to allow ordering by an SQL
 * formula passed by the user. Is simply appends the <code>sqlFormula</code>
 * passed by the user to the resulting SQL query, without any verification.
 * 
 * @author Mithlesh Singh
 * @since March 30, 2014
 */
public class OrderBySqlFormula extends Order {

	private static final long serialVersionUID = 2240419110825487797L;

	private String sqlFormula;

	protected OrderBySqlFormula(String sqlFormula) {
		super(sqlFormula, true);
		this.sqlFormula = sqlFormula;
	}

	@Override
	public String toString() {

		return sqlFormula;
	}

	@Override
	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {

		return sqlFormula;
	}

	public static Order sqlFormula(String sqlFormula) {

		return new OrderBySqlFormula(sqlFormula);
	}
}

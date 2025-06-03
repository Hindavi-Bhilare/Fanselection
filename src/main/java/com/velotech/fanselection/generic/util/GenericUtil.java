
package com.velotech.fanselection.generic.util;

import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import com.velotech.fanselection.utils.JavaUtil;

public class GenericUtil {

	public static Conjunction getConjuction(Class<?> modelClass, List<SearchCriteria> searchCriterias) {

		Conjunction conjunction = Restrictions.conjunction();
		if (searchCriterias != null)
			for (SearchCriteria searchCriteria : searchCriterias) {
				if (JavaUtil.getFieldType(modelClass, searchCriteria.getSearchProperty()).equals("Date")) {
					conjunction.add(
							Restrictions.between(searchCriteria.getSearchProperty(), searchCriteria.getStartDate(), searchCriteria.getEndDate()));
				} else
					conjunction.add(getRestriction(modelClass, searchCriteria));
			}
		return conjunction;
	}

	public static Object getConjuction(Class<?> modelClass, SearchCriteria searchCriteria) {

		Conjunction conjunction = Restrictions.conjunction();
		if (searchCriteria != null)
			conjunction.add(getRestriction(modelClass, searchCriteria));
		return conjunction;
	}

	public static Disjunction getDisjunction(Class<?> modelClass, List<SearchCriteria> searchCriterias) {

		Disjunction disjunction = Restrictions.disjunction();
		if (searchCriterias != null)
			for (SearchCriteria searchCriteria : searchCriterias) {
				disjunction.add(getRestriction(modelClass, searchCriteria));
			}
		return disjunction;
	}

	public static SimpleExpression getRestriction(Class<?> modelClass, SearchCriteria searchCriteria) {

		switch (JavaUtil.getFieldType(modelClass, searchCriteria.getSearchProperty())) {
		case "String":
		case "Short":
		case "short":
			return Restrictions.like(searchCriteria.getSearchProperty(), (String) searchCriteria.getSearchValue(), MatchMode.ANYWHERE);
		case "boolean":
		case "Boolean":
		case "int":
		case "Integer":
		case "double":
		case "Double":
		case "long":
		case "Long":
			return Restrictions.eq(searchCriteria.getSearchProperty(),
					JavaUtil.getValueOfType(JavaUtil.getFieldType(modelClass, searchCriteria.getSearchProperty()), searchCriteria.getSearchValue()));
		case "parent":
			return Restrictions.eq(searchCriteria.getSearchProperty(), JavaUtil.getValueOfType("Integer", searchCriteria.getSearchValue()));
		default:
			return null;
		}
	}

}

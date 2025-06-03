
package com.velotech.fanselection.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SqlOrder {

	private String sortProperty;

	private String sortDirection;// "ASC" or "DESC"

	public SqlOrder(String sort) {

		JSONArray sortArray;
		try {
			if (sort != null) {
				sortArray = new JSONArray(sort);
				JSONObject obj = sortArray.getJSONObject(0);
				this.sortProperty = (String) obj.get("property");
				this.sortDirection = (String) obj.get("direction");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public SqlOrder(String property, String direction) {
		this.sortProperty = property;
		this.sortDirection = direction;
	}

	public String getSortProperty() {

		return sortProperty;
	}

	public void setSortProperty(String sortProperty) {

		this.sortProperty = sortProperty;
	}

	public String getSortDirection() {

		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {

		this.sortDirection = sortDirection;
	}

}

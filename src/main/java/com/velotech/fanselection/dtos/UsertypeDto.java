
package com.velotech.fanselection.dtos;

import java.util.List;

public class UsertypeDto {

	private int id;

	private String userType;

	private List<Integer> fanTypeIds;

	private List<String> fanSeries;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getUserType() {

		return userType;
	}

	public void setUserType(String userType) {

		this.userType = userType;
	}

	public List<Integer> getFanTypeIds() {
		return fanTypeIds;
	}

	public void setFanTypeIds(List<Integer> fanTypeIds) {
		this.fanTypeIds = fanTypeIds;
	}

	public List<String> getFanSeries() {
		return fanSeries;
	}

	public void setFanSeries(List<String> fanSeries) {
		this.fanSeries = fanSeries;
	}

	
}

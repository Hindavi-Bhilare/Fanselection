
package com.velotech.fanselection.dtos;

public class DepartmentDto {

	private int id;

	private String department;

	private Double minimumServiceFactor;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getDepartment() {

		return department;
	}

	public void setDepartment(String department) {

		this.department = department;
	}

	public Double getMinimumServiceFactor() {

		return minimumServiceFactor;
	}

	public void setMinimumServiceFactor(Double minimumServiceFactor) {

		this.minimumServiceFactor = minimumServiceFactor;
	}

}


package com.velotech.fanselection.dtos;

public class UserRelationDto {

	private int id;

	private String loginId;

	private String reportingToId;

	private String reportingTo;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getLoginId() {

		return loginId;
	}

	public void setLoginId(String loginId) {

		this.loginId = loginId;
	}

	public String getReportingToId() {

		return reportingToId;
	}

	public void setReportingToId(String reportingToId) {

		this.reportingToId = reportingToId;
	}

	public String getReportingTo() {

		return reportingTo;
	}

	public void setReportingTo(String reportingTo) {

		this.reportingTo = reportingTo;
	}

}


package com.velotech.fanselection.dashboard.dto;

import java.util.Date;

public class OfferDto {

	private int id;

	private Date offerDate;

	private String offerNo;

	private String rev;

	private String customer;

	private String project;

	private Double value;

	private String status;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public Date getOfferDate() {

		return offerDate;
	}

	public void setOfferDate(Date offerDate) {

		this.offerDate = offerDate;
	}

	public String getOfferNo() {

		return offerNo;
	}

	public void setOfferNo(String offerNo) {

		this.offerNo = offerNo;
	}

	public String getRev() {

		return rev;
	}

	public void setRev(String rev) {

		this.rev = rev;
	}

	public String getCustomer() {

		return customer;
	}

	public void setCustomer(String customer) {

		this.customer = customer;
	}

	public String getProject() {

		return project;
	}

	public void setProject(String project) {

		this.project = project;
	}

	public Double getValue() {

		return value;
	}

	public void setValue(Double value) {

		this.value = value;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

}

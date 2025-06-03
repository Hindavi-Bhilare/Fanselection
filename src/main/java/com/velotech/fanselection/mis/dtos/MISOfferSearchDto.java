
package com.velotech.fanselection.mis.dtos;

import java.util.Date;

public class MISOfferSearchDto {

	private String customerName;

	private String offerStatus;

	private String segment;

	private String project;

	private String user;

	private String salesPerson;

	private String currency;

	private Date offerDate;

	public String getCustomerName() {

		return customerName;
	}

	public void setCustomerName(String customerName) {

		this.customerName = customerName;
	}

	public String getOfferStatus() {

		return offerStatus;
	}

	public void setOfferStatus(String offerStatus) {

		this.offerStatus = offerStatus;
	}

	public String getSegment() {

		return segment;
	}

	public void setSegment(String segment) {

		this.segment = segment;
	}

	public String getProject() {

		return project;
	}

	public void setProject(String project) {

		this.project = project;
	}

	public String getSalesPerson() {

		return salesPerson;
	}

	public void setSalesPerson(String salesPerson) {

		this.salesPerson = salesPerson;
	}

	public String getCurrency() {

		return currency;
	}

	public void setCurrency(String currency) {

		this.currency = currency;
	}

	public String getUser() {

		return user;
	}

	public void setUser(String user) {

		this.user = user;
	}

	public Date getOfferDate() {

		return offerDate;
	}

	public void setOfferDate(Date offerDate) {

		this.offerDate = offerDate;
	}

}


package com.velotech.fanselection.dtos;

public class CustomerContactDto {

	private int id;

	private Integer customerId;

	private String customer;

	private String contactPerson;

	private String email;

	private String contactNumber;

	private String designation;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public Integer getCustomerId() {

		return customerId;
	}

	public void setCustomerId(Integer customerId) {

		this.customerId = customerId;
	}

	public String getCustomer() {

		return customer;
	}

	public void setCustomer(String customer) {

		this.customer = customer;
	}

	public String getContactPerson() {

		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {

		this.contactPerson = contactPerson;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getContactNumber() {

		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {

		this.contactNumber = contactNumber;
	}

	public String getDesignation() {

		return designation;
	}

	public void setDesignation(String designation) {

		this.designation = designation;
	}

}

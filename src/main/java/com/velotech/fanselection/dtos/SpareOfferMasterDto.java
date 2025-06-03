
package com.velotech.fanselection.dtos;

import java.util.Date;

public class SpareOfferMasterDto {

	private int id;

	private int customerId;

	private String customerName;

	private int companyId;

	private String companyName;

	private Date enquiryDate;

	private String enquiryReference;

	private Date quotationDate;

	private String logNo;

	private String pumpModel;

	private Boolean showDiscount;

	private Boolean showPrice;

	private Double total;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public int getCustomerId() {

		return customerId;
	}

	public void setCustomerId(int customerId) {

		this.customerId = customerId;
	}

	public String getCustomerName() {

		return customerName;
	}

	public void setCustomerName(String customerName) {

		this.customerName = customerName;
	}

	public int getCompanyId() {

		return companyId;
	}

	public void setCompanyId(int companyId) {

		this.companyId = companyId;
	}

	public String getCompanyName() {

		return companyName;
	}

	public void setCompanyName(String companyName) {

		this.companyName = companyName;
	}

	public Date getEnquiryDate() {

		return enquiryDate;
	}

	public void setEnquiryDate(Date enquiryDate) {

		this.enquiryDate = enquiryDate;
	}

	public String getEnquiryReference() {

		return enquiryReference;
	}

	public void setEnquiryReference(String enquiryReference) {

		this.enquiryReference = enquiryReference;
	}

	public Date getQuotationDate() {

		return quotationDate;
	}

	public void setQuotationDate(Date quotationDate) {

		this.quotationDate = quotationDate;
	}

	public String getLogNo() {

		return logNo;
	}

	public void setLogNo(String logNo) {

		this.logNo = logNo;
	}

	public String getPumpModel() {

		return pumpModel;
	}

	public void setPumpModel(String pumpModel) {

		this.pumpModel = pumpModel;
	}

	public Boolean getShowDiscount() {

		return showDiscount;
	}

	public void setShowDiscount(Boolean showDiscount) {

		this.showDiscount = showDiscount;
	}

	public Boolean getShowPrice() {

		return showPrice;
	}

	public void setShowPrice(Boolean showPrice) {

		this.showPrice = showPrice;
	}

	public Double getTotal() {

		return total;
	}

	public void setTotal(Double total) {

		this.total = total;
	}

}

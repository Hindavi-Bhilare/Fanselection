
package com.velotech.fanselection.offer.dto;

import java.util.Date;

public class OfferStatusHistoryDto {

	private int id;

	private Integer offerRevId;

	private String status;

	private String createdBy;

	private Date createdDate;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public Integer getOfferRevId() {

		return offerRevId;
	}

	public void setOfferRevId(Integer offerRevId) {

		this.offerRevId = offerRevId;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public String getCreatedBy() {

		return createdBy;
	}

	public void setCreatedBy(String createdBy) {

		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {

		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {

		this.createdDate = createdDate;
	}

}

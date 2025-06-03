
package com.velotech.fanselection.offer.dto;

import java.util.List;

public class OfferStatusUpdateDto {

	private Integer offerRevId;

	private String status;

	private String orderNo;

	private List<OfferFanStatusDto> offerFans;

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

	public String getOrderNo() {

		return orderNo;
	}

	public void setOrderNo(String orderNo) {

		this.orderNo = orderNo;
	}

	public List<OfferFanStatusDto> getOfferFans() {

		return offerFans;
	}

	public void setOfferFans(List<OfferFanStatusDto> offerFans) {

		this.offerFans = offerFans;
	}

}

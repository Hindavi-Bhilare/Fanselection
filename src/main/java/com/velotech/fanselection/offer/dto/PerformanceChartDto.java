
package com.velotech.fanselection.offer.dto;

public class PerformanceChartDto {

	private Integer offerPumpDpId;

	private Integer OfferFanId;

	private String[] flow;

	private String[] head;

	private String[] speed;

	public Integer getOfferPumpDpId() {

		return offerPumpDpId;
	}

	public void setOfferPumpDpId(Integer offerPumpDpId) {

		this.offerPumpDpId = offerPumpDpId;
	}

	public Integer getOfferFanId() {
		return OfferFanId;
	}

	public void setOfferFanId(Integer offerFanId) {
		OfferFanId = offerFanId;
	}

	public String[] getFlow() {

		return flow;
	}

	public void setFlow(String[] flow) {

		this.flow = flow;
	}

	public String[] getHead() {

		return head;
	}

	public void setHead(String[] head) {

		this.head = head;
	}

	public String[] getSpeed() {

		return speed;
	}

	public void setSpeed(String[] speed) {

		this.speed = speed;
	}

}

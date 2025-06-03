
package com.velotech.fanselection.dashboard.dto;

public class OfferStatusCount {

	private Object status;

	private Object count;

	public OfferStatusCount() {
		super();
	}

	public OfferStatusCount(Object status, Object count) {
		super();
		this.status = status;
		this.count = count;
	}

	public Object getStatus() {

		return status;
	}

	public void setStatus(Object status) {

		this.status = status;
	}

	public Object getCount() {

		return count;
	}

	public void setCount(Object count) {

		this.count = count;
	}

}


package com.velotech.fanselection.utils;

public class Pagination {

	public Integer start;

	public Integer limit;

	public Pagination() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pagination(Integer start, Integer limit) {
		this.start = start;
		this.limit = limit;
	}

	public Integer getStart() {

		return start;
	}

	public void setStart(Integer start) {

		if (start < 0)
			this.start = 0;
		else
			this.start = start;
	}

	public Integer getLimit() {

		return limit;
	}

	public void setLimit(Integer limit) {

		if (limit < 0)
			this.limit = 0;
		else
			this.limit = limit;
	}

}

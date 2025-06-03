
package com.velotech.fanselection.dtos;

public class OfferRevTermsAndConditionsDto {

	private int id;

	private Integer offerRevId;

	private String termType;

	private String description;

	private Integer lineNum;

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

	public String getTermType() {

		return termType;
	}

	public void setTermType(String termType) {

		this.termType = termType;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public Integer getLineNum() {

		return lineNum;
	}

	public void setLineNum(Integer lineNum) {

		this.lineNum = lineNum;
	}

}

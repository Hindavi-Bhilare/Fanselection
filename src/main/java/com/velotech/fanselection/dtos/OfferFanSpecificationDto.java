
package com.velotech.fanselection.dtos;

public class OfferFanSpecificationDto {

	private int id;

	private Integer offerFanId;

	private String parameter;

	private String value;

	private Boolean isActive;

	private Integer sequence;

	private Boolean showInQuotation;

	private Boolean showInDataSheet;

	private String groupColumn;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public Integer getOfferFanId() {

		return offerFanId;
	}

	public void setOfferFanId(Integer offerFanId) {

		this.offerFanId = offerFanId;
	}

	public String getParameter() {

		return parameter;
	}

	public void setParameter(String parameter) {

		this.parameter = parameter;
	}

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}

	public Boolean getIsActive() {

		return isActive;
	}

	public void setIsActive(Boolean isActive) {

		this.isActive = isActive;
	}

	public Integer getSequence() {

		return sequence;
	}

	public void setSequence(Integer sequence) {

		this.sequence = sequence;
	}

	public Boolean getShowInQuotation() {

		return showInQuotation;
	}

	public void setShowInQuotation(Boolean showInQuotation) {

		this.showInQuotation = showInQuotation;
	}

	public Boolean getShowInDataSheet() {

		return showInDataSheet;
	}

	public void setShowInDataSheet(Boolean showInDataSheet) {

		this.showInDataSheet = showInDataSheet;
	}

	public String getGroupColumn() {

		return groupColumn;
	}

	public void setGroupColumn(String groupColumn) {

		this.groupColumn = groupColumn;
	}

}

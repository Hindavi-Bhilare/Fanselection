
package com.velotech.fanselection.dtos;

public class SelectedPricingDetailsDto {

	private Integer id;

	private Integer selectedPricingId;

	private String parameter;

	private String value;

	private Boolean showInSummary;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public Integer getSelectedPricingId() {

		return selectedPricingId;
	}

	public void setSelectedPricingId(Integer selectedPricingId) {

		this.selectedPricingId = selectedPricingId;
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

	public Boolean getShowInSummary() {

		return showInSummary;
	}

	public void setShowInSummary(Boolean showInSummary) {

		this.showInSummary = showInSummary;
	}

}

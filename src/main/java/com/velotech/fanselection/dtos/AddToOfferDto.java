
package com.velotech.fanselection.dtos;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.velotech.fanselection.selection.models.AdditionalCentrifugalFanAttributes;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddToOfferDto {

	private Integer selectedFanId;

	private Integer offerRevId;

	private String tagNo;

	private Integer primeMoverId;
	
	private List<UserInputDto> userInputDto;

	private String moc;
	
	private AdditionalCentrifugalFanAttributes additionalCentrifugalFanAttributes;

	public Integer getSelectedFanId() {

		return selectedFanId;
	}

	public void setSelectedFanId(Integer selectedFanId) {

		this.selectedFanId = selectedFanId;
	}

	public Integer getOfferRevId() {

		return offerRevId;
	}

	public void setOfferRevId(Integer offerRevId) {

		this.offerRevId = offerRevId;
	}

	public String getTagNo() {

		return tagNo;
	}

	public void setTagNo(String tagNo) {

		this.tagNo = tagNo;
	}


	public String getMoc() {

		return moc;
	}

	public void setMoc(String moc) {

		this.moc = moc;
	}

	public Integer getPrimeMoverId() {

		return primeMoverId;
	}

	public void setPrimeMoverId(Integer primeMoverId) {

		this.primeMoverId = primeMoverId;
	}
	public List<UserInputDto> getUserInputDto() {

		return userInputDto;
	}

	public void setUserInputDto(List<UserInputDto> userInputDto) {

		this.userInputDto = userInputDto;
	}

	public AdditionalCentrifugalFanAttributes getAdditionalCentrifugalFanAttributes() {
		return additionalCentrifugalFanAttributes;
	}

	public void setAdditionalCentrifugalFanAttributes(
			AdditionalCentrifugalFanAttributes additionalCentrifugalFanAttributes) {
		this.additionalCentrifugalFanAttributes = additionalCentrifugalFanAttributes;
	}
		
}

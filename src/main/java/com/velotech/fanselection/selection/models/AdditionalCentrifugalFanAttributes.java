
package com.velotech.fanselection.selection.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionalCentrifugalFanAttributes {    

	private String rotation;

	private String feetLocation;

	private String suctionOrientation;

	private String dischargeOrientation;

	private String companionFlange;

	private Integer nrvMasterId;

	private Integer extendedCableLength;

	private String startingMethod;

	private Integer variantId;

	public String getRotation() {

		return rotation;
	}

	public void setRotation(String rotation) {

		this.rotation = rotation;
	}

	public String getFeetLocation() {

		return feetLocation;
	}

	public void setFeetLocation(String feetLocation) {

		this.feetLocation = feetLocation;
	}

	public String getSuctionOrientation() {

		return suctionOrientation;
	}

	public void setSuctionOrientation(String suctionOrientation) {

		this.suctionOrientation = suctionOrientation;
	}

	public String getDischargeOrientation() {

		return dischargeOrientation;
	}

	public void setDischargeOrientation(String dischargeOrientation) {

		this.dischargeOrientation = dischargeOrientation;
	}

	public String getCompanionFlange() {

		return companionFlange;
	}

	public void setCompanionFlange(String companionFlange) {

		this.companionFlange = companionFlange;
	}

	public Integer getNrvMasterId() {

		return nrvMasterId;
	}

	public void setNrvMasterId(Integer nrvMasterId) {

		this.nrvMasterId = nrvMasterId;
	}

	public Integer getExtendedCableLength() {

		return extendedCableLength;
	}

	public void setExtendedCableLength(Integer extendedCableLength) {

		this.extendedCableLength = extendedCableLength;
	}

	public String getStartingMethod() {

		return startingMethod;
	}

	public void setStartingMethod(String startingMethod) {

		this.startingMethod = startingMethod;
	}

	public Integer getVariantId() {

		return variantId;
	}

	public void setVariantId(Integer variantId) {

		this.variantId = variantId;
	}

}

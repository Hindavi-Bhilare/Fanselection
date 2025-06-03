
package com.velotech.fanselection.offer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class OfferFanDownloadDto {

	private int id;

	private Boolean performancePDF;

	private Boolean csPDF;

	private Boolean bareFanPDF;

	private Boolean fanSetPDF;

	private Boolean qapPDF;

	private Boolean dataSheetPDF;

	private Boolean motorPDF;

	@JsonIgnoreProperties
	private String system;

	private String tagNo;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public Boolean getPerformancePDF() {

		return performancePDF;
	}

	public void setPerformancePDF(Boolean performancePDF) {

		this.performancePDF = performancePDF;
	}

	public Boolean getCsPDF() {

		return csPDF;
	}

	public void setCsPDF(Boolean csPDF) {

		this.csPDF = csPDF;
	}

	public Boolean getBareFanPDF() {

		return bareFanPDF;
	}

	public void setBareFanPDF(Boolean bareFanPDF) {

		this.bareFanPDF = bareFanPDF;
	}

	public Boolean getFanSetPDF() {

		return fanSetPDF;
	}

	public void setFanSetPDF(Boolean fanSetPDF) {

		this.fanSetPDF = fanSetPDF;
	}

	public Boolean getQapPDF() {

		return qapPDF;
	}

	public void setQapPDF(Boolean qapPDF) {

		this.qapPDF = qapPDF;
	}

	public Boolean getDataSheetPDF() {

		return dataSheetPDF;
	}

	public void setDataSheetPDF(Boolean dataSheetPDF) {

		this.dataSheetPDF = dataSheetPDF;
	}

	public String getSystem() {

		return system;
	}

	public Boolean getMotorPDF() {

		return motorPDF;
	}

	public void setMotorPDF(Boolean motorPDF) {

		this.motorPDF = motorPDF;
	}

	public void setSystem(String system) {

		this.system = system;
	}

	public String getTagNo() {

		return tagNo;
	}

	public void setTagNo(String tagNo) {

		this.tagNo = tagNo;
	}

}

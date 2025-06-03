
package com.velotech.fanselection.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.velotech.fanselection.offer.dto.OfferMasterDownloadDto;

public class EmailLogDto {

	private int id;

	private String[] emailTo;

	@JsonInclude(Include.NON_NULL)
	private String emailFrom;

	@JsonInclude(Include.NON_NULL)
	private String[] cc;

	@JsonInclude(Include.NON_NULL)
	private String[] bcc;

	private String subject;

	private String emailbody;

	private Boolean emailStatus;

	private String createdBy;

	private Date createdDate;

	private String offerRev;

	private String offerNo;

	private Integer offerRevId;

	private OfferMasterDownloadDto offerMasterDownloadDto;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getEmailFrom() {

		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {

		this.emailFrom = emailFrom;
	}

	public String getSubject() {

		return subject;
	}

	public void setSubject(String subject) {

		this.subject = subject;
	}

	public String getEmailbody() {

		return emailbody;
	}

	public void setEmailbody(String emailbody) {

		this.emailbody = emailbody;
	}

	public Boolean getEmailStatus() {

		return emailStatus;
	}

	public void setEmailStatus(Boolean emailStatus) {

		this.emailStatus = emailStatus;
	}

	public Date getCreatedDate() {

		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {

		this.createdDate = createdDate;
	}

	public String getCreatedBy() {

		return createdBy;
	}

	public void setCreatedBy(String createdBy) {

		this.createdBy = createdBy;
	}

	public String[] getEmailTo() {

		return emailTo;
	}

	public void setEmailTo(String[] emailTo) {

		this.emailTo = emailTo;
	}

	public String[] getCc() {

		return cc;
	}

	public void setCc(String[] cc) {

		this.cc = cc;
	}

	public String[] getBcc() {

		return bcc;
	}

	public void setBcc(String[] bcc) {

		this.bcc = bcc;
	}

	public String getOfferRev() {

		return offerRev;
	}

	public void setOfferRev(String offerRev) {

		this.offerRev = offerRev;
	}

	public String getOfferNo() {

		return offerNo;
	}

	public void setOfferNo(String offerNo) {

		this.offerNo = offerNo;
	}

	public Integer getOfferRevId() {

		return offerRevId;
	}

	public void setOfferRevId(Integer offerRevId) {

		this.offerRevId = offerRevId;
	}

	public OfferMasterDownloadDto getOfferMasterDownloadDto() {

		return offerMasterDownloadDto;
	}

	public void setOfferMasterDownloadDto(OfferMasterDownloadDto offerMasterDownloadDto) {

		this.offerMasterDownloadDto = offerMasterDownloadDto;
	}

}

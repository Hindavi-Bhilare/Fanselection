package com.velotech.fanselection.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

/**
 * Tbl90XmlCentrifugalFanSpeed created by Vikram
 */
@Entity
@Table(name = "tbl_90_xml_centrifugalfan_speed")
@FilterDef(name = "company", parameters = { @ParamDef(name = "company", type = "java.lang.String") })
@Filter(name = "company", condition = ":company= company ")
public class Tbl90XmlCentrifugalFanSpeed implements java.io.Serializable { 

	private String centrifugalFanSpeedId;

	private Tbl90XmlCentrifugalModelMaster tbl90XmlCentrifugalModelMaster;

	private Integer speed;

	private Integer pressureDegree;

	private Integer powerDegree;

	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;

	private String company;

	private Set<Tbl90XmlCentrifugalPerformanceDataHead> tbl90XmlCentrifugalPerformanceDataHeads = new HashSet<Tbl90XmlCentrifugalPerformanceDataHead>(0);

	public Tbl90XmlCentrifugalFanSpeed() {

	}

	public Tbl90XmlCentrifugalFanSpeed(String centrifugalFanSpeedId) {
		this.centrifugalFanSpeedId = centrifugalFanSpeedId;
	}

	
	@Id
	@Column(name = "centrifugalFanSpeedId", unique = true, nullable = false, length = 200)
	public String getCentrifugalFanSpeedId() {
		return centrifugalFanSpeedId;
	}

	public void setCentrifugalFanSpeedId(String centrifugalFanSpeedId) {
		this.centrifugalFanSpeedId = centrifugalFanSpeedId;
	}

	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fanModelId", nullable = false)
	public Tbl90XmlCentrifugalModelMaster getTbl90XmlCentrifugalModelMaster() {
		return tbl90XmlCentrifugalModelMaster;
	}

	public void setTbl90XmlCentrifugalModelMaster(Tbl90XmlCentrifugalModelMaster tbl90XmlCentrifugalModelMaster) {
		this.tbl90XmlCentrifugalModelMaster = tbl90XmlCentrifugalModelMaster;
	}

	
	@Column(name = "speed")
	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	@Column(name = "pressureDegree")
	public Integer getPressureDegree() {
		return pressureDegree;
	}

	public void setPressureDegree(Integer pressureDegree) {
		this.pressureDegree = pressureDegree;
	}

	@Column(name = "powerDegree")
	public Integer getPowerDegree() {
		return powerDegree;
	}

	public void setPowerDegree(Integer powerDegree) {
		this.powerDegree = powerDegree;
	}

	@Column(name = "createdBy", length = 50)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createdDate", length = 10)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "modifiedBy", length = 50)
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modifiedDate", length = 10)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "company", length = 50)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbl90XmlCentrifugalFanSpeed")
	public Set<Tbl90XmlCentrifugalPerformanceDataHead> getTbl90XmlCentrifugalPerformanceDataHeads() {
		return tbl90XmlCentrifugalPerformanceDataHeads;
	}

	public void setTbl90XmlCentrifugalPerformanceDataHeads(Set<Tbl90XmlCentrifugalPerformanceDataHead> tbl90XmlCentrifugalPerformanceDataHeads) {
		this.tbl90XmlCentrifugalPerformanceDataHeads = tbl90XmlCentrifugalPerformanceDataHeads;
	}
		

}

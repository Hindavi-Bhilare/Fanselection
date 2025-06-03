package com.velotech.fanselection.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * Tbl01CentrifugalModelMaster created By Vikram
 */
@Entity
@Table(name = "tbl_01_centrifugal_FanSpeed")
@FilterDef(name = "company", parameters = { @ParamDef(name = "company", type = "java.lang.String") })
@Filter(name = "company", condition = ":company= company ")
public class Tbl01CentrifugalFanSpeed implements java.io.Serializable { 

	
	private int id;
	
	private Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster;
	
	private Integer speed;
	
	private Double rangeQMin;
	
	private Double rangeQMax;
	
	private Integer pressureDegree;
	
	private Integer powerDegree;
	
	private String termsPressure;
	
	private String termsPower;
	
	private Double qminPressure;

	private Double qmaxPressure;

	private Double qminPower;

	private Double qmaxPower;
	
	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;

	private String company;
	
	private Set<Tbl01CentrifugalSpeedData> tbl01CentrifugalSpeedData = new HashSet<Tbl01CentrifugalSpeedData>(0);
	
	
	public Tbl01CentrifugalFanSpeed() {
	}

	public Tbl01CentrifugalFanSpeed(int id) {
		this.id = id;
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fanModelId")
	public Tbl01CentrifugalModelMaster getTbl01CentrifugalModelMaster() {
		return tbl01CentrifugalModelMaster;
	}

	public void setTbl01CentrifugalModelMaster(Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster) {
		this.tbl01CentrifugalModelMaster = tbl01CentrifugalModelMaster;
	}

	@Column(name = "speed")
	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	@Column(name = "rangeQMin", precision = 18)
	public Double getRangeQMin() {
		return rangeQMin;
	}

	public void setRangeQMin(Double rangeQMin) {
		this.rangeQMin = rangeQMin;
	}

	@Column(name = "rangeQMax", precision = 18)
	public Double getRangeQMax() {
		return rangeQMax;
	}

	public void setRangeQMax(Double rangeQMax) {
		this.rangeQMax = rangeQMax;
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

	@Column(name = "termsPressure", length = 50)
	public String getTermsPressure() {
		return termsPressure;
	}

	public void setTermsPressure(String termsPressure) {
		this.termsPressure = termsPressure;
	}

	@Column(name = "termsPower", length = 50)
	public String getTermsPower() {
		return termsPower;
	}

	public void setTermsPower(String termsPower) {
		this.termsPower = termsPower;
	}

	
	@Column(name = "qminPressure", precision = 18)
	public Double getQminPressure() {
		return qminPressure;
	}

	public void setQminPressure(Double qminPressure) {
		this.qminPressure = qminPressure;
	}

	@Column(name = "qmaxPressure", precision = 18)
	public Double getQmaxPressure() {
		return qmaxPressure;
	}

	public void setQmaxPressure(Double qmaxPressure) {
		this.qmaxPressure = qmaxPressure;
	}

	@Column(name = "qminPower", precision = 18)
	public Double getQminPower() {
		return qminPower;
	}

	public void setQminPower(Double qminPower) {
		this.qminPower = qminPower;
	}

	@Column(name = "qmaxPower", precision = 18)
	public Double getQmaxPower() {
		return qmaxPower;
	}

	public void setQmaxPower(Double qmaxPower) {
		this.qmaxPower = qmaxPower;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbl01CentrifugalFanSpeed")
	public Set<Tbl01CentrifugalSpeedData> getTbl01CentrifugalSpeedData() {
		return tbl01CentrifugalSpeedData;
	}

	public void setTbl01CentrifugalSpeedData(Set<Tbl01CentrifugalSpeedData> tbl01CentrifugalSpeedData) {
		this.tbl01CentrifugalSpeedData = tbl01CentrifugalSpeedData;
	}

	
	
	
	
}

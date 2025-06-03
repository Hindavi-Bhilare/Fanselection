package com.velotech.fanselection.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = "tbl_01_centrifugalSpeedData")
@FilterDef(name = "company", parameters = { @ParamDef(name = "company", type = "java.lang.String") })
@Filter(name = "company", condition = ":company= company ")
public class Tbl01CentrifugalSpeedData implements java.io.Serializable{ 
	
	private int id;
	
	private Tbl01CentrifugalFanSpeed tbl01CentrifugalFanSpeed;
	
	private Double flow;
	
	private Double pressure;
	
	private Double power;
	
	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;

	private String company;
	
	
	public Tbl01CentrifugalSpeedData() {
	}

	public Tbl01CentrifugalSpeedData(int id) {
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fanSpeedId")
	public Tbl01CentrifugalFanSpeed getTbl01CentrifugalFanSpeed() {
		return tbl01CentrifugalFanSpeed;
	}

	public void setTbl01CentrifugalFanSpeed(Tbl01CentrifugalFanSpeed tbl01CentrifugalFanSpeed) {
		this.tbl01CentrifugalFanSpeed = tbl01CentrifugalFanSpeed;
	}

	@Column(name = "flow", precision = 18)
	public Double getFlow() {
		return flow;
	}

	public void setFlow(Double flow) {
		this.flow = flow;
	}

	@Column(name = "pressure", precision = 18)
	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}

	@Column(name = "power", precision = 18)
	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
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
  

}

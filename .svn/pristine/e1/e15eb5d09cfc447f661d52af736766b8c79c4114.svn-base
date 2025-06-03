package com.velotech.fanselection.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

/**
 * Tbl90XmlCentrifugalModelMaster created by Vikram
 */
@Entity
@Table(name = "tbl_90_xml_centrifugalmodelmaster")
@FilterDef(name = "company", parameters = { @ParamDef(name = "company", type = "java.lang.String") })
@Filter(name = "company", condition = ":company= company ")
public class Tbl90XmlCentrifugalModelMaster { 

	private Integer id;

	private String fanModel;

	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;

	private String company;
	
	public Tbl90XmlCentrifugalModelMaster() {
	}

	public Tbl90XmlCentrifugalModelMaster(Integer id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "fanModel", length = 200)
	public String getFanModel() {
		return fanModel;
	}

	
	public void setFanModel(String fanModel) {
		this.fanModel = fanModel;
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

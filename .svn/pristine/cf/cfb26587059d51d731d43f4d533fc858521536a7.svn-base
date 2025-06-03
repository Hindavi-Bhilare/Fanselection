package com.velotech.fanselection.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Parameter;

import com.google.gson.annotations.Expose;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl01ProductMaster;
import com.velotech.fanselection.models.Tbl90DocumentMaster;

/**
 * Tbl01CentrifugalModelMaster created By Vikram
 */
@Entity
@Table(name = "tbl_01_centrifugal_ModelMaster", uniqueConstraints = @UniqueConstraint(columnNames = { "fanModel", "company" }))
@FilterDef(name = "company", parameters = { @ParamDef(name = "company", type = "java.lang.String") })
@Filter(name = "company", condition = ":company= company ")
public class Tbl01CentrifugalModelMaster implements java.io.Serializable{ 
	
	private int id;
	
	private Tbl01ProductMaster tbl01ProductMaster;

	private Tbl01Fantype tbl01fantype;
	
	@Expose
	private String fanModel;
	
	private Integer maxSpeed;
	
	private Integer minSpeed;
	
	private Boolean isAffinityLaw;
	
	private String intermediateSpeed;
	
	private String uuid;
	
	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;

	private String company;
	
	private Boolean availability;
	
	private Integer pole;

	private Double fanDia;
	
	private Integer blades;
	
	private Integer frequency;
	
	private String make;
	
	private Double fanGD2;
	
	private Double inletArea;

	private Tbl90DocumentMaster documentMaster;
	
	
	
	public Tbl01CentrifugalModelMaster() {
	}

	public Tbl01CentrifugalModelMaster(int id) {
		this.id = id;
	}
	
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "tbl01ProductMaster"))
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Tbl01ProductMaster getTbl01ProductMaster() {
		return tbl01ProductMaster;
	}

	public void setTbl01ProductMaster(Tbl01ProductMaster tbl01ProductMaster) {
		this.tbl01ProductMaster = tbl01ProductMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fanTypeId")
	public Tbl01Fantype getTbl01fantype() {
		return tbl01fantype;
	}

	public void setTbl01fantype(Tbl01Fantype tbl01fantype) {
		this.tbl01fantype = tbl01fantype;
	}

	@Column(name = "fanModel", nullable = false, length = 200)
	public String getFanModel() {
		return fanModel;
	}

	public void setFanModel(String fanModel) {
		this.fanModel = fanModel;
	}


	@Column(name = "maxSpeed")
	public Integer getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Integer maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	@Column(name = "minSpeed")
	public Integer getMinSpeed() {
		return minSpeed;
	}

	public void setMinSpeed(Integer minSpeed) {
		this.minSpeed = minSpeed;
	}

	@Column(name = "isAffinityLaw")
	public Boolean getIsAffinityLaw() {
		return isAffinityLaw;
	}

	public void setIsAffinityLaw(Boolean isAffinityLaw) {
		this.isAffinityLaw = isAffinityLaw;
	}

	@Column(name = "intermediateSpeed")
	public String getIntermediateSpeed() {
		return intermediateSpeed;
	}

	public void setIntermediateSpeed(String intermediateSpeed) {
		this.intermediateSpeed = intermediateSpeed;
	}
	
	@Column(name = "uuid", columnDefinition = "uniqueidentifier")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	@Column(name = "availability")
	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "documentId")
	public Tbl90DocumentMaster getDocumentMaster() {
		return documentMaster;
	}

	public void setDocumentMaster(Tbl90DocumentMaster documentMaster) {
		this.documentMaster = documentMaster;
	}

	@Column(name = "pole")
	public Integer getPole() {
		return pole;
	}

	public void setPole(Integer pole) {
		this.pole = pole;
	}

	@Column(name = "fanDia", precision = 18)
	public Double getFanDia() {
		return fanDia;
	}

	public void setFanDia(Double fanDia) {
		this.fanDia = fanDia;
	}

	@Column(name = "blades")
	public Integer getBlades() {
		return blades;
	}

	public void setBlades(Integer blades) {
		this.blades = blades;
	}

	@Column(name = "frequency")
	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	@Column(name = "make", length = 50)
	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	@Column(name = "fanGD2", precision = 18)
	public Double getFanGD2() {
		return fanGD2;
	}

	public void setFanGD2(Double fanGD2) {
		this.fanGD2 = fanGD2;
	}

	@Column(name = "inletArea", precision = 18)
	public Double getInletArea() {
		return inletArea;
	}

	public void setInletArea(Double inletArea) {
		this.inletArea = inletArea;
	}
	
	

 
}

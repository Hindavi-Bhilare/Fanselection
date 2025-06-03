package com.velotech.fanselection.dtos;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl01ProductMaster;
import com.velotech.fanselection.models.Tbl90DocumentMaster;

public class CentrifugalModelMasterDto { 
	
    private int id;
	
	private String fanModel;
	
    private String fanType;

	private Integer fanTypeId;

	private Integer maxSpeed;

	private Integer minSpeed;

	private Boolean isAffinityLaw;

	private String intermediateSpeed;

	private String uuid;

	private Integer documentId;
	
	private Boolean availability;
	
	private Integer pole;

	private Double fanDia;
	
	private Integer blades;
	
	private Integer frequency;
	
	private String make;
	
	private Double fanGD2;
	
	private Double inletArea;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFanModel() {
		return fanModel;
	}

	public void setFanModel(String fanModel) {
		this.fanModel = fanModel;
	}

	public Integer getFanTypeId() {
		return fanTypeId;
	}

	public void setFanTypeId(Integer fanTypeId) {
		this.fanTypeId = fanTypeId;
	}

	public Integer getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Integer maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Integer getMinSpeed() {
		return minSpeed;
	}

	public void setMinSpeed(Integer minSpeed) {
		this.minSpeed = minSpeed;
	}

	public Boolean getIsAffinityLaw() {
		return isAffinityLaw;
	}

	public void setIsAffinityLaw(Boolean isAffinityLaw) {
		this.isAffinityLaw = isAffinityLaw;
	}

	public String getIntermediateSpeed() {
		return intermediateSpeed;
	}

	public void setIntermediateSpeed(String intermediateSpeed) {
		this.intermediateSpeed = intermediateSpeed;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public String getFanType() {
		return fanType;
	}

	public void setFanType(String fanType) {
		this.fanType = fanType;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	public Integer getPole() {
		return pole;
	}

	public void setPole(Integer pole) {
		this.pole = pole;
	}

	public Double getFanDia() {
		return fanDia;
	}

	public void setFanDia(Double fanDia) {
		this.fanDia = fanDia;
	}

	public Integer getBlades() {
		return blades;
	}

	public void setBlades(Integer blades) {
		this.blades = blades;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public Double getFanGD2() {
		return fanGD2;
	}

	public void setFanGD2(Double fanGD2) {
		this.fanGD2 = fanGD2;
	}

	public Double getInletArea() {
		return inletArea;
	}

	public void setInletArea(Double inletArea) {
		this.inletArea = inletArea;
	}
	
	
	
	
	
}

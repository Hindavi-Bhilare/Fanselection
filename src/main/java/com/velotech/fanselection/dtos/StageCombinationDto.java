
package com.velotech.fanselection.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StageCombinationDto {

	private Integer id;

	private Integer modelMasterId;

	private Integer primemoverId;

	private String modelName;

	private String pumpModelName;

	private Double power;

	private Integer motorSeriesId;

	private String motorSeries;

	private Integer stage;

	private Integer selectionTypeId;

	private String selectionType;
	
	private Integer voltage;
	
	private String identification;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public Integer getModelMasterId() {

		return modelMasterId;
	}

	public void setModelMasterId(Integer modelMasterId) {

		this.modelMasterId = modelMasterId;
	}

	
	public Integer getPrimemoverId() {
		return primemoverId;
	}

	public void setPrimemoverId(Integer primemoverId) {
		this.primemoverId = primemoverId;
	}

	public String getModelName() {

		return modelName;
	}

	public void setModelName(String modelName) {

		this.modelName = modelName;
	}

	public String getPumpModelName() {

		return pumpModelName;
	}

	public void setPumpModelName(String pumpModelName) {

		this.pumpModelName = pumpModelName;
	}

	public Double getPower() {

		return power;
	}

	public void setPower(Double power) {

		this.power = power;
	}

	public Integer getMotorSeriesId() {

		return motorSeriesId;
	}

	public void setMotorSeriesId(Integer motorSeriesId) {

		this.motorSeriesId = motorSeriesId;
	}

	public String getMotorSeries() {

		return motorSeries;
	}

	public void setMotorSeries(String motorSeries) {

		this.motorSeries = motorSeries;
	}

	public Integer getStage() {

		return stage;
	}

	public void setStage(Integer stage) {

		this.stage = stage;
	}

	public Integer getSelectionTypeId() {

		return selectionTypeId;
	}

	public void setSelectionTypeId(Integer selectionTypeId) {

		this.selectionTypeId = selectionTypeId;
	}

	public String getSelectionType() {

		return selectionType;
	}

	public void setSelectionType(String selectionType) {

		this.selectionType = selectionType;
	}

	public Integer getVoltage() {
		return voltage;
	}

	public void setVoltage(Integer voltage) {
		this.voltage = voltage;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	

}

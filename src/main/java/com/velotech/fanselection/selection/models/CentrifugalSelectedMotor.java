
package com.velotech.fanselection.selection.models;

public class CentrifugalSelectedMotor { 

	private Integer primemoverId;

	private Integer motorSeriesId;

	private String motorSeries;

	private String description;

	private Integer phase;

	private String effClass;

	// private Integer pole;

	private String motorMoc;

	private String mounting;

	private String motorModelName;

	private Double motorPower;

	private Double motorPowerHp;

	private Double motorCurrentDP;

	private Double motorEffciencyDP;

	private String currentEq;

	private String motorEfficiencyEq;

	private Double overallEfficiency;

	private Double overallPowerDP;

	private String overallEffEq;

	private Double motorOverload;

	private Double motorSpeed;

	public Integer getPrimemoverId() {

		return primemoverId;
	}

	public void setPrimemoverId(Integer primemoverId) {

		this.primemoverId = primemoverId;
	}

	public Integer getMotorSeriesId() {

		return motorSeriesId;
	}

	public void setMotorSeriesId(Integer motorSeriesId) {

		this.motorSeriesId = motorSeriesId;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public String getMotorSeries() {

		return motorSeries;
	}

	public void setMotorSeries(String motorSeries) {

		this.motorSeries = motorSeries;
	}

	public Integer getPhase() {

		return phase;
	}

	public void setPhase(Integer phase) {

		this.phase = phase;
	}

	public String getEffClass() {

		return effClass;
	}

	public void setEffClass(String effClass) {

		this.effClass = effClass;
	}

	/*
	 * public Integer getPole() {
	 * 
	 * return pole; }
	 * 
	 * public void setPole(Integer pole) {
	 * 
	 * this.pole = pole; }
	 */

	public String getMounting() {

		return mounting;
	}

	public String getMotorMoc() {

		return motorMoc;
	}

	public void setMotorMoc(String motorMoc) {

		this.motorMoc = motorMoc;
	}

	public void setMounting(String mounting) {

		this.mounting = mounting;
	}

	public String getCurrentEq() {

		return currentEq;
	}

	public void setCurrentEq(String currentEq) {

		this.currentEq = currentEq;
	}

	public Double getOverallEfficiency() {

		return overallEfficiency;
	}

	public void setOverallEfficiency(Double overallEfficiency) {

		this.overallEfficiency = overallEfficiency;
	}

	public Double getOverallPowerDP() {

		return overallPowerDP;
	}

	public void setOverallPowerDP(Double overallPowerDP) {

		this.overallPowerDP = overallPowerDP;
	}

	public String getOverallEffEq() {

		return overallEffEq;
	}

	public void setOverallEffEq(String overallEffEq) {

		this.overallEffEq = overallEffEq;
	}

	public Double getMotorOverload() {

		return motorOverload;
	}

	public void setMotorOverload(Double motorOverload) {

		this.motorOverload = motorOverload;
	}

	public String getMotorModelName() {

		return motorModelName;
	}

	public void setMotorModelName(String motorModelName) {

		this.motorModelName = motorModelName;
	}

	public Double getMotorPower() {

		return motorPower;
	}

	public void setMotorPower(Double motorPower) {

		this.motorPower = motorPower;
	}

	public Double getMotorPowerHp() {

		return motorPowerHp;
	}

	public void setMotorPowerHp(Double motorPowerHp) {

		this.motorPowerHp = motorPowerHp;
	}

	public Double getMotorCurrentDP() {

		return motorCurrentDP;
	}

	public void setMotorCurrentDP(Double motorCurrentDP) {

		this.motorCurrentDP = motorCurrentDP;
	}

	public Double getMotorEffciencyDP() {

		return motorEffciencyDP;
	}

	public void setMotorEffciencyDP(Double motorEffciencyDP) {

		this.motorEffciencyDP = motorEffciencyDP;
	}

	public String getMotorEfficiencyEq() {

		return motorEfficiencyEq;
	}

	public void setMotorEfficiencyEq(String motorEfficiencyEq) {

		this.motorEfficiencyEq = motorEfficiencyEq;
	}

	public Double getMotorSpeed() {

		return motorSpeed;
	}

	public void setMotorSpeed(Double motorSpeed) {

		this.motorSpeed = motorSpeed;
	}

}

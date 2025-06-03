package com.velotech.fanselection.dtos;

public class PumpMotorSeriesDto {

	private Integer id;

	private Integer pumptypeId;

	private String pumpSeries;

	private Integer motorId;

	private String motorSeries;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPumptypeId() {
		return pumptypeId;
	}

	public void setPumptypeId(Integer pumptypeId) {
		this.pumptypeId = pumptypeId;
	}

	public String getPumpSeries() {
		return pumpSeries;
	}

	public void setPumpSeries(String pumpSeries) {
		this.pumpSeries = pumpSeries;
	}

	public Integer getMotorId() {
		return motorId;
	}

	public void setMotorId(Integer motorId) {
		this.motorId = motorId;
	}

	public String getMotorSeries() {
		return motorSeries;
	}

	public void setMotorSeries(String motorSeries) {
		this.motorSeries = motorSeries;
	}

}

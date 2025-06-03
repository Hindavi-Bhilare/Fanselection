
package com.velotech.fanselection.selection.models;

public class RejectedCentrifugalFan { 

	private String fanModel;

	private Integer vaneAngle;

	private Integer speed;
	
	private Double fanDia;
			
	private String reason;

	public RejectedCentrifugalFan(String fanModel, Integer vaneAngle, Integer speed, Double fanDia, String reason) {
		this.fanModel = fanModel;
		this.vaneAngle = vaneAngle;
		this.speed = speed;
		this.fanDia=fanDia;
		this.reason = reason;
	}

	public String getFanModel() {
		return fanModel;
	}

	public void setFanModel(String fanModel) {
		this.fanModel = fanModel;
	}

	public Integer getVaneAngle() {
		return vaneAngle;
	}

	public void setVaneAngle(Integer vaneAngle) {
		this.vaneAngle = vaneAngle;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Double getFanDia() {
		return fanDia;
	}

	public void setFanDia(Double fanDia) {
		this.fanDia = fanDia;
	}

	
	
	
	

}

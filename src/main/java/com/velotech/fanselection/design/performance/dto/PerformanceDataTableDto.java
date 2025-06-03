
package com.velotech.fanselection.design.performance.dto;

public class PerformanceDataTableDto {

	public String pumpModel;

	public String performance;

	public Integer head;

	public Double flow;

	public Double power;

	public Double eff;

	public Double npsh;

	public PerformanceDataTableDto(String pumpModel, String performance, Integer head, Double flow, Double power, Double eff, Double npsh) {
		this.pumpModel = pumpModel;
		this.performance = performance;
		this.head = head;
		this.flow = flow;
		this.power = power;
		this.eff = eff;
		this.npsh = npsh;
	}

	public String getPumpModel() {

		return pumpModel;
	}

	public void setPumpModel(String pumpModel) {

		this.pumpModel = pumpModel;
	}

	public Integer getHead() {

		return head;
	}

	public void setHead(Integer head) {

		this.head = head;
	}

	public Double getFlow() {

		return flow;
	}

	public void setFlow(Double flow) {

		this.flow = flow;
	}

	public Double getPower() {

		return power;
	}

	public void setPower(Double power) {

		this.power = power;
	}

	public Double getEff() {

		return eff;
	}

	public void setEff(Double eff) {

		this.eff = eff;
	}

	public Double getNpsh() {

		return npsh;
	}

	public void setNpsh(Double npsh) {

		this.npsh = npsh;
	}

	public String getPerformance() {

		return performance;
	}

	public void setPerformance(String performance) {

		this.performance = performance;
	}

}

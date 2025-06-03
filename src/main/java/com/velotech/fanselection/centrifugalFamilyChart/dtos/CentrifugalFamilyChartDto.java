
package com.velotech.fanselection.centrifugalFamilyChart.dtos;

import java.util.Random;

public class CentrifugalFamilyChartDto {

	private String imageName = "familyChart";

	private String padfName = "familyChart";

	private String series;

	private int seriesId;

	private String fanModel;

	private int fanModelId;

	private String head;

	private String headUnit;

	private String flow;

	private String flowUnit;

	private double rpm;

	private Random randomGenerator = new Random();

	public String getImageName() {

		int var = randomGenerator.nextInt(999999);
		return imageName = imageName + var + ".pdf";
	}

	public void setImageName(String imageName) {

		this.imageName = imageName;
	}

	public String getPadfName() {

		int var = randomGenerator.nextInt(999999);
		return padfName = padfName + var + ".pdf";
	}

	public void setPadfName(String padfName) {

		this.padfName = padfName;
	}

	public String getSeries() {

		return series;
	}

	public void setSeries(String series) {

		this.series = series;
	}

	public String getFanModel() {
		return fanModel;
	}

	public void setFanModel(String fanModel) {
		this.fanModel = fanModel;
	}

	public int getFanModelId() {
		return fanModelId;
	}

	public void setFanModelId(int fanModelId) {
		this.fanModelId = fanModelId;
	}

	public String getHead() {

		return head;
	}

	public void setHead(String head) {

		this.head = head;
	}

	public String getHeadUnit() {

		return headUnit;
	}

	public void setHeadUnit(String headUnit) {

		this.headUnit = headUnit;
	}

	public String getFlow() {

		return flow;
	}

	public void setFlow(String flow) {

		this.flow = flow;
	}

	public String getFlowUnit() {

		if (flowUnit.equals("mÂ³/hr"))
			flowUnit = "m3/hr";
		return flowUnit;
	}

	public void setFlowUnit(String flowUnit) {

		this.flowUnit = flowUnit;
	}

	public double getRpm() {

		return rpm;
	}

	public void setRpm(double rpm) {

		this.rpm = rpm;
	}

	public int getSeriesId() {

		return seriesId;
	}

	public void setSeriesId(int seriesId) {

		this.seriesId = seriesId;
	}

}


package com.velotech.fanselection.dtos;

public class ModelPerformanceDto {

	private int modelPerformanceId;

	private Integer modelMasterId;

	private String modelMaster;

	private Integer performancecurvemasterId;
	
	private Integer[] performancecurvemasterIds;

	private String performancecurvemaster;

	public int getModelPerformanceId() {

		return modelPerformanceId;
	}

	public void setModelPerformanceId(int modelPerformanceId) {

		this.modelPerformanceId = modelPerformanceId;
	}

	public Integer getModelMasterId() {

		return modelMasterId;
	}

	public void setModelMasterId(Integer modelMasterId) {

		this.modelMasterId = modelMasterId;
	}

	public String getModelMaster() {

		return modelMaster;
	}

	public void setModelMaster(String modelMaster) {

		this.modelMaster = modelMaster;
	}
	

	public Integer getPerformancecurvemasterId() {
		return performancecurvemasterId;
	}

	public void setPerformancecurvemasterId(Integer performancecurvemasterId) {
		this.performancecurvemasterId = performancecurvemasterId;
	}

	public Integer[] getPerformancecurvemasterIds() {

		return performancecurvemasterIds;
	}

	public void setPerformancecurvemasterIds(Integer[] performancecurvemasterIds) {

		this.performancecurvemasterIds = performancecurvemasterIds;
	}

	public String getPerformancecurvemaster() {

		return performancecurvemaster;
	}

	public void setPerformancecurvemaster(String performancecurvemaster) {

		this.performancecurvemaster = performancecurvemaster;
	}

}

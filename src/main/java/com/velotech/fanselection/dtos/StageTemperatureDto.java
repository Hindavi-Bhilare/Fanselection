
package com.velotech.fanselection.dtos;

public class StageTemperatureDto {

	private int id;

	private Integer modelMasterId;

	private Double temperature;

	private Integer minStage;

	private Integer maxStage;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public Integer getModelMasterId() {

		return modelMasterId;
	}

	public void setModelMasterId(Integer modelMasterId) {

		this.modelMasterId = modelMasterId;
	}

	public Double getTemperature() {

		return temperature;
	}

	public void setTemperature(Double temperature) {

		this.temperature = temperature;
	}

	public Integer getMinStage() {

		return minStage;
	}

	public void setMinStage(Integer minStage) {

		this.minStage = minStage;
	}

	public Integer getMaxStage() {

		return maxStage;
	}

	public void setMaxStage(Integer maxStage) {

		this.maxStage = maxStage;
	}

}

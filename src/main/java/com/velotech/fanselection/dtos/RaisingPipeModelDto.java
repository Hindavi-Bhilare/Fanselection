
package com.velotech.fanselection.dtos;

public class RaisingPipeModelDto {

	private Integer raisingPipeModelId;

	private Integer modelMasterId;

	private String pumpModel;

	private Integer raisingPipeId;

	private Double raisingPipeSize;

	private Integer stage;

	private Boolean defaultFlag;

	public Integer getRaisingPipeModelId() {

		return raisingPipeModelId;
	}

	public void setRaisingPipeModelId(Integer raisingPipeModelId) {

		this.raisingPipeModelId = raisingPipeModelId;
	}

	public Integer getModelMasterId() {

		return modelMasterId;
	}

	public void setModelMasterId(Integer modelMasterId) {

		this.modelMasterId = modelMasterId;
	}

	public String getPumpModel() {

		return pumpModel;
	}

	public void setPumpModel(String pumpModel) {

		this.pumpModel = pumpModel;
	}

	public Integer getRaisingPipeId() {

		return raisingPipeId;
	}

	public void setRaisingPipeId(Integer raisingPipeId) {

		this.raisingPipeId = raisingPipeId;
	}

	public Double getRaisingPipeSize() {

		return raisingPipeSize;
	}

	public void setRaisingPipeSize(Double raisingPipeSize) {

		this.raisingPipeSize = raisingPipeSize;
	}

	public Integer getStage() {

		return stage;
	}

	public void setStage(Integer stage) {

		this.stage = stage;
	}

	public Boolean getDefaultFlag() {

		return defaultFlag;
	}

	public void setDefaultFlag(Boolean defaultFlag) {

		this.defaultFlag = defaultFlag;
	}

}

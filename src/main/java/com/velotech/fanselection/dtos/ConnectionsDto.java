
package com.velotech.fanselection.dtos;

public class ConnectionsDto {

	private int connectionId;

	private Integer modelMasterId;

	private Integer pumpTypeId;

	private String pumpTypeSeries;

	private String pumpModel;

	private String rotation;

	private Integer stage;

	private String feetLocation;

	public int getConnectionId() {

		return connectionId;
	}

	public void setConnectionId(int connectionId) {

		this.connectionId = connectionId;
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

	public String getRotation() {

		return rotation;
	}

	public void setRotation(String rotation) {

		this.rotation = rotation;
	}

	public Integer getStage() {

		return stage;
	}

	public void setStage(Integer stage) {

		this.stage = stage;
	}

	public String getFeetLocation() {

		return feetLocation;
	}

	public void setFeetLocation(String feetLocation) {

		this.feetLocation = feetLocation;
	}

	public Integer getPumpTypeId() {

		return pumpTypeId;
	}

	public void setPumpTypeId(Integer pumpTypeId) {

		this.pumpTypeId = pumpTypeId;
	}

	public String getPumpTypeSeries() {

		return pumpTypeSeries;
	}

	public void setPumpTypeSeries(String pumpTypeSeries) {

		this.pumpTypeSeries = pumpTypeSeries;
	}

}

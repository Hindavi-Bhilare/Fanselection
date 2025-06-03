
package com.velotech.fanselection.dtos;

public class NozzleDto {

	private int nozzleId;

	private Integer modelMasterId;

	private String modelMaster;

	private String size;

	private String type;

	public int getNozzleId() {

		return nozzleId;
	}

	public void setNozzleId(int nozzleId) {

		this.nozzleId = nozzleId;
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

	public String getSize() {

		return size;
	}

	public void setSize(String size) {

		this.size = size;
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

}

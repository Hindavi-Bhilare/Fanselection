package com.velotech.fanselection.dtos;

public class ModelNrvDto {

	private Integer id;

	private Integer modelMasterId;

	private String pumpModel;

	private Integer nrvMasterId;

	private String nrvName;

	private Boolean default_;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getNrvMasterId() {
		return nrvMasterId;
	}

	public void setNrvMasterId(int nrvMasterId) {
		this.nrvMasterId = nrvMasterId;
	}

	public String getNrvName() {
		return nrvName;
	}

	public void setNrvName(String nrvName) {
		this.nrvName = nrvName;
	}

	public Boolean getDefault_() {
		return default_;
	}

	public void setDefault_(Boolean default_) {
		this.default_ = default_;
	}

}

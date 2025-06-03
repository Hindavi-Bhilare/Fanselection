
package com.velotech.fanselection.dtos;

public class ModelVariantDto {

	private int id;

	private Integer modelMasterId;

	private String variant;

	private String value;

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

	public String getVariant() {

		return variant;
	}

	public void setVariant(String variant) {

		this.variant = variant;
	}

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}

}

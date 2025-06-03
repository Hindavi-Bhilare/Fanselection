
package com.velotech.fanselection.dtos;

import java.math.BigDecimal;

public class ModelPriceDto {

	private int id;

	private Integer variantMasterId;

	private String variantMaster;

	private Integer centrifugalModelId;
	
	private String fanModel;

	private Integer primeMoverMasterId;

	private String primeMoverSeries;

	private String primeMoverModel;

	private String modelmaster;

	private String description;

	private String mocStd;

	private String rotation;

	private String articleNo;

	private BigDecimal price;

	private String nrv;

	private String[] startingMethod;

	private String name;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public Integer getVariantMasterId() {

		return variantMasterId;
	}

	public void setVariantMasterId(Integer variantMasterId) {

		this.variantMasterId = variantMasterId;
	}

	public String getVariantMaster() {

		return variantMaster;
	}

	public void setVariantMaster(String variantMaster) {

		this.variantMaster = variantMaster;
	}

	public String getModelmaster() {

		return modelmaster;
	}

	public void setModelmaster(String modelmaster) {

		this.modelmaster = modelmaster;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}


	public String getMocStd() {

		return mocStd;
	}

	public void setMocStd(String mocStd) {

		this.mocStd = mocStd;
	}

	public String getRotation() {

		return rotation;
	}

	public void setRotation(String rotation) {

		this.rotation = rotation;
	}

	public String getArticleNo() {

		return articleNo;
	}

	public void setArticleNo(String articleNo) {

		this.articleNo = articleNo;
	}

	public BigDecimal getPrice() {

		return price;
	}

	public void setPrice(BigDecimal price) {

		this.price = price;
	}


	public Integer getCentrifugalModelId() {
		return centrifugalModelId;
	}

	public void setCentrifugalModelId(Integer centrifugalModelId) {
		this.centrifugalModelId = centrifugalModelId;
	}

	public String getFanModel() {
		return fanModel;
	}

	public void setFanModel(String fanModel) {
		this.fanModel = fanModel;
	}

	public Integer getPrimeMoverMasterId() {

		return primeMoverMasterId;
	}

	public void setPrimeMoverMasterId(Integer primeMoverMasterId) {

		this.primeMoverMasterId = primeMoverMasterId;
	}

	public String getPrimeMoverSeries() {

		return primeMoverSeries;
	}

	public void setPrimeMoverSeries(String primeMoverSeries) {

		this.primeMoverSeries = primeMoverSeries;
	}

	public String getPrimeMoverModel() {

		return primeMoverModel;
	}

	public void setPrimeMoverModel(String primeMoverModel) {

		this.primeMoverModel = primeMoverModel;
	}

	public String getNrv() {

		return nrv;
	}

	public void setNrv(String nrv) {

		this.nrv = nrv;
	}

	public String[] getStartingMethod() {

		return startingMethod;
	}

	public void setStartingMethod(String[] startingMethod) {

		this.startingMethod = startingMethod;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

}

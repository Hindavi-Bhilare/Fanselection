
package com.velotech.fanselection.dtos;

public class BomDto {

	private int id;

	private Integer offerFanId;

	private String itemNo;

	private Integer productTypeId;

	private String productTypeDescription;

	private Integer itemId;

	private String description;

	private Double qty;

	private String qtyUom;

	private String materialDescription;

	private String articleNo;

	private Boolean reportSummary;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getItemNo() {

		return itemNo;
	}

	public void setItemNo(String itemNo) {

		this.itemNo = itemNo;
	}

	public Integer getItemId() {

		return itemId;
	}

	public void setItemId(Integer itemId) {

		this.itemId = itemId;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public Double getQty() {

		return qty;
	}

	public void setQty(Double qty) {

		this.qty = qty;
	}

	public String getQtyUom() {

		return qtyUom;
	}

	public void setQtyUom(String qtyUom) {

		this.qtyUom = qtyUom;
	}

	// public String getSize() {
	//
	// return size;
	// }
	//
	// public void setSize(String size) {
	//
	// this.size = size;
	// }

	public String getMaterialDescription() {

		return materialDescription;
	}

	public void setMaterialDescription(String materialDescription) {

		this.materialDescription = materialDescription;
	}

	public String getArticleNo() {

		return articleNo;
	}

	public void setArticleNo(String articleNo) {

		this.articleNo = articleNo;
	}

	public Boolean getReportSummary() {

		return reportSummary;
	}

	public void setReportSummary(Boolean reportSummary) {

		this.reportSummary = reportSummary;
	}

	public Integer getOfferFanId() {

		return offerFanId;
	}

	public void setOfferFanId(Integer offerFanId) {

		this.offerFanId = offerFanId;
	}

	public Integer getProductTypeId() {

		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {

		this.productTypeId = productTypeId;
	}

	public String getProductTypeDescription() {

		return productTypeDescription;
	}

	public void setProductTypeDescription(String productTypeDescription) {

		this.productTypeDescription = productTypeDescription;
	}

}

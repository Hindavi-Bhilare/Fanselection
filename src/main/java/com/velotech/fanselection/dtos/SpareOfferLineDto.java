
package com.velotech.fanselection.dtos;

public class SpareOfferLineDto {

	private int id;

	private int spareOfferMasterId;

	private String itemId;

	private String description;

	private Double qty;

	private Double price;

	private Double discount;

	private Double subTotal;

	private String remarks;

	private Integer lineNum;

	private String qtyUom;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public int getSpareOfferMasterId() {

		return spareOfferMasterId;
	}

	public void setSpareOfferMasterId(int spareOfferMasterId) {

		this.spareOfferMasterId = spareOfferMasterId;
	}

	public String getItemId() {

		return itemId;
	}

	public void setItemId(String itemId) {

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

	public Double getPrice() {

		return price;
	}

	public void setPrice(Double price) {

		this.price = price;
	}

	public Double getDiscount() {

		return discount;
	}

	public void setDiscount(Double discount) {

		this.discount = discount;
	}

	public Double getSubTotal() {

		return subTotal;
	}

	public void setSubTotal(Double subTotal) {

		this.subTotal = subTotal;
	}

	public String getRemarks() {

		return remarks;
	}

	public void setRemarks(String remarks) {

		this.remarks = remarks;
	}

	public Integer getLineNum() {

		return lineNum;
	}

	public void setLineNum(Integer lineNum) {

		this.lineNum = lineNum;
	}

	public String getQtyUom() {

		return qtyUom;
	}

	public void setQtyUom(String qtyUom) {

		this.qtyUom = qtyUom;
	}

}

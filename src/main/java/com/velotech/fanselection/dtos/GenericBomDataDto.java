
package com.velotech.fanselection.dtos;

public class GenericBomDataDto {

	private int genericBomDataId;

	private Integer genericBomId;

	private Integer itemMasterId;

	private String itemMaster;

	private String itemNo;

	private String description;

	private Double qty;

	private String qtyExpression;

	private String qtyUom;

/*	private String size;

	private Boolean sparePartflag;

	private String sparePartRecomQty;

	private Boolean costing;
*/
	private Boolean showInDatasheet;
	
	private Integer materialMasterId;

	private String materialMaster;
	
	private Integer sequence;


	public int getGenericBomDataId() {

		return genericBomDataId;
	}

	public void setGenericBomDataId(int genericBomDataId) {

		this.genericBomDataId = genericBomDataId;
	}

	public Integer getGenericBomId() {

		return genericBomId;
	}

	public void setGenericBomId(Integer genericBomId) {

		this.genericBomId = genericBomId;
	}

	public Integer getItemMasterId() {

		return itemMasterId;
	}

	public void setItemMasterId(Integer itemMasterId) {

		this.itemMasterId = itemMasterId;
	}

	public String getItemMaster() {

		return itemMaster;
	}

	public void setItemMaster(String itemMaster) {

		this.itemMaster = itemMaster;
	}

	public String getItemNo() {

		return itemNo;
	}

	public void setItemNo(String itemNo) {

		this.itemNo = itemNo;
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

	public String getQtyExpression() {

		return qtyExpression;
	}

	public void setQtyExpression(String qtyExpression) {

		this.qtyExpression = qtyExpression;
	}

	public String getQtyUom() {

		return qtyUom;
	}

	public void setQtyUom(String qtyUom) {

		this.qtyUom = qtyUom;
	}

	/*public String getSize() {

		return size;
	}

	public void setSize(String size) {

		this.size = size;
	}

	public Boolean getSparePartflag() {

		return sparePartflag;
	}

	public void setSparePartflag(Boolean sparePartflag) {

		this.sparePartflag = sparePartflag;
	}

	public String getSparePartRecomQty() {

		return sparePartRecomQty;
	}

	public void setSparePartRecomQty(String sparePartRecomQty) {

		this.sparePartRecomQty = sparePartRecomQty;
	}

	public Boolean getCosting() {

		return costing;
	}

	public void setCosting(Boolean costing) {

		this.costing = costing;
	}*/

	public Boolean getShowInDatasheet() {

		return showInDatasheet;
	}

	public void setShowInDatasheet(Boolean showInDatasheet) {

		this.showInDatasheet = showInDatasheet;
	}

	public Integer getMaterialMasterId() {
		return materialMasterId;
	}

	public void setMaterialMasterId(Integer materialMasterId) {
		this.materialMasterId = materialMasterId;
	}

	public String getMaterialMaster() {
		return materialMaster;
	}

	public void setMaterialMaster(String materialMaster) {
		this.materialMaster = materialMaster;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}	
	
	

}

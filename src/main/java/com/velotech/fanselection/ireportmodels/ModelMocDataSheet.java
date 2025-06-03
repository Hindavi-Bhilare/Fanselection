
package com.velotech.fanselection.ireportmodels;

public class ModelMocDataSheet {

	private String mocStd;

	private String itemDescription;

	private String specification;

	private String genericName;

	public ModelMocDataSheet(String mocStd, String itemDescription, String specification, String genericName) {
		this.mocStd = mocStd;
		this.itemDescription = itemDescription;
		this.specification = specification;
		this.genericName = genericName;
	}

	public String getMocStd() {

		return mocStd;
	}

	public void setMocStd(String mocStd) {

		this.mocStd = mocStd;
	}

	public String getItemDescription() {

		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {

		this.itemDescription = itemDescription;
	}

	public String getSpecification() {

		return specification;
	}

	public void setSpecification(String specification) {

		this.specification = specification;
	}

	public String getGenericName() {

		return genericName;
	}

	public void setGenericName(String genericName) {

		this.genericName = genericName;
	}

}

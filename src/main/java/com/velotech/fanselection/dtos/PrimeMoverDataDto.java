
package com.velotech.fanselection.dtos;

public class PrimeMoverDataDto {

	private int framesizeDataId;

	private Integer primemoverMasterId;

//	private String manufacturer;

	private String parameter;

	private String value;

	public int getFramesizeDataId() {

		return framesizeDataId;
	}

	public void setFramesizeDataId(int framesizeDataId) {

		this.framesizeDataId = framesizeDataId;
	}

	public Integer getPrimemoverMasterId() {

		return primemoverMasterId;
	}

	public void setPrimemoverMasterId(Integer primemoverMasterId) {

		this.primemoverMasterId = primemoverMasterId;
	}

	public String getParameter() {

		return parameter;
	}

	public void setParameter(String parameter) {

		this.parameter = parameter;
	}

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}

	/*public String getManufacturer() {

		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {

		this.manufacturer = manufacturer;
	}*/

}

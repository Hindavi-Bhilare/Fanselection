
package com.velotech.fanselection.dtos;

public class GenericBomMocDto {

	private Integer id;

	private Integer pumpTypeId;

	private String pumpType;

	private String pumpSeries;

	private String mocStd;

	private String systemPressure;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public Integer getPumpTypeId() {

		return pumpTypeId;
	}

	public void setPumpTypeId(Integer pumpTypeId) {

		this.pumpTypeId = pumpTypeId;
	}

	public String getPumpType() {

		return pumpType;
	}

	public void setPumpType(String pumpType) {

		this.pumpType = pumpType;
	}

	public String getMocStd() {

		return mocStd;
	}

	public void setMocStd(String mocStd) {

		this.mocStd = mocStd;
	}

	public String getSystemPressure() {

		return systemPressure;
	}

	public void setSystemPressure(String systemPressure) {

		this.systemPressure = systemPressure;
	}

	public String getPumpSeries() {

		return pumpSeries;
	}

	public void setPumpSeries(String pumpSeries) {

		this.pumpSeries = pumpSeries;
	}

}

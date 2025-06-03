
package com.velotech.fanselection.dtos;

public class IsoEffListDto {

	private Integer id;

	private Integer parentId;

	private Double effValue;

	private Boolean curveJoin;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public Integer getParentId() {

		return parentId;
	}

	public void setParentId(Integer parentId) {

		this.parentId = parentId;
	}

	public Double getEffValue() {

		return effValue;
	}

	public void setEffValue(Double effValue) {

		this.effValue = effValue;
	}

	public Boolean getCurveJoin() {

		return curveJoin;
	}

	public void setCurveJoin(Boolean curveJoin) {

		this.curveJoin = curveJoin;
	}

}

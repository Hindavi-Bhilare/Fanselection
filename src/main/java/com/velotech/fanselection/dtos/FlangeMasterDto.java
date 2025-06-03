
package com.velotech.fanselection.dtos;

public class FlangeMasterDto {

	private int flangeId;

	private Integer seriesId;

	private String series;

	private Integer size;

	private String specification;

	private String flangetype;

	private String[] mocIdList;

	private String mocIds;

	private String layersOn;

	private String layersOff;

	private String companionFlangeWt;

	public int getFlangeId() {

		return flangeId;
	}

	public void setFlangeId(int flangeId) {

		this.flangeId = flangeId;
	}

	public Integer getSeriesId() {

		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {

		this.seriesId = seriesId;
	}

	public String getSeries() {

		return series;
	}

	public void setSeries(String series) {

		this.series = series;
	}

	public Integer getSize() {

		return size;
	}

	public void setSize(Integer size) {

		this.size = size;
	}

	public String getSpecification() {

		return specification;
	}

	public void setSpecification(String specification) {

		this.specification = specification;
	}

	public String getFlangetype() {

		return flangetype;
	}

	public void setFlangetype(String flangetype) {

		this.flangetype = flangetype;
	}

	public String[] getMocIdList() {

		return mocIdList;
	}

	public void setMocIdList(String[] mocIdList) {

		this.mocIdList = mocIdList;
	}

	public String getLayersOn() {

		return layersOn;
	}

	public void setLayersOn(String layersOn) {

		this.layersOn = layersOn;
	}

	public String getLayersOff() {

		return layersOff;
	}

	public void setLayersOff(String layersOff) {

		this.layersOff = layersOff;
	}

	public String getCompanionFlangeWt() {

		return companionFlangeWt;
	}

	public void setCompanionFlangeWt(String companionFlangeWt) {

		this.companionFlangeWt = companionFlangeWt;
	}

	public String getMocIds() {

		return mocIds;
	}

	public void setMocIds(String mocIds) {

		this.mocIds = mocIds;
	}

}

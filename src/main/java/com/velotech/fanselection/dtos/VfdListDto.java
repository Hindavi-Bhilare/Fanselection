
package com.velotech.fanselection.dtos;

public class VfdListDto {

	private Integer id;

	private Integer parentId;

	private Integer vfdspeed;

	private String frequency;

	public String getFrequency() {

		return frequency;
	}

	public void setFrequency(String frequency) {

		this.frequency = frequency;
	}

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

	public Integer getVfdspeed() {

		return vfdspeed;
	}

	public void setVfdspeed(Integer vfdspeed) {

		this.vfdspeed = vfdspeed;
	}

}

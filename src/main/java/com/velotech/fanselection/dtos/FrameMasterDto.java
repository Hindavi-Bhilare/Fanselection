
package com.velotech.fanselection.dtos;

public class FrameMasterDto {

	private int id;

	private String frameMaster; // only for view;

	private String frameSize;

	private Integer pole;

	private Double shaftDia;
	
	private String uuid;
	
	private String description;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getFrameSize() {

		return frameSize;
	}

	public void setFrameSize(String frameSize) {

		this.frameSize = frameSize;
	}

	public Integer getPole() {

		return pole;
	}

	public void setPole(Integer pole) {

		this.pole = pole;
	}

	public Double getShaftDia() {

		return shaftDia;
	}

	public void setShaftDia(Double shaftDia) {

		this.shaftDia = shaftDia;
	}

	public String getFrameMaster() {

		return frameMaster;
	}

	public void setFrameMaster(String frameMaster) {

		this.frameMaster = frameMaster;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}

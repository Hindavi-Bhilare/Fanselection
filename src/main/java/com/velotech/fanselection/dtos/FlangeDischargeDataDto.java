package com.velotech.fanselection.dtos;



public class FlangeDischargeDataDto {
	
	private int flangedataId;

	private Integer flangeMasterId;
	
	private String flangeMaster;
	
	private String parameter;

	private String value;


	
	public int getFlangedataId() {
	
		return flangedataId;
	}

	
	public void setFlangedataId(int flangedataId) {
	
		this.flangedataId = flangedataId;
	}

	
	public Integer getFlangeMasterId() {
	
		return flangeMasterId;
	}

	
	public void setFlangeMasterId(Integer flangeMasterId) {
	
		this.flangeMasterId = flangeMasterId;
	}

	
	public String getFlangeMaster() {
	
		return flangeMaster;
	}

	
	public void setFlangeMaster(String flangeMaster) {
	
		this.flangeMaster = flangeMaster;
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


}

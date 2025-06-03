package com.velotech.fanselection.dtos;

public class ParameterValueDto {
	
	private int id;
	
	private String uuid;
	
	private String parameter;

	private String value;
	
	private String parameterLabel;

	private Integer sequence;

	private Boolean isExpression;
	
	private Boolean isUserInput;

	private String suffix;

	private String prefix;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Boolean getIsExpression() {
		return isExpression;
	}

	public void setIsExpression(Boolean isExpression) {
		this.isExpression = isExpression;
	}

	public Boolean getIsUserInput() {
		return isUserInput;
	}

	public void setIsUserInput(Boolean isUserInput) {
		this.isUserInput = isUserInput;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParameterLabel() {
		return parameterLabel;
	}

	public void setParameterLabel(String parameterLabel) {
		this.parameterLabel = parameterLabel;
	}
	
	

}

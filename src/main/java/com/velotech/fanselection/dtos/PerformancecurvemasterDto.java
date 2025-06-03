
package com.velotech.fanselection.dtos;

public class PerformancecurvemasterDto {

	private Integer id;

	private Integer testingId;

	private String testingClass;

	private String performanceCurveNo;

	private Double serviceFactor;

	private String frequency;

	private Integer pole;

	private Double speed;

	private Double maxSpeed;

	private Double minSpeed;

	private Double maxSolidSize;

	private String variableParameter;

//	private String impellerType;

//	private Integer vaneNo;

	private String refCurveNo;

	private String type;

	private Boolean interpolationType;

	private Boolean isISO;

	private String vfd;

//	private Double axialThrustBalancing;
//
//	private Double axialThrustWoBalancing;
//
//	private Double radialThrust;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public Integer getTestingId() {

		return testingId;
	}

	public void setTestingId(Integer testingId) {

		this.testingId = testingId;
	}

	public String getTestingClass() {

		return testingClass;
	}

	public void setTestingClass(String testingClass) {

		this.testingClass = testingClass;
	}

	public String getPerformanceCurveNo() {

		return performanceCurveNo;
	}

	public void setPerformanceCurveNo(String performanceCurveNo) {

		this.performanceCurveNo = performanceCurveNo;
	}

	public Double getServiceFactor() {

		return serviceFactor;
	}

	public void setServiceFactor(Double serviceFactor) {

		this.serviceFactor = serviceFactor;
	}

	public String getFrequency() {

		return frequency;
	}

	public void setFrequency(String frequency) {

		this.frequency = frequency;
	}

	public Integer getPole() {

		return pole;
	}

	public void setPole(Integer pole) {

		this.pole = pole;
	}

	public Double getSpeed() {

		return speed;
	}

	public void setSpeed(Double speed) {

		this.speed = speed;
	}

	public Double getMaxSpeed() {

		return maxSpeed;
	}

	public void setMaxSpeed(Double maxSpeed) {

		this.maxSpeed = maxSpeed;
	}

	public Double getMinSpeed() {

		return minSpeed;
	}

	public void setMinSpeed(Double minSpeed) {

		this.minSpeed = minSpeed;
	}

	public Double getMaxSolidSize() {

		return maxSolidSize;
	}

	public void setMaxSolidSize(Double maxSolidSize) {

		this.maxSolidSize = maxSolidSize;
	}

	public String getVariableParameter() {

		return variableParameter;
	}

	public void setVariableParameter(String variableParameter) {

		this.variableParameter = variableParameter;
	}

//	public String getImpellerType() {
//
//		return impellerType;
//	}
//
//	public void setImpellerType(String impellerType) {
//
//		this.impellerType = impellerType;
//	}
//
//	public Integer getVaneNo() {
//
//		return vaneNo;
//	}
//
//	public void setVaneNo(Integer vaneNo) {
//
//		this.vaneNo = vaneNo;
//	}

	public String getRefCurveNo() {

		return refCurveNo;
	}

	public void setRefCurveNo(String refCurveNo) {

		this.refCurveNo = refCurveNo;
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public Boolean getInterpolationType() {

		return interpolationType;
	}

	public void setInterpolationType(Boolean interpolationType) {

		this.interpolationType = interpolationType;
	}

	public String getVfd() {

		return vfd;
	}

	public void setVfd(String vfd) {

		this.vfd = vfd;
	}

//	public Double getAxialThrustBalancing() {
//
//		return axialThrustBalancing;
//	}
//
//	public void setAxialThrustBalancing(Double axialThrustBalancing) {
//
//		this.axialThrustBalancing = axialThrustBalancing;
//	}
//
//	public Double getAxialThrustWoBalancing() {
//
//		return axialThrustWoBalancing;
//	}
//
//	public void setAxialThrustWoBalancing(Double axialThrustWoBalancing) {
//
//		this.axialThrustWoBalancing = axialThrustWoBalancing;
//	}
//
//	public Double getRadialThrust() {
//
//		return radialThrust;
//	}
//
//	public void setRadialThrust(Double radialThrust) {
//
//		this.radialThrust = radialThrust;
//	}

	public Boolean getIsISO() {
		return isISO;
	}

	public void setIsISO(Boolean isISO) {
		this.isISO = isISO;
	}

}


package com.velotech.fanselection.dtos;

public class GenericBomDto {

	private int genericBomId;

	private Integer fanTypeId;

	private String fanSeries;

	/*private Integer motorSeriesId;

	private String motorSeries;
*/
	private String fanType;

	private String mocStd;

	private String systemPressure;

	private String shaftGroup;

	private Integer minStage;

	private Integer maxStage;

	private Integer variantId;
	
	private Integer sequence;

	public int getGenericBomId() {

		return genericBomId;
	}

	public void setGenericBomId(int genericBomId) {

		this.genericBomId = genericBomId;
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

	public String getShaftGroup() {

		return shaftGroup;
	}

	public void setShaftGroup(String shaftGroup) {

		this.shaftGroup = shaftGroup;
	}

	public Integer getMinStage() {

		return minStage;
	}

	public void setMinStage(Integer minStage) {

		this.minStage = minStage;
	}

	public Integer getMaxStage() {

		return maxStage;
	}

	public void setMaxStage(Integer maxStage) {

		this.maxStage = maxStage;
	}

	public Integer getVariantId() {

		return variantId;
	}

	public void setVariantId(Integer variantId) {

		this.variantId = variantId;
	}

	

	/*public Integer getMotorSeriesId() {

		return motorSeriesId;
	}

	public void setMotorSeriesId(Integer motorSeriesId) {

		this.motorSeriesId = motorSeriesId;
	}

	public String getMotorSeries() {

		return motorSeries;
	}

	public void setMotorSeries(String motorSeries) {

		this.motorSeries = motorSeries;
	}*/


	public Integer getFanTypeId() {
		return fanTypeId;
	}

	public void setFanTypeId(Integer fanTypeId) {
		this.fanTypeId = fanTypeId;
	}

	public String getFanSeries() {
		return fanSeries;
	}

	public void setFanSeries(String fanSeries) {
		this.fanSeries = fanSeries;
	}

	public String getFanType() {
		return fanType;
	}

	public void setFanType(String fanType) {
		this.fanType = fanType;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}	

}

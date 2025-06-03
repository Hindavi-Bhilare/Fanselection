
package com.velotech.fanselection.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrimeMoverMasterDto {

	private int primemoverId;
	
	private String effClass;

	private Integer pole;

	private String series;

	private String framesize;

	private String moc;

	private String terminalBoxLoc;

	private Double power;

	private Double speed;

	private Integer voltage;

	private Double efficiency;

	private Double startingCurrent;

	private String startingTorque;

	private String voltageTolerance;

	private Double ratedCurrent;

	private Double maxCurrent;

	private Integer phase;

	private String manufacturer;

	private Integer frequency;

	private String mounting;

	private String specification;

	private String gd2;

	private String noiseLevel;

	private String primemoverType;

	private String weight;

	private String articleNo;

	private Double price;

	private String spaceHeater;

	private String radiatorMtg;

	private String characteristics;

	private String shaftDia;

	private Double ambientTemp;

	private String tempRiseClass;

	private String datasheet;

	private Double powerHp;

	private Boolean isSpecial;

	private String speedPower;

	private String[] startingMethod;

	private Integer motorSeriesId;

	private String motorSeries;

	private String modelName;

	private String primeMoverSize;

	private Integer motorCableLength;

	private String qapName;

	private String maximumCableSize;

	private String insulationClass;

	private String enclousreClass;

	private String uuid;

	private Integer frameMasterId;

	private String frameMaster;

	public int getPrimemoverId() {

		return primemoverId;
	}

	public void setPrimemoverId(int primemoverId) {

		this.primemoverId = primemoverId;
	}

	public String getSeries() {

		return series;
	}

	public void setSeries(String series) {

		this.series = series;
	}

	public String getFramesize() {

		return framesize;
	}

	public void setFramesize(String framesize) {

		this.framesize = framesize;
	}

	public String getMoc() {

		return moc;
	}

	public void setMoc(String moc) {

		this.moc = moc;
	}

	public String getTerminalBoxLoc() {

		return terminalBoxLoc;
	}

	public void setTerminalBoxLoc(String terminalBoxLoc) {

		this.terminalBoxLoc = terminalBoxLoc;
	}

	public Double getPower() {

		return power;
	}

	public void setPower(Double power) {

		this.power = power;
	}

	public Double getSpeed() {

		return speed;
	}

	public void setSpeed(Double speed) {

		this.speed = speed;
	}
	
	
	public Integer getVoltage() {
		return voltage;
	}

	public void setVoltage(Integer voltage) {
		this.voltage = voltage;
	}

	public Double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(Double efficiency) {
		this.efficiency = efficiency;
	}

	public Double getStartingCurrent() {
		return startingCurrent;
	}

	public void setStartingCurrent(Double startingCurrent) {
		this.startingCurrent = startingCurrent;
	}

	public String getStartingTorque() {
		return startingTorque;
	}

	public void setStartingTorque(String startingTorque) {
		this.startingTorque = startingTorque;
	}

	public String getVoltageTolerance() {
		return voltageTolerance;
	}

	public void setVoltageTolerance(String voltageTolerance) {
		this.voltageTolerance = voltageTolerance;
	}

	public Double getRatedCurrent() {
		return ratedCurrent;
	}

	public void setRatedCurrent(Double ratedCurrent) {
		this.ratedCurrent = ratedCurrent;
	}

	public Double getMaxCurrent() {
		return maxCurrent;
	}

	public void setMaxCurrent(Double maxCurrent) {
		this.maxCurrent = maxCurrent;
	}

	public Integer getPhase() {
		return phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public String getMounting() {
		return mounting;
	}

	public void setMounting(String mounting) {
		this.mounting = mounting;
	}

	public String getSpecification() {

		return specification;
	}

	public void setSpecification(String specification) {

		this.specification = specification;
	}

	public String getGd2() {

		return gd2;
	}

	public void setGd2(String gd2) {

		this.gd2 = gd2;
	}

	public String getNoiseLevel() {

		return noiseLevel;
	}

	public void setNoiseLevel(String noiseLevel) {

		this.noiseLevel = noiseLevel;
	}

	public String getPrimemoverType() {

		return primemoverType;
	}

	public void setPrimemoverType(String primemoverType) {

		this.primemoverType = primemoverType;
	}

	public String getWeight() {

		return weight;
	}

	public void setWeight(String weight) {

		this.weight = weight;
	}

	public String getArticleNo() {

		return articleNo;
	}

	public void setArticleNo(String articleNo) {

		this.articleNo = articleNo;
	}

	public Double getPrice() {

		return price;
	}

	public void setPrice(Double price) {

		this.price = price;
	}

	public String getSpaceHeater() {

		return spaceHeater;
	}

	public void setSpaceHeater(String spaceHeater) {

		this.spaceHeater = spaceHeater;
	}

	public String getRadiatorMtg() {

		return radiatorMtg;
	}

	public void setRadiatorMtg(String radiatorMtg) {

		this.radiatorMtg = radiatorMtg;
	}

	public String getCharacteristics() {

		return characteristics;
	}

	public void setCharacteristics(String characteristics) {

		this.characteristics = characteristics;
	}

	public String getShaftDia() {

		return shaftDia;
	}

	public void setShaftDia(String shaftDia) {

		this.shaftDia = shaftDia;
	}

	public Double getAmbientTemp() {

		return ambientTemp;
	}

	public void setAmbientTemp(Double ambientTemp) {

		this.ambientTemp = ambientTemp;
	}

	public String getTempRiseClass() {

		return tempRiseClass;
	}

	public void setTempRiseClass(String tempRiseClass) {

		this.tempRiseClass = tempRiseClass;
	}

	public String getDatasheet() {

		return datasheet;
	}

	public void setDatasheet(String datasheet) {

		this.datasheet = datasheet;
	}

	public Double getPowerHp() {

		return powerHp;
	}

	public void setPowerHp(Double powerHp) {

		this.powerHp = powerHp;
	}

	public Boolean getIsSpecial() {

		return isSpecial;
	}

	public void setIsSpecial(Boolean isSpecial) {

		this.isSpecial = isSpecial;
	}

	public String getSpeedPower() {

		return speedPower;
	}

	public void setSpeedPower(String speedPower) {

		this.speedPower = speedPower;
	}

	public String[] getStartingMethod() {
		return startingMethod;
	}

	public void setStartingMethod(String[] startingMethod) {
		this.startingMethod = startingMethod;
	}

	public Integer getMotorSeriesId() {

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
	}

	public String getModelName() {

		return modelName;
	}

	public void setModelName(String modelName) {

		this.modelName = modelName;
	}

	public String getPrimeMoverSize() {

		return primeMoverSize;
	}

	public void setPrimeMoverSize(String primeMoverSize) {

		this.primeMoverSize = primeMoverSize;
	}

	public Integer getMotorCableLength() {

		return motorCableLength;
	}

	public void setMotorCableLength(Integer motorCableLength) {

		this.motorCableLength = motorCableLength;
	}

	public String getQapName() {

		return qapName;
	}

	public void setQapName(String qapName) {

		this.qapName = qapName;
	}

	public String getMaximumCableSize() {

		return maximumCableSize;
	}

	public void setMaximumCableSize(String maximumCableSize) {

		this.maximumCableSize = maximumCableSize;
	}

	public String getInsulationClass() {
		return insulationClass;
	}

	public void setInsulationClass(String insulationClass) {
		this.insulationClass = insulationClass;
	}

	public String getEnclousreClass() {
		return enclousreClass;
	}

	public void setEnclousreClass(String enclousreClass) {
		this.enclousreClass = enclousreClass;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getFrameMasterId() {
		return frameMasterId;
	}

	public void setFrameMasterId(Integer frameMasterId) {
		this.frameMasterId = frameMasterId;
	}

	public String getFrameMaster() {
		return frameMaster;
	}

	public void setFrameMaster(String frameMaster) {
		this.frameMaster = frameMaster;
	}

	public String getEffClass() {
		return effClass;
	}

	public void setEffClass(String effClass) {
		this.effClass = effClass;
	}

	public Integer getPole() {
		return pole;
	}

	public void setPole(Integer pole) {
		this.pole = pole;
	}

}

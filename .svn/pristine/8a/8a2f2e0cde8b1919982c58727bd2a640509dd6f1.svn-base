
package com.velotech.fanselection.selection.models;

import java.util.List;

import com.velotech.fanselection.utils.VelotechUtil;

public class CentrifugalFanSelectionModel {   

	private Double dp_flow;//userUnit

	private String dp_uom_flow;

	private Double dp_pressure;//userUnit

	private String dp_uom_pressure;

	private String dp_uom_power;

	private Integer dp_totalQty;

	private Integer dp_frequency;
	
	private String dp_speed;

	private Boolean checkPressureMissTolerance;

	private Integer pressureMissToleranceMin;

	private Integer pressureMissToleranceMax;

	private String dp_searchCriteria;

	private Integer minDia;

	private Integer maxDia;

	private Integer minSpeed;

	private Integer maxSpeed;

	private Integer minPower;

	private Integer maxPower;

	private Double dp_FlowConverted;//baseUOM

	private Double dp_PressureConverted;//baseUOM

	private String dp_primemover_type;

	private String dp_effClass;

	private String dp_specification;

	private String dp_manufacturer;

	private String dp_pmSeries;

	private Double dp_serviceFactor;
	
	private Double minTemperature;
	
	private Double maxTemperature;
	
	private Double elevation;
	
	private String dp_pole;
	
	private List<String> seriesSelected;
	
	private String typeOfDriver;
	
	private Double correctedDensity;
	
	private Double inletTemperature;

	
	public Integer getMinDia() {
		return minDia;
	}

	public void setMinDia(Integer minDia) {
		this.minDia = minDia;
	}

	public Integer getMaxDia() {
		return maxDia;
	}

	public void setMaxDia(Integer maxDia) {
		this.maxDia = maxDia;
	}

	public Integer getMinSpeed() {
		return minSpeed;
	}

	public void setMinSpeed(Integer minSpeed) {
		this.minSpeed = minSpeed;
	}

	public Integer getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Integer maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Integer getMinPower() {
		return minPower;
	}

	public void setMinPower(Integer minPower) {
		this.minPower = minPower;
	}

	public Integer getMaxPower() {
		return maxPower;
	}

	public void setMaxPower(Integer maxPower) {
		this.maxPower = maxPower;
	}

	public Double getDp_flow() {
		return dp_flow;
	}

	public void setDp_flow(Double dp_flow) {
		this.dp_flow = dp_flow;
	}

	public String getDp_uom_flow() {
		return dp_uom_flow;
	}

	public void setDp_uom_flow(String dp_uom_flow) {
		this.dp_uom_flow = dp_uom_flow;
	}

	public Double getDp_pressure() {
		return dp_pressure;
	}

	public void setDp_pressure(Double dp_pressure) {
		this.dp_pressure = dp_pressure;
	}

	public String getDp_uom_pressure() {
		return dp_uom_pressure;
	}

	public void setDp_uom_pressure(String dp_uom_pressure) {
		this.dp_uom_pressure = dp_uom_pressure;
	}

	public String getDp_uom_power() {
		return dp_uom_power;
	}

	public void setDp_uom_power(String dp_uom_power) {
		this.dp_uom_power = dp_uom_power;
	}

	public Integer getDp_totalQty() {
		return dp_totalQty;
	}

	public void setDp_totalQty(Integer dp_totalQty) {
		this.dp_totalQty = dp_totalQty;
	}

	public Integer getDp_frequency() {
		return dp_frequency;
	}

	public void setDp_frequency(Integer dp_frequency) {
		this.dp_frequency = dp_frequency;
	}

	public Boolean getCheckPressureMissTolerance() {
		return checkPressureMissTolerance;
	}

	public void setCheckPressureMissTolerance(Boolean checkPressureMissTolerance) {
		this.checkPressureMissTolerance = checkPressureMissTolerance;
	}

	public Integer getPressureMissToleranceMin() {
		return pressureMissToleranceMin;
	}

	public void setPressureMissToleranceMin(Integer pressureMissToleranceMin) {
		this.pressureMissToleranceMin = pressureMissToleranceMin;
	}

	public Integer getPressureMissToleranceMax() {
		return pressureMissToleranceMax;
	}

	public void setPressureMissToleranceMax(Integer pressureMissToleranceMax) {
		this.pressureMissToleranceMax = pressureMissToleranceMax;
	}

	public String getDp_searchCriteria() {
		return dp_searchCriteria;
	}

	public void setDp_searchCriteria(String dp_searchCriteria) {
		this.dp_searchCriteria = dp_searchCriteria;
	}

	public Double getDp_FlowConverted() {
		return dp_FlowConverted;
	}

	public void setDp_FlowConverted(Double dp_FlowConverted) {
		this.dp_FlowConverted = dp_FlowConverted;
	}

	public Double getDp_PressureConverted() {
		return dp_PressureConverted;
	}

	public void setDp_PressureConverted(Double dp_PressureConverted) {
		this.dp_PressureConverted = dp_PressureConverted;
	}

	public void convertDutyPoint() {

		VelotechUtil velotechUtil = new VelotechUtil();
		this.dp_FlowConverted = velotechUtil.convertFlow(getDp_flow(), dp_uom_flow, "m3/hr");
		this.dp_PressureConverted = velotechUtil.convertPressure(getDp_pressure(), dp_uom_pressure, "MMWG");

	}

	public String getDp_effClass() {
		return dp_effClass;
	}

	public void setDp_effClass(String dp_effClass) {
		this.dp_effClass = dp_effClass;
	}

	public String getDp_specification() {
		return dp_specification;
	}

	public void setDp_specification(String dp_specification) {
		this.dp_specification = dp_specification;
	}

	public String getDp_manufacturer() {
		return dp_manufacturer;
	}

	public void setDp_manufacturer(String dp_manufacturer) {
		this.dp_manufacturer = dp_manufacturer;
	}

	public String getDp_pmSeries() {
		return dp_pmSeries;
	}

	public void setDp_pmSeries(String dp_pmSeries) {
		this.dp_pmSeries = dp_pmSeries;
	}

	public String getDp_primemover_type() {
		return dp_primemover_type;
	}

	public void setDp_primemover_type(String dp_primemover_type) {
		this.dp_primemover_type = dp_primemover_type;
	}

	public Double getDp_serviceFactor() {
		return dp_serviceFactor;
	}

	public void setDp_serviceFactor(Double dp_serviceFactor) {
		this.dp_serviceFactor = dp_serviceFactor;
	}

	public Double getElevation() {
		return elevation;
	}

	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}

	public Double getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(Double minTemperature) {
		this.minTemperature = minTemperature;
	}

	public Double getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(Double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public String getDp_pole() {
		return dp_pole;
	}

	public void setDp_pole(String dp_pole) {
		this.dp_pole = dp_pole;
	}

	public List<String> getSeriesSelected() {
		return seriesSelected;
	}

	public void setSeriesSelected(List<String> seriesSelected) {
		this.seriesSelected = seriesSelected;
	}

	public String getTypeOfDriver() {
		return typeOfDriver;
	}

	public void setTypeOfDriver(String typeOfDriver) {
		this.typeOfDriver = typeOfDriver;
	}

	public String getDp_speed() {
		return dp_speed;
	}

	public void setDp_speed(String dp_speed) {
		this.dp_speed = dp_speed;
	}

	public Double getCorrectedDensity() {
		return correctedDensity;
	}

	public void setCorrectedDensity(Double correctedDensity) {
		this.correctedDensity = correctedDensity;
	}

	public Double getInletTemperature() {
		return inletTemperature;
	}

	public void setInletTemperature(Double inletTemperature) {
		this.inletTemperature = inletTemperature;
	}

	
	
	
}

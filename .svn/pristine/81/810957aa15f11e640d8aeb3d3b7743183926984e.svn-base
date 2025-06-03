package com.velotech.fanselection.utils;

import com.aspose.cad.imageoptions.UnitType;

public class PageSize {
	Boolean isMetric = false;
	Double coefficient = 1.0;

	public PageSize(int unitType) {
		DefineUnitSystem(unitType);
	}

	private void DefineUnitSystem(int unitType) {

		isMetric = false;
		coefficient = 1.0;
		switch (unitType) {
		case UnitType.Parsec:
			coefficient = 3.0857 * 10000000000000000.0;
			isMetric = true;
			break;
		case UnitType.LightYear:
			coefficient = 9.4607 * 1000000000000000.0;
			isMetric = true;
			break;
		case UnitType.AstronomicalUnit:
			coefficient = 1.4960 * 100000000000.0;
			isMetric = true;
			break;
		case UnitType.Gigameter:
			coefficient = 1000000000.0;
			isMetric = true;
			break;
		case UnitType.Kilometer:
			coefficient = 1000.0;
			isMetric = true;
			break;
		case UnitType.Decameter:
			isMetric = true;
			coefficient = 10.0;
			break;
		case UnitType.Hectometer:
			isMetric = true;
			coefficient = 100.0;
			break;
		case UnitType.Meter:
			isMetric = true;
			coefficient = 1.0;
			break;
		case UnitType.Centimenter:
			isMetric = true;
			coefficient = 0.01;
			break;
		case UnitType.Decimeter:
			isMetric = true;
			coefficient = 0.1;
			break;
		case UnitType.Millimeter:
			isMetric = true;
			coefficient = 0.001;
			break;
		case UnitType.Micrometer:
			isMetric = true;
			coefficient = 0.000001;
			break;
		case UnitType.Nanometer:
			isMetric = true;
			coefficient = 0.000000001;
			break;
		case UnitType.Angstrom:
			isMetric = true;
			coefficient = 0.0000000001;
			break;
		case UnitType.Inch:
			coefficient = 1.0;
			break;
		case UnitType.MicroInch:
			coefficient = 0.000001;
			break;
		case UnitType.Mil:
			coefficient = 0.001;
			break;
		case UnitType.Foot:
			coefficient = 12.0;
			break;
		case UnitType.Yard:
			coefficient = 36.0;
			break;
		case UnitType.Mile:
			coefficient = 63360.0;
			break;
		}

	}

	public Boolean getIsMetric() {
		return isMetric;
	}

	public void setIsMetric(Boolean isMetric) {
		this.isMetric = isMetric;
	}

	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

}

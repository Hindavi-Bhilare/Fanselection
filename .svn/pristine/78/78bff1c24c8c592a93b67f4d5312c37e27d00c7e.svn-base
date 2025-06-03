package com.velotech.fanselection.utils;

import java.util.ArrayList;
import java.util.List;

import com.aspose.cad.internal.ay.iF;

public class PressureUnitConverter {

	// Conversion factors from mmwg to other units
	private static final double MMHG_TO_MMWG = 13.5951;
	private static final double PA_TO_MMWG = 0.101972; // 1 Pa = 0.101972 mmwg
	private static final double ATM_TO_MMWG = 101325.0 * PA_TO_MMWG;
	private static final double PSI_TO_MMWG = 6894.75729 * PA_TO_MMWG;
	private static final double BAR_TO_MMWG = 100000.0 * PA_TO_MMWG;
	private static final double KPA_TO_MMWG = 1000.0 * PA_TO_MMWG;
	private static final double NM2_TO_MMWG = PA_TO_MMWG; // 1 N/m² is equivalent to 1 Pascal
	private static final double INWG_TO_MMWG = 25.4; // 1 inwg is approximately 25.4 mmwg
	private static final double MMCE_TO_MMWG = 13.5951; // 1 mmHg is approximately 13.5951 mmwg

	public enum Unit {
		MMWG, MMHG, PA, ATM, PSI, BAR, KPA, NM2, INWG, MMCE
	}

	public List<String> getPressureUnits() {
		List<String> ans = new ArrayList<>();
		ans.add("MMWG");
		ans.add("MMHG");
		ans.add("PA");
		ans.add("ATM");
		ans.add("PSI");
		ans.add("BAR");
		ans.add("KPA");
		ans.add("NM2");
		ans.add("INWG");
		ans.add("MMCE");

		return ans;
	}

	public static double convert(Double value, String fromUnit, String toUnit) {
		// Convert the value to mmwg
		Double valueInMMWG = toMMWG(value, fromUnit);
		
		double factor = 0;
		
		/*
		 * if (fromValue.isNaN()) ans = factor; else ans = fromValue * factor;
		 */
		
		
		if (valueInMMWG.isNaN()) {
			if (fromUnit.equals("MMWG")) {
				if (toUnit.equals("MMWG"))
					factor = 1;
				else if (toUnit.equals("PA"))
					factor = 1/PA_TO_MMWG;
				else if (toUnit.equals("MMHG"))
					factor = 1 / MMHG_TO_MMWG;
				else if (toUnit.equals("NM2"))
					factor = 1 / PA_TO_MMWG;
				else if (toUnit.equals("ATM"))
					factor = 1 / ATM_TO_MMWG;
				else if (toUnit.equals("PSI"))
					factor = 1 / PSI_TO_MMWG;
				else if (toUnit.equals("BAR"))
					factor = 1 / BAR_TO_MMWG;
				else if (toUnit.equals("KPA"))
					factor = 1 / KPA_TO_MMWG;
				
				
				else if (toUnit.equals("INWG"))
					factor = 1 / INWG_TO_MMWG;
				else if (toUnit.equals("MMCE"))
					factor = 1 / MMCE_TO_MMWG;
				
			}
				return factor;
		}
		// Convert the value from mmwg to the target unit
		return fromMMWG(valueInMMWG, toUnit);
	}

	public static double convert(String fromUnit, String toUnit) {
		// Convert the value to mmwg
		double valueInMMWG = toMMWG(fromUnit);
		// Convert the value from mmwg to the target unit
		return fromMMWG(toUnit);
	}

	private static double toMMWG(double value, String unit) {
		switch (unit.toUpperCase()) {
		case "MMHG":
			return value * MMHG_TO_MMWG;
		case "PA":
		case "NM2":
			return value * PA_TO_MMWG;
		case "ATM":
			return value * ATM_TO_MMWG;
		case "PSI":
			return value * PSI_TO_MMWG;
		case "BAR":
			return value * BAR_TO_MMWG;
		case "KPA":
			return value * KPA_TO_MMWG;
		case "INWG":
			return value * INWG_TO_MMWG;
		case "MMCE":
			return value * MMCE_TO_MMWG;
		case "MMWG":
			return value;
		default:
			return value;
		}
	}

	private static double toMMWG(String unit) {
		switch (unit.toUpperCase()) {
		case "MMHG":
			return MMHG_TO_MMWG;
		case "PA":
		case "NM2":
			return PA_TO_MMWG;
		case "ATM":
			return ATM_TO_MMWG;
		case "PSI":
			return PSI_TO_MMWG;
		case "BAR":
			return BAR_TO_MMWG;
		case "KPA":
			return KPA_TO_MMWG;
		case "INWG":
			return INWG_TO_MMWG;
		case "MMCE":
			return MMCE_TO_MMWG;
		case "MMWG":
			return 1;
		default:
			return 1;
		}
	}

	private static double fromMMWG(double value, String unit) {
		switch (unit.toUpperCase()) {
		case "MMHG":
			return value / MMHG_TO_MMWG;
		case "PA":
		case "NM2":
			return value / PA_TO_MMWG;
		case "ATM":
			return value / ATM_TO_MMWG;
		case "PSI":
			return value / PSI_TO_MMWG;
		case "BAR":
			return value / BAR_TO_MMWG;
		case "KPA":
			return value / KPA_TO_MMWG;
		case "INWG":
			return value / INWG_TO_MMWG;
		case "MMCE":
			return value / MMCE_TO_MMWG;
		case "MMWG":
			return value;
		default:
			return value;
		}
	}

	private static double fromMMWG(String unit) {
		switch (unit.toUpperCase()) {
		case "MMHG":
			return 1 / MMHG_TO_MMWG;
		case "PA":
		case "NM2":
			return 1 / PA_TO_MMWG;
		case "ATM":
			return 1 / ATM_TO_MMWG;
		case "PSI":
			return 1 / PSI_TO_MMWG;
		case "BAR":
			return 1 / BAR_TO_MMWG;
		case "KPA":
			return 1 / KPA_TO_MMWG;
		case "INWG":
			return 1 / INWG_TO_MMWG;
		case "MMCE":
			return 1 / MMCE_TO_MMWG;
		case "MMWG":
			return 1;
		default:
			return 1;
		}
	}

	public static void main(String[] args) {
		double value = 100; // example value in mmwg

		System.out.println("100 mmwg in mmHg: " + convert(value, "MMWG", "MMHG"));
		System.out.println("100 mmwg in Pa: " + convert(value, "MMWG", "PA"));
		System.out.println("100 mmwg in atm: " + convert(value, "MMWG", "ATM"));
		System.out.println("100 mmwg in psi: " + convert(value, "MMWG", "PSI"));
		System.out.println("100 mmwg in bar: " + convert(value, "MMWG", "BAR"));
		System.out.println("100 mmwg in kPa: " + convert(value, "MMWG", "KPA"));
		System.out.println("100 mmwg in N/m²: " + convert(value, "MMWG", "NM2"));
		System.out.println("100 mmwg in inwg: " + convert(value, "MMWG", "INWG"));
		System.out.println("100 mmwg in mmce: " + convert(value, "MMWG", "MMCE"));
	}
}

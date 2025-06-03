package com.velotech.fanselection.utils;

import java.util.ArrayList;
import java.util.List;

public class FlowUnitConverter {

    // Conversion factors from m³/h to other units
    private static final double M3H_TO_M3S = 1.0 / 3600.0;
    private static final double M3H_TO_LS = 1000.0 / 3600.0;
    private static final double M3H_TO_LMIN = 1000.0 / 60.0;
    private static final double M3H_TO_GPM = 264.172;
    private static final double M3H_TO_CFM = 35.3147;

    public enum Unit {
        M3H, M3S, LS, LMIN, GPM, CFM
    }
    
    public List<String> getPressureUnits() {
		List<String> ans = new ArrayList<>();
		ans.add("M3H");
		ans.add("M3S");
		ans.add("LS");
		ans.add("LMIN");
		ans.add("GPM");
		ans.add("CFM");

		return ans;
	}
    
    
    

    public static double convert(double value, String fromUnit, String toUnit) {
        // Convert the value to m³/h
        double valueInM3H = toM3H(value, fromUnit);

        // Convert the value from m³/h to the target unit
        return fromM3H(valueInM3H, toUnit);
    }
    
    
    
    
    
    

    private static double toM3H(double value, String unit) {
        switch (unit.toUpperCase()) {
            case "M3S":
                return value / M3H_TO_M3S;
            case "LS":
                return value / M3H_TO_LS;
            case "LMIN":
                return value / M3H_TO_LMIN;
            case "GPM":
                return value / M3H_TO_GPM;
            case "CFM":
                return value / M3H_TO_CFM;
            case "M3H":
            default:
                return value;
        }
    }

    private static double fromM3H(double value, String unit) {
        switch (unit.toUpperCase()) {
            case "M3S":
                return value * M3H_TO_M3S;
            case "LS":
                return value * M3H_TO_LS;
            case "LMIN":
                return value * M3H_TO_LMIN;
            case "GPM":
                return value * M3H_TO_GPM;
            case "CFM":
                return value * M3H_TO_CFM;
            case "M3H":
            default:
                return value;
        }
    }

    public static void main(String[] args) {
        double value = 100; // example value in m³/h

        System.out.println("100 m³/h in m³/s: " + convert(value, "M3H", "M3S"));
        System.out.println("100 m³/h in L/s: " + convert(value, "M3H", "LS"));
        System.out.println("100 m³/h in L/min: " + convert(value, "M3H", "LMIN"));
        System.out.println("100 m³/h in GPM: " + convert(value, "M3H", "GPM"));
        System.out.println("100 m³/h in CFM: " + convert(value, "M3H", "CFM"));
    }
}


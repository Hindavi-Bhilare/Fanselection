
package com.velotech.fanselection.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

public interface VeloUtil {

	public static List<Double> crossSectionalPoint(Double ratedCurrent, String startingMethod, Integer distanceOfCable, Double cos) {

		double[] qValues = { 1.5, 2.5, 4.0, 6.0, 10.0, 16, 25, 35, 50, 70 };

		List<Double> result = new ArrayList<>();
		double deltaU = 3, U = 400, Q1, Q2;

		Double[] iMaxDOL = { 23.0, 30.0, 41.0, 53.0, 74.0, 99.0, 131.0, 162.0, 202.0, 250.0 };

		Double[] iMaxStarDelta = { 40.0, 52.0, 71.0, 92.0, 128.0, 171.0, 227.0, 280.0, 350.0, 433.0 };

		try {
			if (startingMethod.equals("DOL")) {
				Q1 = ((3.1 * distanceOfCable * ratedCurrent * cos) / (deltaU * U));
				Q2 = ((1.55 * distanceOfCable * ratedCurrent * cos) / (deltaU * U));
				for (int i = 0; i < qValues.length; i++) {
					if (qValues[i] > Q1 && ratedCurrent < iMaxDOL[i]) {
						Q1 = qValues[i];
						break;
					}
				}
				for (int j = 0; j < qValues.length; j++) {
					if (qValues[j] > Q2 && ratedCurrent / 2 < iMaxDOL[j]) {
						Q2 = qValues[j];
						break;
					}
				}
			} else if (startingMethod.equals("Autotransformer")) {
				Q1 = ((3.6 * distanceOfCable * ratedCurrent * cos) / (deltaU * U));
				Q2 = ((1.8 * distanceOfCable * ratedCurrent * cos) / (deltaU * U));
				for (int i = 0; i < qValues.length; i++) {
					if (qValues[i] > Q1 && ratedCurrent < iMaxDOL[i]) {
						Q1 = qValues[i];
						break;
					}
				}
				for (int j = 0; j < qValues.length; j++) {
					if (qValues[j] > Q2 && ratedCurrent < iMaxDOL[j]) {
						Q2 = qValues[j];
						break;
					}
				}
			} else {
				Q2 = ((2.1 * distanceOfCable * ratedCurrent * cos) / (deltaU * U));
				Q1 = 0;
				for (int i = 0; i < qValues.length; i++) {
					if (qValues[i] > Q2 && ratedCurrent < iMaxStarDelta[i]) {
						Q2 = qValues[i];
						break;
					}
				}

			}
			result.add(Q1);
			result.add(Q2);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	public static double linearInterp(double[] x, double[] y, double xi) {

		// return linear interpolation of (x,y) on xi
		LinearInterpolator li = new LinearInterpolator();
		PolynomialSplineFunction psf = li.interpolate(x, y);
		double yi = psf.value(xi);
		return yi;
	}
}

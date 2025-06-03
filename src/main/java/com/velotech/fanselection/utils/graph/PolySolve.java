/***************************************************************************
 *   lutusp@arachnoid.com                                                  *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************/

package com.velotech.fanselection.utils.graph;

import java.awt.Color;
import java.util.Vector;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
 * PolySolve.java
 *
 * Created on Oct 16, 2009, 10:35:14 AM
 */
/**
 *
 * @author lutusp
 */
@Component
@Scope("prototype")
public class PolySolve {

	private boolean m_fStandAlone = false;

	String nameVersion = "PolySolve 3.3";

	private String copyright = "Copyright \251 2009, P. Lutus -- http://www.arachnoid.com";

	public Color gridColor = new Color(192, 240, 192);

	public Color zeroColor = new Color(192, 192, 192);

	public Color lineColor = new Color(0, 0, 255);

	public Color dataColor = new Color(255, 0, 0);

	MatrixFunctions mfunct;

	public double dotScale = 4.0;

	public String pageData;

	public String errorMsg = "";

	public boolean data_valid = false;

	public int poly_order = 2; // default order

	public double xmin, xmax, ymin, ymax;

	public double dataXmin, dataXmax;

	public double dataYmin, dataYmax;

	public double eps;

	public int listingForm = 0;

	public boolean error;

	private String defaultData = "-1 -1\n" + "0 3\n" + "1 2.5\n" + "2 5\n" + "3 4\n" + "5 2\n" + "7 5\n" + "9 4\n";

	Vector<Pair> userDataVector;

	Pair[] userData;

	// double[] terms;
	double result_cc;

	double result_se;

	MatrixFunctions m = new MatrixFunctions();

	public void readData(String list) {

		userDataVector = new Vector<Pair>();
		double x = 0, y = 0, q = 0;
		// list = pageData;
		try {
			// filter all but numerical entry characters
			// and trim beginning and end ws
			list = list.replaceAll("[^\\.0-9eE+-]+", " ").trim();
			// create array of numerical values
			String[] num_array = list.split(" ");
			boolean paired = true;
			// parse array using new iterator syntax
			for (String qs : num_array) {
				try {
					q = Double.valueOf(qs).doubleValue();
				} catch (Exception e) {
					String es = "";
					if (qs.length() == 0) {
						es = "No data.";
					} else {
						es = "Cannot parse \"" + qs + "\" in input.";
					}
					throw new Exception(es);
				}
				if (paired) {
					x = q;
				} else {
					y = q;
					userDataVector.add(new Pair(x, y));
					ymin = Math.min(y, ymin);
					ymax = Math.max(y, ymax);
					xmin = Math.min(x, xmin);
					xmax = Math.max(x, xmax);
				}
				paired = !paired;
			}
			if (!paired) {
				throw new Exception("Data not in x,y pairs (odd number of entries) ... add more data.");
			}
		} catch (Exception e) {
			data_valid = false;
			errorMsg = e.getMessage();
			userDataVector.clear();
		}
	}

	public void getData(String list) {

		eps = 1e-12;
		xmin = 1e30;
		ymin = xmin;
		xmax = 1e-30;
		ymax = xmax;
		double x, y;
		readData(list);
		int n = userDataVector.size();
		if (n > 0) {
			if (Math.abs(xmin - xmax) < 1e-3) {
				xmin -= 1e-3;
				xmax += 1e-3;
			}
			if (Math.abs(ymin - ymax) < 1e-3) {
				ymin -= 1e-3;
				ymax += 1e-3;
			}

			dataXmax = xmax;
			dataXmin = xmin;
			dataYmax = ymax;
			dataYmin = ymin;
			double q = (ymax - ymin) / 6;
			ymin -= q;
			ymax += q;
			q = (xmax - xmin) / 6;
			xmin -= q;
			xmax += q;
		}
	}

	void p(String s) {

		System.out.println(s);
	}

	public double[] process(String list, int poly_order) {

		// pageData = dataTextArea.getText();
		double[] terms = new double[poly_order + 1];
		data_valid = false;
		poly_order = (poly_order < 0) ? 0 : poly_order;
		poly_order = (poly_order > 512) ? 512 : poly_order;
		// degreeTextField.setText("" + poly_order);
		errorMsg = "";
		getData(list);
		int size = userDataVector.size();
		if (size > 1) {
			userData = userDataVector.toArray(new Pair[] {});
			terms = m.polyregress(userData, poly_order);
			// result_cc = mfunct.corr_coeff(userData, terms);
			// result_se = mfunct.std_error(userData, terms);

			data_valid = true;
		} else {
			data_valid = false;
			if (errorMsg.length() == 0) {
				errorMsg = "Insufficient Data (minimum of 2 data pairs needed).";
			}
			// resultText.setText("");
		}

		return terms;
	}

	double fx(double x, double[] terms) {

		double a = 0;
		int e = 0;
		for (double i : terms) {
			a += i * Math.pow(x, e);
			e++;
		}
		return a;
	}

	// as simple as I could make it
	// given the misbehavior of polynomials
	double findRoot2(double y, double x, double[] terms, double scale) {

		int max = 256;
		boolean positive = true;
		double epsilon = 1e-8;
		double dy = 0;
		double ody = Double.NaN;
		while (Math.abs(dy = (fx(x, terms) - y)) > epsilon && max-- > 0) {
			if (Double.isInfinite(x)) {
				break;
			}
			if (!Double.isNaN(ody)) {
				if (Math.abs(dy) > ody) {
					positive = !positive;
				}
			}
			ody = Math.abs(dy);
			dy *= scale;
			x += (positive) ? dy : -dy;
		}
		if (max <= 0 || Double.isInfinite(x)) {
			x = Double.NaN;
		}

		return x;
	}

	// begin with small steps, if algorithm fails
	// gradually make them larger
	double findRoot(double y, double x, double[] terms) {

		double scale = Math.pow(2, -32);
		int max = 64;
		double rx;
		while (Double.isNaN(rx = findRoot2(y, x, terms, scale)) && max-- > 0) {
			scale *= 2.0;
		}
		return rx;
	}

	public double plotFunct(double x, double[] terms) {

		return fx(x, terms);
	}
}

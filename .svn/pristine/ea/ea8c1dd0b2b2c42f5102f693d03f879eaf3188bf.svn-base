
package com.velotech.fanselection.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.velotech.fanselection.selection.models.Viscosity;

/**
 * @author SHARAD
 *
 */
@Component
public class ViscosityUtil {

	static Logger log = LogManager.getLogger(ViscosityUtil.class.getName());

	@Autowired
	private VelotechUtil velotechUtil;

	public Viscosity viscosityCorrectionFactor(Viscosity model) {

		double flow = model.getFlow();
		double head = model.getHead();
		double viscosity = model.getViscosity();
		String flow_ucom = model.getFlow_ucom();
		String head_ucom = model.getHead_ucom();
		String viscosity_ucom = model.getViscosity_ucom();
		double CQ = 0;
		double CE = 0;
		double CH1 = 0;
		double CH2 = 0;
		double CH3 = 0;
		double CH4 = 0;
		String ERROR = null;
		if (viscosity == 0d) {
			CQ = 1d;
			CE = 1d;
			CH1 = 1d;
			CH2 = 1d;
			CH3 = 1d;
			CH4 = 1d;
			return new Viscosity(flow, head, viscosity, flow_ucom, head_ucom, viscosity_ucom, CQ, CE, CH1, CH2, CH3, CH4, ERROR);
		}

		double x = 0.0;
		viscosity = velotechUtil.convertViscosity(viscosity, viscosity_ucom, "SSU");
		flow = velotechUtil.convertFlow(flow, flow_ucom, "m3/hr");
		flow = velotechUtil.convertFlow(flow, "m3/hr", "USGPM");
		head = velotechUtil.convertHead(head, head_ucom, "m");
		head = velotechUtil.convertHead(head, "m", "ft");
		// 'Subroutine To Calculate The Correction Factors For A Capacity Equal
		// To Or Less 'Than 100 GPM
		if (flow <= 100) {
			if (flow < 10)
				ERROR = "The FLOW is too low (Flow > 10)";
			else if ((head < 6) || (head > 400))
				ERROR = "The HEAD is out Of Range ( 6 < Head < 400 )";
			else if ((viscosity < 40) || (viscosity > 10000))
				ERROR = "The VISCOSITY is out Of Range ( 40 < VISCOSITY < 10000 )";

			// Calculate the X Position
			if (viscosity < 62)
				x = -2.219 * Math.log10(flow) - 1.092 * Math.log10(head) + 9.259 * Math.log10(viscosity) - 6.929;
			else
				x = -2.219 * Math.log10(flow) - 1.092 * Math.log10(head) + 4.25 * Math.log10(viscosity) + 2.231;

			// Calculate The ETA Correction Factor From The Curve Fits
			if (x < 0.25)
				CE = 1;
			else if (x > 12)
				CE = 0.036;
			else
				CE = 1.009 - 0.0533 * x + 0.01804 * Math.pow(x, 2) - 0.004171 * Math.pow(x, 3) + 0.0002061 * Math.pow(x, 4);

			// Find Correction Factor For Capacity
			if (x < 2.1)
				CQ = 1;
			else if (x > 8)
				CQ = -0.14033 * x + 1.883;
			else
				CQ = 1.097 - 0.06812 * x + 0.01269 * Math.pow(x, 2) - 0.00117 * Math.pow(x, 3);

			// Find Correction Factors For Total Head
			if (x < 7.301) {
				CH3 = 1;
				CH1 = 1;
				CH2 = 1;
				CH4 = 1;
			} else {
				CH3 = -0.2551 + 0.4448 * x - 0.04818 * Math.pow(x, 2) + 0.001482 * Math.pow(x, 3);
				CH1 = CH3;
				CH2 = CH3;
				CH4 = CH3;
			}
		}
		// Subroutine To Calculate The Correction Factors For A Capacity Greater
		// Than 100 GPM But Less Than 10,000 GPM
		else if ((100 < flow) && (flow < 10000)) {
			if (flow > 10000)
				ERROR = "The FLOW is too High (Flow < 10000)";
			else if ((head < 15) || (head > 600))
				ERROR = "The HEAD is out Of Range ( 15 < Head < 600 )";
			else if ((viscosity < 4) || (viscosity > 15000))
				ERROR = "The VISCOSITY is out Of Range ( 4 < VISCOSITY < 15000 )";

			// Calculate the X Position
			if (viscosity < 66)
				x = -1.659 * Math.log10(flow) - 0.841 * Math.log10(head) + 7.297 * Math.log10(viscosity) - 3.298;
			else
				x = -1.659 * Math.log10(flow) - 0.841 * Math.log10(head) + 3.417 * Math.log10(viscosity) + 3.901;

			// Calculate The ETA Correction Factor From The Curve Fits
			if (x < 0.875)
				CE = 1;
			else if (x > 10.5)
				ERROR = "NO DATA AVAILABLE";
			else
				CE = 1.014 - 0.01353 * x - 0.0006971 * Math.pow(x, 2) - 0.0005186 * Math.pow(x, 3);

			// Find Correction Factor For Capacity
			if (x < 5)
				CQ = 1;
			else
				CQ = 1.39 - 0.2022 * x + 0.03609 * Math.pow(x, 2) - 0.00225 * Math.pow(x, 3);

			// Find Correction Factor For Head (.6XQNW)
			if (x < 3.7)
				CH1 = 1;
			else
				CH1 = 1.17 - 0.09042 * x + 0.01573 * Math.pow(x, 2) - 0.0009826 * Math.pow(x, 3);

			// Find Correction Factor For Head (.8XQNW)
			if (x < 3.3125)
				CH2 = 1;
			else
				CH2 = 1.161 - 0.08742 * x + 0.01523 * Math.pow(x, 2) - 0.0009901 * Math.pow(x, 3);

			// Find Correction Factor For Head (1.0XQNW)
			if (x < 3.03125)
				CH3 = 1;
			else
				CH3 = 1.133 - 0.0748 * x + 0.01306 * Math.pow(x, 2) - 0.0009044 * Math.pow(x, 3);

			// Find Correction Factor For Head (1.2XQNW)
			if (x < 1.875)
				CH4 = 1;
			else
				CH4 = 1.057 - 0.04111 * x + 0.007728 * Math.pow(x, 2) - 0.0006723 * Math.pow(x, 3);
		}

		return new Viscosity(flow, head, viscosity, flow_ucom, head_ucom, viscosity_ucom, CQ, CE, CH1, CH2, CH3, CH4, ERROR);
	}
}

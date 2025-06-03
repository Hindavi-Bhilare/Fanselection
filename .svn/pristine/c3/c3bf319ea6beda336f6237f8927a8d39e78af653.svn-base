package com.velotech.fanselection.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.velotech.fanselection.models.Tbl01CentrifugalFanSpeed;
import com.velotech.fanselection.models.Tbl01CentrifugalSpeedData;
import com.velotech.fanselection.utils.graph.PolySolve;

@Component
public class CentrifugalSpeedTermsUtil {
	
	static Logger log = LogManager.getLogger(CentrifugalSpeedTermsUtil.class.getName());

	VelotechUtil velotechUtil = new VelotechUtil();

	PolySolve polySolve = new PolySolve();

	public Tbl01CentrifugalFanSpeed termsFromData(Tbl01CentrifugalFanSpeed tbl01CentrifugalFanSpeed) {

		try {
			List<Double> qStd = new ArrayList<Double>();
			List<Double> hStd = new ArrayList<Double>();
			List<Double> pStd = new ArrayList<Double>();
			List<Double> qpStd = new ArrayList<Double>();

			Iterator<Tbl01CentrifugalSpeedData> tbl01CentrifugalSpeedDataIterator = tbl01CentrifugalFanSpeed
					.getTbl01CentrifugalSpeedData().iterator();
			while (tbl01CentrifugalSpeedDataIterator.hasNext()) {
				Tbl01CentrifugalSpeedData tbl01CentrifugalSpeedData = tbl01CentrifugalSpeedDataIterator.next();
				qStd.add(tbl01CentrifugalSpeedData.getFlow().doubleValue());
				hStd.add(tbl01CentrifugalSpeedData.getPressure().doubleValue());

				if (tbl01CentrifugalSpeedData.getPower() != null && !"".equals(tbl01CentrifugalSpeedData.getPower())) {

					pStd.add(tbl01CentrifugalSpeedData.getPower().doubleValue());
					qpStd.add(tbl01CentrifugalSpeedData.getFlow().doubleValue());
				}

			}
			double[] termsH = polySolve.process(velotechUtil.XY(qStd, hStd), tbl01CentrifugalFanSpeed.getPressureDegree());
			double[] termsP = polySolve.process(velotechUtil.XY(qpStd, pStd), tbl01CentrifugalFanSpeed.getPowerDegree());

			tbl01CentrifugalFanSpeed.setTermsPressure(Arrays.toString(termsH));
			tbl01CentrifugalFanSpeed.setTermsPower(Arrays.toString(termsP));

			tbl01CentrifugalFanSpeed.setQminPressure(Double.parseDouble(String.valueOf(Collections.min(qStd))));
			tbl01CentrifugalFanSpeed.setQmaxPressure(Double.parseDouble(String.valueOf(Collections.max(qStd))));
			tbl01CentrifugalFanSpeed.setQminPower(Double.parseDouble(String.valueOf(Collections.min(qpStd))));
			tbl01CentrifugalFanSpeed.setQmaxPower(Double.parseDouble(String.valueOf(Collections.max(qpStd))));

		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
		}
		return tbl01CentrifugalFanSpeed;
	}
}

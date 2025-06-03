package com.velotech.fanselection.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class SoundUtil {

	private static Logger log = LogManager.getLogger(SoundUtil.class.getName());

	VelotechUtil velotechUtil = new VelotechUtil();

	PressureUnitConverter pressureUnitConverter = new PressureUnitConverter();

	Double flowRateLPS;

	Double refFlow;

	Double pressurePascal;

	Double refPressure;

	Double bt;

	List<Double> soundFrequency;

	List<Double> stdValues;

	List<Double> lwListInside;

	List<Double> lwListOutside;

	Double calfreq;

	Double reffreq;

	Double lpOverallInside;

	Double lpOverallOutside;

	public JSONObject  calculateSound(Double flow, String flowUOM, Double pressure, String pressureUOM, Integer speed,
			Integer noOfBlades) {
		JSONObject soundData = new JSONObject();
		
		try {
			bt = 6d;
			refFlow = 0.472;
			refPressure = 248.8;
			soundFrequency = Arrays.asList(63d, 125d, 250d, 500d, 1000d, 2000d, 4000d, 8000d);
			stdValues = Arrays.asList(44d, 42d, 46d, 44d, 42d, 40d, 37d, 30d);

			flowRateLPS = velotechUtil.convertFlow(flow, flowUOM, "LPS");
			pressurePascal = pressureUnitConverter.convert(pressure, pressureUOM, "PA");

			calfreq = (speed / 60d) * noOfBlades;

			lwListInside = new ArrayList<Double>();
			for (int i = 0; i < soundFrequency.size(); i++) {
				lwListInside.add(stdValues.get(i) + (10 * Math.log10(flowRateLPS / refFlow))
						+ (20 * Math.log10(pressurePascal / refPressure)));
				
			}

			List<Double> temp = soundFrequency.stream().map(i -> Math.abs(i - calfreq)).collect(Collectors.toList());

			int indexOfMinimum = temp.indexOf(Collections.min(temp));

			reffreq = soundFrequency.get(indexOfMinimum);
			lwListInside.set(indexOfMinimum, lwListInside.get(indexOfMinimum) + bt);

			System.out.println(lwListInside);
			lpOverallInside = 0d;
			for (int i = 0; i < lwListInside.size(); i++) {
				lpOverallInside = lpOverallInside + Math.pow(10, lwListInside.get(i) / 10d);
			}
			lpOverallInside = 10d * Math.log10(lpOverallInside);
			System.out.println(lpOverallInside);

			lwListOutside = lwListInside.stream().map(i -> i - 3).collect(Collectors.toList());
			System.out.println(lwListOutside);

			lpOverallOutside = 0d;
			for (int i = 0; i < lwListOutside.size(); i++) {
				lpOverallOutside = lpOverallOutside + Math.pow(10, lwListOutside.get(i) / 10d);
			}
			lpOverallOutside = 10d * Math.log10(lpOverallOutside);
			System.out.println(lpOverallOutside);
			
			 
//	        soundData.put("soundFrequency", soundFrequency);
//	        soundData.put("lwListInside", lwListInside);
//	        soundData.put("lwListOutside", lwListOutside);

	       
	      //  System.out.println(soundData);
			
			 JSONArray soundDataArray = new JSONArray();
		        for (int i = 0; i < soundFrequency.size(); i++) {
		            JSONObject data = new JSONObject();
		            data.put("soundFrequency", soundFrequency.get(i));
		            data.put("lwListInside", lwListInside.get(i));
		            data.put("lwListOutside", lwListOutside.get(i));
		            soundDataArray.put(data);
		        }

		        // Store final data in soundData object
		        soundData.put("soundDataArray", soundDataArray);
		        soundData.put("lpOverallInside", lpOverallInside);
		        soundData.put("lpOverallOutside", lpOverallOutside);
		        
		        System.out.println("SoundDataArray:----"+ soundDataArray);
		        
		        System.out.println("SoundData:----"+ soundData);


		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return soundData;

	}

}

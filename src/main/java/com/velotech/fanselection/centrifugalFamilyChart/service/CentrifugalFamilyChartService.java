
package com.velotech.fanselection.centrifugalFamilyChart.service;

import com.velotech.fanselection.centrifugalFamilyChart.dtos.CentrifugalFamilyChartDto;
import com.velotech.fanselection.utils.ApplicationResponse;

public interface CentrifugalFamilyChartService {

	ApplicationResponse getFamilyChart(CentrifugalFamilyChartDto familyChartDto);
	
	ApplicationResponse getFanModel(Integer seriesId);

	ApplicationResponse getAllSeriesFamilyChart(Integer seriesId, String flow, String flowUnit, String head, 
			String headUnit, double rpm);

	ApplicationResponse getSeries();
}

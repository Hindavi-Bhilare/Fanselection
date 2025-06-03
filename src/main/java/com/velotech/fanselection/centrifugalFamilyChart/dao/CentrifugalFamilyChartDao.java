package com.velotech.fanselection.centrifugalFamilyChart.dao;


import java.util.List;

import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.utils.ApplicationResponse;

public interface CentrifugalFamilyChartDao {

	Tbl01CentrifugalModelMaster getModelPeformance(int fanModelId);

	//List<Integer> getvfdSpeed(int perfCurveId, String frequency);

	ApplicationResponse getSeries();
	
	ApplicationResponse getFanModel(int seriesId);
	
	List<Tbl01CentrifugalModelMaster> getAllModelPeformance(int seriesId);

}

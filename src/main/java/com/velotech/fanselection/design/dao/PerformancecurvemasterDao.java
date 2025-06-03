
package com.velotech.fanselection.design.dao;

import com.velotech.fanselection.models.Tbl01CentrifugalFanSpeed;

public interface PerformancecurvemasterDao {

	

	Tbl01CentrifugalFanSpeed getTbl10PerformanceSpeed(Integer id);

	boolean updateTbl10PerformanceDia(Tbl01CentrifugalFanSpeed tbl01CentrifugalFanSpeed);

	
}

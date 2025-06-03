
package com.velotech.fanselection.admin.service;

import com.velotech.fanselection.utils.ApplicationResponse;

public interface PerformanceSpeedtermsService {

	ApplicationResponse getSeries();

	ApplicationResponse getFanModel(String seriesId);

	ApplicationResponse getPerformanceCurveSpeed(String perfCurveId);

	ApplicationResponse upatePerformanceSpeedterms(String series, String fanModel,  String perfSpeed);

}

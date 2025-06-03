
package com.velotech.fanselection.design.performance.service;

import java.util.ArrayList;



import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.velotech.fanselection.design.performance.dao.PerformanceDataTabularDao;
import com.velotech.fanselection.design.performance.dto.PerformanceDataTableDto;
import com.velotech.fanselection.design.performance.dto.PerformanceDataTabularDto;
//import com.velotech.fanselection.models.Tbl09Performancecurvemaster;
//import com.velotech.fanselection.models.Tbl12ModelPerformance;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.selection.dao.CentrifugalFanSelectionDao;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.VelotechUtil;
import com.velotech.fanselection.utils.graph.PolySolve;

@Service
@Transactional
public class PerformanceDataTabularServiceImpl implements PerformanceDataTabularService {

	static Logger log = LogManager.getLogger(PerformanceDataTabularServiceImpl.class.getName());

	@Autowired
	private CentrifugalFanSelectionDao fanSelectionDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private PerformanceDataTabularDao dao;

	@Autowired
	private PolySolve poly;

	/*
	 * @Override public ApplicationResponse getCharts(PerformanceDataTabularDto dto)
	 * {
	 * 
	 * ApplicationResponse applicationResponse = null;
	 * 
	 * List<PerformanceDataTableDto> dataTableDtos = new ArrayList<>(); try {
	 * 
	 * List<Integer> head = new ArrayList<>(); int step = dto.getIncrementedHead();
	 * 
	 * for (int i = dto.getHeadMin().intValue(); i <= dto.getHead().intValue(); i +=
	 * step) { head.add(i); }
	 * 
	 * List<Tbl12ModelPerformance> modelPerformances = dao.getModelMaster(dto); for
	 * (Tbl12ModelPerformance tbl12ModelPerformance : modelPerformances) {
	 * Tbl09Performancecurvemaster performance =
	 * tbl12ModelPerformance.getTbl09Performancecurvemaster(); if
	 * (performance.getPerformanceCurveNo().equals("HC-65-26-002"))
	 * System.out.println(); Tbl02Modelmaster modelMaster =
	 * tbl12ModelPerformance.getTbl02Modelmaster(); Set<Tbl10PerformanceVane>
	 * performanceDias1 = performance.getTbl10PerformanceDias();
	 * List<Tbl10PerformanceVane> performanceDias = new ArrayList<>();
	 * performanceDias.addAll(performanceDias1);
	 * performanceDias.sort(Comparator.comparingDouble(Tbl10PerformanceVane::
	 * getVariable).reversed());
	 * 
	 * for (Tbl10PerformanceVane tbl10PerformanceDia : performanceDias) { double[]
	 * termsE = velotechUtil.convertStringArrayToDoubleArray(tbl10PerformanceDia.
	 * getTermsEfficiency()); double[] termsH =
	 * velotechUtil.convertStringArrayToDoubleArray(tbl10PerformanceDia.getTermsHead
	 * ()); double[] termsP =
	 * velotechUtil.convertStringArrayToDoubleArray(tbl10PerformanceDia.
	 * getTermsPower()); double[] termsN =
	 * velotechUtil.convertStringArrayToDoubleArray(tbl10PerformanceDia.getTermsNpsh
	 * ());
	 * 
	 * Double xFactorUom = velotechUtil.convertFlow(Double.NaN, "m3/hr",
	 * dto.getFlowUnit()); Double yFactorUom = velotechUtil.convertHead(Double.NaN,
	 * "m", dto.getHeadUnit());
	 * 
	 * double[] termsHFinal =
	 * velotechUtil.terms_Conversion(String.valueOf(xFactorUom),
	 * String.valueOf(yFactorUom), termsH); double[] termsPFinal =
	 * velotechUtil.terms_Conversion(String.valueOf(xFactorUom), "1", termsP);
	 * double[] termsEFinal =
	 * velotechUtil.terms_Conversion(String.valueOf(xFactorUom), "1", termsE);
	 * double[] termsNFinal =
	 * velotechUtil.terms_Conversion(String.valueOf(xFactorUom),
	 * String.valueOf(yFactorUom), termsN); double qmin =
	 * tbl10PerformanceDia.getQminHead().doubleValue() * xFactorUom; double qmax =
	 * tbl10PerformanceDia.getQmaxHead().doubleValue() * xFactorUom; double q = 0, p
	 * = 0, e = 0, n = 0; for (Integer h1 : head) { double[] tempTerms = new
	 * double[termsHFinal.length]; System.arraycopy(termsHFinal, 0, tempTerms, 0,
	 * termsHFinal.length);
	 * 
	 * List<Double> xRoots = velotechUtil.findRoots(tempTerms, h1, qmin, qmax); if
	 * (xRoots.isEmpty()) continue; for (Double double1 : xRoots) { q = double1; } p
	 * = poly.plotFunct(q, termsPFinal); e = poly.plotFunct(q, termsEFinal); n =
	 * poly.plotFunct(q, termsNFinal); if (q == 0 && h1 == 0) {
	 * 
	 * } else { PerformanceDataTableDto performanceDataTable = new
	 * PerformanceDataTableDto(modelMaster.getPumpModel(),
	 * performance.getPerformanceCurveNo(), h1, q, p, e, n);
	 * dataTableDtos.add(performanceDataTable); }
	 * 
	 * } break; } } Random randomGenerator = new Random(); int var =
	 * randomGenerator.nextInt(999999); String fileName = "PerformanceData-" +
	 * dto.getSeries() + var + ".pdf"; String pdfPath =
	 * velotechUtil.getUserRealPath() + fileName; Tbl28CompanyMaster companyMaster =
	 * fanSelectionDao.getDefaultCompany(velotechUtil.getLoginId());
	 * 
	 * Map<String, Object> parameters1 = new HashMap<String, Object>();
	 * parameters1.put("logoPath",
	 * velotechUtil.getCompanyDocumentPath(companyMaster.getLogo()));
	 * parameters1.put("companyName", companyMaster.getDocumentCompanyName());
	 * 
	 * velotechUtil.getReport(dataTableDtos, "performanceSheetData.jasper", pdfPath,
	 * parameters1); applicationResponse =
	 * ApplicationResponseUtil.getResponseToGetData(true,
	 * velotechUtil.getUserContextPath() + fileName);
	 * 
	 * } catch (Exception e) { log.error(e.getMessage(), e); }
	 * 
	 * return applicationResponse; }
	 */

	/*
	 * @Override public ApplicationResponse downloadExcel(PerformanceDataTabularDto
	 * dto) {
	 * 
	 * ApplicationResponse applicationResponse = null;
	 * 
	 * List<PerformanceDataTableDto> dataTableDtos = new ArrayList<>(); try {
	 * 
	 * List<Integer> head = new ArrayList<>(); int step = dto.getIncrementedHead();
	 * 
	 * for (int i = dto.getHeadMin().intValue(); i <= dto.getHead().intValue(); i +=
	 * step) { head.add(i); }
	 * 
	 * List<Tbl12ModelPerformance> modelPerformances = dao.getModelMaster(dto); for
	 * (Tbl12ModelPerformance tbl12ModelPerformance : modelPerformances) {
	 * Tbl09Performancecurvemaster performance =
	 * tbl12ModelPerformance.getTbl09Performancecurvemaster(); if
	 * (performance.getPerformanceCurveNo().equals("HC-65-26-002"))
	 * System.out.println(); Tbl02Modelmaster modelMaster =
	 * tbl12ModelPerformance.getTbl02Modelmaster(); Set<Tbl10PerformanceVane>
	 * performanceDias1 = performance.getTbl10PerformanceDias();
	 * List<Tbl10PerformanceVane> performanceDias = new ArrayList<>();
	 * performanceDias.addAll(performanceDias1);
	 * performanceDias.sort(Comparator.comparingDouble(Tbl10PerformanceVane::
	 * getVariable).reversed());
	 * 
	 * for (Tbl10PerformanceVane tbl10PerformanceDia : performanceDias) { double[]
	 * termsE = velotechUtil
	 * .convertStringArrayToDoubleArray(tbl10PerformanceDia.getTermsEfficiency());
	 * double[] termsH =
	 * velotechUtil.convertStringArrayToDoubleArray(tbl10PerformanceDia.getTermsHead
	 * ()); double[] termsP =
	 * velotechUtil.convertStringArrayToDoubleArray(tbl10PerformanceDia.
	 * getTermsPower()); double[] termsN =
	 * velotechUtil.convertStringArrayToDoubleArray(tbl10PerformanceDia.getTermsNpsh
	 * ());
	 * 
	 * Double xFactorUom = velotechUtil.convertFlow(Double.NaN, "m3/hr",
	 * dto.getFlowUnit()); Double yFactorUom = velotechUtil.convertHead(Double.NaN,
	 * "m", dto.getHeadUnit());
	 * 
	 * double[] termsHFinal =
	 * velotechUtil.terms_Conversion(String.valueOf(xFactorUom),
	 * String.valueOf(yFactorUom), termsH); double[] termsPFinal =
	 * velotechUtil.terms_Conversion(String.valueOf(xFactorUom), "1", termsP);
	 * double[] termsEFinal =
	 * velotechUtil.terms_Conversion(String.valueOf(xFactorUom), "1", termsE);
	 * double[] termsNFinal =
	 * velotechUtil.terms_Conversion(String.valueOf(xFactorUom),
	 * String.valueOf(yFactorUom), termsN); double qmin =
	 * tbl10PerformanceDia.getQminHead().doubleValue() * xFactorUom; double qmax =
	 * tbl10PerformanceDia.getQmaxHead().doubleValue() * xFactorUom; double q = 0, p
	 * = 0, e = 0, n = 0; for (Integer h1 : head) { double[] tempTerms = new
	 * double[termsHFinal.length]; System.arraycopy(termsHFinal, 0, tempTerms, 0,
	 * termsHFinal.length);
	 * 
	 * List<Double> xRoots = velotechUtil.findRoots(tempTerms, h1, qmin, qmax); if
	 * (xRoots.isEmpty()) continue; for (Double double1 : xRoots) { q = double1; } p
	 * = poly.plotFunct(q, termsPFinal); e = poly.plotFunct(q, termsEFinal); n =
	 * poly.plotFunct(q, termsNFinal); if (q == 0 && h1 == 0) {
	 * 
	 * } else { PerformanceDataTableDto performanceDataTable = new
	 * PerformanceDataTableDto( modelMaster.getPumpModel(),
	 * performance.getPerformanceCurveNo(), h1, q, p, e, n);
	 * dataTableDtos.add(performanceDataTable); }
	 * 
	 * } break; } } Random randomGenerator = new Random(); int var =
	 * randomGenerator.nextInt(999999); String fileName = "PerformanceData-" +
	 * dto.getSeries() + var + ".xlsx"; String pdfPath =
	 * velotechUtil.getUserRealPath() + fileName; Tbl28CompanyMaster companyMaster =
	 * fanSelectionDao.getDefaultCompany(velotechUtil.getUsername());
	 * 
	 * Map<String, Object> parameters1 = new HashMap<String, Object>();
	 * parameters1.put("logoPath",
	 * velotechUtil.getCompanyDocumentPath(companyMaster.getLogo()));
	 * parameters1.put("companyName", companyMaster.getDocumentCompanyName());
	 * 
	 * velotechUtil.ireportToxlsx(dataTableDtos, "performanceSheetData.jasper",
	 * pdfPath, parameters1); applicationResponse =
	 * ApplicationResponseUtil.getResponseToGetData(true,
	 * velotechUtil.getUserContextPath() + fileName);
	 * 
	 * } catch (Exception e) { log.error(e.getMessage(), e); }
	 * 
	 * return applicationResponse; }
	 */

}

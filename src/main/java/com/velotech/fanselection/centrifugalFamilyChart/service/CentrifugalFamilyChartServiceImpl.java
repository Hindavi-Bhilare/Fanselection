
package com.velotech.fanselection.centrifugalFamilyChart.service;

import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowagie.text.pdf.PdfReader;
import com.velotech.fanselection.centrifugalFamilyChart.dao.CentrifugalFamilyChartDao;
import com.velotech.fanselection.centrifugalFamilyChart.dtos.CentrifugalFamilyChartDto;
import com.velotech.fanselection.centrifugalFamilyChart.dtos.FamilyChartSheet;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl01CentrifugalFanSpeed;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.ComboBox;
import com.velotech.fanselection.utils.VelotechUtil;
import com.velotech.fanselection.utils.graph.GraphModel;
import com.velotech.fanselection.utils.graph.GraphUtil;
import com.velotech.fanselection.utils.graph.Point;

@Service
@Transactional
public class CentrifugalFamilyChartServiceImpl implements CentrifugalFamilyChartService {

	static Logger log = LogManager.getLogger(CentrifugalFamilyChartServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private CentrifugalFamilyChartDao dao;

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getFamilyChart(CentrifugalFamilyChartDto family) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		String reportPath = "";
		boolean successMsg = false;
		try {

			Tbl01CentrifugalModelMaster modelPerfObj = dao.getModelPeformance(family.getFanModelId());
			reportPath = familychart(family, modelPerfObj, family.getImageName());

			if (reportPath != null && !reportPath.equals(""))
				successMsg = true;
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(successMsg,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, reportPath);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	public String familychart(CentrifugalFamilyChartDto family, Tbl01CentrifugalModelMaster modelPerfObj, String outputFileName) {

		String path = "";
		// ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			DecimalFormat df = new DecimalFormat("0.#");
			JFreeChart chart = null;
			GraphUtil graphUtil = new GraphUtil();
			VelotechUtil velotechUtil = new VelotechUtil();
			double rpm = 0d;
			if (family.getRpm() > 0)
				rpm = family.getRpm();

			List<GraphModel> graphModelListPressure = new ArrayList<GraphModel>();

			List<GraphModel> graphModelListPower = new ArrayList<GraphModel>();

			List<Point> dutyPointList = new ArrayList<Point>();

			if (!family.getFlow().equals("") && !family.getHead().equals(""))
				dutyPointList
						.add(new Point(Double.parseDouble(family.getFlow()), Double.parseDouble(family.getHead())));

			Tbl01CentrifugalModelMaster modelMaster = modelPerfObj;
			
			/*
			 * Tbl02Modelmaster modelMaster = (Tbl02Modelmaster)
			 * genericDao.getRecordById(Tbl02Modelmaster.class, family.getFanModelId());
			 */

			@SuppressWarnings("unchecked")
			List<Tbl01CentrifugalFanSpeed> tbl01CentrifugalFanSpeeds = (List<Tbl01CentrifugalFanSpeed>) genericDao
					.getRecordsByParentId(Tbl01CentrifugalFanSpeed.class, "tbl01CentrifugalModelMaster.id",
							modelMaster.getId());

			List<Double> variableParameter = new ArrayList<Double>();

			Map<Integer, Tbl01CentrifugalFanSpeed> centrifugalFanSpeedMap = new HashMap<Integer, Tbl01CentrifugalFanSpeed>();

			for (Tbl01CentrifugalFanSpeed tbl01CentrifugalFanSpeed : tbl01CentrifugalFanSpeeds) {
				centrifugalFanSpeedMap.put(tbl01CentrifugalFanSpeed.getSpeed(), tbl01CentrifugalFanSpeed);

			}

			SortedSet<Integer> treset = new TreeSet<Integer>(centrifugalFanSpeedMap.keySet()).descendingSet();
			Tbl01CentrifugalFanSpeed centrifugalFanSpeed = new Tbl01CentrifugalFanSpeed();
			for (Integer cfsobj : treset) {
				centrifugalFanSpeed = centrifugalFanSpeedMap.get(cfsobj);
				double[] termsH = velotechUtil.convertStringArrayToDoubleArray(centrifugalFanSpeed.getTermsPressure());
				double[] termsP = velotechUtil.convertStringArrayToDoubleArray(centrifugalFanSpeed.getTermsPower());
				
                 
				Double factor = 1d;
				List<Integer> speedList = new ArrayList<Integer>();
				

				speedList.add(cfsobj);
				// need to add intermediate speed
				//if isAffinity is True then add intermediatespeed
				if(modelMaster.getIsAffinityLaw()) {
					String intermediateSpeedString = modelMaster.getIntermediateSpeed();
					Arrays.stream(intermediateSpeedString.split(","))
		              .map(Integer::parseInt)
		              .forEach(speedList::add);
				}
				//till here 
				
				Collections.sort(speedList);
				for (Integer speed : speedList) {
					factor = (speed / centrifugalFanSpeed.getSpeed().doubleValue());

					double[] termsHSpeed = velotechUtil.terms_Conversion(String.valueOf(factor),
							String.valueOf(Math.pow(factor, 2)), termsH);
					double[] termsPSpeed = velotechUtil.terms_Conversion(String.valueOf(factor),
							String.valueOf(Math.pow(factor, 3)), termsP);

					Double xFactorUom = velotechUtil.convertFlow(Double.NaN, "m3/hr", family.getFlowUnit());
					Double yFactorUom = velotechUtil.convertPressure(Double.NaN, "MMWG", family.getHeadUnit());

					double[] termsHFinal = velotechUtil.terms_Conversion(String.valueOf(xFactorUom),
							String.valueOf(yFactorUom), termsHSpeed);
					double[] termsPFinal = velotechUtil.terms_Conversion(String.valueOf(xFactorUom), "1", termsPSpeed);

					double qmin = centrifugalFanSpeed.getQminPressure().doubleValue() * factor * xFactorUom;
					double qpmin = centrifugalFanSpeed.getQminPower().doubleValue() * factor * xFactorUom;

					double qmax = centrifugalFanSpeed.getQmaxPressure().doubleValue() * factor * xFactorUom;
					double qpmax = centrifugalFanSpeed.getQmaxPower().doubleValue() * factor * xFactorUom;

					double rangeQmin = centrifugalFanSpeed.getRangeQMin().doubleValue() * factor * xFactorUom;
					double rangeQmax = centrifugalFanSpeed.getRangeQMax().doubleValue() * factor * xFactorUom;
					String annoTemp = "";

					annoTemp = speed.toString();

					if (termsH.length > 0) {
						graphModelListPressure.add(new GraphModel(termsHFinal, qmin, qmax, true, "blue", "Solid", 2,
								"red", "verticalRectangle", annoTemp));

						graphModelListPower.add(new GraphModel(termsPFinal, qpmin, qpmax, true, "blue", "Solid", 2,
								"red", "verticalRectangle", annoTemp));

					}
				}

//				graphModelListPressure.add(new GraphModel(
//						convertStringArrayToDoubleArray(vaneJsonObject.get("pressureEq").toString()),
//						vaneJsonObject.getDouble("minFlow"), vaneJsonObject.getDouble("maxFlow"), true, "blue", "Solid",
//						lineThikness, "red", "verticalRectangle", vaneJsonObject.get("vaneAngle").toString()));
//
//				graphModelListPower.add(new GraphModel(
//						convertStringArrayToDoubleArray(vaneJsonObject.get("powerEq").toString()),
//					vaneJsonObject.getDouble("minFlow"), vaneJsonObject.getDouble("maxFlow"), true, "blue", "Solid",
//						lineThikness, "red", "verticalRectangle", vaneJsonObject.get("vaneAngle").toString()));

			}

			List<Double> isoEffList = new ArrayList<Double>();
			Map<Double, Boolean> checkJoin = new HashMap<Double, Boolean>();

			CombinedDomainXYPlot combinedDomainXYPlot = graphUtil.combineGraph("Flow in " + family.getFlowUnit() + "",
					true, 0);

			XYPlot plotHead = graphUtil.individualGraph(graphModelListPressure, dutyPointList,
					"Pressure(" + family.getHeadUnit() + ")", new java.awt.Point(0, 1), true, true, true);

			XYPlot plotPower = graphUtil.individualGraph(graphModelListPower, null, "Power(kW)",
					new java.awt.Point(0, 1), true, true, true);

			combinedDomainXYPlot = getGraphWeight(family, combinedDomainXYPlot, plotHead, plotPower);

			String subHeading = "";

			chart = graphUtil.getChart("", subHeading, combinedDomainXYPlot, true);

			chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 3));
			/*
			 * if (!family.getFlow().isEmpty()) { ValueMarker marker = new
			 * ValueMarker(Double.parseDouble(family.getFlow()), Color.blue, new
			 * BasicStroke(1.0f)); plotHead.addDomainMarker(marker, Layer.BACKGROUND);
			 * 
			 * plotPower.addDomainMarker(marker, Layer.BACKGROUND);
			 * 
			 * }
			 */
			// String outputFileName = family.getImageName();
			Tbl28CompanyMaster companyMaster = (Tbl28CompanyMaster) genericDao.getRecordById(Tbl28CompanyMaster.class,
					velotechUtil.getCompanyId());

			FamilyChartSheet sheet = new FamilyChartSheet(modelMaster, chart);
			String logoPath = velotechUtil.getCompanyDocumentPath() + companyMaster.getLogo();
			sheet.setLogoPath(logoPath);
			sheet.setCompanyName(companyMaster.getDocumentCompanyName());

			List<FamilyChartSheet> familyChartSheets = new ArrayList<FamilyChartSheet>();
			familyChartSheets.add(sheet);
			path = velotechUtil.getReport(familyChartSheets, "familyChart.jasper", outputFileName,
					new HashMap<String, Object>());

			/*
			 * applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
			 * ApplicationConstants.DATA_LOAD_SUCCESS_MSG, reportPath);
			 */
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return path;
	}

	private CombinedDomainXYPlot getGraphWeight(CentrifugalFamilyChartDto family, CombinedDomainXYPlot combinedDomainXYPlot,
			XYPlot plotHead, XYPlot plotPower) {

		combinedDomainXYPlot.add(plotHead, 3);

		combinedDomainXYPlot.add(plotPower, 2);

		return combinedDomainXYPlot;
	}

	@Override
	public ApplicationResponse getFanModel(Integer seriesId) {
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<ComboBox> dtos = new ArrayList<>();
			ComboBox box = new ComboBox();

			box.setLabel("All");
			box.setValue("All");
			dtos.add(box);

			@SuppressWarnings("unchecked")
			List<Tbl01CentrifugalModelMaster> tbl01CentrifugalModelMasters = (List<Tbl01CentrifugalModelMaster>) dao
					.getFanModel(seriesId).getData();
			for (Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster : tbl01CentrifugalModelMasters) {
				ComboBox dto = new ComboBox();
				dto.setLabel(tbl01CentrifugalModelMaster.getFanModel());
				dto.setValue(tbl01CentrifugalModelMaster.getId());
				dtos.add(dto);
			}
			long total = applicationResponse.getTotal();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getAllSeriesFamilyChart(Integer seriesId, String flow, String flowUnit, String head,
			String headUnit, double rpm
	/* , String stage, String frequency, String isIso, String isVFD */) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		String reportPath = "";
		Random randomGenerator = new Random();
		List<PdfReader> pdfReaderList = new ArrayList<PdfReader>();
		try {
			DecimalFormat df = new DecimalFormat("0.#");

			CentrifugalFamilyChartDto family = new CentrifugalFamilyChartDto();
			family.setSeriesId(seriesId);
			family.setFlow(flow);
			family.setFlowUnit(flowUnit);
			family.setHead(head);
			family.setHeadUnit(headUnit);
			family.setRpm(rpm);
			// family.setStage(stage);
			// family.setFrequency(frequency);
			// family.setIsIso(isIso);
			// family.setIsVFD(isVFD);

			List<Tbl01CentrifugalModelMaster> modelPerfObjList = dao.getAllModelPeformance(family.getSeriesId());

			for (Tbl01CentrifugalModelMaster modelPerfObj : modelPerfObjList) {

				int var = randomGenerator.nextInt(999999);
				String outputFileName = "Familychart" + var + ".pdf";
				reportPath = familychart(family, modelPerfObj, outputFileName);
				if (reportPath != null && !reportPath.equals(""))
					pdfReaderList.add(new PdfReader(velotechUtil.getUserRealPath() + outputFileName));
			}

			if (pdfReaderList.size() > 0)
				reportPath = velotechUtil.mergePDF(pdfReaderList, "performanceChartReport_" + family.getImageName());

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, reportPath);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getSeries() {
		// TODO Auto-generated method stub
		return null;
	}

}

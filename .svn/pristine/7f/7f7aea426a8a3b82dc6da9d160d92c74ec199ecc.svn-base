
package com.velotech.fanselection.selection.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl01CentrifugalFanSpeed;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl1401Motortype;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl95TxPumpSelectionModel;
import com.velotech.fanselection.models.Tbl95TxSelectedPumps;
import com.velotech.fanselection.selection.dao.CentrifugalFanSelectionDao;
import com.velotech.fanselection.selection.models.CentrifugalFanSelectionModel;
import com.velotech.fanselection.selection.models.RejectedCentrifugalFan;
import com.velotech.fanselection.selection.models.SelectedCentrifugalFan;
import com.velotech.fanselection.selection.models.SelectedRejectedCentrifugalFans;
import com.velotech.fanselection.utils.VelotechUtil;
import com.velotech.fanselection.utils.graph.GraphUtil;
import com.velotech.fanselection.utils.graph.PolySolve;

@Service
@Scope("prototype")
public class CentrifugalFanSelector {

	private static Logger log = LogManager.getLogger(CentrifugalFanSelector.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CentrifugalFanSelectionDao dao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private PolySolve polySolve;

	@Autowired
	private GraphUtil graphUtil;

	private List<SelectedCentrifugalFan> selectedFanList;

	private List<RejectedCentrifugalFan> rejectedFanList;

	private CentrifugalFanSelectionModel fanSelectionModel;

	private Integer tempSpeed;

	private List<Integer> variableParameterList;

	private Map<Integer, Tbl01CentrifugalFanSpeed> variableParameterVsSpeed;

	private Map<Integer, List<double[]>> variableParameterVsTerms;

	private Map<Integer, List<List<Double>>> variableParameterVsData;

	private String nearMissReason = "";

	private DecimalFormat string2DForm = new DecimalFormat("#.##");

	private DecimalFormat string4DForm = new DecimalFormat("#.####");

	private Double actualPressure;

	private Double dpPower;

	private Integer selectedSpeed;

	private double[] termsPressureSelected;

	private double[] termsPowerSelected;

	private double qMinSelected;

	private double qMaxSelected;

	private double correctedDensityAtMinTemp;

	private double correctedDensityAtMaxTemp;

	private Double inletArea;
	
	private Double outletDynamicPressure;
	
	private Double correctedStaticPressure;
	
	private Double outletVelocity;

	public CentrifugalFanSelector() {

		super();
	}

	public SelectedRejectedCentrifugalFans getSelectedRejectedFanList(CentrifugalFanSelectionModel selectionModel) {

		SelectedRejectedCentrifugalFans selectedRejectedFanList = new SelectedRejectedCentrifugalFans();
		try {
			Long timeStamp = System.currentTimeMillis();

			selectedFanList = new ArrayList<>();
			rejectedFanList = new ArrayList<>();
			fanSelectionModel = selectionModel;

			List<Double> tempSpeedPrimemover = new ArrayList<Double>();

			/* if (selectionModel.getDp_searchCriteria().equals("Primemover speed")) {
			 * tempSpeedPrimemover = dao.getTempPrimemoverSpeed(selectionModel); if
			 * (tempSpeedPrimemover == null || tempSpeedPrimemover.isEmpty()) {
			 * rejectedFanList.add(new RejectedCentrifugalFan("", null, null, null,
			 * "Selected PrimeMover Data Not Found"));
			 * selectedRejectedFanList.setSelectedFans(selectedFanList);
			 * selectedRejectedFanList.setRejectedFans(rejectedFanList); return
			 * selectedRejectedFanList; } }
			 */

			List<Tbl01CentrifugalModelMaster> tbl01CentrifugalModelMaster = dao.getmodelMaster(selectionModel);

			iterateModelPerformance(selectedFanList, rejectedFanList, tbl01CentrifugalModelMaster, tempSpeedPrimemover);

			Integer fanSelectionModelId = saveSelectionModel(selectionModel);

			List<SelectedCentrifugalFan> selectedFans = saveSelectedFans(selectedFanList, fanSelectionModelId);

			selectedRejectedFanList.setSelectedFans(selectedFanList);
			selectedRejectedFanList.setRejectedFans(rejectedFanList);

			System.out.println("Time to do 1 copy with copyProperties: " + (System.currentTimeMillis() - timeStamp));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return selectedRejectedFanList;
	}

	private Integer saveSelectionModel(CentrifugalFanSelectionModel selectionModel2) throws JSONException {

		Tbl95TxPumpSelectionModel tbl95TxPumpSelectionModel = new Tbl95TxPumpSelectionModel();
		try {
			Gson gson = new Gson();

			dao.deleteTbl95TxPumpSelectionModel();

			// Save PumpSelectionModel for current sessionId
			JSONObject jsonSelectionModel = new JSONObject(gson.toJson(selectionModel2));

			tbl95TxPumpSelectionModel.setPumpSelectionModel(jsonSelectionModel.toString());
			tbl95TxPumpSelectionModel.setSessionId(velotechUtil.getSessionId());
			tbl95TxPumpSelectionModel.setCompany(velotechUtil.getCompany());
			tbl95TxPumpSelectionModel.setCreatedDate(new Date());
			dao.saveTbl95TxPumpSelectionModel(tbl95TxPumpSelectionModel);
			// genericDao.save(tbl95TxPumpSelectionModel);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl95TxPumpSelectionModel.getId();
	}

	private List<SelectedCentrifugalFan> saveSelectedFans(List<SelectedCentrifugalFan> selectedFans,
			Integer fanSelectionModelId) throws JSONException {

		try {
			Gson gson = new Gson();

			dao.deleteTbl95TxSelectedPumps();

			// Save selected pumps for current sessionId

			for (SelectedCentrifugalFan selectedFan : selectedFans) {
				Tbl95TxSelectedPumps txSelectedPump = new Tbl95TxSelectedPumps();

				JSONObject jsonSelectedPumps = new JSONObject(gson.toJson(selectedFan));
				txSelectedPump.setSelectedPump(jsonSelectedPumps.toString());
				txSelectedPump.setPumpSelectionModelId(fanSelectionModelId);
				txSelectedPump.setSessionId(velotechUtil.getSessionId());
				txSelectedPump.setCompany(velotechUtil.getCompany());
				txSelectedPump.setCreatedDate(new Date());
				dao.saveTbl95TxSelectedPumps(txSelectedPump);

				selectedFan.setId(txSelectedPump.getId());

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return selectedFans;
	}

	private void iterateModelPerformance(List<SelectedCentrifugalFan> selectedFanList,
			List<RejectedCentrifugalFan> rejectedFanList, List<Tbl01CentrifugalModelMaster> modelmasterList,
			List<Double> tempSpeedPrimemover) {

		for (int i = 0; i < modelmasterList.size(); i++) {
			if (modelmasterList.get(i).getFanModel().equals("TEAB-5-1600")) // NC-4-0A3-4
				System.out.println("wait");
			nearMissReason = "";
			Tbl01CentrifugalModelMaster modelmaster = modelmasterList.get(i);

			setData(modelmaster);

			if (variableParameterList.isEmpty()) {
				rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), 0, 0, modelmaster.getFanDia(),
						"No Speed Data Found"));
				continue;
			}
			Collections.sort(variableParameterList, Collections.reverseOrder());

			if (!iterateVariableParameterList(modelmaster, rejectedFanList))
				continue;

			Tbl14PrimemoverMaster tbl14PrimemoverMaster = getPrimeMover(modelmaster);

			if (checkAdditionalConstraints(modelmaster, rejectedFanList)) {
				continue;
			}

			SelectedCentrifugalFan selectedFan = setSelectedFanValuesJson(modelmaster, tbl14PrimemoverMaster);
			selectedFan.setNearMissReason(nearMissReason);

			selectedFanList.add(selectedFan);

		}
	}

	private boolean iterateVariableParameterList(Tbl01CentrifugalModelMaster modelmaster,
			List<RejectedCentrifugalFan> rejectedFanList) {
		Boolean ans = false;
		try {

			/*
			 * if (!checkFeasibilityModel(modelmaster, rejectedFanList)) return ans;
			 */

			Boolean selected = false;

			outletVelocity=0d;
			outletDynamicPressure=0d;
			
			if (modelmaster.getInletArea() > 0d) {
				outletVelocity = velotechUtil
						.roundAvoid(((fanSelectionModel.getDp_FlowConverted() / 3600) / modelmaster.getInletArea()), 2);

				outletDynamicPressure = velotechUtil.roundAvoid(
						(((fanSelectionModel.getCorrectedDensity() * outletVelocity * outletVelocity) / 2) / 9.81), 2);
			}

			correctedStaticPressure = (fanSelectionModel.getDp_PressureConverted()
					* fanSelectionModel.getCorrectedDensity()) / 1.2;

			Double totalPressure = correctedStaticPressure + outletDynamicPressure;

			if (modelmaster.getIsAffinityLaw())
				selected = iterateVariableParameterListAffinity(modelmaster, rejectedFanList, totalPressure);
			else
				selected = iterateVariableParameterListMultipleSpeed(modelmaster, rejectedFanList, totalPressure);

			if (!selected)
				return ans;

			ans = true;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}


	private Boolean iterateVariableParameterListMultipleSpeed(Tbl01CentrifugalModelMaster modelmaster,
			List<RejectedCentrifugalFan> rejectedFanList, Double totalPressure) {
		Boolean ans = false;
		try {
			for (int i = 0; i < variableParameterList.size() - 1; i++) {

				Integer maxSpeed = variableParameterList.get(i);
				Integer minSpeed = variableParameterList.get(i + 1);

				double[] termsPressureMax = variableParameterVsTerms.get(maxSpeed).get(0);
				double[] termsPressureMin = variableParameterVsTerms.get(minSpeed).get(0);

				double maxPressure = polySolve.plotFunct(fanSelectionModel.getDp_FlowConverted(), termsPressureMax);
				double minPressure = polySolve.plotFunct(fanSelectionModel.getDp_FlowConverted(), termsPressureMin);

				if (!(totalPressure >= minPressure && totalPressure <= maxPressure))
					continue;

				if (fanSelectionModel.getDp_searchCriteria().equalsIgnoreCase("Primemover speed")) {
					tempSpeed = (int) velotechUtil.annotation_TRIM(maxPressure, minPressure, maxSpeed.doubleValue(),
							minSpeed.doubleValue(), totalPressure);

				} else if (Integer.valueOf(fanSelectionModel.getDp_speed()) > minSpeed
						&& Integer.valueOf(fanSelectionModel.getDp_speed()) < maxSpeed) {
					tempSpeed = Integer.valueOf(fanSelectionModel.getDp_speed());
				} else {
					continue;
				}

				List<Double> qStdRpm = variableParameterVsData.get(maxSpeed).get(0);
				List<Double> qMinRpm = variableParameterVsData.get(minSpeed).get(0);

				List<List<Double>> qhTrim = graphUtil.trimCurveCalculation(Collections.min(qStdRpm),
						Collections.max(qStdRpm), Collections.min(qMinRpm), Collections.max(qMinRpm),
						String.valueOf(termsPressureMax.length - 1), termsPressureMax, termsPressureMax,
						termsPressureMin, termsPressureMin, maxSpeed.toString(), minSpeed.toString(), tempSpeed);

				termsPressureSelected = polySolve.process(graphUtil.getXy(qhTrim.get(0), qhTrim.get(1)),
						termsPressureMax.length - 1);

				actualPressure = polySolve.plotFunct(totalPressure, termsPressureSelected);

				List<Double> qpStdRpm = variableParameterVsData.get(maxSpeed).get(1);
				List<Double> qpMinRpm = variableParameterVsData.get(minSpeed).get(1);

				double[] termsPowerMax = variableParameterVsTerms.get(maxSpeed).get(1);
				double[] termsPowerMin = variableParameterVsTerms.get(minSpeed).get(1);

				List<List<Double>> qpTrim = graphUtil.trimCurveCalculation(Collections.min(qpStdRpm),
						Collections.max(qpStdRpm), Collections.min(qpMinRpm), Collections.max(qpMinRpm),
						String.valueOf(termsPowerMax.length - 1), termsPowerMax, termsPowerMax, termsPowerMin,
						termsPowerMin, maxSpeed.toString(), minSpeed.toString(), tempSpeed);

				termsPowerSelected = polySolve.process(graphUtil.getXy(qpTrim.get(0), qpTrim.get(1)),
						termsPowerMax.length - 1);

				dpPower = polySolve.plotFunct(fanSelectionModel.getDp_FlowConverted(), termsPowerSelected);

				double rangeQminTrimDia = graphUtil.y_TRIM(variableParameterVsSpeed.get(maxSpeed).getRangeQMin(),
						variableParameterVsSpeed.get(minSpeed).getRangeQMin(), maxSpeed, minSpeed, tempSpeed);
				double rangeQmaxTrimDia = graphUtil.y_TRIM(variableParameterVsSpeed.get(maxSpeed).getRangeQMax(),
						variableParameterVsSpeed.get(minSpeed).getRangeQMax(), maxSpeed, minSpeed, tempSpeed);

				qMinSelected = graphUtil.y_TRIM(variableParameterVsSpeed.get(maxSpeed).getQminPressure(),
						variableParameterVsSpeed.get(minSpeed).getQminPressure(), maxSpeed, minSpeed, tempSpeed);
				qMaxSelected = graphUtil.y_TRIM(variableParameterVsSpeed.get(maxSpeed).getQmaxPressure(),
						variableParameterVsSpeed.get(minSpeed).getQmaxPressure(), maxSpeed, minSpeed, tempSpeed);

				if (!checkFeasibilitySpeed(tempSpeed, rejectedFanList, modelmaster, rangeQminTrimDia, rangeQmaxTrimDia))
					continue;

				selectedSpeed = tempSpeed;

				ans = true;
				break;

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	private Boolean iterateVariableParameterListAffinity(Tbl01CentrifugalModelMaster modelmaster,
			List<RejectedCentrifugalFan> rejectedFanList, Double totalPressure) {
		Boolean ans = false;
		try {

			double[] termsPressure = variableParameterVsTerms.get(variableParameterList.get(0)).get(0);
			double[] termsPower = variableParameterVsTerms.get(variableParameterList.get(0)).get(1);

			if (fanSelectionModel.getDp_searchCriteria().equalsIgnoreCase("Primemover speed")) {
				tempSpeed = velotechUtil.findFanSpeed(fanSelectionModel.getDp_FlowConverted(), totalPressure,
						variableParameterList.get(0).doubleValue(), termsPressure);

			} else {
				tempSpeed = Integer.valueOf(fanSelectionModel.getDp_speed());
			}

			if (tempSpeed > modelmaster.getMaxSpeed() || tempSpeed < modelmaster.getMinSpeed()) {
				ans = false;
				return ans;
			}

			termsPressureSelected = velotechUtil.terms_head_rpm_Conversion(tempSpeed.doubleValue(),
					variableParameterList.get(0).doubleValue(), termsPressure);

			termsPowerSelected = velotechUtil.terms_power_rpm_Conversion(tempSpeed.doubleValue(),
					variableParameterList.get(0).doubleValue(), termsPower);

			actualPressure = polySolve.plotFunct(fanSelectionModel.getDp_FlowConverted(), termsPressureSelected);
			dpPower = polySolve.plotFunct(fanSelectionModel.getDp_FlowConverted(), termsPowerSelected);

			qMinSelected = (tempSpeed.doubleValue() / variableParameterList.get(0).doubleValue())
					* variableParameterVsSpeed.get(variableParameterList.get(0)).getQminPressure();
			qMaxSelected = (tempSpeed.doubleValue() / variableParameterList.get(0).doubleValue())
					* variableParameterVsSpeed.get(variableParameterList.get(0)).getQmaxPressure();

			if (!checkFeasibilitySpeedAffinity(rejectedFanList, modelmaster))
				return ans;

			selectedSpeed = tempSpeed;
			ans = true;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	private Tbl14PrimemoverMaster getPrimeMover(Tbl01CentrifugalModelMaster modelmaster) {
		Tbl14PrimemoverMaster ans = null;
		try {
			double primemoverCal = dpPower * fanSelectionModel.getDp_serviceFactor();

			ans = getprimeMoverList(primemoverCal, modelmaster);
			if (ans == null)
				nearMissReason += "Motor Not Available";

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	private Boolean checkAdditionalConstraints(Tbl01CentrifugalModelMaster modelmaster,
			List<RejectedCentrifugalFan> rejectedFanList) {

		Boolean ans = false;
		if (fanSelectionModel.getMinDia() != null) {
			if (modelmaster.getFanDia() < fanSelectionModel.getMinDia()) {
				rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), 0, selectedSpeed,
						modelmaster.getFanDia(), "Fan Diameter is less than user input fan diameter"));
				ans = true;
			}
		}

		if (fanSelectionModel.getMaxDia() != null) {
			if (modelmaster.getFanDia() > fanSelectionModel.getMaxDia()) {
				rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), 0, selectedSpeed,
						modelmaster.getFanDia(), "Fan Diameter is more than user input fan diameter"));
				ans = true;
			}
		}
		if (fanSelectionModel.getMinSpeed() != null) {
			if (tempSpeed < fanSelectionModel.getMinSpeed()) {
				rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), 0, selectedSpeed,
						modelmaster.getFanDia(), "Fan Speed is less than user input speed"));
				ans = true;
			}
		}

		if (fanSelectionModel.getMaxSpeed() != null) {
			if (tempSpeed > fanSelectionModel.getMaxSpeed()) {
				rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), 0, selectedSpeed,
						modelmaster.getFanDia(), "Fan Speed is more than user input speed"));
				ans = true;
			}
		}

		if (fanSelectionModel.getMinPower() != null) {
			if (dpPower < fanSelectionModel.getMinPower()) {
				rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), 0, selectedSpeed,
						modelmaster.getFanDia(), "Fan Power is less than user input power"));
				ans = true;
			}
		}

		if (fanSelectionModel.getMaxPower() != null) {
			if (dpPower > fanSelectionModel.getMaxPower()) {
				rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), 0, selectedSpeed,
						modelmaster.getFanDia(), "Fan Power is more than user input power"));
				ans = true;
			}
		}
		return ans;
	}

	private SelectedCentrifugalFan setSelectedFanValuesJson(Tbl01CentrifugalModelMaster modelmaster,
			Tbl14PrimemoverMaster tbl14PrimemoverMaster) {
		SelectedCentrifugalFan selectedFan = new SelectedCentrifugalFan();
		try {
			JSONObject ans = new JSONObject();
			// Centrifugal new formulas calculation value
			double referenceDensity = 1.293;
			double referenceTemperatureInKelwin = 273;
			double referencePressureInPascal = 101325.0375;

			double atmosphericPressureInPascal = 1.1975 * (Math.pow(10, -8))
					* (Math.pow((288.15 - (0.0065 * fanSelectionModel.getElevation())), 5.25588));
			atmosphericPressureInPascal = velotechUtil.roundAvoid(atmosphericPressureInPascal, 2);
			System.out.println("AtmosphericPressure in Pascal: " + atmosphericPressureInPascal);

			correctedDensityAtMinTemp = referenceDensity * (referenceTemperatureInKelwin / (referenceTemperatureInKelwin + fanSelectionModel.getMinTemperature()))
					* ((atmosphericPressureInPascal - (fanSelectionModel.getDp_PressureConverted() * 9.81)) / referencePressureInPascal);
			correctedDensityAtMinTemp = velotechUtil.roundAvoid(correctedDensityAtMinTemp, 3);
			System.out.println("CorrectedDensity At MinTemperature:" + correctedDensityAtMinTemp);

			correctedDensityAtMaxTemp = referenceDensity * (referenceTemperatureInKelwin / (referenceTemperatureInKelwin + fanSelectionModel.getMaxTemperature()))
					* ((atmosphericPressureInPascal - (fanSelectionModel.getDp_PressureConverted() * 9.81)) / referencePressureInPascal);
			correctedDensityAtMaxTemp = velotechUtil.roundAvoid(correctedDensityAtMaxTemp, 3);
			System.out.println("CorrectedDensity At MaxTemperature:" + correctedDensityAtMaxTemp);

			// Till here new formulas value calculated

			double dpFlow = fanSelectionModel.getDp_flow();
			double dpFlowConverted = fanSelectionModel.getDp_FlowConverted();
			// double dpEff = velotechUtil.roundAvoid(((dpFlowConverted / 3600) *
			// actualPressure) / (102 * dpPower), 4);

			double dpPressure = velotechUtil.roundAvoid(
					velotechUtil.convertPressure(correctedStaticPressure, "MMWG", fanSelectionModel.getDp_uom_pressure()), 2);
			double dpPowerTemp = velotechUtil
					.roundAvoid(velotechUtil.convertPower(dpPower, "kW", fanSelectionModel.getDp_uom_power()), 2);

			// new formula for power at max and min temperature
			// double powerAtMinTemp = velotechUtil.roundAvoid(dpPower *
			// correctedDensityAtMinTemp / 1.2, 2);
			// System.out.println("Power at Minimum Temperature: " + powerAtMinTemp);
			double powerAtMaxTemp = velotechUtil.roundAvoid(dpPower * correctedDensityAtMaxTemp / 1.2, 2);
			System.out.println("Power at Maximum Temperature: " + powerAtMaxTemp);
			double powerAtMinTemp = velotechUtil
					.roundAvoid((powerAtMaxTemp * correctedDensityAtMinTemp) / correctedDensityAtMaxTemp, 2);
			System.out.println("Power at Minimum Temperature: " + powerAtMinTemp);
			double dpEff = velotechUtil.roundAvoid(((dpFlowConverted / 3600) * actualPressure) / (102 * powerAtMaxTemp),
					4);
			System.out.println("Static Efficiency: " + dpEff);

			double density = 1.2;
			// Till here

			// shifting claculating formulas from TechnicalDatasheet 22-03-2025//

			

			double outletDynamicPressureUOM = velotechUtil.convertPressure(outletDynamicPressure, "MMWG",
					fanSelectionModel.getDp_uom_pressure());



			double totalPressure = (outletDynamicPressureUOM + dpPressure);
			System.out.println("totalPressure: " + totalPressure);
			double totalEfficiency = velotechUtil.roundAvoid(((dpFlowConverted / 3600)
					* (velotechUtil.convertPressure(totalPressure, fanSelectionModel.getDp_uom_pressure(), "MMWG")))
					/ (102 * powerAtMaxTemp), 4);

			// Till here

			selectedFan.setDpFlow(dpFlow);
			selectedFan.setDpPressure(dpPressure);
			selectedFan.setDpEff(dpEff);
			selectedFan.setCorrectedDensityAtMinTemp(correctedDensityAtMinTemp);
			selectedFan.setCorrectedDensityAtMaxTemp(correctedDensityAtMaxTemp);
			selectedFan.setElevation(fanSelectionModel.getElevation());
			selectedFan.setMaxTempreture(fanSelectionModel.getMaxTemperature());
			selectedFan.setMinTemperature(fanSelectionModel.getMinTemperature());
			selectedFan.setPowerAtMinTemp(powerAtMinTemp);
			selectedFan.setPowerAtMaxTemp(powerAtMaxTemp);
			selectedFan.setFanGD2(modelmaster.getFanGD2());
			selectedFan.setOutletDynamicPressure(outletDynamicPressure);
			selectedFan.setTotalPressure(totalPressure);
			selectedFan.setTotalEfficiency(totalEfficiency);

//			  double velocity = velotechUtil .roundAvoid((dpFlowConverted / 3600) / (3.14 *
//			  impellerRadius * impellerRadius), 2);

			selectedFan.setVelocity(outletVelocity);
			selectedFan.setDpPower(dpPowerTemp);
			selectedFan.setSpeed(tempSpeed);
			selectedFan.setFanModel(modelmaster.getFanModel());
			selectedFan.setSeries(modelmaster.getTbl01fantype().getSeries());
			selectedFan.setSelectedSpeed(selectedSpeed);
			selectedFan.setFanTypeUuid(modelmaster.getUuid());

			if (tbl14PrimemoverMaster != null) {
				selectedFan.setPrimemoverId(tbl14PrimemoverMaster.getPrimemoverId());
				selectedFan.setPrimemoverFrame(tbl14PrimemoverMaster.getFramesize());
				selectedFan.setPrimemoverPower(tbl14PrimemoverMaster.getPower());
				selectedFan.setPrimemoverSpeed(tbl14PrimemoverMaster.getSpeed().intValue());
				selectedFan.setPrimeMoverUuid(tbl14PrimemoverMaster.getUuid());
				selectedFan.setMotorTypeUuid(tbl14PrimemoverMaster.getTbl1401Motortype().getUuid());

			}

			ans.put("dpFlow", dpFlow);
			ans.put("dpPressure", dpPressure);
			ans.put("pressureUOM", fanSelectionModel.getDp_uom_pressure());
			ans.put("flowUOM", fanSelectionModel.getDp_uom_flow());
			ans.put("powerUOM", fanSelectionModel.getDp_uom_power());
			ans.put("dpPower", dpPowerTemp);
			ans.put("dpEff", dpEff);
			ans.put("speed", tempSpeed);
			ans.put("velocity", outletVelocity);

			JSONArray speedJsonArray = new JSONArray();

			List<Integer> speedList = new ArrayList<Integer>();
			speedList.addAll(variableParameterList);
			if (modelmaster.getIsAffinityLaw()) {
				String intermediateSpeedString = modelmaster.getIntermediateSpeed();
				Arrays.stream(intermediateSpeedString.split(",")).map(Integer::parseInt).forEach(speedList::add);
			}

			// for selectedSpeed
			JSONObject speedJsonObject = new JSONObject();
			speedJsonObject.put("selectedSpeed", selectedSpeed);

			double[] termsPressure = velotechUtil.terms_Conversion(
					String.valueOf(velotechUtil.convertFlow(Double.NaN, "m3/hr", fanSelectionModel.getDp_uom_flow())),
					String.valueOf(velotechUtil.convertPressure("MMWG", fanSelectionModel.getDp_uom_pressure())),
					termsPressureSelected);

			speedJsonObject.put("pressureEq", termsPressure);

			double[] termsPower = velotechUtil.terms_Conversion(
					String.valueOf(velotechUtil.convertFlow(Double.NaN, "m3/hr", fanSelectionModel.getDp_uom_flow())),
					String.valueOf(velotechUtil.convertPower(Double.NaN, "kW", fanSelectionModel.getDp_uom_power())),
					termsPowerSelected);

			speedJsonObject.put("powerEq", termsPower);
			speedJsonObject.put("minFlow",
					velotechUtil.convertFlow(qMinSelected, "m3/hr", fanSelectionModel.getDp_uom_flow()));
			speedJsonObject.put("maxFlow",
					velotechUtil.convertFlow(qMaxSelected, "m3/hr", fanSelectionModel.getDp_uom_flow()));

			speedJsonObject.put("isSelected", true);
			speedJsonArray.put(speedJsonObject);

			if (modelmaster.getIsAffinityLaw()) {

				double[] termsPressureBase = velotechUtil.terms_Conversion(
						String.valueOf(
								velotechUtil.convertFlow(Double.NaN, "m3/hr", fanSelectionModel.getDp_uom_flow())),
						String.valueOf(velotechUtil.convertPressure("MMWG", fanSelectionModel.getDp_uom_pressure())),
						variableParameterVsTerms.get(speedList.get(0)).get(0));

				double[] termsPowerBase = velotechUtil.terms_Conversion(
						String.valueOf(
								velotechUtil.convertFlow(Double.NaN, "m3/hr", fanSelectionModel.getDp_uom_flow())),
						String.valueOf(
								velotechUtil.convertPower(Double.NaN, "kW", fanSelectionModel.getDp_uom_power())),
						variableParameterVsTerms.get(speedList.get(0)).get(1));

				double minFlow = velotechUtil.convertFlow((variableParameterVsData.get(speedList.get(0)).get(0).get(0)),
						"m3/hr", fanSelectionModel.getDp_uom_flow());

				double maxFlow = velotechUtil.convertFlow((variableParameterVsData.get(speedList.get(0)).get(0).get(1)),
						"m3/hr", fanSelectionModel.getDp_uom_flow());

				for (int i = 0; i < speedList.size(); i++) {
					speedJsonObject = new JSONObject();
					speedJsonObject.put("selectedSpeed", speedList.get(i));

					termsPressure = velotechUtil.terms_head_rpm_Conversion(speedList.get(i).doubleValue(),
							speedList.get(0).doubleValue(), termsPressureBase);

					speedJsonObject.put("pressureEq", termsPressure);

					termsPower = velotechUtil.terms_power_rpm_Conversion(speedList.get(i).doubleValue(),
							speedList.get(0).doubleValue(), termsPowerBase);

					speedJsonObject.put("powerEq", termsPower);
					speedJsonObject.put("minFlow",
							(speedList.get(i).doubleValue() / speedList.get(0).doubleValue()) * minFlow);
					speedJsonObject.put("maxFlow",
							(speedList.get(i).doubleValue() / speedList.get(0).doubleValue()) * maxFlow);

					speedJsonObject.put("isSelected", false);
					speedJsonArray.put(speedJsonObject);
					System.out.println(selectedSpeed);

				}

			} else {
				for (int i = 0; i < speedList.size(); i++) {
					speedJsonObject = new JSONObject();
					speedJsonObject.put("selectedSpeed", speedList.get(i));

					termsPressure = velotechUtil.terms_Conversion(
							String.valueOf(
									velotechUtil.convertFlow(Double.NaN, "m3/hr", fanSelectionModel.getDp_uom_flow())),
							String.valueOf(
									velotechUtil.convertPressure("MMWG", fanSelectionModel.getDp_uom_pressure())),
							variableParameterVsTerms.get(speedList.get(i)).get(0));

					speedJsonObject.put("pressureEq", termsPressure);

					termsPower = velotechUtil.terms_Conversion(
							String.valueOf(
									velotechUtil.convertFlow(Double.NaN, "m3/hr", fanSelectionModel.getDp_uom_flow())),
							String.valueOf(
									velotechUtil.convertPower(Double.NaN, "kW", fanSelectionModel.getDp_uom_power())),
							variableParameterVsTerms.get(speedList.get(i)).get(1));

					speedJsonObject.put("powerEq", termsPower);
					speedJsonObject.put("minFlow",
							velotechUtil.convertFlow((variableParameterVsData.get(speedList.get(i)).get(0).get(0)),
									"m3/hr", fanSelectionModel.getDp_uom_flow()));
					speedJsonObject.put("maxFlow",
							velotechUtil.convertFlow((variableParameterVsData.get(speedList.get(i)).get(0).get(1)),
									"m3/hr", fanSelectionModel.getDp_uom_flow()));

					speedJsonObject.put("isSelected", false);
					speedJsonArray.put(speedJsonObject);
					System.out.println(selectedSpeed);

				}
			}

			ans.put("speedDetails", speedJsonArray);

			ans.put("selectedCentrifugalFan", new JSONObject(velotechUtil.gson().toJson(selectedFan)));
			ans.put("tbl01CentrifugalModelMaster", new JSONObject(velotechUtil.gsonWithExpose().toJson(modelmaster)));
			if (tbl14PrimemoverMaster != null) {
				ans.put("tbl14PrimemoverMaster",
						new JSONObject(velotechUtil.gsonWithExpose().toJson(tbl14PrimemoverMaster)));

				Tbl1401Motortype tbl1401Motortype = dao
						.gettbl1401Motortype(tbl14PrimemoverMaster.getTbl1401Motortype().getId());
				if (tbl1401Motortype != null) {
					Tbl1401Motortype newtbl1401Motortype = new Tbl1401Motortype();
					BeanUtils.copyProperties(tbl1401Motortype, newtbl1401Motortype);
					ans.put("tbl1401Motortype",
							new JSONObject(velotechUtil.gsonWithExpose().toJson(newtbl1401Motortype)));
				}
			}

			selectedFan.setSelectedFanJson(ans.toString());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return selectedFan;
	}

	private Tbl14PrimemoverMaster getprimeMoverList(double primemoverCal, Tbl01CentrifugalModelMaster modelmaster) {
		List<Tbl14PrimemoverMaster> primeMoverMasterList = new ArrayList<>();
		Tbl14PrimemoverMaster primemoverMaster = null;
		try {

			primeMoverMasterList = dao.getPrimeMoverForMotorSelection(primemoverCal, modelmaster, fanSelectionModel,
					selectedSpeed);
			if (primeMoverMasterList.size() > 0) {
				primemoverMaster = primeMoverMasterList.get(0);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return primemoverMaster;
	}

	private Boolean checkFeasibilityModel(Tbl01CentrifugalModelMaster modelmaster,
			List<RejectedCentrifugalFan> rejectedFanList) {
		Boolean ans = false;
		try {
			if (modelmaster.getIsAffinityLaw()) {

				double minFlow = (modelmaster.getMinSpeed().doubleValue() / variableParameterList.get(0).doubleValue())
						* variableParameterVsSpeed.get(variableParameterList.get(0)).getQminPressure();
				double maxFlow = variableParameterVsSpeed.get(variableParameterList.get(0)).getQmaxPressure();
				if (minFlow > fanSelectionModel.getDp_FlowConverted()
						|| maxFlow < fanSelectionModel.getDp_FlowConverted()) {
					rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), 0,
							variableParameterList.get(0), modelmaster.getFanDia(), "Flow out of Range"));
					return ans;
				}

				double[] termsPressureMin = velotechUtil.terms_head_rpm_Conversion(
						modelmaster.getMinSpeed().doubleValue(), variableParameterList.get(0).doubleValue(),
						variableParameterVsTerms.get(variableParameterList.get(0)).get(0));
				double[] termsPressureMax = variableParameterVsTerms.get(variableParameterList.get(0)).get(0);

				double minPressure = polySolve.plotFunct(fanSelectionModel.getDp_FlowConverted(), termsPressureMin);
				double maxPressure = polySolve.plotFunct(fanSelectionModel.getDp_FlowConverted(), termsPressureMax);
				if (minPressure > fanSelectionModel.getDp_PressureConverted()
						* (1 + fanSelectionModel.getPressureMissToleranceMin() / 100)
						|| maxPressure < fanSelectionModel.getDp_PressureConverted()
								* (1 + fanSelectionModel.getPressureMissToleranceMin() / 100)) {
					rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), 0,
							variableParameterList.get(0), modelmaster.getFanDia(), "Pressure out of Range"));
					return ans;
				}
				ans = true;

			} else {

				double minFlow = variableParameterVsSpeed.get(Collections.min(variableParameterList)).getQminPressure();
				double maxFlow = variableParameterVsSpeed.get(Collections.max(variableParameterList)).getQmaxPressure();
				if (minFlow > fanSelectionModel.getDp_FlowConverted()
						|| maxFlow < fanSelectionModel.getDp_FlowConverted()) {
					rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), 0,
							variableParameterList.get(0), modelmaster.getFanDia(), "Flow out of Range"));
					return ans;
				}

				double[] termsPressureMin = variableParameterVsTerms.get(Collections.min(variableParameterList)).get(0);
				double[] termsPressureMax = variableParameterVsTerms.get(Collections.max(variableParameterList)).get(0);
				double minPressure = polySolve.plotFunct(fanSelectionModel.getDp_FlowConverted(), termsPressureMin);
				double maxPressure = polySolve.plotFunct(fanSelectionModel.getDp_FlowConverted(), termsPressureMax);
				if (minPressure > fanSelectionModel.getDp_PressureConverted()
						* (1 + fanSelectionModel.getPressureMissToleranceMin() / 100)
						|| maxPressure < fanSelectionModel.getDp_PressureConverted()
								* (1 + fanSelectionModel.getPressureMissToleranceMin() / 100)) {
					rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), 0,
							variableParameterList.get(0), modelmaster.getFanDia(), "Pressure out of Range"));
					return ans;
				}
				ans = true;

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;

	}

	private Boolean checkFeasibilitySpeed(Integer selectedSpeed, List<RejectedCentrifugalFan> rejectedFanList,
			Tbl01CentrifugalModelMaster modelmaster, double rangeQminTrimDia, double rangeQmaxTrimDia) {
		Boolean ans = false;
		try {

			if (rangeQminTrimDia > fanSelectionModel.getDp_FlowConverted()
					|| rangeQmaxTrimDia < fanSelectionModel.getDp_FlowConverted()) {
				rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), selectedSpeed, selectedSpeed,
						modelmaster.getFanDia(), "Flow out of Range"));
				return ans;
			}

			if (actualPressure < fanSelectionModel.getDp_PressureConverted()
					* (1 + fanSelectionModel.getPressureMissToleranceMin() / 100d)) {
				rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), selectedSpeed, selectedSpeed,
						modelmaster.getFanDia(), "Pressure out of Range"));
				return ans;
			}
			ans = true;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;

	}

	private Boolean checkFeasibilitySpeedAffinity(List<RejectedCentrifugalFan> rejectedFanList,
			Tbl01CentrifugalModelMaster modelmaster) {
		Boolean ans = false;
		try {
			double minFlow = velotechUtil.Q_rpm_conversion(tempSpeed.doubleValue(),
					variableParameterList.get(0).doubleValue(),
					variableParameterVsData.get(variableParameterList.get(0)).get(0).get(0));
			double maxFlow = velotechUtil.Q_rpm_conversion(tempSpeed.doubleValue(),
					variableParameterList.get(0).doubleValue(),
					variableParameterVsData.get(variableParameterList.get(0)).get(0).get(1));
			if (minFlow > fanSelectionModel.getDp_FlowConverted()
					|| maxFlow < fanSelectionModel.getDp_FlowConverted()) {
				rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), selectedSpeed,
						variableParameterList.get(0), modelmaster.getFanDia(), "Flow out of Range"));
				return ans;
			}

			if (actualPressure < fanSelectionModel.getDp_PressureConverted()
					* (1 + fanSelectionModel.getPressureMissToleranceMin() / 100d)
			/*
			 * || actualPressure > fanSelectionModel.getDp_PressureConverted() (1 +
			 * fanSelectionModel.getPressureMissToleranceMax() / 100d)
			 */) {
				rejectedFanList.add(new RejectedCentrifugalFan(modelmaster.getFanModel(), selectedSpeed,
						variableParameterList.get(0), modelmaster.getFanDia(), "Pressure out of Range"));
				return ans;
			}
			ans = true;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;

	}

	private void setData(Tbl01CentrifugalModelMaster modelmaster) {

		try {

			variableParameterList = new ArrayList<>();
			variableParameterVsSpeed = new HashMap<>();
			variableParameterVsTerms = new HashMap<>();
			variableParameterVsData = new HashMap<>();

			@SuppressWarnings("unchecked")
			List<Tbl01CentrifugalFanSpeed> masterList = (List<Tbl01CentrifugalFanSpeed>) genericDao
					.getRecordsByParentId(Tbl01CentrifugalFanSpeed.class, "tbl01CentrifugalModelMaster.id",
							modelmaster.getId());
			List<Tbl01CentrifugalFanSpeed> tbl01CentrifugalFanSpeeds = new ArrayList<Tbl01CentrifugalFanSpeed>();
			if (modelmaster.getIsAffinityLaw()) {
				Tbl01CentrifugalFanSpeed maxSpeed = Collections.max(masterList,
						Comparator.comparing(s -> s.getSpeed()));
				tbl01CentrifugalFanSpeeds.add(maxSpeed);
			} else {
				tbl01CentrifugalFanSpeeds.addAll(masterList);
			}

			for (Tbl01CentrifugalFanSpeed tbl01CentrifugalFanSpeed : tbl01CentrifugalFanSpeeds) {
				variableParameterList.add(tbl01CentrifugalFanSpeed.getSpeed());
				variableParameterVsSpeed.put(tbl01CentrifugalFanSpeed.getSpeed(), tbl01CentrifugalFanSpeed);
				double[] termsPressure = velotechUtil
						.convertStringArrayToDoubleArray(tbl01CentrifugalFanSpeed.getTermsPressure());
				double[] termsPower = velotechUtil
						.convertStringArrayToDoubleArray(tbl01CentrifugalFanSpeed.getTermsPower());

				List<double[]> termsList = new ArrayList<double[]>();
				termsList.add(termsPressure);
				termsList.add(termsPower);

				variableParameterVsTerms.put(tbl01CentrifugalFanSpeed.getSpeed(), termsList);
				List<Double> QminMaxHead = new ArrayList<Double>();
				List<Double> QminMaxPower = new ArrayList<Double>();

				if (tbl01CentrifugalFanSpeed.getQminPressure() != null)
					QminMaxHead.add(tbl01CentrifugalFanSpeed.getQminPressure().doubleValue());
				if (tbl01CentrifugalFanSpeed.getQmaxPressure() != null)
					QminMaxHead.add(tbl01CentrifugalFanSpeed.getQmaxPressure().doubleValue());
				if (tbl01CentrifugalFanSpeed.getQminPower() != null)
					QminMaxPower.add(tbl01CentrifugalFanSpeed.getQminPower().doubleValue());
				if (tbl01CentrifugalFanSpeed.getQmaxPower() != null)
					QminMaxPower.add(tbl01CentrifugalFanSpeed.getQmaxPower().doubleValue());

				List<List<Double>> perfdataList = new ArrayList<List<Double>>();
				perfdataList.add(QminMaxHead);
				perfdataList.add(QminMaxPower);
				variableParameterVsData.put(tbl01CentrifugalFanSpeed.getSpeed(), perfdataList);

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}


package com.velotech.fanselection.selection.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.jfree.chart.JFreeChart;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lowagie.text.pdf.PdfReader;
import com.octomix.josson.Josson;
import com.velotech.fanselection.common.service.JsonService;
import com.velotech.fanselection.dtos.AddToOfferDto;
import com.velotech.fanselection.dtos.FanTypeDto;
import com.velotech.fanselection.dtos.UserInputDto;
import com.velotech.fanselection.dxf.model.OfferDrawingPojo;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.ireportmodels.PerformanceChartCondition;
import com.velotech.fanselection.ireportmodels.PerformanceChartSheet;
import com.velotech.fanselection.ireportmodels.TechnicalDataSheet;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl01VariantLine;
import com.velotech.fanselection.models.Tbl1401Motortype;
//import com.velotech.fanselection.models.Tbl1410MotorTypeBom;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl23OfferMaster;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl27RequirementsDp;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.models.Tbl28SelectedFan;
import com.velotech.fanselection.models.Tbl28SelectedFanJsonValue;
import com.velotech.fanselection.models.Tbl28SelectedFanVariant;
import com.velotech.fanselection.models.Tbl28SelectedPricing;
import com.velotech.fanselection.models.Tbl28SelectedPrimemover;
import com.velotech.fanselection.models.Tbl28SelectedpricingDetails;
import com.velotech.fanselection.models.Tbl90DocumentMaster;
import com.velotech.fanselection.models.Tbl95TxPumpSelectionModel;
import com.velotech.fanselection.models.Tbl95TxSelectedPumps;
import com.velotech.fanselection.offer.service.OfferDetailsService;
import com.velotech.fanselection.selection.dao.CentrifugalFanSelectionDao;
import com.velotech.fanselection.selection.models.AdditionalCentrifugalFanAttributes;
import com.velotech.fanselection.selection.models.CentrifugalFanSelectionModel;
import com.velotech.fanselection.selection.models.SelectedCentrifugalFan;
import com.velotech.fanselection.selection.models.SelectedRejectedCentrifugalFans;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.ComboBox;
import com.velotech.fanselection.utils.JFreeChartSvgRenderer;
import com.velotech.fanselection.utils.SoundUtil;
import com.velotech.fanselection.utils.VelotechUtil;

import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;

@Service
@Transactional
@Scope("prototype")
public class CentrifugalFanSelectionServiceImpl implements CentrifugalFanSelectionService {

	static Logger log = LogManager.getLogger(CentrifugalFanSelectionServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private JsonService jsonService;

	@Autowired
	private CentrifugalFanSelector fanSelector;

	@Autowired
	private CentrifugalFanSelectionDao dao;

	@Autowired
	private OfferDetailsService offerDetailsService;

	@Override
	public ApplicationResponse getFanTypes(String searchValue) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			List<Tbl01Fantype> models = dao.getFanType(searchValue);
			long total = models.size();
			List<FanTypeDto> dtos = getFanTypes(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<FanTypeDto> getFanTypes(List<Tbl01Fantype> models) {

		List<FanTypeDto> dtos = new ArrayList<>();
		for (Tbl01Fantype model : models) {
			FanTypeDto dto = new FanTypeDto();

			BeanUtils.copyProperties(model, dto);
			if (model.getDocumentMasterImage() != null) {
				String path = velotechUtil.getCompanyContextPathOthers();
				dto.setImageName(path + model.getDocumentMasterImage().getFileName());
			}

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public ApplicationResponse getFanSelection(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			CentrifugalFanSelectionModel fanSelectionModel = new CentrifugalFanSelectionModel();
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);

			fanSelectionModel = mapper.readValue(requestPayload, CentrifugalFanSelectionModel.class);

			fanSelectionModel = setDefaultValues(fanSelectionModel);

			SelectedRejectedCentrifugalFans selectedRejectedFans = doSelection(fanSelectionModel);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, selectedRejectedFans);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private CentrifugalFanSelectionModel setDefaultValues(CentrifugalFanSelectionModel fanSelectionModel) {

		try {

			fanSelectionModel.convertDutyPoint();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return fanSelectionModel;
	}

	private SelectedRejectedCentrifugalFans doSelection(CentrifugalFanSelectionModel fanSelectionModel)
			throws JSONException {

		SelectedRejectedCentrifugalFans selectedRejectedFans = new SelectedRejectedCentrifugalFans();
		try {

			NumberFormat percentage = NumberFormat.getPercentInstance();
			percentage.setMinimumFractionDigits(1);

			selectedRejectedFans = fanSelector.getSelectedRejectedFanList(fanSelectionModel);

			Collections.sort(selectedRejectedFans.getSelectedFans());
			selectedRejectedFans.setSelectedFans(selectedRejectedFans.getSelectedFans());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return selectedRejectedFans;

	}

	@Override
	public ApplicationResponse addToOffer(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();

		try {

			AddToOfferDto addToOfferDto = new AddToOfferDto();
			ObjectMapper mapper = new ObjectMapper();
			addToOfferDto = mapper.readValue(requestPayload, AddToOfferDto.class);

			Tbl95TxSelectedPumps tbl95TxSelectedPump = (Tbl95TxSelectedPumps) genericDao
					.getRecordById(Tbl95TxSelectedPumps.class, addToOfferDto.getSelectedFanId());
			Tbl95TxPumpSelectionModel tbl95TxPumpSelectionModel = (Tbl95TxPumpSelectionModel) genericDao
					.getRecordById(Tbl95TxPumpSelectionModel.class, tbl95TxSelectedPump.getPumpSelectionModelId());
			Gson gson = new Gson();
			CentrifugalFanSelectionModel selectionModel = gson
					.fromJson(tbl95TxPumpSelectionModel.getPumpSelectionModel(), CentrifugalFanSelectionModel.class);
			SelectedCentrifugalFan selectedFan = gson.fromJson(tbl95TxSelectedPump.getSelectedPump(),
					SelectedCentrifugalFan.class);
			// selectedFan.setMoc(addToOfferDto.getMoc());
			makeOfferFan(selectionModel, selectedFan, addToOfferDto);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getPerformanceGraph(Integer selectedFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl95TxSelectedPumps tbl95TxSelectedPump = (Tbl95TxSelectedPumps) genericDao
					.getRecordById(Tbl95TxSelectedPumps.class, selectedFanId);
			Tbl95TxPumpSelectionModel tbl95TxPumpSelectionModel = (Tbl95TxPumpSelectionModel) genericDao
					.getRecordById(Tbl95TxPumpSelectionModel.class, tbl95TxSelectedPump.getPumpSelectionModelId());

			Gson gson = new Gson();
			CentrifugalFanSelectionModel selectionModel = gson
					.fromJson(tbl95TxPumpSelectionModel.getPumpSelectionModel(), CentrifugalFanSelectionModel.class);
			SelectedCentrifugalFan selectedFan = gson.fromJson(tbl95TxSelectedPump.getSelectedPump(),
					SelectedCentrifugalFan.class);

			JFreeChart chart = velotechUtil
					.getPerformanceChartImage(new JSONObject(selectedFan.getSelectedFanJson().toString()));

			Tbl28CompanyMaster tbl28CompanyMaster = (Tbl28CompanyMaster) genericDao
					.getRecordById(Tbl28CompanyMaster.class, velotechUtil.getCompanyId());
			Tbl14PrimemoverMaster tbl14PrimemoverMaster=null;
if(selectedFan.getPrimemoverId()!=null)
			 tbl14PrimemoverMaster = (Tbl14PrimemoverMaster) genericDao.getRecordById(Tbl14PrimemoverMaster.class, selectedFan.getPrimemoverId());

			PerformanceChartSheet performanceChartSheet = new PerformanceChartSheet(chart);
			performanceChartSheet.setLogoPath(velotechUtil.getCompanyDocumentPath(tbl28CompanyMaster.getLogo()));
			performanceChartSheet.setCompanyName(tbl28CompanyMaster.getDocumentCompanyName());
			performanceChartSheet.setCompanyAddress(tbl28CompanyMaster.getDocumentComapnyAddress());
			performanceChartSheet.setPreparedBy(velotechUtil.getUsername());
			performanceChartSheet.setFanModel(selectedFan.getFanModel());
			performanceChartSheet.setDpFlow(selectedFan.getDpFlow());
			performanceChartSheet.setDpHead(selectedFan.getDpPressure());
			performanceChartSheet.setUomFlow(selectionModel.getDp_uom_flow());
			performanceChartSheet.setUomHead(selectionModel.getDp_uom_pressure());
			performanceChartSheet.setVanAngle(selectedFan.getSelectedSpeed());
			performanceChartSheet.setSpeed(selectedFan.getSpeed().toString());
			if(tbl14PrimemoverMaster!=null)
			{
				performanceChartSheet.setFrequency(tbl14PrimemoverMaster.getFrequency().toString());
				performanceChartSheet.setMotorModel(tbl14PrimemoverMaster.getModelName());
			}

			List<PerformanceChartSheet> sheetList = new ArrayList<PerformanceChartSheet>();
			sheetList.add(performanceChartSheet);
			String reportPath = velotechUtil.getReport(sheetList, "performanceChartSheet.jasper",
					"Performance Graph.pdf", new HashMap<String, Object>());
			String xReportPath = reportPath.replace('\\', '/');
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, xReportPath);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private void makeOfferFan(CentrifugalFanSelectionModel operatingConditions, SelectedCentrifugalFan selectedPump,
			AddToOfferDto addToOfferDto) {

		try {

			JSONObject jo = new JSONObject();
			JSONObject finalData = new JSONObject();

			velotechUtil.mergeJson(jo, new JSONObject(selectedPump.getSelectedFanJson()));
			jo.put("selectionModel", new JSONObject(velotechUtil.gson().toJson(operatingConditions)));

			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class,
					addToOfferDto.getOfferRevId());
			Tbl26OfferFan tbl26OfferFan = saveOfferFan(selectedPump, addToOfferDto, tbl23OfferRev);

			AdditionalCentrifugalFanAttributes additionalFanAttributes = addToOfferDto
					.getAdditionalCentrifugalFanAttributes();

			Integer barepumpGaId = 0;
			Integer csdId = 0;
			Integer pumpSetGaId = 0;

			Tbl14PrimemoverMaster primemoverMaster = dao.getPrimeMoverData(selectedPump.getPrimemoverId());

			Tbl01CentrifugalModelMaster tbl02Modelmaster = (Tbl01CentrifugalModelMaster) genericDao
					.getUniqueRecord(Tbl01CentrifugalModelMaster.class, "fanModel", selectedPump.getFanModel());
			List<String> productUuid = new ArrayList<>();
			productUuid.add(tbl02Modelmaster.getTbl01fantype().getUuid());
			if (primemoverMaster != null) {
				productUuid.add(primemoverMaster.getUuid());
				if (primemoverMaster.getTbl14FrameMaster() != null)
					productUuid.add(primemoverMaster.getTbl14FrameMaster().getUuid());
				if (primemoverMaster.getTbl1401Motortype() != null)
					productUuid.add(primemoverMaster.getTbl1401Motortype().getUuid());
			}
			jo = jsonService.setJsonParameter(jo, productUuid, "data", "");

			saveRequirementsDp(operatingConditions, selectedPump, tbl26OfferFan, jo);

			saveSelectedPrimemover(selectedPump, tbl26OfferFan, operatingConditions.getDp_totalQty(), jo);

			// saveSelectedFanBom(selectedPump, addToOfferDto, tbl26OfferFan, 0, jo);

			Tbl28SelectedFan tbl28SelectedFan = saveSelectedFan(selectedPump, addToOfferDto, tbl26OfferFan, 0,
					additionalFanAttributes, barepumpGaId, csdId, pumpSetGaId, jo);

			Tbl28SelectedPricing tbl28SelectedPricing = saveSelectedPricing(operatingConditions, selectedPump,
					addToOfferDto, tbl26OfferFan, additionalFanAttributes);

			saveSelectedPricingDetails(tbl28SelectedPricing, selectedPump, tbl28SelectedFan);

			jo = getJsonFilterSeriesMaster(jo, selectedPump);
			System.out.println("MeargeObject-1:-" + jo.toString());
			// getJsonFilterMaster(jo, selectedPump);
			System.out.println("MeargeObject-2:-" + jo.toString());

			jo = jsonService.setJsonParameter(jo, selectedPump.getFanTypeUuid(), "data", "");
			System.out.println("FinalJsonData:-" + jo.toString());

			jo = setUserInput(addToOfferDto, jo);

			saveTbl28SelectedFanJsonValue(tbl26OfferFan, jo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private JSONObject setUserInput(AddToOfferDto addToOfferDto, JSONObject jo) {

		try {
			JSONObject jsonObject1 = new JSONObject();
			List<UserInputDto> ans = addToOfferDto.getUserInputDto();
			if (ans != null)
				for (UserInputDto userInputDto : ans) {
					jsonObject1.put(userInputDto.getParameter(), userInputDto.getValue());
				}

			jo.put("userInput", jsonObject1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo;

	}

	public JSONObject getJsonFilterSeriesMaster(JSONObject jo, SelectedCentrifugalFan selectedPump) {

		try {
			JSONObject data = new JSONObject();

			/*
			 * data.put("barepumpGaTemplate",
			 * jsonService.getJsonFilterData(selectedPump.getPumpModelUuid(),
			 * selectedPump.getPumpTypeUuid(), "barepumpGaTemplate", jo));
			 */
			/*
			 * data.put("pumpCSDLayers",
			 * jsonService.getJsonFilterData(selectedPump.getPumpModelUuid(),
			 * selectedPump.getPumpTypeUuid(), "pumpCSDLayers", jo));
			 * data.put("pumpsetGaTemplate",
			 * jsonService.getJsonFilterData(selectedPump.getPumpModelUuid(),
			 * selectedPump.getPumpTypeUuid(), "pumpsetGaTemplate", jo));
			 * data.put("pumpSetGaLayers",
			 * jsonService.getJsonFilterData(selectedPump.getPumpModelUuid(),
			 * selectedPump.getPumpTypeUuid(), "pumpSetGaLayers", jo));
			 * data.put("pumpPIDTemplate",
			 * jsonService.getJsonFilterData(selectedPump.getPumpModelUuid(),
			 * selectedPump.getPumpTypeUuid(), "pumpPIDTemplate", jo));
			 */

			jo = velotechUtil.mergeJsonNew(jo, data, "data");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return jo;

	}

	private void saveTbl28SelectedFanJsonValue(Tbl26OfferFan tbl26OfferFan, JSONObject jo) {

		try {

			Tbl28SelectedFanJsonValue tbl28SelectedFanJsonValue = new Tbl28SelectedFanJsonValue();
			tbl28SelectedFanJsonValue.setTbl26OfferFan(tbl26OfferFan);
			tbl28SelectedFanJsonValue.setJsonText(jo.toString());
			genericDao.save(tbl28SelectedFanJsonValue);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private Tbl26OfferFan saveOfferFan(SelectedCentrifugalFan selectedPump, AddToOfferDto addToOfferDto,
			Tbl23OfferRev tbl23OfferRev) {

		Tbl26OfferFan tbl26OfferFan = new Tbl26OfferFan();
		try {
			tbl26OfferFan.setTbl23OfferRev(tbl23OfferRev);
			tbl26OfferFan.setTagNo(addToOfferDto.getTagNo());// tagNo
			tbl26OfferFan.setStatus("Decision Pending");
			tbl26OfferFan.setFanSelectionType("SOFTWARE");
			tbl26OfferFan.setVerticalLogic(false);
			tbl26OfferFan.setLineTotal(0d);
			tbl26OfferFan.setAddonTotal(0d);
			tbl26OfferFan.setTotal(0d);
			genericDao.save(tbl26OfferFan);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl26OfferFan;
	}

	private List<Tbl28SelectedFanVariant> saveSelectedFanVariants(AddToOfferDto addToOfferDto,
			Tbl26OfferFan tbl26OfferFan, JSONObject jo) {

		List<Tbl28SelectedFanVariant> tbl28SelectedFanVariants = new ArrayList<>();
		try {
			JSONObject jsonObject1 = new JSONObject();
			List<Tbl01VariantLine> tbl01VariantLines = (List<Tbl01VariantLine>) genericDao.getRecordsByParentId(
					Tbl01VariantLine.class, "tbl01VariantMaster.id",
					addToOfferDto.getAdditionalCentrifugalFanAttributes().getVariantId());
			for (Tbl01VariantLine tbl01VariantLine : tbl01VariantLines) {
				Tbl28SelectedFanVariant tbl28SelectedFanVariant = new Tbl28SelectedFanVariant();
				tbl28SelectedFanVariant.setTbl26OfferFan(tbl26OfferFan);
				tbl28SelectedFanVariant.setVariant(tbl01VariantLine.getParameter());
				tbl28SelectedFanVariant.setValue(tbl01VariantLine.getValue());
				genericDao.save(tbl28SelectedFanVariant);
				tbl28SelectedFanVariants.add(tbl28SelectedFanVariant);
				jsonObject1.put(tbl28SelectedFanVariant.getVariant(), tbl28SelectedFanVariant.getValue());
			}
			jo.put("tbl28SelectedFanVariant", jsonObject1);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl28SelectedFanVariants;
	}

	private void saveRequirementsDp(CentrifugalFanSelectionModel operatingConditions,
			SelectedCentrifugalFan selectedFan, Tbl26OfferFan tbl26OfferFan, JSONObject jo) {

		try {
			Tbl27RequirementsDp tbl27RequirementsDp = createRequirementsDp(operatingConditions, selectedFan,
					tbl26OfferFan);
			genericDao.save(tbl27RequirementsDp);
			jo.put("tbl27RequirementsDp", new JSONObject(velotechUtil.gsonWithExpose().toJson(tbl27RequirementsDp)));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private Tbl27RequirementsDp createRequirementsDp(CentrifugalFanSelectionModel operatingConditions,
			SelectedCentrifugalFan selectedFan, Tbl26OfferFan tbl26OfferFan) {

		Tbl27RequirementsDp tbl27RequirementsDp = new Tbl27RequirementsDp();

		try {
			boolean effModImpeller = false, effModMoc = false, effModStages = false, effModCoating = false;

			tbl27RequirementsDp.setTbl26OfferFan(tbl26OfferFan);
			tbl27RequirementsDp.setFlow(Double.valueOf(operatingConditions.getDp_flow()));
			tbl27RequirementsDp.setHead(Double.valueOf(operatingConditions.getDp_pressure()));
			// tbl27RequirementsDp.setPole(tbl26OfferFan.getTbl28SelectedPrimemover().getPole().toString());

			Double flowConverted = velotechUtil.convertFlow(operatingConditions.getDp_flow(),
					operatingConditions.getDp_uom_flow(), "m3/hr");
			tbl27RequirementsDp.setFlowConverted(flowConverted);

			Double headConverted = velotechUtil.convertPressure(operatingConditions.getDp_pressure(),
					operatingConditions.getDp_uom_pressure(), "MMWG");
			tbl27RequirementsDp.setHeadConverted(headConverted);

			tbl27RequirementsDp.setQty(operatingConditions.getDp_totalQty());

			tbl27RequirementsDp.setUomFlow(operatingConditions.getDp_uom_flow());
			tbl27RequirementsDp.setUomHead(operatingConditions.getDp_uom_pressure());
			tbl27RequirementsDp.setUomPower(operatingConditions.getDp_uom_power());
			tbl27RequirementsDp.setSeries(selectedFan.getSeries());
			tbl27RequirementsDp.setFrequency(operatingConditions.getDp_frequency().toString());
			// tbl27RequirementsDp.setMinSoh(operatingConditions.getDp_min_soh());
			// tbl27RequirementsDp.setMaxSoh(operatingConditions.getDp_max_soh());
			// tbl27RequirementsDp.setMaxFlow(operatingConditions.getDp_max_flow());
			// tbl27RequirementsDp.setMinHead(operatingConditions.getDp_min_head());
			// tbl27RequirementsDp.setAddEff(operatingConditions.getDp_AddEff());
			tbl27RequirementsDp.setEffModImpeller(effModImpeller);
			tbl27RequirementsDp.setEffModMoc(effModMoc);
			tbl27RequirementsDp.setEffModStages(effModStages);
			tbl27RequirementsDp.setEffModCoating(effModCoating);

			// tbl27RequirementsDp.setDriverSizing(operatingConditions.getDp_driver_sizing());
			// tbl27RequirementsDp.setServiceFactor(operatingConditions.getDp_serviceFactor());
			// tbl27RequirementsDp.setTypeOfDrive(operatingConditions.getTypeOfDriver());
			// tbl27RequirementsDp.setConstant(operatingConditions.getConstant());
			// tbl27RequirementsDp.setFlowMissToleranceMin(operatingConditions.getFlowMissToleranceMin());
			tbl27RequirementsDp.setHeadMissToleranceMin(operatingConditions.getPressureMissToleranceMin());
			tbl27RequirementsDp.setDp_searchCriteria(operatingConditions.getDp_searchCriteria());
			// tbl27RequirementsDp.setSpeed(tbl26OfferFan.getTbl28SelectedFan().getSpeed());
			// tbl27RequirementsDp.setPhase(operatingConditions.getPhase());

			// tbl27RequirementsDp.setEquivalentMotorSeriesId(operatingConditions.getEquivalentMotorSeriesId());
			tbl27RequirementsDp.setTypeOfDrive(operatingConditions.getTypeOfDriver());
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl27RequirementsDp;
	}

	private JSONObject createRequirementsDpJson(CentrifugalFanSelectionModel operatingConditions,
			SelectedCentrifugalFan selectedPump, Tbl26OfferFan tbl26OfferFan) {

		JSONObject jo = new JSONObject();

		try {
			Tbl27RequirementsDp tbl27RequirementsDp = createRequirementsDp(operatingConditions, selectedPump,
					tbl26OfferFan);
			jo = new JSONObject(velotechUtil.gsonWithExpose().toJson(tbl27RequirementsDp));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return jo;
	}

	private void saveSelectedPrimemover(SelectedCentrifugalFan selectedPump, Tbl26OfferFan tbl26OfferPump,
			Integer totalQty, JSONObject jo) {

		try {
			Tbl14PrimemoverMaster tbl14PrimemoverMaster = dao.getPrimeMoverData(selectedPump.getPrimemoverId());
			if (tbl14PrimemoverMaster != null) {
				Tbl28SelectedPrimemover tbl28SelectedPrimemover = new Tbl28SelectedPrimemover();
				tbl28SelectedPrimemover.setTbl26OfferFan(tbl26OfferPump);
				// tbl28SelectedPrimemover.setTbl40OfferDocument(null);
				tbl28SelectedPrimemover.setEff(tbl14PrimemoverMaster.getEff());
				tbl28SelectedPrimemover.setPole(tbl14PrimemoverMaster.getPole());
				tbl28SelectedPrimemover.setSeries(tbl14PrimemoverMaster.getSeries());
				tbl28SelectedPrimemover.setMoc(tbl14PrimemoverMaster.getMoc());
				tbl28SelectedPrimemover.setPower(tbl14PrimemoverMaster.getPower());
				tbl28SelectedPrimemover.setSpeed(tbl14PrimemoverMaster.getSpeed());
				tbl28SelectedPrimemover.setPrimemoverId(tbl14PrimemoverMaster.getPrimemoverId());
				tbl28SelectedPrimemover.setModelName(tbl14PrimemoverMaster.getModelName());
				genericDao.save(tbl28SelectedPrimemover);
				jo.put("tbl28SelectedPrimemover",
						new JSONObject(velotechUtil.gsonWithExpose().toJson(tbl28SelectedPrimemover)));
				DecimalFormat autoDForm = new DecimalFormat("");
				String descriptionPrimemover = tbl14PrimemoverMaster.getPrimemoverType() + " "
						+ autoDForm.format(tbl14PrimemoverMaster.getPowerHp()) + " HP, " + "/"
						+ autoDForm.format(tbl14PrimemoverMaster.getPower()) + " kW, "
						+ autoDForm.format(tbl14PrimemoverMaster.getSpeed());

				double subtotal = totalQty * tbl14PrimemoverMaster.getPrice().doubleValue();

				List<Tbl28SelectedpricingDetails> tbl28SelectedpricingDetailses = new ArrayList<>();

				Tbl28SelectedpricingDetails primeMoverTypePricingDetails = new Tbl28SelectedpricingDetails();
				primeMoverTypePricingDetails.setParameter("PrimeMover Type");
				primeMoverTypePricingDetails.setValue(tbl14PrimemoverMaster.getPrimemoverType());
				primeMoverTypePricingDetails.setShowInSummary(true);

				Tbl28SelectedpricingDetails manufacturerPricingDetails = new Tbl28SelectedpricingDetails();
				manufacturerPricingDetails.setParameter("Manufacturer");
				manufacturerPricingDetails.setValue(tbl14PrimemoverMaster.getManufacturer());
				manufacturerPricingDetails.setShowInSummary(true);

				Tbl28SelectedpricingDetails powerPricingDetails = new Tbl28SelectedpricingDetails();
				powerPricingDetails.setParameter("Power");
				powerPricingDetails.setValue(autoDForm.format(tbl14PrimemoverMaster.getPower()) + " kW" + " / "
						+ autoDForm.format(tbl14PrimemoverMaster.getPowerHp()) + " HP");
				powerPricingDetails.setShowInSummary(true);

				Tbl28SelectedpricingDetails speedPricingDetails = new Tbl28SelectedpricingDetails();
				speedPricingDetails.setParameter("Speed");
				speedPricingDetails.setValue(autoDForm.format(tbl14PrimemoverMaster.getSpeed()) + " RPM");
				speedPricingDetails.setShowInSummary(true);

				Tbl28SelectedpricingDetails frequencyPricingDetails = new Tbl28SelectedpricingDetails();
				frequencyPricingDetails.setParameter("Frequency (Hz)");
				frequencyPricingDetails.setValue(tbl14PrimemoverMaster.getFrequency().toString());
				frequencyPricingDetails.setShowInSummary(true);

				Tbl28SelectedpricingDetails effClassPricingDetails = new Tbl28SelectedpricingDetails();
				effClassPricingDetails.setParameter("Eff Class");
				effClassPricingDetails.setValue(tbl14PrimemoverMaster.getEffClass());
				effClassPricingDetails.setShowInSummary(true);

				Tbl28SelectedpricingDetails polePricingDetails = new Tbl28SelectedpricingDetails();
				polePricingDetails.setParameter("Pole");
				polePricingDetails.setValue(tbl14PrimemoverMaster.getPole().toString());
				polePricingDetails.setShowInSummary(true);

				Tbl28SelectedpricingDetails mountingPricingDetails = new Tbl28SelectedpricingDetails();
				mountingPricingDetails.setParameter("Mounting");
				mountingPricingDetails.setValue(tbl14PrimemoverMaster.getMounting());
				mountingPricingDetails.setShowInSummary(true);

				Tbl28SelectedpricingDetails specificationPricingDetails = new Tbl28SelectedpricingDetails();
				specificationPricingDetails.setParameter("Specification");
				specificationPricingDetails.setValue(tbl14PrimemoverMaster.getSpecification());
				specificationPricingDetails.setShowInSummary(true);

				tbl28SelectedpricingDetailses.add(primeMoverTypePricingDetails);
				tbl28SelectedpricingDetailses.add(manufacturerPricingDetails);
				tbl28SelectedpricingDetailses.add(powerPricingDetails);
				tbl28SelectedpricingDetailses.add(speedPricingDetails);
				tbl28SelectedpricingDetailses.add(frequencyPricingDetails);
				tbl28SelectedpricingDetailses.add(effClassPricingDetails);
				tbl28SelectedpricingDetailses.add(polePricingDetails);
				tbl28SelectedpricingDetailses.add(mountingPricingDetails);
				tbl28SelectedpricingDetailses.add(specificationPricingDetails);

				if (!velotechUtil.getCompany().equals("MBH01")) {
					Tbl28SelectedpricingDetails mocPricingDetails = new Tbl28SelectedpricingDetails();
					mocPricingDetails.setParameter("MOC");
					mocPricingDetails.setValue(tbl14PrimemoverMaster.getMoc());
					mocPricingDetails.setShowInSummary(true);
					tbl28SelectedpricingDetailses.add(mocPricingDetails);
				}

				Tbl28SelectedPricing tbl28SelectedPricing = new Tbl28SelectedPricing();
				tbl28SelectedPricing.setTbl26OfferFan(tbl26OfferPump);
				tbl28SelectedPricing.setTbl01ProductMaster(tbl14PrimemoverMaster.getTbl01ProductMaster());
				// tbl28SelectedPricing.setSorting(orderNo);
				tbl28SelectedPricing.setItemName("PrimeMover");
				tbl28SelectedPricing.setDescription(descriptionPrimemover);
				tbl28SelectedPricing.setQty(totalQty);
				tbl28SelectedPricing.setSource("BoughtOut");
				tbl28SelectedPricing.setDiscount(0d);
				tbl28SelectedPricing.setMargin(0d);
				tbl28SelectedPricing.setArticleNo(tbl14PrimemoverMaster.getArticleNo());
				tbl28SelectedPricing.setPricePerQty(tbl14PrimemoverMaster.getPrice().doubleValue());
				tbl28SelectedPricing.setSubTotal(subtotal);
				tbl28SelectedPricing.setScope("No");
				tbl28SelectedPricing.setGroupClass("ACCESSORIES");
				tbl28SelectedPricing.setApplyFactor(true);
				tbl28SelectedPricing.setTotalOfferFactor(1.0);
				tbl28SelectedPricing.setMake(tbl14PrimemoverMaster.getManufacturer());
				genericDao.save(tbl28SelectedPricing);

				saveSelectedpricingDetails(tbl28SelectedpricingDetailses);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
	}

	private Tbl28SelectedFan saveSelectedFan(SelectedCentrifugalFan selectedFan, AddToOfferDto addToOfferDto,
			Tbl26OfferFan tbl26OfferFan, Integer variantId, AdditionalCentrifugalFanAttributes additionalFanAttributes,
			Integer barefanGaId, Integer csdId, Integer fanSetGaId, JSONObject jo) {

		Tbl28SelectedFan tbl28SelectedFan = new Tbl28SelectedFan();
		try {
			tbl28SelectedFan.setTbl26OfferFan(tbl26OfferFan);
			tbl28SelectedFan.setFanModel(selectedFan.getFanModel());
			tbl28SelectedFan.setDpEff(selectedFan.getDpEff());
			tbl28SelectedFan.setDpFlow(selectedFan.getDpFlow());
			tbl28SelectedFan.setDpPower(selectedFan.getDpPower());
			tbl28SelectedFan.setDpPressure(selectedFan.getDpPressure());
			tbl28SelectedFan.setMotorTypeUuid(selectedFan.getMotorTypeUuid());
			tbl28SelectedFan.setSelectedFanJson(selectedFan.getSelectedFanJson());
			tbl28SelectedFan.setDisplayFanModelName(selectedFan.getDisplayFanModelName());
			tbl28SelectedFan.setFanModelId(selectedFan.getFanModelId());
			tbl28SelectedFan.setMocStd(selectedFan.getMocStd());
			tbl28SelectedFan.setRotation(selectedFan.getRotation());
			tbl28SelectedFan.setPerformanceCurveNo(selectedFan.getPerformanceCurveNo());
			tbl28SelectedFan.setSeries(selectedFan.getSeries());
			tbl28SelectedFan.setSpeed(selectedFan.getSpeed());
			tbl28SelectedFan.setBarefanGaId(selectedFan.getBarefanGaId());
			tbl28SelectedFan.setCompany(selectedFan.getCompany());
			tbl28SelectedFan.setCreatedBy(selectedFan.getCreatedBy());
			tbl28SelectedFan.setCreatedDate(selectedFan.getCreatedDate());
			tbl28SelectedFan.setBarefanGaId(barefanGaId);
			tbl28SelectedFan.setFanSetArticleNo("");
			tbl28SelectedFan.setCsdId(csdId);
			tbl28SelectedFan.setFansetGaId(fanSetGaId);
			tbl28SelectedFan.setMocStd(selectedFan.getMocStd());
			tbl28SelectedFan.setModifiedBy(selectedFan.getModifiedBy());
			tbl28SelectedFan.setModifiedDate(selectedFan.getModifiedDate());
			tbl28SelectedFan.setPrimeMoverUuid(selectedFan.getPrimeMoverUuid());
			tbl28SelectedFan.setPumpModelUuid(selectedFan.getFanModelUuid());
			tbl28SelectedFan.setPumpTypeUuid(selectedFan.getFanTypeUuid());
			tbl28SelectedFan.setVariantId(variantId);
			tbl28SelectedFan.setVaneAngle(selectedFan.getSelectedSpeed());

			genericDao.save(tbl28SelectedFan);
			jo.put("tbl28SelectedFan", new JSONObject(velotechUtil.gsonWithExpose().toJson(tbl28SelectedFan)));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl28SelectedFan;
	}

	private Tbl28SelectedPricing saveSelectedPricing(CentrifugalFanSelectionModel operatingConditions,
			SelectedCentrifugalFan selectedFan, AddToOfferDto addToOfferDto, Tbl26OfferFan tbl26OfferFan,
			AdditionalCentrifugalFanAttributes additionalFanAttributes) {

		Tbl28SelectedPricing tbl28SelectedPricing = new Tbl28SelectedPricing();
		try {

			double pumpPrice = 0d;
			double pumpsubTotal = pumpPrice * operatingConditions.getDp_totalQty();
			tbl28SelectedPricing.setTbl26OfferFan(tbl26OfferFan);
			// tbl28SelectedPricing.setTbl01ProductMaster(new
			// Tbl01ProductMaster(selectedPump.getFanModelId()));
			// tbl28SelectedPricing.setSorting(orderNo);
			tbl28SelectedPricing.setItemName("Fan");
			tbl28SelectedPricing.setDescription(selectedFan.getFanModel());
			tbl28SelectedPricing.setQty(operatingConditions.getDp_totalQty());
			tbl28SelectedPricing.setSource("InHouse");
			tbl28SelectedPricing.setDiscount(0d);
			tbl28SelectedPricing.setMargin(0d);
			tbl28SelectedPricing.setArticleNo("");
			tbl28SelectedPricing.setPricePerQty(pumpPrice);
			tbl28SelectedPricing.setSubTotal(pumpsubTotal);
			tbl28SelectedPricing.setScope("Yes");
			tbl28SelectedPricing.setGroupClass("FanSet");
			tbl28SelectedPricing.setApplyFactor(true);
			tbl28SelectedPricing.setTotalOfferFactor(1.0);
			genericDao.save(tbl28SelectedPricing);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl28SelectedPricing;
	}

	private void saveSelectedPricingDetails(Tbl28SelectedPricing tbl28SelectedPricing,
			SelectedCentrifugalFan selectedPump, Tbl28SelectedFan tbl28SelectedFan) {

		Tbl28SelectedpricingDetails modelPricingDetails = new Tbl28SelectedpricingDetails();
		try {
			modelPricingDetails.setTbl28SelectedPricing(tbl28SelectedPricing);
			modelPricingDetails.setParameter("Model");
			modelPricingDetails.setValue(selectedPump.getFanModel());
			modelPricingDetails.setShowInSummary(true);

			Tbl28SelectedpricingDetails sizePricingDetails = new Tbl28SelectedpricingDetails();
			sizePricingDetails.setTbl28SelectedPricing(tbl28SelectedPricing);
			sizePricingDetails.setParameter("Size");
			sizePricingDetails.setShowInSummary(true);

			Tbl28SelectedpricingDetails stagePricingDetails = new Tbl28SelectedpricingDetails();
			stagePricingDetails.setTbl28SelectedPricing(tbl28SelectedPricing);
			stagePricingDetails.setParameter("Stage");
			stagePricingDetails.setShowInSummary(true);

			Tbl28SelectedpricingDetails suctionOrientationPricingDetails = new Tbl28SelectedpricingDetails();
			suctionOrientationPricingDetails.setTbl28SelectedPricing(tbl28SelectedPricing);
			suctionOrientationPricingDetails.setParameter("Suction Orientation");
			suctionOrientationPricingDetails.setShowInSummary(true);

			Tbl28SelectedpricingDetails dischargeOrientationPricingDetails = new Tbl28SelectedpricingDetails();
			dischargeOrientationPricingDetails.setTbl28SelectedPricing(tbl28SelectedPricing);
			dischargeOrientationPricingDetails.setParameter("Discharge Orientation");
			dischargeOrientationPricingDetails.setShowInSummary(true);

			Tbl28SelectedpricingDetails flangeStandardPricingDetails = new Tbl28SelectedpricingDetails();
			flangeStandardPricingDetails.setTbl28SelectedPricing(tbl28SelectedPricing);
			flangeStandardPricingDetails.setParameter("Flange Standard");
			flangeStandardPricingDetails.setShowInSummary(true);

			List<Tbl28SelectedpricingDetails> tbl28SelectedpricingDetailses = new ArrayList<>();
			tbl28SelectedpricingDetailses.add(modelPricingDetails);
			tbl28SelectedpricingDetailses.add(sizePricingDetails);
			tbl28SelectedpricingDetailses.add(stagePricingDetails);
			tbl28SelectedpricingDetailses.add(suctionOrientationPricingDetails);
			tbl28SelectedpricingDetailses.add(dischargeOrientationPricingDetails);
			tbl28SelectedpricingDetailses.add(flangeStandardPricingDetails);

			Tbl28SelectedpricingDetails mocPricingDetails = new Tbl28SelectedpricingDetails();
			mocPricingDetails.setTbl28SelectedPricing(tbl28SelectedPricing);
			mocPricingDetails.setParameter("MOC STD");
			mocPricingDetails.setValue(tbl28SelectedFan.getMocStd());
			mocPricingDetails.setShowInSummary(true);
			tbl28SelectedpricingDetailses.add(mocPricingDetails);

			Tbl28SelectedpricingDetails feetLocationPricingDetails = new Tbl28SelectedpricingDetails();
			feetLocationPricingDetails.setTbl28SelectedPricing(tbl28SelectedPricing);
			feetLocationPricingDetails.setParameter("Feet Location");
			feetLocationPricingDetails.setShowInSummary(true);
			tbl28SelectedpricingDetailses.add(feetLocationPricingDetails);

			saveSelectedpricingDetails(tbl28SelectedpricingDetailses);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void saveSelectedpricingDetails(List<Tbl28SelectedpricingDetails> tbl28SelectedpricingDetailses) {

		try {
			for (Tbl28SelectedpricingDetails tbl28SelectedpricingDetails : tbl28SelectedpricingDetailses) {
				genericDao.save(tbl28SelectedpricingDetails);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	private Tbl26OfferFan createOfferFanPerformanceGraph(CentrifugalFanSelectionModel selectionModel,
			SelectedCentrifugalFan selectedPump) {

		Tbl26OfferFan ans = new Tbl26OfferFan();
		try {

			Tbl28SelectedFan tbl28SelectedFan = new Tbl28SelectedFan();
			Tbl28SelectedPrimemover tbl28SelectedPrimemover = new Tbl28SelectedPrimemover();
			tbl28SelectedFan.setFanModel(selectedPump.getFanModel());
			tbl28SelectedFan.setDisplayFanModelName(selectedPump.getFanModel());

			Tbl27RequirementsDp tbl27RequirementsDp = createRequirementsDp(selectionModel, selectedPump,
					new Tbl26OfferFan());

			/*
			 * Tbl27RequirementsDp tbl27RequirementsDp = new Tbl27RequirementsDp();
			 * tbl27RequirementsDp.setUomFlow(selectionModel.getDp_uom_flow());
			 * tbl27RequirementsDp.setUomHead(selectionModel.getDp_uom_head());
			 * tbl27RequirementsDp.setFlow(Double.valueOf(selectedPump.getDpFlow ()));
			 * tbl27RequirementsDp.setHead(Double.valueOf(selectedPump.getDpHead ()));
			 * tbl27RequirementsDp.setQty(selectionModel.getDp_totalQty());
			 * tbl27RequirementsDp.setFrequency(selectionModel.getDp_frequency() );
			 * tbl27RequirementsDp.setUomPower(selectionModel.getDp_uom_power()) ;
			 */

			tbl28SelectedFan.setDpFlow(selectedPump.getDpFlow());
			tbl28SelectedFan.setDpPressure(selectedPump.getDpPressure());
			tbl28SelectedFan.setDpEff(selectedPump.getDpEff());
			tbl28SelectedFan.setDpPower(selectedPump.getDpPower());
			tbl28SelectedFan.setFanModel(selectedPump.getFanModel());
			tbl28SelectedFan.setSelectedFanJson(selectedPump.getSelectedFanJson());
			tbl28SelectedFan.setSeries(selectedPump.getSeries());
			tbl28SelectedFan.setSpeed(selectedPump.getSpeed());
			tbl28SelectedFan.setVaneAngle(selectedPump.getSelectedSpeed());

			Tbl14PrimemoverMaster tbl14PrimemoverMaster = null;
			
			if(selectedPump.getPrimemoverId() != null) {
				 tbl14PrimemoverMaster = (Tbl14PrimemoverMaster) genericDao
						.getRecordById(Tbl14PrimemoverMaster.class, selectedPump.getPrimemoverId());
			}
			
			if (tbl14PrimemoverMaster != null) {
				tbl28SelectedPrimemover.setModelName(tbl14PrimemoverMaster.getModelName());
			}
			

			ans.setTbl27RequirementsDp(tbl27RequirementsDp);
			ans.setTbl28SelectedFan(tbl28SelectedFan);
			ans.setTbl28SelectedPrimemover(tbl28SelectedPrimemover);
			ans.setTbl23OfferRev(new Tbl23OfferRev());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public ApplicationResponse isUniqueTagNumber(String tagNo, Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		boolean ans = false;
		try {
			ans = dao.isUniqueTagNumber(tagNo, offerRevId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(ans,
				ApplicationConstants.DATA_LOAD_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getJsonParameterDataUserInput(Integer selectedFanId) {
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			applicationResponse = offerDetailsService.getJsonParameterDataUserInput(selectedFanId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;

	}

	public ApplicationResponse quickDocument(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		String path = "false";
		try {
			Random randomGenerator = new Random();
			int var = randomGenerator.nextInt(999999);
			AddToOfferDto addToOfferDto = new AddToOfferDto();
			ObjectMapper mapper = new ObjectMapper();
			addToOfferDto = mapper.readValue(requestPayload, AddToOfferDto.class);

			Tbl95TxSelectedPumps tbl95TxSelectedPump = (Tbl95TxSelectedPumps) genericDao
					.getRecordById(Tbl95TxSelectedPumps.class, addToOfferDto.getSelectedFanId());
			Tbl95TxPumpSelectionModel tbl95TxPumpSelectionModel = (Tbl95TxPumpSelectionModel) genericDao
					.getRecordById(Tbl95TxPumpSelectionModel.class, tbl95TxSelectedPump.getPumpSelectionModelId());

			Gson gson = new Gson();
			CentrifugalFanSelectionModel selectionModel = gson
					.fromJson(tbl95TxPumpSelectionModel.getPumpSelectionModel(), CentrifugalFanSelectionModel.class);
			SelectedCentrifugalFan selectedFan = gson.fromJson(tbl95TxSelectedPump.getSelectedPump(),
					SelectedCentrifugalFan.class);
			// selectedFan.setMoc(addToOfferDto.getMoc());
			JSONObject jo = new JSONObject();
			velotechUtil.mergeJson(jo, new JSONObject(selectedFan.getSelectedFanJson()));
			jo.put("selectionModel", new JSONObject(velotechUtil.gson().toJson(selectionModel)));

			Tbl26OfferFan tbl26OfferFan = createOfferFanPerformanceGraph(selectionModel, selectedFan);
//			tbl26OfferFan.getTbl28SelectedFan().setStartingMethod(addToOfferDto.getAdditionalPumpAttributes().getStartingMethod());
			Tbl14PrimemoverMaster tbl14PrimemoverMaster = null;
			if (selectedFan.getPrimemoverId() != null) {
			tbl14PrimemoverMaster = (Tbl14PrimemoverMaster) genericDao
						.getRecordById(Tbl14PrimemoverMaster.class, selectedFan.getPrimemoverId());
			}
			

			PerformanceChartCondition performanceChartCondition = new PerformanceChartCondition();

			performanceChartCondition.configureCondition(tbl26OfferFan);
			performanceChartCondition.setShowMinMaxDia(false);
			// JFreeChart chart =
			// velotechUtil.getPerformanceChartImage(performanceChartCondition);
			JFreeChart chart = velotechUtil
					.getPerformanceChartImage(new JSONObject(selectedFan.getSelectedFanJson().toString()));

			Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster = (Tbl01CentrifugalModelMaster) genericDao
					.getUniqueRecord(Tbl01CentrifugalModelMaster.class, "fanModel", selectedFan.getFanModel());

			TechnicalDataSheet technicalDataSheet = new TechnicalDataSheet(tbl26OfferFan, null, tbl14PrimemoverMaster,
					tbl01CentrifugalModelMaster);

			SoundUtil soundUtil = new SoundUtil();
			JSONObject soundData = soundUtil.calculateSound(selectedFan.getDpFlow(), selectionModel.getDp_uom_flow(),
					selectedFan.getDpPressure(), selectionModel.getDp_uom_pressure(), selectedFan.getSpeed(),
					tbl01CentrifugalModelMaster.getBlades());

			// Access the data
			org.json.JSONArray soundArray = soundData.getJSONArray("soundDataArray");
			Double lpOverallInside = soundData.getDouble("lpOverallInside");
			Double lpOverallOutside = soundData.getDouble("lpOverallOutside");
			jo.put("soundData", soundData);

			Tbl28CompanyMaster tbl28CompanyMaster = (Tbl28CompanyMaster) genericDao
					.getRecordById(Tbl28CompanyMaster.class, velotechUtil.getCompanyId());

			technicalDataSheet.setLogoPath(velotechUtil.getCompanyDocumentPath(tbl28CompanyMaster.getLogo()));
			technicalDataSheet.setCompanyName(tbl28CompanyMaster.getDocumentCompanyName());
			technicalDataSheet.setCompanyAddress(tbl28CompanyMaster.getDocumentComapnyAddress());
			technicalDataSheet.setPreparedBy(velotechUtil.getUsername());

			List<String> productUuid = new ArrayList<>();
			productUuid.add(tbl01CentrifugalModelMaster.getTbl01fantype().getUuid());
			if (tbl14PrimemoverMaster != null) {
				productUuid.add(tbl14PrimemoverMaster.getUuid());
				productUuid.add(tbl14PrimemoverMaster.getTbl14FrameMaster().getUuid());
				productUuid.add(tbl14PrimemoverMaster.getTbl1401Motortype().getUuid());

				// jo.put("tbl14PrimemoverMaster", new
				// JSONObject(velotechUtil.gsonWithExpose().toJson(tbl14PrimemoverMaster)));
			}
			jo = jsonService.setJsonParameter(jo, productUuid, "data", "");

			// Integer variantId =
			// addToOfferDto.getAdditionalPumpAttributes().getVariantId();
			// selectedFan.setVariantId(variantId);

			// List<Tbl28SelectedFanBom> tbl28SelectedPumpBoms =
			// setSelectedPumpBom(selectedFan, addToOfferDto,tbl26OfferFan, variantId, jo);
			// technicalDataSheet.setModelMocDataSheets(getSelectedFanBom(tbl28SelectedPumpBoms));
			jo.put("tbl27RequirementsDp", createRequirementsDpJson(selectionModel, selectedFan, new Tbl26OfferFan()));

//			List<Tbl28SelectedPumpBom> tbl28SelectedPumpBomsMotor = setSelectedMotorBom(
//					selectedPump.getMotorSeriesId());
//			technicalDataSheet.setMotorMocDataSheets(getSelectedPumpBom(tbl28SelectedPumpBomsMotor));
            if(tbl14PrimemoverMaster != null) {
            	Tbl1401Motortype tbl1401Motortype = (Tbl1401Motortype) genericDao.getRecordById(Tbl1401Motortype.class,
    					tbl14PrimemoverMaster.getTbl1401Motortype().getId());
    			if (tbl1401Motortype != null) {
    				Tbl1401Motortype newtbl1401Motortype = new Tbl1401Motortype();
    				BeanUtils.copyProperties(tbl1401Motortype, newtbl1401Motortype);
    				// jo.put("tbl1401Motortype",
    				// new
    				// JSONObject(velotechUtil.gsonWithExpose().toJson(newtbl1401Motortype)));
    				/*
    				 * Tbl15WiringDiagram tbl15WiringDiagram =
    				 * offerDao.getWiringDiagram(tbl1401Motortype.getPhase(),
    				 * tbl26OfferPump.getTbl28SelectedPumpValues().getStartingMethod(),
    				 * tbl14PrimemoverMaster.getTbl1401Motortype().getSeries()); if
    				 * (tbl15WiringDiagram != null) { if (tbl15WiringDiagram.getMotorSeries()
    				 * .contains(tbl14PrimemoverMaster.getTbl1401Motortype().getSeries())) {
    				 * technicalDataSheet.setWiringImageName(tbl15WiringDiagram.getDocumentMaster().
    				 * getFileName()); } }
    				 */
    			}
            }
			

			Tbl28SelectedFan tbl28SelectedFan = tbl26OfferFan.getTbl28SelectedFan();

//			Tbl28SelectedFan tbl28SelectedPumpValues = getSelectedFanValues(selectionModel, selectedFan,
//					new Tbl26OfferFan(), addToOfferDto);
			// technicalDataSheet.setNrvLoss(tbl28SelectedPumpValues.getNrvLoss());
			// technicalDataSheet.setNrvName(tbl28SelectedPumpValues.getNrvName());
			// String cosFullLoad = "";
			// technicalDataSheet.setExtendedCableLength(tbl28SelectedPumpValues.getExtendedCableLength());
			// technicalDataSheet.setMotorCableLength(tbl14PrimemoverMaster.getMotorCableLength());

//			cosFullLoad = tbl14PrimemoverMaster.getPowerFactor50() != null
//					&& tbl14PrimemoverMaster.getPowerFactor50() != 0
//							? tbl14PrimemoverMaster.getPowerFactor50().toString()
//							: " ";
//			cosFullLoad = cosFullLoad + "-"
//					+ (tbl14PrimemoverMaster.getPowerFactor75() != null && tbl14PrimemoverMaster.getPowerFactor75() != 0
//							? tbl14PrimemoverMaster.getPowerFactor75().toString()
//							: " ");
//			cosFullLoad = cosFullLoad + "-"
//					+ (tbl14PrimemoverMaster.getPowerFactor100() != null
//							&& tbl14PrimemoverMaster.getPowerFactor100() != 0
//									? tbl14PrimemoverMaster.getPowerFactor100().toString()
//									: " ");
//			technicalDataSheet.setCosFullLoad(cosFullLoad);
            if(tbl14PrimemoverMaster != null) {
            	technicalDataSheet.setMotorRatedSpeed(tbl14PrimemoverMaster.getSpeed());
            	technicalDataSheet.setMaximumCableSize(tbl14PrimemoverMaster.getMaximumCableSize());
            }
			
			// technicalDataSheet.setMotorEffFullLoad(tbl14PrimemoverMaster.getEfficiency100());
			technicalDataSheet.setMinFluidLevel(0.5);
			//technicalDataSheet.setUserBoreWellSize(tbl26OfferFan.getTbl27RequirementsDp().getBorewellSize());

			/*
			 * Tbl15StartingMethod tbl15StartingMethod =
			 * offerDao.getTbl15StartingMethod(tbl14PrimemoverMaster.getPower(),
			 * tbl28SelectedPumpValues.getStartingMethod());
			 * 
			 * if (tbl15StartingMethod != null)
			 * technicalDataSheet.setMinimumCableSize(tbl15StartingMethod.getCableSize());
			 * else technicalDataSheet.setMinimumCableSize("-");
			 */

			

			jo = getJsonFilterSeriesMaster(jo, selectedFan);
			System.out.println("MeargeObject-1:-" + jo.toString());
			// getJsonFilterMaster(jo, selectedPump);
			System.out.println("MeargeObject-2:-" + jo.toString());

			jo = jsonService.setJsonParameter(jo, selectedFan.getFanTypeUuid(), "data", "");
			System.out.println("FinalJsonData:-" + jo.toString());

			jo = setUserInput(addToOfferDto, jo);

			// JSOn Code
			// jo.put("technicalDataSheet", new
			// JSONObject(velotechUtil.gson().toJson(technicalDataSheet)));
			jo.put("technicalDataSheet", new JSONObject(technicalDataSheet));

			System.out.println("jo finaldata:-" + jo);
			InputStream is = new ByteArrayInputStream(jo.toString().getBytes());
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(JsonQueryExecuterFactory.JSON_INPUT_STREAM, is);
			parameters.put("chart", new JFreeChartSvgRenderer(chart));

			String datasheetName = "";
			Tbl90DocumentMaster documentMaster;
			if (tbl01CentrifugalModelMaster.getDocumentMaster() != null) {
				documentMaster = (Tbl90DocumentMaster) genericDao.getRecordById(Tbl90DocumentMaster.class,
						tbl01CentrifugalModelMaster.getDocumentMaster().getId());

			} else
				documentMaster = (Tbl90DocumentMaster) genericDao.getRecordById(Tbl90DocumentMaster.class,
						tbl01CentrifugalModelMaster.getTbl01fantype().getDocumentMaster().getId());

			datasheetName = documentMaster.getFileName();
			velotechUtil.getJsonReport(datasheetName, technicalDataSheet.getOutputPdfName(), parameters);
			// path = velotechUtil.getUserContextPath() +
			// technicalDataSheet.getOutputPdfName() + ".pdf";

			// Performance chart
			PerformanceChartSheet performanceChartSheet = new PerformanceChartSheet(tbl26OfferFan, chart);
			performanceChartSheet.setLogoPath(velotechUtil.getCompanyDocumentPath(tbl28CompanyMaster.getLogo()));
			performanceChartSheet.setCompanyName(tbl28CompanyMaster.getDocumentCompanyName());
			performanceChartSheet.setCompanyAddress(tbl28CompanyMaster.getDocumentComapnyAddress());
			performanceChartSheet.setPreparedBy(velotechUtil.getUsername());

			List<PerformanceChartSheet> sheetList = new ArrayList<PerformanceChartSheet>();
			sheetList.add(performanceChartSheet);
			String reportPath = velotechUtil.getReport(sheetList, "performanceChartSheet.jasper",
					performanceChartSheet.getOutputPdfName(), new HashMap<String, Object>());
			String xReportPath = reportPath.replace('\\', '/');
			List<PdfReader> temp = new ArrayList<PdfReader>();
			temp.add(new PdfReader(velotechUtil.getUserRealPath() + technicalDataSheet.getOutputPdfName()));
			temp.add(new PdfReader(velotechUtil.getUserRealPath() + performanceChartSheet.getOutputPdfName()));

			/// Drawing

			OfferDrawingPojo offerDrawing = new OfferDrawingPojo();

			Josson josson = Josson.fromJsonString(jo.toString());
			offerDrawing.setJosson(josson);

			offerDrawing.setTbl01CentrifugalModelMaster(tbl01CentrifugalModelMaster);
			offerDrawing.setTbl26OfferFan(tbl26OfferFan);
			offerDrawing.setTbl14PrimemoverMaster(tbl14PrimemoverMaster);

			// set offer revision
			Tbl23OfferRev tbl23OfferRev = new Tbl23OfferRev();
			Tbl23OfferMaster tbl23OfferMaster = new Tbl23OfferMaster();
			tbl23OfferMaster.setOfferNo("");
			tbl23OfferRev.setTbl23OfferMaster(tbl23OfferMaster);
			tbl23OfferRev.setOfferdate(null);
			tbl23OfferRev.setConsultant("");
			tbl23OfferRev.setEndUser("");
			tbl23OfferRev.setProject("");
			tbl23OfferRev.setContractor("");
			tbl23OfferRev.setSuppliedBy("");
			tbl23OfferRev.setReference("");
			tbl23OfferRev.setEnquiryDate(null);
			tbl23OfferRev.setSubject("");
			tbl23OfferRev.setTbl52UsermasterByLoginId(null);
			tbl23OfferRev.setTbl28CompanyMaster(tbl28CompanyMaster);
			tbl23OfferRev.setTbl25CustomerMaster(null);
			tbl23OfferRev.setStatus("Inpreparation");
			/*
			 * if (tbl23OfferRev.getTbl52UsermasterByLoginId() != null)
			 * result.put("preparedBy",
			 * tbl23OfferRev.getTbl52UsermasterByLoginId().getUserName());
			 */

			// end

			offerDrawing.setTbl23OfferRev(tbl23OfferRev);
//			String barepumpGa = getBearpumpGaDrg(tbl02Modelmaster, tbl26OfferPump, offerDrawing);
//			if (!barepumpGa.equals("false"))
			// temp.add(new PdfReader(velotechUtil.getUserRealPath() +
			// offerDrawing.getOutputPdfName() + ".pdf"));
			/*
			 * String pumpsetGa = getPumpsetGaDrg(tbl02Modelmaster, tbl26OfferPump,
			 * offerDrawing); if (!pumpsetGa.equals("false")) temp.add(new
			 * PdfReader(velotechUtil.getUserRealPath() + offerDrawing.getOutputPdfName() +
			 * ".pdf"));
			 */

			path = velotechUtil.mergePDF(temp, "technicalDatasheetFinal" + var + ".pdf");

			path = velotechUtil.getUserContextPath() + "technicalDatasheetFinal" + var + ".pdf";
			//

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, path);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getEffClasses() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			/*
			 * String displayField = "effClass"; String valueField = "effClass";
			 */

			List<String> effclass = dao.getEffclass("effClass", "primemoverType", "Motor");
			List<ComboBox> comboBoxs = new ArrayList<>();

			ComboBox box = new ComboBox();
			box.setValue("All");
			comboBoxs.add(box);
			for (String ans : effclass) {
				box = new ComboBox();
				box.setValue(ans);
				comboBoxs.add(box);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, comboBoxs);

			/*
			 * List<SearchCriteria> searchCriterias = new ArrayList<>(); SearchCriteria
			 * primemoverType = new SearchCriteria("primemoverType", "Motor");
			 * searchCriterias.add(primemoverType);
			 * 
			 * Conjunction conjunction =
			 * GenericUtil.getConjuction(Tbl14PrimemoverMaster.class, searchCriterias);
			 * applicationResponse = genericDao.getComboRecords(Tbl14PrimemoverMaster.class,
			 * displayField, valueField, conjunction);
			 * 
			 * Object data = "All"; applicationResponse.setData(data); List<?> models =
			 * (List<?>) applicationResponse.getData(); long total =
			 * applicationResponse.getTotal(); applicationResponse =
			 * ApplicationResponseUtil.getResponseToGetData(true,
			 * ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
			 */
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getSpecifications(String primeMoverType) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			// String displayField = "specification";
			// String valueField = "specification";

			if (primeMoverType == null)
				primeMoverType = "Motor";

			List<String> specification = dao.getEffclass("specification", "primemoverType", primeMoverType);
			List<ComboBox> comboBoxs = new ArrayList<>();

			ComboBox box = new ComboBox();
			box.setValue("All");
			comboBoxs.add(box);
			for (String ans : specification) {
				box = new ComboBox();
				box.setValue(ans);
				comboBoxs.add(box);
			}

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, comboBoxs);

			/*
			 * List<SearchCriteria> searchCriterias = new ArrayList<>(); SearchCriteria
			 * primemoverType = new SearchCriteria("primemoverType", primeMoverType);
			 * searchCriterias.add(primemoverType);
			 * 
			 * Conjunction conjunction =
			 * GenericUtil.getConjuction(Tbl14PrimemoverMaster.class, searchCriterias);
			 * applicationResponse = genericDao.getComboRecords(Tbl14PrimemoverMaster.class,
			 * displayField, valueField, conjunction); List<?> models = (List<?>)
			 * applicationResponse.getData(); long total = applicationResponse.getTotal();
			 * applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
			 * ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
			 */
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getTempRiseClasses() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String displayField = "tempRiseClass";
			String valueField = "tempRiseClass";

			List<String> tempRiseClass = dao.getAmbientemp("tempRiseClass", "primemoverType", "Motor");
			List<ComboBox> comboBoxs = new ArrayList<>();

			ComboBox box = new ComboBox();
			box.setValue("All");
			comboBoxs.add(box);
			for (String ans : tempRiseClass) {
				box = new ComboBox();
				box.setValue(ans);
				comboBoxs.add(box);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, comboBoxs);
			/*
			 * List<SearchCriteria> searchCriterias = new ArrayList<>(); SearchCriteria
			 * primemoverType = new SearchCriteria("primemoverType", "Motor");
			 * searchCriterias.add(primemoverType);
			 * 
			 * Conjunction conjunction =
			 * GenericUtil.getConjuction(Tbl14PrimemoverMaster.class, searchCriterias);
			 * applicationResponse = genericDao.getComboRecords(Tbl14PrimemoverMaster.class,
			 * displayField, valueField, conjunction); List<?> models = (List<?>)
			 * applicationResponse.getData(); long total = applicationResponse.getTotal();
			 * applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
			 * ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
			 */
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getManufacturers(String primeMoverType) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String displayField = "manufacturer";
			String valueField = "manufacturer";

			if (primeMoverType == null)
				primeMoverType = "Motor";

			List<String> manufacturer = dao.getEffclass("manufacturer", "primemoverType", primeMoverType);
			List<ComboBox> comboBoxs = new ArrayList<>();

			ComboBox box = new ComboBox();
			box.setValue("All");
			comboBoxs.add(box);
			for (String ans : manufacturer) {
				box = new ComboBox();
				box.setValue(ans);
				comboBoxs.add(box);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, comboBoxs);
			/*
			 * List<SearchCriteria> searchCriterias = new ArrayList<>(); SearchCriteria
			 * primemoverType = new SearchCriteria("primemoverType", primeMoverType);
			 * searchCriterias.add(primemoverType);
			 * 
			 * Conjunction conjunction =
			 * GenericUtil.getConjuction(Tbl14PrimemoverMaster.class, searchCriterias);
			 * applicationResponse = genericDao.getComboRecords(Tbl14PrimemoverMaster.class,
			 * displayField, valueField, conjunction); List<?> models = (List<?>)
			 * applicationResponse.getData(); long total = applicationResponse.getTotal();
			 * applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
			 * ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
			 */
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getPmseries(String primeMoverType) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String displayField = "series";
			String valueField = "series";

			if (primeMoverType == null)
				primeMoverType = "Motor";

			List<SearchCriteria> searchCriterias = new ArrayList<>();
			SearchCriteria primemoverType = new SearchCriteria("primemoverType", primeMoverType);
			searchCriterias.add(primemoverType);

			Conjunction conjunction = GenericUtil.getConjuction(Tbl14PrimemoverMaster.class, searchCriterias);
			applicationResponse = genericDao.getComboRecords(Tbl14PrimemoverMaster.class, displayField, valueField,
					conjunction);
			List<ComboBox> models = (List<ComboBox>) applicationResponse.getData();
			ComboBox all = new ComboBox();
			all.setLabel("All");
			all.setValue("");
			models.add(0, all);
			long total = applicationResponse.getTotal();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getPmPoles() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String displayField = "pole";
			String valueField = "pole";

			List<SearchCriteria> searchCriterias = new ArrayList<>();
			SearchCriteria primemoverType = new SearchCriteria("primemoverType", "Motor");
			searchCriterias.add(primemoverType);

			Conjunction conjunction = GenericUtil.getConjuction(Tbl14PrimemoverMaster.class, searchCriterias);
			applicationResponse = genericDao.getComboRecords(Tbl14PrimemoverMaster.class, displayField, valueField,
					conjunction);
			List<ComboBox> models = (List<ComboBox>) applicationResponse.getData();
			ComboBox all = new ComboBox();
			all.setLabel("All");
			all.setValue("");
			models.add(0, all);
			long total = applicationResponse.getTotal();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getMountings(String primeMoverType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationResponse getAmbientTemps() {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.velotech.fanselection.design.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.SimpleExpression;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.design.dao.CentrifugalFanSpeedDao;
import com.velotech.fanselection.dtos.CentrifugalFanSpeedDto;
import com.velotech.fanselection.dtos.PerformaceGraphDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.models.Tbl01CentrifugalFanSpeed;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01CentrifugalSpeedData;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.CentrifugalSpeedTermsUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;
import com.velotech.fanselection.utils.graph.GraphModel;
import com.velotech.fanselection.utils.graph.GraphUtil;
import com.velotech.fanselection.utils.graph.Point;
import com.velotech.fanselection.utils.graph.PolySolve;

@Service
@Transactional
public class CentrifugalFanSpeedServiceImpl implements CentrifugalFanSpeedService { 

	static Logger log = LogManager.getLogger(CentrifugalFanSpeedServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CentrifugalFanSpeedDao dao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private CentrifugalSpeedTermsUtil centrifugalSpeedTermsUtil;

	
	@Override
	public ApplicationResponse addRecord(String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;

		try {

			Tbl01CentrifugalFanSpeed model = new Tbl01CentrifugalFanSpeed();
			CentrifugalFanSpeedDto dto = new CentrifugalFanSpeedDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, CentrifugalFanSpeedDto.class);
			BeanUtils.copyProperties(dto, model);
			model.setCompany(velotechUtil.getCompany());
			if (dto.getFanModelId() != null)
				model.setTbl01CentrifugalModelMaster(new Tbl01CentrifugalModelMaster(dto.getFanModelId()));
			Tbl01CentrifugalFanSpeed centrifugalFanSpeed = (Tbl01CentrifugalFanSpeed) genericDao
					.getRecordById(Tbl01CentrifugalFanSpeed.class, dto.getId());
			if (centrifugalFanSpeed != null)
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, "Fan Speed Already Exist!");
			else {
				genericDao.save(model);
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
						ApplicationConstants.INSERT_SUCCESS_MSG);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		try {
			CentrifugalFanSpeedDto dto = new CentrifugalFanSpeedDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, CentrifugalFanSpeedDto.class);
			Tbl01CentrifugalFanSpeed model = (Tbl01CentrifugalFanSpeed) genericDao.getRecordById(Tbl01CentrifugalFanSpeed.class,
					dto.getId());

			BeanUtils.copyProperties(dto, model);
			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.INSERT_SUCCESS_MSG);
			model = centrifugalSpeedTermsUtil.termsFromData(model);
			genericDao.update(model);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		try {
			genericDao.deleteAll(Tbl01CentrifugalFanSpeed.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			List<SearchCriteria> searchCriterias = requestWrapper.getSearchCriterias() != null
					? requestWrapper.getSearchCriterias()
					: new ArrayList<>();
			if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(),
						requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}

			Conjunction conjunction = GenericUtil.getConjuction(Tbl01CentrifugalFanSpeed.class, searchCriterias);
			applicationResponse = dao.getCentrifugalFanSpeed(conjunction);
			List<Tbl01CentrifugalFanSpeed> models = (List<Tbl01CentrifugalFanSpeed>) applicationResponse.getData();
			List<CentrifugalFanSpeedDto> dtos = getData(models);

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<CentrifugalFanSpeedDto> getData(List<Tbl01CentrifugalFanSpeed> models) {

		List<CentrifugalFanSpeedDto> dtos = new ArrayList<>();

		DecimalFormat oneDForm = new DecimalFormat("#.#");
		DecimalFormat threeDForm = new DecimalFormat("#.###");
		try {
			for (Tbl01CentrifugalFanSpeed model : models) {
				CentrifugalFanSpeedDto dto = new CentrifugalFanSpeedDto();
				BeanUtils.copyProperties(model, dto);

				// Bep Calculation
				Set<Tbl01CentrifugalSpeedData> tbl01CentrifugalSpeedDataSet = new HashSet<Tbl01CentrifugalSpeedData>();
				tbl01CentrifugalSpeedDataSet = model.getTbl01CentrifugalSpeedData();

				if (tbl01CentrifugalSpeedDataSet.size() > 0) {
					Iterator<Tbl01CentrifugalSpeedData> centrifugalSpeedDataSet = tbl01CentrifugalSpeedDataSet.iterator();
					List<Double> q = new ArrayList<Double>();
					List<Double> e = new ArrayList<Double>();

					while (centrifugalSpeedDataSet.hasNext()) {
						Tbl01CentrifugalSpeedData centrifugalSpeedData = centrifugalSpeedDataSet.next();
						q.add(centrifugalSpeedData.getFlow().doubleValue());

					}

				}
				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return dtos;
	}

	
	@Override
	public void getPerformanceGraph(String requestPayload) {

		String perfCurveName;
		PrintWriter out;
		String userPath = velotechUtil.getUserRealPath();
		try {
			PerformaceGraphDto dto = new PerformaceGraphDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, PerformaceGraphDto.class);

			out = response.getWriter();
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "no-store");
			JFreeChart chart = null;
			GraphUtil graphUtil = new GraphUtil();
			VelotechUtil velotechUtil = new VelotechUtil();
			PolySolve polysolve = new PolySolve();
			List<GraphModel> graphModelListHead = new ArrayList<GraphModel>();
			List<GraphModel> graphModelListPower = new ArrayList<GraphModel>();

			Map<Double, Boolean> checkJoin = new HashMap<Double, Boolean>();
			if (dto.getType().equals("folder")) {
				SearchCriteria searchCriteria = new SearchCriteria("tbl01CentrifugalModelMaster.id", dto.getValue());
				SimpleExpression restriction = GenericUtil.getRestriction(Tbl01CentrifugalFanSpeed.class, searchCriteria);

				List<Tbl01CentrifugalFanSpeed> tbl01CentrifugalFanSpeeds = (List<Tbl01CentrifugalFanSpeed>) genericDao
						.getRecordsByParentId(Tbl01CentrifugalFanSpeed.class, "tbl01CentrifugalModelMaster.id", dto.getValue());
				
				
				List<Tbl01CentrifugalFanSpeed> tbl01CentrifugalFanSpeed1 = tbl01CentrifugalFanSpeeds.stream()
				        .sorted(Comparator.comparing(Tbl01CentrifugalFanSpeed::getSpeed, Comparator.nullsLast(Integer::compareTo)))
				        .collect(Collectors.toList());
				
				for (Tbl01CentrifugalFanSpeed centrifugalFanSpeed :  tbl01CentrifugalFanSpeed1) {

					Iterator<Tbl01CentrifugalSpeedData> centrifugalSpeedDataSet = centrifugalFanSpeed.getTbl01CentrifugalSpeedData()
							.iterator();
					List<Double> q = new ArrayList<Double>();
					List<Double> h = new ArrayList<Double>();
					List<Double> qp = new ArrayList<Double>();
					List<Double> p = new ArrayList<Double>();

					while (centrifugalSpeedDataSet.hasNext()) {
						Tbl01CentrifugalSpeedData centrifugalSpeedData = centrifugalSpeedDataSet.next();
						q.add(centrifugalSpeedData.getFlow().doubleValue());
						h.add(centrifugalSpeedData.getPressure().doubleValue());
						if (centrifugalSpeedData.getPower() != null) {
							p.add(centrifugalSpeedData.getPower().doubleValue());
							qp.add(centrifugalSpeedData.getFlow().doubleValue());
						}

					}
					if (!q.isEmpty())
						graphModelListHead.add(new GraphModel(
								polysolve.process(velotechUtil.XY(q, h), centrifugalFanSpeed.getPressureDegree()),
								Collections.min(q), Collections.max(q), true, "blue", "Solid", 3, "red",
								"verticalRectangle", centrifugalFanSpeed.getSpeed().toString()));

					if (!qp.isEmpty())
						graphModelListPower.add(new GraphModel(
								polysolve.process(velotechUtil.XY(qp, p), centrifugalFanSpeed.getPowerDegree()),
								Collections.min(qp), Collections.max(qp),  true, "blue", "Solid", 3, "red",
								"verticalRectangle", centrifugalFanSpeed.getSpeed().toString()));

				}

				List<Point> dutyPointList = new ArrayList<Point>();

				CombinedDomainXYPlot combinedDomainXYPlot = graphUtil.combineGraph("Flow m\u00B3/hr", true, 0);

				combinedDomainXYPlot.add((graphUtil.individualGraph(graphModelListHead,null, "Pressure(MMWG)",
						new java.awt.Point(), true, true, true)));

				combinedDomainXYPlot.add((graphUtil.individualGraph(graphModelListPower,null, "Power(kw)",
						new java.awt.Point(1, 1), true, true, true)));
				chart = graphUtil.getChart("", " ", combinedDomainXYPlot, true);
			} else {
				Tbl01CentrifugalFanSpeed centrifugalFanSpeed = dao.getCentrifugalFanSpeedDataForGraph(dto.getValue());
				Iterator<Tbl01CentrifugalSpeedData> centrifugalSpeedDataSet = centrifugalFanSpeed.getTbl01CentrifugalSpeedData()
						.iterator();
				List<Double> q = new ArrayList<Double>();
				List<Double> h = new ArrayList<Double>();
				List<Double> p = new ArrayList<Double>();

				while (centrifugalSpeedDataSet.hasNext()) {
					Tbl01CentrifugalSpeedData  centrifugalSpeedData = centrifugalSpeedDataSet.next();
					q.add(centrifugalSpeedData.getFlow().doubleValue());
					h.add(centrifugalSpeedData.getPressure().doubleValue());
					if (centrifugalSpeedData.getPower() != null)
						p.add(centrifugalSpeedData.getPower().doubleValue());
					else
						p.add(null);
				}
				if (!q.isEmpty()) {
					graphModelListHead.add(new GraphModel(
							polysolve.process(velotechUtil.XY(q, h), centrifugalFanSpeed.getPressureDegree()),
							Collections.min(q), Collections.max(q),  true, "blue", "Solid", 3, "red",
							"verticalRectangle", centrifugalFanSpeed.getSpeed().toString()));

					graphModelListPower.add(new GraphModel(
							polysolve.process(velotechUtil.XY(q, p), centrifugalFanSpeed.getPowerDegree()),
							Collections.min(q), Collections.max(q), true, "blue", "Solid", 3, "red",
							"verticalRectangle", centrifugalFanSpeed.getSpeed().toString()));

				}

				List<Point> dutyPointList = new ArrayList<Point>();

				CombinedDomainXYPlot combinedDomainXYPlot = graphUtil.combineGraph("Flow m\u00B3/hr", true, 0);
				combinedDomainXYPlot.add(graphUtil.individualGraph(graphModelListHead,null, "Pressure(MMWG)",
						new java.awt.Point(), true, true, true));

				combinedDomainXYPlot.add((graphUtil.individualGraph(graphModelListPower,null, "Power(kw)",
						new java.awt.Point(1, 1), true, true, true)));

				chart = graphUtil.getChart("", " ", combinedDomainXYPlot, true);
			}

			Random randomGenerator = new Random();
			int var = randomGenerator.nextInt(999999);
			perfCurveName = "perfCurve" + var + ".png";
			File file1 = new File(userPath, perfCurveName);
			if (!file1.exists()) {
				file1.createNewFile();
			}
			ChartUtils.saveChartAsPNG(file1, chart, 490, 522);
			out.print(velotechUtil.getUserContextPath() + perfCurveName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

}



package com.velotech.fanselection.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velotech.fanselection.admin.dao.PerformanceSpeedtermsDao;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl01CentrifugalFanSpeed;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.CentrifugalSpeedTermsUtil;
import com.velotech.fanselection.utils.ComboBox;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class PerformanceSpeedtermsServiceImpl implements PerformanceSpeedtermsService {

	static Logger log = LogManager.getLogger(PerformanceSpeedtermsServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private PerformanceSpeedtermsDao dao;

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getSeries() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			List<ComboBox> dtos = new ArrayList<>();
			ComboBox box = new ComboBox();
			box.setLabel("All");
			box.setValue("All");
			dtos.add(box);

			List<Tbl01Fantype> pumptypes = (List<Tbl01Fantype>) genericDao.getRecords(Tbl01Fantype.class);

			for (Tbl01Fantype tbl01Fantype : pumptypes) {
				ComboBox dto = new ComboBox();
				dto.setLabel(tbl01Fantype.getSeries());
				dto.setValue(tbl01Fantype.getId());
				dtos.add(dto);
			}
			long total = applicationResponse.getTotal();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getFanModel(String seriesId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<ComboBox> dtos = new ArrayList<>();
			ComboBox box = new ComboBox();
			box.setLabel("All");
			box.setValue("All");
			dtos.add(box);

			if (!seriesId.equalsIgnoreCase("All")) {
				List<Tbl01CentrifugalModelMaster> tbl01CentrifugalModelMasters = (List<Tbl01CentrifugalModelMaster>) dao.getFanModel(seriesId).getData();
				for (Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster : tbl01CentrifugalModelMasters) {
					ComboBox dto = new ComboBox();
					dto.setLabel(tbl01CentrifugalModelMaster.getFanModel());
					dto.setValue(tbl01CentrifugalModelMaster.getId());
					dtos.add(dto);
				}
			}

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	/*
	 * @Override public ApplicationResponse getPerformaneCurve(String modelId) {
	 * 
	 * ApplicationResponse applicationResponse = new ApplicationResponse();
	 * 
	 * try {
	 * 
	 * List<ComboBox> dtos = new ArrayList<>(); ComboBox box = new ComboBox();
	 * box.setLabel("All"); box.setValue("All"); dtos.add(box); if
	 * (!modelId.equalsIgnoreCase("All")) { List<Tbl09Performancecurvemaster>
	 * tbl09Performancecurvemaster = (List<Tbl09Performancecurvemaster>)
	 * dao.getPerformaneCurve(modelId) .getData(); for (Tbl09Performancecurvemaster
	 * master : tbl09Performancecurvemaster) { ComboBox dto = new ComboBox();
	 * dto.setLabel(master.getPerformanceCurveNo()); dto.setValue(master.getId());
	 * dtos.add(dto); } }
	 * 
	 * applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
	 * ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return applicationResponse; }
	 */

	@Override
	@SuppressWarnings("unchecked")
	public ApplicationResponse getPerformanceCurveSpeed(String fanModelId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<ComboBox> dtos = new ArrayList<>();
			ComboBox box = new ComboBox();
			box.setLabel("All");
			box.setValue("All");
			dtos.add(box);
			if (!fanModelId.equalsIgnoreCase("All")) {
				List<Tbl01CentrifugalFanSpeed> tbl01CentrifugalFanSpeeds = (List<Tbl01CentrifugalFanSpeed>) dao.getPerformanceCurveSpeed(fanModelId).getData();
				for (Tbl01CentrifugalFanSpeed tbl01CentrifugalFanSpeed : tbl01CentrifugalFanSpeeds) {
					ComboBox dto = new ComboBox();
					dto.setLabel(tbl01CentrifugalFanSpeed.getSpeed());
					dto.setValue(tbl01CentrifugalFanSpeed.getId());
					dtos.add(dto);
				}

			}

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse upatePerformanceSpeedterms(String series, String fanModel,  String perfSpeed) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		CentrifugalSpeedTermsUtil centrifugalSpeedTermsUtil = new CentrifugalSpeedTermsUtil();
		try {
			List<Tbl01CentrifugalFanSpeed> performanceSpeedList = new ArrayList<Tbl01CentrifugalFanSpeed>();
			performanceSpeedList = dao.getPerformanceSpeed(series, fanModel,  perfSpeed);
			for (Tbl01CentrifugalFanSpeed tbl01CentrifugalFanSpeed : performanceSpeedList) {
				tbl01CentrifugalFanSpeed = centrifugalSpeedTermsUtil.termsFromData(tbl01CentrifugalFanSpeed);
			}
			for (Tbl01CentrifugalFanSpeed tbl01CentrifugalFanSpeed : performanceSpeedList) {
				genericDao.update(tbl01CentrifugalFanSpeed);
			}
			long total = applicationResponse.getTotal();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, total);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}
}

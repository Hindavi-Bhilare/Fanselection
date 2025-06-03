
package com.velotech.fanselection.design.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velotech.fanselection.design.dao.CentrifugalXmlModelmasterDao;
import com.velotech.fanselection.dtos.CentrifugalXmlModelmasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.models.Tbl01CentrifugalFanSpeed;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01CentrifugalSpeedData;
import com.velotech.fanselection.models.Tbl90XmlCentrifugalFanSpeed;
import com.velotech.fanselection.models.Tbl90XmlCentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl90XmlCentrifugalPerformanceDataHead;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.CentrifugalSpeedTermsUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class CentrifugalXmlModelmasterServiceImpl implements CentrifugalXmlModelmasterService { 

	static Logger log = LogManager.getLogger(CentrifugalXmlModelmasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CentrifugalXmlModelmasterDao dao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private CentrifugalSpeedTermsUtil centrifugalSpeedTermsUtil;

	@Override
	public ApplicationResponse copyRecord(List<Integer> datas) {

		ApplicationResponse applicationResponse = new ApplicationResponse();

		List<Integer> diaId = new ArrayList<>();
		try {
			List<Tbl90XmlCentrifugalModelMaster> models = (List<Tbl90XmlCentrifugalModelMaster>) genericDao
					.getRecordByIds(Tbl90XmlCentrifugalModelMaster.class, datas);
			for (Tbl90XmlCentrifugalModelMaster tbl90XmlCentrifugalModelMaster : models) {

				Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster = dao
						.getCentrifugalModelMaster(tbl90XmlCentrifugalModelMaster.getFanModel());

				if (tbl01CentrifugalModelMaster == null)
					continue;

				List<Tbl90XmlCentrifugalFanSpeed> tbl90XmlCentrifugalFanSpeeds = dao
						.getTbl90XmlCentrifugalFanSpeed(tbl90XmlCentrifugalModelMaster.getId());
				if (tbl90XmlCentrifugalFanSpeeds.size() > 0) {

					for (Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeed : tbl90XmlCentrifugalFanSpeeds) {
						Tbl01CentrifugalFanSpeed centrifugalFanSpeed = copyCentrifugalFanSpeed(
								tbl90XmlCentrifugalFanSpeed, tbl01CentrifugalModelMaster);
						centrifugalFanSpeed.setCreatedBy(velotechUtil.getLoginId());
						centrifugalFanSpeed.setCreatedDate(new Date());
						centrifugalFanSpeed.setCompany(velotechUtil.getCompany());

						dao.saveTbl01CentrifugalFanSpeed(centrifugalFanSpeed);
						List<Double> mixMaxQ = new ArrayList<>();
						diaId.add(centrifugalFanSpeed.getId());
						Set<Tbl90XmlCentrifugalPerformanceDataHead> tbl90XmlCentrifugalPerformanceDataHeads = tbl90XmlCentrifugalFanSpeed
								.getTbl90XmlCentrifugalPerformanceDataHeads();
						for (Object object : tbl90XmlCentrifugalPerformanceDataHeads) {
							Tbl90XmlCentrifugalPerformanceDataHead tbl90XmlCentrifugalPerformanceDataHead = (Tbl90XmlCentrifugalPerformanceDataHead) object;
							Tbl01CentrifugalSpeedData centrifugalSpeedData = copyCentrifugalSpeedData(
									tbl90XmlCentrifugalPerformanceDataHead, centrifugalFanSpeed);
							dao.saveTbl01CentrifugalSpeedData(centrifugalSpeedData);
							mixMaxQ.add(centrifugalSpeedData.getFlow().doubleValue());
						}
						centrifugalFanSpeed.setRangeQMin(Collections.min(mixMaxQ));
						centrifugalFanSpeed.setRangeQMax(Collections.max(mixMaxQ));
						dao.updateTbl01CentrifugalFanSpeed(centrifugalFanSpeed);
					}
				}
			}
			Integer[] ids = new Integer[diaId.size()];
			for (int i = 0; i < diaId.size(); i++) {
				ids[i] = diaId.get(i);
			}
			updateTerms(ids);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.COPY_SUCCESS_MSG);

		return applicationResponse;
	}

	private Tbl01CentrifugalSpeedData copyCentrifugalSpeedData(Tbl90XmlCentrifugalPerformanceDataHead tbl90XmlCentrifugalPerformanceDataHead,
			Tbl01CentrifugalFanSpeed centrifugalFanSpeed) {

		// TODO Auto-generated method stub
		Tbl01CentrifugalSpeedData centrifugalSpeedData = new Tbl01CentrifugalSpeedData();
		try {
			centrifugalSpeedData.setTbl01CentrifugalFanSpeed(centrifugalFanSpeed);
			centrifugalSpeedData.setFlow(tbl90XmlCentrifugalPerformanceDataHead.getQ());
			centrifugalSpeedData.setPressure(tbl90XmlCentrifugalPerformanceDataHead.getH());
			if (tbl90XmlCentrifugalPerformanceDataHead.getP() != null)
				centrifugalSpeedData.setPower(tbl90XmlCentrifugalPerformanceDataHead.getP());
			centrifugalSpeedData.setCompany(velotechUtil.getCompany());
			centrifugalSpeedData.setCreatedBy(velotechUtil.getUsername());
			centrifugalSpeedData.setCreatedDate(new Date());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return centrifugalSpeedData;
	}

	private Tbl01CentrifugalFanSpeed copyCentrifugalFanSpeed(Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeed,
			Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster) {

		// TODO Auto-generated method stub
		Tbl01CentrifugalFanSpeed centrifugalFanSpeed = new Tbl01CentrifugalFanSpeed();
		try {
			BeanUtils.copyProperties(tbl90XmlCentrifugalFanSpeed, centrifugalFanSpeed, new String[] { "id" });
			centrifugalFanSpeed.setTbl01CentrifugalModelMaster(tbl01CentrifugalModelMaster);
			centrifugalFanSpeed.setCompany(velotechUtil.getCompany());
			centrifugalFanSpeed.setCreatedBy(velotechUtil.getUsername());
			centrifugalFanSpeed.setCreatedDate(new Date());
			// performanceDia.setVa(Double.parseDouble(tbl90XmlPerformanceDia.getVariable()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return centrifugalFanSpeed;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		List<Tbl90XmlCentrifugalModelMaster> models = (List<Tbl90XmlCentrifugalModelMaster>) genericDao
				.getRecordByIds(Tbl90XmlCentrifugalModelMaster.class, ids);
		genericDao.deleteAll(models);

		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(),
						requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}

			Conjunction conjunction = GenericUtil.getConjuction(Tbl90XmlCentrifugalModelMaster.class, searchCriterias);
			Pagination pagination = requestWrapper.getPagination();
			applicationResponse = genericDao.getRecords(Tbl90XmlCentrifugalModelMaster.class, conjunction, pagination);

			List<Tbl90XmlCentrifugalModelMaster> models = (List<Tbl90XmlCentrifugalModelMaster>) applicationResponse
					.getData();
			long total = applicationResponse.getTotal();

			List<CentrifugalXmlModelmasterDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	private List<CentrifugalXmlModelmasterDto> getData(List<Tbl90XmlCentrifugalModelMaster> models) {

		List<CentrifugalXmlModelmasterDto> dtos = new ArrayList<>();
		for (Tbl90XmlCentrifugalModelMaster model : models) {
			CentrifugalXmlModelmasterDto dto = new CentrifugalXmlModelmasterDto();
			BeanUtils.copyProperties(model, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	public boolean updateTerms(Integer[] id) {

		boolean ans = false;
		try {
			List<Tbl01CentrifugalFanSpeed> centrifugalFanSpeedList = new ArrayList<Tbl01CentrifugalFanSpeed>();
			centrifugalFanSpeedList = dao.getCentrifugalFanSpeed(id);
			for (Tbl01CentrifugalFanSpeed tbl01CentrifugalFanSpeed : centrifugalFanSpeedList) {
				centrifugalSpeedTermsUtil = new CentrifugalSpeedTermsUtil();
				tbl01CentrifugalFanSpeed = centrifugalSpeedTermsUtil.termsFromData(tbl01CentrifugalFanSpeed);
				dao.updateTbl01CentrifugalFanSpeed(tbl01CentrifugalFanSpeed);
			}
			ans = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e.fillInStackTrace());
		}
		return ans;
	}
}

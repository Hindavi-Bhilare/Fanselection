
package com.velotech.fanselection.combobox.service;

import java.util.ArrayList;




import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.velotech.fanselection.combobox.dao.ComboboxDao;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl03MocMaster;
import com.velotech.fanselection.models.Tbl09Dropdownlist;
//import com.velotech.fanselection.models.Tbl09Performancecurvemaster;
import com.velotech.fanselection.models.Tbl1401Motortype;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl58UserCompany;
import com.velotech.fanselection.models.Tbl80SegmentMaster;
import com.velotech.fanselection.offer.dao.OfferDetailsDao;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.ComboBox;
import com.velotech.fanselection.utils.CommonList;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class ComboboxServiceImpl implements ComboboxService {

	static Logger log = LogManager.getLogger(ComboboxServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	OfferDetailsDao offerDetaisDao;

	@Autowired
	private ComboboxDao dao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private CommonList commonlist;

	@Override
	public ApplicationResponse getComboRecords(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String model = requestWrapper.getModelName();
			String displayField = requestWrapper.getDisplayField();
			String valueField = requestWrapper.getValueField();
			String searchProperty = requestWrapper.getSearchProperty();
			Object searchValue = requestWrapper.getSearchValue();

			SearchCriteria searchCriteria = null;
			if (searchProperty != null)
				searchCriteria = new SearchCriteria(searchProperty, searchValue);

			Class<?> modelClass = Class.forName(model);
			List<?> models = new ArrayList<>();

			applicationResponse = genericDao.getComboRecords(modelClass, displayField, valueField,
					GenericUtil.getConjuction(modelClass, searchCriteria));
			models = (List<?>) applicationResponse.getData();
			long total = applicationResponse.getTotal();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getPumpTypes() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			applicationResponse = genericDao.getComboRecords(Tbl01Fantype.class, "series", "id", null);
			List<ComboBox> models = (List<ComboBox>) applicationResponse.getData();
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
	public ApplicationResponse getTableList() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			List<String> tbllist = genericDao.getTables();
			List<ComboBox> dtos = new ArrayList<>();
			for (String table : tbllist) {

				ComboBox dto = new ComboBox();
				dto.setLabel(table);
				dto.setValue(table);
				dtos.add(dto);
			}
			long total = dtos.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getOffers(String query, RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			applicationResponse = dao.getOffers(query, requestWrapper.getPagination());

			List<Tbl23OfferRev> models = (List<Tbl23OfferRev>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<ComboBox> dtos = new ArrayList<>();
			for (Tbl23OfferRev tbl23OfferRev : models) {
				ComboBox dto = new ComboBox();
				dto.setLabel(tbl23OfferRev.getTbl23OfferMaster().getOfferNo() + "(" + tbl23OfferRev.getRev() + ")");
				dto.setValue(tbl23OfferRev.getId());

				dtos.add(dto);
			}

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getOfferTags(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<?> models = new ArrayList<>();
			long total = 0;
			if (offerRevId != null) {
				SearchCriteria searchCriteria = new SearchCriteria("tbl23OfferRev.id", offerRevId);
				applicationResponse = genericDao.getComboRecords(Tbl26OfferFan.class, "tagNo", "tagNo",
						GenericUtil.getConjuction(Tbl26OfferFan.class, searchCriteria));
				models = (List<?>) applicationResponse.getData();
				total = applicationResponse.getTotal();
			}

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getUserCompanies() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<ComboBox> models = new ArrayList<>();
			if (velotechUtil.isAdminUser()) {
				List<Tbl28CompanyMaster> tbl28CompanyMasters = (List<Tbl28CompanyMaster>) genericDao
						.getRecords(Tbl28CompanyMaster.class);
				for (Tbl28CompanyMaster tbl28CompanyMaster : tbl28CompanyMasters) {
					ComboBox comboBox = new ComboBox();
					comboBox.setLabel(tbl28CompanyMaster.getCompanyName());
					comboBox.setValue(tbl28CompanyMaster.getId());
					models.add(comboBox);
				}
			} else {
				List<Tbl58UserCompany> tbl58UserCompanies = dao.getUserCompanies();
				for (Tbl58UserCompany tbl58UserCompany : tbl58UserCompanies) {
					ComboBox comboBox = new ComboBox();
					comboBox.setLabel(tbl58UserCompany.getTbl28CompanyMaster().getCompanyName());
					comboBox.setValue(tbl58UserCompany.getTbl28CompanyMaster().getId());
					models.add(comboBox);
				}
			}
			long total = models.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getPrimeMover() {

		List<ComboBox> models = new ArrayList<>();
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl14PrimemoverMaster> tbl14PrimemoverMasterList = dao.getPrimeMover();
			for (Tbl14PrimemoverMaster tbl14PrimemoverMaster : tbl14PrimemoverMasterList) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(tbl14PrimemoverMaster.getSeries());
				comboBox.setValue(tbl14PrimemoverMaster.getPrimemoverId());
				models.add(comboBox);
			}
			long total = models.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	/*@Override
	public ApplicationResponse getCanopy() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<ComboBox> models = new ArrayList<>();
			List<String> canopys = dao.getCanopy();
			for (String canopy : canopys) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(canopy);
				comboBox.setValue(canopy);
				models.add(comboBox);
			}
			long total = models.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}*/

	/*@Override
	public ApplicationResponse getDrainRim() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<ComboBox> models = new ArrayList<>();
			List<String> drainRims = dao.getDrainRim();
			for (String drainRim : drainRims) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(drainRim);
				comboBox.setValue(drainRim);
				models.add(comboBox);
			}
			long total = models.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}*/

	/*@Override
	public ApplicationResponse getEarthing() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<ComboBox> models = new ArrayList<>();
			List<String> earthings = dao.getEarthing();
			for (String earthing : earthings) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(earthing);
				comboBox.setValue(earthing);
				models.add(comboBox);
			}
			long total = models.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}*/

	/*@Override
	public ApplicationResponse getLiftingLug() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<ComboBox> models = new ArrayList<>();
			List<String> liftingLugs = dao.getLiftingLug();
			for (String liftingLug : liftingLugs) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(liftingLug);
				comboBox.setValue(liftingLug);
				models.add(comboBox);
			}
			long total = models.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}*/

	/*@Override
	public ApplicationResponse getStandard() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<ComboBox> models = new ArrayList<>();
			List<String> standards = dao.getStandard();
			for (String standard : standards) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(standard);
				comboBox.setValue(standard);
				models.add(comboBox);
			}
			long total = models.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}*/

	@Override
	public ApplicationResponse getMocStd(Integer modelmasterid) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<ComboBox> models = new ArrayList<>();

			if (modelmasterid != null) {

				Tbl01CentrifugalModelMaster tbl02Modelmaster = (Tbl01CentrifugalModelMaster) genericDao.getRecordById(Tbl01CentrifugalModelMaster.class,
						modelmasterid);

				List<Tbl03MocMaster> tbl03MocMaster = (List<Tbl03MocMaster>) genericDao.getRecordsByParentId(
						Tbl03MocMaster.class, "tbl01fantype.id", tbl02Modelmaster.getTbl01fantype().getId());

				for (Tbl03MocMaster mocStd : tbl03MocMaster) {
					ComboBox comboBox = new ComboBox();
					comboBox.setLabel(mocStd.getMocStd());
					comboBox.setValue(mocStd.getMocStd());
					models.add(comboBox);
				}

				applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
						ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models);

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public ApplicationResponse getPerformanceCurve(Integer
	 * modelmasterid) {
	 * 
	 * ApplicationResponse applicationResponse = new ApplicationResponse();
	 * 
	 * try { List<Tbl09Performancecurvemaster> tbl09Performancecurvemaster =
	 * (List<Tbl09Performancecurvemaster>) dao
	 * .getPerformaneCurve(modelmasterid).getData();
	 * 
	 * List<ComboBox> dtos = new ArrayList<>(); for (Tbl09Performancecurvemaster
	 * master : tbl09Performancecurvemaster) { ComboBox dto = new ComboBox();
	 * dto.setLabel(master.getPerformanceCurveNo()); dto.setValue(master.getId());
	 * dtos.add(dto); } long total = applicationResponse.getTotal();
	 * applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
	 * ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total); } catch (Exception
	 * e) { log.error(e.getMessage(), e); } return applicationResponse; }
	 */

	@Override
	public ApplicationResponse getSegmentMaster() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String displayField = "segment";
			String valueField = "segment";

			applicationResponse = genericDao.getComboRecords(Tbl80SegmentMaster.class, displayField, valueField, null);
			List<ComboBox> models = (List<ComboBox>) applicationResponse.getData();
			ComboBox all = new ComboBox();
			all.setLabel("All");
			all.setValue("All");
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
	public ApplicationResponse getUserMaster() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String displayField = "user";
			String valueField = "user";

			List<String> userlogin = commonlist.getSubordinatesIds();
			List<Tbl52Usermaster> tbl52Usermaster = (List<Tbl52Usermaster>) genericDao
					.findByParam(Tbl52Usermaster.class, "loginId", userlogin);
			List<ComboBox> models = new ArrayList<>();

			for (Tbl52Usermaster tbl52Usermasterold : tbl52Usermaster) {

				String user = tbl52Usermasterold.getUserName();
				ComboBox model = new ComboBox();
				model.setLabel(user);
				model.setValue(user);
				models.add(model);
			}

			ComboBox all = new ComboBox();
			all.setLabel("All");
			all.setValue("All");
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
	public ApplicationResponse getStatus() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String displayField = "status";
			String valueField = "status";

			applicationResponse = genericDao.getComboRecords(Tbl23OfferRev.class, displayField, valueField, null);
			if (applicationResponse == null)
				applicationResponse.setData("Active");
			List<ComboBox> models = (List<ComboBox>) applicationResponse.getData();
			ComboBox all = new ComboBox();
			all.setLabel("All");
			all.setValue("All");
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
	public ApplicationResponse getType() {

		List<ComboBox> models = new ArrayList<>();
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl09Dropdownlist> tbl09Dropdownlists = dao.getType();
			for (Tbl09Dropdownlist tbl09DropdownlistData : tbl09Dropdownlists) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(tbl09DropdownlistData.getValue());
				comboBox.setValue(tbl09DropdownlistData.getValue());
				models.add(comboBox);
			}
			long total = models.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getCategory() {

		List<ComboBox> models = new ArrayList<>();
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl09Dropdownlist> tbl09Dropdownlists = dao.getCategory();
			for (Tbl09Dropdownlist tbl09DropdownlistData : tbl09Dropdownlists) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(tbl09DropdownlistData.getValue());
				comboBox.setValue(tbl09DropdownlistData.getValue());
				models.add(comboBox);
			}
			long total = models.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	/*@Override
	public ApplicationResponse getModelNrv(Integer pumpModelId) {

		// TODO Auto-generated method stub
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl02ModelNrv> modelNrvList = dao.getModelNrv(pumpModelId);
			List<ComboBox> models = new ArrayList<>();
			if (modelNrvList.size() > 0) {
				for (Tbl02ModelNrv modelNrv : modelNrvList) {
					ComboBox comboBox = new ComboBox();
					comboBox.setLabel(modelNrv.getTbl02Nrvmaster().getNrvName());
					comboBox.setValue(modelNrv.getTbl02Nrvmaster().getId());
					models.add(comboBox);
				}
			} else {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel("NA");
				comboBox.setValue(0);
				models.add(comboBox);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}*/

	@Override
	public ApplicationResponse getStartingMethod(Integer primeMoverId) {

		// TODO Auto-generated method stub
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<ComboBox> models = new ArrayList<>();
			String staringMethod = dao.getStartingMethod(primeMoverId);
			List<String> startingMethods = Arrays.asList(staringMethod.split(","));
			for (String tempStartingMethod : startingMethods) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(tempStartingMethod);
				comboBox.setValue(tempStartingMethod);
				models.add(comboBox);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	/*@Override
	public ApplicationResponse getSelectionType() {

		// TODO Auto-generated method stub
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<ComboBox> models = new ArrayList<>();
			List<Tbl00SelectiontypeMaster> tbl00SelectiontypeMasterList = dao.getSelectionType();
			for (Tbl00SelectiontypeMaster tbl00SelectiontypeMaster2 : tbl00SelectiontypeMasterList) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(tbl00SelectiontypeMaster2.getGroup());
				comboBox.setValue(tbl00SelectiontypeMaster2.getId());
				models.add(comboBox);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}*/

	@Override
	public ApplicationResponse getMotorSeries() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String model = "com.velotech.pumpselection.models.Tbl1401Motortype";
			String displayField = "series";
			String valueField = "series";
			SearchCriteria searchCriteria = null;
			Class<?> modelClass = Class.forName(model);
			List<?> models = new ArrayList<>();

			applicationResponse = genericDao.getComboRecords(modelClass, displayField, valueField,
					GenericUtil.getConjuction(modelClass, searchCriteria));
			models = (List<?>) applicationResponse.getData();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	// @Override
	// public ApplicationResponse getQap(Integer offerPumpId) {
	//
	// List<ComboBox> models = new ArrayList<>();
	// ApplicationResponse applicationResponse = new ApplicationResponse();
	// try {
	// Tbl26OfferPump tbl26OfferPump = (Tbl26OfferPump)
	// genericDao.getRecordById(Tbl26OfferPump.class, offerPumpId);
	// Tbl28SelectedPump tbl28SelectedPump =
	// tbl26OfferPump.getTbl28SelectedPump();
	//
	// Tbl14PrimemoverMaster tbl14PrimemoverMaster_M =
	// offerDetaisDao.getPrimeMover(offerPumpId);
	// // List<Tbl03QapMaster> qapMasters =
	// // dao.getQap(tbl14PrimemoverMaster_M.getTbl1401Motortype().getSeries(),
	// // tbl28SelectedPump.getSeries());
	// if (tbl14PrimemoverMaster_M.getTbl03QapMaster() != null &&
	// tbl14PrimemoverMaster_M.getTbl03QapMaster().getTbl90DocumentMaster() !=
	// null) {
	// Tbl03QapMaster qapMaster = tbl14PrimemoverMaster_M.getTbl03QapMaster();
	// ComboBox comboBox = new ComboBox();
	// comboBox.setLabel(qapMaster.getName());
	// comboBox.setValue(qapMaster.getId());
	// models.add(comboBox);
	// }
	//
	// applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
	// ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models);
	// } catch (Exception e) {
	// log.error(e.getMessage(), e);
	// }
	// return applicationResponse;
	// }

	// @Override
	// public ApplicationResponse getQapForPump(Integer offerPumpId) {
	//
	// List<ComboBox> models = new ArrayList<>();
	// ApplicationResponse applicationResponse = new ApplicationResponse();
	// try {
	// Tbl26OfferPump tbl26OfferPump = (Tbl26OfferPump)
	// genericDao.getRecordById(Tbl26OfferPump.class, offerPumpId);
	// String pumpModel = tbl26OfferPump.getTbl28SelectedPump().getPumpModel();
	// List<Tbl02Modelmaster> modelmasters = (List<Tbl02Modelmaster>)
	// genericDao.findByParam(Tbl02Modelmaster.class, "pumpModel", pumpModel);
	// if (modelmasters.get(0).getTbl03QapMaster() != null &&
	// modelmasters.get(0).getTbl03QapMaster().getTbl90DocumentMaster() != null)
	// {
	// Tbl03QapMaster qapMaster = modelmasters.get(0).getTbl03QapMaster();
	// ComboBox comboBox = new ComboBox();
	// comboBox.setLabel(qapMaster.getName());
	// comboBox.setValue(qapMaster.getId());
	// models.add(comboBox);
	// }
	// applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
	// ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models);
	// } catch (Exception e) {
	// log.error(e.getMessage(), e);
	// }
	// return applicationResponse;
	//
	// }

	@Override
	public ApplicationResponse getSalesPerson() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		ComboBox comboBox = new ComboBox();

		try {
			Tbl52Usermaster tbl52Usermaster = dao.getSalesPerson();
			comboBox.setLabel(tbl52Usermaster.getUserName());
			comboBox.setValue(tbl52Usermaster.getLoginId());
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, comboBox);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getSalesPerson(Integer organisationId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<ComboBox> dtos = new ArrayList<>();
		try {
			List<Tbl52Usermaster> tbl52Usermasters = dao.getSalesPerson(organisationId);
			for (Tbl52Usermaster tbl52Usermaster : tbl52Usermasters) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(tbl52Usermaster.getUserName());
				comboBox.setValue(tbl52Usermaster.getUserName());
				dtos.add(comboBox);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getEmailIds() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String searchProperty = null;
			SearchCriteria searchCriteria = null;
			List<?> models = new ArrayList<>();
			applicationResponse = genericDao.getComboRecords(Tbl52Usermaster.class, "email", "email",
					GenericUtil.getConjuction(Tbl52Usermaster.class, searchCriteria));
			models = (List<?>) applicationResponse.getData();
			long total = applicationResponse.getTotal();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

/*	@Override
	public ApplicationResponse getStartingMethodfromWi() {

		// TODO Auto-generated method stub
		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<ComboBox> dtos = new ArrayList<>();
		try {
			List<String> models = dao.getStartingMethod();
			for (String method : models) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(method);
				comboBox.setValue(method);
				dtos.add(comboBox);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}*/

	@Override
	public ApplicationResponse getPrimeMoverBombo(Integer motorSeriesId,Double power) {
		List<ComboBox> models = new ArrayList<>();
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl14PrimemoverMaster> tbl14PrimemoverMasterList = dao.getPrimeMoverCombo(motorSeriesId,power);
			for (Tbl14PrimemoverMaster tbl14PrimemoverMaster : tbl14PrimemoverMasterList) {
				//Tbl14PrimemoverVoltage tbl14PrimemoverVoltage = (Tbl14PrimemoverVoltage) genericDao.getRecordsByParentId(Tbl14PrimemoverVoltage.class, "tbl14PrimemoverMaster.primemoverId", tbl14PrimemoverMaster.getPrimemoverId());
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(tbl14PrimemoverMaster.getVoltage());
				comboBox.setValue(tbl14PrimemoverMaster.getPrimemoverId());
				models.add(comboBox);
			}
			long total = models.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getMotorDescription(String motorSeriesId) {
		List<ComboBox> models = new ArrayList<>();
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl1401Motortype> tbl1401MotortypeList = dao.getMotorDescription(motorSeriesId);
			for (Tbl1401Motortype tbl14PrimemoverMaster : tbl1401MotortypeList) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(tbl14PrimemoverMaster.getDescription());
				comboBox.setValue(tbl14PrimemoverMaster.getId());
				models.add(comboBox);
			}
			long total = models.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}
	
	
	@Override
	public ApplicationResponse getPumpTypesWithAll() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<ComboBox> dtos = new ArrayList<>();
		try {
			ComboBox comboBox = new ComboBox();
			comboBox.setLabel("All");
			comboBox.setValue("All");
			dtos.add(comboBox);
			List<Tbl01Fantype> pumpTypeList = (List<Tbl01Fantype>) genericDao.getRecords(Tbl01Fantype.class);
			for (Tbl01Fantype tbl01Fantype : pumpTypeList) {
				comboBox = new ComboBox();
				comboBox.setLabel(tbl01Fantype.getSeries());
				comboBox.setValue(tbl01Fantype.getSeries());
				dtos.add(comboBox);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getModelPerformancesCombo() {
		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<ComboBox> dtos = new ArrayList<>();
		try {
			ComboBox comboBox = new ComboBox();
			dtos.add(comboBox);
			/*
			 * List<Tbl09Performancecurvemaster> modelPerf =
			 * (List<Tbl09Performancecurvemaster>)
			 * genericDao.getRecords(Tbl09Performancecurvemaster.class); for
			 * (Tbl09Performancecurvemaster tbl12modelperf : modelPerf) { comboBox = new
			 * ComboBox(); comboBox.setLabel(tbl12modelperf.getPerformanceCurveNo());
			 * comboBox.setValue(tbl12modelperf.getId()); dtos.add(comboBox); }
			 */
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	/*
	 * @Override public ApplicationResponse getIdentityCombo(Integer pumpModelId) {
	 * ApplicationResponse applicationResponse = new ApplicationResponse();
	 * List<ComboBox> dtos = new ArrayList<>(); try { ComboBox comboBox = new
	 * ComboBox();
	 * 
	 * List<String> identificationList = dao.getPerformanceDiaList(pumpModelId); for
	 * (String perfDia : identificationList) { comboBox = new ComboBox();
	 * comboBox.setLabel(perfDia); comboBox.setValue(perfDia); dtos.add(comboBox); }
	 * applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
	 * ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return applicationResponse; }
	 */

}

package com.velotech.fanselection.offer.service;

import java.io.ByteArrayInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lowagie.text.pdf.PdfReader;
import com.octomix.josson.Josson;
import com.velotech.fanselection.common.service.JsonService;
import com.velotech.fanselection.company.service.CompanyService;
import com.velotech.fanselection.company.utils.CompanyServiceUtil;
import com.velotech.fanselection.dtos.JsonUserInputDto;
import com.velotech.fanselection.dtos.OfferRevDto;
import com.velotech.fanselection.dtos.UserInputDto;
import com.velotech.fanselection.dxf.dao.DxfDao;
import com.velotech.fanselection.dxf.model.OfferDrawingPojo;
import com.velotech.fanselection.dxf.util.OfferDrawingBlock;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.ireportmodels.MocData;
import com.velotech.fanselection.ireportmodels.ModelMocDataSheet;
import com.velotech.fanselection.ireportmodels.ParameterValue;
import com.velotech.fanselection.ireportmodels.PerformanceChartCondition;
import com.velotech.fanselection.ireportmodels.PerformanceChartSheet;
import com.velotech.fanselection.ireportmodels.TechnicalDataSheet;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01ProductTypeMaster;
import com.velotech.fanselection.models.Tbl1401Motortype;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl23OfferMaster;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl23OfferRevTermsAndConditions;
import com.velotech.fanselection.models.Tbl23OfferShare;
import com.velotech.fanselection.models.Tbl25CustomerMaster;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl27OfferFanDp;
import com.velotech.fanselection.models.Tbl27RequirementsDp;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.models.Tbl28OfferFanSpecification;
import com.velotech.fanselection.models.Tbl28SelectedFan;
import com.velotech.fanselection.models.Tbl28SelectedFanBom;
import com.velotech.fanselection.models.Tbl28SelectedFanJsonValue;
import com.velotech.fanselection.models.Tbl28SelectedFanVariant;
import com.velotech.fanselection.models.Tbl28SelectedPricing;
import com.velotech.fanselection.models.Tbl28SelectedPrimemover;
import com.velotech.fanselection.models.Tbl28SelectedpricingDetails;
import com.velotech.fanselection.models.Tbl30OfferPriceAddon;
import com.velotech.fanselection.models.Tbl30OfferPriceFactor;
import com.velotech.fanselection.models.Tbl30Offerfanpriceaddon;
import com.velotech.fanselection.models.Tbl30Offerfanpricefactor;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl80SegmentMaster;
import com.velotech.fanselection.models.Tbl80TemplateMaster;
import com.velotech.fanselection.models.Tbl90DocumentMaster;
import com.velotech.fanselection.models.Tbl95TxSelectedPumps;
import com.velotech.fanselection.offer.dao.OfferDetailsDao;
import com.velotech.fanselection.offer.dto.OfferFanDpDto;
import com.velotech.fanselection.offer.dto.OfferTreeDto;
import com.velotech.fanselection.selection.models.SelectedCentrifugalFan;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.JFreeChartSvgRenderer;
import com.velotech.fanselection.utils.SoundUtil;
import com.velotech.fanselection.utils.VelotechUtil;

import net.minidev.json.JSONArray;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;

@Service
@Transactional
public class OfferDetailsServiceImpl implements OfferDetailsService {

	static Logger log = LogManager.getLogger(OfferDetailsServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private OfferDetailsDao dao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private DxfDao dxfdao;

	@Autowired
	private CompanyServiceUtil companyServiceUtil;

	@Autowired
	private HttpSession session;

	@Autowired
	private OfferDrawingBlock offerDrawingBlock;

	@Autowired
	private JsonService jsonService;

	/*
	 * @Autowired OfferDrawingPojo offerDrawing;
	 */

	@Override
	public ApplicationResponse updateOfferRevision(String requestPayload)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			OfferRevDto dto = new OfferRevDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, OfferRevDto.class);
			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, dto.getId());

			tbl23OfferRev.setOfferdate(dto.getOfferdate());
			tbl23OfferRev.setShowCoveringLetter(dto.getShowCoveringLetter());
			tbl23OfferRev.setShowDiscount(dto.getShowDiscount());
			tbl23OfferRev.setShowPrice(dto.getShowPrice());
			tbl23OfferRev.setShowPriceBreakUp(dto.getShowPriceBreakUp());
			tbl23OfferRev.setShowTagPrice(dto.getShowTagPrice());
			tbl23OfferRev.setCoverLetterContent(dto.getCoverLetterContent());
			tbl23OfferRev.setTbl80SegmentMaster(new Tbl80SegmentMaster(dto.getSegmentId()));
			tbl23OfferRev.setTbl25CustomerMaster(new Tbl25CustomerMaster(dto.getCustomerMasterId()));
			tbl23OfferRev.setCurrency(dto.getCurrency());
			tbl23OfferRev.setTbl28CompanyMaster(new Tbl28CompanyMaster(dto.getCompanyMasterId()));
			tbl23OfferRev.setTbl52UsermasterBySalesPerson(new Tbl52Usermaster(dto.getSalesPersonLoginId()));
			tbl23OfferRev.setConsultant(dto.getConsultant());
			tbl23OfferRev.setEndUser(dto.getEndUser());
			tbl23OfferRev.setProject(dto.getProject());
			tbl23OfferRev.setContractor(dto.getContractor());
			tbl23OfferRev.setSuppliedBy(dto.getSuppliedBy());
			tbl23OfferRev.setReference(dto.getReference());
			tbl23OfferRev.setEnquiryDetails(dto.getEnquiryDetails());
			tbl23OfferRev.setEnquiryDate(dto.getEnquiryDate());
			tbl23OfferRev.setTenderDate(dto.getTenderDate());
			tbl23OfferRev.setSubject(dto.getSubject());
			tbl23OfferRev.setPriceReqDate(dto.getPriceReqDate());

			genericDao.update(tbl23OfferRev);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.UPDATE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getOfferTree(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, offerRevId);
			String offerNoRev = tbl23OfferRev.getTbl23OfferMaster().getOfferNo() + "(" + tbl23OfferRev.getRev() + ")";

			List<Tbl26OfferFan> tbl26OfferFans = (List<Tbl26OfferFan>) genericDao
					.getRecordsByParentId(Tbl26OfferFan.class, "tbl23OfferRev.id", offerRevId);

			Object tree = getTreeData(offerNoRev, tbl26OfferFans);

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, tree);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private Object getTreeData(String offerNoRev, List<Tbl26OfferFan> tbl26OfferFans) {

		OfferTreeDto mainBranch = new OfferTreeDto();
		mainBranch.setText(offerNoRev);
		mainBranch.setExpanded(true);
		mainBranch.setIconCls("x-fa fa-briefcase blue");
		mainBranch.setCard("od-offerrevision");
		List<Object> mainsChildren = new ArrayList<>();
		try {
			List<Object> subChildren = new ArrayList<>();
			for (Tbl26OfferFan node : tbl26OfferFans) {

				OfferTreeDto branch = new OfferTreeDto();
				branch.setOfferFanId(node.getId());
				branch.setText(node.getTagNo());
				branch.setIconCls("x-fa fa-briefcase dark-grey");
				branch.setCard("od-offerfan");
				subChildren = getFansChildren(node);
				branch.setData(subChildren);
				mainsChildren.add(branch);
			}
			mainBranch.setData(mainsChildren);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mainBranch;
	}

	private List<Object> getFansChildren(Tbl26OfferFan node) {

		List<Object> mainsChildren = new ArrayList<>();
		try {

			OfferTreeDto leaf = new OfferTreeDto();
			leaf.setText("Selected Fan");
			leaf.setIconCls("x-fa fa-long-arrow-right dark-grey");
			leaf.setLeaf(true);
			leaf.setCard("od-selectedfan");
			mainsChildren.add(leaf);

			leaf = new OfferTreeDto();
			leaf.setText("Performance Chart");
			leaf.setIconCls("x-fa fa-long-arrow-right dark-grey");
			leaf.setLeaf(true);
			leaf.setCard("od-performancechart");
			mainsChildren.add(leaf);

//			leaf = new OfferTreeDto();
//			leaf.setText("Motor");
//			leaf.setIconCls("x-fa fa-long-arrow-right dark-grey");
//			leaf.setLeaf(true);
//			leaf.setCard("od-primemover");
//			mainsChildren.add(leaf);
			// else {
			// if (node.getTbl27RequirementsDp().getBorewellSize() > 7) {
			// leaf = new OfferTreeDto();
			// leaf.setText("Motor");
			// leaf.setIconCls("x-fa fa-long-arrow-right dark-grey");
			// leaf.setLeaf(true);
			// leaf.setCard("od-primemover");
			// mainsChildren.add(leaf);
			// }
			// }
//			leaf = new OfferTreeDto();
//			leaf.setText("Fan Sectional Drg.");
//			leaf.setIconCls("x-fa fa-long-arrow-right dark-grey");
//			leaf.setLeaf(true);
//			leaf.setCard("od-csd");
//			mainsChildren.add(leaf);
			/*
			 * leaf = new OfferTreeDto(); leaf.setText("Motor Sectional Drg.");
			 * leaf.setIconCls("x-fa fa-long-arrow-right dark-grey"); leaf.setLeaf(true);
			 * leaf.setCard("od-motordrawing"); mainsChildren.add(leaf);
			 */
//
//			leaf = new OfferTreeDto();
//			leaf.setText("Barefan GA");
//			leaf.setIconCls("x-fa fa-long-arrow-right dark-grey");
//			leaf.setLeaf(true);
//			leaf.setCard("od-barefanga");
//			mainsChildren.add(leaf);
//
//			leaf = new OfferTreeDto();
//			leaf.setText("Fanset GA");
//			leaf.setIconCls("x-fa fa-long-arrow-right dark-grey");
//			leaf.setLeaf(true);
//			leaf.setCard("od-fansetga");
//			mainsChildren.add(leaf);

//			leaf = new OfferTreeDto();
//			leaf.setText("BOM");
//			leaf.setIconCls("x-fa fa-long-arrow-right dark-grey");
//			leaf.setLeaf(true);
//			leaf.setCard("od-bom");
//			mainsChildren.add(leaf);

			leaf = new OfferTreeDto();
			leaf.setText("Data Sheet");
			leaf.setIconCls("x-fa fa-long-arrow-right dark-grey");
			leaf.setLeaf(true);
			leaf.setCard("od-datasheet");
			mainsChildren.add(leaf);

			/*
			 * leaf = new OfferTreeDto(); leaf.setText("Specification");
			 * leaf.setIconCls("x-fa fa-long-arrow-right dark-grey"); leaf.setLeaf(true);
			 * leaf.setCard("od-specification"); mainsChildren.add(leaf);
			 */

			/*
			 * leaf = new OfferTreeDto(); leaf.setText("QAP");
			 * leaf.setIconCls("x-fa fa-long-arrow-right dark-grey"); leaf.setLeaf(true);
			 * leaf.setCard("od-qap"); mainsChildren.add(leaf);
			 */

			/*
			 * leaf = new OfferTreeDto(); leaf.setText("User Input");
			 * leaf.setIconCls("x-fa fa-long-arrow-right dark-grey"); leaf.setLeaf(true);
			 * leaf.setCard("od-userinput"); mainsChildren.add(leaf);
			 */

			leaf = new OfferTreeDto();
			leaf.setText("Pricing");
			leaf.setIconCls("x-fa fa-long-arrow-right dark-grey");
			leaf.setLeaf(true);
			leaf.setCard("od-pricing");
			mainsChildren.add(leaf);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mainsChildren;
	}

	@Override
	public ApplicationResponse copyOffer(Integer offerRevisionId, String newRevision, String newofferNo) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			Tbl23OfferRev tbl23OfferRevOld = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class,
					offerRevisionId);

			Tbl23OfferMaster tbl23OfferMasterNew = new Tbl23OfferMaster();
			tbl23OfferMasterNew.setOfferNo(newofferNo);
			genericDao.save(tbl23OfferMasterNew);

			Tbl23OfferRev tbl23OfferRevNew = new Tbl23OfferRev();
			BeanUtils.copyProperties(tbl23OfferRevOld, tbl23OfferRevNew);
			tbl23OfferRevNew.setTbl23OfferMaster(tbl23OfferMasterNew);
			tbl23OfferRevNew.setRev(newRevision);
			genericDao.save(tbl23OfferRevNew);

			@SuppressWarnings("unchecked")
			List<Tbl26OfferFan> oldOfferFans = (List<Tbl26OfferFan>) genericDao
					.getRecordsByParentId(Tbl26OfferFan.class, "tbl23OfferRev.id", tbl23OfferRevOld.getId());
			copyOfferFansData(oldOfferFans, tbl23OfferRevNew);
			copyOfferRevChildsData(tbl23OfferRevOld, tbl23OfferRevNew);

			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.COPY_SUCCESS_MSG);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse copyOfferRevision(Integer oldOfferRevisionId, String newRevision) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			Tbl23OfferRev tbl23OfferRevOld = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class,
					oldOfferRevisionId);
			tbl23OfferRevOld.setActivetFlag(false);
			genericDao.update(tbl23OfferRevOld);

			Tbl23OfferRev tbl23OfferRevNew = new Tbl23OfferRev();
			BeanUtils.copyProperties(tbl23OfferRevOld, tbl23OfferRevNew);
			tbl23OfferRevNew.setRev(newRevision);
			tbl23OfferRevNew.setActivetFlag(true);
			genericDao.save(tbl23OfferRevNew);

			@SuppressWarnings("unchecked")
			List<Tbl26OfferFan> oldOfferFans = (List<Tbl26OfferFan>) genericDao
					.getRecordsByParentId(Tbl26OfferFan.class, "tbl23OfferRev.id", tbl23OfferRevOld.getId());
			copyOfferFansData(oldOfferFans, tbl23OfferRevNew);
			copyOfferRevChildsData(tbl23OfferRevOld, tbl23OfferRevNew);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.COPY_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse copyOfferFan(Integer offerFanId, String newTagNo) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl26OfferFan tbl26OfferFanOld = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			boolean copied = copyOfferFanData(tbl26OfferFanOld.getTbl23OfferRev(), tbl26OfferFanOld, newTagNo);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(copied,
					copied ? ApplicationConstants.COPY_SUCCESS_MSG : ApplicationConstants.COPY_FAIL_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private boolean copyOfferFansData(List<Tbl26OfferFan> oldOfferFans, Tbl23OfferRev tbl23OfferRev) {

		boolean ans = false;
		try {
			for (Tbl26OfferFan tbl26OfferFanOld : oldOfferFans) {

				copyOfferFanData(tbl23OfferRev, tbl26OfferFanOld, tbl26OfferFanOld.getTagNo());
			}
			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@SuppressWarnings("unchecked")
	private boolean copyOfferFanData(Tbl23OfferRev tbl23OfferRev, Tbl26OfferFan tbl26OfferFanOld, String newTagNo) {

		boolean ans = false;
		try {
			Tbl26OfferFan tbl26OfferFanNew = new Tbl26OfferFan();
			BeanUtils.copyProperties(tbl26OfferFanOld, tbl26OfferFanNew);
			tbl26OfferFanNew.setTbl23OfferRev(tbl23OfferRev);
			tbl26OfferFanNew.setTagNo(newTagNo);
			genericDao.save(tbl26OfferFanNew);

			int newOfferFanId = tbl26OfferFanNew.getId();

			Tbl27RequirementsDp tbl27RequirementsDp = tbl26OfferFanOld.getTbl27RequirementsDp();
			if (tbl27RequirementsDp != null) {
				Tbl27RequirementsDp tbl27RequirementsDpNew = new Tbl27RequirementsDp();
				BeanUtils.copyProperties(tbl27RequirementsDp, tbl27RequirementsDpNew);
				tbl27RequirementsDpNew.setTbl26OfferFan(new Tbl26OfferFan(newOfferFanId));
				genericDao.save(tbl27RequirementsDpNew);
			}

			/*
			 * Tbl28SelectedVertical tbl28SelectedVertical =
			 * tbl26OfferPumpOld.getTbl28SelectedVertical(); if (tbl28SelectedVertical !=
			 * null) { Tbl28SelectedVertical tbl28SelectedVerticalNew = new
			 * Tbl28SelectedVertical(); BeanUtils.copyProperties(tbl28SelectedVertical,
			 * tbl28SelectedVerticalNew); tbl28SelectedVerticalNew.setTbl26OfferPump(new
			 * Tbl26OfferPump(newOfferPumpId)); genericDao.save(tbl28SelectedVerticalNew); }
			 */

			Tbl28SelectedFan tbl28SelectedFan = tbl26OfferFanOld.getTbl28SelectedFan();
			if (tbl28SelectedFan != null) {
				Tbl28SelectedFan tbl28SelectedFanNew = new Tbl28SelectedFan();
				BeanUtils.copyProperties(tbl28SelectedFan, tbl28SelectedFanNew);
				tbl28SelectedFanNew.setTbl26OfferFan(new Tbl26OfferFan(newOfferFanId));

				genericDao.save(tbl28SelectedFanNew);
			}

			Tbl28SelectedPrimemover tbl28SelectedPrimemover = tbl26OfferFanOld.getTbl28SelectedPrimemover();
			if (tbl28SelectedPrimemover != null) {
				Tbl28SelectedPrimemover tbl28SelectedPrimemoverNew = new Tbl28SelectedPrimemover();
				BeanUtils.copyProperties(tbl28SelectedPrimemover, tbl28SelectedPrimemoverNew);
				tbl28SelectedPrimemoverNew.setTbl26OfferFan(new Tbl26OfferFan(newOfferFanId));
				genericDao.save(tbl28SelectedPrimemoverNew);
			}

			Tbl27OfferFanDp tbl27OfferFanDp = tbl26OfferFanOld.getTbl27OfferFanDp();
			if (tbl27OfferFanDp != null) {
				Tbl27OfferFanDp tbl27OfferFanDpNew = new Tbl27OfferFanDp();
				BeanUtils.copyProperties(tbl27OfferFanDp, tbl27OfferFanDpNew);
				tbl27OfferFanDpNew.setTbl26OfferFan(new Tbl26OfferFan(newOfferFanId));
				genericDao.save(tbl27OfferFanDpNew);
			}

			List<Tbl28SelectedFanVariant> tbl28SelectedFanVariant = (List<Tbl28SelectedFanVariant>) genericDao
					.getRecordsByParentId(Tbl28SelectedFanVariant.class, "tbl26OfferFan.id", tbl26OfferFanOld.getId());
			for (Tbl28SelectedFanVariant tbl28SelectedFanVariantData : tbl28SelectedFanVariant) {
				Tbl28SelectedFanVariant tbl28SelectedFanVariantNew = new Tbl28SelectedFanVariant();
				BeanUtils.copyProperties(tbl28SelectedFanVariantData, tbl28SelectedFanVariantNew);
				tbl28SelectedFanVariantNew.setTbl26OfferFan(new Tbl26OfferFan(newOfferFanId));
				genericDao.save(tbl28SelectedFanVariantNew);

			}

			List<Tbl30Offerfanpricefactor> tbl30OfferFanPriceFactor = (List<Tbl30Offerfanpricefactor>) genericDao
					.getRecordsByParentId(Tbl30Offerfanpricefactor.class, "tbl26OfferFan.id", tbl26OfferFanOld.getId());
			for (Tbl30Offerfanpricefactor tbl30OfferFanPriceFactorData : tbl30OfferFanPriceFactor) {
				Tbl30Offerfanpricefactor tbl30OfferFanPriceFactorNew = new Tbl30Offerfanpricefactor();
				BeanUtils.copyProperties(tbl30OfferFanPriceFactorData, tbl30OfferFanPriceFactorNew);
				tbl30OfferFanPriceFactorNew.setTbl26OfferFan(new Tbl26OfferFan(newOfferFanId));
				genericDao.save(tbl30OfferFanPriceFactorNew);

			}

			List<Tbl28SelectedPricing> tbl28SelectedPricings = (List<Tbl28SelectedPricing>) genericDao
					.getRecordsByParentId(Tbl28SelectedPricing.class, "tbl26OfferFan.id", tbl26OfferFanOld.getId());
			for (Tbl28SelectedPricing tbl28SelectedPricing : tbl28SelectedPricings) {
				Tbl28SelectedPricing tbl28SelectedPricingNew = new Tbl28SelectedPricing();
				BeanUtils.copyProperties(tbl28SelectedPricing, tbl28SelectedPricingNew);
				tbl28SelectedPricingNew.setTbl26OfferFan(new Tbl26OfferFan(newOfferFanId));
				genericDao.save(tbl28SelectedPricingNew);

				List<Tbl28SelectedpricingDetails> tbl28SelectedPricingDetails = (List<Tbl28SelectedpricingDetails>) genericDao
						.getRecordsByParentId(Tbl28SelectedpricingDetails.class, "tbl28SelectedPricing.id",
								tbl28SelectedPricing.getId());
				for (Tbl28SelectedpricingDetails tbl28SelectedPricingDetail : tbl28SelectedPricingDetails) {
					Tbl28SelectedpricingDetails tbl28SelectedPricingDetailsNew = new Tbl28SelectedpricingDetails();
					BeanUtils.copyProperties(tbl28SelectedPricingDetail, tbl28SelectedPricingDetailsNew);
					tbl28SelectedPricingDetailsNew.setTbl28SelectedPricing(tbl28SelectedPricingNew);
					genericDao.save(tbl28SelectedPricingDetailsNew);

				}
			}

			List<Tbl28OfferFanSpecification> tbl28OfferFanSpecifications = (List<Tbl28OfferFanSpecification>) genericDao
					.getRecordsByParentId(Tbl28OfferFanSpecification.class, "tbl26OfferFan.id",
							tbl26OfferFanOld.getId());
			for (Tbl28OfferFanSpecification tbl28OfferFanSpecification : tbl28OfferFanSpecifications) {
				Tbl28OfferFanSpecification tbl28OfferFanSpecificationNew = new Tbl28OfferFanSpecification();
				BeanUtils.copyProperties(tbl28OfferFanSpecification, tbl28OfferFanSpecificationNew);
				tbl28OfferFanSpecificationNew.setTbl26OfferFan(new Tbl26OfferFan(newOfferFanId));
				genericDao.save(tbl28OfferFanSpecificationNew);
			}

			List<Tbl30Offerfanpriceaddon> tbl30OfferFanPriceAddons = (List<Tbl30Offerfanpriceaddon>) genericDao
					.getRecordsByParentId(Tbl30Offerfanpriceaddon.class, "tbl26OfferFan.id", tbl26OfferFanOld.getId());
			for (Tbl30Offerfanpriceaddon tbl30Offerfanpriceaddon : tbl30OfferFanPriceAddons) {
				Tbl30Offerfanpriceaddon tbl30OfferFanPriceAddonNew = new Tbl30Offerfanpriceaddon();
				BeanUtils.copyProperties(tbl30Offerfanpriceaddon, tbl30OfferFanPriceAddonNew);
				tbl30OfferFanPriceAddonNew.setTbl26OfferFan(new Tbl26OfferFan(newOfferFanId));
				genericDao.save(tbl30OfferFanPriceAddonNew);
			}

			List<Tbl28SelectedFanBom> tbl28SelectedFanBoms = (List<Tbl28SelectedFanBom>) genericDao
					.getRecordsByParentId(Tbl28SelectedFanBom.class, "tbl26OfferFan.id", tbl26OfferFanOld.getId());
			for (Tbl28SelectedFanBom tbl28SelectedFanBom : tbl28SelectedFanBoms) {
				Tbl28SelectedFanBom tbl28SelectedFanBomNew = new Tbl28SelectedFanBom();
				BeanUtils.copyProperties(tbl28SelectedFanBom, tbl28SelectedFanBomNew);
				tbl28SelectedFanBomNew.setTbl26OfferFan(new Tbl26OfferFan(newOfferFanId));
				genericDao.save(tbl28SelectedFanBomNew);
			}
			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@SuppressWarnings("unchecked")
	private boolean copyOfferRevChildsData(Tbl23OfferRev tbl23OfferRevOld, Tbl23OfferRev tbl23OfferRevNew) {

		boolean ans = true;
		try {

			List<Tbl23OfferRevTermsAndConditions> tbl23OfferRevTermsAndConditionses = (List<Tbl23OfferRevTermsAndConditions>) genericDao
					.getRecordsByParentId(Tbl23OfferRevTermsAndConditions.class, "tbl23OfferRev.id",
							tbl23OfferRevOld.getId());
			for (Tbl23OfferRevTermsAndConditions tbl23OfferRevTermsAndConditions : tbl23OfferRevTermsAndConditionses) {
				Tbl23OfferRevTermsAndConditions tbl23OfferRevTermsAndConditionsNew = new Tbl23OfferRevTermsAndConditions();
				BeanUtils.copyProperties(tbl23OfferRevTermsAndConditions, tbl23OfferRevTermsAndConditionsNew);
				tbl23OfferRevTermsAndConditionsNew.setTbl23OfferRev(tbl23OfferRevNew);
				genericDao.save(tbl23OfferRevTermsAndConditionsNew);

			}

			List<Tbl30OfferPriceFactor> tbl30OfferPriceFactors = (List<Tbl30OfferPriceFactor>) genericDao
					.getRecordsByParentId(Tbl30OfferPriceFactor.class, "tbl23OfferRev.id", tbl23OfferRevOld.getId());
			for (Tbl30OfferPriceFactor tbl30OfferPriceFactor : tbl30OfferPriceFactors) {
				Tbl30OfferPriceFactor tbl30OfferPriceFactorNew = new Tbl30OfferPriceFactor();
				BeanUtils.copyProperties(tbl30OfferPriceFactor, tbl30OfferPriceFactorNew);
				tbl30OfferPriceFactorNew.setTbl23OfferRev(tbl23OfferRevNew);
				genericDao.save(tbl30OfferPriceFactorNew);
			}

			List<Tbl23OfferShare> tbl23OfferShares = (List<Tbl23OfferShare>) genericDao
					.getRecordsByParentId(Tbl23OfferShare.class, "tbl23OfferRev.id", tbl23OfferRevOld.getId());
			for (Tbl23OfferShare tbl23OfferShare : tbl23OfferShares) {
				Tbl23OfferShare tbl23OfferShareNew = new Tbl23OfferShare();
				BeanUtils.copyProperties(tbl23OfferShare, tbl23OfferShareNew);
				tbl23OfferShareNew.setTbl23OfferRev(tbl23OfferRevNew);
				genericDao.save(tbl23OfferShareNew);
			}

			List<Tbl30OfferPriceAddon> tbl30OfferPriceAddons = (List<Tbl30OfferPriceAddon>) genericDao
					.getRecordsByParentId(Tbl30OfferPriceAddon.class, "tbl23OfferRev.id", tbl23OfferRevOld.getId());
			for (Tbl30OfferPriceAddon tbl30OfferPriceAddon : tbl30OfferPriceAddons) {
				Tbl30OfferPriceAddon tbl30OfferPriceAddonNew = new Tbl30OfferPriceAddon();
				BeanUtils.copyProperties(tbl30OfferPriceAddon, tbl30OfferPriceAddonNew);
				tbl30OfferPriceAddonNew.setTbl23OfferRev(tbl23OfferRevNew);
				genericDao.save(tbl30OfferPriceAddonNew);
			}

		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public ApplicationResponse getRequirements(Integer offerFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			Tbl27RequirementsDp tbl27RequirementsDp = tbl26OfferFan.getTbl27RequirementsDp();

			JSONObject requirementsDp = new JSONObject();
			requirementsDp.put("Tag No", tbl26OfferFan.getTagNo());

			requirementsDp.put("Flow", tbl27RequirementsDp.getFlow() + tbl27RequirementsDp.getUomFlow());
			requirementsDp.put("Head", tbl27RequirementsDp.getHead() + tbl27RequirementsDp.getUomHead());
			requirementsDp.put("Pump Qty in System", tbl27RequirementsDp.getPumpQtyinSystem());
			requirementsDp.put("Qty", tbl27RequirementsDp.getQty());
			requirementsDp.put("Solid Concentration", tbl27RequirementsDp.getSolidConcentration());
			requirementsDp.put("Specific Gravity", tbl27RequirementsDp.getSpgr());
			requirementsDp.put("Series", tbl27RequirementsDp.getSeries());
			requirementsDp.put("Lost To", tbl26OfferFan.getLostTo());
			requirementsDp.put("Lost Reason", tbl26OfferFan.getReason());
			requirementsDp.put("Driver Sizing", tbl27RequirementsDp.getDriverSizing());
			requirementsDp.put("Phase", tbl27RequirementsDp.getPhase());
			requirementsDp.put("Selection Range", tbl27RequirementsDp.getSelectionRange());
//			requirementsDp.put("Constant", tbl27RequirementsDp.getConstant());
			requirementsDp.put("Service Factor", tbl27RequirementsDp.getServiceFactor());
//			if (tbl27RequirementsDp.getConstant().equals("Head"))
//				requirementsDp.put("Flow Tolerance Min", tbl27RequirementsDp.getFlowMissToleranceMin());
//			else
			requirementsDp.put("Head Tolerance Min", tbl27RequirementsDp.getHeadMissToleranceMin());

//			if (tbl27RequirementsDp.getDp_searchCriteria().equals("Actual Speed")) {
			requirementsDp.put("Speed Type", tbl27RequirementsDp.getDp_searchCriteria());
			requirementsDp.put("Actual Speed", tbl27RequirementsDp.getDp_speed());
//			}

			JSONObject data = new JSONObject();
			data.put("requirementsDp", requirementsDp);

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, data.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getSelectedFanDetails(Integer offerFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			Tbl27RequirementsDp tbl27RequirementsDp = tbl26OfferFan.getTbl27RequirementsDp();
			Tbl28SelectedFan tbl28SelectedFan = tbl26OfferFan.getTbl28SelectedFan();

			Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster = (Tbl01CentrifugalModelMaster) genericDao
					.getUniqueRecord(Tbl01CentrifugalModelMaster.class, "fanModel", tbl28SelectedFan.getFanModel());

			@SuppressWarnings("unchecked")
			List<Tbl28SelectedFanVariant> tbl28SelectedFanVariants = (List<Tbl28SelectedFanVariant>) genericDao
					.getRecordsByParentId(Tbl28SelectedFanVariant.class, "tbl26OfferFan.id", offerFanId);

			JsonObject selectedPumpJson = new JsonObject();
			selectedPumpJson.addProperty("Fan Model", tbl28SelectedFan.getFanModel());
			selectedPumpJson.addProperty("Series", tbl01CentrifugalModelMaster.getTbl01fantype().getFanType());
			selectedPumpJson.addProperty("Performance Curve No", tbl28SelectedFan.getPerformanceCurveNo());
			selectedPumpJson.addProperty("MOC Std", tbl28SelectedFan.getMocStd());
			selectedPumpJson.addProperty("Rotation", tbl28SelectedFan.getRotation());

			JsonObject selectedPumpValuesJson = new JsonObject();
			selectedPumpValuesJson.addProperty("Requested Flow",
					tbl27RequirementsDp.getFlow() + " " + tbl27RequirementsDp.getUomFlow());
			selectedPumpValuesJson.addProperty("Requested Pressure",
					tbl27RequirementsDp.getHead() + " " + tbl27RequirementsDp.getUomHead());

			selectedPumpValuesJson.addProperty("DutyPoint Flow",
					tbl28SelectedFan.getDpFlow() + " " + tbl27RequirementsDp.getUomFlow());
			selectedPumpValuesJson.addProperty("DutyPoint Pressure",
					tbl28SelectedFan.getDpPressure() + " " + tbl27RequirementsDp.getUomHead());
			selectedPumpValuesJson.addProperty("DutyPoint Power", tbl28SelectedFan.getDpPower() + " kW");
//			selectedPumpValuesJson.addProperty("DutyPoint Eff.",
//					velotechUtil.roundAvoid(Double.valueOf(tbl28SelectedFanValues.getEfficiencyDp()) * 100d, 2)
//							+ " %");

			/*
			 * JSONObject selectedPumpVariants = new JSONObject(); for
			 * (Tbl28SelectedPumpVariant tbl28SelectedPumpVariant :
			 * tbl28SelectedPumpVariants)
			 * selectedPumpVariants.put(tbl28SelectedPumpVariant.getVariant(),
			 * tbl28SelectedPumpVariant.getValue());
			 */

			Tbl14PrimemoverMaster tbl14PrimemoverMaster_M = dao.getPrimeMover(offerFanId);
			JsonObject primeMover = new JsonObject();
			if (tbl14PrimemoverMaster_M != null) {
				primeMover.addProperty("PrimeMover Model", tbl14PrimemoverMaster_M.getModelName());

				if (tbl14PrimemoverMaster_M.getTbl1401Motortype() != null) {
					primeMover.addProperty("Motor Series", tbl14PrimemoverMaster_M.getTbl1401Motortype().getSeries());
					primeMover.addProperty("Phase", tbl14PrimemoverMaster_M.getTbl1401Motortype().getPhase());
				}

				primeMover.addProperty("Power(Kw)", tbl14PrimemoverMaster_M.getPower());
				primeMover.addProperty("Power(Hp)", tbl14PrimemoverMaster_M.getPowerHp());

				primeMover.addProperty("Orientation", tbl14PrimemoverMaster_M.getMounting());
				primeMover.addProperty("Weight", tbl14PrimemoverMaster_M.getWeight());
				primeMover.addProperty("Manufacturer", tbl14PrimemoverMaster_M.getManufacturer());
				primeMover.addProperty("Prime Mover Type", "Submersible Borewell Motor");
				primeMover.addProperty("Frequency", tbl14PrimemoverMaster_M.getFrequency());
				primeMover.addProperty("Pole", tbl14PrimemoverMaster_M.getPole());
				primeMover.addProperty("Speed(RPM)", tbl14PrimemoverMaster_M.getSpeed());
				primeMover.addProperty("MOC", tbl14PrimemoverMaster_M.getMoc());
			}

			JSONObject data = new JSONObject();
			data.put("selectedPump", selectedPumpJson);
			data.put("selectedPumpValues", selectedPumpValuesJson);
			data.put("selectedPrimemover", primeMover);
			// data.put("selectedPumpVariants", selectedPumpVariants);

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, data.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getPrimeMoverDetails(Integer offerFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl14PrimemoverMaster tbl14PrimemoverMaster_M = dao.getPrimeMover(offerFanId);

			JsonObject primeMover = new JsonObject();
			if (tbl14PrimemoverMaster_M != null) {
				primeMover.addProperty("PrimeMover Model", tbl14PrimemoverMaster_M.getModelName());
				primeMover.addProperty("Motor Series", tbl14PrimemoverMaster_M.getTbl1401Motortype().getSeries());
				primeMover.addProperty("Power(Kw)", tbl14PrimemoverMaster_M.getPower());
				primeMover.addProperty("Power(Hp)", tbl14PrimemoverMaster_M.getPowerHp());
				primeMover.addProperty("Phase", tbl14PrimemoverMaster_M.getTbl1401Motortype().getPhase());
				primeMover.addProperty("Orientation", tbl14PrimemoverMaster_M.getMounting());
				primeMover.addProperty("Weight", tbl14PrimemoverMaster_M.getWeight());
				primeMover.addProperty("Manufacturer", tbl14PrimemoverMaster_M.getManufacturer());
				primeMover.addProperty("Prime Mover Type", "Submersible Borewell Motor");
				primeMover.addProperty("Frequency", tbl14PrimemoverMaster_M.getFrequency());
				primeMover.addProperty("Pole", tbl14PrimemoverMaster_M.getPole());
				primeMover.addProperty("Speed(RPM)", tbl14PrimemoverMaster_M.getSpeed());
				primeMover.addProperty("MOC", tbl14PrimemoverMaster_M.getMoc());
			}

			JSONObject data = new JSONObject();
			data.put("primeMover", primeMover);

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, data.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	/*
	 * @Override public ApplicationResponse getSelectedPumpQap(Integer offerPumpId)
	 * {
	 * 
	 * ApplicationResponse applicationResponse = new ApplicationResponse(); try {
	 * Tbl26OfferPump tbl26OfferPump = (Tbl26OfferPump)
	 * genericDao.getRecordById(Tbl26OfferPump.class, offerPumpId);
	 * Tbl28SelectedPump tbl28SelectedPump = tbl26OfferPump.getTbl28SelectedPump();
	 * 
	 * JSONObject selectedQap = new JSONObject(); selectedQap.put("qapId",
	 * tbl28SelectedPump.getQapId()); selectedQap.put("qapName",
	 * tbl28SelectedPump.getQapName());
	 * 
	 * JSONObject data = new JSONObject(); data.put("selectedQap", selectedQap);
	 * 
	 * applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
	 * ApplicationConstants.DATA_LOAD_SUCCESS_MSG, data.toString()); } catch
	 * (Exception e) { log.error(e.getMessage(), e); } return applicationResponse; }
	 */

	/*
	 * @Override public ApplicationResponse deleteSelectedPumpQap(Integer
	 * offerPumpId) {
	 * 
	 * ApplicationResponse applicationResponse = new ApplicationResponse(); try {
	 * 
	 * dao.deleteQapPricing(offerPumpId); Tbl26OfferPump tbl26OfferPump =
	 * (Tbl26OfferPump) genericDao.getRecordById(Tbl26OfferPump.class, offerPumpId);
	 * Tbl28SelectedPump tbl28SelectedPump = tbl26OfferPump.getTbl28SelectedPump();
	 * tbl28SelectedPump.setQapId(null); tbl28SelectedPump.setQapName(null);
	 * genericDao.update(tbl28SelectedPump); updatePricing(tbl26OfferPump);
	 * applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
	 * ApplicationConstants.DELETE_SUCCESS_MSG); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return applicationResponse; }
	 */

	@Override
	public ApplicationResponse deleteSelectedMotorQap(Integer offerFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			dao.deleteQapPricing(offerFanId);
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			Tbl28SelectedFan tbl28SelectedFan = tbl26OfferFan.getTbl28SelectedFan();
			genericDao.update(tbl28SelectedFan);
			updatePricing(tbl26OfferFan);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private boolean updatePricing(Tbl26OfferFan tbl26OfferFan) {

		boolean updated = false;
		try {
			updateOfferFanPricing(tbl26OfferFan);
			updateOfferRevPricing(tbl26OfferFan.getTbl23OfferRev());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return updated;
	}

	@SuppressWarnings("unchecked")
	private boolean updateOfferFanPricing(Tbl26OfferFan tbl26OfferFan) {

		boolean updated = false;
		try {
			List<Tbl28SelectedPricing> tbl28SelectedPricings = (List<Tbl28SelectedPricing>) genericDao
					.getRecordsByParentId(Tbl28SelectedPricing.class, "tbl26OfferFan.id", tbl26OfferFan.getId());

			double lineTotal = tbl28SelectedPricings.stream().mapToDouble(model -> model.getSubTotal()).sum();
			double addonTotal = tbl26OfferFan.getAddonTotal() == null ? 0 : tbl26OfferFan.getAddonTotal();
			tbl26OfferFan.setLineTotal(lineTotal);
			tbl26OfferFan.setTotal(addonTotal + lineTotal);

			genericDao.update(tbl26OfferFan);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return updated;
	}

	@SuppressWarnings("unchecked")
	private boolean updateOfferRevPricing(Tbl23OfferRev tbl23OfferRev) {

		boolean updated = false;
		try {
			List<Tbl26OfferFan> tbl26OfferFans = (List<Tbl26OfferFan>) genericDao
					.getRecordsByParentId(Tbl26OfferFan.class, "tbl23OfferRev.id", tbl23OfferRev.getId());

			double lineTotal = tbl26OfferFans.stream().mapToDouble(model -> model.getTotal()).sum();
			double addonTotal = tbl23OfferRev.getAddonTotal() == null ? 0 : tbl23OfferRev.getAddonTotal();
			tbl23OfferRev.setLineTotal(lineTotal);
			tbl23OfferRev.setOfferTotal(addonTotal + lineTotal);

			genericDao.update(tbl23OfferRev);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return updated;
	}

	// @Override
	// public ApplicationResponse saveSelectedPumpQap(Integer offerPumpId,
	// Integer motorQapId, Integer pumpQapId) {
	//
	// ApplicationResponse applicationResponse = new ApplicationResponse();
	// try {
	// // delete previously selected QAP
	// dao.deleteQapPricing(offerPumpId);
	//
	// Tbl26OfferPump tbl26OfferPump = (Tbl26OfferPump)
	// genericDao.getRecordById(Tbl26OfferPump.class,
	// offerPumpId);
	// Tbl28SelectedPump tbl28SelectedPump =
	// tbl26OfferPump.getTbl28SelectedPump();
	// Tbl27RequirementsDp tbl27RequirementsDp =
	// tbl26OfferPump.getTbl27RequirementsDp();
	//
	// // update selected pump set new QAP
	// if (pumpQapId != null) {
	// Tbl03QapMaster tbl03QapMaster = (Tbl03QapMaster)
	// genericDao.getRecordById(Tbl03QapMaster.class,
	// pumpQapId);
	// tbl28SelectedPump.setQapId(tbl03QapMaster.getId());
	// tbl28SelectedPump.setQapName(tbl03QapMaster.getName());
	// }
	// // update selected pump set new Motor QAP
	// if (motorQapId != null) {
	// Tbl03QapMaster tbl03QapMasterForMotor = (Tbl03QapMaster)
	// genericDao.getRecordById(Tbl03QapMaster.class,
	// motorQapId);
	// tbl28SelectedPump.setMotorQapId(tbl03QapMasterForMotor.getId());
	// }
	// genericDao.update(tbl28SelectedPump);
	// List<Tbl03QapPrice> tbl03QapPrices = dao.getQapPrices(pumpQapId,
	// tbl28SelectedPump.getPumpModel());
	// Tbl28SelectedPricing tbl28SelectedPricing = new
	// Tbl28SelectedPricing();
	// tbl28SelectedPricing.setTbl26OfferPump(tbl26OfferPump);
	// tbl28SelectedPricing.setSorting(1);
	// tbl28SelectedPricing.setItemName("QAP");
	// tbl28SelectedPricing.setMake(velotechUtil.getCompany());
	// tbl28SelectedPricing.setQty(tbl27RequirementsDp.getQty());
	// tbl28SelectedPricing.setSource("InHouse");
	// tbl28SelectedPricing.setDiscount(0d);
	// tbl28SelectedPricing.setMargin(0d);
	// tbl28SelectedPricing.setScope("Yes");
	// tbl28SelectedPricing.setGroupClass("QAP");
	// tbl28SelectedPricing.setApplyFactor(true);
	// tbl28SelectedPricing.setTotalOfferFactor(tbl26OfferPump.getTbl23OfferRev().getFactorTotal());
	// tbl28SelectedPricing.setRecordType("manual");
	// if (!tbl03QapPrices.isEmpty()) {
	// tbl28SelectedPricing.setDescription(tbl03QapPrices.get(0).getTbl03QapMaster().getName());
	// tbl28SelectedPricing.setArticleNo(tbl03QapPrices.get(0).getTbl03QapMaster().getArticleNo());
	// tbl28SelectedPricing.setPricePerQty(tbl03QapPrices.get(0).getPrice()
	// != null ? tbl03QapPrices.get(0).getPrice().doubleValue() : 0d);
	// } /*
	// * else {
	// * tbl28SelectedPricing.setDescription(tbl03QapMaster.getName())
	// * ;
	// * tbl28SelectedPricing.setArticleNo(tbl03QapMaster.getArticleNo
	// * ());
	// * tbl28SelectedPricing.setPricePerQty(tbl03QapMaster.getPrice()
	// * != null ? tbl03QapMaster.getPrice().doubleValue() : 0d); }
	// */
	// tbl28SelectedPricing.setSubTotal(Double.valueOf(tbl27RequirementsDp.getQty())
	// * tbl28SelectedPricing.getPricePerQty());
	// genericDao.save(tbl28SelectedPricing);
	// updatePricing(tbl26OfferPump);
	// applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
	// ApplicationConstants.INSERT_SUCCESS_MSG);
	// } catch (Exception e) {
	// log.error(e.getMessage(), e);
	// }
	// return applicationResponse;
	// }

	/*
	 * @Override public ApplicationResponse saveSelectedPumpQap(Integer offerPumpId,
	 * Integer qapId) {
	 * 
	 * ApplicationResponse applicationResponse = new ApplicationResponse(); try { //
	 * delete previously selected QAP dao.deleteQapPricing(offerPumpId);
	 * 
	 * Tbl26OfferPump tbl26OfferPump = (Tbl26OfferPump)
	 * genericDao.getRecordById(Tbl26OfferPump.class, offerPumpId);
	 * Tbl28SelectedPump tbl28SelectedPump = tbl26OfferPump.getTbl28SelectedPump();
	 * Tbl27RequirementsDp tbl27RequirementsDp =
	 * tbl26OfferPump.getTbl27RequirementsDp(); Tbl03QapMaster tbl03QapMaster =
	 * (Tbl03QapMaster) genericDao.getRecordById(Tbl03QapMaster.class, qapId);
	 * 
	 * // update selected pump set new QAP
	 * tbl28SelectedPump.setQapId(tbl03QapMaster.getId());
	 * tbl28SelectedPump.setQapName(tbl03QapMaster.getName());
	 * genericDao.update(tbl28SelectedPump);
	 * 
	 * //List<Tbl03QapPrice> tbl03QapPrices = dao.getQapPrices(qapId,
	 * tbl28SelectedPump.getPumpModel()); Tbl28SelectedPricing tbl28SelectedPricing
	 * = new Tbl28SelectedPricing();
	 * tbl28SelectedPricing.setTbl26OfferPump(tbl26OfferPump);
	 * tbl28SelectedPricing.setSorting(1); tbl28SelectedPricing.setItemName("QAP");
	 * tbl28SelectedPricing.setMake(velotechUtil.getCompany()); //
	 * tbl28SelectedPricing.setQty(Integer.parseInt(tbl27RequirementsDp.getQty()));
	 * tbl28SelectedPricing.setSource("InHouse");
	 * tbl28SelectedPricing.setDiscount(0d); tbl28SelectedPricing.setMargin(0d);
	 * tbl28SelectedPricing.setScope("Yes");
	 * tbl28SelectedPricing.setGroupClass("QAP");
	 * tbl28SelectedPricing.setApplyFactor(true);
	 * tbl28SelectedPricing.setTotalOfferFactor(tbl26OfferPump.getTbl23OfferRev().
	 * getFactorTotal()); tbl28SelectedPricing.setRecordType("manual"); //
	 * tbl28SelectedPricing.setQtyUom("Nos"); if (!tbl03QapPrices.isEmpty()) {
	 * tbl28SelectedPricing.setDescription(tbl03QapPrices.get(0).getTbl03QapMaster()
	 * .getName());
	 * tbl28SelectedPricing.setArticleNo(tbl03QapPrices.get(0).getTbl03QapMaster().
	 * getArticleNo()); tbl28SelectedPricing.setPricePerQty(
	 * tbl03QapPrices.get(0).getPrice() != null ?
	 * tbl03QapPrices.get(0).getPrice().doubleValue() : 0d); } else {
	 * tbl28SelectedPricing.setDescription(tbl03QapMaster.getName());
	 * tbl28SelectedPricing.setArticleNo(tbl03QapMaster.getArticleNo());
	 * tbl28SelectedPricing.setPricePerQty( tbl03QapMaster.getPrice() != null ?
	 * tbl03QapMaster.getPrice().doubleValue() : 0d); } tbl28SelectedPricing
	 * .setSubTotal(Double.valueOf(tbl27RequirementsDp.getQty()) *
	 * tbl28SelectedPricing.getPricePerQty());
	 * genericDao.save(tbl28SelectedPricing); updatePricing(tbl26OfferPump);
	 * applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
	 * ApplicationConstants.INSERT_SUCCESS_MSG); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return applicationResponse; }
	 */

	@Override
	public ApplicationResponse checkIsSaveFanQap(Integer offerFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			Tbl28SelectedFan tbl28SelectedFan = tbl26OfferFan.getTbl28SelectedFan();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, true);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse checkIsSaveMotorQap(Integer offerFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			Tbl28SelectedFan tbl28SelectedFan = tbl26OfferFan.getTbl28SelectedFan();

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, true);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	/*
	 * @Override public ApplicationResponse showSelectedPumpQap(Integer offerPumpId,
	 * Integer qapId) {
	 * 
	 * Tbl03QapMaster tbl03QapMaster = new Tbl03QapMaster(); ApplicationResponse
	 * applicationResponse = new ApplicationResponse(); try { String
	 * documentContextPath = ""; String documentRealPath =
	 * velotechUtil.getCompanyDocumentPath(); String userRealPath =
	 * velotechUtil.getUserRealPath(); boolean fileExists = false; if (qapId !=
	 * null) tbl03QapMaster = (Tbl03QapMaster)
	 * genericDao.getRecordById(Tbl03QapMaster.class, qapId);
	 * 
	 * if (tbl03QapMaster.getTbl90DocumentMaster() != null) { File srcFile = new
	 * File(documentRealPath,
	 * tbl03QapMaster.getTbl90DocumentMaster().getFileName()); File dstFile = new
	 * File(userRealPath, tbl03QapMaster.getTbl90DocumentMaster().getFileName()); if
	 * (srcFile.exists()) { documentContextPath = velotechUtil.getUserContextPath();
	 * velotechUtil.copyfile(srcFile, dstFile); documentContextPath =
	 * documentContextPath + tbl03QapMaster.getTbl90DocumentMaster().getFileName();
	 * fileExists = true; }
	 * 
	 * }
	 * 
	 * String message = fileExists ? ApplicationConstants.DATA_LOAD_SUCCESS_MSG :
	 * ApplicationConstants.FILE_NOT_EXISTS_MSG; applicationResponse =
	 * ApplicationResponseUtil.getResponseToGetData(fileExists, message,
	 * documentContextPath); } catch (Exception e) { log.error(e.getMessage(), e); }
	 * return applicationResponse; }
	 */

	// @Override
	// public ApplicationResponse showSelectedPumpQap(Integer offerPumpId,
	// Integer qapId) {
	//
	// ApplicationResponse applicationResponse = new ApplicationResponse();
	// try {
	// String documentContextPath = velotechUtil.getUserContextPath();
	// String documentRealPath = velotechUtil.getCompanyQapPath();
	// String userRealPath = velotechUtil.getUserRealPath();
	// boolean fileExists = false;
	// Tbl03QapMaster tbl03QapMaster = (Tbl03QapMaster)
	// genericDao.getRecordById(Tbl03QapMaster.class, qapId);
	// if (tbl03QapMaster.getFileName() != null) {
	// File srcFile = new File(documentRealPath, tbl03QapMaster.getFileName());
	// File dstFile = new File(userRealPath, tbl03QapMaster.getFileName());
	// if (srcFile.exists()) {
	// velotechUtil.copyfile(srcFile, dstFile);
	// documentContextPath = documentContextPath + tbl03QapMaster.getFileName();
	// fileExists = true;
	// }
	// }
	// session.setAttribute("QAPPDF", documentRealPath +
	// tbl03QapMaster.getFileName());
	// String message = fileExists ? ApplicationConstants.DATA_LOAD_SUCCESS_MSG
	// : ApplicationConstants.FILE_NOT_EXISTS_MSG;
	// applicationResponse =
	// ApplicationResponseUtil.getResponseToGetData(fileExists, message,
	// documentContextPath);
	// } catch (Exception e) {
	// log.error(e.getMessage(), e);
	// }
	// return applicationResponse;
	// }

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public ApplicationResponse getBarePumpDrawing(Integer offerPumpId,
	 * boolean download) {
	 * 
	 * ApplicationResponse applicationResponse = new ApplicationResponse(); try {
	 * 
	 * OfferDrawingPojo offerDrawing = new OfferDrawingPojo(); String path = "";
	 * Tbl26OfferPump tbl26OfferPump = (Tbl26OfferPump)
	 * genericDao.getRecordById(Tbl26OfferPump.class, offerPumpId); Tbl23OfferRev
	 * tbl23OfferRev = tbl26OfferPump.getTbl23OfferRev();
	 * offerDrawing.setTbl23OfferRev(tbl23OfferRev); Tbl28SelectedPump
	 * tbl28SelectedPump = new Tbl28SelectedPump();
	 * 
	 * Tbl28SelectedPumpJsonValue tbl28SolarSelectedPumpJsonValue =
	 * tbl26OfferPump.getTbl28SelectedPumpJsonValue(); JSONObject sourceData = new
	 * JSONObject(tbl28SolarSelectedPumpJsonValue.getJsonText());
	 * 
	 * Josson josson = Josson.fromJsonString(sourceData.toString());
	 * offerDrawing.setJosson(josson);
	 * 
	 * Tbl02Modelmaster tbl02Modelmaster = new Tbl02Modelmaster(); if
	 * (tbl26OfferPump.getTbl28SelectedPump() != null) tbl28SelectedPump =
	 * tbl26OfferPump.getTbl28SelectedPump();
	 * 
	 * List<Tbl02Modelmaster> tbl02Modelmasters = (List<Tbl02Modelmaster>)
	 * genericDao .findByParam(Tbl02Modelmaster.class, "pumpModel",
	 * tbl28SelectedPump.getPumpModel()); tbl02Modelmaster =
	 * tbl02Modelmasters.size() > 0 ? tbl02Modelmasters.get(0) : null;
	 * 
	 * Tbl14PrimemoverMaster tbl14PrimemoverMaster = dxfdao
	 * .getPrimeMover(tbl26OfferPump.getTbl28SelectedPrimemover());
	 * 
	 * Tbl13Barepumpga tbl13Barepumpga = dxfdao.getBarePumpga(tbl02Modelmaster,
	 * Integer.parseInt(tbl28SelectedPump.getStage()),
	 * tbl28SelectedPump.getMocStd());
	 * 
	 * Tbl80TemplateMaster tbl80TemplateMaster = new Tbl80TemplateMaster(); if
	 * (tbl13Barepumpga != null) { tbl80TemplateMaster =
	 * tbl13Barepumpga.getTbl80TemplateMaster();
	 * offerDrawing.setTbl13Barepumpga(tbl13Barepumpga);
	 * offerDrawing.setTbl26OfferPump(tbl26OfferPump);
	 * offerDrawing.setTbl80TemplateMaster(tbl80TemplateMaster);
	 * offerDrawing.setTbl14PrimemoverMaster(tbl14PrimemoverMaster);
	 * offerDrawing.setDrawingNumber(tbl13Barepumpga.getDrgNo());
	 * 
	 * if (download) offerDrawing.setDownload(true);
	 * 
	 * offerDrawing.setPdf(true);
	 * offerDrawingBlock.getGeneratedDxfViewAspose(offerDrawing);
	 * 
	 * if (offerDrawing.isCheckPath()) { if (offerDrawing.isDownload()) path =
	 * offerDrawing.getZipPath(); else path = offerDrawing.getPath(); }
	 * applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
	 * ApplicationConstants.DATA_LOAD_SUCCESS_MSG, path); } else {
	 * applicationResponse = ApplicationResponseUtil.getResponseToGetData(false,
	 * ApplicationConstants.FILE_NOT_EXISTS_MSG, path); } } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return applicationResponse; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getCsdDrawing(Integer offerFanId, boolean download) {

		ApplicationResponse applicationResponse = new ApplicationResponse();

		try {
			String path = null;

			OfferDrawingPojo offerDrawing = new OfferDrawingPojo();
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			Tbl23OfferRev tbl23OfferRev = tbl26OfferFan.getTbl23OfferRev();
			offerDrawing.setTbl23OfferRev(tbl23OfferRev);
			Tbl28SelectedFan tbl28SelectedFan = tbl26OfferFan.getTbl28SelectedFan();

			Tbl28SelectedFanJsonValue tbl28SolarSelectedPumpJsonValue = tbl26OfferFan.getTbl28SelectedFanJsonValue();
			JSONObject sourceData = new JSONObject(tbl28SolarSelectedPumpJsonValue.getJsonText());

			Josson josson = Josson.fromJsonString(sourceData.toString());
			offerDrawing.setJosson(josson);

			if (tbl28SelectedFan != null) {

				List<Tbl01CentrifugalModelMaster> tbl01CentrifugalModelMasters = (List<Tbl01CentrifugalModelMaster>) genericDao
						.findByParam(Tbl01CentrifugalModelMaster.class, "fanModel", tbl28SelectedFan.getFanModel());
				Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster = tbl01CentrifugalModelMasters.size() > 0
						? tbl01CentrifugalModelMasters.get(0)
						: null;
				/*
				 * Tbl18Csd tbl18Csd = dxfdao.getCsd(tbl02Modelmaster,
				 * Integer.parseInt(tbl28SelectedPump.getStage()),
				 * tbl28SelectedPump.getMocStd());
				 */
				Tbl80TemplateMaster tbl80TemplateMaster = new Tbl80TemplateMaster();
				/*
				 * if (tbl18Csd.getTbl80TemplateMaster() != null) tbl80TemplateMaster =
				 * tbl18Csd.getTbl80TemplateMaster();
				 */

				offerDrawing.setTbl26OfferFan(tbl26OfferFan);
				offerDrawing.setTbl23OfferRev(tbl23OfferRev);
				offerDrawing.setDRW_TYPE("CSD");
				offerDrawing.setTbl01CentrifugalModelMaster(tbl01CentrifugalModelMaster);
				// offerDrawing.setTbl18Csd(tbl18Csd);
				offerDrawing.setTbl80TemplateMaster(tbl80TemplateMaster);
				// offerDrawing.setDrawingNumber(tbl18Csd.getDrgNo());
				offerDrawing.setMocDatas(getPumpBom(offerFanId));

				if (download)
					offerDrawing.setDownload(true);
				offerDrawing.setPdf(true);

				offerDrawingBlock.getGeneratedDxfViewAspose(offerDrawing);

				if (offerDrawing.isCheckPath()) {
					if (offerDrawing.isDownload())
						path = offerDrawing.getZipPath();
					else
						path = offerDrawing.getPath();
				}

				applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
						ApplicationConstants.DATA_LOAD_SUCCESS_MSG, path);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<MocData> getPumpBom(Integer offerFanId) {

		List<MocData> mocDatas = new ArrayList<>();
		try {

			/*
			 * List<Tbl28SelectedPumpBom> selectedPumpBoms = (List<Tbl28SelectedPumpBom>)
			 * genericDao.getRecordsByParentId(Tbl28SelectedPumpBom.class,
			 * "tbl26OfferPump.id", offerPumpId);
			 */
			List<Tbl01ProductTypeMaster> productTypeMasters = (List<Tbl01ProductTypeMaster>) genericDao
					.findByParam(Tbl01ProductTypeMaster.class, "productTypeDescription", "Fan");

			List<Tbl28SelectedFanBom> selectedFanBoms = dao.getSelectedBomForDrawing(offerFanId,
					productTypeMasters.get(0).getId());
			for (Tbl28SelectedFanBom tbl28SelectedFanBom : selectedFanBoms) {
				MocData mocData = new MocData();
				mocData.setItemNo(tbl28SelectedFanBom.getItemNo());
				mocData.setDescription(tbl28SelectedFanBom.getDescription());
				mocData.setQty(tbl28SelectedFanBom.getQty());
				mocData.setQtyUom(tbl28SelectedFanBom.getQtyUom());
				mocData.setMaterialDescription(tbl28SelectedFanBom.getMaterialDescription());
				mocDatas.add(mocData);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mocDatas;
	}

	@SuppressWarnings("unchecked")

	@Override
	public ApplicationResponse getDataSheet(Integer offerPumpId) {

		ApplicationResponse applicationResponse = null;
		try {
			JSONObject jo = new JSONObject();

			String path = "false";
			Random randomGenerator = new Random();
			int var = randomGenerator.nextInt(999999);

			Tbl26OfferFan tbl26OfferPump = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerPumpId);

			Tbl23OfferRev tbl23OfferRev = tbl26OfferPump.getTbl23OfferRev();

			Tbl27RequirementsDp tbl27RequirementsDp = tbl26OfferPump.getTbl27RequirementsDp();

			Tbl14PrimemoverMaster tbl14PrimemoverMaster = null;
			if (tbl26OfferPump.getTbl28SelectedPrimemover() != null)
				tbl14PrimemoverMaster = (Tbl14PrimemoverMaster) genericDao.getRecordById(Tbl14PrimemoverMaster.class,
						tbl26OfferPump.getTbl28SelectedPrimemover().getPrimemoverId());

			Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster = (Tbl01CentrifugalModelMaster) genericDao
					.getUniqueRecord(Tbl01CentrifugalModelMaster.class, "fanModel",
							tbl26OfferPump.getTbl28SelectedFan().getFanModel());

			PerformanceChartCondition performanceChartCondition = new PerformanceChartCondition();
			performanceChartCondition.configureCondition(tbl26OfferPump);

			Tbl28SelectedFan tbl28SelectedFan = tbl26OfferPump.getTbl28SelectedFan();
			String selectedFanJson = tbl28SelectedFan.getSelectedFanJson();
			JSONObject selectedFanJsonObject = new JSONObject(selectedFanJson);

			JFreeChart chart = velotechUtil.getPerformanceChartImage(selectedFanJsonObject);

			TechnicalDataSheet technicalDataSheet = new TechnicalDataSheet(tbl26OfferPump, tbl23OfferRev,
					tbl14PrimemoverMaster, tbl01CentrifugalModelMaster);

			String companyLogo = tbl23OfferRev.getTbl28CompanyMaster().getLogo();
			String logoFileName = companyLogo != null ? companyLogo : "Logo.jpg";
			technicalDataSheet.setLogoPath(velotechUtil.getCompanyDocumentPath(logoFileName));

			List<Tbl01ProductTypeMaster> productTypePump = (List<Tbl01ProductTypeMaster>) genericDao
					.findByParam(Tbl01ProductTypeMaster.class, "productTypeDescription", "Fan");
//	  List<Tbl28SelectedPumpBom> tbl28SelectedPumpBoms = dao.getSelectedBomForDatasheet(tbl26OfferPump.getId(),productTypePump.get(0).getId());
			Tbl1401Motortype tbl1401Motortype = null;
			if(tbl14PrimemoverMaster != null) {
				 tbl1401Motortype = (Tbl1401Motortype) genericDao.getRecordById(Tbl1401Motortype.class,
						tbl14PrimemoverMaster.getTbl1401Motortype().getId());
			}
			

			List<Tbl01ProductTypeMaster> productTypeMotor = (List<Tbl01ProductTypeMaster>) genericDao
					.findByParam(Tbl01ProductTypeMaster.class, "productTypeDescription", "PrimeMover");
			// List<Tbl28SelectedPumpBom> tbl28SelectedPumpBomsMotor = dao
			// .getSelectedBomForDatasheet(tbl26OfferPump.getId(),
			// productTypeMotor.get(0).getId());
			// technicalDataSheet.setMotorMocDataSheets(getSelectedPumpBom(tbl28SelectedPumpBomsMotor));

			List<TechnicalDataSheet> technicalDataSheets = new ArrayList<TechnicalDataSheet>();

			if (tbl14PrimemoverMaster != null) {
				technicalDataSheet.setMotorCableLength(tbl14PrimemoverMaster.getMotorCableLength());
			}
			

			Tbl28SelectedFanJsonValue tbl28SelectedPumpJsonValue = tbl26OfferPump.getTbl28SelectedFanJsonValue();
			velotechUtil.mergeJson(jo, new JSONObject(tbl28SelectedPumpJsonValue.getJsonText()));

//			JSONObject jsonObject = new JSONObject(technicalDataSheet);

			SoundUtil soundUtil = new SoundUtil();
			JSONObject soundData = soundUtil.calculateSound(tbl28SelectedFan.getDpFlow(),
					tbl27RequirementsDp.getUomFlow(), tbl28SelectedFan.getDpPressure(),
					tbl27RequirementsDp.getUomHead(), tbl28SelectedFan.getSpeed(),
					tbl01CentrifugalModelMaster.getBlades());

			// Access the data
			org.json.JSONArray soundArray = soundData.getJSONArray("soundDataArray");
			Double lpOverallInside = soundData.getDouble("lpOverallInside");
			Double lpOverallOutside = soundData.getDouble("lpOverallOutside");
			jo.put("soundData", soundData);

			jo.put("technicalDataSheet", new JSONObject(technicalDataSheet));

			System.out.println("jo finaldata:-" + jo);
			InputStream is = new ByteArrayInputStream(jo.toString().getBytes());

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(JsonQueryExecuterFactory.JSON_INPUT_STREAM, is);
			parameters.put("chart", new JFreeChartSvgRenderer(chart));

			technicalDataSheets.add(technicalDataSheet);

			String datasheetName = "";
			if (tbl01CentrifugalModelMaster.getDocumentMaster() != null
					&& !tbl01CentrifugalModelMaster.getDocumentMaster().equals(""))
				datasheetName = tbl01CentrifugalModelMaster.getDocumentMaster().getFileName();
			Tbl90DocumentMaster documentMaster;
			if (tbl01CentrifugalModelMaster.getDocumentMaster() != null) {
				documentMaster = (Tbl90DocumentMaster) genericDao.getRecordById(Tbl90DocumentMaster.class,
						tbl01CentrifugalModelMaster.getDocumentMaster().getId());
			} else
				documentMaster = (Tbl90DocumentMaster) genericDao.getRecordById(Tbl90DocumentMaster.class,
						tbl01CentrifugalModelMaster.getTbl01fantype().getDocumentMaster().getId());

			datasheetName = documentMaster.getFileName();
			velotechUtil.getJsonReport(datasheetName, technicalDataSheet.getOutputPdfName() + ".pdf", parameters);
			path = velotechUtil.getUserContextPath() + technicalDataSheet.getOutputPdfName() + ".pdf";

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, path);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;

	}

	private List<ParameterValue> setVariants(List<Tbl28SelectedFanVariant> tbl28SelectedFanVariants) {
		List<ParameterValue> ans = new ArrayList<>();
		try {
			for (Tbl28SelectedFanVariant tbl28SelectedFanVariants1 : tbl28SelectedFanVariants) {
				ans.add(new ParameterValue(tbl28SelectedFanVariants1.getVariant(),
						tbl28SelectedFanVariants1.getValue()));
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	private List<ModelMocDataSheet> getSelectedFanBom(List<Tbl28SelectedFanBom> tbl28SelectedFanBoms) {

		List<ModelMocDataSheet> ans = new ArrayList<>();
		try {

			for (Tbl28SelectedFanBom tbl28SelectedFanBom : tbl28SelectedFanBoms) {
				ModelMocDataSheet mocDataSheet = new ModelMocDataSheet(
						tbl28SelectedFanBom.getTbl26OfferFan().getTbl28SelectedFan().getMocStd(),
						tbl28SelectedFanBom.getDescription(), tbl28SelectedFanBom.getMaterialDescription(),
						tbl28SelectedFanBom.getMaterialDescription());
				ans.add(mocDataSheet);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return ans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse deleteOfferRevision(Integer offerRevisionId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class,
					offerRevisionId);
			Tbl23OfferMaster tbl23OfferMaster = tbl23OfferRev.getTbl23OfferMaster();
			List<Tbl23OfferRev> tbl23OfferRevs = (List<Tbl23OfferRev>) genericDao
					.getRecordsByParentId(Tbl23OfferRev.class, "tbl23OfferMaster.id", tbl23OfferMaster.getId());

			if (tbl23OfferRevs.size() > 1)
				genericDao.delete(tbl23OfferRev);
			else
				genericDao.delete(tbl23OfferMaster);

			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteOfferFan(Integer offerFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			genericDao.delete(tbl26OfferFan);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse renameOfferFanTagNo(Integer offerFanId, String newTagNo) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			tbl26OfferFan.setTagNo(newTagNo);
			genericDao.update(tbl26OfferFan);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.UPDATE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse transferOffer(Integer offerRevisionId, String userMasterId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl52Usermaster tbl52Usermaster = new Tbl52Usermaster();
			tbl52Usermaster.setLoginId(userMasterId);
			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class,
					offerRevisionId);
			tbl23OfferRev.setTbl52UsermasterByLoginId(tbl52Usermaster);
			genericDao.update(tbl23OfferRev);

			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.UPDATE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getQuotation(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			String outputFileName = offerRevId + "-" + new Date().toString().replaceAll(" ", "").replaceAll(":", "")
					+ ".pdf";

			String outputFileNamefinal = offerRevId + "Quotation" + "-"
					+ new Date().toString().replaceAll(" ", "").replaceAll(":", "") + ".pdf";
			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, offerRevId);
			Tbl28CompanyMaster tbl28CompanyMaster = tbl23OfferRev.getTbl28CompanyMaster();
			if (velotechUtil.getCompany().equals("CT01"))
				parameters.put("gstNo", tbl28CompanyMaster.getGstNo());

			parameters.put("offerId", offerRevId);
			parameters.put("logo", velotechUtil.getCompanyDocumentPath(tbl28CompanyMaster.getLogo()));
			parameters.put("companyName", tbl28CompanyMaster.getDocumentCompanyName());
			parameters.put("address1", tbl28CompanyMaster.getAddress());
			parameters.put("contact", tbl28CompanyMaster.getContactPhone());
			parameters.put("pincode", tbl28CompanyMaster.getPostalCode());
			parameters.put("email", tbl28CompanyMaster.getEmail());
			parameters.put("website", tbl28CompanyMaster.getWebsite());
			parameters.put("city", tbl28CompanyMaster.getCity());
			parameters.put("state", tbl28CompanyMaster.getState());
			parameters.put("country", tbl28CompanyMaster.getCountry());
			Double offerTotal = tbl23OfferRev.getOfferTotal();
			if (offerTotal.equals(0.0))
				parameters.put("offertotalinwords", "");
			else {
				String offerTotalWords = velotechUtil.convertNumberToWord(offerTotal);
				parameters.put("offertotalinwords", offerTotalWords);
			}

			List<Tbl52Usermaster> tbl52Usermasters = (List<Tbl52Usermaster>) genericDao
					.findByParam(Tbl52Usermaster.class, "userName", velotechUtil.getUsername());

			Tbl52Usermaster tbl52Usermaster = tbl52Usermasters.size() > 0 ? tbl52Usermasters.get(0) : null;

			parameters.put("user", tbl52Usermaster.getUserName());
			parameters.put("mobileNo", tbl52Usermaster.getMobileNo());

			List<PdfReader> pdfReaderList = new ArrayList<PdfReader>();

			if (tbl23OfferRev.getShowCoveringLetter() && !tbl23OfferRev.getCoverLetterContent().equals("")) {
				pdfReaderList = velotechUtil.getOfferCovertLetter(pdfReaderList, parameters);
			}

			velotechUtil.getReportJdbc("quatation.jasper", velotechUtil.getUserRealPath() + outputFileName, parameters);

			pdfReaderList.add(new PdfReader(velotechUtil.getUserRealPath() + outputFileName));

			String path = velotechUtil.mergePDF(pdfReaderList, outputFileNamefinal);

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, path);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getSummarySheet(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			dao.deleteProjectSummaryQuote(velotechUtil.getSessionId());
			List<Tbl26OfferFan> tbl26OfferFans = (List<Tbl26OfferFan>) genericDao
					.getRecordsByParentId(Tbl26OfferFan.class, "tbl23OfferRev.id", offerRevId);
			CompanyService companyService = companyServiceUtil.getCompanyService(velotechUtil.getCompany());
			companyService.getSummarySheetForOffers(tbl26OfferFans);

			Map<String, Object> parameters = new HashMap<String, Object>();

			Tbl28CompanyMaster companyMaster = (Tbl28CompanyMaster) genericDao.getRecordById(Tbl28CompanyMaster.class,
					tbl26OfferFans.get(0).getTbl23OfferRev().getTbl28CompanyMaster().getId());

			parameters.put("SUBREPORT_DIR", velotechUtil.getReportPath());
			parameters.put("showSmmaryQuotation", "Quotation");
			parameters.put("logo", velotechUtil.getCompanyDocumentPath(companyMaster.getLogo()));
			parameters.put("companyName", companyMaster.getDocumentCompanyName());
			parameters.put("address1", companyMaster.getAddress());
			parameters.put("contact", companyMaster.getContactPhone());
			parameters.put("pincode", companyMaster.getPostalCode());
			parameters.put("email", companyMaster.getEmail());
			parameters.put("website", companyMaster.getWebsite());
			parameters.put("city", companyMaster.getCity());
			parameters.put("state", companyMaster.getState());
			parameters.put("country", companyMaster.getCountry());
			parameters.put("offerRevId", offerRevId);
			parameters.put("showPrice", tbl26OfferFans.get(0).getTbl23OfferRev().getShowPrice());
			parameters.put("showPriceBreakUp", tbl26OfferFans.get(0).getTbl23OfferRev().getShowPriceBreakUp());
			parameters.put("sessionId", velotechUtil.getSessionId());
			String outputFileName = "ProjectSummary" + new Date().toString().replaceAll(" ", "").replaceAll(":", "")
					+ ".pdf";
			String outputFileNamefinal = "ProjectSummaryWithCoverLetter"
					+ new Date().toString().replaceAll(" ", "").replaceAll(":", "") + ".pdf";
			List<PdfReader> pdfReaderList = new ArrayList<PdfReader>();
			if (tbl26OfferFans.get(0).getTbl23OfferRev().getShowCoveringLetter()
					&& !tbl26OfferFans.get(0).getTbl23OfferRev().getCoverLetterContent().equals("")) {
				parameters.put("offerId", tbl26OfferFans.get(0).getTbl23OfferRev().getId());

				pdfReaderList = velotechUtil.getOfferCovertLetter(pdfReaderList, parameters);
			}
			velotechUtil.getReportJdbc("projectOffer.jasper", velotechUtil.getUserRealPath() + outputFileName,
					parameters);

			pdfReaderList.add(new PdfReader(velotechUtil.getUserRealPath() + outputFileName));

			String path = velotechUtil.mergePDF(pdfReaderList, outputFileNamefinal);

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, path);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getCosting(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			String outputFileName = offerRevId + "-" + new Date().toString().replaceAll(" ", "").replaceAll(":", "")
					+ ".pdf";
			String outputFileNamefinal = offerRevId + "Costing" + "-"
					+ new Date().toString().replaceAll(" ", "").replaceAll(":", "") + ".pdf";
			List<Tbl26OfferFan> tbl26OfferFans = (List<Tbl26OfferFan>) genericDao
					.getRecordsByParentId(Tbl26OfferFan.class, "tbl23OfferRev.id", offerRevId);
			List<Integer> offerFanIds = new ArrayList<>();
			for (Tbl26OfferFan tbl26OfferFan : tbl26OfferFans) {
				offerFanIds.add(tbl26OfferFan.getId());
			}

			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, offerRevId);
			Tbl28CompanyMaster companyMaster = tbl23OfferRev.getTbl28CompanyMaster();

			parameters.put("offerFanId", offerFanIds);
			parameters.put("logo", velotechUtil.getCompanyDocumentPath(companyMaster.getLogo()));
			parameters.put("companyName", companyMaster.getCompanyName());
			parameters.put("address1", companyMaster.getAddress());
			parameters.put("contact", companyMaster.getContactPhone());
			parameters.put("pincode", companyMaster.getPostalCode());
			parameters.put("email", companyMaster.getEmail());
			parameters.put("website", companyMaster.getWebsite());
			parameters.put("city", companyMaster.getCity());
			parameters.put("state", companyMaster.getState());
			parameters.put("country", companyMaster.getCountry());

			List<PdfReader> pdfReaderList = new ArrayList<PdfReader>();

			velotechUtil.getReportJdbc("costing.jasper", velotechUtil.getUserRealPath() + outputFileName, parameters);

			pdfReaderList.add(new PdfReader(velotechUtil.getUserRealPath() + outputFileName));

			String path = velotechUtil.mergePDF(pdfReaderList, outputFileNamefinal);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, path);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getPerformanceChart(Integer offerFanId) {
		ApplicationResponse applicationResponse = null;
		try {
			String toSend = "false";
			Tbl26OfferFan tbl26OfferFan = dao.getOfferFanForPerformanceChart(offerFanId);

			// new JSONObject(selectedFan.getSelectedFanJson().toString())
			Tbl28SelectedFan tbl28SelectedFan = tbl26OfferFan.getTbl28SelectedFan();

			String selectedFanJson = tbl28SelectedFan.getSelectedFanJson();

			JSONObject selectedFanJsonObject = new JSONObject(selectedFanJson);

			PerformanceChartCondition performanceChartCondition = new PerformanceChartCondition();
			performanceChartCondition.configureCondition(tbl26OfferFan);

			JFreeChart chart = velotechUtil.getPerformanceChartImage(selectedFanJsonObject);
			Tbl23OfferRev tbl23OfferRev = tbl26OfferFan.getTbl23OfferRev();
			Tbl14PrimemoverMaster tbl14PrimemoverMaster = null;
			if (tbl26OfferFan.getTbl28SelectedPrimemover() != null)
				tbl14PrimemoverMaster = (Tbl14PrimemoverMaster) genericDao.getRecordById(Tbl14PrimemoverMaster.class,
						tbl26OfferFan.getTbl28SelectedPrimemover().getPrimemoverId());
			Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster = (Tbl01CentrifugalModelMaster) genericDao
					.getUniqueRecord(Tbl01CentrifugalModelMaster.class, "fanModel",
							tbl26OfferFan.getTbl28SelectedFan().getFanModel());

			PerformanceChartSheet sheet = new PerformanceChartSheet(tbl26OfferFan, chart);
			// -------------------------------------------------------------------------
			Tbl28CompanyMaster companyMaster = tbl23OfferRev.getTbl28CompanyMaster();
			sheet.setLogoPath(velotechUtil.getCompanyDocumentPath(companyMaster.getLogo()));
			sheet.setCompanyName(companyMaster.getDocumentCompanyName());

			List<PerformanceChartSheet> sheetList = new ArrayList<PerformanceChartSheet>();
			sheetList.add(sheet);
			String pdfPath = velotechUtil.getUserRealPath() + sheet.getOutputPdfName() + ".pdf";
			System.out.println(pdfPath);
			new VelotechUtil().getReport(sheetList, "performanceChartSheet.jasper", sheet.getOutputPdfName() + ".pdf",
					new HashMap<String, Object>());

			if (new File(pdfPath).exists())
				toSend = velotechUtil.getUserContextPath() + sheet.getOutputPdfName() + ".pdf";

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, toSend);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse addPerformanceChartData(OfferFanDpDto dpDto) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			String flow = String.join(",", Arrays.toString(dpDto.getFlows())).replace("[", "").replace("]", "");
			String head = String.join(",", Arrays.toString(dpDto.getHeads())).replace("[", "").replace("]", "");
			String speed = String.join(",", Arrays.toString(dpDto.getSpeeds())).replace("[", "").replace("]", "");

			Tbl27OfferFanDp tbl27OfferFanDp = (Tbl27OfferFanDp) genericDao.getRecordById(Tbl27OfferFanDp.class,
					dpDto.getOfferFanId());
			if (tbl27OfferFanDp == null) {
				tbl27OfferFanDp = new Tbl27OfferFanDp();
				tbl27OfferFanDp.setTbl26OfferFan(new Tbl26OfferFan(dpDto.getOfferFanId()));
			}
			tbl27OfferFanDp.setFlow(flow);
			tbl27OfferFanDp.setHead(head);
			tbl27OfferFanDp.setSpeed(speed);
			genericDao.save(tbl27OfferFanDp);

			/*
			 * Tbl28SelectedFanValues tbl28SelectedFanValues = (Tbl28SelectedFanValues)
			 * genericDao .getRecordById(Tbl28SelectedFanValues.class,
			 * dpDto.getOfferFanId());
			 * tbl28SelectedFanValues.setRangeTypeUser(dpDto.getBase());
			 * tbl28SelectedFanValues.setQminRangeUser(dpDto.getMinflowCal());
			 * tbl28SelectedFanValues.setQmaxRangeUser(dpDto.getMaxflowCal());
			 * tbl28SelectedFanValues.setShowAppRange(dpDto.getShowAppRange());
			 * tbl28SelectedFanValues.setShowDutyPoints(dpDto.getShowDutyPoints());
			 * tbl28SelectedFanValues.setShowMinMaxDia(dpDto.getShowMinMaxDia());
			 * tbl28SelectedFanValues.setShowVfd(dpDto.getShowVfd());
			 * tbl28SelectedFanValues.setShowOverallEffGraph(dpDto.getShowOverallEffGraph())
			 * ; tbl28SelectedFanValues.setShowOverallPowerGraph(dpDto.
			 * getShowOverallPowerGraph());
			 * tbl28SelectedFanValues.setShowCurrentGraph(dpDto.getShowCurrentGraph());
			 * genericDao.save(tbl28SelectedFanValues);
			 */
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.INSERT_SUCCESS_MSG);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getAppRangeCalculation(OfferFanDpDto dpDto) {

		ApplicationResponse applicationResponse = new ApplicationResponse();

		try {
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			double[] tempHq = dpDto.getHeadPerPumpEq();
			double[] tempHq1 = new double[tempHq.length];
			double[] tempHq2 = new double[tempHq.length];
			for (int j = 0; j < tempHq.length; j++) {
				tempHq1[j] = tempHq[j];
				tempHq2[j] = tempHq[j];
			}
			List<Double> minList = velotechUtil.findRoots(tempHq1, dpDto.getMinHeadCal(), dpDto.getqMin(),
					dpDto.getqMax());
			List<Double> maxList = velotechUtil.findRoots(tempHq2, dpDto.getMaxHeadCal(), dpDto.getqMin(),
					dpDto.getqMax());

			dpDto.setMinflowCal(minList.size() > 0 ? Double.parseDouble(twoDForm.format(minList.get(0))) : 0d);
			dpDto.setMaxflowCal(maxList.size() > 0 ? Double.parseDouble(twoDForm.format(maxList.get(0))) : 0d);

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, "Calculation Successfully", dpDto);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getPrimeMoverDrawing(Integer offerPumpId, Boolean download) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		OfferDrawingPojo offerDrawing = new OfferDrawingPojo();
		try {
			String path = null;
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerPumpId);
			Tbl23OfferRev tbl23OfferRev = tbl26OfferFan.getTbl23OfferRev();

			Tbl28SelectedPrimemover selectedPrimemover = tbl26OfferFan.getTbl28SelectedPrimemover();
			if (selectedPrimemover != null) {
				Tbl14PrimemoverMaster primemoverMaster = (Tbl14PrimemoverMaster) genericDao
						.getRecordById(Tbl14PrimemoverMaster.class, selectedPrimemover.getPrimemoverId());
				Tbl1401Motortype motortype = null;
				if(primemoverMaster != null) {
					 motortype = (Tbl1401Motortype) genericDao.getRecordById(Tbl1401Motortype.class,
							primemoverMaster.getTbl1401Motortype().getId());
				}
				

				Tbl80TemplateMaster tbl80TemplateMaster = new Tbl80TemplateMaster();
				if (motortype.getTbl80TemplateMasterByCsdTemplateId() != null)
					tbl80TemplateMaster = motortype.getTbl80TemplateMasterByCsdTemplateId();

				offerDrawing.setTbl26OfferFan(tbl26OfferFan);
				offerDrawing.setTbl23OfferRev(tbl23OfferRev);
				offerDrawing.setDRW_TYPE("CSD");
				offerDrawing.setTbl80TemplateMaster(tbl80TemplateMaster);
				if (motortype != null) {
					offerDrawing.setDrawingNumber(motortype.getCsdDrawingNo());
				}
				
				offerDrawing.setMocDatas(getPrimeMoverpartList(offerPumpId));

				if (download)
					offerDrawing.setDownload(true);

				offerDrawing.setPdf(true);

				offerDrawingBlock.getGeneratedDxfViewAspose(offerDrawing);

				if (offerDrawing.isCheckPath()) {

					if (offerDrawing.isDownload())
						path = offerDrawing.getZipPath();
					else
						path = offerDrawing.getPath();
				}
				int var = new Random().nextInt(999999);
				List<PdfReader> pdfReaders = new ArrayList<>();
				pdfReaders
						.add(new PdfReader(velotechUtil.getUserRealPath() + offerDrawing.getOutputPdfName() + ".pdf"));

				offerDrawing.setTbl80TemplateMaster(tbl80TemplateMaster);
				// offerDrawingBlock.getGeneratedDxfView(offerDrawing);
				// if (tbl80TemplateMaster.getNewFormat())
				// offerDrawingBlock.getGeneratedDxfViewAspose(offerDrawing);
				// else
				// offerDrawingBlock.getGeneratedDxfView(offerDrawing);
				// if (offerDrawing.isCheckPath()) {
				//
				// if (offerDrawing.isDownload())
				// path = offerDrawing.getZipPath();
				// else
				// path = offerDrawing.getPath();
				// }
				// pdfReaders
				// .add(new PdfReader(velotechUtil.getUserRealPath() +
				// offerDrawing.getOutputPdfName() + ".pdf"));
				// pdfReaders.add(new
				// PdfReader(offerDrawingBlock.makeBomImage(offerDrawing)));
				// //to show partlist on seperate page

				String newPath = velotechUtil.mergePDF(pdfReaders, "PrimemoverDrg" + var + ".pdf");

				applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
						ApplicationConstants.DATA_LOAD_SUCCESS_MSG, newPath);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<MocData> getPrimeMoverpartList(int offerFanId) {

		List<MocData> mocDatas = new ArrayList<>();
		try {

			List<Tbl01ProductTypeMaster> productTypeMasters = (List<Tbl01ProductTypeMaster>) genericDao
					.findByParam(Tbl01ProductTypeMaster.class, "productTypeDescription", "PrimeMover");

			List<Tbl28SelectedFanBom> selectedFanBoms = dao.getSelectedBomForDrawing(offerFanId,
					productTypeMasters.get(0).getId());

			for (Tbl28SelectedFanBom tbl28SelectedFanBom : selectedFanBoms) {
				MocData mocData = new MocData();
				mocData.setItemNo(tbl28SelectedFanBom.getItemNo());
				mocData.setDescription(tbl28SelectedFanBom.getDescription());
				mocData.setQty(tbl28SelectedFanBom.getQty());
				mocData.setQtyUom(tbl28SelectedFanBom.getQtyUom());
				mocData.setMaterialDescription(tbl28SelectedFanBom.getMaterialDescription());
				mocDatas.add(mocData);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mocDatas;
	}

	@Override
	public ApplicationResponse saveJsonUserInput(String requestPayload, Integer offerFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			JsonUserInputDto jsonUserInputDto = new JsonUserInputDto();
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			jsonUserInputDto = mapper.readValue(requestPayload, JsonUserInputDto.class);
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			JSONObject rootJson = new JSONObject();
			Tbl28SelectedFanJsonValue tbl28SelectedPumpJsonValue = tbl26OfferFan.getTbl28SelectedFanJsonValue();

			velotechUtil.mergeJson(rootJson, new JSONObject(tbl28SelectedPumpJsonValue.getJsonText()));

			JSONObject jsonObject1 = new JSONObject();
			List<UserInputDto> ans = jsonUserInputDto.getUserInputDto();
			for (UserInputDto userInputDto : ans) {
				jsonObject1.put(userInputDto.getParameter(), userInputDto.getValue());
			}

			rootJson.put("userInput", jsonObject1);

			tbl28SelectedPumpJsonValue.setJsonText(rootJson.toString());
			genericDao.update(tbl28SelectedPumpJsonValue);

			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getJsonUserInput(Integer offerFanId) {
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);

			Tbl28SelectedFanJsonValue tbl28SelectedFanJsonValue = tbl26OfferFan.getTbl28SelectedFanJsonValue();
			JSONObject sourceData = new JSONObject(tbl28SelectedFanJsonValue.getJsonText());

			Josson josson = Josson.fromJsonString(sourceData.toString());
			JSONObject data = new JSONObject();

			if (josson.getNode("userInput") != null) {
				JsonNode dataConfigurationNew = josson.getNode("userInput");
				Josson newJosson = Josson.from(dataConfigurationNew);
				JsonNode jsonNodeData = velotechUtil.flatten(newJosson);
				if (jsonNodeData != null && jsonNodeData.size() > 0)
					data.put("userInput", jsonNodeData);
			}

			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, data.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getJsonParameterDataUserInput(Integer selectedFanId) {
		ApplicationResponse applicationResponse = null;
		try {
			Tbl95TxSelectedPumps tbl95TxSelectedPump = (Tbl95TxSelectedPumps) genericDao
					.getRecordById(Tbl95TxSelectedPumps.class, selectedFanId);
			Gson gson = new Gson();
			SelectedCentrifugalFan selectedFan = gson.fromJson(tbl95TxSelectedPump.getSelectedPump(),
					SelectedCentrifugalFan.class);

			List<String> uuids = new ArrayList<>();
			uuids.add(selectedFan.getFanTypeUuid());
			uuids.add(selectedFan.getFanModelUuid());
			uuids.add(selectedFan.getMotorTypeUuid());
			if (selectedFan.getPrimeMoverUuid() != null)
				uuids.add(selectedFan.getPrimeMoverUuid());

			Map<String, Object> rawData = jsonService.setJsonParameterDataUser();
			rawData.putAll(jsonService.setJsonParameterDataUser(uuids));
			JSONArray parameterArray = new JSONArray();
			for (String keyValue : rawData.keySet()) {
				JSONObject parameter = new JSONObject();
				JSONArray parameterData = new JSONArray();
				if (rawData.get(keyValue) instanceof JSONArray) {
					JSONArray ja = (JSONArray) rawData.get(keyValue);

					for (int i = 0; i < ja.size(); i++) {
						JSONObject data = new JSONObject();
						data.put("label", ja.get(i));
						data.put("value", ja.get(i));
						parameterData.add(data);
					}

				} else if (rawData.get(keyValue) instanceof String) {
					JSONObject data = new JSONObject();
					data.put("label", rawData.get(keyValue));
					data.put("value", rawData.get(keyValue));
					parameterData.add(data);

				}
				parameter.put("parameterLabel", keyValue);
				parameter.put("data", parameterData);
				parameterArray.add(parameter);
			}
			JSONObject rootObject = new JSONObject();
			rootObject.put("data", parameterArray);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, rootObject.toString());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getJsonParameterDataUser(Integer offerFanId) {

		ApplicationResponse applicationResponse = null;
		try {

			Tbl26OfferFan tbl26OfferFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			Tbl28SelectedFanJsonValue tbl28SelectedFanJsonValue = tbl26OfferFan.getTbl28SelectedFanJsonValue();
			JSONObject sourceData = new JSONObject(tbl28SelectedFanJsonValue.getJsonText());
			Josson josson = Josson.fromJsonString(sourceData.toString());

			List<String> uuids = new ArrayList<>();
			uuids.add(josson.getNode("selectedFan.pumpTypeUuid").textValue());
			uuids.add(josson.getNode("selectedFan.pumpModelUuid").textValue());
			if (josson.getNode("selectedFan.primeMoverUuid") != null)
				uuids.add(josson.getNode("selectedFan.primeMoverUuid").textValue());

			if (josson.getNode("selectedFan.motorTypeUuid") != null)
				uuids.add(josson.getNode("selectedFan.motorTypeUuid").textValue());

			Map<String, Object> rawData = jsonService.setJsonParameterDataUser();
			rawData.putAll(jsonService.setJsonParameterDataUser(uuids));
			JSONArray parameterArray = new JSONArray();
			for (String keyValue : rawData.keySet()) {
				JSONObject parameter = new JSONObject();
				JSONArray parameterData = new JSONArray();
				if (rawData.get(keyValue) instanceof JSONArray) {
					JSONArray ja = (JSONArray) rawData.get(keyValue);

					for (int i = 0; i < ja.size(); i++) {
						JSONObject data = new JSONObject();
						data.put("label", ja.get(i));
						data.put("value", ja.get(i));
						parameterData.add(data);
					}

				} else if (rawData.get(keyValue) instanceof String) {
					JSONObject data = new JSONObject();
					data.put("label", rawData.get(keyValue));
					data.put("value", rawData.get(keyValue));
					parameterData.add(data);

				}
				parameter.put("parameterName", keyValue);
				parameter.put("data", parameterData);
				parameterArray.add(parameter);
			}
			JSONObject rootObject = new JSONObject();
			rootObject.put("data", parameterArray);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, rootObject.toString());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;

	}

	/*
	 * @Override public ApplicationResponse getPumpSetgaDrawing(Integer offerPumpId,
	 * Boolean download) { ApplicationResponse applicationResponse = new
	 * ApplicationResponse(); try { OfferDrawingPojo offerDrawing = new
	 * OfferDrawingPojo(); String path = ""; Tbl26OfferPump tbl26OfferPump =
	 * (Tbl26OfferPump) genericDao.getRecordById(Tbl26OfferPump.class, offerPumpId);
	 * Tbl23OfferRev tbl23OfferRev = tbl26OfferPump.getTbl23OfferRev();
	 * offerDrawing.setTbl23OfferRev(tbl23OfferRev); Tbl28SelectedPump
	 * tbl28SelectedPump = tbl26OfferPump.getTbl28SelectedPump();
	 * 
	 * Tbl28SelectedPumpJsonValue tbl28SolarSelectedPumpJsonValue =
	 * tbl26OfferPump.getTbl28SelectedPumpJsonValue(); JSONObject sourceData = new
	 * JSONObject(tbl28SolarSelectedPumpJsonValue.getJsonText());
	 * 
	 * Josson josson = Josson.fromJsonString(sourceData.toString());
	 * offerDrawing.setJosson(josson);
	 * 
	 * List<Tbl02Modelmaster> tbl02Modelmasters = (List<Tbl02Modelmaster>)
	 * genericDao .findByParam(Tbl02Modelmaster.class, "pumpModel",
	 * tbl28SelectedPump.getPumpModel()); Tbl02Modelmaster tbl02Modelmaster =
	 * tbl02Modelmasters.size() > 0 ? tbl02Modelmasters.get(0) : null;
	 * offerDrawing.setTbl02Modelmaster(tbl02Modelmaster);
	 * 
	 * Tbl15Pumpsetga tbl15Pumpsetga = dxfdao.getPumpsetga(tbl02Modelmaster,
	 * Integer.parseInt(tbl28SelectedPump.getStage()),
	 * tbl28SelectedPump.getMocStd());
	 * offerDrawing.setTbl26OfferPump(tbl26OfferPump);
	 * 
	 * Tbl80TemplateMaster tbl80TemplateMaster = new Tbl80TemplateMaster(); if
	 * (tbl15Pumpsetga != null) { tbl80TemplateMaster =
	 * tbl15Pumpsetga.getTbl80TemplateMaster();
	 * offerDrawing.setTbl15Pumpsetga(tbl15Pumpsetga);
	 * 
	 * offerDrawing.setTbl80TemplateMaster(tbl80TemplateMaster);
	 * offerDrawing.setDrawingNumber(tbl15Pumpsetga.getDrgNo());
	 * 
	 * if (download) offerDrawing.setDownload(true);
	 * 
	 * offerDrawing.setPdf(true);
	 * 
	 * offerDrawingBlock.getGeneratedDxfViewAspose(offerDrawing);
	 * 
	 * if (offerDrawing.isCheckPath()) { if (offerDrawing.isDownload()) path =
	 * offerDrawing.getZipPath(); else path = offerDrawing.getPath(); }
	 * session.setAttribute("pumpsetPDF", velotechUtil.getUserRealPath() +
	 * offerDrawing.getOutputPdfName() + ".pdf"); applicationResponse =
	 * ApplicationResponseUtil.getResponseToGetData(true,
	 * ApplicationConstants.DATA_LOAD_SUCCESS_MSG, path); } else {
	 * applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
	 * ApplicationConstants.FILE_NOT_EXISTS_MSG, path); } } catch (Exception e) {
	 * log.error(e.getMessage(), e); } return applicationResponse; }
	 */

}

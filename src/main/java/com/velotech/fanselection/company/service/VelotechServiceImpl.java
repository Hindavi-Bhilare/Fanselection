
package com.velotech.fanselection.company.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl1401Motortype;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl28OfferFanSpecification;
import com.velotech.fanselection.models.Tbl28SelectedPricing;
import com.velotech.fanselection.models.Tbl28SelectedFan;
import com.velotech.fanselection.models.Tbl28SelectedFanBom;
import com.velotech.fanselection.models.Tbl28SelectedFanVariant;
import com.velotech.fanselection.models.Tbl28SelectedpricingDetails;
import com.velotech.fanselection.models.Tbl61ProjectSummaryQuoteReport;
import com.velotech.fanselection.selection.models.SelectedCentrifugalFan;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
public class VelotechServiceImpl implements VelotechService, CompanyService {

	static Logger log = LogManager.getLogger(VelotechServiceImpl.class.getName());

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private GenericDao genericDao;

	@SuppressWarnings("unchecked")
	@Override
	public boolean getSummarySheetForOffers(List<Tbl26OfferFan> offerFans) {

		List<Tbl61ProjectSummaryQuoteReport> projectSummaryQuoteReports = new ArrayList<>();
		try {
			DecimalFormat autoDForm = new DecimalFormat("0.0");
			String sessionId = velotechUtil.getSessionId();
			// delete exisiting records

			for (Tbl26OfferFan tbl26OfferFan : offerFans) {
				int i = 0;

				List<Tbl01Fantype> tbl01Fantypes = (List<Tbl01Fantype>) genericDao.findByParam(Tbl01Fantype.class, "series",
						tbl26OfferFan.getTbl28SelectedFan().getSeries());
				Tbl01Fantype tbl01Fantype = tbl01Fantypes.size() > 0 ? tbl01Fantypes.get(0) : null;
				
				projectSummaryQuoteReports.add(new Tbl61ProjectSummaryQuoteReport(0, sessionId, tbl26OfferFan.getId(), "Fan Type",
						tbl01Fantype.getFanType(), tbl26OfferFan.getTagNo(), "Application Details", i++));

				projectSummaryQuoteReports.add(new Tbl61ProjectSummaryQuoteReport(0, sessionId, tbl26OfferFan.getId(), "Flow",
						tbl26OfferFan.getTbl27RequirementsDp().getFlow() + " " + tbl26OfferFan.getTbl27RequirementsDp().getUomFlow(),
						tbl26OfferFan.getTagNo(), "Duty Points Required", i++));
				projectSummaryQuoteReports.add(new Tbl61ProjectSummaryQuoteReport(0, sessionId, tbl26OfferFan.getId(), "Head",
						tbl26OfferFan.getTbl27RequirementsDp().getHead() + " " + tbl26OfferFan.getTbl27RequirementsDp().getUomHead(),
						tbl26OfferFan.getTagNo(), "Duty Points Required", i++));
			
				i = 200;
				if (tbl26OfferFan.getTbl28SelectedPrimemover() != null) {
					Tbl14PrimemoverMaster tbl14PrimemoverMaster = (Tbl14PrimemoverMaster) genericDao.getRecordById(Tbl14PrimemoverMaster.class,
							tbl26OfferFan.getTbl28SelectedPrimemover().getPrimemoverId());
					//Tbl14PrimemoverVoltage tbl14PrimemoverVoltage = (Tbl14PrimemoverVoltage) genericDao.getRecordsByParentId(Tbl14PrimemoverVoltage.class, "tbl14PrimemoverMaster.primemoverId", tbl26OfferFan.getTbl28SelectedPrimemover().getPrimemoverId());
					Tbl1401Motortype tbl1401Motortype = (Tbl1401Motortype) genericDao.getRecordById(Tbl1401Motortype.class, tbl14PrimemoverMaster.getTbl1401Motortype().getId());
					projectSummaryQuoteReports.add(new Tbl61ProjectSummaryQuoteReport(0, sessionId, tbl26OfferFan.getId(), "Type",
							tbl14PrimemoverMaster.getPrimemoverType(), tbl26OfferFan.getTagNo(), "Prime Mover", i++));
					projectSummaryQuoteReports.add(new Tbl61ProjectSummaryQuoteReport(0, sessionId, tbl26OfferFan.getId(), "Make",
							tbl14PrimemoverMaster.getManufacturer(), tbl26OfferFan.getTagNo(), "Prime Mover", i++));
					projectSummaryQuoteReports.add(new Tbl61ProjectSummaryQuoteReport(0, sessionId, tbl26OfferFan.getId(), "Power",
							autoDForm.format(tbl14PrimemoverMaster.getPower()) + " kW / " + tbl14PrimemoverMaster.getPowerHp() + " HP",
							tbl26OfferFan.getTagNo(), "Prime Mover", i++));
					projectSummaryQuoteReports.add(new Tbl61ProjectSummaryQuoteReport(0, sessionId, tbl26OfferFan.getId(), "Frame Size",
							tbl14PrimemoverMaster.getFramesize(), tbl26OfferFan.getTagNo(), "Prime Mover", i++));
					projectSummaryQuoteReports.add(new Tbl61ProjectSummaryQuoteReport(0, sessionId, tbl26OfferFan.getId(), " Volts / Phase / Hz",
							tbl14PrimemoverMaster.getVoltage() + "(V)/ " + tbl1401Motortype.getPhase() + " / " + tbl14PrimemoverMaster.getFrequency() + "(Hz)",
							tbl26OfferFan.getTagNo(), "Prime Mover", i++));
				}
				
	/*			
				List<Tbl28SelectedFanVariant> tbl28SelectedFanVariants = (List<Tbl28SelectedFanVariant>) genericDao
						.getRecordsByParentId(Tbl28SelectedFanVariant.class, "tbl26OfferFan.id", tbl26OfferFan.getId());
				for (Tbl28SelectedFanVariant tbl28SelectedFanVariant : tbl28SelectedFanVariants) {
					i = 300;
					projectSummaryQuoteReports
							.add(new Tbl61ProjectSummaryQuoteReport(0, sessionId, tbl26OfferFan.getId(), tbl28SelectedFanVariant.getVariant(),
									tbl28SelectedFanVariant.getValue(), tbl26OfferFan.getTagNo(), "Technicals", i++));
				}
				
				*/
				
				List<Tbl28OfferFanSpecification> offerFanSpecifications = (List<Tbl28OfferFanSpecification>) genericDao
						.getRecordsByParentId(Tbl28OfferFanSpecification.class, "tbl26OfferFan.id", tbl26OfferFan.getId());
				if (offerFanSpecifications != null) {
					for (Tbl28OfferFanSpecification tbl28OfferFanSpecification : offerFanSpecifications) {
						i = 400;
						projectSummaryQuoteReports.add(
								new Tbl61ProjectSummaryQuoteReport(0, sessionId, tbl26OfferFan.getId(), tbl28OfferFanSpecification.getParameter(),
										tbl28OfferFanSpecification.getValue(), tbl26OfferFan.getTagNo(), "Technicals", i++));
					}
				}

				List<Tbl28SelectedFanBom> tbl28SelectedFanBoms = (List<Tbl28SelectedFanBom>) genericDao
						.getRecordsByParentId(Tbl28SelectedFanBom.class, "tbl26OfferFan.id", tbl26OfferFan.getId());

				for (Tbl28SelectedFanBom tbl28SelectedFanBom : tbl28SelectedFanBoms) {
					i = 400;
					if (tbl28SelectedFanBom.getReportSummary() != null && tbl28SelectedFanBom.getReportSummary()) {
						projectSummaryQuoteReports.add(new Tbl61ProjectSummaryQuoteReport(0, sessionId, tbl26OfferFan.getId(),
								tbl28SelectedFanBom.getDescription().toUpperCase(), tbl28SelectedFanBom.getMaterialDescription(),
								tbl26OfferFan.getTagNo(), "MOC", i++));
					}
				}

				List<Tbl28SelectedPricing> tbl28SelectedPricingSet = (List<Tbl28SelectedPricing>) genericDao
						.getRecordsByParentId(Tbl28SelectedPricing.class, "tbl26OfferFan.id", tbl26OfferFan.getId());

				/*
				 * if
				 * (offerPumps.get(0).getTbl23OfferRev().getShowPriceBreakUp())
				 * { for (Tbl28SelectedPricing tbl28SelectedPricing :
				 * tbl28SelectedPricingSet) { i = 500;
				 * projectSummaryQuoteReports.add(new
				 * Tbl61ProjectSummaryQuoteReport(0, sessionId,
				 * tbl26OfferPump.getId(),
				 * tbl28SelectedPricing.getItemName().toUpperCase(),
				 * tbl28SelectedPricing.getSubTotal().toString(),
				 * tbl26OfferPump.getTagNo(), "Price", i++)); } }
				 */
				i = 600;
				/*
				 * if (offerPumps.get(0).getTbl23OfferRev().getShowPrice()) {
				 * projectSummaryQuoteReports.add(new
				 * Tbl61ProjectSummaryQuoteReport(0, sessionId,
				 * tbl26OfferPump.getId(), "Addon Price",
				 * tbl26OfferPump.getAddonTotal().toString(),
				 * tbl26OfferPump.getTagNo(), "Price", i++)); } if
				 * (offerPumps.get(0).getTbl23OfferRev().getShowTagPrice()) {
				 * projectSummaryQuoteReports.add(new
				 * Tbl61ProjectSummaryQuoteReport(0, sessionId,
				 * tbl26OfferPump.getId(), "Total Price",
				 * tbl26OfferPump.getLineTotal().toString(),
				 * tbl26OfferPump.getTagNo(), "Price", i++)); }
				 */
			}
			genericDao.saveAll(projectSummaryQuoteReports);
		}

		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return true;
	}

	@Override
	public List<Tbl28SelectedpricingDetails> getPrimeMoverPricingDetails(Tbl14PrimemoverMaster tbl14PrimemoverMaster) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tbl28SelectedpricingDetails> getFanPricingDetails(SelectedCentrifugalFan selectedFan, Tbl28SelectedFan tbl28SelectedFan,
			Set<Tbl28SelectedFanVariant> tbl28SelectedFanVariants, Tbl01Fantype tbl01Fantype) {

		// TODO Auto-generated method stub
		return null;
	}

}

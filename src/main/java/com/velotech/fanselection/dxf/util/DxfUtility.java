
package com.velotech.fanselection.dxf.util;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.utils.VelotechUtil;

public class DxfUtility {

	private VelotechUtil velotechUtil = new VelotechUtil();

	public HashMap<String, String> getAllFieldDataOfferPump(Tbl26OfferFan offerFan) {

		HashMap<String, String> result = new HashMap<String, String>();
		result.put("tagNo", offerFan.getTagNo());
		return result;
	}




	public HashMap<String, String> getAllFieldDataOfferRev(Tbl23OfferRev tbl23OfferRev) {

		HashMap<String, String> result = new HashMap<String, String>();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

		result.put("offerNo", tbl23OfferRev.getTbl23OfferMaster().getOfferNo());
		if (tbl23OfferRev.getOfferdate() != null)
			result.put("offerDate", df.format(tbl23OfferRev.getOfferdate()));
		result.put("consultant", tbl23OfferRev.getConsultant());
		result.put("endUser", tbl23OfferRev.getEndUser());
		result.put("project", tbl23OfferRev.getProject());
		result.put("contractor", tbl23OfferRev.getContractor());
		result.put("suppliedBy", tbl23OfferRev.getSuppliedBy());
		result.put("reference", tbl23OfferRev.getReference());
		if (tbl23OfferRev.getEnquiryDate() != null)
			result.put("enquiryDate", tbl23OfferRev.getEnquiryDate().toString());
		result.put("subject", tbl23OfferRev.getSubject());
		/*
		 * result.put("deliveryTerms", deliveryTerms); result.put("exciseTerms",
		 * exciseTerms); result.put("guranteeTerms", guranteeTerms);
		 * result.put("paymentTerms", paymentTerms);
		 * result.put("inspectionTerms", inspectionTerms);
		 */
		if (tbl23OfferRev.getTbl52UsermasterByLoginId() != null)
			result.put("preparedBy", tbl23OfferRev.getTbl52UsermasterByLoginId().getUserName());
		if (tbl23OfferRev.getTbl28CompanyMaster() != null) {
			// result.put("companyOffer", tbl28CompanyMaster.getAddress());
			result.put("companyOffer", tbl23OfferRev.getTbl28CompanyMaster().getDocumentCompanyName());
			result.put("company", tbl23OfferRev.getTbl28CompanyMaster().getCompanyName());
		}
		if (tbl23OfferRev.getTbl25CustomerMaster() != null) {
			result.put("customerName", tbl23OfferRev.getTbl25CustomerMaster().getCustomerName());
			result.put("customerAddress1", tbl23OfferRev.getTbl25CustomerMaster().getCustomerAddress1());
		}
		return result;

	}

	public String getOfferLogoName(Tbl28CompanyMaster tbl28CompanyMaster) {

		if (tbl28CompanyMaster != null)
			return tbl28CompanyMaster.getLogo();
		else
			return "Logo.jpg";
	}

	public String getOfferCompany(Tbl28CompanyMaster tbl28CompanyMaster) {

		if (tbl28CompanyMaster != null)
			return tbl28CompanyMaster.getCompanyName();
		else
			return "";
	}

}

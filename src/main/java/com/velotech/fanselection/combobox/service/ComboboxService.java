
package com.velotech.fanselection.combobox.service;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface ComboboxService {

	ApplicationResponse getComboRecords(RequestWrapper requestWrapper);

	ApplicationResponse getOffers(String query, RequestWrapper requestWrapper);

	ApplicationResponse getOfferTags(Integer offerRevId);

	ApplicationResponse getTableList();

	ApplicationResponse getPumpTypes();

	ApplicationResponse getUserCompanies();

	ApplicationResponse getPrimeMover();

	//ApplicationResponse getCanopy();

	//ApplicationResponse getDrainRim();

	//ApplicationResponse getEarthing();

	//ApplicationResponse getLiftingLug();

	//ApplicationResponse getStandard();

	ApplicationResponse getMocStd(Integer modelmasterid);

	//ApplicationResponse getPerformanceCurve(Integer modelmasterid);

	ApplicationResponse getSegmentMaster();

	ApplicationResponse getUserMaster();

	ApplicationResponse getStatus();

	ApplicationResponse getType();

	ApplicationResponse getCategory();

	//ApplicationResponse getModelNrv(Integer pumpModelId);

	ApplicationResponse getStartingMethod(Integer primeMoverId);

	//ApplicationResponse getSelectionType();

	ApplicationResponse getMotorSeries();

//	ApplicationResponse getQap(Integer offerPumpId);

	ApplicationResponse getSalesPerson();

	ApplicationResponse getSalesPerson(Integer organisationId);

//	ApplicationResponse getQapForPump(Integer offerPumpId);

	ApplicationResponse getEmailIds();

	//ApplicationResponse getStartingMethodfromWi();

	ApplicationResponse getPrimeMoverBombo(Integer motorSeriesId,Double power);

	ApplicationResponse getMotorDescription(String motorSeriesId);
	
	ApplicationResponse getPumpTypesWithAll();

	ApplicationResponse getModelPerformancesCombo();

	//ApplicationResponse getIdentityCombo(Integer pumpModelId);



}

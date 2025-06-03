
package com.velotech.fanselection.selection.service;

import java.util.List;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl28SelectedFanBom;
import com.velotech.fanselection.utils.ApplicationResponse;

public interface CentrifugalFanSelectionService {

	// New method for fanselection

	ApplicationResponse getFanTypes(String searchValue);

	ApplicationResponse getFanSelection(String requestPayload);

	ApplicationResponse addToOffer(String requestPayload);

	ApplicationResponse isUniqueTagNumber(String tagNo, Integer offerRevId);

	ApplicationResponse getJsonParameterDataUserInput(Integer selectedPumpId);

	ApplicationResponse getPerformanceGraph(Integer selectedFanId);

	ApplicationResponse quickDocument(String requestPayload);

	ApplicationResponse getMountings(String primeMoverType);

	ApplicationResponse getEffClasses();

	ApplicationResponse getSpecifications(String primeMoverType);

	ApplicationResponse getAmbientTemps();

	ApplicationResponse getTempRiseClasses();

	ApplicationResponse getManufacturers(String primeMoverType);

	ApplicationResponse getPmseries(String primeMoverType);

	ApplicationResponse getPmPoles();

}

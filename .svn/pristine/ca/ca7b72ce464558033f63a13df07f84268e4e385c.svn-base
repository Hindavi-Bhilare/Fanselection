
package com.velotech.fanselection.offer.service;

import java.util.List;

import com.velotech.fanselection.utils.ApplicationResponse;

public interface OfferPumpPriceService {

	ApplicationResponse getOfferFactor(Integer offerRevId);

	ApplicationResponse getSpareParts(Integer offerPumpId);

	ApplicationResponse saveSparePartsPricing(Integer offerPumpId, List<Integer> ids);

	ApplicationResponse getSelectedPricings(Integer offerPumpId);

	ApplicationResponse saveSelectedPricing(String requestPayload);

	ApplicationResponse deleteSelectedPricings(List<Integer> ids);

	ApplicationResponse getPricingDetails(Integer selectedPricingId);

	ApplicationResponse savePricingDetails(String requestPayload);

	ApplicationResponse deletePricingDetails(List<Integer> ids);

	ApplicationResponse getAddons(Integer offerPumpId);

	ApplicationResponse saveAddons(String requestPayload);

	ApplicationResponse deleteAddons(List<Integer> ids);

	boolean updateOfferPumpPricing(Integer offerPumpId);

	boolean updateSelectedPricings(Integer offerPumpId);

}

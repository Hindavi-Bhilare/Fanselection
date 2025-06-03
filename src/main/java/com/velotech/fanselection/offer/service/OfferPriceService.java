
package com.velotech.fanselection.offer.service;

import java.util.List;

import com.velotech.fanselection.utils.ApplicationResponse;

public interface OfferPriceService {

	ApplicationResponse getOfferTotal(Integer offerRevId);

	ApplicationResponse getOfferPumpPrices(Integer offerRevId);

	ApplicationResponse getAddons(Integer offerRevId);

	ApplicationResponse saveAddons(String requestPayload);

	ApplicationResponse deleteAddons(List<Integer> ids);

	ApplicationResponse saveFactors(String requestPayload);

	ApplicationResponse getFactors(Integer offerRevId);

	ApplicationResponse deleteFactors(List<Integer> ids);

	boolean updateOfferPricing(Integer offerRevId);

	boolean updateOfferFactor(Integer offerRevId);

}

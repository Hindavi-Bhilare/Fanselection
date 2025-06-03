
package com.velotech.fanselection.offer.dao;

import java.util.List;


import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl28OfferFanSpecification;
import com.velotech.fanselection.models.Tbl28SelectedFanBom;

public interface OfferDetailsDao {

	Tbl14PrimemoverMaster getPrimeMover(Integer offerFanId);

	boolean deleteQapPricing(Integer offerFanId);

	//List<Tbl03QapPrice> getQapPrices(Integer qapId, String fanModel);

	boolean deleteProjectSummaryQuote(String sessionId);

	Tbl26OfferFan getOfferFanForPerformanceChart(Integer offerFanId);

	List<Tbl28SelectedFanBom> getSelectedBomForDatasheet(int offerFanId, int productTypeId);

	List<Tbl28SelectedFanBom> getSelectedBomForDrawing(int offerFanId, int productTypeId);

	//Tbl15WiringDiagram getWiringDiagram(Integer phase, String startingMethod, String motorType);

	List<Tbl28OfferFanSpecification> getTbl28OfferFanspecifications(Integer offerFanId);

	//Tbl15StartingMethod getTbl15StartingMethod(Double power, String startingMethod);

}

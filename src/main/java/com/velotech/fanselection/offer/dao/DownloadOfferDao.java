package com.velotech.fanselection.offer.dao;

import java.util.List;


import com.velotech.fanselection.models.Tbl26OfferFan;

public interface DownloadOfferDao {

	List<Tbl26OfferFan> getDownloadOfferData(Integer offerRevId);

}


package com.velotech.fanselection.mis.dao;

import java.util.List;

import com.velotech.fanselection.mis.dtos.MISOfferSearchDto;
import com.velotech.fanselection.models.views.MisOffer;

public interface MISOfferDao {

	List<MisOffer> getMISOffer(MISOfferSearchDto misOfferSearchDto);

}

package com.velotech.fanselection.design.dao;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.Pagination;

public interface CentrifugalModelMasterDao {
	
	ApplicationResponse getCentrifugalModelMasterData(String searchProperty, String searchValue, Pagination pagination);

}

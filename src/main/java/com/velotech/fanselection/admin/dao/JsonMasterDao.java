package com.velotech.fanselection.admin.dao;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface JsonMasterDao {

	public ApplicationResponse getRecords(RequestWrapper requestWrapper); 
}


package com.velotech.fanselection.common.dao;

import java.util.List;

import com.velotech.fanselection.models.Tbl90JsonMaster;
import com.velotech.fanselection.models.Tbl90ParameterValue;

public interface JsonDao {

	Tbl90JsonMaster getJsonMaster(String jsonName);

	Tbl90JsonMaster getJsonMaster(String jsonName, String uuid);

	List<Tbl90ParameterValue> getJsonParameterValue(String uuid);

	List<Tbl90ParameterValue> getJsonParameterValue(List<String> uuids);

	List<Tbl90ParameterValue> getJsonParameterValue();
	
	List<Tbl90ParameterValue> getJsonParameterValueUser();
	
	List<Tbl90ParameterValue> getJsonParameterValueUser(List<String> uuids);

}

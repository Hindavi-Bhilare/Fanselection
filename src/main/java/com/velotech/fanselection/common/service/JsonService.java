
package com.velotech.fanselection.common.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface JsonService {

	String getJsonMaster(String jsonName, JSONObject jsonobj); // ok

	Map<String, Object> getJsonParameterMaster(String suffix);

	JSONObject calculate(JSONObject data, String key1);

	Object getJsonFilterData(String modelUuid, String seriesUuid, String jsonName, JSONObject jsonobj);

	Object getJsonFilterData(String modelUuid, String seriesUuid, String jsonName, JSONObject jsonobj, Object returnIfnull);

	Map<String, Object> getJsonParameterValue(List<String> uuids, String suffix);

	Map<String, Object> getJsonParameterValue(String uuid, String suffix);

	JSONObject setJsonParameter(JSONObject jo, List<String> modelUuid, String key, String suffix);

	JSONObject setJsonParameter(JSONObject jo, String modelUuid, String key, String suffix);

	JSONObject setJsonParameter(JSONObject jo, String key, String suffix);
	
	public Map<String, Object> setJsonParameterDataUser();
	
	public Map<String, Object> setJsonParameterDataUser(List<String> uuids);

	// Object getJsonFilterData(String pumpModel, Object object, String string,
	// JSONObject jo);

	// List<String> getJsonFilterData(Object modelUuid, Integer pumpTypeId,
	// String jsonName, JSONObject jo);
}

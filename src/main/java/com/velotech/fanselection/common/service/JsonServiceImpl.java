
package com.velotech.fanselection.common.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.apache.commons.lang.ArrayUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octomix.josson.Josson;
import com.velotech.fanselection.common.dao.JsonDao;
import com.velotech.fanselection.models.Tbl90JsonMaster;
import com.velotech.fanselection.models.Tbl90ParameterValue;
import com.velotech.fanselection.utils.VelotechUtil;

import net.minidev.json.JSONArray;

@Service
@Transactional

public class JsonServiceImpl implements JsonService {

	static Logger log = LogManager.getLogger(JsonServiceImpl.class.getName());

	@Autowired
	private JsonDao dao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Override
	public Map<String, Object> getJsonParameterValue(List<String> uuids, String suffix) {

		Map<String, Object> data = new LinkedHashMap<String, Object>();
		try {

			List<Tbl90ParameterValue> tbl90SolarParameterValues = dao.getJsonParameterValue(uuids);

			setParameterValue(data, tbl90SolarParameterValues, suffix);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

	@Override
	public Map<String, Object> getJsonParameterMaster(String suffix) {

		Map<String, Object> data = new LinkedHashMap<String, Object>();
		try {

			List<Tbl90ParameterValue> parameterValuesList = dao.getJsonParameterValue();
			setParameterValue(data, parameterValuesList, suffix);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

	private void setParameterValue(Map<String, Object> data, List<Tbl90ParameterValue> parameterValuesList, String suffix) throws JSONException {

		for (Tbl90ParameterValue tbl90ParameterValue : parameterValuesList) {
			String parameterName = suffix + tbl90ParameterValue.getParameter();
			if (tbl90ParameterValue.getIsExpression()) {
				JSONObject temp = new JSONObject();
				temp.put("expression", tbl90ParameterValue.getValue());
				data.put(parameterName, temp);
			} else
				data.put(parameterName, tbl90ParameterValue.getValue());

		}
	}

	private Map<String, Object> getParameterValueUser(List<Tbl90ParameterValue> parameterValuesList) throws JSONException {

		Map<String, Object> ans = new LinkedHashMap<>();
		try {
			for (Tbl90ParameterValue tbl90ParameterValue : parameterValuesList) {
				if (tbl90ParameterValue.getValue() == null || tbl90ParameterValue.getValue().equals(""))
					ans.put(tbl90ParameterValue.getParameterLabel(), "");
				else {
					String[] temp = tbl90ParameterValue.getValue().split("\\|");
					if (temp.length == 1)
						ans.put(tbl90ParameterValue.getParameterLabel(), tbl90ParameterValue.getValue());
					else {
						JSONArray ja = new JSONArray();
						for (int i = 0; i < temp.length; i++) {
							ja.add(temp[i]);
						}
						ans.put(tbl90ParameterValue.getParameterLabel(), ja);
					}
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public Map<String, Object> getJsonParameterValue(String uuid, String suffix) {

		Map<String, Object> data = new LinkedHashMap<String, Object>();
		try {

			List<Tbl90ParameterValue> tbl90ParameterValues = dao.getJsonParameterValue(uuid);

			setParameterValue(data, tbl90ParameterValues, suffix);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

	@Override
	public JSONObject calculate(JSONObject data, String key1) {

		try {

			// System.out.println(data.toString());
			Josson josson = Josson.fromJsonString(data.toString());

			/*
			 * String expression = josson.getString( "entries()" +
			 * ".concat('field('," + "        key," + "        value.entries()"
			 * + "             .[value =~ '.*\\(.*\\).*']*" +
			 * "             .concat('.field(',key,': eval(',key,'))')" +
			 * "             .join()," + "        ')')" + ".join('.')");
			 */

			// before 1st July 23. upto 1st level expression
			/*
			 * String expression = josson.getString("entries()" +
			 * ".concat('field('," + "        key," + "        value.entries()"
			 * + "             .[isNotEmpty(value.expression)]*" +
			 * "             .concat('.field(',key,': eval(',key,'.expression))')"
			 * + "             .join()," + "        ')')" + ".join('.')");
			 */

			// added on 1st July 23 as this evaluates 2nd level expression also
			// https://stackoverflow.com/questions/76588758/filter-json-data-upto-2nd-or-3rd-level/76593232#76593232
			String expression = josson.getString("flatten('_').~'.*_expression$'*.get(..).map(key::value).mergeObjects().unflatten('_')"
					+ ".concat('field('," + "        entries()" + "        .concat(key," + "                '.field('," + "                value"
					+ "                  .entries()" + "                  .concat(key,"
					+ "                          if([isNotEmpty(value.expression)],"
					+ "                             concat(': eval(', key, '.expression)')," + "                             concat('.field(',"
					+ "                                    value" + "                                      .entries()"
					+ "                                      .concat(key, ': eval(', key, '.expression)')"
					+ "                                      .join(',')," + "                                    ')'"
					+ "                             )" + "                          )" + "                   )" + "                  .join(','),"
					+ "                ')'" + "         )" + "        .join(',')," + "        ')'" + " )");

			System.out.println(expression);
			if (expression == null || expression.equals(""))
				return data;

			JsonNode node1 = josson.getNode(expression);
			System.out.println(node1.toString());

			/*
			 * Iterator<Entry<String, JsonNode>> iterator =
			 * josson.getNode(key1).fields(); while(iterator.hasNext()) {
			 * Entry<String, JsonNode> field = iterator.next(); JsonNode
			 * nodeValue = null; if (josson.getNode(key1+".eval(" +
			 * field.getKey() + ")") == null) nodeValue =
			 * josson.getNode(key1+"."+field.getKey()); else
			 * nodeValue=josson.getNode(key1+".eval(" + field.getKey() + ")");
			 * ans.put(field.getKey(), getNodeValues(nodeValue));
			 * System.out.println("nodeValue:-"+nodeValue.toString()); }
			 * 
			 * ans.put(key1, ans);
			 */
			data = new JSONObject(node1.toString());

			System.out.println("calculated data" + data);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}

	private JsonNode calculateNode(Entry<String, JsonNode> field, Josson josson) {

		JsonNode ans = null;
		try {
			if (josson.getNode("eval(" + field.getKey() + ")") == null)
				System.out.println(josson.getNode(field.getKey()));
			else
				System.out.println(josson.getNode("eval(" + field.getKey() + ")"));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public String getJsonMaster(String jsonName, JSONObject jsonobj) {

		String ans = "";
		try {
			Tbl90JsonMaster jsonMaster = dao.getJsonMaster(jsonName);
			if (jsonMaster != null) {
				Josson josson = Josson.fromJsonString(jsonMaster.getJsonText());
				Josson jossonSource = Josson.fromJsonString(jsonobj.toString());

				josson.put("sourceData", jossonSource.getNode());

				JsonNode a = josson.getNode();
				Josson newJosson = Josson.from(a);
				ans = newJosson.getNode("eval(filter)").asText();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public Object getJsonFilterData(String modelUuid, String seriesUuid, String jsonName, JSONObject jsonobj) {

		Object ans = "";
		try {
			Boolean check = false;
			Josson josson = null;
			Tbl90JsonMaster tbl90JsonMaster = null;

			if (modelUuid != null)
				tbl90JsonMaster = dao.getJsonMaster(jsonName, modelUuid);
			if (tbl90JsonMaster != null) {
				josson = Josson.fromJsonString(tbl90JsonMaster.getJsonText());
				check = true;
			} else {
				if (seriesUuid != null)
					tbl90JsonMaster = dao.getJsonMaster(jsonName, seriesUuid);
				if (tbl90JsonMaster != null) {
					josson = Josson.fromJsonString(tbl90JsonMaster.getJsonText());
					check = true;
				} else {
					if (jsonName != null)
						tbl90JsonMaster = dao.getJsonMaster(jsonName);
					if (tbl90JsonMaster != null) {
						josson = Josson.fromJsonString(tbl90JsonMaster.getJsonText());
						check = true;
					}
				}
			}
			if (check) {
				Josson jossonSource = Josson.fromJsonString(jsonobj.toString());
				josson.put("sourceData", jossonSource.getNode());
				JsonNode a = josson.getNode();
				Josson newJosson = Josson.from(a);
				// System.out.println(newJosson.getNode().toString());
				JsonNode query = newJosson.getNode("eval(filter)");
				JSONObject jo = new JSONObject();
				if (query != null) {
					if (query.isArray()) {
						ObjectMapper mapper = new ObjectMapper();
						List<?> list = mapper.convertValue(query, ArrayList.class);
						ans = list;
					} else {
						if (query.isTextual())
							ans = query.asText();
						if (query.isBoolean())
							ans = query.asBoolean();
						if (query.isNumber())
							ans = query.asDouble();
						if (query.isObject()) {
							jo = new JSONObject(query.toString());
							ans = jo;
						}
						// to parse as integer
						// VelotechUtil v.getInteger(ans);
					}
				} else {
					List<?> list = null;
					ans = list;
				}

			} else {
				List<?> list = null;
				ans = list;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		System.out.println("ans:- " + ans);
		return ans;
	}

	@Override
	public Object getJsonFilterData(String modelUuid, String seriesUuid, String jsonName, JSONObject jsonobj, Object returnIfnull) {

		Object ans = null;
		try {
			ans = getJsonFilterData(modelUuid, seriesUuid, jsonName, jsonobj);
			if (ans == null)
				ans = returnIfnull;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		System.out.println("ans:- " + ans);
		return ans;
	}

	private Object getNodeValues(JsonNode nodeValue) {

		Object ans = new Object();
		try {
			if (nodeValue.isArray()) {
				ans = nodeValue;
			} else if (nodeValue.isInt())
				ans = nodeValue.asInt();
			else if (nodeValue.isDouble())
				ans = nodeValue.asDouble();
			else if (nodeValue.isBoolean())
				ans = nodeValue.asBoolean();
			else
				ans = nodeValue.asText();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public JSONObject setJsonParameter(JSONObject jo, List<String> modelUuid, String key, String suffix) {

		try {
			Map<String, Object> newData = getJsonParameterValue(modelUuid, suffix);
			jo = mergeAndCalcMap(jo, newData, key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return jo;
	}

	@Override
	public JSONObject setJsonParameter(JSONObject jo, String modelUuid, String key, String suffix) {

		try {
			// long start = System.currentTimeMillis();
			Map<String, Object> newData = getJsonParameterValue(modelUuid, suffix);
			jo = mergeAndCalcMap(jo, newData, key);
			// System.out.println("1 Cycle:" + (start -
			// System.currentTimeMillis()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return jo;
	}

	@Override
	public JSONObject setJsonParameter(JSONObject jo, String key, String suffix) {

		try {
			Map<String, Object> newData = getJsonParameterMaster(suffix);
			jo = mergeAndCalcMap(jo, newData, key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return jo;
	}

	private JSONObject mergeAndCalcMap(JSONObject jo, Map<String, Object> newData, String key) {

		try {
			for (String keyValue : newData.keySet()) {
				JSONObject data = new JSONObject();
				data.put(keyValue, newData.get(keyValue));
				jo = velotechUtil.mergeJsonNew(jo, data, key);
				// System.out.println("After merge" + jo.toString());
				jo = calculate(jo, key);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return jo;
	}

	@Override
	public Map<String, Object> setJsonParameterDataUser() {

		Map<String, Object> ans = new LinkedHashMap<>();
		try {
			List<Tbl90ParameterValue> parameterValuesList = dao.getJsonParameterValueUser();
			ans = getParameterValueUser(parameterValuesList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	@Override
	public Map<String, Object> setJsonParameterDataUser(List<String> uuids) {

		Map<String, Object> ans = new LinkedHashMap<>();
		try {
			List<Tbl90ParameterValue> parameterValuesList = dao.getJsonParameterValueUser(uuids);
			ans = getParameterValueUser(parameterValuesList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}
}

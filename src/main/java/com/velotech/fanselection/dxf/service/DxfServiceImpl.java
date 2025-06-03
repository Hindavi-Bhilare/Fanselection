
package com.velotech.fanselection.dxf.service;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.dxf.dao.DxfDao;
import com.velotech.fanselection.dxf.model.OfferDrawingPojo;
import com.velotech.fanselection.dxf.util.OfferDrawingBlock;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.ireportmodels.MocData;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl03GenericBomData;
import com.velotech.fanselection.models.Tbl58UserCompany;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.ComboBox;
import com.velotech.fanselection.utils.CommonList;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class DxfServiceImpl implements DxfService {

	static Logger log = LogManager.getLogger(DxfServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private DxfDao dao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private CommonList commonList;

	@Autowired
	private OfferDrawingBlock offerDrawingBlock;

	@Autowired
	private OfferDrawingPojo offerDrawing;

	@Override
	public ApplicationResponse getAddtionalParamter(Integer seriesId, Integer modelId, Integer mocId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			JSONObject rootObject = new JSONObject();
			JSONArray additionalAttributes = getAdditionalAttributesJson(seriesId, modelId, mocId);
			rootObject.put("additionalAttributes", additionalAttributes);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, rootObject.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private JSONArray getAdditionalAttributesJson(Integer seriesId, Integer modelId, Integer mocId) {

		JSONArray rootObject = new JSONArray();
		try {

			Tbl01CentrifugalModelMaster modelmaster = (Tbl01CentrifugalModelMaster) genericDao.getRecordById(Tbl01CentrifugalModelMaster.class, modelId);

			/*
			 * List<String> rotations = Arrays.asList(modelmaster.getRotation().split(","));
			 * JSONObject attribut1 = new JSONObject(); JSONArray rotationsJson = new
			 * JSONArray(); for (String rotation : rotations) { JSONObject data = new
			 * JSONObject(); data.put("label", rotation); data.put("value", rotation);
			 * rotationsJson.put(data); } attribut1.put("attributeName", "Rotation");
			 * attribut1.put("fieldName", "rotation"); attribut1.put("data", rotationsJson);
			 * rootObject.put(attribut1);
			 */
			/*
			 * if (mocId != null) { List<String> flangeTypes =
			 * dao.getTab4DatasFromFlangeMaster(mocId, seriesId); JSONObject attribut5 = new
			 * JSONObject(); JSONArray flangeTypesJson = new JSONArray(); for (String
			 * flangeType : flangeTypes) { JSONObject data = new JSONObject();
			 * data.put("label", flangeType); data.put("value", flangeType);
			 * flangeTypesJson.put(data); } attribut5.put("attributeName", "Flange Type");
			 * attribut5.put("fieldName", "flangeType"); attribut5.put("data",
			 * flangeTypesJson); rootObject.put(attribut5);
			 * 
			 * }
			 */
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rootObject;
	}

	@Override
	public ApplicationResponse getVariants(Integer seriesId, Integer modelId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<String> tempList = new ArrayList<String>();
			HashMap<String, List<String>> variantMap = new HashMap<String, List<String>>();

			/*
			 * variantMap = commonList.getVariantForModelVarientsList(modelId);
			 * if (variantMap == null || variantMap.isEmpty()) variantMap =
			 * commonList.getVariantList(seriesId);
			 */

			JSONArray variants = new JSONArray();
			Iterator<Entry<String, List<String>>> itr = variantMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, List<String>> pairs = itr.next();
				tempList = pairs.getValue();
				JSONObject variant = new JSONObject();
				JSONArray variantData = new JSONArray();
				for (int i = 0; i < tempList.size(); i++) {
					JSONObject data = new JSONObject();
					data.put("label", tempList.get(i));
					data.put("value", tempList.get(i));
					variantData.put(data);
				}

				variant.put("variantName", pairs.getKey());
				variant.put("data", variantData);
				variants.put(variant);
			}
			JSONObject rootObject = new JSONObject();
			rootObject.put("variants", variants);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, rootObject.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getUserCompany() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<ComboBox> models = new ArrayList<>();
			List<Tbl58UserCompany> tbl58UserCompanies = dao.getUserCompany();
			for (Tbl58UserCompany tbl58UserCompany : tbl58UserCompanies) {
				ComboBox comboBox = new ComboBox();
				comboBox.setLabel(tbl58UserCompany.getTbl28CompanyMaster().getCompanyName());
				comboBox.setValue(tbl58UserCompany.getTbl28CompanyMaster().getLogo());
				models.add(comboBox);
			}

			long total = models.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, models, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public ApplicationResponse showDxf(String requestPayload, boolean download)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();

		String path = "";
		try {
		} catch (Exception e) {

			log.error(e.getMessage(), e);
		}

		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
				ApplicationConstants.DATA_LOAD_SUCCESS_MSG, path);
		return applicationResponse;
	}

	private List<MocData> generateGenericBom(int offerPumpId, int seriesId, String shaftGroup, String mocStd, int stage,
			Integer variantId) {

		List<MocData> ans = new ArrayList();
		try {

			List<Tbl03GenericBomData> genericBomDatas = dao.getGenericbomData(seriesId, shaftGroup, mocStd, stage,
					variantId);

			for (Tbl03GenericBomData tbl03GenericBomData : genericBomDatas) {
				MocData mocData = new MocData();

				String specification = tbl03GenericBomData.getTbl03MaterialMaster().getSpecification();

				mocData.setItemNo(tbl03GenericBomData.getItemNo());
				mocData.setDescription(tbl03GenericBomData.getDescription());
				mocData.setQty(tbl03GenericBomData.getQty());
				mocData.setQtyUom(tbl03GenericBomData.getQtyUom());
				mocData.setMaterialDescription(specification);
				ans.add(mocData);
			}
			System.err.println("");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	/*private Tbl80TemplateMaster getTemplateMaster(Tbl13Barepumpga tbl13Barepumpga, Tbl15Pumpsetga tbl15Pumpsetga,
			Tbl18Csd tbl18Csd, String drw_TYPE) {

		Tbl80TemplateMaster tbl80TemplateMaster = new Tbl80TemplateMaster();
		try {
			switch (drw_TYPE.toUpperCase()) {
			case "CSD":
				tbl80TemplateMaster = tbl18Csd.getTbl80TemplateMaster();
				break;
			case "BAREPUMP":
				tbl80TemplateMaster = tbl13Barepumpga.getTbl80TemplateMaster();
				break;
			case "PUMPSET":
				tbl80TemplateMaster = tbl15Pumpsetga.getTbl80TemplateMaster();
				break;

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl80TemplateMaster;
	}*/
}

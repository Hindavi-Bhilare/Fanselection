
package com.velotech.fanselection.others.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.velotech.fanselection.selection.models.Viscosity;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.VelotechUtil;
import com.velotech.fanselection.utils.ViscosityUtil;

@Service
@Transactional
public class ViscosityCorrectionServiceImpl implements ViscosityCorrectionService {

	private static Logger log = LogManager.getLogger(ViscosityCorrectionServiceImpl.class.getName());

	@Autowired
	private VelotechUtil velo;

	@Autowired
	private ViscosityUtil viscosityUtil;

	@Override
	public ApplicationResponse calculateViscosity(Viscosity viscosity) {

		ApplicationResponse applicationResponse = new ApplicationResponse();

		try {
			JSONObject rootObject = new JSONObject();
			java.text.DecimalFormat twoDForm = new java.text.DecimalFormat("#.##");
			String tempFlow = "-", tempHead1 = "-", tempHead2 = "-", tempHead3 = "-", tempHead4 = "-";

			viscosity = viscosityUtil.viscosityCorrectionFactor(viscosity);

			if (viscosity.getERROR() == null) {

				double converted_Flow = viscosity.getFlow() / viscosity.getCQ();
				converted_Flow = velo.convertFlow(converted_Flow, "USGPM", "m3/hr");
				converted_Flow = velo.convertFlow(converted_Flow, "m3/hr", viscosity.getFlow_ucom());

				double converted_Head1 = viscosity.getHead() / viscosity.getCH1();
				converted_Head1 = velo.convertHead(converted_Head1, "ft", "m");
				converted_Head1 = velo.convertHead(converted_Head1, "m", viscosity.getHead_ucom());

				double converted_Head2 = viscosity.getHead() / viscosity.getCH2();
				converted_Head2 = velo.convertHead(converted_Head2, "ft", "m");
				converted_Head2 = velo.convertHead(converted_Head2, "m", viscosity.getHead_ucom());

				double converted_Head3 = viscosity.getHead() / viscosity.getCH3();
				converted_Head3 = velo.convertHead(converted_Head3, "ft", "m");
				converted_Head3 = velo.convertHead(converted_Head3, "m", viscosity.getHead_ucom());

				double converted_Head4 = viscosity.getHead() / viscosity.getCH4();
				converted_Head4 = velo.convertHead(converted_Head4, "ft", "m");
				converted_Head4 = velo.convertHead(converted_Head4, "m", viscosity.getHead_ucom());

				tempFlow = "  " + twoDForm.format(converted_Flow) + " " + viscosity.getFlow_ucom() + "";
				tempHead1 = "  " + twoDForm.format(converted_Head1) + " " + viscosity.getHead_ucom() + "";
				tempHead2 = " " + twoDForm.format(converted_Head2) + " " + viscosity.getHead_ucom() + "";
				tempHead3 = " " + twoDForm.format(converted_Head3) + " " + viscosity.getHead_ucom() + "";
				tempHead4 = " " + twoDForm.format(converted_Head4) + " " + viscosity.getHead_ucom() + "";

				JSONArray dataCollection = new JSONArray();

				JSONObject result = new JSONObject();

				result.put("Terms", "Flow ( CQ )");
				result.put("CorrectionFactors", twoDForm.format(viscosity.getCQ()));
				result.put("WaterPerformance", tempFlow);
				result.put("message", "null");
				dataCollection.put(result);

				result = new JSONObject();
				result.put("Terms", "Head ( CH@0.6XQNW )");
				result.put("CorrectionFactors", twoDForm.format(viscosity.getCH1()));
				result.put("WaterPerformance", tempHead1);
				dataCollection.put(result);

				result = new JSONObject();
				result.put("Terms", "Head ( CH@0.8XQNW )");
				result.put("CorrectionFactors", twoDForm.format(viscosity.getCH2()));
				result.put("WaterPerformance", tempHead2);
				dataCollection.put(result);

				result = new JSONObject();
				result.put("Terms", "Head  ( CH@1.0XQNW )");
				result.put("CorrectionFactors", twoDForm.format(viscosity.getCH3()));
				result.put("WaterPerformance", tempHead3);
				dataCollection.put(result);

				result = new JSONObject();
				result.put("Terms", "Head ( CH@1.2XQNW )");
				result.put("CorrectionFactors", twoDForm.format(viscosity.getCH4()));
				result.put("WaterPerformance", tempHead4);
				dataCollection.put(result);

				result = new JSONObject();
				result.put("Terms", "Efficiency ( CE ) ");
				result.put("CorrectionFactors", twoDForm.format(viscosity.getCE()));
				dataCollection.put(result);

				rootObject.put("success", "true");
				rootObject.put("message", "Loaded data");
				rootObject.put("data", dataCollection);

			} else {

				JSONArray dataCollection = new JSONArray();

				JSONObject result = new JSONObject();

				result.put("Terms", "Flow ( CQ )");
				result.put("CorrectionFactors", "-");
				result.put("WaterPerformance", "-");
				result.put("message", viscosity.getERROR());
				dataCollection.put(result);

				result = new JSONObject();
				result.put("Terms", "Head ( CH@0.6XQNW )");
				result.put("CorrectionFactors", "-");
				result.put("WaterPerformance", "-");
				dataCollection.put(result);

				result = new JSONObject();
				result.put("Terms", "Head ( CH@0.8XQNW )");
				result.put("CorrectionFactors", "-");
				result.put("WaterPerformance", "-");
				dataCollection.put(result);

				result = new JSONObject();
				result.put("Terms", "Head  ( CH@1.0XQNW )");
				result.put("CorrectionFactors", "-");
				result.put("WaterPerformance", "-");
				dataCollection.put(result);

				result = new JSONObject();
				result.put("Terms", "Head ( CH@1.2XQNW )");
				result.put("CorrectionFactors", "-");
				result.put("WaterPerformance", "-");
				dataCollection.put(result);

				result = new JSONObject();
				result.put("Terms", "Efficiency ( CE ) ");
				result.put("CorrectionFactors", "-");
				dataCollection.put(result);

				rootObject.put("success", "true");
				rootObject.put("message", viscosity.getERROR());
				rootObject.put("data", dataCollection);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG,
					rootObject.toString());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}

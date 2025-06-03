package com.velotech.fanselection.velotech;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.octomix.josson.Josson;

public class jossonExp {

	public jossonExp() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws JsonProcessingException, JSONException {

	/*	String a ="{\"selectionModel\":{\"dp_searchCriteria\":\"PerformanceSpeed\",\"dp_speed\":0,\"dp_FlowConverted\":5,\"constant\":\"Flow\",\"dp_orientation\":\"Horizontal\",\"dp_head\":30,\"dp_min_head\":\"\",\"headMissToleranceMax\":30,\"dp_temperature\":20,\"dp_max_flow\":\"\",\"dp_totalQty\":1,\"dp_HeadConverted\":30,\"flowMissToleranceMin\":0,\"dp_uom_head\":\"m\",\"headMissToleranceMin\":-10,\"selectionTypeId\":3,\"phase\":0,\"seriesSelected\":[\"280\"],\"dp_spgr\":0.9981,\"dp_uom_flow\":\"m3/hr\",\"dp_HeadPerPumpWaterVertical\":0,\"selectionRange\":\"Design\",\"dp_frequency\":\"50\",\"dp_driver_sizing\":\"%ofBKW\",\"dp_serviceFactor\":1,\"dp_min_soh\":\"\",\"checkFlowMissTolerance\":false,\"dp_flow\":5,\"dp_uom_power\":\"kW\",\"dp_npsha\":\"\",\"checkHeadMissTolerance\":true,\"flowMissToleranceMax\":0,\"dp_max_soh\":\"\",\"powerCutOff\":\"\",\"dp_NPSHAConverted\":0,\"dp_AddEff\":\"\"},\"tbl14PrimemoverMaster\":{\"moc\":\"C.I.\",\"framesize\":\"SMG-80\",\"eff\":0.81,\"primemoverId\":24,\"series\":\"SMG\",\"primemoverType\":\"Motor\",\"specification\":\"TEFC\",\"weight\":\"9.02\",\"power\":0.75,\"uuid\":\"EDCBFD29-30B6-49C2-940A-52A7C7B55541\",\"speed\":2808},\"data\":{\"B1\":{\"expression\":\"calc(if(stg=2|stg=3|stg=4,365,if(stg=5|stg=6|stg=7|stg=8,472,if(stg=9,486,if(stg=10|stg=11|stg=12,585,if(stg=13|stg=14|stg=15|stg=16,693,if(stg=18|stg=20|stg=22,847,if(stg=24|stg=26,955,if(stg=29,1117,if(stg=32,1139,if(stg=36,1247,0)))))))))),stg:$.selectedPump.stg)\"}},\"tbl02Modelmaster\":{\"isFixPower\":true,\"stageType\":\"Multi\",\"minStage\":2,\"rotation\":\"CW\",\"suction\":\"34\",\"shaftDia\":25,\"availability\":true,\"uuid\":\"328F458D-B9E4-4091-B0CF-69B7F09649E1\",\"shaftGroup\":\"G1\",\"pumpModel\":\"SCR5\",\"maxStage\":36,\"stageFactor\":1,\"discharge\":\"34\",\"make\":\"ShaktiPumps\"},\"selectedPump\":{\"operatingRange\":\"0;8.04;0;8.04;0;8.04\",\"overallPowerDP\":0,\"motorSeries\":\"SMG\",\"pole\":\"2\",\"soh\":\"33.42\",\"powerDp\":\"0.58\",\"primemoverId\":24,\"headEq\":\"[33.42036856072957,-0.16552313343083563,-0.19352672499276202,-0.005605218003068868];[33.42036856072951,-0.16552313343069247,-0.19352672499280982,-0.005605218003064882];[33.42036856072951,-0.16552313343069247,-0.19352672499280982,-0.005605218003064882]\",\"discharge\":\"34\",\"primemoverCal\":0.58,\"id\":0,\"primeMoverID\":24,\"motorSeriesId\":81,\"phase\":3,\"impellerDia2\":\"2\",\"perfCurvNoId\":5674,\"effMinMax\":\"0.01;8.041;0.01;8.041;0.01;8.041\",\"nearMissReason\":\"\",\"stg\":5,\"motorPowerHp\":1,\"dp_uom_flow\":\"m3/hr\",\"dpFlow\":\"5\",\"motorEffciencyDP\":0,\"diaList\":\"2;2.0;2.0\",\"trimDia\":2,\"minDia\":2,\"stgType\":\"Multi\",\"npshrDp\":\"1.35\",\"currentEq\":\"[0.0,0.0,0.0]\",\"displayPumpName\":\"SCR5-5Stage\",\"overallEffEq\":\"[0.0,0.0,0.0]\",\"pumpModelId\":5205,\"headMinMax\":\"0.01;8.04;0.01;8.04;0.01;8.04\",\"primeMoverPower\":\"0.75\",\"npshrMinMax\":\"0.99;8.13;0.99;8.04;0.99;8.04\",\"description\":\"SMG-3PH-2P-50Hz-IE2-415V-Shakti\",\"pumpTypeId\":280,\"shaftGroup\":\"G1\",\"effEq\":\"[0.0018723175786915375,0.27049095558382374,-0.036123702243584244,0.0014445050515372623];[0.001872317578645484,0.2704909555838856,-0.03612370224360223,0.0014445050515386857];[0.001872317578645484,0.2704909555838856,-0.03612370224360223,0.0014445050515386857]\",\"speed\":\"2900\",\"pumpModel\":\"SCR5\",\"npshrEq\":\"[0.6590645559459207,0.03593707091290621,0.0012389190268782338,0.0038230693066403014];[0.6590645559456596,0.03593707091313331,0.0012389190268238398,0.003823069306644143];[0.6590645559456596,0.03593707091313331,0.0012389190268238398,0.003823069306644143]\",\"isStdModel\":true,\"bepPump\":\"5.68\",\"dp_uom_head\":\"m\",\"primeMoverPowerHP\":\"1\",\"trimRatio\":0,\"motorPower\":0.75,\"pumpTypeUuid\":\"E49F5764-5ABB-4302-B23B-323D71313982\",\"pumpModelUuid\":\"328F458D-B9E4-4091-B0CF-69B7F09649E1\",\"motorTypeUuid\":\"A4CC0794-1DBC-41F1-9F6F-081BBC4C2466\",\"powerMinMax\":\"0.01;8.04;0.01;8.04;0.01;8.04\",\"bepEff\":0.6376450538635257,\"bepFlowPercentage\":0.88,\"suction\":\"34\",\"primeMoverUuid\":\"EDCBFD29-30B6-49C2-940A-52A7C7B55541\",\"motorModelName\":\"SMG-0.75\",\"motorCurrentDP\":0,\"powerEq\":\"[0.30639233733508786,0.0548353327720349,0.003808377036988107,-7.149196628695083E-4];[0.30639233733509225,0.05483533277203101,0.003808377036988943,-7.149196628695601E-4];[0.30639233733509225,0.05483533277203101,0.003808377036988943,-7.149196628695601E-4]\",\"overallEfficiency\":0,\"maxDia\":2,\"efficiencyDp\":\"0.6318\",\"series\":\"SCR\",\"dp_uom_power\":\"kW\",\"powerDpMax\":\"0.63\",\"motorEfficiencyEq\":\"[0.0,0.0,0.0]\",\"pumpShaftDia\":\"25.0\",\"perfCurvNo\":\"SCR-5\",\"dpHead\":\"27.05\",\"motorOverload\":0}}";
		JSONObject jo = new JSONObject();
		jo.put("B1", 4);
		jo.put("B2", 5);
		jo.put("stg", 4);
		
		Josson josson = Josson.fromJsonString(jo.toString());*/
		
	/*	String expression = josson.getString("flatten('_').~'.*_expression$'*.get(..).map(key::value).mergeObjects().unflatten('_')"
				+ ".concat('field('," + "        entries()" + "        .concat(key," + "                '.field('," + "                value"
				+ "                  .entries()" + "                  .concat(key,"
				+ "                          if([isNotEmpty(value.expression)],"
				+ "                             concat(': eval(', key, '.expression)')," + "                             concat('.field(',"
				+ "                                    value" + "                                      .entries()"
				+ "                                      .concat(key, ': eval(', key, '.expression)')"
				+ "                                      .join(',')," + "                                    ')'"
				+ "                             )" + "                          )" + "                   )" + "                  .join(','),"
				+ "                ')'" + "         )" + "        .join(',')," + "        ')'" + " )");*/
		
		
		//JsonNode node1 = josson.getNode(expression);
		//System.out.println(node1.toString());

		
	//	System.out.println(josson.getNode("calc(if(stg.in(2),256,500),stg:$.stg)"));
		
		//System.out.println(josson.getNode("calc(if(stg=2|stg=3|stg=4,365, if(stg=5|stg=6|stg=7|stg=8,472, if(stg=9,486, if(stg=10|stg=11|stg=12,585, if(stg=13|stg=14|stg=15|stg=16,693, if(stg=18|stg=20|stg=22,847,if(stg=24|stg=26,955, if(stg=29,1117, if(stg=32,1139, if(stg=36,1247,0)))))))))),stg:$.stg)"));
		
		//System.out.println(josson.getNode("calc(if(stg=2,19.2, if(stg=3,20.1, if(stg=4,20.5, if(stg=5,23.4, if(stg=6,25.4, if(stg=7,26.1,if(stg=8,26.9, if(stg=9,33.2, if(stg=10,33.6, if(stg=11,35.9, if(stg=12,36.3, if(stg=13,36.9, if(stg=14,37.6, if(stg=15,365, if(stg=16,38.8, if(stg=18,75.1, if(stg=20,76.9, if(stg=22,56.6, if(stg=24,58.7, if(stg=26,60, if(stg=29,61.9, if(stg=32,76.7, if(stg=2,79.3,0))))))))))))))))))))))),stg:$.selectedPump.stg)"));
		
		//System.out.println(josson.getNode("calc(if(stg=2|stg=3|stg=4|stg=5|stg=6|stg=7|stg=8|stg=9|stg=10|stg=11|stg=12|stg=13|stg=15|stg=17|stg=19,360,if(stg=21|stg=23|stg=25|stg=27|stg=30|stg=33|stg=36,1246,0))stg:$.stg)"));
		
		String jo="{\"selectionModel\":{\"dp_searchCriteria\":\"PerformanceSpeed\",\"dp_speed\":0,\"dp_FlowConverted\":6,\"constant\":\"Flow\",\"dp_orientation\":\"Horizontal\",\"dp_head\":900,\"dp_min_head\":\"\",\"headMissToleranceMax\":30,\"dp_temperature\":20,\"dp_max_flow\":\"\",\"dp_totalQty\":1,\"dp_HeadConverted\":900,\"flowMissToleranceMin\":0,\"dp_uom_head\":\"m\",\"headMissToleranceMin\":-10,\"selectionTypeId\":3,\"phase\":0,\"seriesSelected\":[\"288\"],\"dp_spgr\":0.9981,\"dp_uom_flow\":\"m3/hr\",\"dp_HeadPerPumpWaterVertical\":0,\"selectionRange\":\"Design\",\"dp_frequency\":\"50\",\"dp_driver_sizing\":\"%ofBKW\",\"dp_serviceFactor\":1,\"dp_min_soh\":\"\",\"checkFlowMissTolerance\":false,\"dp_flow\":6,\"dp_uom_power\":\"kW\",\"dp_npsha\":\"\",\"checkHeadMissTolerance\":true,\"flowMissToleranceMax\":0,\"dp_max_soh\":\"\",\"powerCutOff\":\"\",\"dp_NPSHAConverted\":0,\"dp_AddEff\":\"\"},\"tbl14PrimemoverMaster\":{\"moc\":\"C.I.\",\"framesize\":\"STD\",\"eff\":0.76,\"specification\":\"-\",\"weight\":\"49\",\"uuid\":\"43E912A9-F803-4A8B-8E45-BAF352F0854B\",\"powerHp\":7.5,\"voltageTolerance\":\"380\",\"speed\":2878,\"voltage\":380,\"ratedCurrent\":12.8,\"modelName\":\"6MTSF-7.5HP\",\"primemoverId\":100,\"series\":\"4 PREMIUM\",\"primemoverType\":\"Motor\",\"power\":5.5,\"motorCableLength\":5},\"data\":{\"c\":{\"expression\":\"calc(if(series='4PREMIUM',237+21*A,238+42*A),A:$.selectedPump.stg,series:$.tbl14PrimemoverMaster.series)\"},\"reportImage\":\"QF-GA.png\"},\"tbl02Modelmaster\":{\"isFixPower\":true,\"stageType\":\"Multi\",\"minStage\":5,\"rotation\":\"CW\",\"suction\":\"NA\",\"availability\":true,\"uuid\":\"55924640-759D-4B2D-B476-62A378107772\",\"shaftGroup\":\"G1\",\"pumpModel\":\"QF12\",\"maxStage\":110,\"stageFactor\":1,\"discharge\":\"50\",\"make\":\"ShaktiPumps\"},\"tbl27RequirementsDp\":{},\"selectedPump\":{\"operatingRange\":\"0.02;11.42;0.02;11.42;0.02;11.42\",\"overallPowerDP\":0,\"motorSeries\":\"4 PREMIUM\",\"pole\":\"2\",\"soh\":\"1101.92\",\"powerDp\":\"23.25\",\"primemoverId\":100,\"headEq\":\"[1101.9181183853475,-72.43404166068171,8.204439266268373,-0.6610617401282181];[1101.9181183853657,-72.43404166069755,8.204439266271484,-0.6610617401283867];[1101.9181183853657,-72.43404166069755,8.204439266271484,-0.6610617401283867]\",\"discharge\":\"50\",\"primemoverCal\":23.25,\"id\":0,\"primeMoverID\":100,\"motorSeriesId\":50,\"phase\":3,\"impellerDia2\":\"5\",\"perfCurvNoId\":5743,\"effMinMax\":\"0.01;11.4195;0.01;11.4195;0.01;11.4195\",\"nearMissReason\":\"MotorOverload;\",\"stg\":54,\"motorPowerHp\":7.5,\"dp_uom_flow\":\"m3/hr\",\"dpFlow\":\"6\",\"motorEffciencyDP\":0,\"diaList\":\"5;5.0;5.0\",\"trimDia\":5,\"minDia\":5,\"stgType\":\"Multi\",\"npshrDp\":\"0\",\"currentEq\":\"[0.0,0.0,0.0]\",\"displayPumpName\":\"QF12\",\"overallEffEq\":\"[0.0,0.0,0.0]\",\"pumpModelId\":52877,\"headMinMax\":\"0.01;11.42;0.01;11.42;0.01;11.42\",\"primeMoverPower\":\"5.5\",\"npshrMinMax\":\";\",\"description\":\"MTSF-3PH-2P-50Hz-380V-Shakti\",\"pumpTypeId\":288,\"shaftGroup\":\"G1\",\"effEq\":\"[0.02233550644920764,0.2004954039234455,-0.022857551257384826,8.043768133633321E-4];[0.02233550644920239,0.2004954039234519,-0.022857551257386324,8.043768133634225E-4];[0.02233550644920239,0.2004954039234519,-0.022857551257386324,8.043768133634225E-4]\",\"speed\":\"2900\",\"pumpModel\":\"QF12\",\"npshrEq\":\"[];[0.0];[0.0]\",\"isStdModel\":false,\"bepPump\":\"6.9\",\"dp_uom_head\":\"m\",\"primeMoverPowerHP\":\"7.5\",\"trimRatio\":0,\"motorPower\":5.5,\"pumpTypeUuid\":\"920A8712-AC96-4AF3-A12A-060343A5C7F0\",\"pumpModelUuid\":\"55924640-759D-4B2D-B476-62A378107772\",\"motorTypeUuid\":\"50ADF56F-2EC8-45D3-8500-5C2E9FE305C0\",\"powerMinMax\":\"0.01;11.42;0.01;11.42;0.01;11.42\",\"bepEff\":0.5818115234375,\"bepFlowPercentage\":0.87,\"suction\":\"NA\",\"primeMoverUuid\":\"43E912A9-F803-4A8B-8E45-BAF352F0854B\",\"motorModelName\":\"6MTSF-7.5HP\",\"motorCurrentDP\":0,\"powerEq\":\"[10.049781496156989,1.9954564296459223,0.22132086865793976,-0.02638943497329988];[10.049781496157161,1.995456429645711,0.22132086865798817,-0.026389434973302758];[10.049781496157161,1.995456429645711,0.22132086865798817,-0.026389434973302758]\",\"overallEfficiency\":0,\"maxDia\":5,\"efficiencyDp\":\"0.5762\",\"series\":\"QF\",\"dp_uom_power\":\"kW\",\"powerDpMax\":\"26.8\",\"motorEfficiencyEq\":\"[0.0,0.0,0.0]\",\"perfCurvNo\":\"QF12\",\"dpHead\":\"819.88\",\"motorOverload\":4.23},\"tbl1401Motortype\":{\"phase\":3,\"effClass\":\"IE3\",\"series\":\"6MTSF\",\"description\":\"MTSF-3PH-2P-50Hz-380V-Shakti\",\"pole\":2,\"frequency\":50}}";
		
		Josson josson = Josson.fromJsonString(jo.toString());
		
		//System.out.println(josson.getNode("calc(if(series=''4 PREMIUM'',237+21*A,238+42*A),A:$.selectedPump.stg,series:$.tbl14PrimemoverMaster.series)"));
		
		//System.out.println(josson.getNode("calc(if(series='6MTSF',237+21,238+42),series:$.tbl14PrimemoverMaster.series)"));
		
		//System.out.println(josson.getNode("calc(if(stg=1,350,350+90*(stg-1)),stg:$.selectedPump.stg)"));
		
		System.out.println(josson.getNode("calc(if(stg<=1,140,if(stg<=2,140,if(stg<=3,140,if(stg<=4,178,if(stg<=5,178,if(stg<=6,178,if(stg<=7,198,if(stg<=8,198,if(stg<=9,198,if(stg<=10,220,if(stg<=12,220, if(stg<=14,220,if(stg<=16,220,if(stg<=18,260,(stg<=20,260,if(stg<=22,260,0)))))))))))))))),stg:$.selectedPump.stg)"));
		
		//System.out.println(josson.getNode("calc(if(A=4,237+21,238+42),A:$.selectedPump.stg)"));
	
	
	}

}

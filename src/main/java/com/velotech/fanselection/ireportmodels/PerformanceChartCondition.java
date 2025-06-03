
package com.velotech.fanselection.ireportmodels;

import java.util.ArrayList;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.utils.graph.Point;

public class PerformanceChartCondition implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private List<Point> dutyPointList;

	private List<Integer> vfdPointList;

	private String dutyPointPara;

	private String vfdPointPara;

	private int offerPumpId;

	private String uomFlow;

	private String uomHead;

	private String flow;

	private String head;

	private String speed;

	private boolean showDutyPoints;

	private boolean showMinMaxDia;

	private boolean showVFD;

	private boolean showAppRange;

	private Boolean showCurrentGraph;

	private Boolean showOverallEffGraph;

	private Boolean showOverallPowerGraph;

	private String minAppBase;

	private double minAppRange;

	private double maxAppRange;

	private double minQ;

	private double maxQ;

	private double minHead;

	private double maxHead;

	private String headEq;

	private Tbl26OfferFan tbl26OfferFan;

	public PerformanceChartCondition() {

	}

	public PerformanceChartCondition(boolean showDutyPoints, boolean showMinMaxDia, boolean showVFD,
			boolean showAppRange) {
		this.showDutyPoints = showDutyPoints;
		this.showMinMaxDia = showMinMaxDia;
		this.showVFD = showVFD;
		this.showAppRange = showAppRange;
	}

	public PerformanceChartCondition(int offerPumpId) {
		this.offerPumpId = offerPumpId;
	}

	public List<Point> getDutyPointList() {

		return dutyPointList;
	}

	public void setDutyPointList(List<Point> dutyPointList) {

		this.dutyPointList = dutyPointList;
		setFLowHead();
	}

	public String getDutyPointPara() {

		return dutyPointPara;
	}

	public void setDutyPointPara(String dutyPointPara) {

		this.dutyPointPara = dutyPointPara;
		JSONArray tombArray;
		try {
			dutyPointList = new ArrayList<Point>();
			tombArray = new JSONArray(dutyPointPara);
			double flow = 0d;
			double head = 0d;
			Object flowObj;
			Object headObj;
			for (int i = 0; i < tombArray.length(); i++) {
				flow = 0d;
				head = 0d;

				flowObj = tombArray.getJSONObject(i).get("flow");
				headObj = tombArray.getJSONObject(i).get("head");

				if (flowObj.getClass().getName().equals("java.lang.Double"))
					flow = (Double) tombArray.getJSONObject(i).get("flow");
				else if (flowObj.getClass().getName().equals("java.lang.Integer"))
					flow = (Integer) tombArray.getJSONObject(i).get("flow");
				else if (flowObj.getClass().getName().equals("java.lang.String")) {
					try {
						flow = Double.parseDouble((String) tombArray.getJSONObject(i).get("flow"));
					} catch (Exception e) {

					}
				}

				if (headObj.getClass().getName().equals("java.lang.Double"))
					head = (Double) tombArray.getJSONObject(i).get("head");
				else if (headObj.getClass().getName().equals("java.lang.Integer"))
					head = (Integer) tombArray.getJSONObject(i).get("head");
				else if (headObj.getClass().getName().equals("java.lang.String")) {
					try {
						head = Double.parseDouble((String) tombArray.getJSONObject(i).get("head"));
					} catch (Exception e) {

					}
				}

				if (flow != 0d && head != 0d)
					this.dutyPointList.add(new Point(flow, head));
			}

			setFLowHead();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public List<Integer> getVfdPointList() {

		return vfdPointList;
	}

	public void setVfdPointList(List<Integer> vfdPointList) {

		this.vfdPointList = vfdPointList;
		setVFD_Speed();
	}

	public String getVfdPointPara() {

		return vfdPointPara;
	}

	public void setVfdPointPara(String vfdPointPara) {

		this.vfdPointPara = vfdPointPara;
		JSONArray tombArray;
		try {
			vfdPointList = new ArrayList<Integer>();
			tombArray = new JSONArray(vfdPointPara);
			int speed = 0;
			Object speedObj;
			for (int i = 0; i < tombArray.length(); i++) {
				speed = 0;
				speedObj = tombArray.getJSONObject(i).get("speed");

				if (speedObj.getClass().getName().equals("java.lang.Double"))
					speed = Integer.parseInt(String.valueOf(tombArray.getJSONObject(i).get("speed")));
				else if (speedObj.getClass().getName().equals("java.lang.Integer"))
					speed = (Integer) tombArray.getJSONObject(i).get("speed");
				else if (speedObj.getClass().getName().equals("java.lang.String")) {
					try {
						speed = Integer.parseInt((String) tombArray.getJSONObject(i).get("speed"));
					} catch (Exception e) {

					}
				}

				if (speed != 0)
					this.vfdPointList.add(speed);
			}

			setVFD_Speed();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setFLowHead() {

		StringBuilder sbFlow = new StringBuilder();
		StringBuilder sbHead = new StringBuilder();

		for (int i = 0; i < dutyPointList.size(); i++) {
			sbFlow.append(dutyPointList.get(i).getPointCoordinates()[0] + ";");
			sbHead.append(dutyPointList.get(i).getPointCoordinates()[1] + ";");
		}

		this.flow = sbFlow.toString();
		this.head = sbHead.toString();
	}

	public void setVFD_Speed() {

		StringBuilder sbSpedd = new StringBuilder();

		for (int i = 0; i < vfdPointList.size(); i++)
			sbSpedd.append(vfdPointList.get(i) + ";");

		this.speed = sbSpedd.toString();
	}

	public int getOfferPumpId() {

		return offerPumpId;
	}

	public void setOfferPumpId(int offerPumpId) {

		this.offerPumpId = offerPumpId;
	}

	public String getUomFlow() {

		return uomFlow;
	}

	public void setUomFlow(String uomFlow) {

		this.uomFlow = uomFlow;
	}

	public String getUomHead() {

		return uomHead;
	}

	public void setUomHead(String uomHead) {

		this.uomHead = uomHead;
	}

	public boolean isShowDutyPoints() {

		return showDutyPoints;
	}

	public void setShowDutyPoints(boolean showDutyPoints) {

		this.showDutyPoints = showDutyPoints;
	}

	public boolean isShowMinMaxDia() {

		return showMinMaxDia;
	}

	public void setShowMinMaxDia(boolean showMinMaxDia) {

		this.showMinMaxDia = showMinMaxDia;
	}

	public boolean isShowVFD() {

		return showVFD;
	}

	public void setShowVFD(boolean showVFD) {

		this.showVFD = showVFD;
	}

	public boolean isShowAppRange() {

		return showAppRange;
	}

	public void setShowAppRange(boolean showAppRange) {

		this.showAppRange = showAppRange;
	}

	public Boolean getShowCurrentGraph() {

		return showCurrentGraph;
	}

	public void setShowCurrentGraph(Boolean showCurrentGraph) {

		this.showCurrentGraph = showCurrentGraph;
	}

	public Boolean getShowOverallEffGraph() {

		return showOverallEffGraph;
	}

	public void setShowOverallEffGraph(Boolean showOverallEffGraph) {

		this.showOverallEffGraph = showOverallEffGraph;
	}

	public Boolean getShowOverallPowerGraph() {

		return showOverallPowerGraph;
	}

	public void setShowOverallPowerGraph(Boolean showOverallPowerGraph) {

		this.showOverallPowerGraph = showOverallPowerGraph;
	}

	public String getFlow() {

		return flow;
	}

	public void setFlow(String flow) {

		this.flow = flow;
	}

	public String getHead() {

		return head;
	}

	public void setHead(String head) {

		this.head = head;
	}

	public String getSpeed() {

		return speed;
	}

	public void setSpeed(String speed) {

		this.speed = speed;
	}

	public double getMinAppRange() {

		return minAppRange;
	}

	public void setMinAppRange(double minAppRange) {

		this.minAppRange = minAppRange;
	}

	public double getMaxAppRange() {

		return maxAppRange;
	}

	public void setMaxAppRange(double maxAppRange) {

		this.maxAppRange = maxAppRange;
	}

	public String getMinAppBase() {

		return minAppBase;
	}

	public void setMinAppBase(String minAppBase) {

		this.minAppBase = minAppBase;
	}

	public double getMinQ() {

		return minQ;
	}

	public void setMinQ(double minQ) {

		this.minQ = minQ;
	}

	public double getMaxQ() {

		return maxQ;
	}

	public void setMaxQ(double maxQ) {

		this.maxQ = maxQ;
	}

	public double getMinHead() {

		return minHead;
	}

	public void setMinHead(double minHead) {

		this.minHead = minHead;
	}

	public double getMaxHead() {

		return maxHead;
	}

	public void setMaxHead(double maxHead) {

		this.maxHead = maxHead;
	}

	public String getHeadEq() {

		return headEq;
	}

	public void setHeadEq(String headEq) {

		this.headEq = headEq;
	}

	public Tbl26OfferFan getTbl26OfferFan() {

		return tbl26OfferFan;
	}

	public void setTbl26OfferFan(Tbl26OfferFan tbl26OfferFan) {

		this.tbl26OfferFan = tbl26OfferFan;
	}

	public void configureCondition(Tbl26OfferFan tbl26OfferFan) {

		this.tbl26OfferFan = tbl26OfferFan;
		this.dutyPointList = new ArrayList<Point>();
		this.vfdPointList = new ArrayList<Integer>();
		if (this.tbl26OfferFan != null) {
			if (tbl26OfferFan.getTbl27RequirementsDp() != null) {
				try {
					this.uomFlow = tbl26OfferFan.getTbl27RequirementsDp().getUomFlow();
					this.uomHead = tbl26OfferFan.getTbl27RequirementsDp().getUomHead();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		

			if (tbl26OfferFan.getTbl27OfferFanDp() != null) {
				try {
					String[] flow = new String[1];
					String[] head = new String[1];
					String[] speed = new String[1];
					if (tbl26OfferFan.getTbl27OfferFanDp() != null) {
						if (tbl26OfferFan.getTbl27OfferFanDp().getFlow() != null) {
							flow = tbl26OfferFan.getTbl27OfferFanDp().getFlow().split(";");
							head = tbl26OfferFan.getTbl27OfferFanDp().getHead().split(";");
							flow = tbl26OfferFan.getTbl27OfferFanDp().getFlow().split(",");
							head = tbl26OfferFan.getTbl27OfferFanDp().getHead().split(",");
						}
						if (tbl26OfferFan.getTbl27OfferFanDp().getSpeed() != null) {
							speed = tbl26OfferFan.getTbl27OfferFanDp().getSpeed().split(",");
						}
					}
					for (int i = 0; i < flow.length; i++) {
						this.dutyPointList.add(
								new Point(flow[i] != null && !"".equals(flow[i]) ? Double.parseDouble(flow[i]) : 0d,
										head[i] != null && !"".equals(flow[i]) ? Double.parseDouble(head[i]) : 0d));
					}
					for (int i = 0; i < speed.length; i++) {
						if (speed[i] != null && !(Double.parseDouble(speed[i]) == 0)) {
							// this.vfdPointList.add(Integer.parseInt(speed[i]));
							Double sp = Double.parseDouble(speed[i]);
							this.vfdPointList.add(sp.intValue());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}
}


package com.velotech.fanselection.selection.models;

public class SelectedCentrifugalFanPressureGraph { 

	private int id;

	private String pumpModel;

	private String perfCurvNo;

	private Integer stg;

	private String speed;

	private boolean nearMissReason;

	private String nearMissReasonMsg;

	private String efficiencyDpPerPump;

	private String src;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getPumpModel() {

		return pumpModel;
	}

	public void setPumpModel(String pumpModel) {

		this.pumpModel = pumpModel;
	}

	public String getPerfCurvNo() {

		return perfCurvNo;
	}

	public void setPerfCurvNo(String perfCurvNo) {

		this.perfCurvNo = perfCurvNo;
	}

	public Integer getStg() {

		return stg;
	}

	public void setStg(Integer stg) {

		this.stg = stg;
	}

	public String getSpeed() {

		return speed;
	}

	public void setSpeed(String speed) {

		this.speed = speed;
	}

	public boolean isNearMissReason() {

		return nearMissReason;
	}

	public void setNearMissReason(boolean nearMissReason) {

		this.nearMissReason = nearMissReason;
	}

	public String getNearMissReasonMsg() {

		return nearMissReasonMsg;
	}

	public void setNearMissReasonMsg(String nearMissReasonMsg) {

		this.nearMissReasonMsg = nearMissReasonMsg;
	}

	public String getEfficiencyDpPerPump() {

		return efficiencyDpPerPump;
	}

	public void setEfficiencyDpPerPump(String efficiencyDpPerPump) {

		this.efficiencyDpPerPump = efficiencyDpPerPump;
	}

	public String getSrc() {

		return src;
	}

	public void setSrc(String src) {

		this.src = src;
	}

}


package com.velotech.fanselection.selection.models;

public class Viscosity {

	// input
	private double flow;

	private double head;

	private double viscosity;

	private String flow_ucom;

	private String head_ucom;

	private String viscosity_ucom;

	// output
	private double CQ;

	private double CE;

	private double CH1;

	private double CH2;

	private double CH3;

	private double CH4;

	private String ERROR;

	public Viscosity() {
	}

	public Viscosity(double flow, double head, double viscosity, String flow_ucom, String head_ucom, String viscosity_ucom, double cQ, double cE,
			double cH1, double cH2, double cH3, double cH4, String eRROR) {
		this.flow = flow;
		this.head = head;
		this.viscosity = viscosity;
		this.flow_ucom = flow_ucom;
		this.head_ucom = head_ucom;
		this.viscosity_ucom = viscosity_ucom;
		CQ = cQ;
		CE = cE;
		CH1 = cH1;
		CH2 = cH2;
		CH3 = cH3;
		CH4 = cH4;
		ERROR = eRROR;
	}

	public double getFlow() {

		return flow;
	}

	public void setFlow(double flow) {

		this.flow = flow;
	}

	public double getHead() {

		return head;
	}

	public void setHead(double head) {

		this.head = head;
	}

	public double getViscosity() {

		return viscosity;
	}

	public void setViscosity(double viscosity) {

		this.viscosity = viscosity;
	}

	public String getFlow_ucom() {

		return flow_ucom;
	}

	public void setFlow_ucom(String flow_ucom) {

		this.flow_ucom = flow_ucom.replace("Â", "");
	}

	public String getHead_ucom() {

		return head_ucom;
	}

	public void setHead_ucom(String head_ucom) {

		this.head_ucom = head_ucom;
	}

	public String getViscosity_ucom() {

		return viscosity_ucom;
	}

	public void setViscosity_ucom(String viscosity_ucom) {

		this.viscosity_ucom = viscosity_ucom;
	}

	public double getCQ() {

		return CQ;
	}

	public void setCQ(double cQ) {

		CQ = cQ;
	}

	public double getCE() {

		return CE;
	}

	public void setCE(double cE) {

		CE = cE;
	}

	public double getCH1() {

		return CH1;
	}

	public void setCH1(double cH1) {

		CH1 = cH1;
	}

	public double getCH2() {

		return CH2;
	}

	public void setCH2(double cH2) {

		CH2 = cH2;
	}

	public double getCH3() {

		return CH3;
	}

	public void setCH3(double cH3) {

		CH3 = cH3;
	}

	public double getCH4() {

		return CH4;
	}

	public void setCH4(double cH4) {

		CH4 = cH4;
	}

	public String getERROR() {

		return ERROR;
	}

	public void setERROR(String eRROR) {

		ERROR = eRROR;
	}

}


package com.velotech.fanselection.ireportmodels;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

import org.jfree.chart.JFreeChart;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.utils.JFreeChartSvgRenderer;
import com.velotech.fanselection.utils.VelotechUtil;

public class PerformanceChartSheet {

	VelotechUtil velotechUtil = new VelotechUtil();

	private Date offerDate;

	private String pdfRealPath;

	private String customerName;

	private String customerAddress;

	private String offerLogoName;

	private String offerCompanyName;

	private String logoPath;

	private String arrowPath = velotechUtil.getCommonImagePath() + "Above.png";

	private String logo1Path;

	private String consultant;

	private String application;

	private String project;

	private Date enquiryDate;

	private String reference;

	private String offerNo;

	private String offerRev;

	private String offerRevId;

	private String preparedBy;

	private String companyName;

	private String companyAddress;

	private String tagNo;

	private String uomFlow;

	private String uomHead;

	private String uomPower;

	private String uomEff = "%";

	private String uomDimension = "mm";

	private String uomSpeed = "RPM";

	private String uomFrequency = "Hz";

	private Double spgr;

	private Double temperature;

	private String uomTemperature = "Deg C";

	private String fanModel;

	private Integer vanAngle;

	private String motorModel;

	private String primeMoverSeries;

	private double dpFlow;

	private double dpHead;

	private double efficiencyDp;

	private String powerDp;

	private String powerDpMax;

	private String npshrDp;

	private String trimDia;

	private String soh;

	private String npshA;

	private double flow;

	private double head;

	private String speed;

	private int qty;

	private String pole;

	private String solidSize;

	private String maxSolidSize;

	private String frequency;

	private String testingStd;

	private double primeMoverPower;

	private double primeMoverPowerHp;

	private String uomPressure;

	private String suctionPressure;

	private String system;

	private String pumpQtyinSystem;

	private String feetLocation;

	private String packing;

	private String lubrication;

	private String flangeSpecification;

	private String rotation;

	private String impellarDia;

	private String bearingType;

	private String axisOfOperation;

	private String fluidName = "water";

	private JFreeChartSvgRenderer chart;

	private String outputPdfName;

	private String pressureUom;

	private String email;

	private String mobileNo;

	private double primemoverEff;

	private Double ratedCurrent;

	private String startingTorque;

	private Double motorEffDp;

	private Double motorCurrentDp;

	private Double overallEfficiency;

	private Double overallPowerDP;

	private String startingMethod;

	private String voltageTolerance;

	private Integer phase;

	private String articleNo;
	
	private String typeOfDrive;
	
	
	
	
	public PerformanceChartSheet( JFreeChart chart) {

		DecimalFormat decimalFormat = new DecimalFormat("0.##");
		DecimalFormat twoDForm = new DecimalFormat("#.##");

		if (chart != null)
			this.chart = new JFreeChartSvgRenderer(chart);

		try {
			setOutputPdfName("PerformanceChart");
			setLogos();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	

	public PerformanceChartSheet(Tbl26OfferFan tbl26OfferFan, JFreeChart chart) {

		DecimalFormat decimalFormat = new DecimalFormat("0.##");
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		
		if (chart != null)
			this.chart = new JFreeChartSvgRenderer(chart);

		try {
			

			if (tbl26OfferFan.getTbl23OfferRev() != null) {
				try {
					this.offerRev = tbl26OfferFan.getTbl23OfferRev().getRev();
					this.offerRevId = Integer.toString(tbl26OfferFan.getTbl23OfferRev().getId());
					if (tbl26OfferFan.getTbl23OfferRev().getOfferdate() != null)
						this.offerDate = tbl26OfferFan.getTbl23OfferRev().getOfferdate();
					this.consultant = tbl26OfferFan.getTbl23OfferRev().getConsultant();
					if (tbl26OfferFan.getTbl23OfferRev().getTbl52UsermasterByLoginId() != null)
						this.preparedBy = tbl26OfferFan.getTbl23OfferRev().getTbl52UsermasterByLoginId().getUserName();
					this.enquiryDate = tbl26OfferFan.getTbl23OfferRev().getEnquiryDate();
					if (tbl26OfferFan.getTbl23OfferRev().getTbl28CompanyMaster() != null) {
						this.companyAddress = tbl26OfferFan.getTbl23OfferRev().getTbl28CompanyMaster().getAddress();
						this.companyName = tbl26OfferFan.getTbl23OfferRev().getTbl28CompanyMaster()
								.getDocumentCompanyName();
						this.email = tbl26OfferFan.getTbl23OfferRev().getTbl28CompanyMaster().getEmail();
						this.mobileNo = tbl26OfferFan.getTbl23OfferRev().getTbl28CompanyMaster().getMobileNo();
					}
					if (tbl26OfferFan.getTbl23OfferRev().getTbl25CustomerMaster() != null) {
						this.customerName = tbl26OfferFan.getTbl23OfferRev().getTbl25CustomerMaster().getCustomerName();
						this.customerAddress = tbl26OfferFan.getTbl23OfferRev().getTbl25CustomerMaster()
								.getCustomerAddress1();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (tbl26OfferFan != null) {
				this.tagNo = tbl26OfferFan.getTagNo();
				if (tbl26OfferFan.getTbl27RequirementsDp() != null) {
					try {
						this.uomFlow = tbl26OfferFan.getTbl27RequirementsDp().getUomFlow();
						this.uomHead = tbl26OfferFan.getTbl27RequirementsDp().getUomHead();
						this.uomPower = tbl26OfferFan.getTbl27RequirementsDp().getUomPower();

						this.flow = tbl26OfferFan.getTbl27RequirementsDp().getFlow();
						this.head = tbl26OfferFan.getTbl27RequirementsDp().getHead();
						this.spgr = tbl26OfferFan.getTbl27RequirementsDp().getSpgr();
						this.temperature = tbl26OfferFan.getTbl27RequirementsDp().getDp_temperature();
						this.solidSize = tbl26OfferFan.getTbl27RequirementsDp().getSolidSize();
						this.qty = tbl26OfferFan.getTbl27RequirementsDp().getQty();
						this.uomPressure = tbl26OfferFan.getTbl27RequirementsDp().getUomPressure();
						this.system = tbl26OfferFan.getTbl27RequirementsDp().getSystem();
						this.pumpQtyinSystem = tbl26OfferFan.getTbl27RequirementsDp().getPumpQtyinSystem();
						this.testingStd = tbl26OfferFan.getTbl27RequirementsDp().getTestingStd();
						this.application = tbl26OfferFan.getTbl27RequirementsDp().getApplication();
						this.frequency = tbl26OfferFan.getTbl27RequirementsDp().getFrequency();
						this.typeOfDrive = tbl26OfferFan.getTbl27RequirementsDp().getTypeOfDrive();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (tbl26OfferFan.getTbl28SelectedFan() != null) {
					try {

						this.fanModel = tbl26OfferFan.getTbl28SelectedFan().getFanModel();
						this.vanAngle = tbl26OfferFan.getTbl28SelectedFan().getVaneAngle();
						this.rotation = tbl26OfferFan.getTbl28SelectedFan().getRotation();
						this.articleNo = tbl26OfferFan.getTbl28SelectedFan().getFanSetArticleNo();

						this.dpFlow = tbl26OfferFan.getTbl28SelectedFan().getDpFlow();
						this.dpHead = (tbl26OfferFan.getTbl28SelectedFan().getDpPressure());

						this.powerDp = String.valueOf(tbl26OfferFan.getTbl28SelectedFan().getDpPower());
						this.speed = tbl26OfferFan.getTbl28SelectedFan().getSpeed().toString();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (tbl26OfferFan.getTbl28SelectedPrimemover() != null) {
					this.motorModel = tbl26OfferFan.getTbl28SelectedPrimemover().getModelName();
					this.primeMoverSeries = tbl26OfferFan.getTbl28SelectedPrimemover().getSeries();
				}
			}

			setOutputPdfName("PerformanceChart");
			setLogos();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getOfferDate() {

		return offerDate;
	}

	public void setOfferDate(Date offerDate) {

		this.offerDate = offerDate;
	}

	public String getPdfRealPath() {

		return pdfRealPath;
	}

	public void setPdfRealPath(String pdfRealPath) {

		this.pdfRealPath = pdfRealPath;
	}

	public String getCustomerName() {

		return customerName;
	}

	public void setCustomerName(String customerName) {

		this.customerName = customerName;
	}

	public String getCustomerAddress() {

		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {

		this.customerAddress = customerAddress;
	}

	public String getOfferLogoName() {

		return offerLogoName;
	}

	public void setOfferLogoName(String offerLogoName) {

		this.offerLogoName = offerLogoName;
	}

	public String getOfferCompanyName() {

		return offerCompanyName;
	}

	public void setOfferCompanyName(String offerCompanyName) {

		this.offerCompanyName = offerCompanyName;
	}

	public String getLogoPath() {

		return logoPath;
	}

	public void setLogoPath(String logoPath) {

		this.logoPath = logoPath;
	}

	public String getLogo1Path() {

		return logo1Path;
	}

	public void setLogo1Path(String logo1Path) {

		this.logo1Path = logo1Path;
	}

	public String getConsultant() {

		return consultant;
	}

	public void setConsultant(String consultant) {

		this.consultant = consultant;
	}

	public String getApplication() {

		return application;
	}

	public void setApplication(String application) {

		this.application = application;
	}

	public String getProject() {

		return project;
	}

	public void setProject(String project) {

		this.project = project;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getEnquiryDate() {

		return enquiryDate;
	}

	public void setEnquiryDate(Date enquiryDate) {

		this.enquiryDate = enquiryDate;
	}

	public String getReference() {

		return reference;
	}

	public void setReference(String reference) {

		this.reference = reference;
	}

	public String getOfferNo() {

		return offerNo;
	}

	public void setOfferNo(String offerNo) {

		this.offerNo = offerNo;
	}

	public String getOfferRev() {

		return offerRev;
	}

	public void setOfferRev(String offerRev) {

		this.offerRev = offerRev;
	}

	public String getOfferRevId() {

		return offerRevId;
	}

	public void setOfferRevId(String offerRevId) {

		this.offerRevId = offerRevId;
	}

	public String getPreparedBy() {

		return preparedBy;
	}

	public void setPreparedBy(String preparedBy) {

		this.preparedBy = preparedBy;
	}

	public String getCompanyName() {

		return companyName;
	}

	public void setCompanyName(String companyName) {

		this.companyName = companyName;
	}

	public String getCompanyAddress() {

		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {

		this.companyAddress = companyAddress;
	}

	public String getTagNo() {

		return tagNo;
	}

	public void setTagNo(String tagNo) {

		this.tagNo = tagNo;
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

	public String getUomPower() {

		return uomPower;
	}

	public void setUomPower(String uomPower) {

		this.uomPower = uomPower;
	}

	public String getUomEff() {

		return uomEff;
	}

	public void setUomEff(String uomEff) {

		this.uomEff = uomEff;
	}

	public String getUomDimension() {

		return uomDimension;
	}

	public void setUomDimension(String uomDimension) {

		this.uomDimension = uomDimension;
	}

	public String getUomSpeed() {

		return uomSpeed;
	}

	public void setUomSpeed(String uomSpeed) {

		this.uomSpeed = uomSpeed;
	}

	public String getUomFrequency() {

		return uomFrequency;
	}

	public void setUomFrequency(String uomFrequency) {

		this.uomFrequency = uomFrequency;
	}

	public Double getSpgr() {

		return spgr;
	}

	public void setSpgr(Double spgr) {

		this.spgr = spgr;
	}

	public Double getTemperature() {

		return temperature;
	}

	public void setTemperature(Double temperature) {

		this.temperature = temperature;
	}

	public String getFanModel() {

		return fanModel;
	}

	public void setFanModel(String fanModel) {

		this.fanModel = fanModel;
	}

	public Integer getVanAngle() {
		return vanAngle;
	}

	public void setVanAngle(Integer vanAngle) {
		this.vanAngle = vanAngle;
	}

	public String getMotorModel() {

		return motorModel;
	}

	public void setMotorModel(String motorModel) {

		this.motorModel = motorModel;
	}

	public String getPrimeMoverSeries() {

		return primeMoverSeries;
	}

	public void setPrimeMoverSeries(String primeMoverSeries) {

		this.primeMoverSeries = primeMoverSeries;
	}

	public String getTrimDia() {

		return trimDia;
	}

	public void setTrimDia(String trimDia) {

		this.trimDia = trimDia;
	}

	public String getNpshA() {

		return npshA;
	}

	public void setNpshA(String npshA) {

		this.npshA = npshA;
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



	public String getSpeed() {

		return speed;
	}

	public void setSpeed(String speed) {

		this.speed = speed;
	}

	public int getQty() {

		return qty;
	}

	public void setQty(int qty) {

		this.qty = qty;
	}

	public String getPole() {

		return pole;
	}

	public void setPole(String pole) {

		this.pole = pole;
	}

	public String getSolidSize() {

		return solidSize;
	}

	public void setSolidSize(String solidSize) {

		this.solidSize = solidSize;
	}

	public String getMaxSolidSize() {

		return maxSolidSize;
	}

	public void setMaxSolidSize(String maxSolidSize) {

		this.maxSolidSize = maxSolidSize;
	}

	public String getFrequency() {

		return frequency;
	}

	public void setFrequency(String frequency) {

		this.frequency = frequency;
	}

	public String getTestingStd() {

		return testingStd;
	}

	public void setTestingStd(String testingStd) {

		this.testingStd = testingStd;
	}

	public double getPrimeMoverPower() {

		return primeMoverPower;
	}

	public void setPrimeMoverPower(double primeMoverPower) {

		this.primeMoverPower = primeMoverPower;
	}

	public double getPrimeMoverPowerHp() {

		return primeMoverPowerHp;
	}

	public void setPrimeMoverPowerHp(double primeMoverPowerHp) {

		this.primeMoverPowerHp = primeMoverPowerHp;
	}

	public String getUomPressure() {

		return uomPressure;
	}

	public void setUomPressure(String uomPressure) {

		this.uomPressure = uomPressure;
	}

	public String getSuctionPressure() {

		return suctionPressure;
	}

	public void setSuctionPressure(String suctionPressure) {

		this.suctionPressure = suctionPressure;
	}

	public String getSystem() {

		return system;
	}

	public void setSystem(String system) {

		this.system = system;
	}

	public String getPumpQtyinSystem() {

		return pumpQtyinSystem;
	}

	public void setPumpQtyinSystem(String pumpQtyinSystem) {

		this.pumpQtyinSystem = pumpQtyinSystem;
	}

	public String getFeetLocation() {

		return feetLocation;
	}

	public void setFeetLocation(String feetLocation) {

		this.feetLocation = feetLocation;
	}

	public String getPacking() {

		return packing;
	}

	public void setPacking(String packing) {

		this.packing = packing;
	}

	public String getLubrication() {

		return lubrication;
	}

	public void setLubrication(String lubrication) {

		this.lubrication = lubrication;
	}

	public String getFlangeSpecification() {

		return flangeSpecification;
	}

	public void setFlangeSpecification(String flangeSpecification) {

		this.flangeSpecification = flangeSpecification;
	}

	public String getRotation() {

		return rotation;
	}

	public String getImpellarDia() {

		return impellarDia;
	}

	public void setImpellarDia(String impellarDia) {

		this.impellarDia = impellarDia;
	}

	public String getBearingType() {

		return bearingType;
	}

	public void setBearingType(String bearingType) {

		this.bearingType = bearingType;
	}

	public String getAxisOfOperation() {

		return axisOfOperation;
	}

	public void setAxisOfOperation(String axisOfOperation) {

		this.axisOfOperation = axisOfOperation;
	}

	public void setRotation(String rotation) {

		this.rotation = rotation;
	}

	public JFreeChartSvgRenderer getChart() {

		return chart;
	}

	public void setChart(JFreeChartSvgRenderer chart) {

		this.chart = chart;
	}

	public String getOutputPdfName() {

		return outputPdfName;
	}

	public void setOutputPdfName(String outputPdfName) {

		Random randomGenerator = new Random();
		int var = randomGenerator.nextInt(999999);
		this.outputPdfName = outputPdfName + var;
	}

	public void setLogos() {

		try {

			this.logoPath = velotechUtil.getCompanyDocumentPath() + this.getOfferLogoName();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getArrowPath() {

		return arrowPath;
	}

	public void setArrowPath(String arrowPath) {

		this.arrowPath = arrowPath;
	}

	public String getPressureUom() {

		return pressureUom;
	}

	public void setPressureUom(String pressureUom) {

		this.pressureUom = pressureUom;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getMobileNo() {

		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {

		this.mobileNo = mobileNo;
	}

	public double getPrimemoverEff() {

		return primemoverEff;
	}

	public void setPrimemoverEff(double primemoverEff) {

		this.primemoverEff = primemoverEff;
	}

	public Double getRatedCurrent() {

		return ratedCurrent;
	}

	public void setRatedCurrent(Double ratedCurrent) {

		this.ratedCurrent = ratedCurrent;
	}

	public String getStartingTorque() {

		return startingTorque;
	}

	public void setStartingTorque(String startingTorque) {

		this.startingTorque = startingTorque;
	}

	public VelotechUtil getVelotechUtil() {

		return velotechUtil;
	}

	public void setVelotechUtil(VelotechUtil velotechUtil) {

		this.velotechUtil = velotechUtil;
	}

	public double getDpFlow() {

		return dpFlow;
	}

	public void setDpFlow(double dpFlow) {

		this.dpFlow = dpFlow;
	}

	public double getDpHead() {

		return dpHead;
	}

	public void setDpHead(double dpHead) {

		this.dpHead = dpHead;
	}

	public double getEfficiencyDp() {

		return efficiencyDp;
	}

	public void setEfficiencyDp(double efficiencyDp) {

		this.efficiencyDp = efficiencyDp;
	}

	public String getPowerDp() {

		return powerDp;
	}

	public void setPowerDp(String powerDp) {

		this.powerDp = powerDp;
	}

	public String getPowerDpMax() {

		return powerDpMax;
	}

	public void setPowerDpMax(String powerDpMax) {

		this.powerDpMax = powerDpMax;
	}

	public String getNpshrDp() {

		return npshrDp;
	}

	public void setNpshrDp(String npshrDp) {

		this.npshrDp = npshrDp;
	}

	public String getSoh() {

		return soh;
	}

	public void setSoh(String soh) {

		this.soh = soh;
	}

	public Double getMotorCurrentDp() {

		return motorCurrentDp;
	}

	public void setMotorCurrentDp(Double motorCurrentDp) {

		this.motorCurrentDp = motorCurrentDp;
	}

	public Double getOverallEfficiency() {

		return overallEfficiency;
	}

	public void setOverallEfficiency(Double overallEfficiency) {

		this.overallEfficiency = overallEfficiency;
	}

	public Double getOverallPowerDP() {

		return overallPowerDP;
	}

	public void setOverallPowerDP(Double overallPowerDP) {

		this.overallPowerDP = overallPowerDP;
	}

	public String getStartingMethod() {

		return startingMethod;
	}

	public void setStartingMethod(String startingMethod) {

		this.startingMethod = startingMethod;
	}

	public String getVoltageTolerance() {

		return voltageTolerance;
	}

	public void setVoltageTolerance(String voltageTolerance) {

		this.voltageTolerance = voltageTolerance;
	}

	public Integer getPhase() {

		return phase;
	}

	public void setPhase(Integer phase) {

		this.phase = phase;
	}

	public String getArticleNo() {

		return articleNo;
	}

	public void setArticleNo(String articleNo) {

		this.articleNo = articleNo;
	}

	public Double getMotorEffDp() {

		return motorEffDp;
	}

	public void setMotorEffDp(Double motorEffDp) {

		this.motorEffDp = motorEffDp;
	}

	public String getFluidName() {
		return fluidName;
	}

	public void setFluidName(String fluidName) {
		this.fluidName = fluidName;
	}

	public String getUomTemperature() {
		return uomTemperature;
	}

	public void setUomTemperature(String uomTemperature) {
		this.uomTemperature = uomTemperature;
	}



	public String getTypeOfDrive() {
		return typeOfDrive;
	}



	public void setTypeOfDrive(String typeOfDrive) {
		this.typeOfDrive = typeOfDrive;
	}
	
	

}

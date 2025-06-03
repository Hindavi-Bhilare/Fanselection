
package com.velotech.fanselection.ireportmodels;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
//import com.velotech.fanselection.models.Tbl09Performancecurvemaster;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.utils.VelotechUtil;

public class TechnicalDataSheet {

	VelotechUtil velotechUtil = new VelotechUtil();

	private String aeroFoilDesign;

	private Double density;

	private String uomDensity = "kg/m3";

	private String building;

	private String location;

	private String quantityOfParticularFanType;

	private Double requestedCapacity;

	// private String uomCapacity = "m3/hr";

	private Double requestedStaticPressure;

	private Double requestedDuty;

	private Double actualDuty;

	// private String uomRequestedStaticPressure = "mmWC";

	private Double actualCapacity;

	private Double actualStaticPressure;

	// private String uomTotalPressureAtRatedSpeed = "mmWC";

	private Double totalPressure;

	private Integer ratedSpeed;

	// private String uomRatedSpeed = "RPM";

	private Double criticalSpeedOfFan;

	// private String uomCriticalSpeedOfFan = "RPM";

	private Double fanPowerAtRatedSpeed;

	private String uomFanPowerAtRatedSpeed = "BKW";

	private Double staticEfficiency;

	private String uomStaticEfficiency = "%";

	private Double totalEfficiency;

	private Double fanDia;

	// private String uomImpellerDiameter = "mm";

	private Integer numberOfBladesInImpeller;

	private Double outletVelocity;

	private Double outletDynamicPressure;

	private Double impellerRadius;

	private String uomOutletVelocity = "m/s";

	private String typeOfDrive;

	private String impellerMaterial;

	private String casingMaterial;

	private String supportingMaterial;

	private Double selectedMotorRating;

	private String uomSelectedMotorRating = "KW";

	private Double noisLevelAt1mLevel;

	private String uomNoiseLevel = "DBA";

	// Below fields are old

	private String series;

	private String fanType;
	
	private String bladeType;
	
	private String typeOfDriver;

	private Integer selectedSpeed;

	private String mounting;

	private String outputPdfName;

	private String modelName;

	private String calculatedFlow;

	private double pressure;

	private String startingCurrent;

	private Double powerFactor;

	private Double nrvLoss;

	private String wiringImageName;

	private String nrvName;

	private Integer extendedCableLength;

	private Integer motorCableLength;

	private Double motorRatedSpeed;

	private String maximumCableSize;

	private String minimumCableSize;

	private Integer userBoreWellSize;

	private Double minFluidLevel;

	private String cosFullLoad;

	private Double motorEffFullLoad;

	private String fanWeight;

	List<ModelMocDataSheet> modelMocDataSheets;

	List<ModelMocDataSheet> motorMocDataSheets;

	List<ParameterValue> selectedFanVariants;

	private String logoPath;

	private Date offerDate;

	private String pdfRealPath;

	private String customerName;

	private String customerAddress;

	private String offerLogoName;

	private String offerCompanyName;

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

	private String uomSolidSize;

	private String uomPower;

	private String uomMotorPower = "HP";

	private String uomEff = "%";

	private String uomDimension = "mm";

	private String uomSpeed = "RPM";

	private String uomFrequency = "Hz";

	private Double spgr;

	private Double temperature;

	private Double minTemperature;

	private Double maxTemperature;

	private String uomTemperature = "Deg C";

	private String motorModel;

	private String primeMoverSeries;

	private String performanceCurveNo;

	private String discharge;

	private double dpFlow;

	private double dpPressure;

	private double efficiencyDp;

	private String powerDp;

	private String powerDpMax;

	private String trimDia;

	private String soh;

	private double flow;

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

	private String fanQtyinSystem;

	private String feetLocation;

	private String packing;

	private String lubrication;

	private String flangeSpecification;

	private String rotation;

	private String impellarDia;

	private String bearingType;

	private String axisOfOperation;

	private String fluidName = "Water";

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

	private String voltageTolerance;

	private Integer phase;

	private Double sizeCable1;

	private Double sizeCable2;

	private String articleNo;

	private String insulationClass;

	private String enclousreClass;

	private String effClass;

	private String degreeProtection;

	private String fanModel;
	
	public TechnicalDataSheet() {

	}

	public TechnicalDataSheet(Tbl26OfferFan tbl26OfferPump, Tbl23OfferRev tbl23OfferRev,
			Tbl14PrimemoverMaster tbl14PrimemoverMaster, Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster) {

		DecimalFormat decimalFormat = new DecimalFormat("0.##");
		DecimalFormat twoDForm = new DecimalFormat("#.##");

		this.aeroFoilDesign = "Confirmed";
		this.density = 1.2;

		try {

			if (tbl01CentrifugalModelMaster != null) {

				this.series = tbl01CentrifugalModelMaster.getTbl01fantype().getSeries();
				this.fanType = tbl01CentrifugalModelMaster.getTbl01fantype().getFanType();
				this.mounting = tbl01CentrifugalModelMaster.getTbl01fantype().getMounting();
				this.bladeType=tbl01CentrifugalModelMaster.getTbl01fantype().getBladeType();
				//this.typeOfDriver=tbl01CentrifugalModelMaster.getTbl01fantype().getDriveType();
				this.fanDia = tbl01CentrifugalModelMaster.getFanDia();
				this.numberOfBladesInImpeller=tbl01CentrifugalModelMaster.getBlades();
				this.impellerRadius = (fanDia / 2) / 1000;
			}

			
			VelotechUtil velotechUtil = new VelotechUtil();
			actualCapacity=velotechUtil.roundAvoid((velotechUtil.convertFlow(tbl26OfferPump.getTbl28SelectedFan().getDpFlow(), tbl26OfferPump.getTbl27RequirementsDp().getUomFlow(), "m3/hr")),2);
			//actualCapacity = tbl26OfferPump.getTbl28SelectedFan().getDpFlow();
			actualStaticPressure =velotechUtil.roundAvoid(tbl26OfferPump.getTbl28SelectedFan().getDpPressure(),2);
			//actualStaticPressure = velotechUtil.roundAvoid((tbl26OfferPump.getTbl28SelectedFan().getDpPressure()),2);
			
			System.out.println("actualStaticPressure: "+actualStaticPressure);
			
			fanPowerAtRatedSpeed = tbl26OfferPump.getTbl28SelectedFan().getDpPower();

			//this.outletVelocity = velotechUtil.roundAvoid((actualCapacity / 3600) / (3.14 * impellerRadius * impellerRadius), 2);
			this.outletVelocity = velotechUtil.roundAvoid(velotechUtil.velocityInFan(actualCapacity, fanDia),2);

			this.outletDynamicPressure = velotechUtil
					.roundAvoid((((density * outletVelocity * outletVelocity) / 2) / 9.81), 2);
			
			this.outletDynamicPressure =	velotechUtil.convertPressure(this.outletDynamicPressure, "MMWG", tbl26OfferPump.getTbl27RequirementsDp().getUomHead());

			System.out.println("outletDynamicPressure: "+ velotechUtil.convertPressure(this.outletDynamicPressure, "MMWG", tbl26OfferPump.getTbl27RequirementsDp().getUomHead()));

			staticEfficiency=tbl26OfferPump.getTbl28SelectedFan().getDpEff();
//			this.staticEfficiency = velotechUtil.roundAvoid(((actualCapacity / 3600) * actualStaticPressure) / (102 * fanPowerAtRatedSpeed), 4);
//			staticEfficiency=staticEfficiency*100;
			this.totalPressure = (outletDynamicPressure + actualStaticPressure);
			System.out.println("totalPressure: "+totalPressure);
            //velotechUtil.convertPressure(totalPressure, tbl26OfferPump.getTbl27RequirementsDp().getUomHead(), "MMWG");
			this.totalEfficiency = velotechUtil.roundAvoid(((actualCapacity / 3600) * ( velotechUtil.convertPressure(totalPressure, tbl26OfferPump.getTbl27RequirementsDp().getUomHead(), "MMWG"))) / (102 * fanPowerAtRatedSpeed), 4);
			totalEfficiency=totalEfficiency*100;
			setOutputPdfName("TechnicalDataSheet");

			if (tbl23OfferRev != null) {
				try {

					this.offerLogoName = tbl23OfferRev.getTbl28CompanyMaster().getLogo();
					this.offerCompanyName = tbl23OfferRev.getTbl28CompanyMaster().getCompanyName();
					this.offerNo = tbl23OfferRev.getTbl23OfferMaster().getOfferNo();
					this.offerRev = tbl23OfferRev.getRev();
					this.offerRevId = Integer.toString(tbl23OfferRev.getId());
					if (tbl23OfferRev.getOfferdate() != null)
						this.offerDate = tbl23OfferRev.getOfferdate();
					this.consultant = tbl23OfferRev.getConsultant();
					if (tbl23OfferRev.getTbl52UsermasterByLoginId() != null)
						this.preparedBy = tbl23OfferRev.getTbl52UsermasterByLoginId().getUserName();
					this.project = tbl23OfferRev.getProject();
					this.reference = tbl23OfferRev.getReference();
					this.enquiryDate = tbl23OfferRev.getEnquiryDate();
					if (tbl23OfferRev.getTbl28CompanyMaster() != null) {
						this.companyAddress = tbl23OfferRev.getTbl28CompanyMaster().getAddress();
						this.companyName = tbl23OfferRev.getTbl28CompanyMaster().getDocumentCompanyName();
						this.email = tbl23OfferRev.getTbl28CompanyMaster().getEmail();
						this.mobileNo = tbl23OfferRev.getTbl28CompanyMaster().getMobileNo();
					}
					if (tbl23OfferRev.getTbl25CustomerMaster() != null) {
						this.customerName = tbl23OfferRev.getTbl25CustomerMaster().getCustomerName();
						this.customerAddress = tbl23OfferRev.getTbl25CustomerMaster().getCustomerAddress1();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (tbl14PrimemoverMaster != null) {
				try {
					this.primeMoverPower = tbl14PrimemoverMaster.getPower();
					this.primeMoverPowerHp = tbl14PrimemoverMaster.getPowerHp();
					this.motorRatedSpeed = tbl14PrimemoverMaster.getSpeed();

					this.ratedCurrent = tbl14PrimemoverMaster.getRatedCurrent();
					this.startingTorque = tbl14PrimemoverMaster.getStartingTorque();
					this.degreeProtection = tbl14PrimemoverMaster.getEnclousreClass();
					this.effClass = tbl14PrimemoverMaster.getEffClass();
					this.voltageTolerance = tbl14PrimemoverMaster.getVoltageTolerance();
					this.enclousreClass = tbl14PrimemoverMaster.getEnclousreClass();
					this.insulationClass = tbl14PrimemoverMaster.getInsulationClass();

					if (tbl14PrimemoverMaster.getPole() != 0)
						this.pole = tbl14PrimemoverMaster.getPole().toString();
					this.phase = tbl14PrimemoverMaster.getPhase();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			if (tbl26OfferPump != null) {
				this.tagNo = tbl26OfferPump.getTagNo();
				if (tbl26OfferPump.getTbl27RequirementsDp() != null) {
					try {
						this.uomFlow = tbl26OfferPump.getTbl27RequirementsDp().getUomFlow();
						this.uomHead = tbl26OfferPump.getTbl27RequirementsDp().getUomHead();
						this.uomPower = tbl26OfferPump.getTbl27RequirementsDp().getUomPower();
						
						this.flow = tbl26OfferPump.getTbl27RequirementsDp().getFlow();
						this.pressure = tbl26OfferPump.getTbl27RequirementsDp().getHead();

						this.qty = tbl26OfferPump.getTbl27RequirementsDp().getQty();
						this.uomPressure = tbl26OfferPump.getTbl27RequirementsDp().getUomPressure();

						this.system = tbl26OfferPump.getTbl27RequirementsDp().getSystem();
						this.fanQtyinSystem = tbl26OfferPump.getTbl27RequirementsDp().getPumpQtyinSystem();
						this.testingStd = tbl26OfferPump.getTbl27RequirementsDp().getTestingStd();
						this.application = tbl26OfferPump.getTbl27RequirementsDp().getApplication();
						this.frequency = tbl26OfferPump.getTbl27RequirementsDp().getFrequency();
						this.typeOfDriver = tbl26OfferPump.getTbl27RequirementsDp().getTypeOfDrive();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (tbl26OfferPump.getTbl28SelectedFan() != null) {
					try {
						this.fanModel = tbl26OfferPump.getTbl28SelectedFan().getFanModel();
						this.rotation = tbl26OfferPump.getTbl28SelectedFan().getRotation();
						this.articleNo = tbl26OfferPump.getTbl28SelectedFan().getFanSetArticleNo();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (tbl26OfferPump.getTbl28SelectedFan() != null) {
					try {

						this.dpFlow = tbl26OfferPump.getTbl28SelectedFan().getDpFlow();
						this.dpPressure = tbl26OfferPump.getTbl28SelectedFan().getDpPressure();
						this.ratedSpeed = tbl26OfferPump.getTbl28SelectedFan().getSpeed();
						this.actualDuty = tbl26OfferPump.getTbl28SelectedFan().getDpFlow();
						this.selectedSpeed = tbl26OfferPump.getTbl28SelectedFan().getVaneAngle();
				        this.powerDp = String.valueOf(tbl26OfferPump.getTbl28SelectedFan().getDpPower());

						
//		          this.impellarDia = tbl26OfferPump.getTbl28SelectedFan().getDiaList().split(";")[0];
//		          this.efficiencyDp = velotechUtil.formateEff(Double.parseDouble(tbl26OfferPump.getTbl28SelectedFan().getEfficiencyDp())* 100.0);
//		          this.powerDp = String.valueOf(tbl26OfferPump.getTbl28SelectedFan().getPowerDp());
//		          this.powerDpMax = tbl26OfferPump.getTbl28SelectedFan().getPowerDpMax();
//		          this.trimDia = tbl26OfferPump.getTbl28SelectedFan().getTrimDia();
//		          this.powerDpMax = tbl26OfferPump.getTbl28SelectedFan().getPowerDpMax();
//		          this.speed = tbl26OfferPump.getTbl28SelectedFan().getSpeed().toString();
//		          this.overallEfficiency = tbl26OfferPump.getTbl28SelectedFan().getOverallEfficiency();
//		          this.overallPowerDP = tbl26OfferPump.getTbl28SelectedFan().getOverallPowerDP();
//		          this.motorCurrentDp = tbl26OfferPump.getTbl28SelectedFan().getMotorCurrentDp();
//		          this.motorEffDp = tbl26OfferPump.getTbl28SelectedFan().getMotorEffDp();
//		          this.primemoverEff = tbl26OfferPump.getTbl28SelectedFan().getMotorEffDp();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (tbl26OfferPump.getTbl28SelectedPrimemover() != null) {
					this.motorModel = tbl26OfferPump.getTbl28SelectedPrimemover().getModelName();
					this.primeMoverSeries = tbl26OfferPump.getTbl28SelectedPrimemover().getSeries();
				}

				if (tbl26OfferPump.getTbl27RequirementsDp() != null) {
					this.requestedDuty = tbl26OfferPump.getTbl27RequirementsDp().getFlow();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getAeroFoilDesign() {
		return aeroFoilDesign;
	}

	public void setAeroFoilDesign(String aeroFoilDesign) {
		this.aeroFoilDesign = aeroFoilDesign;
	}

	public String getQuantityOfParticularFanType() {
		return quantityOfParticularFanType;
	}

	public void setQuantityOfParticularFanType(String quantityOfParticularFanType) {
		this.quantityOfParticularFanType = quantityOfParticularFanType;
	}

	public Integer getRatedSpeed() {
		return ratedSpeed;
	}

	public void setRatedSpeed(Integer ratedSpeed) {
		this.ratedSpeed = ratedSpeed;
	}

	public Double getCriticalSpeedOfFan() {
		return criticalSpeedOfFan;
	}

	public void setCriticalSpeedOfFan(Double criticalSpeedOfFan) {
		this.criticalSpeedOfFan = criticalSpeedOfFan;
	}

	public Double getFanPowerAtRatedSpeed() {
		return fanPowerAtRatedSpeed;
	}

	public void setFanPowerAtRatedSpeed(Double fanPowerAtRatedSpeed) {
		this.fanPowerAtRatedSpeed = fanPowerAtRatedSpeed;
	}

	public String getUomFanPowerAtRatedSpeed() {
		return uomFanPowerAtRatedSpeed;
	}

	public void setUomFanPowerAtRatedSpeed(String uomFanPowerAtRatedSpeed) {
		this.uomFanPowerAtRatedSpeed = uomFanPowerAtRatedSpeed;
	}

	public Double getFanDia() {
		return fanDia;
	}

	public void setFanDia(Double fanDia) {
		this.fanDia = fanDia;
	}

	public Double getImpellerRadius() {
		return impellerRadius;
	}

	public void setImpellerRadius(Double impellerRadius) {
		this.impellerRadius = impellerRadius;
	}

	public Integer getNumberOfBladesInImpeller() {
		return numberOfBladesInImpeller;
	}

	public void setNumberOfBladesInImpeller(Integer numberOfBladesInImpeller) {
		this.numberOfBladesInImpeller = numberOfBladesInImpeller;
	}

	public String getTypeOfDrive() {
		return typeOfDrive;
	}

	public void setTypeOfDrive(String typeOfDrive) {
		this.typeOfDrive = typeOfDrive;
	}

	public String getImpellerMaterial() {
		return impellerMaterial;
	}

	public void setImpellerMaterial(String impellerMaterial) {
		this.impellerMaterial = impellerMaterial;
	}

	public String getCasingMaterial() {
		return casingMaterial;
	}

	public void setCasingMaterial(String casingMaterial) {
		this.casingMaterial = casingMaterial;
	}

	public String getSupportingMaterial() {
		return supportingMaterial;
	}

	public void setSupportingMaterial(String supportingMaterial) {
		this.supportingMaterial = supportingMaterial;
	}

	public Double getSelectedMotorRating() {
		return selectedMotorRating;
	}

	public void setSelectedMotorRating(Double selectedMotorRating) {
		this.selectedMotorRating = selectedMotorRating;
	}

	public String getUomSelectedMotorRating() {
		return uomSelectedMotorRating;
	}

	public void setUomSelectedMotorRating(String uomSelectedMotorRating) {
		this.uomSelectedMotorRating = uomSelectedMotorRating;
	}

	public Double getNoisLevelAt1mLevel() {
		return noisLevelAt1mLevel;
	}

	public void setNoisLevelAt1mLevel(Double noisLevelAt1mLevel) {
		this.noisLevelAt1mLevel = noisLevelAt1mLevel;
	}

	public String getUomNoiseLevel() {
		return uomNoiseLevel;
	}

	public void setUomNoiseLevel(String uomNoiseLevel) {
		this.uomNoiseLevel = uomNoiseLevel;
	}

	public Double getDensity() {
		return density;
	}

	public void setDensity(Double density) {
		this.density = density;
	}

	public String getUomDensity() {
		return uomDensity;
	}

	public void setUomDensity(String uomDensity) {
		this.uomDensity = uomDensity;
	}

	public Double getRequestedDuty() {
		return requestedDuty;
	}

	public void setRequestedDuty(Double requestedDuty) {
		this.requestedDuty = requestedDuty;
	}

	public Double getActualDuty() {
		return actualDuty;
	}

	public void setActualDuty(Double actualDuty) {
		this.actualDuty = actualDuty;
	}

	public Double getRequestedCapacity() {
		return requestedCapacity;
	}

	public void setRequestedCapacity(Double requestedCapacity) {
		this.requestedCapacity = requestedCapacity;
	}

	public Double getRequestedStaticPressure() {
		return requestedStaticPressure;
	}

	public void setRequestedStaticPressure(Double requestedStaticPressure) {
		this.requestedStaticPressure = requestedStaticPressure;
	}

	public Double getActualCapacity() {
		return actualCapacity;
	}

	public void setActualCapacity(Double actualCapacity) {
		this.actualCapacity = actualCapacity;
	}

	public Double getActualStaticPressure() {
		return actualStaticPressure;
	}

	public void setActualStaticPressure(Double actualStaticPressure) {
		this.actualStaticPressure = actualStaticPressure;
	}

	public Double getTotalPressure() {
		return totalPressure;
	}

	public void setTotalPressure(Double totalPressure) {
		this.totalPressure = totalPressure;
	}

	public Double getStaticEfficiency() {
		return staticEfficiency;
	}

	public void setStaticEfficiency(Double staticEfficiency) {
		this.staticEfficiency = staticEfficiency;
	}

	public Double getTotalEfficiency() {
		return totalEfficiency;
	}

	public void setTotalEfficiency(Double totalEfficiency) {
		this.totalEfficiency = totalEfficiency;
	}

	public String getUomStaticEfficiency() {
		return uomStaticEfficiency;
	}

	public void setUomStaticEfficiency(String uomStaticEfficiency) {
		this.uomStaticEfficiency = uomStaticEfficiency;
	}


	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getSeries() {

		return series;
	}

	public void setSeries(String series) {

		this.series = series;
	}

	public String getFanType() {

		return fanType;
	}

	public void setFanType(String fanType) {

		this.fanType = fanType;
	}

	public String getMounting() {

		return mounting;
	}

	public void setMounting(String mounting) {

		this.mounting = mounting;
	}

	public String getOutputPdfName() {

		return outputPdfName;
	}

	public List<ModelMocDataSheet> getModelMocDataSheets() {

		return modelMocDataSheets;
	}

	public void setModelMocDataSheets(List<ModelMocDataSheet> modelMocDataSheets) {

		this.modelMocDataSheets = modelMocDataSheets;
	}

	public List<ModelMocDataSheet> getMotorMocDataSheets() {

		return motorMocDataSheets;
	}

	public void setMotorMocDataSheets(List<ModelMocDataSheet> motorMocDataSheets) {

		this.motorMocDataSheets = motorMocDataSheets;
	}

	public void setOutputPdfName(String outputPdfName) {

		Random randomGenerator = new Random();
		int var = randomGenerator.nextInt(999999);
		this.outputPdfName = outputPdfName + var;
	}

	public String getModelName() {

		return modelName;
	}

	public void setModelName(String modelName) {

		this.modelName = modelName;
	}

	public String getCalculatedFlow() {

		return calculatedFlow;
	}

	public void setCalculatedFlow(String calculatedFlow) {

		this.calculatedFlow = calculatedFlow;
	}

	public String getStartingCurrent() {

		return startingCurrent;
	}

	public void setStartingCurrent(String startingCurrent) {

		this.startingCurrent = startingCurrent;
	}

	public Double getPowerFactor() {

		return powerFactor;
	}

	public void setPowerFactor(Double powerFactor) {

		this.powerFactor = powerFactor;
	}

	public Double getNrvLoss() {

		return nrvLoss;
	}

	public void setNrvLoss(Double nrvLoss) {

		this.nrvLoss = nrvLoss;
	}

	public String getWiringImageName() {

		return wiringImageName;
	}

	public void setWiringImageName(String wiringImageName) {

		this.wiringImageName = wiringImageName;
	}

	public String getNrvName() {

		return nrvName;
	}

	public void setNrvName(String nrvName) {

		this.nrvName = nrvName;
	}

	public Integer getExtendedCableLength() {

		return extendedCableLength;
	}

	public void setExtendedCableLength(Integer extendedCableLength) {

		this.extendedCableLength = extendedCableLength;
	}

	public Integer getMotorCableLength() {

		return motorCableLength;
	}

	public void setMotorCableLength(Integer motorCableLength) {

		this.motorCableLength = motorCableLength;
	}

	public Double getMotorRatedSpeed() {

		return motorRatedSpeed;
	}

	public void setMotorRatedSpeed(Double motorRatedSpeed) {

		this.motorRatedSpeed = motorRatedSpeed;
	}

	public String getMaximumCableSize() {

		return maximumCableSize;
	}

	public void setMaximumCableSize(String maximumCableSize) {

		this.maximumCableSize = maximumCableSize;
	}

	public String getMinimumCableSize() {

		return minimumCableSize;
	}

	public void setMinimumCableSize(String minimumCableSize) {

		this.minimumCableSize = minimumCableSize;
	}

	public Integer getUserBoreWellSize() {

		return userBoreWellSize;
	}

	public void setUserBoreWellSize(Integer userBoreWellSize) {

		this.userBoreWellSize = userBoreWellSize;
	}

	public Double getMinFluidLevel() {

		return minFluidLevel;
	}

	public void setMinFluidLevel(Double minFluidLevel) {

		this.minFluidLevel = minFluidLevel;
	}

	public String getCosFullLoad() {

		return cosFullLoad;
	}

	public void setCosFullLoad(String cosFullLoad) {

		this.cosFullLoad = cosFullLoad;
	}

	public Double getMotorEffFullLoad() {

		return motorEffFullLoad;
	}

	public void setMotorEffFullLoad(Double motorEffFullLoad) {

		this.motorEffFullLoad = motorEffFullLoad;
	}

	public String getFanWeight() {

		return fanWeight;
	}

	public void setFanWeight(String fanWeight) {

		this.fanWeight = fanWeight;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
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

	public String getUomSolidSize() {
		return uomSolidSize;
	}

	public void setUomSolidSize(String uomSolidSize) {
		this.uomSolidSize = uomSolidSize;
	}

	public String getUomPower() {
		return uomPower;
	}

	public void setUomPower(String uomPower) {
		this.uomPower = uomPower;
	}

	public String getUomMotorPower() {
		return uomMotorPower;
	}

	public void setUomMotorPower(String uomMotorPower) {
		this.uomMotorPower = uomMotorPower;
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

	public Double getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(Double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public String getUomTemperature() {
		return uomTemperature;
	}

	public void setUomTemperature(String uomTemperature) {
		this.uomTemperature = uomTemperature;
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

	public String getPerformanceCurveNo() {
		return performanceCurveNo;
	}

	public void setPerformanceCurveNo(String performanceCurveNo) {
		this.performanceCurveNo = performanceCurveNo;
	}

	public String getDischarge() {
		return discharge;
	}

	public void setDischarge(String discharge) {
		this.discharge = discharge;
	}

	public double getDpFlow() {
		return dpFlow;
	}

	public void setDpFlow(double dpFlow) {
		this.dpFlow = dpFlow;
	}

	public double getDpPressure() {
		return dpPressure;
	}

	public void setDpPressure(double dpPressure) {
		this.dpPressure = dpPressure;
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

	public String getTrimDia() {
		return trimDia;
	}

	public void setTrimDia(String trimDia) {
		this.trimDia = trimDia;
	}

	public String getSoh() {
		return soh;
	}

	public void setSoh(String soh) {
		this.soh = soh;
	}

	public double getFlow() {
		return flow;
	}

	public void setFlow(double flow) {
		this.flow = flow;
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

	public void setRotation(String rotation) {
		this.rotation = rotation;
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

	public String getFluidName() {
		return fluidName;
	}

	public void setFluidName(String fluidName) {
		this.fluidName = fluidName;
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

	public Double getMotorEffDp() {
		return motorEffDp;
	}

	public void setMotorEffDp(Double motorEffDp) {
		this.motorEffDp = motorEffDp;
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

	public Double getSizeCable1() {
		return sizeCable1;
	}

	public void setSizeCable1(Double sizeCable1) {
		this.sizeCable1 = sizeCable1;
	}

	public Double getSizeCable2() {
		return sizeCable2;
	}

	public void setSizeCable2(Double sizeCable2) {
		this.sizeCable2 = sizeCable2;
	}

	public String getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(String articleNo) {
		this.articleNo = articleNo;
	}

	public String getInsulationClass() {
		return insulationClass;
	}

	public void setInsulationClass(String insulationClass) {
		this.insulationClass = insulationClass;
	}

	public String getEnclousreClass() {
		return enclousreClass;
	}

	public void setEnclousreClass(String enclousreClass) {
		this.enclousreClass = enclousreClass;
	}

	public String getEffClass() {
		return effClass;
	}

	public void setEffClass(String effClass) {
		this.effClass = effClass;
	}

	public String getDegreeProtection() {
		return degreeProtection;
	}

	public void setDegreeProtection(String degreeProtection) {
		this.degreeProtection = degreeProtection;
	}

	public List<ParameterValue> getSelectedFanVariants() {
		return selectedFanVariants;
	}

	public void setSelectedFanVariants(List<ParameterValue> selectedFanVariants) {
		this.selectedFanVariants = selectedFanVariants;
	}

	public Double getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(Double minTemperature) {
		this.minTemperature = minTemperature;
	}

	public VelotechUtil getVelotechUtil() {
		return velotechUtil;
	}

	public void setVelotechUtil(VelotechUtil velotechUtil) {
		this.velotechUtil = velotechUtil;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getSelectedSpeed() {
		return selectedSpeed;
	}

	public void setSelectedSpeed(Integer selectedSpeed) {
		this.selectedSpeed = selectedSpeed;
	}

	public Double getOutletVelocity() {
		return outletVelocity;
	}

	public void setOutletVelocity(Double outletVelocity) {
		this.outletVelocity = outletVelocity;
	}

	public Double getOutletDynamicPressure() {
		return outletDynamicPressure;
	}

	public void setOutletDynamicPressure(Double outletDynamicPressure) {
		this.outletDynamicPressure = outletDynamicPressure;
	}

	public String getFanModel() {
		return fanModel;
	}

	public void setFanModel(String fanModel) {
		this.fanModel = fanModel;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public String getFanQtyinSystem() {
		return fanQtyinSystem;
	}

	public void setFanQtyinSystem(String fanQtyinSystem) {
		this.fanQtyinSystem = fanQtyinSystem;
	}

	public String getUomOutletVelocity() {
		return uomOutletVelocity;
	}

	public void setUomOutletVelocity(String uomOutletVelocity) {
		this.uomOutletVelocity = uomOutletVelocity;
	}

	public String getBladeType() {
		return bladeType;
	}

	public void setBladeType(String bladeType) {
		this.bladeType = bladeType;
	}

	public String getTypeOfDriver() {
		return typeOfDriver;
	}

	public void setTypeOfDriver(String typeOfDriver) {
		this.typeOfDriver = typeOfDriver;
	}

	
	
	
	
	

}

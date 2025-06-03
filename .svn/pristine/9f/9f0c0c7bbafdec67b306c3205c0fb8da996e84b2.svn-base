
package com.velotech.fanselection.dxf.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.octomix.josson.Josson;
import com.velotech.fanselection.dxf.util.DxfUtility;
import com.velotech.fanselection.ireportmodels.MocData;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl1401Motortype;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl80TemplateMaster;
import com.velotech.fanselection.utils.VelotechUtil;

@Component
public class OfferDrawingPojo {

	private DxfUtility dxfUtility = new DxfUtility();

	private Tbl23OfferRev tbl23OfferRev;

	private Tbl26OfferFan tbl26OfferFan;
	
	private Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster;

	//private Tbl18Csd tbl18Csd;

	private Tbl80TemplateMaster tbl80TemplateMaster;

	private List<String> drgParameters = new ArrayList<>();

	private List<String> layerList = new ArrayList<String>();

	private List<String> imageList = new ArrayList<String>();

	//private Tbl13Barepumpga tbl13Barepumpga;

	private Tbl14PrimemoverMaster tbl14PrimemoverMaster;

	private Tbl1401Motortype tbl1401Motortype;

	//private Tbl15Pumpsetga tbl15Pumpsetga;

	private List<MocData> mocDatas = new ArrayList<>();

	private HashMap<String, String> offerPumpTrueBlocks = new HashMap<String, String>();

	private HashMap<String, String> offerMasterTrueBlocks = new HashMap<String, String>();

	boolean order = false;

	private String drawingNumber;

	private String offerLogoName;

	private String offerCompanyName;

	private String companionFangeSelected;

	private String perfImageName;

	private String bomimageName = "Bom" + new Date().toString().replaceAll(" ", "").replaceAll(":", "") + ".jpg";

	boolean pdf = false;

	public boolean performance = false;

	boolean checkPath = false;

	boolean download = false;

	public boolean dataSheet = false;

	private String dxfName;

	private String outputPdfName;

	private String zipPath;

	private String userName;

	int pumpModelId;

	String pumpModel;

	int seriesId;

	String series;

	Random randomGenerator = new Random();

	private String path;

	private HashMap<String, String> selectedPumpDoc = new HashMap<String, String>();

	private String DRW_TYPE;

	private String DXF_PATH;

	private String SVG_PATH;

	private String PDF_PATH;

	private String BOM_PATH;

	private Josson josson;

	private Boolean isTemplate = false;

	VelotechUtil veloObj = new VelotechUtil();

	public Tbl23OfferRev getTbl23OfferRev() {

		return tbl23OfferRev;
	}

	public void setTbl23OfferRev(Tbl23OfferRev tbl23OfferRev) {

		this.tbl23OfferRev = tbl23OfferRev;
		this.offerMasterTrueBlocks = dxfUtility.getAllFieldDataOfferRev(tbl23OfferRev);
		this.offerLogoName = dxfUtility.getOfferLogoName(tbl23OfferRev.getTbl28CompanyMaster());
		this.offerCompanyName = dxfUtility.getOfferCompany(tbl23OfferRev.getTbl28CompanyMaster());
		if (tbl23OfferRev.getStatus().equals("Won") || tbl23OfferRev.getStatus().equals("PartiallyWon")
				|| tbl23OfferRev.getStatus().equals("Lost"))
			this.order = true;
	}

	public Tbl26OfferFan getTbl26OfferFan() {

		return tbl26OfferFan;
	}

	public void setTbl26OfferFan(Tbl26OfferFan tbl26OfferFan) {

		this.tbl26OfferFan = tbl26OfferFan;
		this.offerPumpTrueBlocks =        dxfUtility.getAllFieldDataOfferPump(tbl26OfferFan); 

	
	

	}

	/*public Tbl18Csd getTbl18Csd() {

		return tbl18Csd;
	}

	public void setTbl18Csd(Tbl18Csd tbl18Csd) {

		this.tbl18Csd = tbl18Csd;
	}*/

	public Tbl80TemplateMaster getTbl80TemplateMaster() {

		return tbl80TemplateMaster;
	}

	public void setTbl80TemplateMaster(Tbl80TemplateMaster tbl80TemplateMaster) {

		try {
			if (tbl80TemplateMaster != null) {
				this.dxfName = tbl80TemplateMaster.getTbl90DocumentMaster().getFileName();
				this.tbl80TemplateMaster = tbl80TemplateMaster;
				this.layerList = veloObj.trimData(tbl80TemplateMaster.getLayersList());
				this.imageList = veloObj.trimData(tbl80TemplateMaster.getAttachedFile());
				this.drgParameters = veloObj.trimData(tbl80TemplateMaster.getParameterList());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getLayerList() {

		return layerList;
	}

	public void setLayerList(List<String> layerList) {

		this.layerList = layerList;
	}

	public List<String> getImageList() {

		return imageList;
	}

	public void setImageList(List<String> imageList) {

		this.imageList = imageList;
	}

	/*public Tbl13Barepumpga getTbl13Barepumpga() {

		return tbl13Barepumpga;
	}

	public void setTbl13Barepumpga(Tbl13Barepumpga tbl13Barepumpga) {

		this.tbl13Barepumpga = tbl13Barepumpga;

	}*/

	public Tbl14PrimemoverMaster getTbl14PrimemoverMaster() {

		return tbl14PrimemoverMaster;
	}

	public void setTbl14PrimemoverMaster(Tbl14PrimemoverMaster tbl14PrimemoverMaster) {

		if (tbl14PrimemoverMaster != null && tbl14PrimemoverMaster.getPrimemoverId() != 0) {
			this.tbl14PrimemoverMaster = tbl14PrimemoverMaster;

		}
	}


/*
	public Tbl15Pumpsetga getTbl15Pumpsetga() {

		return tbl15Pumpsetga;
	}

	public void setTbl15Pumpsetga(Tbl15Pumpsetga tbl15Pumpsetga) {

		this.tbl15Pumpsetga = tbl15Pumpsetga;
	}*/

	public List<MocData> getMocDatas() {

		return mocDatas;
	}

	public void setMocDatas(List<MocData> mocDatas) {

		this.mocDatas = mocDatas;
	}



	public HashMap<String, String> getOfferPumpTrueBlocks() {

		return offerPumpTrueBlocks;
	}

	public void setOfferPumpTrueBlocks(HashMap<String, String> offerPumpTrueBlocks) {

		this.offerPumpTrueBlocks = offerPumpTrueBlocks;
	}



	public HashMap<String, String> getOfferMasterTrueBlocks() {

		return offerMasterTrueBlocks;
	}

	public void setOfferMasterTrueBlocks(HashMap<String, String> offerMasterTrueBlocks) {

		this.offerMasterTrueBlocks = offerMasterTrueBlocks;
	}

	
	

	public boolean isOrder() {

		return order;
	}

	public void setOrder(boolean order) {

		this.order = order;
	}

	public String getDrawingNumber() {

		return drawingNumber;
	}

	public void setDrawingNumber(String drawingNumber) {

		this.drawingNumber = drawingNumber;
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

	public String getCompanionFangeSelected() {

		return companionFangeSelected;
	}

	public void setCompanionFangeSelected(String companionFangeSelected) {

		this.companionFangeSelected = companionFangeSelected;
	}



	public String getPerfImageName() {

		return perfImageName;
	}

	public void setPerfImageName(String perfImageName) {

		this.perfImageName = perfImageName;
	}

	public String getBomimageName() {

		return bomimageName;
	}

	public void setBomimageName(String bomimageName) {

		this.bomimageName = bomimageName;
	}

	public boolean isPdf() {

		return pdf;
	}

	public void setPdf(boolean pdf) {

		this.pdf = pdf;
	}

	public boolean isPerformance() {

		return performance;
	}

	public void setPerformance(boolean performance) {

		this.performance = performance;
	}

	public boolean isCheckPath() {

		return checkPath;
	}

	public void setCheckPath(boolean checkPath) {

		this.checkPath = checkPath;
	}

	public boolean isDownload() {

		return download;
	}

	public void setDownload(boolean download) {

		this.download = download;
	}

	public boolean isDataSheet() {

		return dataSheet;
	}

	public void setDataSheet(boolean dataSheet) {

		this.dataSheet = dataSheet;
	}

	public String getDxfName() {

		return dxfName;
	}

	public void setDxfName(String dxfName) {

		this.dxfName = dxfName;
	}

	public String getOutputPdfName() {

		return outputPdfName;
	}

	public void setOutputPdfName() {

		int var = randomGenerator.nextInt(999999);
		this.outputPdfName = this.dxfName.split("\\.")[0] + var;
	}

	public String getZipPath() {

		this.zipPath = veloObj.getUserContextPath() + dxfName.split("\\.")[0] + ".zip";
		return zipPath;
	}

	public void setZipPath(String zipPath) {

		this.zipPath = zipPath;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getPath() {

		if (isPdf())
			path = veloObj.getUserContextPath() + this.outputPdfName + ".pdf";
		else
			path = veloObj.getUserContextPath() + dxfName.split("\\.")[0] + ".jpg";
		return path;
	}

	public void setPath(String path) {

		this.path = path;
	}

	public int getPumpModelId() {

		return pumpModelId;
	}

	public void setPumpModelId(int pumpModelId) {

		this.pumpModelId = pumpModelId;
	}

	public String getPumpModel() {

		return pumpModel;
	}

	public void setPumpModel(String pumpModel) {

		this.pumpModel = pumpModel;
	}

	public int getSeriesId() {

		return seriesId;
	}

	public void setSeriesId(int seriesId) {

		this.seriesId = seriesId;
	}

	public String getSeries() {

		return series;
	}

	public void setSeries(String series) {

		this.series = series;
	}

	public HashMap<String, String> getSelectedPumpDoc() {

		return selectedPumpDoc;
	}

	public void setSelectedPumpDoc(HashMap<String, String> selectedPumpDoc) {

		this.selectedPumpDoc = selectedPumpDoc;
	}

	public String getDXF_PATH() {

		return DXF_PATH;
	}

	public String getDRW_TYPE() {

		return DRW_TYPE;
	}

	public void setDRW_TYPE(String dRW_TYPE) {

		DRW_TYPE = dRW_TYPE;
	}

	public void setDXF_PATH(String dXF_PATH) {

		DXF_PATH = dXF_PATH;
	}

	public String getBOM_PATH() {

		return BOM_PATH;
	}

	public void setBOM_PATH(String bOM_PATH) {

		BOM_PATH = bOM_PATH;
	}

	public String getSVG_PATH() {

		return SVG_PATH;
	}

	public void setSVG_PATH(String sVG_PATH) {

		SVG_PATH = sVG_PATH;
	}

	public String getPDF_PATH() {

		return PDF_PATH;
	}

	public void setPDF_PATH(String pDF_PATH) {

		PDF_PATH = pDF_PATH;
	}

	public void setTbl16CouplingMaster(Object object) {

		// TODO Auto-generated method stub

	}

	public Boolean getIsTemplate() {
		return isTemplate;
	}

	public void setIsTemplate(Boolean isTemplate) {
		this.isTemplate = isTemplate;
	}

	public Tbl1401Motortype getTbl1401Motortype() {
		return tbl1401Motortype;
	}

	public void setTbl1401Motortype(Tbl1401Motortype tbl1401Motortype) {

		if (tbl1401Motortype != null) {
			this.tbl1401Motortype = tbl1401Motortype;

		}
	}

	public Josson getJosson() {
		return josson;
	}

	public void setJosson(Josson josson) {
		this.josson = josson;
	}

	public List<String> getDrgParameters() {
		return drgParameters;
	}

	public void setDrgParameters(List<String> drgParameters) {
		this.drgParameters = drgParameters;
	}

	public Tbl01CentrifugalModelMaster getTbl01CentrifugalModelMaster() {
		return tbl01CentrifugalModelMaster;
	}

	public void setTbl01CentrifugalModelMaster(Tbl01CentrifugalModelMaster tbl01CentrifugalModelMaster) {
		this.tbl01CentrifugalModelMaster = tbl01CentrifugalModelMaster;
		this.pumpModelId = tbl01CentrifugalModelMaster.getId();
		this.pumpModel = tbl01CentrifugalModelMaster.getFanModel();
		this.seriesId = tbl01CentrifugalModelMaster.getTbl01fantype().getId();
		this.series = tbl01CentrifugalModelMaster.getTbl01fantype().getSeries();
	}
	
	
	

}

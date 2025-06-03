
package com.velotech.fanselection.centrifugalFamilyChart.dtos;

import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;

import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.utils.JFreeChartSvgRenderer;
import com.velotech.fanselection.utils.VelotechUtil;

public class FamilyChartSheet {

	@Autowired
	private VelotechUtil velotechUtil;

	private String fanModel;

	private String uomDimension;

	private String logoName;

	private String logoPath;

	private String companyName;
	
	private  JFreeChartSvgRenderer chart;

	public FamilyChartSheet(Tbl01CentrifugalModelMaster modelmaster, JFreeChart chart) {
		
		this.chart = new JFreeChartSvgRenderer(chart);
		 
		 

		//this.chart = new JCommonDrawableRendererImpl((Drawable) chart);

		this.fanModel = modelmaster.getFanModel();
		this.uomDimension = "mm";

	}

	public VelotechUtil getVelotechUtil() {
		return velotechUtil;
	}

	public void setVelotechUtil(VelotechUtil velotechUtil) {
		this.velotechUtil = velotechUtil;
	}

	public String getFanModel() {
		return fanModel;
	}

	public void setFanModel(String fanModel) {
		this.fanModel = fanModel;
	}

	public String getUomDimension() {
		return uomDimension;
	}

	public void setUomDimension(String uomDimension) {
		this.uomDimension = uomDimension;
	}

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public JFreeChartSvgRenderer getChart() {
		return chart;
	}

	public void setChart(JFreeChartSvgRenderer chart) {
		this.chart = chart;
	}


}

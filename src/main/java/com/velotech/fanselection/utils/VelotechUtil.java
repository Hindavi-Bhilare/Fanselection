
package com.velotech.fanselection.utils;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aspose.cad.internal.ay.iF;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import com.octomix.josson.Josson;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.utils.graph.GraphModel;
import com.velotech.fanselection.utils.graph.GraphUtil;
import com.velotech.fanselection.utils.graph.Point;
import com.velotech.fanselection.utils.graph.PolySolve;

import flanagan.complex.Complex;
import flanagan.complex.ComplexPoly;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

/**
 * @author SHARAD
 *
 */
@Component
public class VelotechUtil {

	static Logger log = LogManager.getLogger(VelotechUtil.class.getName());

	@Autowired
	private GraphUtil graphUtil;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private PolySolve polySolve;

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private ExportDXFDrawingToPDF aspose;

	public String getSessionId() {

		String sessionId = null;
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes.getRequest().getSession().getAttribute("sessionId") != null)
			sessionId = (String) requestAttributes.getRequest().getSession().getAttribute("sessionId");
		return sessionId;
	}

	public String getUsername() {

		String username = null;
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes.getRequest().getSession().getAttribute("username") != null)
			username = (String) requestAttributes.getRequest().getSession().getAttribute("username");
		return username;
	}

	public String getLoginId() {

		String loginId = null;
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes.getRequest().getSession().getAttribute("loginId") != null)
			loginId = (String) requestAttributes.getRequest().getSession().getAttribute("loginId");
		return loginId;
	}

	public boolean isAdminUser() {

		boolean isAdminUser = false;
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes.getRequest().getSession().getAttribute("isAdminUser") != null)
			isAdminUser = (boolean) requestAttributes.getRequest().getSession().getAttribute("isAdminUser");
		return isAdminUser;
	}

	public boolean isMarketingHead() {

		boolean isMarketingHead = false;
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes.getRequest().getSession().getAttribute("isMarketingHead") != null)
			isMarketingHead = (boolean) requestAttributes.getRequest().getSession().getAttribute("isMarketingHead");
		return isMarketingHead;
	}

	public String getCompany() {

		String company = null;
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes.getRequest().getSession().getAttribute("company") != null)
			company = requestAttributes.getRequest().getSession().getAttribute("company").toString();
		return company;
	}

	public Integer getCompanyId() {

		Integer companyId = null;
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes.getRequest().getSession().getAttribute("companyId") != null)
			companyId = (Integer) requestAttributes.getRequest().getSession().getAttribute("companyId");
		return companyId;
	}

	/**
	 * returns userTemp path of logged in user
	 *
	 */
	public String getUserRealPath() {

		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		return requestAttributes.getRequest().getSession().getAttribute("userRealPath").toString()
				+ System.getProperty("file.separator");
	}

	/**
	 * returns userTemp path of logged in user
	 *
	 */
	public String getUserContextPath() {

		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		return requestAttributes.getRequest().getSession().getAttribute("userContextPath").toString()
				+ System.getProperty("file.separator");
	}

	/**
	 * returns root path of project
	 *
	 */
	public String getProjectRealPath() {

		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		return requestAttributes.getRequest().getSession().getAttribute("projectRealPath").toString();
	}

	/**
	 * returns real path of specified
	 *
	 */
	public String getRealPath(String string) {

		String reportPath = getProjectRealPath() + string;
		return reportPath;
	}

	public String getReportPath() {

		String path = getProjectRealPath();
		String reportPath = path + "iReports" + System.getProperty("file.separator");
		return reportPath;
	}

	public String getCompanyPath() {

		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		String userPath = getProjectRealPath() + "company" + System.getProperty("file.separator")
				+ requestAttributes.getRequest().getSession().getAttribute("company")
				+ System.getProperty("file.separator");
		return userPath;
	}

	public String getCompanyContextPath() {

		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		String path = servletContext.getContextPath() + System.getProperty("file.separator") + "company"
				+ System.getProperty("file.separator")
				+ requestAttributes.getRequest().getSession().getAttribute("company")
				+ System.getProperty("file.separator");
		return path;
	}

	public String getCompanyContextPathOthers() {

		String path = "";
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		path = requestAttributes.getRequest().getSession().getAttribute("companyContextPath")
				+ System.getProperty("file.separator") + "documents" + System.getProperty("file.separator");

		return path;
	}

	public String getCompanyContextPathDxf(RequestWrapper requestWrapper) {

		String path = "";
		try {
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();

			String dxfFilePath = getCompanyDocumentPath(requestWrapper.getSearchProperty());
			String newFile = getUserRealPath() + requestWrapper.getSearchProperty();
			copyfile(dxfFilePath, newFile);

			String pathOld = requestAttributes.getRequest().getSession().getAttribute("companyContextPath")
					+ System.getProperty("file.separator") + "documents" + System.getProperty("file.separator");

			String name = requestWrapper.getSearchProperty() + getRandomNumber();

			ExportDXFDrawingToPDF aspose = new ExportDXFDrawingToPDF();
			path = aspose.convert(dxfFilePath, name, "Landscape");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return path;
	}

	public String getCompanyDocumentPath(String fileName) {

		String userPath = getCompanyDocumentPath() + fileName;
		return userPath;
	}

	public String getCompanyDocumentPath() {

		String userPath = getCompanyPath() + "documents" + System.getProperty("file.separator");
		return userPath;
	}

	public String getCompanyDocumentContextPath() {

		String userPath = getCompanyContextPath() + "documents" + System.getProperty("file.separator");
		return userPath;
	}

	public String getBlankImagePath() {

		String path = getProjectRealPath();
		String userPath = path + "images" + System.getProperty("file.separator") + "noimage.jpg";
		return userPath;
	}

	public String getCommonImagePath() {

		String path = getProjectRealPath();
		String userPath = path + "images" + System.getProperty("file.separator");
		return userPath;
	}

	public String fileCheck(File file) {

		String filePath = getBlankImagePath();
		try {
			if (file != null && file.exists())
				filePath = file.getPath();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return filePath;
	}

	public String getReport(Collection<?> beanCollection, String jasperName, String outputFileName,
			Map<String, Object> parameters1) {

		String path = getUserContextPath() + outputFileName;
		try {
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(beanCollection);
			JasperPrint jasperPrint = JasperFillManager.fillReport(getCompanyDocumentPath() + jasperName, parameters1,
					beanCollectionDataSource);
			ArrayList<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrint);

			File file1 = new File(getUserRealPath() + outputFileName);
			if (!file1.exists()) {
				file1.createNewFile();
			}
			OutputStream output = new FileOutputStream(file1);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
			exporter.exportReport();
			output.flush();
			output.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return path;
	}

	public String getJsonReport(String jasperName, String outputFileName, Map<String, Object> parameters1) {

		String path = getUserContextPath() + outputFileName;
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(getCompanyDocumentPath() + jasperName, parameters1);
			ArrayList<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrint);

			File file1 = new File(getUserRealPath() + outputFileName);
			if (!file1.exists()) {
				file1.createNewFile();
			}
			OutputStream output = new FileOutputStream(file1);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
			exporter.exportReport();
			output.flush();
			output.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return path;
	}

	public String getBlankJsonReport(String jasperName, String outputFileName) {

		String path = getUserContextPath() + outputFileName;
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(getCompanyDocumentPath() + jasperName,
					new HashMap<String, Object>());
			ArrayList<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrint);

			File file1 = new File(getUserRealPath() + outputFileName);
			if (!file1.exists()) {
				file1.createNewFile();
			}
			OutputStream output = new FileOutputStream(file1);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
			exporter.exportReport();
			output.flush();
			output.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return path;
	}

	public String getReportJdbc(String jasperName, String outputFilePathName, Map<String, Object> parameters) {

		String path = "";
		try {

			Connection con = JdbcConnection.getConnection();

			JasperPrint jasperPrint = JasperFillManager.fillReport(getCompanyDocumentPath() + jasperName, parameters,
					con);

			ArrayList<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrint);
			if (outputFilePathName.equals("")) {
				outputFilePathName = "Output";
			}
			File file1 = new File(outputFilePathName);

			if (!file1.exists()) {
				file1.createNewFile();
			}
			OutputStream output = new FileOutputStream(file1);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
			exporter.exportReport();
			output.flush();
			output.close();

			path = getUserContextPath() + outputFilePathName;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return path;
	}

	public void ireportToxlsx(String jasperpath, String path, Map<String, Object> parameters1) {

		try {
			Connection con = JdbcConnection.getConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(getCompanyDocumentPath() + jasperpath, parameters1,
					con);
			ArrayList<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrint);

			File file1 = new File(path);
			if (!file1.exists()) {
				file1.createNewFile();
			}
			OutputStream output = new FileOutputStream(file1);
			JRXlsExporter exporter = new JRXlsExporter();
			// exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
			// jasperPrintList);
			// exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
			SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			configuration.setOnePagePerSheet(true);
			configuration.setDetectCellType(true);
			configuration.setCollapseRowSpan(false);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			output.flush();
			output.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String mergePDF(List<PdfReader> pdfReaders, String outputPDFName) {

		String PDFpath = "false";
		try {
			PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(getUserRealPath() + outputPDFName));
			copy.open();
			if (null != pdfReaders && !pdfReaders.isEmpty()) {
				Iterator iter = pdfReaders.iterator();
				while (iter.hasNext()) {
					String pageNOs = "";
					PdfReader pdfReader = (PdfReader) iter.next();
					int noOfPages = pdfReader.getNumberOfPages();
					if (noOfPages > 0) {
						pageNOs = getNumderOfPages(noOfPages);
					}
					copy.addDocument(pdfReader, pageNOs);
				}
			}
			copy.setFullCompression();
			copy.close();
			PDFpath = getUserContextPath() + outputPDFName;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return PDFpath;
	}

	private String getNumderOfPages(int noOfPages) {

		String pageNOs = "";
		boolean flag = false;
		for (int i = 0; i < noOfPages; i++) {

			if (flag == true) {
				Integer c = i;
				pageNOs = pageNOs.concat("," + c.toString());
			}
			if (flag == false) {
				Integer c = i;
				pageNOs = c.toString();
				flag = true;
			}
		}
		return pageNOs;
	}

	public String getTagDirectory(int offerRevId, int offerPumpId) {

		String path = getRealPath("");
		String userPath = path + "/OFFER/" + "/" + offerRevId + "/" + offerPumpId + "/";
		return userPath;
	}

	public void ireportToxlsx(Collection<?> beanCollection, String jasperpath, String path,
			Map<String, Object> parameters1) {

		try {
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(beanCollection);
			JasperPrint jasperPrint = JasperFillManager.fillReport(getCompanyDocumentPath() + jasperpath, parameters1,
					beanCollectionDataSource);

			File file1 = new File(path);
			if (!file1.exists()) {
				file1.createNewFile();
			}
			OutputStream output = new FileOutputStream(file1);
			JRXlsxExporter exporter1 = new JRXlsxExporter();
			exporter1.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter1.setExporterOutput(new SimpleOutputStreamExporterOutput(output));

			exporter1.exportReport();
			output.flush();
			output.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * converts the base equation to new equation Logic: coefficients of equation
	 * multiplied by factor coeff(0)*yfactor coeff(1)*new yfactor/xFactor ' '
	 * coeff(length)*new yfactor/xFactor
	 * 
	 * @param xFactor = factor by which X value increases
	 * @param yfactor = factor by which Y value increases
	 * @param terms=  equation of the Efficiency Curve
	 * @return new terms(equation) at req factors
	 * @date: 24th Nov 2012 Code By: Pranav
	 */
	public double[] terms_Conversion(String xFactor, String yfactor, double[] terms) {

		double[] ans = new double[terms.length];
		if (xFactor.equals("") || xFactor.equals("0.0") || yfactor.equals("") || yfactor.equals("0.0")) {
			ans = terms;
		} else {
			double factor = Double.parseDouble(yfactor);
			for (int i = 0; i < terms.length; i++) {
				ans[i] = terms[i] * factor;
				factor = factor / Double.parseDouble(xFactor);
			}
		}
		return ans;
	}

	public double[] convertStringArrayToDoubleArray(String array) {

		double[] terms = null;
		try {
			if (null == array)
				return new double[0];
			String[] termstemp = array.replace("[", "").replace("]", "").split(",");
			terms = new double[termstemp.length];
			for (int j = 0; j < termstemp.length; j++) {
				if (!termstemp[j].equals(""))
					terms[j] = Double.valueOf(termstemp[j]);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return terms;
	}

	public double[] convertStringArrayToDoubleArray(String[] termstemp) {

		double[] terms = null;
		try {
			if (termstemp == null)
				return new double[0];
			;
			terms = new double[termstemp.length];
			for (int j = 0; j < termstemp.length; j++) {
				if (!termstemp[j].equals(""))
					terms[j] = Double.parseDouble(termstemp[j]);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return terms;
	}

	public Integer[] convertStringArrayToIntegerArray(String[] termstemp) {

		Integer[] terms = null;
		try {
			if (termstemp == null)
				return new Integer[0];
			;
			terms = new Integer[termstemp.length];
			for (int j = 0; j < termstemp.length; j++) {
				if (!termstemp[j].equals(""))
					terms[j] = new Integer((int) Double.parseDouble(termstemp[j]));
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return terms;
	}

	public String[] convertDoubleArrayToStringArray(double[] termstemp) {

		String[] terms = null;
		try {
			if (termstemp == null)
				return new String[0];
			;
			terms = new String[termstemp.length];
			for (int j = 0; j < termstemp.length; j++) {
				terms[j] = String.valueOf(termstemp[j]);
				if (termstemp[j] == (int) termstemp[j])
					terms[j] = String.format("%d", (int) termstemp[j]);
				else
					terms[j] = String.format("%s", termstemp[j]);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return terms;
	}

	public String[] convertIntegerArrayToStringArray(Integer[] termstemp) {

		String[] terms = null;
		try {
			if (termstemp == null)
				return new String[0];
			;
			terms = new String[termstemp.length];
			for (int j = 0; j < termstemp.length; j++) {
				if (termstemp[j] != null && !termstemp[j].equals(""))
					terms[j] = String.valueOf(termstemp[j]);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return terms;
	}

	public double convertPower(Double fromValue, String fromType, String toType) {

		double ans = 0;
		double factor = 0;
		if (toType.equalsIgnoreCase("kw")) {
			if (fromType.equalsIgnoreCase("kw"))
				factor = 1;
			else if (fromType.equalsIgnoreCase("hp"))
				factor = 0.746;
		} else {
			if (toType.equalsIgnoreCase("hp"))
				factor = 1 / 0.746;
		}
		if (fromValue.isNaN())
			ans = factor;
		else
			ans = fromValue * factor;
		return ans;
	}

	public double convertFlow(Double fromValue, String fromType, String toType) {

		// to return only factor input type for "fromValue" should be null
		double ans = 0;
		double factor = 0;
		if (toType.equalsIgnoreCase("m3/hr")) {
			if (fromType.equalsIgnoreCase("m3/hr"))
				factor = 1;
			else if (fromType.equalsIgnoreCase("LPS"))
				factor = 3.6;
			else if (fromType.equalsIgnoreCase("LPM"))
				factor = 0.06;
			else if (fromType.equalsIgnoreCase("LPH"))
				factor = 0.001;
			else if (fromType.equalsIgnoreCase("USGPM"))
				factor = 0.2271247;
			else if (fromType.equalsIgnoreCase("IGPM"))
				factor = 0.2727655;
			else if (fromType.equalsIgnoreCase("USGPH"))
				factor = 0.003785412;
			else if (fromType.equalsIgnoreCase("IGPH"))
				factor = 0.004546092;
		} else {
			if (toType.equalsIgnoreCase("LPS"))
				factor = 0.277777777777778;
			else if (toType.equalsIgnoreCase("LPM"))
				factor = 16.6666666666667;
			else if (toType.equalsIgnoreCase("LPH"))
				factor = 1000;
			else if (toType.equalsIgnoreCase("USGPM"))
				factor = 4.40286767577459;
			else if (toType.equalsIgnoreCase("IGPM"))
				factor = 3.66615279425001;
			else if (toType.equalsIgnoreCase("USGPH"))
				factor = 264.172037284185;
			else if (toType.equalsIgnoreCase("IGPH"))
				factor = 219.96915152619;
		}
		if (fromValue.isNaN())
			ans = factor;
		else
			ans = fromValue * factor;
		return ans;
	}

	public List<Double> convertFlow(List<Double> fromValue, String fromType, String toType) {

		List<Double> ans = new ArrayList<Double>();
		if (toType.equals("m3/hr")) {
			if (fromType.equals("m3/hr"))
				return fromValue;
			else if (fromType.equals("LPS")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 3.6);
				return ans;
			} else if (fromType.equals("LPM")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 0.06);
				return ans;
			} else if (fromType.equals("LPH")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 0.001);
				return ans;
			} else if (fromType.equals("USGPM")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 0.2271247);
				return ans;
			} else if (fromType.equals("IGPM")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 0.2727655);
				return ans;
			} else if (fromType.equals("USGPH")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 0.003785412);
				return ans;
			} else if (fromType.equals("IGPH")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 0.004546092);
				return ans;
			}
		} else {
			if (toType.equals("LPS")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 0.277777777777778);
				return ans;
			} else if (toType.equals("LPM")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 16.6666666666667);
				return ans;
			} else if (toType.equals("LPH")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 1000);
				return ans;
			} else if (toType.equals("USGPM")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 4.40286767577459);
				return ans;
			} else if (toType.equals("IGPM")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 3.66615279425001);
				return ans;
			} else if (toType.equals("USGPH")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 264.172037284185);
				return ans;
			} else if (toType.equals("IGPH")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 219.96915152619);
				return ans;
			}
		}
		return ans;
	}

	public double convertHead(Double fromValue, String fromType, String toType) {

		double ans = 0;
		double factor = 0;
		if (toType.equals("m")) {
			if (fromType.equals("m"))
				factor = 1;
			else if (fromType.equals("ft"))
				factor = 0.3048;
		} else
			factor = 3.28083989501312;

		if (fromValue.isNaN())
			ans = factor;
		else
			ans = fromValue * factor;
		return ans;
	}

	public List<Double> convertHead(List<Double> fromValue, String fromType, String toType) {

		List<Double> ans = new ArrayList<Double>();
		if (toType.equals("m")) {
			if (fromType.equals("m"))
				return fromValue;
			else if (fromType.equals("ft")) {
				for (int i = 0; i < fromValue.size(); i++)
					ans.add(fromValue.get(i) * 0.3048);
				return ans;
			}
		} else {
			for (int i = 0; i < fromValue.size(); i++)
				ans.add(fromValue.get(i) * 3.28083989501312);
			return ans;
		}
		return ans;
	}

	public double convertPressure(Double fromValue, String fromType, String toType) {
		PressureUnitConverter pressureUnitConverter = new PressureUnitConverter();
		double ans = 0;

		ans = pressureUnitConverter.convert(fromValue, fromType, toType);

		return ans;
	}

	public double convertPressure(String fromType, String toType) {
		PressureUnitConverter pressureUnitConverter = new PressureUnitConverter();
		double ans = 0;

		ans = pressureUnitConverter.convert(fromType, toType);

		return ans;
	}

	public List<Double> convertPressure(List<Double> fromValue, String fromType, String toType) {
		PressureUnitConverter pressureUnitConverter = new PressureUnitConverter();
		List<Double> ans = new ArrayList<Double>();
		for (Double double1 : fromValue) {
			ans.add(pressureUnitConverter.convert(double1, fromType, toType));
		}
		return ans;
	}

	public double convertViscosity(Double fromValue, String fromType, String toType) {

		double ans = 0;
		double constant1 = 0.0;
		double constant2 = 0.0;

		if (toType.equals("Cst")) {
			if (fromType.equals("Cst"))
				return fromValue;
			else if (fromType.equals("SSU")) {
				if (fromValue >= 100.0) {
					constant1 = 118.8;
					constant2 = 2.272;
				} else {
					constant1 = 176.28;
					constant2 = 2.212;
				}
				ans = (Math.pow(fromValue, 2.0) - constant1 * Math.pow(constant2, 2.0)) / (2 * constant2 * fromValue);
			}
		} else {
			if (fromType.equals("SSU"))
				return fromValue;
			else {
				if (fromValue >= 20.65) {
					constant1 = 118.8;
					constant2 = 2.272;
				} else {
					constant1 = 176.28;
					constant2 = 2.212;
				}
				ans = (Math.sqrt(Math.pow(fromValue, 2.0) + constant1) + fromValue) * constant2;
			}
		}
		return ans;
	}

	public List<Double> convertViscosity(List<Double> fromValue, String fromType, String toType) {

		List<Double> ans = new ArrayList<Double>();
		if (toType.equals("Cst")) {
			if (fromType.equals("Cst"))
				return fromValue;

		} else {

		}
		return ans;
	}

	/**
	 * converts the equation of Head and Power Curve from 1st stage to req stage
	 * Logic: coefficients of equation + multiplied by factor
	 * coeff(0)=(coeff(0)*factor*(stage-1))+coeff(0)
	 * coeff(1)=(coeff(1)*factor*(stage-1))+coeff(1) ' '
	 * coeff(n)=(coeff(n)*factor*(stage-1))+coeff(n)
	 * 
	 * @param stg    = is the stage at which the equation needs to be converted
	 * @param factor = is the factor at which the head increases with stage
	 * @param terms= equation of the Head or Power Curve
	 * @return new terms(equation) at req stage
	 * @date: 12th Nov 2012 Code By: Pranav
	 */
	public double[] terms_H_stage_conversion(String stg, String factor, double[] terms) {

		double[] ans = new double[terms.length];
		for (int i = 0; i < terms.length; i++) {
			ans[i] = (terms[i] * Double.parseDouble(factor) * (Double.parseDouble(stg) - 1)) + terms[i];
		}
		return ans;
	}

	public List<String> castMapToList(Map tempMap) {

		List<String> tempList = new ArrayList<String>();
		Set<String> atempKeySet = tempMap.keySet();
		for (Iterator it = atempKeySet.iterator(); it.hasNext();) {
			String key = (String) it.next();
			tempList.add(key);
		}
		return tempList;
	}

	public List<Double> castMapToDoubleList(Map tempMap) {

		List<Double> tempList = new ArrayList<Double>();
		Set<Double> atempKeySet = tempMap.keySet();
		for (Iterator it = atempKeySet.iterator(); it.hasNext();) {
			Double key = (Double) it.next();
			tempList.add(key);
		}
		return tempList;
	}

	/**
	 * returns Upper or equal and lower or equal value from list Logic:
	 * 
	 * @param valueList       = list of values
	 * @param key             = value which is compared
	 * @param deltaEfficiency = efficiency which needs to be added or subracted
	 * @return ans = upperValue at 0th position and lower value at 1st position
	 * @date: 6th Jan 2012 Code By: Pranav
	 */
	public List<Double> findUpperLower(List<Double> valueList, Double key) {

		List<Double> ans = new ArrayList<Double>();
		List<Double> list = new ArrayList<>(valueList);

		double upper = 0d;
		double lower = 0d;

		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) >= key) {
				upper = list.get(i);
				break;
			}
		}

		Collections.reverse(list);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) <= key) {
				lower = list.get(i);
				break;
			}
		}

		if (lower == 0d)
			lower = upper;

		ans.add(upper);
		ans.add(lower);

		return ans;
	}

	public List<Integer> findUpperLowerPosition(List<Double> valueList, Double key) {

		List<Integer> ans = new ArrayList();

		Integer upper = 0;
		Integer lower = 0;

		Collections.sort(valueList);
		for (int i = 0; i < valueList.size(); i++) {
			if (valueList.get(i) >= key) {
				upper = i;
				break;
			}
		}

		Collections.reverse(valueList);
		for (int i = 0; i < valueList.size(); i++) {
			if (valueList.get(i) <= key) {
				lower = i;
				break;
			}
		}

		if (lower == 0)
			lower = upper;

		ans.add(upper);
		ans.add(lower);

		return ans;
	}

	public double annotation_TRIM(double y_upper, double y_lower, double upperAnnotation, double lowerAnnotation,
			double y_trim) {

		if (upperAnnotation == lowerAnnotation)
			return upperAnnotation;
		else
			return upperAnnotation - ((y_upper - y_trim) * (upperAnnotation - lowerAnnotation) / (y_upper - y_lower));
	}

	public double convertLength(Double fromValue, String fromType, String toType) {

		// base is mm
		double ans = 0;
		double factor = 0;
		if (toType.equalsIgnoreCase("mm")) {
			if (fromType.equalsIgnoreCase("mm"))
				factor = 1;
			else if (fromType.equalsIgnoreCase("In"))
				factor = 25.4;
			else if (fromType.equalsIgnoreCase("ft"))
				factor = 304.8;
			else if (fromType.equalsIgnoreCase("m"))
				factor = 1000;
		} else {
			if (toType.equalsIgnoreCase("In")) {
				factor = 0.03936996;
			} else if (toType.equalsIgnoreCase("ft")) {
				factor = 0.00328083;
			} else if (toType.equalsIgnoreCase("m")) {
				factor = 1 / 1000;
			}
		}
		if (fromValue.isNaN())
			ans = factor;
		else
			ans = fromValue * factor;
		return ans;
	}

	public double convertTemp(Double fromValue, String fromType, String toType) {

		double ans = 0;
		if (toType.equals("Deg C")) {
			if (fromType.equals("Deg C"))
				return fromValue;
			else if (fromType.equals("Deg F"))
				return (fromValue - 32) / .55;
		} else {
			if (toType.equals("Deg C")) {
				return (fromValue * 1.8) + 32;
			}
		}
		return ans;
	}

	/**
	 * converts the equation of Head Curve from base_speed to req_speed Logic:
	 * coefficients of equation multiplied by factor coeff(0)*factor^2
	 * coeff(1)*factor^1 ' ' coeff(length)*factor(n-1)
	 * 
	 * @param req_speed  = is the speed at which the equation needs to be converted
	 * @param base_speed = is the speed at which the equation is
	 * @param terms=     equation of the Head Curve
	 * @return new terms(equation) at req_speed
	 * @date: 12th Nov 2012
	 * @author: Pranav
	 */
	public double[] terms_head_rpm_Conversion(Double req_speed, Double base_speed, double[] terms) {

		double[] ans = new double[terms.length];
		if (req_speed.isNaN() || req_speed.equals(0d)) {
			ans = terms;
		} else {
			int j = 2;
			double factor = req_speed / base_speed;
			for (int i = 0; i < terms.length; i++) {
				ans[i] = terms[i] * Math.pow(factor, j--);
			}
		}
		return ans;
	}

	public double convertTo2Decimal(Double trimVariable) {

		Double ans = 0d;
		try {
			if (!Double.isInfinite(trimVariable)) {
				DecimalFormat df = new DecimalFormat("#.##");
				ans = Double.valueOf(df.format(trimVariable));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;

	}

	public double convertTo1Decimal(Double trimVariable) {

		Double ans = 0d;
		try {
			if (!Double.isInfinite(trimVariable)) {
				DecimalFormat df = new DecimalFormat("#.#");
				ans = Double.valueOf(df.format(trimVariable));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;

	}

	/**
	 * calculate the flow factor to be multiplied to dpFlowSystem Logic: for
	 * parallel pumps flow is divided by pumpQty
	 * 
	 * @param system  = "Standalone" || "Series" || "Parallel"
	 * @param pumpQty = Pump qty in system
	 * @return factor
	 * @date: 25th Nov 2012 Code By: Pranav
	 */
	public double systemFactorFlow(String system, String pumpQty) {

		double ans = 0;
		if (system.equals("Standalone") || system.equals("Series"))
			ans = 1;
		else if (system.equals("Parallel"))
			ans = 1 / Integer.parseInt(pumpQty);
		return ans;
	}

	/**
	 * calculate the head factor to be multiplied to dpFlowSystem Logic: for Series
	 * pumps flow is divided by pumpQty
	 * 
	 * @param system  = "Standalone" || "Series" || "Parallel"
	 * @param pumpQty = Pump qty in system
	 * @return factor
	 * @date: 25th Nov 2012 Code By: Pranav
	 */
	public double systemFactorHead(String system, String pumpQty) {

		double ans = 0;
		if (system.equals("Standalone") || system.equals("ParallelSeries"))
			ans = 1;
		else if (system.equals("Series"))
			ans = 1 / Integer.parseInt(pumpQty);
		return ans;
	}

	public List<Double> Q_rpm_conversion(String req_speed, String base_speed, List<Double> Q) {

		List<Double> ans = new ArrayList<Double>();
		if (req_speed.equals("") || req_speed.equals("0.0")) {
			ans = Q;
		} else {
			for (int i = 0; i < Q.size(); i++) {
				ans.add((Double.parseDouble(req_speed) / Double.parseDouble(base_speed)) * Q.get(i));
			}
		}
		return ans;
	}

	public List<Double> Q_rpm_conversion(Double req_speed, Double base_speed, List<Double> Q) {

		List<Double> ans = new ArrayList<Double>();
		if (req_speed.equals("") || req_speed.equals("0.0")) {
			ans = Q;
		} else {
			for (int i = 0; i < Q.size(); i++) {
				ans.add((req_speed / base_speed) * Q.get(i));
			}
		}
		return ans;
	}

	public double Q_rpm_conversion(String req_speed, String base_speed, double Q) {

		double ans = 0;
		if (req_speed.equals("") || req_speed.equals("0.0")) {
			ans = Q;
		} else {
			ans = ((Double.parseDouble(req_speed) / Double.parseDouble(base_speed)) * Q);
		}
		return ans;
	}

	public double Q_rpm_conversion(Double req_speed, Double base_speed, double Q) {

		double ans = 0;
		if (req_speed == null || req_speed == 0) {
			ans = Q;
		} else {
			ans = ((req_speed / base_speed) * Q);
		}
		return ans;
	}

	public List<Double> H_rpm_conversion(String req_speed, String base_speed, List<Double> H) {

		List<Double> ans = new ArrayList<Double>();
		if (req_speed.equals("") || req_speed.equals("0.0")) {
			ans = H;
		} else {
			for (int i = 0; i < H.size(); i++) {
				ans.add(Math.pow((Double.parseDouble(req_speed) / Double.parseDouble(base_speed)), 2) * H.get(i));
			}
		}
		return ans;
	}

	public Double H_rpm_conversion(String req_speed, String base_speed, double H) {

		double ans = 0;
		if (req_speed.equals("") || req_speed.equals("0.0")) {
			ans = H;
		} else {
			ans = Math.pow((Double.parseDouble(req_speed) / Double.parseDouble(base_speed)), 2) * H;
		}
		return ans;
	}

	public Double H_rpm_conversion(Double req_speed, Double base_speed, double H) {

		double ans = 0;
		if (req_speed == null || req_speed == 0) {
			ans = H;
		} else {
			ans = Math.pow((req_speed / base_speed), 2) * H;
		}
		return ans;
	}

	/**
	 * converts the base equation to new equation Logic: coefficients of equation
	 * divide by factor
	 * 
	 * @param factor = factor by which power increase
	 * @param terms= equation of the Efficiency Curve
	 * @return new terms(equation) at req factors
	 * @date: 28th Feb 2014 Code By: Pranav
	 */
	public double[] termsConversionPower(double factor, double[] terms) {

		double[] ans = new double[terms.length];
		if (factor == 0d) {
			ans = terms;
		} else {

			for (int i = 0; i < terms.length; i++) {
				ans[i] = terms[i] / factor;
			}
		}
		return ans;
	}

	/**
	 * converts the base equation to new equation Logic: coefficients of equation
	 * multiply by factor
	 * 
	 * @param factor = factor by which efficiency increase
	 * @param terms= equation of the Efficiency Curve
	 * @return new terms(equation) at req factors
	 * @date: 28th Feb 2014 Code By: Pranav
	 */
	public double[] termsConversionEff(double factor, double[] terms) {

		double[] ans = new double[terms.length];
		if (factor == 0d) {
			ans = terms;
		} else {

			for (int i = 0; i < terms.length; i++) {
				ans[i] = terms[i] * factor;
			}
		}
		return ans;
	}

	public Double pipeSizeFromVelocity(Double velocity, Double flow) {

		Double ans = 0d;
		try {
			ans = Math.pow((277.8 * flow * 4d / (velocity * Math.PI)), 0.5d);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	/**
	 * converts the equation of Power Curve from base_speed to req_speed Logic:
	 * coefficients of equation multiplied by factor coeff(0)*factor^3
	 * coeff(1)*factor^2 ' ' coeff(length)*factor(n-1)
	 * 
	 * @param req_speed  = is the speed at which the equation needs to be converted
	 * @param base_speed = is the speed at which the equation is
	 * @param terms=     equation of the Power Curve
	 * @return new terms(equation) at req_speed
	 * @date: 12th Nov 2012 Code By: Pranav
	 */
	public double[] terms_power_rpm_Conversion(Double req_speed, Double base_speed, double[] terms) {

		double[] ans = new double[terms.length];
		if (req_speed.isNaN() || req_speed.equals(0d)) {
			ans = terms;
		} else {
			int j = 3;
			double factor = req_speed / base_speed;
			for (int i = 0; i < terms.length; i++) {
				ans[i] = terms[i] * Math.pow(factor, j--);
			}
		}
		return ans;
	}

	/**
	 * converts the equation of Efficiency Curve from base_speed to req_speed Logic:
	 * coefficients of equation multiplied by factor coeff(0)*factor^0
	 * coeff(1)*factor^-1 ' ' coeff(length)*factor(n-1)
	 * 
	 * @param req_speed  = is the speed at which the equation needs to be converted
	 * @param base_speed = is the speed at which the equation is
	 * @param terms=     equation of the Efficiency Curve
	 * @return new terms(equation) at req_speed
	 * @date: 12th Nov 2012 Code By: Pranav
	 */
	public double[] terms_eff_rpm_Conversion(String req_speed, String base_speed, double[] terms) {

		double[] ans = new double[terms.length];
		if (req_speed.equals("") || req_speed.equals("0.0")) {
			ans = terms;
		} else {
			int j = 0;
			double factor = Double.parseDouble(req_speed) / Double.parseDouble(base_speed);
			for (int i = 0; i < terms.length; i++) {
				ans[i] = terms[i] * Math.pow(factor, j--);
			}
		}
		return ans;
	}

	public Double findPumpSpeed(Double reqX, Double reqY, Double base_speed, double[] terms, int stage,
			double stageFactor, int speedDirection) {

		Double pumpSpeed = 0.0;
		Double speedFactor = 0.0;
		double[] termsStage = terms_H_stage_conversion(String.valueOf(stage), String.valueOf(stageFactor), terms);
		double[] terms1 = new double[termsStage.length];

		for (int i = 0; i < termsStage.length; i++) {
			terms1[i] = (termsStage[termsStage.length - (i + 1)] * Math.pow(reqX, (termsStage.length - (i + 1))));
		}
		terms1[terms1.length - 3] = terms1[terms1.length - 3] - reqY;
		List<Double> roots = graphUtil.findRoots(terms1, 0.0);
		if (speedDirection == 1) {
			Collections.sort(roots);
			for (int i = 0; i < roots.size(); i++) {
				if (roots.get(i) >= 1) {
					speedFactor = roots.get(i);
					break;
				}
			}
		} else if (speedDirection == -1) {
			Collections.sort(roots, Collections.reverseOrder());
			for (int i = 0; i < roots.size(); i++) {
				if (roots.get(i) <= 1) {
					speedFactor = roots.get(i);
					break;
				}
			}
		}

		pumpSpeed = Math.ceil(speedFactor * base_speed);
		return pumpSpeed;
	}

	public Integer findFanSpeed(Double reqX, Double reqY, Double base_speed, double[] terms) {
		Integer ans = 0;
		Double speedFactor = 0.0;
		double[] terms1 = new double[terms.length];

		for (int i = 0; i < terms.length; i++) {
			terms1[i] = (terms[terms.length - (i + 1)] * Math.pow(reqX, (terms.length - (i + 1))));
		}
		terms1[terms1.length - 3] = terms1[terms1.length - 3] - reqY;
		List<Double> roots = graphUtil.findRoots(terms1, 0.0);
		Collections.sort(roots, Collections.reverseOrder());
		for (int i = 0; i < roots.size(); i++) {
			if (roots.get(i) <= 1) {
				speedFactor = roots.get(i);
				break;
			}
		}

		ans = (int) Math.ceil(speedFactor * base_speed);
		return ans;
	}

	public double formateEff(double eff) {

		double ans = 0d;
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		String effTemp = "";
		try {
			ans = eff;
			if (String.valueOf(ans).contains(".")) {
				int index = String.valueOf(ans).indexOf(".");
				String sub = String.valueOf(ans).substring(index + 1);
				if (sub.length() > 2) {
					sub = twoDForm.format(eff);
					if (sub.contains(".")) {
						index = sub.indexOf(".");
						effTemp = sub.substring(index + 1);
						if (effTemp.length() > 2) {
							sub = sub.substring(0, index + 1);
							effTemp = effTemp.substring(0, 2);
							effTemp = sub + effTemp;
							ans = Double.parseDouble(effTemp);
						} else
							ans = Double.parseDouble(sub);
					} else
						ans = Double.parseDouble(sub);

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	public String XY(List<Double> X, List<Double> Y) {

		String ans = "";

		for (int i = 0; i < X.size(); i++) {
			if (Y.get(i) != null) {
				ans += X.get(i) + " ";
				ans += Y.get(i) + " ";
			}
		}
		return ans;
	}

	public List<String> trimData(String data) {

		List<String> trimedData = new ArrayList<String>();

		if (data != null) {
			String[] array = data.split(",");
			for (String string : array)
				trimedData.add(string);
		}

		return trimedData;
	}

	public List<Double> findRoots(double[] terms1, double eff, double Qmin, double Qmax) {

		double[] terms = new double[terms1.length];
		System.arraycopy(terms1, 0, terms, 0, terms1.length);

		List<Double> Xlist = new ArrayList<Double>();
		List<Double> finalXlist = new ArrayList<Double>();
		terms[0] = terms[0] - eff;
		ComplexPoly test = new ComplexPoly(terms);
		test.roots(true);
		Complex[] roots = test.laguerreAll();
		int counts = 0;
		for (int i = 0; i < roots.length; i++) {
			if (String.valueOf(roots[i]).contains("j0.0")) {
				counts = counts + 1;
				String[] tempValue = String.valueOf(roots[i]).split(" ");

				if (!Xlist.contains(Double.parseDouble(tempValue[0])))
					Xlist.add(Double.parseDouble(tempValue[0]));
			}
		}

		for (int i = 0; i < Xlist.size(); i++) {
			if (Qmin <= Xlist.get(i) && Xlist.get(i) <= Qmax) {
				finalXlist.add(Xlist.get(i));
			}
		}

		return finalXlist;
	}

	public void copyfile(String srFile, String dtFile) {

		try {
			File source = new File(srFile);
			File destination = new File(dtFile);

			InputStream in = new FileInputStream(source);
			OutputStream out = new FileOutputStream(destination);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public boolean copyfile(File srFile, File dtFile) {

		boolean ans = false;
		try {
			InputStream in = new FileInputStream(srFile);
			OutputStream out = new FileOutputStream(dtFile);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			ans = true;
			in.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	public List<List<Double>> XYList(List<Double> X, List<Double> Y) {

		List<List<Double>> ans = new ArrayList<List<Double>>();
		List<Double> tempX = new ArrayList<Double>();
		List<Double> tempY = new ArrayList<Double>();

		for (int i = 0; i < X.size(); i++) {
			if (Y.get(i) != null) {
				tempX.add(X.get(i));
				tempY.add(Y.get(i));
			}
		}
		ans.add(tempX);
		ans.add(tempY);
		return ans;
	}

	public List<Double> getBEP(List<List<Double>> xy, int degree) {

		List<Double> bep = new ArrayList<Double>();

		double[] terms = polySolve.process(XY(xy.get(0), xy.get(1)), degree == 0 ? 3 : degree);
		double[] tempTerms = new double[terms.length];

		System.arraycopy(terms, 0, tempTerms, 0, terms.length);

		List<Double> Xroots = new ArrayList<Double>();
		try {
			double lowerEff = 0.9;
			double higherEff = 1.0;
			double tempEff = 0.0;
			double BEP = 0.0;
			double root = 0.0;

			Xroots = findRoots(tempTerms, lowerEff, Collections.min(xy.get(0)), Collections.max(xy.get(0)));

			while (Xroots.isEmpty() && lowerEff > 0.0) {
				higherEff = lowerEff;
				lowerEff = lowerEff - 0.1;
				tempTerms = new double[terms.length];
				System.arraycopy(terms, 0, tempTerms, 0, terms.length);
				Xroots = findRoots(tempTerms, lowerEff, Collections.min(xy.get(0)), Collections.max(xy.get(0)));
			}
			if (lowerEff > BEP) {
				BEP = lowerEff;
			} else {
				System.out.println("BEP==" + BEP);
				bep.add(root);
				bep.add(BEP);
				return bep;
			}

			do {

				tempEff = (lowerEff + higherEff) / 2;
				tempTerms = new double[terms.length];
				System.arraycopy(terms, 0, tempTerms, 0, terms.length);
				Xroots = findRoots(tempTerms, tempEff, Collections.min(xy.get(0)), Collections.max(xy.get(0)));

				if (Xroots.isEmpty()) {
					higherEff = tempEff;
				} else {
					if (tempEff > BEP) {
						BEP = tempEff;
						lowerEff = tempEff;
						root = Xroots.get(0);
					}
				}
				if (Math.round(tempEff * 100000.0) / 100000.0 == Math.round(higherEff * 100000.0) / 100000.0
						&& Math.round(tempEff * 100000.0) / 100000.0 == Math.round(lowerEff * 100000.0) / 100000.0)
					break;

			} while (true);
			bep.add(root);
			bep.add(BEP);
			/*
			 * System.out.println(
			 * "---------------------------------- NEW METHOD -----------------------------"
			 * ); System.out.println("X ---------" + root); System.out.println(
			 * "BEP Point---------" + BEP);
			 */

		}

		catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return bep;
	}

	public Double P_rpm_conversion(Double req_speed, Double base_speed, double P) {

		double ans = 0d;
		if (req_speed == null || req_speed == 0) {
			ans = P;
		} else {
			ans = Math.pow((req_speed / base_speed), 3) * P;
		}
		return ans;
	}

	public Double N_rpm_conversionSintech(Double req_speed, Double base_speed, double N) {

		double ans = 0d;
		if (req_speed == null || req_speed == 0) {
			ans = N;
		} else {
			ans = Math.pow((req_speed / base_speed), 1.5) * N;
		}
		return ans;
	}

	public double y_TRIM(double y_upper, double y_lower, double upperAnnotation, double lowerAnnotation,
			double trimAnnotation) {

		if (upperAnnotation == lowerAnnotation)
			return y_upper;
		else
			return y_upper
					- ((upperAnnotation - trimAnnotation) * (y_upper - y_lower) / (upperAnnotation - lowerAnnotation));
	}

	public String getDXFPath(int offerRevId, int offerPumpId, String templateName) {

		String path = getRealPath("");
		String userPath = path + "/OFFER/" + "/" + offerRevId + "/" + offerPumpId + "/" + templateName;
		return userPath;
	}

	public String getBomPath(int offerRevId, int offerPumpId, String fileName) {

		String path = getRealPath("");
		String userPath = path + "/OFFER/" + "/" + offerRevId + "/" + offerPumpId + "/" + fileName;
		return userPath;
	}

	public void makeZipFile(List<File> fileList, String zipName) {

		try {
			byte[] buffer = new byte[1024];
			FileOutputStream fout = new FileOutputStream(getUserRealPath() + zipName + ".zip");
			ZipOutputStream zout = new ZipOutputStream(fout);
			for (int i = 0; i < fileList.size(); i++) {
				if (fileList.get(i).exists()) {
					FileInputStream fin = new FileInputStream(fileList.get(i));

					zout.putNextEntry(new ZipEntry(fileList.get(i).getName()));
					int length;
					while ((length = fin.read(buffer)) > 0) {
						zout.write(buffer, 0, length);
					}
					zout.closeEntry();
					fin.close();
				}
			}
			zout.close();
			zout.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public String convertNumberToWord(Double offerTotal) {

		String amountInWords = null;
		NumberFormat formatter = new DecimalFormat();
		formatter.setMaximumFractionDigits(25);
		String no = formatter.format(offerTotal);
		no = no.replaceAll(",", "");
		String[] noArray = no.split("\\.");
		String[] ans = new String[noArray.length];
		for (int i = 0; i < noArray.length; i++) {
			ans[i] = NumberToWordConverter.numberToWordConvert(Integer.parseInt(noArray[i]));
		}

		amountInWords = "Rs " + ans[0];
		if (ans.length == 2) {
			if (ans[0] != "")
				amountInWords = amountInWords + " And ";
			amountInWords = amountInWords + ans[1] + " paise";
		}
		return amountInWords + " Only";
	}

	public List<PdfReader> getOfferCovertLetter(List<PdfReader> pdfReaderList, Map<String, Object> parameters) {

		try {
			String outputFileName2 = "cover" + "-" + new Date().toString().replaceAll(" ", "").replaceAll(":", "")
					+ ".pdf";

			getReportJdbc("offerCoverLetter.jasper", getUserRealPath() + outputFileName2, parameters);
			pdfReaderList.add(new PdfReader(getUserRealPath() + outputFileName2));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return pdfReaderList;
	}

	public String getCurrentEquation(List<Double> power, List<Double> current, String pumpPowerEq,
			String powerFlowMinMax) {

		String ans = "";
		try {
			double[] termsPowerCurrent = polySolve.process(graphUtil.getXy(power, current), 2);

			String[] powerEq = new String[0];
			powerEq = pumpPowerEq.split(";");
			List<double[]> powerEqs = new ArrayList<double[]>();
			for (int i = 0; i < powerEq.length; i++) {
				String[] tempEq = powerEq[i].replace("[", "").replace("]", "").split(",");
				double[] tempEq1 = new double[tempEq.length];
				for (int j = 0; j < tempEq.length; j++) {
					tempEq1[j] = Double.parseDouble(tempEq[j]);
				}
				powerEqs.add(tempEq1);
				break; // to consider only for trim impeller
			}

			String[] powerMinMax = new String[0];
			powerMinMax = powerFlowMinMax.split(";");
			List<List<Double>> powerMinMaxList = new ArrayList<List<Double>>();
			List<Double> temp = new ArrayList<Double>();
			for (int i = 1; i < powerMinMax.length + 1; i++) {
				temp.add(Double.parseDouble(powerMinMax[i - 1]));
				if (i > 1 && i % 2 == 0) {
					powerMinMaxList.add(temp);
					temp = new ArrayList<Double>();
				}
			}
			double[] eqns = new double[1];
			for (int i = 0; i < powerEqs.size(); i++) {
				List<List<Double>> flowPower = graphUtil.getInterPolationEqn(powerEqs.get(i),
						powerMinMaxList.get(i).get(0), powerMinMaxList.get(i).get(1));
				List<Double> calPower = flowPower.get(1);
				List<Double> calCurrent = new ArrayList<>();
				for (Double double1 : calPower) {
					calCurrent.add(polySolve.plotFunct(double1, termsPowerCurrent));
				}
				eqns = polySolve.process(graphUtil.getXy(flowPower.get(0), calCurrent), 2);

			}

			ans = Arrays.toString(eqns);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	public Double getYValue(String Eq, double x) {

		Double ans = 0d;
		try {
			double[] terms = convertStringArrayToDoubleArray(Eq);
			ans = polySolve.plotFunct(x, terms);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	public String getOverallEffEq(String motorEfficiencyEq, String pumpEfficiencyEq, String powerFlowMinMax) {

		String ans = "";
		try {
			double[] motorEffEq = convertStringArrayToDoubleArray(motorEfficiencyEq);
			double[] pumpEffEq = convertStringArrayToDoubleArray(pumpEfficiencyEq);

			String[] powerMinMax = powerFlowMinMax.split(";");
			ans = graphUtil.getOverallEqn(motorEffEq, pumpEffEq, Double.parseDouble(powerMinMax[0]),
					Double.parseDouble(powerMinMax[1]));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	public JFreeChart getPerformanceChartImage(JSONObject jo) {

		JFreeChart chart = null;
		GraphUtil graphUtil = new GraphUtil();
		List<GraphModel> graphModelListPressure = new ArrayList<GraphModel>();
		List<GraphModel> graphModelListPower = new ArrayList<GraphModel>();
		try {

			List<Point> dutyPointList = new ArrayList<Point>();

			JSONArray vaneArrayListArray = jo.getJSONArray("speedDetails");
			for (int i = 0; i < vaneArrayListArray.length(); i++) {
				JSONObject vaneJsonObject = vaneArrayListArray.getJSONObject(i);
				Integer lineThikness = 1;
				String lineColour = "lightBlue";
				if (vaneJsonObject.get("isSelected").toString() == "true") {
					lineThikness = 3;
					lineColour = "blue";
				}

				graphModelListPressure.add(new GraphModel(
						convertStringArrayToDoubleArray(vaneJsonObject.get("pressureEq").toString()),
						vaneJsonObject.getDouble("minFlow"), vaneJsonObject.getDouble("maxFlow"), true, lineColour,
						"Solid", lineThikness, "red", "verticalRectangle", vaneJsonObject.get("selectedSpeed").toString()));

				graphModelListPower.add(new GraphModel(
						convertStringArrayToDoubleArray(vaneJsonObject.get("powerEq").toString()),
						vaneJsonObject.getDouble("minFlow"), vaneJsonObject.getDouble("maxFlow"), true, lineColour,
						"Solid", lineThikness, "red", "verticalRectangle", vaneJsonObject.get("selectedSpeed").toString()));

			}

			CombinedDomainXYPlot combinedDomainXYPlot = graphUtil
					.combineGraph("Flow (" + jo.getString("flowUOM") + ") ", true, 0);

			dutyPointList.add(new Point(Double.parseDouble(jo.get("dpFlow").toString()),
					Double.parseDouble(jo.get("dpPressure").toString())));

			XYPlot plotPressure = graphUtil.individualGraph(graphModelListPressure, dutyPointList,
					"Pressure(" + jo.getString("pressureUOM") + ")", new java.awt.Point(0, 0), true, true, true);
			dutyPointList.clear();

			dutyPointList.add(new Point(Double.parseDouble(jo.get("dpFlow").toString()),
					Double.parseDouble(jo.get("dpPower").toString())));

			XYPlot plotPower = graphUtil.individualGraph(graphModelListPower, dutyPointList,
					"Power(" + jo.getString("powerUOM") + ")", new java.awt.Point(1, 1), true, true, true);

			combinedDomainXYPlot.add(plotPressure, 1);
			combinedDomainXYPlot.add(plotPower, 1);

			chart = graphUtil.getChart("", "", combinedDomainXYPlot, true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return chart;
	}

	public int getUsertypeId() {

		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		return requestAttributes.getRequest().getSession().getAttribute("usertypeId") != null
				? (Integer) requestAttributes.getRequest().getSession().getAttribute("usertypeId")
				: 0;
	}

	public Object evaluateExpression(String expression) {

		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		Object result = new Object();
		try {
			result = engine.eval(expression);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public String[] splitExpression(String expression) {

		return expression.split("(?<=[-+*/])|(?=[-+*/])");

	}

	public String formExpression(String[] expression, HashMap<String, String> blockValues) {

		StringBuilder expression1 = new StringBuilder();
		try {
			for (int i = 0; i < expression.length; i++) {
				if (blockValues.containsKey(expression[i]))
					expression[i] = blockValues.get(expression[i]);
			}

			for (int i = 0; i < expression.length; i++) {
				expression1.append(expression[i]);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return expression1.toString();
	}

	public String solveExpression(String expression, HashMap<String, String> blockValues) {

		Object ans = new Object();
		try {
			String ans1 = formExpression(splitExpression(expression), blockValues);
			if (!ans1.contains("-"))
				ans = evaluateExpression(ans1);
			else
				ans = "-";

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return ans.toString();
	}

	public double roundAvoid(double value, int places) {

		double scale = Math.pow(10, places);
		return Math.round(value * scale) / scale;
	}

	public double calculateSpGrWater(Double temperature) {

		double spgr = 1;
		try {
			// upto 100 deg
			double[] terms = { 0.99997635610832, 0.00001703304827, -0.00000591292818, 0.00000001569600 };
			spgr = roundAvoid(polySolve.plotFunct(temperature, terms), 4);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return spgr;

	}

	public String getLastName(String filePath) {

		int index = filePath.lastIndexOf(System.getProperty("file.separator"));
		String fileName = filePath.substring(index + 1);
		return fileName;
	}

	public Integer getRandomNumber() {

		Random randomGenerator = new Random();
		return randomGenerator.nextInt(999999);
	}

	public String getTimeStamp() {

		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	}

	public String getFileName(String path) {

		String ans = "";
		try {
			Path pathnew = Paths.get(path);
			ans = pathnew.getFileName().toString();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	public JSONObject mergeJsonNew(JSONObject finalData, JSONObject newData, String targetKey) {

		try {
			/*
			 * if (newData == null || newData.length() == 0) return finalData;
			 */
			Josson master = Josson.fromJsonString(finalData.toString());
			String addNew = newData.toString();

			String[] keys = targetKey.split("\\.");
			StringBuilder a = new StringBuilder();
			a.append("field(");
			for (int i = 0; i < keys.length - 1; i++) {
				String key = keys[i];
				a.append(key + ".field(");
			}
			a.append(keys[keys.length - 1] + ".mergeObjects(?,  json('" + addNew + "'))");

			for (int i = 0; i < keys.length; i++) {
				a.append(")");
			}
			// System.out.println(a.toString());

			if (master.getNode(targetKey) != null && master.getNode(targetKey).isObject()) {
				JsonNode node = master.getNode(a.toString());
				// System.out.println("node.toString():-"+node.toString());
				finalData = new JSONObject(node.toString());
				// System.out.println("finalData.toString():-"+finalData.toString());
				// JSONObject object = new SomeClass().sortJSONObject(new
				// JSONObject(json));
			} else {
				finalData = build(finalData, targetKey);
				master = Josson.fromJsonString(finalData.toString());
				JsonNode node = master.getNode(a.toString());
				finalData = new JSONObject(node.toString());

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return finalData;
	}

	public JSONObject getJsonObject(List<?> list) {

		JSONObject ans = new JSONObject();
		try {
			// ObjectMapper objectMapper = new ObjectMapper();
			Gson gson = new Gson();
			// String arrayToJson = objectMapper.writeValueAsString(list);
			ans.put("data", new JSONArray(gson.toJson(list)));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	public List<?> getPojoList(JsonNode jo) {

		List<?> ans = new ArrayList<>();
		try {
			if (jo.isArray()) {
				ObjectMapper mapper = new ObjectMapper();
				ans = mapper.convertValue(jo, ArrayList.class);

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	public Object getJsonObject(JsonNode node) {

		Object ans = new Object();
		try {
			if (node.isArray()) {
				ObjectMapper mapper = new ObjectMapper();
				List<String> list = mapper.convertValue(node, ArrayList.class);
				ans = list;
			} else
				ans = node.asText();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	public Gson gsonWithExpose() {

		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}

	public Gson gson() {

		return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}

	public JSONObject mergeJson(JSONObject finalData, JSONObject data) {

		try {

			Iterator<String> keys = data.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				finalData.put(key, data.get(key));
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return finalData;

	}

	public JSONObject mergeJson(JSONObject finalData, JSONObject newData, String targetKey) {

		try {
			JSONObject keyObject = new JSONObject();

			if (finalData.has(targetKey))
				keyObject = finalData.getJSONObject(targetKey);

			Iterator<String> keys = newData.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				if (!keyObject.has(key))
					keyObject.put(key, newData.get(key));
			}
			//
			// finalData.accumulate(targetKey, keyObject);
			//

			finalData.put(targetKey, keyObject);

			System.out.println("targetKey:-" + finalData);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return finalData;

	}

	public JSONObject build(JSONObject current, String path) {

		JSONObject json = new JSONObject();
		try {

			String[] keys = path.split("\\.");

			// start from the root
			json = current;
			for (int i = 0; i < keys.length; ++i) {
				String key = keys[i];

				// Search for the current node
				try {
					// If it exist, do nothing
					current = current.getJSONObject(key);
				} // If it does not exist
				catch (JSONException ex) {
					JSONObject tmp = new JSONObject();
					current.put(key, tmp);
					current = tmp;
				}

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	public String getUuid() {

		String ans = "";
		try {
			ans = UUID.randomUUID().toString();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;

	}

	public boolean isNumeric(String str) {

		try {
			if (str != null) {
				Double.parseDouble(str);
				return true;
			} else
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public Integer getInteger(Object str) {

		Integer ans = null;
		try {
			if (str != null) {
				ans = ((Number) str).intValue();
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return ans;
	}

	public List<Double> getDoubleList(Object obj) {

		List<Double> ans = new ArrayList<>();
		try {
			List<?> list = (List<?>) obj;
			for (Object object : list) {
				ans.add(((Number) object).doubleValue());
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return ans;
	}

	public JsonNode flatten(Josson josson) {

		JsonNode ans = null;
		try {
			ans = josson.getNode("flatten('.')");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;

	}

	public JsonNode unFlatten(Josson josson) {

		JsonNode ans = null;
		try {
			ans = josson.getNode("unflatten('//.')");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;

	}

	public Double getNextHighestValue(List<Double> source, Double value) {

		Double ans = Double.NaN;
		try {
			List<Double> newList = new ArrayList<Double>(source);
			Collections.sort(newList);
			for (Double double1 : newList) {
				if (double1 >= value) {
					ans = double1;
					break;
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	public boolean isCollectionSorted(List list) {
		boolean ans = false;
		try {
			List copy = new ArrayList(list);
			Collections.sort(copy);
			ans = copy.equals(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	/**
	 * Converts Speed to given frequency having same pole
	 * 
	 * @param speed         = speed of motor or pump
	 * @param fromPole      = fromPole of motor or pump
	 * @param toPole        = toPole of motor or pump (should be null if conversion
	 *                      for same pole)
	 * @param fromFrequency = fromFrequency of motor or pump
	 * @param toFrequency   = toFrequency of motor or pump
	 * @return New Speed
	 * @date: 13th March 2015 Code By: Pranav
	 */
	public Double convertFrequency(Double speed, Integer fromPole, Integer toPole, Double fromFrequency,
			Double toFrequency) {

		Double ans = 0d;
		Double actualFrequency = getActualFrequency(speed, fromPole);
		Double derivedFrequency = (actualFrequency / fromFrequency) * toFrequency;
		if (toPole == null)
			ans = (derivedFrequency * 120) / fromPole;
		else
			ans = (derivedFrequency * 120) / toPole;
		// rounding to nearest 10
		ans = Math.round(ans / 10d) * 10d;

		return ans;
	}

	/**
	 * calculates Frequency
	 * 
	 * @param speed = speed of motor or pump
	 * @param pole  = pole of motor or pump
	 * @return Frequency
	 * @date: 13th March 2015 Code By: Pranav
	 */
	public Double getActualFrequency(Double speed, Integer pole) {

		Double ans = 0d;
		ans = (speed * pole) / 120;
		return ans;
	}

	public Double velocityInFan(Double flow, Double fanDia) {

		// Double areaPipe = areaPipe(pipeOD);
		Double areaFan = roundAvoid((areaFan(fanDia)), 2);
		if (!areaFan.equals(0.0))
			return 277.78 * flow / areaFan;
		else
			return 0.0;
	}

	public Double areaFan(Double fanDia) {

		Double area = 0.0;
		area = (Math.PI / 4) * Math.pow(fanDia, 2);
		return area;
	}

}

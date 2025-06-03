
package com.velotech.fanselection.design.service;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.velotech.fanselection.design.dao.CentrifugalXmlAttachmentDao;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl90XmlCentrifugalFanSpeed;
import com.velotech.fanselection.models.Tbl90XmlCentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl90XmlCentrifugalPerformanceDataHead;
import com.velotech.fanselection.models.Tbl90XmlCentrifugalPerformanceDataPower;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.FileUtil;
import com.velotech.fanselection.utils.VelotechUtil;
import com.velotech.fanselection.utils.XMLParser;
import com.velotech.fanselection.utils.graph.PolySolve;

//xml

@Service
@Transactional
public class CentrifugalXmlAttachmentServiceImpl implements CentrifugalXmlAttachmentService {

	static Logger log = LogManager.getLogger(CentrifugalXmlAttachmentServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CentrifugalXmlAttachmentDao dao;

	@Autowired
	private PolySolve polySolve;

	@Autowired
	private VelotechUtil velotechUtil;

	// for import excel data
	private Map<String, List<Double>> importExcelData(File destFile) {

		Map<String, List<Double>> impellerDataMap = new HashMap<String, List<Double>>();
		try {
			FileInputStream fileInputStream = new FileInputStream(destFile);
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowNum = sheet.getLastRowNum() + 1;
			int colNum = sheet.getRow(1).getLastCellNum();
			Row row = null;
			Row row1 = null;
			Cell cell = null;
			Cell cell1 = null;
			String impellerDia = "";
			List<Double> impellerdataValue = null;
			for (int i = 1; i < rowNum; i += 2) {
				row = sheet.getRow(i);
				row1 = sheet.getRow(i + 1);
				cell = row.getCell(0);
				// impellerDia = String.valueOf(cell.getNumericCellValue());
				impellerDia = String.valueOf((int) cell.getNumericCellValue());
				impellerdataValue = new ArrayList<>();
				for (int j = 1; j < colNum; j++) {
					cell = row.getCell(j);
					cell1 = row1.getCell(j);
					impellerdataValue.add(cell.getNumericCellValue());
					impellerdataValue.add(cell1.getNumericCellValue());
				}
				impellerDataMap.put(impellerDia, impellerdataValue);
			}
			fileInputStream.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return impellerDataMap;
	}

	private double[] insertPerfDataHead(List<Double> perfDataHead,
			Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeed) {

		double[] termsH = null;
		try {

			List<Tbl90XmlCentrifugalPerformanceDataHead> listDataHead = new ArrayList<Tbl90XmlCentrifugalPerformanceDataHead>();
			List<Double> qStd = new ArrayList<Double>();
			List<Double> hStd = new ArrayList<Double>();

			DecimalFormat tdFormat = new DecimalFormat("#.##");

			for (int i = 0; i < perfDataHead.size(); i++) {
				Tbl90XmlCentrifugalPerformanceDataHead dataHead = new Tbl90XmlCentrifugalPerformanceDataHead();
				dataHead.setQ(perfDataHead.get(i));
				qStd.add(dataHead.getQ());
				dataHead.setH(perfDataHead.get(++i));
				hStd.add(dataHead.getH());
				dataHead.setTbl90XmlCentrifugalFanSpeed(tbl90XmlCentrifugalFanSpeed);
				listDataHead.add(dataHead);
			}
			genericDao.saveAll(listDataHead);

			termsH = polySolve.process(velotechUtil.XY(qStd, hStd), tbl90XmlCentrifugalFanSpeed.getPressureDegree());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return termsH;

	}

	private double[] insertPerfDataPower(List<Double> perfDataPower,
			Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeed) {

		double[] termsP = null;
		DecimalFormat tdFormat = new DecimalFormat("#.##");
		try {
			List<Tbl90XmlCentrifugalPerformanceDataPower> listDataPower = new ArrayList<Tbl90XmlCentrifugalPerformanceDataPower>();
			List<Double> qStd = new ArrayList<Double>();
			List<Double> hStd = new ArrayList<Double>();
			for (int i = 0; i < perfDataPower.size(); i++) {
				Tbl90XmlCentrifugalPerformanceDataPower dataPower = new Tbl90XmlCentrifugalPerformanceDataPower();
				dataPower.setQ(perfDataPower.get(i));
				qStd.add(dataPower.getQ());
				dataPower.setP(perfDataPower.get(++i));
				hStd.add(dataPower.getP());
				dataPower.setTbl90XmlCentrifugalFanSpeed(tbl90XmlCentrifugalFanSpeed);
				listDataPower.add(dataPower);
			}
			genericDao.saveAll(listDataPower);
			termsP = polySolve.process(velotechUtil.XY(qStd, hStd), tbl90XmlCentrifugalFanSpeed.getPowerDegree());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return termsP;

	}

	public static boolean checkXMLFormat(File xmlFile) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			// Get the root element
			Element rootElement = doc.getDocumentElement();

			// Check if the root element is "Workbook"
			if (!rootElement.getNodeName().equals("Workbook")) {
				return false;
			}

			// Check if the namespace matches
			String xmlns = rootElement.getAttribute("xmlns");
			if (!xmlns.equals("urn:schemas-microsoft-com:office:spreadsheet")) {
				return false;
			}

			// Check if DocumentProperties, Styles, and Worksheet elements exist
			NodeList documentProperties = doc.getElementsByTagName("DocumentProperties");
			NodeList styles = doc.getElementsByTagName("Styles");
			NodeList worksheet = doc.getElementsByTagName("Worksheet");
			if (documentProperties.getLength() == 0 || styles.getLength() == 0 || worksheet.getLength() == 0) {
				return false;
			}

			// Add more checks for specific elements, attributes, and data if needed

			return true; // XML format matches
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Error occurred or format doesn't match
		}
	}

	@Override
	public ApplicationResponse uploadXmlAttachment(MultipartFile headAttachment, MultipartFile powerAttachment,
			Integer headDeg, Integer powerDeg, boolean excelCheck, Integer fanModelId

	) {
		// TODO Auto-generated method stub
		ApplicationResponse applicationResponse = new ApplicationResponse();
		boolean ans = false;
		XMLParser parser = new XMLParser();
		Set<Double> impellerDia = new HashSet<Double>();
		boolean validFile = true;
		boolean validateXML = true;

		try {

			Map<String, File> fileMap = new LinkedHashMap<String, File>();

			if (!headAttachment.isEmpty()) {
				File headAttachmentFile = FileUtil.convertMultipartFile(headAttachment, velotechUtil.getUserRealPath(),
						headAttachment.getOriginalFilename());
				fileMap.put(headAttachment.getOriginalFilename(), headAttachmentFile);
			}

			if (!powerAttachment.isEmpty()) {
				File powerAttachmentFile = FileUtil.convertMultipartFile(powerAttachment,
						velotechUtil.getUserRealPath(), powerAttachment.getOriginalFilename());
				fileMap.put(powerAttachment.getOriginalFilename(), powerAttachmentFile);
			}
			Tbl01CentrifugalModelMaster fanModelString = (Tbl01CentrifugalModelMaster) genericDao
					.getRecordById(Tbl01CentrifugalModelMaster.class, fanModelId);
			boolean dataExists = dao.checkCentrifugalModelMaster(fanModelString.getFanModel());
			if (dataExists) {
				return applicationResponse = ApplicationResponseUtil.getResponseToCRUD(validFile,
						ApplicationConstants.RECORD_EXIST_MSG);
			}

			Tbl90XmlCentrifugalModelMaster xmlCentrifugalModelMaster = new Tbl90XmlCentrifugalModelMaster();
			Tbl01CentrifugalModelMaster modelmaster = (Tbl01CentrifugalModelMaster) genericDao
					.getRecordById(Tbl01CentrifugalModelMaster.class, fanModelId);

			xmlCentrifugalModelMaster.setFanModel(modelmaster.getFanModel());
			xmlCentrifugalModelMaster.setCompany(velotechUtil.getCompany());
			dao.saveTbl90CentrifugalXmlModelmaster(xmlCentrifugalModelMaster);

			for (Entry<String, File> fileData : fileMap.entrySet()) {
				System.out.println(fileData.getKey());
				File destFile = new File(velotechUtil.getUserRealPath(), fileData.getKey());

				Map<String, List<Double>> impellerDataMap = null;

				if (!excelCheck) {

					validateXML = checkXMLFormat(destFile);
					if (!validateXML) {
						return applicationResponse = ApplicationResponseUtil.getResponseToCRUD(validFile,
								ApplicationConstants.INVALID_FILE);

					}

					impellerDataMap = parser.getDataFromPerfChart(
							velotechUtil.getUserRealPath() + System.getProperty("file.separator") + fileData.getKey());
				} else {
					impellerDataMap = importExcelData(destFile);
				}

				System.out.println(impellerDataMap.toString());
				System.out.println(impellerDataMap.keySet().toString());

				for (Entry<String, List<Double>> record : impellerDataMap.entrySet()) {

					impellerDia.add(Double.parseDouble(record.getKey()));

					// Tbl02Modelmaster tbl02Modelmaster = new Tbl02Modelmaster();
					Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeed = new Tbl90XmlCentrifugalFanSpeed();
					Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeedDB = null;
					List<Tbl90XmlCentrifugalPerformanceDataHead> tbl90XmlCentrifugalPerformanceDataHeadList = null;

					tbl90XmlCentrifugalFanSpeed
							.setCentrifugalFanSpeedId(xmlCentrifugalModelMaster.getFanModel() + "-" + record.getKey());
					tbl90XmlCentrifugalFanSpeed.setTbl90XmlCentrifugalModelMaster(xmlCentrifugalModelMaster);
					// tbl90XmlPerformanceVane.setTbl02Modelmaster(tbl02Modelmaster);
					tbl90XmlCentrifugalFanSpeed.setSpeed(Integer.parseInt(record.getKey()));
					tbl90XmlCentrifugalFanSpeed.setPressureDegree(headDeg);
					tbl90XmlCentrifugalFanSpeed.setPowerDegree(powerDeg);
					tbl90XmlCentrifugalFanSpeed.setCompany(velotechUtil.getCompany());
					// dao.saveTbl90XmlPerformanceVane(tbl90XmlPerformanceVane);

					double[] termsH = null;
					double[] termsP = null;

					double minflow = 0.8 * Collections.min(record.getValue());
					tbl90XmlCentrifugalFanSpeedDB = dao.checkCentrifugalFanSpeed(tbl90XmlCentrifugalFanSpeed,
							xmlCentrifugalModelMaster.getId());
					if (tbl90XmlCentrifugalFanSpeedDB == null) {
						tbl90XmlCentrifugalFanSpeed.setCompany(velotechUtil.getCompany());
						tbl90XmlCentrifugalFanSpeedDB = dao
								.saveTbl90XmlCentrifugalFanSpeed(tbl90XmlCentrifugalFanSpeed);
					}

					if (fileData.getKey().toLowerCase().contains("head")) {
						termsH = insertPerfDataHead(record.getValue(), tbl90XmlCentrifugalFanSpeedDB);
					}
					if (fileData.getKey().toLowerCase().contains("power")) {
						termsP = insertPerfDataPower(record.getValue(), tbl90XmlCentrifugalFanSpeedDB);
						tbl90XmlCentrifugalPerformanceDataHeadList = dao
								.gettbl90XmlCentrifugalPerformanceDataHead(tbl90XmlCentrifugalFanSpeedDB);

						for (Tbl90XmlCentrifugalPerformanceDataHead tbl90XmlCentrifugalPerformanceDataHead : tbl90XmlCentrifugalPerformanceDataHeadList) {
							if (tbl90XmlCentrifugalPerformanceDataHead.getQ() >= minflow) {
								tbl90XmlCentrifugalPerformanceDataHead.setP(
										polySolve.plotFunct(tbl90XmlCentrifugalPerformanceDataHead.getQ(), termsP));
							}

						}
						boolean insertdata = genericDao.updateAll(tbl90XmlCentrifugalPerformanceDataHeadList);
					}

				}
				destFile.delete();
			}
			ans = true;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (validFile) {

			if (ans == true)
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(ans,
						ApplicationConstants.INSERT_SUCCESS_MSG);
			else
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(ans,
						ApplicationConstants.INSERT_FAIL_MSG);
		} else {
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(validFile,
					ApplicationConstants.INVALID_FILE);
		}
		return applicationResponse;

	}

}

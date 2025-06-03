
package com.velotech.fanselection.dxf.util;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kabeja.batik.tools.SAXJPEGSerializer;
import org.kabeja.batik.tools.SAXPDFSerializer;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;
import org.kabeja.svg.SVGGenerator;
import org.kabeja.xml.SAXPrettyOutputter;
import org.kabeja.xml.SAXSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.velotech.fanselection.dxf.model.OfferDrawingPojo;
import com.velotech.fanselection.dxf.service.DxfServiceImpl;
import com.velotech.fanselection.ireportmodels.MocData;
import com.velotech.fanselection.models.Tbl40OfferDocument;
import com.velotech.fanselection.utils.ExportDXFDrawingToPDF;
import com.velotech.fanselection.utils.VelotechUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class OfferDrawingBlock {

	static Logger log = LogManager.getLogger(DxfServiceImpl.class.getName());

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private ExportDXFDrawingToPDF aspose;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getGeneratedDxfView(OfferDrawingPojo offerDrawing) {

		String contextPath = null;
		try {
			if (offerDrawing.getDxfName() != null) {
				File dxfFile = new File(velotechUtil.getCompanyDocumentPath(offerDrawing.getDxfName()));
				offerDrawing.setUserName(velotechUtil.getUsername());
				if (dxfFile.exists()) {
					HashMap<String, String> blockValues = new HashMap<String, String>();
					Map<String, Boolean> layerValues = new HashMap();
					contextPath = velotechUtil.getUserContextPath() + offerDrawing.getDxfName();
					String path = velotechUtil.getUserRealPath() + offerDrawing.getDxfName();
					File tempDXH = new File(path);
					FileOutputStream fileOutputStream = new FileOutputStream(tempDXH);
					blockValues = setAllBlocksValues(blockValues, offerDrawing);
					layerValues = setLayersOnOff(offerDrawing);

					DxfParser.parseDXF(dxfFile, fileOutputStream, blockValues, layerValues,
							offerDrawing.getImageList());
					fileOutputStream.flush();
					fileOutputStream.close();
					offerDrawing.setCheckPath(true);
					if (!offerDrawing.isDownload()) {
						offerDrawing.setOutputPdfName();
						geneRateDxfImageCommon(offerDrawing);
					} else
						download(offerDrawing);
					offerDrawing.setCheckPath(true);

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return contextPath;
	}

	public OfferDrawingPojo getGeneratedDxfViewAspose(OfferDrawingPojo dxfPojo1) {

		if (dxfPojo1.getDxfName() == null || !dxfPojo1.getDxfName().toUpperCase().contains(".DXF"))
			return dxfPojo1;
		try {
			String dxfFilePath = velotechUtil.getCompanyDocumentPath(dxfPojo1.getDxfName());

			File dxfFile = new File(dxfFilePath);
			dxfPojo1.setUserName(velotechUtil.getUsername());
			if (dxfFile.exists()) {
				HashMap<String, String> blockValues = new HashMap<String, String>();
				Map<String, Boolean> layerValues = new HashMap();

				String newFile = velotechUtil.getUserRealPath() + dxfPojo1.getDxfName();
				velotechUtil.copyfile(dxfFilePath, newFile);

				layerValues = setLayersOnOff(dxfPojo1);
				if (!dxfPojo1.getIsTemplate())
					blockValues = setAllBlocksValues(blockValues, dxfPojo1);
				else
					blockValues = fillImageBlocks(blockValues, dxfPojo1);

				if (!dxfPojo1.isDownload()) {
					dxfPojo1.setOutputPdfName();
					aspose.convert(newFile, dxfPojo1.getOutputPdfName(), blockValues, layerValues,
							dxfPojo1.getTbl80TemplateMaster().getLayout());

				} else {
					File tempDXH = new File(velotechUtil.getUserRealPath() + dxfPojo1.getDxfName());

					FileOutputStream fileOutputStream = new FileOutputStream(tempDXH);
					DxfParser.parseDXF(dxfFile, fileOutputStream, blockValues, layerValues, dxfPojo1.getImageList());
					download(dxfPojo1);
				}
				dxfPojo1.setCheckPath(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dxfPojo1;
	}

	public void geneRateDxfImageCommon(OfferDrawingPojo dxfPojo1) throws ParseException {

		String ext = ".jpg";

		try {
			System.out.println("start time:" + new Date());
			SVGGenerator generator = new SVGGenerator();
			Parser dxfParser = ParserBuilder.createDefaultParser();
			// dxfParser.parse(dxfPojo1.getDXF_PATH(), "");
			dxfParser.parse(velotechUtil.getUserRealPath() + dxfPojo1.getDxfName(), "");

			DXFDocument doc = dxfParser.getDocument();
			@SuppressWarnings("rawtypes")
			HashMap context = getContext(dxfPojo1);
			SAXSerializer out = null;
			FileOutputStream pdfOutputStream = null;
			out = new SAXPrettyOutputter();
			if (dxfPojo1.isPdf()) {
				out = new SAXPDFSerializer();// for PDF //SAXPrettyOutputter();
												// for SVG
				ext = ".pdf";
				pdfOutputStream = new FileOutputStream(
						new File(velotechUtil.getUserRealPath() + dxfPojo1.getOutputPdfName() + ext));
			} else {
				out = new SAXJPEGSerializer();// for Image
				pdfOutputStream = new FileOutputStream(
						new File(velotechUtil.getUserRealPath() + dxfPojo1.getDxfName().split("\\.")[0] + ext));
			}

			out.setOutput(pdfOutputStream);
			generator.generate(doc, out, context);
			pdfOutputStream.flush();
			pdfOutputStream.close();
			System.out.println("end time:" + new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * public void geneRateDxfImageCommon1(OfferDrawingPojo dxfPojo1) throws
	 * ParseException {
	 * 
	 * try { System.out.println("start time:" + new Date());
	 * 
	 * // ExStart:ExportDXFDrawingToPDF String srcFile =
	 * velotechUtil.getUserRealPath() + dxfPojo1.getDxfName();
	 * 
	 * Image image = Image.load(srcFile);
	 * 
	 * // Create an instance of PdfOptions PdfOptions pdfOptions =
	 * getpdfOptions(dxfPojo1);
	 * 
	 * // Export the DXF to PDF image.save(velotechUtil.getUserRealPath() +
	 * dxfPojo1.getOutputPdfName() + ".pdf", pdfOptions); //
	 * ExEnd:ExportDXFDrawingToPDF System.out.println("end time:" + new Date());
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */

	/*
	 * private PdfOptions getpdfOptions(OfferDrawingPojo dxfPojo1) {
	 * 
	 * PdfOptions pdfOptions = new PdfOptions(); try { // Create an instance of
	 * CadRasterizationOptions and set its various // properties
	 * CadRasterizationOptions rasterizationOptions = new
	 * CadRasterizationOptions();
	 * rasterizationOptions.setBackgroundColor(Color.getWhite());
	 * 
	 * if (dxfPojo1.getTbl80TemplateMaster().getLayout().equals("Portrait")) {
	 * rasterizationOptions.setPageWidth(210);
	 * rasterizationOptions.setPageHeight(297); } else {
	 * rasterizationOptions.setPageWidth(297);
	 * rasterizationOptions.setPageHeight(210); }
	 * 
	 * // Set the VectorRasterizationOptions property
	 * pdfOptions.setVectorRasterizationOptions(rasterizationOptions);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } // TODO Auto-generated
	 * method stub return pdfOptions; }
	 */

	private HashMap getContext(OfferDrawingPojo offerDrawingPojo) {

		HashMap context = new HashMap();
		if (offerDrawingPojo.getTbl80TemplateMaster().getLayout().equals("Portrait")) {
			context.put("width", "210mm");
			context.put("paper", "A4");
			context.put("orientation", "portrait");
			context.put("height", "297mm");
		} else {
			context.put("width", "297mm");
			context.put("paper", "A4");
			context.put("orientation", "landscape");
			context.put("height", "210mm");
		}
		context.put("bounds-rule", "modelspace");
		context.put("dpi", "300");

		return context;
	}

	private HashMap<String, String> setAllBlocksValues(HashMap<String, String> blockValues,
			OfferDrawingPojo offerDrawingPojo) {

		try {
			setBlockValues(blockValues, offerDrawingPojo);
			if (offerDrawingPojo.isPerformance() || offerDrawingPojo.isDataSheet())
				fillImageBlocksForPerformance(blockValues, offerDrawingPojo);
			else
				fillImageBlocks(blockValues, offerDrawingPojo);
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
		}
		return blockValues;
	}

	private HashMap<String, String> setBlockValues(HashMap<String, String> blockValues,
			OfferDrawingPojo offerDrawingPojo) {

		try {
			JsonNode node = offerDrawingPojo.getJosson().getNode();

			blockValues.put("offerNo", offerDrawingPojo.getTbl23OfferRev().getTbl23OfferMaster().getOfferNo());
			blockValues.put("pumpModel", offerDrawingPojo.getTbl01CentrifugalModelMaster().getFanModel());
			if(offerDrawingPojo.getTbl23OfferRev().getOfferdate()!=null)
				blockValues.put("offerDate", offerDrawingPojo.getTbl23OfferRev().getOfferdate().toString());
			else
				blockValues.put("offerDate", "");
			if (offerDrawingPojo.getTbl26OfferFan().getTagNo() != null
					&& !offerDrawingPojo.getTbl26OfferFan().getTagNo().equals(""))
			blockValues.put("tagNo", offerDrawingPojo.getTbl26OfferFan().getTagNo());
			else
				blockValues.put("tagNo", "-");
			if(offerDrawingPojo.getTbl23OfferRev().getTbl25CustomerMaster()!=null)
				blockValues.put("customerName",
					offerDrawingPojo.getTbl23OfferRev().getTbl25CustomerMaster().getCustomerName());
			else
				blockValues.put("customerName","-");
			if (offerDrawingPojo.getTbl23OfferRev().getConsultant() != null
					&& !offerDrawingPojo.getTbl23OfferRev().getConsultant().equals(""))
				blockValues.put("consultant", offerDrawingPojo.getTbl23OfferRev().getConsultant());
			else
				blockValues.put("consultant", "-");
			if (offerDrawingPojo.getTbl23OfferRev().getProject() != null
					&& !offerDrawingPojo.getTbl23OfferRev().getProject().equals(""))
				blockValues.put("project", offerDrawingPojo.getTbl23OfferRev().getProject());
			else
				blockValues.put("project", "-");
			if (offerDrawingPojo.getTbl23OfferRev().getEndUser() != null
					&& !offerDrawingPojo.getTbl23OfferRev().getEndUser().equals(""))
				blockValues.put("endUser", offerDrawingPojo.getTbl23OfferRev().getEndUser());
			else
				blockValues.put("endUser", "-");
			
			if (offerDrawingPojo.getTbl23OfferRev().getContractor() != null
					&& !offerDrawingPojo.getTbl23OfferRev().getContractor().equals(""))
			blockValues.put("contractor", offerDrawingPojo.getTbl23OfferRev().getContractor());
			else
				blockValues.put("contractor", "-");
			
			if (offerDrawingPojo.getTbl23OfferRev().getSuppliedBy() != null
					&& !offerDrawingPojo.getTbl23OfferRev().getSuppliedBy().equals(""))
			blockValues.put("suppliedBy", offerDrawingPojo.getTbl23OfferRev().getSuppliedBy());
			else
				blockValues.put("suppliedBy", "-");
			
			blockValues.put("pumpApplication", "-");

			blockValues.put("qty", offerDrawingPojo.getTbl26OfferFan().getTbl27RequirementsDp().getQty().toString());
			List<String> drgParameters = offerDrawingPojo.getDrgParameters();
			for (String string : drgParameters) {
				if (node.findPath(string).isValueNode()) {
					blockValues.put(string, node.findValuesAsText(string).get(0)!=null && 
							!node.findValuesAsText(string).get(0).equals("") && 
							!node.findValuesAsText(string).get(0).equals("0") ? node.findValuesAsText(string).get(0):"-");
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return blockValues;
	}

	private HashMap<String, String> fillImageBlocks(HashMap<String, String> blockValues,
			OfferDrawingPojo offerDrawingPojo) {

		try {
			List<String> attachedFiles = offerDrawingPojo.getImageList();
			File srcFile = null;
			String path = velotechUtil.getProjectRealPath();
			for (int i = 0; i < attachedFiles.size(); i++) {
				if (attachedFiles.get(i).toUpperCase().equals("BOM.JPG"))
					makeBomImage(blockValues, offerDrawingPojo);
				else if (attachedFiles.get(i).toUpperCase().equals("CLIENT.JPG")) {
					Tbl40OfferDocument tbl40OfferDocumentClientLogo = new Tbl40OfferDocument();

					if (offerDrawingPojo.getTbl23OfferRev() != null) {
						tbl40OfferDocumentClientLogo = offerDrawingPojo.getTbl23OfferRev()
								.getTbl40OfferDocumentByClientLogo();

						if (tbl40OfferDocumentClientLogo != null)
							srcFile = new File(
									velotechUtil.getCompanyDocumentPath(
											tbl40OfferDocumentClientLogo.getFileName()));
						blockValues.put(attachedFiles.get(i), velotechUtil.fileCheck(srcFile));
					} else
						blockValues.put(attachedFiles.get(i), velotechUtil.getBlankImagePath());

				} else {
					srcFile = new File(velotechUtil.getCompanyDocumentPath(attachedFiles.get(i)));
					blockValues.put(attachedFiles.get(i), velotechUtil.fileCheck(srcFile));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blockValues;
	}

	private HashMap<String, String> makeBomImage(HashMap<String, String> blockValues,
			OfferDrawingPojo offerDrawingPojo) {

		try {
			List<MocData> mocDatas = offerDrawingPojo.getMocDatas();
			if (mocDatas.size() > 0) {
				JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(mocDatas);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						velotechUtil.getCompanyDocumentPath() + "Moc_data.jasper", new HashMap(),
						beanCollectionDataSource);
				java.awt.Image image = JasperPrintManager.printPageToImage(jasperPrint, 0, 2.0f);

				String bomeImageNameForDxf = offerDrawingPojo.getBomimageName();
				createJpgFromJasper(image, velotechUtil.getUserRealPath() + bomeImageNameForDxf);
				System.out.println("BOM Image Path:" + velotechUtil.getUserRealPath() + bomeImageNameForDxf);
				// createJpgFromJasper(image, offerDrawingPojo.getBOM_PATH());
				blockValues.put("Bom.jpg", velotechUtil.getUserRealPath() + bomeImageNameForDxf);
				// veloObj.PDF_CSV_Exporter(modelMocData, "Moc_data.jasper",
				// offerDrawingPojo.getBomimageName().split("\\.")[0],
				// false, true);
			} else
				blockValues.put("Bom.jpg", velotechUtil.getBlankImagePath());
		} catch (JRException e) {
			e.printStackTrace();
		}
		return blockValues;
	}

	public String makeBomImage(OfferDrawingPojo offerDrawingPojo) {

		String path = "";
		try {

			String companyLogo = offerDrawingPojo.getTbl23OfferRev().getTbl28CompanyMaster().getLogo();
			String logoFileName = companyLogo != null ? companyLogo : "Logo.jpg";

			Map<String, Object> parameters1 = new HashMap<String, Object>();
			parameters1.put("logo", velotechUtil.getCompanyDocumentPath(logoFileName));

			List<MocData> mocDatas = offerDrawingPojo.getMocDatas();
			if (mocDatas.size() > 0) {
				String filename = "bom" + new Random().nextInt(999999) + ".pdf";
				velotechUtil.getReport(mocDatas, "Moc_data_Seperate.jasper", filename, parameters1);
				path = velotechUtil.getUserRealPath() + filename;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	private OfferDrawingPojo download(OfferDrawingPojo offerDrawingPojo) {

		List<String> temp = new ArrayList<String>();
		List<String> imageList = new ArrayList<String>();
		try {
			imageList = offerDrawingPojo.getImageList();
			temp.add(offerDrawingPojo.getDxfName());
			for (int i = 0; i < imageList.size(); i++) {
				if (imageList.get(i).toUpperCase().equals("BOM.JPG")) {
					temp.add(offerDrawingPojo.getBomimageName());
					temp.add(offerDrawingPojo.getBomimageName().split("\\.")[0] + ".csv");
				} else
					temp.add(imageList.get(i));
			}
			makeZip(temp, offerDrawingPojo.getDxfName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offerDrawingPojo;
	}

	void makeZip(List<String> fileList, String zipName) {

		try {
			byte[] buffer = new byte[1024];
			FileOutputStream fout = new FileOutputStream(
					velotechUtil.getUserRealPath() + zipName.split("\\.")[0] + ".zip");
			ZipOutputStream zout = new ZipOutputStream(fout);
			File file = null;
			for (int i = 0; i < fileList.size(); i++) {
				file = new File(velotechUtil.getUserRealPath() + fileList.get(i));
				if (file.exists()) {
					FileInputStream fin = new FileInputStream(velotechUtil.getUserRealPath() + fileList.get(i));

					zout.putNextEntry(new ZipEntry(fileList.get(i)));
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

	void createJpgFromJasper(java.awt.Image image, String imgPath) {

		try {
			MediaTracker mediaTracker = new MediaTracker(new Container());
			mediaTracker.addImage(image, 0);
			mediaTracker.waitForID(0);
			// determine thumbnail size from WIDTH and HEIGHT
			int thumbWidth = Integer.parseInt("2000");// size in pixels
			int thumbHeight = Integer.parseInt("2000");// size in pixles
			double thumbRatio = (double) thumbWidth / (double) thumbHeight;
			int imageWidth = image.getWidth(null);
			int imageHeight = image.getHeight(null);
			double imageRatio = (double) imageWidth / (double) imageHeight;
			if (thumbRatio < imageRatio) {
				thumbHeight = (int) (thumbWidth / imageRatio);
			} else {
				thumbWidth = (int) (thumbHeight * imageRatio);
			}
			// draw original image to thumbnail image object and
			// scale it to the new size on-the-fly
			BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(image, 0, 0, thumbWidth + 1, thumbHeight + 1, null);
			// save thumbnail image to OUTFILE
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(imgPath));
			ImageIO.write(thumbImage, "jpeg", out);
			/*
			 * JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			 * JPEGEncodeParam param =
			 * encoder.getDefaultJPEGEncodeParam(thumbImage);
			 * param.setQuality(1.0f, false); // Best quality - 1.0f, Lowest //
			 * quality - 0.0f encoder.setJPEGEncodeParam(param);
			 * encoder.encode(thumbImage);
			 */
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private HashMap<String, String> fillImageBlocksForPerformance(HashMap<String, String> blockValues,
			OfferDrawingPojo offerDrawingPojo) {

		try {
			List<String> attachedFiles = offerDrawingPojo.getImageList();
			for (int i = 0; i < attachedFiles.size(); i++) {
				if (attachedFiles.get(i).toUpperCase().equals("LOGO.JPG"))
					copyImage(blockValues, offerDrawingPojo.getOfferLogoName(), attachedFiles.get(i));
				else if (attachedFiles.get(i).toUpperCase().equals("LOGO1.JPG")) {
					if ("Mather & Platt Pumps Ltd".equals(offerDrawingPojo.getOfferCompanyName()))
						copyImage(blockValues, "Logo_Wilo.jpg", attachedFiles.get(i));
					else
						blockValues.put(attachedFiles.get(i), velotechUtil.getBlankImagePath());
				} else
					copyImage(blockValues, attachedFiles.get(i), attachedFiles.get(i));
			}
			blockValues.put("perfChart.png", velotechUtil.getUserRealPath() + offerDrawingPojo.getPerfImageName());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return blockValues;
	}

	private HashMap<String, String> copyImage(HashMap<String, String> blockValues, String srcFileName,
			String destFileName) {

		try {
			File srcFile = null;
			if (srcFileName != null)
				srcFile = new File(velotechUtil.getCompanyDocumentPath(), srcFileName);
			if (srcFileName != null && srcFile.exists() && !srcFileName.equals("")) {
				File dtFile = new File(velotechUtil.getUserRealPath(), destFileName);
				velotechUtil.copyfile(srcFile, dtFile);
				blockValues.put(destFileName, velotechUtil.getUserRealPath() + destFileName);
			} else
				blockValues.put(destFileName, velotechUtil.getBlankImagePath());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return blockValues;
	}

	private HashMap<String, String> commonTrueBlock(HashMap<String, String> blockValues,
			HashMap<String, String> flangeTrueBlocks, List<String> blockArray) {

		String value = "";
		try {
			for (String key : new ArrayList<String>(flangeTrueBlocks.keySet()))
				flangeTrueBlocks.put(key.toUpperCase(), flangeTrueBlocks.remove(key));

			for (int i = 0; i < blockArray.size(); i++) {

				if (flangeTrueBlocks.containsKey(blockArray.get(i).split("_")[0].toUpperCase())) {

					if (flangeTrueBlocks.get(blockArray.get(i).split("_")[0].toUpperCase()) != null)
						value = flangeTrueBlocks.get(blockArray.get(i).split("_")[0].toUpperCase());
				} else
					value = "";

				blockValues.put(blockArray.get(i), value);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return blockValues;
	}

	private Map<String, Boolean> setLayersOnOff(OfferDrawingPojo offerDrawing) {

		Map<String, Boolean> layerValues = new HashMap<String, Boolean>();
		try {
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return layerValues;
	}

	private HashMap<String, String> setAllFalseBlocksValues(HashMap<String, String> blockValues,
			OfferDrawingPojo offerDrawingPojo) {

		HashMap<String, List<String>> tableNameBlockNamePairFalse = new HashMap<String, List<String>>();
		List<String> falsetableList = new ArrayList<String>();
		List<String> parameters = null;
		try {

			falsetableList = velotechUtil.castMapToList(tableNameBlockNamePairFalse);
			for (int i = 0; i < falsetableList.size(); i++) {
				parameters = new ArrayList<String>();
				parameters = tableNameBlockNamePairFalse.get(falsetableList.get(i));

			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return blockValues;
	}

}


package com.velotech.fanselection.utils;


import com.aspose.cad.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import com.aspose.cad.imageoptions.UnitType;

import com.aspose.cad.Image;
import com.aspose.cad.fileformats.cad.CadImage;
import com.aspose.cad.fileformats.cad.CadLayersList;
import com.aspose.cad.fileformats.cad.cadconsts.CadEntityTypeName;
import com.aspose.cad.fileformats.cad.cadconsts.CadObjectTypeName;
import com.aspose.cad.fileformats.cad.cadobjects.CadBaseEntity;
import com.aspose.cad.fileformats.cad.cadobjects.CadBaseObject;
import com.aspose.cad.fileformats.cad.cadobjects.CadDimensionBase;
import com.aspose.cad.fileformats.cad.cadobjects.CadInsertObject;
import com.aspose.cad.fileformats.cad.cadobjects.CadMText;
import com.aspose.cad.fileformats.cad.cadobjects.CadRasterImage;
import com.aspose.cad.fileformats.cad.cadobjects.CadRasterImageDef;
import com.aspose.cad.fileformats.cad.cadobjects.CadText;
import com.aspose.cad.fileformats.cad.cadobjects.attentities.CadAttDef;
import com.aspose.cad.fileformats.cad.cadobjects.attentities.CadAttrib;
import com.aspose.cad.fileformats.cad.cadparameters.CadBoolParameter;
import com.aspose.cad.fileformats.cad.cadparameters.CadShortParameter;
import com.aspose.cad.imageoptions.CadRasterizationOptions;
import com.aspose.cad.imageoptions.PdfOptions;
import com.aspose.cad.imageoptions.RasterizationQuality;
import com.aspose.cad.imageoptions.RasterizationQualityValue;

@Component
public class ExportDXFDrawingToPDF {

	private static Logger log = LogManager.getLogger(ExportDXFDrawingToPDF.class.getName());

	private HashMap<String, String> blockValues = new HashMap<String, String>();

	private Map<String, Boolean> layersList = new HashMap<String, Boolean>();

	public void convert(String dxfFilePathSrc, String outputFileName, HashMap<String, String> blockValues1,
			Map<String, Boolean> layerValues, String layout) throws IOException {

		try {
			VelotechUtil veloObj = new VelotechUtil();
			com.aspose.cad.License license = new com.aspose.cad.License();

			license.setLicense(veloObj.getCompanyDocumentPath() + "Aspose.CAD.Java.lic");

			System.out.println("start time:" + new Date());
			// ExStart:ExportDXFDrawingToPDF
			String srcFile = dxfFilePathSrc;

			Image image = Image.load(srcFile);
			CadImage cadImage = (CadImage) image;

			// Create an instance of CadRasterizationOptions and set its various
			// properties
			CadRasterizationOptions rasterizationOptions = new CadRasterizationOptions();
			setPage(rasterizationOptions, layout, cadImage.getUnitType());

			rasterizationOptions.setAutomaticLayoutsScaling(true);
			rasterizationOptions.setBackgroundColor(Color.getWhite());
			rasterizationOptions.setLayouts(new String[] { "Model" });

			// Added to reduced pdf size
			RasterizationQuality q = new RasterizationQuality();
			q.setObjectsPrecision(RasterizationQualityValue.Low);
			q.setText(RasterizationQualityValue.Low);
			q.setTextThicknessNormalization(true);
			rasterizationOptions.setQuality(q);

			blockValues = blockValues1;
			layersList = layerValues;

			System.out.println(CadEntityTypeName.IMAGE);

			setLayersOnOff(cadImage);
			for (CadBaseEntity entity : cadImage.getEntities()) {
				if (entity.getObjectHandle().equalsIgnoreCase("9AA6")) {

					System.out.println(entity.getTypeName());
					System.out.println("");

				}
				IterateCADNodeEntities(entity, cadImage);
			}

			// Create an instance of PdfOptions
			PdfOptions pdfOptions = new PdfOptions();
			// Set the VectorRasterizationOptions property
			pdfOptions.setVectorRasterizationOptions(rasterizationOptions);

			// Export the DXF to PDF
			image.save(veloObj.getUserRealPath() + outputFileName + ".pdf", pdfOptions);

			// ExEnd:ExportDXFDrawingToPDF
			System.out.println("end time:" + new Date());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public String convert(String dxfFilePathSrc, String outputFileName, String layout) throws IOException {
		String path = "";
		try {
			VelotechUtil veloObj = new VelotechUtil();
			com.aspose.cad.License license = new com.aspose.cad.License();

			license.setLicense(veloObj.getCompanyDocumentPath() + "Aspose.CAD.Java.lic");

			System.out.println("start time:" + new Date());
			// ExStart:ExportDXFDrawingToPDF
			String srcFile = dxfFilePathSrc;

			Image image = Image.load(srcFile);
			CadImage cadImage = (CadImage) image;

			// Create an instance of CadRasterizationOptions and set its various
			// properties
			CadRasterizationOptions rasterizationOptions = new CadRasterizationOptions();
			setPage(rasterizationOptions, layout, cadImage.getUnitType());

			rasterizationOptions.setAutomaticLayoutsScaling(true);
			rasterizationOptions.setBackgroundColor(Color.getWhite());
			rasterizationOptions.setLayouts(new String[] { "Model" });

			// Added to reduced pdf size
			RasterizationQuality q = new RasterizationQuality();
			q.setObjectsPrecision(RasterizationQualityValue.Low);
			q.setText(RasterizationQualityValue.Low);
			q.setTextThicknessNormalization(true);
			rasterizationOptions.setQuality(q);

			System.out.println(CadEntityTypeName.IMAGE);

			// Create an instance of PdfOptions
			PdfOptions pdfOptions = new PdfOptions();
			// Set the VectorRasterizationOptions property
			pdfOptions.setVectorRasterizationOptions(rasterizationOptions);

			// Export the DXF to PDF
			image.save(veloObj.getUserRealPath() + outputFileName + ".pdf", pdfOptions);

			// ExEnd:ExportDXFDrawingToPDF
			System.out.println("end time:" + new Date());
			path= veloObj.getUserContextPath() +  outputFileName + ".pdf";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return path;
	}

	private void setLayersOnOff(CadImage cadImage) {

		CadLayersList lay = cadImage.getLayers();
		List<String> layerName = lay.getLayersNames();
		CadBoolParameter cbFalse = new CadBoolParameter();
		cbFalse.setValue(false);
		CadBoolParameter cbTrue = new CadBoolParameter();
		cbTrue.setValue(true);
		for (String string : layerName) {
			if (layersList.containsKey(string)) {
				if (layersList.get(string) == true)
					lay.getLayer(string).setPlotFlag(cbTrue);
				else
					lay.getLayer(string).setPlotFlag(cbFalse);
			}
			CadShortParameter p = new CadShortParameter();
			p.setValue((short) 0);
			lay.getLayer(string).setLineWeight(p);

		}
	}

	private void IterateCADNodeEntities(CadBaseEntity obj, CadImage cadImage) throws IOException {

		switch (obj.getTypeName()) {
		case CadEntityTypeName.TEXT:
			CadText childObjectText = (CadText) obj;
			if (blockValues.containsKey(childObjectText.getDefaultValue())
					&& !(childObjectText.getLayerName().equals("0") || childObjectText.getLayerName().equals("Sheet")))
				if (blockValues.get(childObjectText.getDefaultValue()) != null)
					childObjectText.setDefaultValue(blockValues.get(childObjectText.getDefaultValue()));
			break;

		case CadEntityTypeName.MTEXT:
			CadMText childObjectMText = (CadMText) obj;
			if (blockValues.containsKey(childObjectMText.getText())) {
				childObjectMText.setText(blockValues.get(childObjectMText.getText()) != null
						? blockValues.get(childObjectMText.getText()) : "");
			}
			break;

		case CadEntityTypeName.ATTDEF:
			CadAttDef attDef = (CadAttDef) obj;
			if (blockValues.containsKey(attDef.getDefaultString())) {
				attDef.setDefaultString(blockValues.get(attDef.getDefaultString()) != null
						? blockValues.get(attDef.getDefaultString()) : "");
				attDef.setAlignmentPoint(attDef.getAlignmentPoint());
			}
			break;

		case CadEntityTypeName.ATTRIB:
			CadAttrib attAttrib = (CadAttrib) obj;
			if (blockValues.containsKey(attAttrib.getDefaultText())
					&& blockValues.get(attAttrib.getDefaultText()) != null) {
				// System.out.println("attribute:"+attAttrib.getDefaultText());
				attAttrib.setDefaultText(blockValues.get(attAttrib.getDefaultText()) != null
						? blockValues.get(attAttrib.getDefaultText()) : "");
				attAttrib.setAlignmentPoint(attAttrib.getAlignmentPoint());
				attAttrib.setAttribAlignmentPoint(attAttrib.getAttribAlignmentPoint());
				attAttrib.setTextStartPoint(attAttrib.getTextStartPoint());
			}
			break;
		case CadEntityTypeName.IMAGE:
			CadRasterImage rasterImage = ((CadRasterImage) obj);
			String imagePath = null;
			for (CadBaseObject baseObject : cadImage.getObjects()) {
				if (baseObject.getTypeName() == CadObjectTypeName.IMAGEDEF) {
					CadRasterImageDef imageDef = (CadRasterImageDef) baseObject;
					if (imageDef.getObjectHandle().equals(rasterImage.getImageDefReference())) { // image
																									// path
																									// was
																									// found
																									// imagePath
																									// =
						imagePath = imageDef.getFileName();
						if (blockValues.containsKey(imagePath)) {
							BufferedImage bimg = ImageIO.read(new File(blockValues.get(imagePath)));
							int width = bimg.getWidth();
							int height = bimg.getHeight();
							Double xscale = rasterImage.getImageSizeU() / width;
							Double yscale = rasterImage.getImageSizeV() / height;
							rasterImage.getUVector().setX(rasterImage.getUVector().getX() * Math.min(xscale, yscale));
							rasterImage.getVVector().setY(rasterImage.getVVector().getY() * Math.min(xscale, yscale));
							imageDef.setFileName(blockValues.get(imagePath));
							break;
						}

					}
				}
			}

			break;
		case CadEntityTypeName.INSERT:
			CadInsertObject childInsertObject = (CadInsertObject) obj;

			for (CadBaseEntity tempobj : childInsertObject.getChildObjects()) {
				IterateCADNodeEntities(tempobj, cadImage);
			}
			break;
		case CadEntityTypeName.DIMENSION:
			CadDimensionBase dim = (CadDimensionBase) obj;
			if (blockValues.containsKey(dim.getText())) {
				dim.setText(blockValues.get(dim.getText()) != null ? blockValues.get(dim.getText()) : "");
			}
			break;
		}
	}

	public void setPage(CadRasterizationOptions rasterizationOptions, String layout, int cadImageUnitType) {

		// reference
		// https://apireference.aspose.com/cad/java/com.aspose.cad/Image
		Boolean currentUnitIsMetric = false;
		double currentUnitCoefficient = 1.0;
		PageSize pageSize = new PageSize(cadImageUnitType);
		currentUnitIsMetric = pageSize.getIsMetric();
		currentUnitCoefficient = pageSize.getCoefficient();
		if (currentUnitIsMetric) {
			double metersCoeff = 1 / 1000.0;
			double scaleFactor = metersCoeff / currentUnitCoefficient;
			rasterizationOptions.setUnitType(UnitType.Millimeter);
			if (layout.equals("Portrait")) {
				rasterizationOptions.setPageWidth((float) (210 * scaleFactor));
				rasterizationOptions.setPageHeight((float) (297 * scaleFactor));

			} else {
				rasterizationOptions.setPageWidth((float) (297 * scaleFactor));
				rasterizationOptions.setPageHeight((float) (210 * scaleFactor));
			}

		} else {
			rasterizationOptions.setUnitType(UnitType.Inch);
			if (layout.equals("Portrait")) {
				rasterizationOptions.setPageWidth((float) (8.27f / currentUnitCoefficient));
				rasterizationOptions.setPageHeight((float) (11.69f / currentUnitCoefficient));

			} else {
				rasterizationOptions.setPageWidth((float) (11.69f / currentUnitCoefficient));
				rasterizationOptions.setPageHeight((float) (8.27f / currentUnitCoefficient));
			}
		}

	}

	public HashMap<String, String> getBlockValues() {

		return blockValues;
	}

	public void setBlockValues(HashMap<String, String> blockValues) {

		this.blockValues = blockValues;
	}
}

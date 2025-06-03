
package com.velotech.fanselection.utils.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.List;

import org.jfree.chart.plot.DefaultDrawingSupplier;

import com.aspose.cad.internal.ay.iF;

public class GraphModel {

	private double[] lineEq;

	private double qMin;

	private double qMax;

	private boolean showPoints;

	private Color linColor;

	private BasicStroke lineType;

	private int lineThickness;

	private Color pointColor;

	private Shape pointShape;

	private String annotation;

	public GraphModel() {
	}

	public GraphModel(double[] lineEq, double qMin, double qMax, boolean showPoints, String linColor, String lineType,
			int lineThickness, String pointColor, String pointShape, String annotation1) {
		this.lineEq = lineEq;
		this.qMin = qMin;
		this.qMax = qMax;
		this.showPoints = showPoints;
		this.linColor = getColor(linColor);
		this.lineType = getLineType(lineType, lineThickness);
		this.lineThickness = lineThickness;
		this.pointColor = getColor(pointColor);
		this.pointShape = getPointShape(pointShape);
		if (annotation1 == null)
			this.annotation = "";
		else
			this.annotation = annotation1;

	}

	public double[] getLineEq() {

		return lineEq;
	}

	public void setLineEq(double[] lineEq) {

		this.lineEq = lineEq;
	}

	public double getqMin() {

		return qMin;
	}

	public void setqMin(double qMin) {

		this.qMin = qMin;
	}

	public double getqMax() {

		return qMax;
	}

	public void setqMax(double qMax) {

		this.qMax = qMax;
	}

	public boolean isShowPoints() {

		return showPoints;
	}

	public void setShowPoints(boolean showPoints) {

		this.showPoints = showPoints;
	}

	public Color getLinColor() {

		return linColor;
	}

	public void setLinColor(String linColor) {

		this.linColor = getColor(linColor);
	}

	public BasicStroke getLineType() {

		return lineType;
	}

	public int getLineThickness() {

		return lineThickness;
	}

	public void setLineThickness(int lineThickness) {

		this.lineThickness = lineThickness;
	}

	public void setLineType(String lineType) {

		this.lineType = getLineType(lineType, lineThickness);
	}

	public Color getPointColor() {

		return pointColor;
	}

	public void setPointColor(String pointColor) {

		this.pointColor = getColor(pointColor);
	}

	public Shape getPointShape() {

		return pointShape;
	}

	public void setPointShape(String pointShape) {

		this.pointShape = getPointShape(pointShape);
	}

	public String getAnnotation() {

		return annotation;
	}

	private Color getColor(String color) {

		if (color.contains("red")) {
			return Color.RED;
		} else if (color.contains("blue")) {
			return Color.BLUE;
		} else if (color.contains("green")) {
			return Color.GREEN;
		} else if (color.contains("cyan")) {
			return Color.CYAN;
		} else if (color.contains("gray")) {
			return Color.GRAY;
		} else if (color.contains("yellow")) {
			return Color.YELLOW;
		} else if (color.contains("magenta")) {
			return Color.MAGENTA;
		} else if (color.contains("orange")) {
			return Color.orange;
		}else if(color.contains("lightBlue") ){
			return new Color(115, 147, 217);
		}
			else {
		
			return Color.BLACK;
			}
	}

	private BasicStroke getLineType(String lineType, int lineThickness) {

		if (lineType.equals("Solid"))
			return new BasicStroke(lineThickness);
		else if (lineType.equals("Long dash"))
			return new BasicStroke(lineThickness);
		else if (lineType.equals("Short dash"))
			return new BasicStroke(lineThickness);
		else if (lineType.equals("Dash dot"))
			return new BasicStroke(lineThickness);
		else
			return new BasicStroke(lineThickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
					new float[] { 1.0f, 9f }, 0);
	}

	private float[][] pattern = { { 10.0f }, { 10.0f, 10.0f }, { 10.0f, 10.0f, 2.0f, 10.0f }, { 1.0f, 20.0f } };

	private Stroke[] strokes(float width) {

		Stroke[] strokes = new Stroke[] { new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f), // solid
																														// line
				new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, pattern[1], 0.0f), // dashed
																												// line
				new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, pattern[2], 0.0f), // dash-dotted
																												// line
				new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, pattern[3], 0.0f), // dotted
																												// line
		};
		return strokes;
	}

	public Stroke getOdynStroke(float width, int type) {

		return strokes(width)[type];
	}

	private Shape getPointShape(String pointShape) {

		Shape[] Shapes = DefaultDrawingSupplier.createStandardSeriesShapes();
		// 0 are square
		// 1 are circles
		// 2 are up-pointing triangle
		// 3 are diamond
		// 4 are horizontal rectangle
		// 5 are down-pointing triangle
		// 6 are horizontal ellipse
		// 7 are right-pointing triangle
		// 8 are vertical rectangle
		// 9 are left-pointing triangle
		if (pointShape.equals("square"))
			return Shapes[0];
		else if (pointShape.equals("circles"))
			return Shapes[1];
		else if (pointShape.equals("upTriangle"))
			return Shapes[2];
		else if (pointShape.equals("diamond"))
			return Shapes[3];
		else if (pointShape.equals("horizontalRectangle"))
			return Shapes[4];
		else if (pointShape.equals("downTriangle"))
			return Shapes[5];
		else if (pointShape.equals("horizontalEllipse"))
			return Shapes[6];
		else if (pointShape.equals("rightPointingTriangle"))
			return Shapes[7];
		else if (pointShape.equals("verticalRectangle"))
			return Shapes[8];
		else
			return Shapes[9];
	}
}

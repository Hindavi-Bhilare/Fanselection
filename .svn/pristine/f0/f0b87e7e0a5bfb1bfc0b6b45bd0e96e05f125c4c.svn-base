
package com.velotech.fanselection.utils.graph;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYImageAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.util.ShapeUtils;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.aspose.cad.internal.ac.n;
import com.aspose.cad.internal.ay.iF;
import com.velotech.fanselection.utils.VelotechUtil;

import flanagan.complex.Complex;
import flanagan.complex.ComplexPoly;

@Component
@Scope("prototype")
public class GraphUtil {

	static Logger log = LogManager.getLogger(VelotechUtil.class.getName());

	@Autowired
	private VelotechUtil velotechUtil;

	private Map<Double, List<Double>> annotationXList = new LinkedHashMap<Double, List<Double>>();

	private Map<Double, List<Point>> annotationPoints = new LinkedHashMap<Double, List<Point>>();

	public CombinedDomainXYPlot combineGraph(String xAxisUnit, boolean includeZeroInX, int xGap) {

		NumberAxis xAxis = new NumberAxis(xAxisUnit);
		xAxis.setAutoRangeIncludesZero(includeZeroInX);
		CombinedDomainXYPlot combined_plot = new CombinedDomainXYPlot(xAxis);
		if (xGap > 0)
			combined_plot.setGap(xGap);
		combined_plot.setOrientation(PlotOrientation.VERTICAL);
		return combined_plot;
	}

	private double getTickUnitSize(NumberAxis axis, int number) {

		double interval = 0.0;
		double tempMax = 0.0;
		if (axis.getUpperBound() > 500) {
			interval = 100.0;
		} else if (axis.getUpperBound() > 100) {
			interval = 50.0;
		} else if (axis.getUpperBound() > 10) {
			interval = 10.0;
		} else {
			interval = 1.0;
		}
		tempMax = interval * Math.ceil(axis.getUpperBound() / interval);
		int tick = (int) Math.ceil(tempMax / number);

		// removed to take care of axis value below 10
		/*
		 * while (tick % number != 0) { tick++; }
		 */

		return tick;
	}

	public JFreeChart getChart(String chartHeading, String chartSubHeading, CombinedDomainXYPlot combined_plot,
			boolean minorAxisX) {

		NumberAxis xAxis = (NumberAxis) combined_plot.getDomainAxis();
		// NumberTickUnit rUnit = new NumberTickUnit(getTickUnitSize(xAxis,
		// 10));
		// xAxis.setTickUnit(rUnit);
		xAxis.setAutoRangeIncludesZero(true);

		if (minorAxisX) {
			xAxis.setMinorTickCount(2);
			xAxis.setMinorTickMarksVisible(true);
			combined_plot.setDomainMinorGridlinesVisible(true);
			combined_plot.setDomainMinorGridlineStroke(new BasicStroke(0.0f));
			combined_plot.setDomainMinorGridlinePaint(Color.BLACK);
			combined_plot.setDomainGridlinePaint(new Color(128, 128, 128));
			combined_plot.setDomainGridlineStroke(new BasicStroke(1.0f));
		}
		JFreeChart chart = new JFreeChart(chartHeading, JFreeChart.DEFAULT_TITLE_FONT, combined_plot, false);
		chart.setBackgroundPaint(Color.white);
		if (!chartSubHeading.equals("")) {
			chart.addSubtitle(new TextTitle(chartSubHeading));
		}

		return chart;
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public XYPlot individualGraph(List<GraphModel> graphModelList, List<Point> dutyPointList, String yUnit,
			java.awt.Point annotationLocation, boolean minorAxisX, boolean minorAxisY, boolean yAxisIncludeZero) {

		VelotechUtil velotechUtil = new VelotechUtil();
		XYPlot subPlot = null;
		try {
			XYSeriesCollection x_y_series_collectionPoint = new XYSeriesCollection();
			// XYSeriesCollection x_y_series_collectionLine = new
			// XYSeriesCollection();
			XYSeriesCollection x_y_series_collectionLineDoted = new XYSeriesCollection();
			XYSeriesCollection x_y_series_collectionDutyPoint = new XYSeriesCollection();
			List<XYTextAnnotation> annotationList = new ArrayList<XYTextAnnotation>();
			Font font = new Font("SansSerif", Font.PLAIN, 10);
			// for setting up line thickness and point shape
			XYLineAndShapeRenderer renderer_point = new XYLineAndShapeRenderer();
			XYSplineRenderer renderer_Line_doted = new XYSplineRenderer();
			XYLineAndShapeRenderer renderer_duty_point = new XYLineAndShapeRenderer();

			for (int i = 0; i < graphModelList.size(); i++) {

				XYSeries x_y_series_line_doted = new XYSeries(i + "3");
				XYSeries x_y_series_point = new XYSeries(i + "2");

				List<List<Double>> linePointsDoted = null;
				if (graphModelList.get(i).getLineEq() != null) {
					linePointsDoted = getInterPolationEqn(graphModelList.get(i).getLineEq(),
							graphModelList.get(i).getqMin(), graphModelList.get(i).getqMax());
					for (int j = 0; j < linePointsDoted.get(0).size(); j++) {
						x_y_series_line_doted.add(linePointsDoted.get(0).get(j), linePointsDoted.get(1).get(j));
					}
				}
				// adding set of XYSeries to the collection
				x_y_series_collectionPoint.addSeries(x_y_series_point);
				// x_y_series_collectionLine.addSeries(x_y_series_line);
				x_y_series_collectionLineDoted.addSeries(x_y_series_line_doted);

				renderer_point.setSeriesShape(i, graphModelList.get(i).getPointShape());
				renderer_point.setSeriesPaint(i, graphModelList.get(i).getPointColor());
				if (graphModelList.get(i).getAnnotation() == "8")
					System.out.println("wait");
				renderer_Line_doted.setSeriesPaint(i, graphModelList.get(i).getLinColor());
				Float lineThicknes = (float) graphModelList.get(i).getLineThickness();
				if (lineThicknes >= 2)
					lineThicknes = (float) (lineThicknes - 1.6);
				else
					lineThicknes = (float) (lineThicknes - 0.9);

				renderer_Line_doted.setSeriesStroke(i,
						new BasicStroke(lineThicknes, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, null, 0.0f));

				XYTextAnnotation annotation = null;

				if (annotationLocation != null) {
					if (x_y_series_line_doted.getItemCount() != 0 && annotationLocation.equals(new java.awt.Point())) {
						double xVal = 0, yVal = 0;
						xVal = x_y_series_line_doted.getMinX();
						yVal = x_y_series_collectionLineDoted.getStartY(i, 0).intValue();
						annotation = new XYTextAnnotation(graphModelList.get(i).getAnnotation(),
								xVal /*- x_y_series_collectionLineDoted.getDomainUpperBound(true) * 0.001*/,
								yVal + x_y_series_collectionLineDoted.getRangeUpperBound(true) * 0.05);
					}

					if (x_y_series_line_doted.getItemCount() != 0
							&& annotationLocation.equals(new java.awt.Point(0, 1))) {
						double xVal = 0, yVal = 0;
						xVal = x_y_series_line_doted.getMaxX()
								+ x_y_series_collectionLineDoted.getDomainUpperBound(true) * 0.005;
						yVal = x_y_series_collectionLineDoted.getYValue(i, x_y_series_line_doted.getItemCount() - 1)
								+ x_y_series_collectionLineDoted.getRangeUpperBound(false) * 0.008;
						annotation = new XYTextAnnotation(graphModelList.get(i).getAnnotation(), xVal, yVal);
					}

					double yDistAnno = 0d;
					if (i == 0)
						yDistAnno = x_y_series_collectionLineDoted.getRangeUpperBound(false);

					if (x_y_series_line_doted.getItemCount() != 0
							&& annotationLocation.equals(new java.awt.Point(1, 1))) {
						double xVal = 0, yVal = 0;
						xVal = x_y_series_collectionLineDoted.getDomainUpperBound(true)
								+ graphModelList.get(graphModelList.size() - 1).getqMax() * 0.01;
						yVal = x_y_series_collectionLineDoted.getYValue(i, x_y_series_line_doted.getItemCount() - 1)
								- yDistAnno * 0.02;
						annotation = new XYTextAnnotation(graphModelList.get(i).getAnnotation(), xVal, yVal);
					}
				}

				if (annotation != null && graphModelList.get(i).getLinColor() == Color.BLUE) {
					Font fontNew = new Font("Arial", Font.PLAIN, 8);
					annotation.setFont(fontNew);
					annotation.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
					annotationList.add(annotation);
				}
			}
			List<XYAnnotation> dutyPointXYAnnotation = new ArrayList<XYAnnotation>();

			if (dutyPointList != null) {
				XYSeries x_y_series_duty_point = new XYSeries("dutyPoint" + 1);
				ImageIcon imageIcon = new ImageIcon(velotechUtil.getRealPath("/images/dpShapeSmall.gif"));
				for (int i = 0; i < dutyPointList.size(); i++) {
					/*
					 * x_y_series_duty_point.add(dutyPointList.get(i).getPointCoordinates()[0],
					 * dutyPointList.get(i).getPointCoordinates()[1]);
					 */
					XYAnnotation xyannotation = new XYImageAnnotation(dutyPointList.get(i).getPointCoordinates()[0],
							dutyPointList.get(i).getPointCoordinates()[1], imageIcon.getImage(),
							RectangleAnchor.TOP_RIGHT);
					dutyPointXYAnnotation.add(xyannotation);
				}

				x_y_series_collectionDutyPoint.addSeries(x_y_series_duty_point);

				for (int s = 0; s < x_y_series_collectionDutyPoint.getSeriesCount(); s++) {
					renderer_duty_point.setSeriesLinesVisible(s, false);
					renderer_duty_point.setSeriesShapesVisible(s, true);
					renderer_duty_point.setSeriesPaint(s, Color.BLUE);

					Shape cross = ShapeUtils.createDownTriangle(3);
					Shape shape = new Ellipse2D.Double(-3, -3, 6, 6);

					Shape[] Shapes = DefaultDrawingSupplier.createStandardSeriesShapes();
					renderer_duty_point.setDefaultShape(shape);
				}

			}

			for (int s = 0; s < x_y_series_collectionPoint.getSeriesCount(); s++) {
				renderer_point.setSeriesLinesVisible(s, false);
				renderer_point.setSeriesShapesVisible(s, graphModelList.get(s).isShowPoints());

			}
			for (int s = 0; s < x_y_series_collectionLineDoted.getSeriesCount(); s++) {
				// renderer_Line.setSeriesLinesVisible(s, true);
				// renderer_Line.setSeriesShapesVisible(s, false);
				renderer_Line_doted.setSeriesLinesVisible(s, true);
				renderer_Line_doted.setSeriesShapesVisible(s, false);
			}

			// xy-axis names nd condition for includes zero or not
			List<Double> maxYList = new ArrayList<Double>();

			// if
			// (!Double.isNaN(x_y_series_collectionLine.getRangeUpperBound(true)))
			// maxYList.add(x_y_series_collectionLine.getRangeUpperBound(true));
			if (!Double.isNaN(x_y_series_collectionPoint.getRangeUpperBound(true)))
				maxYList.add(x_y_series_collectionPoint.getRangeUpperBound(true));
			if (!Double.isNaN(x_y_series_collectionLineDoted.getRangeUpperBound(true)))
				maxYList.add(x_y_series_collectionLineDoted.getRangeUpperBound(true));
			if (!Double.isNaN(x_y_series_collectionDutyPoint.getRangeUpperBound(true)))
				maxYList.add(x_y_series_collectionDutyPoint.getRangeUpperBound(true));

			NumberAxis yAxis = new NumberAxis(yUnit);
			double max_yAxisHRange = Collections.max(maxYList) * 1.2;
			double min_yAxisHRange = Collections.min(maxYList);
			if (yAxisIncludeZero) {
				yAxis.setAutoRangeIncludesZero(true);
				yAxis.setRange(0, max_yAxisHRange);
			} else {
				yAxis.setAutoRangeIncludesZero(false);
			}

			if (minorAxisY) {
				yAxis.setMinorTickCount(2);
				yAxis.setMinorTickMarksVisible(true);
			}

			subPlot = new XYPlot(x_y_series_collectionLineDoted, new NumberAxis(""), yAxis, renderer_Line_doted);
			subPlot.setDataset(1, x_y_series_collectionPoint);
			subPlot.setRenderer(1, renderer_point);
			subPlot.setDataset(2, x_y_series_collectionDutyPoint);
			subPlot.setRenderer(2, renderer_duty_point);

			NumberAxis xAxis = (NumberAxis) subPlot.getDomainAxis();
			NumberTickUnit rUnit = new NumberTickUnit(getTickUnitSize(xAxis, 10));
			xAxis.setTickUnit(new NumberTickUnit(2));
			xAxis.setAutoRange(false);
			if (minorAxisX) {
				xAxis.setMinorTickCount(2);
				xAxis.setMinorTickMarksVisible(true);
			}

			/*
			 * if(dutyPointList!=null) { subPlot.setDataset(3,
			 * x_y_series_collectionDutyPoint); subPlot.setRenderer(3, renderer_duty_point);
			 * }
			 */

			for (int i = 0; i < annotationList.size(); i++)
				subPlot.addAnnotation(annotationList.get(i));

			for (int i = 0; i < dutyPointXYAnnotation.size(); i++)
				subPlot.addAnnotation(dutyPointXYAnnotation.get(i));

			subPlot.setDomainGridlinePaint(Color.DARK_GRAY);
			subPlot.setRangeGridlinePaint(Color.DARK_GRAY);

			subPlot.setDomainGridlineStroke(new BasicStroke(0.6f));
			subPlot.setRangeGridlineStroke(new BasicStroke(0.6f));

			if (minorAxisY) {
				subPlot.setRangeMinorGridlinesVisible(true);
				subPlot.setRangeMinorGridlineStroke(new BasicStroke(0.15f));
				subPlot.setRangeMinorGridlinePaint(Color.LIGHT_GRAY);
			}
			if (minorAxisX) {
				subPlot.setDomainMinorGridlinesVisible(true);
				subPlot.setDomainMinorGridlineStroke(new BasicStroke(0.15f));
				subPlot.setDomainMinorGridlinePaint(Color.LIGHT_GRAY);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return subPlot;
	}

	public XYPlot getISOgraph(XYPlot subChartHeadISO, Map<Double, List<List<Point>>> yForISOeff, int methodCallTimes,
			Map<Double, Boolean> checkJoin) {

		try {
			Font font = new Font("Arial", Font.PLAIN, 10);
			XYTextAnnotation annotation = null;
			List<XYTextAnnotation> annotationList = new ArrayList<XYTextAnnotation>();
			XYSeriesCollection x_y_series_collectionLine = new XYSeriesCollection();

			int i = 0;
			for (Iterator<Double> it = yForISOeff.keySet().iterator(); it.hasNext();) {
				Double eff = it.next();
				Boolean joinCheck1 = checkJoin.get(eff);
				String effValue = String.valueOf(Math.round((eff * 100) * 1000) / 1000);
				// System.out.println("effvalue:"+effValue);
				i++;
				List<List<Point>> points = yForISOeff.get(eff);
				XYSeries x_y_series_lineLeft = new XYSeries(i + "L" + methodCallTimes, false);
				XYSeries x_y_series_lineRight = new XYSeries(i + "R" + methodCallTimes, false);
				for (int z = 0; z < points.get(0).size(); z++) {
					// System.out.println("Initial :
					// "+eff+","+points.get(0).get(z).getPointCoordinates()[0]+","+points.get(0).get(z).getPointCoordinates()[1]);
				}
				for (int z = 0; z < points.get(1).size(); z++) {
					// System.out.println("Initial :
					// "+eff+","+points.get(1).get(z).getPointCoordinates()[0]+","+points.get(1).get(z).getPointCoordinates()[1]);
				}

				/*
				 * Collections.sort(points.get(0), new PointCompare(false));
				 * Collections.sort(points.get(1), new PointCompare(true));
				 */

				for (int j2 = 0; j2 < points.get(0).size(); j2++)
					x_y_series_lineLeft.add(points.get(0).get(j2).getPointCoordinates()[0],
							points.get(0).get(j2).getPointCoordinates()[1]);

				for (int j2 = points.get(1).size() - 1; j2 >= 0; j2--)
					x_y_series_lineRight.add(points.get(1).get(j2).getPointCoordinates()[0],
							points.get(1).get(j2).getPointCoordinates()[1]);

				boolean rightCurveAvl = false;
				if (x_y_series_lineRight.getItemCount() > 0)
					rightCurveAvl = true;

				Double joinCheck = 0.0;
				if (rightCurveAvl)
					joinCheck = (x_y_series_lineRight.getX(0).doubleValue()
							- x_y_series_lineLeft.getX(x_y_series_lineLeft.getItemCount() - 1).doubleValue())
							/ subChartHeadISO.getDomainAxis().getUpperBound()
							- subChartHeadISO.getDomainAxis().getLowerBound();

				// if (rightCurveAvl && !joinCheck.isNaN() && joinCheck < 0.15)
				// {
				if (joinCheck1) {
					for (int j2 = 0; j2 < x_y_series_lineRight.getItemCount(); j2++)
						x_y_series_lineLeft.add(x_y_series_lineRight.getDataItem(j2));
					if (x_y_series_lineLeft.getItemCount() >= 3)
						x_y_series_lineLeft = deCasteljaus(x_y_series_lineLeft);
					x_y_series_collectionLine.addSeries(x_y_series_lineLeft);
					if (x_y_series_lineLeft.getItemCount() > 0) {
						annotation = new XYTextAnnotation(effValue + "%",
								x_y_series_lineLeft.getDataItem(0).getXValue(),
								x_y_series_lineLeft.getDataItem(0).getYValue()
										+ x_y_series_collectionLine.getRangeUpperBound(true) * 0.1);
						annotation.setFont(font);
						annotation.setRotationAngle(-45);
						annotation.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
						annotationList.add(annotation);
						annotation = new XYTextAnnotation(effValue + "%",
								x_y_series_lineLeft.getDataItem(x_y_series_lineLeft.getItemCount() - 1).getXValue(),
								x_y_series_lineLeft.getDataItem(x_y_series_lineLeft.getItemCount() - 1).getYValue()
										+ x_y_series_collectionLine.getRangeUpperBound(true) * 0.1);
						annotation.setFont(font);
						annotation.setRotationAngle(-45);
						annotation.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
						annotationList.add(annotation);
					}
				} else {
					if (x_y_series_lineLeft.getItemCount() >= 3)
						x_y_series_lineLeft = deCasteljaus(x_y_series_lineLeft);
					if (x_y_series_lineRight.getItemCount() >= 3)
						x_y_series_lineRight = deCasteljaus(x_y_series_lineRight);
					x_y_series_collectionLine.addSeries(x_y_series_lineLeft);
					x_y_series_collectionLine.addSeries(x_y_series_lineRight);
					if (x_y_series_lineLeft.getItemCount() > 0) {
						annotation = new XYTextAnnotation(effValue + "%",
								x_y_series_lineLeft.getDataItem(0).getXValue(),
								x_y_series_lineLeft.getDataItem(0).getYValue()
										+ x_y_series_collectionLine.getRangeUpperBound(true) * 0.1);
						annotation.setFont(font);
						annotation.setRotationAngle(-45);
						annotation.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
						annotationList.add(annotation);
					}
					if (x_y_series_lineRight.getItemCount() > 0) {
						annotation = new XYTextAnnotation(effValue + "%",
								x_y_series_lineRight.getDataItem(x_y_series_lineRight.getItemCount() - 1).getXValue(),
								x_y_series_lineRight.getDataItem(x_y_series_lineRight.getItemCount() - 1).getYValue()
										+ x_y_series_collectionLine.getRangeUpperBound(true) * 0.1);
						annotation.setFont(font);
						annotation.setRotationAngle(-45);
						annotation.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
						annotationList.add(annotation);
					}
				}

			}

			/*
			 * XYDotRenderer renderer_Line = new XYDotRenderer();
			 * renderer_Line.setDotHeight(2); renderer_Line.setDotWidth(2);
			 */
			XYSmoothLineAndShapeRenderer renderer_Line = new XYSmoothLineAndShapeRenderer();
			// renderer_Line.setPrecision(500);
			renderer_Line.setDrawSeriesLineAsPath(true);
			for (int s = 0; s < x_y_series_collectionLine.getSeriesCount(); s++) {
				renderer_Line.setSeriesLinesVisible(s, true);
				renderer_Line.setSeriesShapesVisible(s, false);
				renderer_Line.setSeriesPaint(s, Color.RED);

			}

			subChartHeadISO.setDataset(3, x_y_series_collectionLine);
			subChartHeadISO.setRenderer(3, renderer_Line);

			for (int j = 0; j < annotationList.size(); j++)
				subChartHeadISO.addAnnotation(annotationList.get(j));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subChartHeadISO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String saveChartAsImage(String fileName, JFreeChart chart, String typeOfChart, Object datasetObj1,
			Object datasetObj2, int chartWidth, int chartHeight) {

		Color[] colors = new Color[15];
		// Wilo color specifications
		colors[0] = new Color(0, 156, 130);// wiloGreen
		colors[1] = new Color(80, 80, 80);// gunMetal
		colors[2] = new Color(255, 180, 0);// technicYellow
		colors[3] = new Color(120, 120, 120);// coolGrey or gunMetal 50%
		colors[4] = new Color(77, 186, 168);// wiloGreen 70%
		colors[5] = new Color(255, 191, 76);// technicYellow 70%
		colors[6] = new Color(166, 166, 166);// gunMetal 50%
		colors[7] = new Color(128, 206, 193);// wiloGreen 50%
		colors[8] = new Color(255, 209, 128);// technicYellow 50%
		colors[9] = new Color(179, 225, 218);// wiloGreen 30%
		colors[10] = new Color(204, 204, 204);// gunMetal 30%
		colors[11] = new Color(0, 90, 205);// Water Blue
		colors[12] = new Color(170, 200, 0);// natural Green
		colors[13] = new Color(245, 65, 0);// vitalRed
		colors[14] = new Color(255, 255, 255);// White

		try {
			if (typeOfChart.equals("PIE")) {
				PiePlot pieplot = (PiePlot) chart.getPlot();
				DefaultPieDataset dataset = (DefaultPieDataset) datasetObj1;
				List<Comparable> keys = dataset.getKeys();
				for (int i = 0; i < keys.size(); i++) {
					int aInt = i % colors.length;
					pieplot.setSectionPaint(keys.get(i), colors[aInt]);
				}
				pieplot.setLabelFont(new Font("SansSerif", Font.PLAIN, 11));
				pieplot.setLabelBackgroundPaint(colors[14]);
				pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}"));
				pieplot.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
				pieplot.setBackgroundPaint(new Color(255, 255, 255));
				pieplot.setNoDataMessage("No Data Available");
				pieplot.setSimpleLabels(false);
				pieplot.setCircular(true);
				pieplot.setOutlineStroke(null);

			} else if (typeOfChart.equals("STACKEDBAR")) {
				CategoryPlot catplot = (CategoryPlot) chart.getPlot();
				DefaultCategoryDataset dataset = (DefaultCategoryDataset) datasetObj1;

				catplot.setNoDataMessage("No Data Available");
				NumberAxis numberAxis = (NumberAxis) catplot.getRangeAxis();
				numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				StackedBarRenderer renderer = (StackedBarRenderer) catplot.getRenderer();

				List<Comparable> keys = dataset.getColumnKeys();
				for (int i = 0; i < keys.size(); i++) {
					int aInt = i % colors.length;
					renderer.setSeriesPaint(i, colors[aInt]);
				}
				renderer.setMaximumBarWidth(.06);
				renderer.setBarPainter(new StandardBarPainter());
				renderer.setDrawBarOutline(true);
				renderer.setDefaultItemLabelsVisible(true);
				renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				catplot.setOutlineStroke(null);
				catplot.setBackgroundPaint(new Color(255, 255, 255));
				CategoryAxis domainAxis = catplot.getDomainAxis();
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
				renderer.setItemMargin(0.01);

			} else if (typeOfChart.equals("XYPlot")) {
				XYPlot plot = (XYPlot) chart.getPlot();
				plot.setNoDataMessage("No Data Available");
				plot.setBackgroundPaint(new Color(255, 255, 255));
				plot.setDomainPannable(true);
				plot.setRangePannable(true);
				plot.setDomainZeroBaselineVisible(true);
				plot.setRangeZeroBaselineVisible(true);

				plot.setDomainGridlineStroke(new BasicStroke(0.0f));
				plot.setDomainMinorGridlineStroke(new BasicStroke(0.0f));
				plot.setDomainGridlinePaint(Color.blue);
				plot.setRangeGridlineStroke(new BasicStroke(0.0f));
				plot.setRangeMinorGridlineStroke(new BasicStroke(0.0f));
				plot.setRangeGridlinePaint(Color.blue);
				// plot.setOutlineStroke(null);
				plot.setDomainMinorGridlinesVisible(true);
				plot.setRangeMinorGridlinesVisible(true);

				XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
				renderer.setSeriesOutlinePaint(0, Color.black);
				renderer.setUseOutlinePaint(true);
				NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();

				domainAxis.setAutoRangeIncludesZero(false);
				domainAxis.setTickMarkInsideLength(2.0f);
				domainAxis.setTickMarkOutsideLength(2.0f);
				domainAxis.setMinorTickCount(2);
				domainAxis.setMinorTickMarksVisible(false);

				NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

				rangeAxis.setTickMarkInsideLength(2.0f);
				rangeAxis.setTickMarkOutsideLength(2.0f);
				rangeAxis.setMinorTickCount(2);
				rangeAxis.setMinorTickMarksVisible(false);

			} else if (typeOfChart.equals("BAR")) {
				CategoryPlot catplot = (CategoryPlot) chart.getPlot();
				catplot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
				DefaultCategoryDataset dataset = (DefaultCategoryDataset) datasetObj1;

				catplot.setNoDataMessage("No Data Available");
				NumberAxis numberAxis = (NumberAxis) catplot.getRangeAxis();
				numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				BarRenderer renderer = (BarRenderer) catplot.getRenderer();

				List<Comparable> keys = dataset.getColumnKeys();
				for (int i = 0; i < keys.size(); i++) {
					int aInt = i % colors.length;
					renderer.setSeriesPaint(i, colors[aInt]);
				}

				renderer.setMaximumBarWidth(.06);
				renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setDefaultItemLabelFont(new Font("SansSerif", Font.PLAIN, 8));
				renderer.setBarPainter(new StandardBarPainter());
				renderer.setDrawBarOutline(true);
				renderer.setDefaultItemLabelsVisible(true);
				renderer.setShadowVisible(false);
				renderer.setItemMargin(0.01);
				// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
				catplot.setOutlineStroke(null);
				catplot.setBackgroundPaint(new Color(255, 255, 255));

			} else if (typeOfChart.equals("DUAL_BAR")) {
				CategoryPlot plot = (CategoryPlot) chart.getPlot();
				plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
				plot.setNoDataMessage("No Data Available");
				CategoryDataset dataofferOrder = (CategoryDataset) datasetObj1;
				ValueAxis axis2 = new NumberAxis("Total Offers and Orders");
				axis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				plot.setRangeAxis(2, axis2);
				plot.setDataset(1, dataofferOrder);
				plot.mapDatasetToRangeAxis(1, 2);
				plot.setRangeAxisLocation(2, AxisLocation.BOTTOM_OR_LEFT);

				BarRenderer renderer = (BarRenderer) plot.getRenderer();
				LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
				renderer2.setDefaultToolTipGenerator(new StandardCategoryToolTipGenerator());
				renderer2.setDefaultLinesVisible(false);
				plot.setRenderer(1, renderer2);
				plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
				List<Comparable> keys = dataofferOrder.getColumnKeys();
				for (int i = 0; i < keys.size(); i++) {
					int aInt = i % colors.length;
					renderer.setSeriesPaint(i, colors[aInt]);
					renderer2.setSeriesPaint(i, colors[aInt]);
				}
				renderer.setMaximumBarWidth(.03);
				renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setDefaultItemLabelFont(new Font("SansSerif", Font.PLAIN, 8));
				renderer2.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer2.setDefaultItemLabelFont(new Font("SansSerif", Font.PLAIN, 8));
				renderer.setBarPainter(new StandardBarPainter());
				renderer.setDrawBarOutline(true);
				renderer.setDefaultItemLabelsVisible(true);
				renderer.setShadowVisible(false);
				renderer.setItemMargin(0.01);
				plot.setOutlineStroke(null);
				plot.setBackgroundPaint(new Color(255, 255, 255));

			}
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());

			File file1 = new File(velotechUtil.getUserContextPath(), fileName);
			if (!file1.exists())
				file1.createNewFile();
			ChartUtils.saveChartAsPNG(file1, chart, chartWidth, chartHeight, info);
		} catch (Exception e) {
			fileName = "false";
			e.printStackTrace();
		}
		return fileName;
	}

	public List<Point> deCasteljaus(List<Point> points) {

		List<Point> finalT = new ArrayList<Point>();
		for (double t = 0; t <= 1; t += 0.1) {
			List<Point> tempList = points;
			List<Point> tempT = new ArrayList<Point>();
			do {
				for (int i = 0; i < tempList.size() - 1; i++) {
					tempT.add(getTintermediate(tempList.get(i), tempList.get(i + 1), t));
					// System.out.println(tempT[i]);
				}
				tempList = tempT;
				tempT = new ArrayList<Point>();
			} while (tempList.size() >= 2);
			finalT.add(tempList.get(0));
			// System.out.println("ccccccccccccccccccc-" +
			// tempList.get(0).getPointCoordinates()[0] + "," +
			// tempList.get(0).getPointCoordinates()[1]);
		}
		return finalT;
	}

	public Point getTintermediate(Point p1, Point p2, double t) {

		double x = (1 - t) * p1.getPointCoordinates()[0] + t * p2.getPointCoordinates()[0];
		double y = (1 - t) * p1.getPointCoordinates()[1] + t * p2.getPointCoordinates()[1];
		return new Point(x, y);
	}

	public XYSeries deCasteljaus(XYSeries xySeries) {

		XYSeries xySeriesTemp = new XYSeries(xySeries.getKey(), false);
		List<Point> finalT = new ArrayList<Point>();
		List<Point> points = new ArrayList<Point>();
		for (int j2 = 0; j2 < xySeries.getItemCount(); j2++)
			points.add(new Point(xySeries.getDataItem(j2).getXValue(), xySeries.getDataItem(j2).getYValue()));
		for (double t = 0; t <= 1; t += 0.1) {
			List<Point> tempList = points;
			List<Point> tempT = new ArrayList<Point>();
			do {
				for (int i = 0; i < tempList.size() - 1; i++) {
					tempT.add(getTintermediate(tempList.get(i), tempList.get(i + 1), t));
					// System.out.println(tempT[i]);
				}
				tempList = tempT;
				tempT = new ArrayList<Point>();
			} while (tempList.size() >= 2);
			xySeriesTemp.add(tempList.get(0).getPointCoordinates()[0], tempList.get(0).getPointCoordinates()[1]);
			// System.out.println("pppppppppppppppppppppp-" +
			// tempList.get(0).getPointCoordinates()[0] + "," +
			// tempList.get(0).getPointCoordinates()[1]);
		}
		return xySeriesTemp;
	}

	public List<List<Double>> trimCurveCalculation(double minFlow_DiaStd, double maxFlow_DiaStd, double minFlow_DiaMin,
			double maxFlow_DiaMin, String Degree, double[] terms_Std, double[] corrected_terms_Std, double[] terms_min,
			double[] corrected_terms_min, String imp_DiaStd, String imp_DiaMin, double trimDia) {

		double minFlow_DiaTrim = y_TRIM(minFlow_DiaStd, minFlow_DiaMin, Double.parseDouble(imp_DiaStd),
				Double.parseDouble(imp_DiaMin), trimDia);
		double maxFlow_DiaTrim = y_TRIM(maxFlow_DiaStd, maxFlow_DiaMin, Double.parseDouble(imp_DiaStd),
				Double.parseDouble(imp_DiaMin), trimDia);

		List<List<Double>> ans = new ArrayList<List<Double>>();
		double H_DiaSTd_trim = 0;
		double H_DiaMin_trim = 0;
		List<Double> XtrimList = new ArrayList<Double>();
		List<Double> YtrimList = new ArrayList<Double>();
		double step = ((maxFlow_DiaTrim - minFlow_DiaTrim) / (25 * Double.parseDouble(Degree) + 1));

		PolySolve polySolve = new PolySolve();

		if (step != 0d) {
			for (double Xtrim = minFlow_DiaTrim; Xtrim <= maxFlow_DiaTrim + 0.1; Xtrim += step) {
				if (Xtrim < minFlow_DiaStd || Xtrim > maxFlow_DiaStd)
					H_DiaSTd_trim = polySolve.plotFunct(Xtrim, corrected_terms_Std);
				else
					H_DiaSTd_trim = polySolve.plotFunct(Xtrim, terms_Std);

				if (Xtrim < minFlow_DiaMin || Xtrim > maxFlow_DiaMin)
					H_DiaMin_trim = polySolve.plotFunct(Xtrim, corrected_terms_min);
				else
					H_DiaMin_trim = polySolve.plotFunct(Xtrim, terms_min);

				XtrimList.add(Xtrim);
				YtrimList.add(y_TRIM(H_DiaSTd_trim, H_DiaMin_trim, Double.parseDouble(imp_DiaStd),
						Double.parseDouble(imp_DiaMin), trimDia));
			}
		}
		ans.add(XtrimList);
		ans.add(YtrimList);
		return ans;
	}

	public String getXy(List<Double> X, List<Double> Y) {

		String ans = "";

		for (int i = 0; i < X.size(); i++) {
			if (Y.get(i) != null) {
				ans += X.get(i) + " ";
				ans += Y.get(i) + " ";
			}
		}
		return ans;
	}

	/**
	 * Calculates the BEP for the given curve Logic: reduce the efficiency by 0.1
	 * starting from 0.9 and check the efficiency where roots are obtained then take
	 * mid point approach to derive at max efficiency
	 * 
	 * @param xy     = list of xy points
	 * @param degree = degree of line. if 0 then it will consider 3 as default
	 * @return List<Double> bep where bep(0) is flow@BEP and bep(1) is
	 *         Efficiency@BEP
	 * @date: 10th Nov 13
	 * @author: Sujit
	 */
	public List<Double> getBEP(List<List<Double>> xy, int degree) {

		List<Double> bep = new ArrayList<Double>();
		PolySolve p = new PolySolve();

		double[] terms = p.process(getXy(xy.get(0), xy.get(1)), degree == 0 ? 3 : degree);
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
			e.printStackTrace();
		}

		return bep;
	}

	/**
	 * Calculates the BEP for the given curve Logic: reduce the efficiency by 0.1
	 * starting from 0.9 and check the efficiency where roots are obtained then take
	 * mid point approach to derive at max efficiency
	 * 
	 * @param xy     = list of xy points
	 * @param degree = degree of line. if 0 then it will consider 3 as default
	 * @return List<Double> bep where bep(0) is flow@BEP and bep(1) is
	 *         Efficiency@BEP
	 * @date: 10th Nov 13
	 * @author: Sujit
	 */
	public List<Double> getBEP(double[] terms, Double xMin, Double xMax) {

		List<Double> bep = new ArrayList<Double>();
		double[] tempTerms = new double[terms.length];

		System.arraycopy(terms, 0, tempTerms, 0, terms.length);

		List<Double> Xroots = new ArrayList<Double>();
		try {
			double lowerEff = 0.9;
			double higherEff = 1.0;
			double tempEff = 0.0;
			double BEP = 0.0;
			double root = 0.0;

			Xroots = findRoots(tempTerms, lowerEff, xMin, xMax);

			while (Xroots.isEmpty() && lowerEff > 0.0) {
				higherEff = lowerEff;
				lowerEff = lowerEff - 0.1;
				tempTerms = new double[terms.length];
				System.arraycopy(terms, 0, tempTerms, 0, terms.length);
				Xroots = findRoots(tempTerms, lowerEff, xMin, xMax);
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
				Xroots = findRoots(tempTerms, tempEff, xMin, xMax);

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
		} catch (Exception e) {
			bep.add(0d);
			bep.add(0d);
			e.printStackTrace();
		}

		return bep;
	}

	public List<Double> findRoots(double[] terms, double eff) {

		List<Double> Xlist = new ArrayList<Double>();
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

		return Xlist;
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

	public double y_TRIM(double y_upper, double y_lower, double upperAnnotation, double lowerAnnotation,
			double trimAnnotation) {

		if (upperAnnotation == lowerAnnotation)
			return y_upper;
		else
			return y_upper
					- ((upperAnnotation - trimAnnotation) * (y_upper - y_lower) / (upperAnnotation - lowerAnnotation));
	}

	public List<List<Double>> getInterPolationEqn(double[] terms, Double xMin, Double xMax) {

		List<List<Double>> ans = new ArrayList<List<Double>>();
		List<Double> xList = new ArrayList<Double>();
		List<Double> yList = new ArrayList<Double>();
		PolySolve polySolve = new PolySolve();
		double step = Math.abs((xMax - xMin) / 10);// (25 * degree + 1);
		if (step != 0)
			for (double i = Math.min(xMin, xMax); i <= Math.max(xMin, xMax) + .1; i += step) {
				xList.add(i);
				yList.add(polySolve.plotFunct(i, terms));
			}
		ans.add(xList);
		ans.add(yList);
		return ans;
	}

	public String getOverallEqn(double[] motorEffEq, double[] pumpEffEq, Double xMin, Double xMax) {

		String ans = "";
		try {

			List<Double> xList = new ArrayList<Double>();
			List<Double> yList = new ArrayList<Double>();
			PolySolve polySolve = new PolySolve();
			double step = Math.abs((xMax - xMin) / 10);// (25 * degree + 1);
			if (step != 0)
				for (double i = Math.min(xMin, xMax); i <= Math.max(xMin, xMax) + .1; i += step) {
					xList.add(i);
					double motorEff = polySolve.plotFunct(i, motorEffEq);
					double pumpEff = polySolve.plotFunct(i, pumpEffEq);
					yList.add(motorEff * pumpEff);
				}

			double[] eqns = polySolve.process(getXy(xList, yList), 2);
			ans = Arrays.toString(eqns);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	public XYPlot addHeadAnnotation(XYPlot plotHead, List<GraphModel> graphModelList) {

		try {
			Font font = new Font("SansSerif", Font.PLAIN, 10);
			for (int i = 0; i < graphModelList.size(); i++) {

				double xVal = 0, yVal = 0;

				xVal = plotHead.getDataset().getXValue(i, 0);

				yVal = plotHead.getDataset().getYValue(i, 0);
				XYTextAnnotation annotation = new XYTextAnnotation(graphModelList.get(i).getAnnotation(),
						xVal + plotHead.getDataset().getXValue(0, 0) * 10,
						yVal + plotHead.getDataset().getYValue(0, 0) * 0.4);

				if (annotation != null) {
					annotation.setFont(font);
					annotation.setBackgroundPaint(Color.WHITE);
					annotation.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
				}

				plotHead.addAnnotation(annotation);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return plotHead;
	}

}

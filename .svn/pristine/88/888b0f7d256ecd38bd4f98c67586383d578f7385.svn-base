package com.velotech.fanselection.utils;

import java.awt.Graphics2D;

import java.awt.geom.Rectangle2D;

import org.jfree.chart.JFreeChart;

import net.sf.jasperreports.engine.JRAbstractSvgRenderer;
import net.sf.jasperreports.renderers.Graphics2DRenderable;

public class JFreeChartSvgRenderer extends JRAbstractSvgRenderer implements Graphics2DRenderable{
    private final JFreeChart chart;

    public JFreeChartSvgRenderer(JFreeChart chart) {
        this.chart = chart;
    }

    @Override
	public void render(Graphics2D g2d, Rectangle2D rectangle) {
        chart.draw(g2d, rectangle);
    }

	
}

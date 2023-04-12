package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;

public class HistogramPanel extends JPanel {
  private static final int BINS = 256;
  private final BufferedImage image;
  private HistogramDataset dataset;


  public HistogramPanel(BufferedImage image) {
    this.image = image;
  }

  ChartPanel createChartPanel() {
    // dataset
    dataset = new HistogramDataset();
    Raster raster = image.getRaster();
    final int w = image.getWidth();
    final int h = image.getHeight();
    double[] r = new double[w * h];
    r = raster.getSamples(0, 0, w, h, 0, r);
    dataset.addSeries("Red", r, BINS);
    r = raster.getSamples(0, 0, w, h, 1, r);
    dataset.addSeries("Green", r, BINS);
    r = raster.getSamples(0, 0, w, h, 2, r);
    dataset.addSeries("Blue", r, BINS);
    // chart
    JFreeChart chart = ChartFactory.createXYLineChart("Histogram", "Value",
            "Count", dataset, PlotOrientation.VERTICAL, true, true, false);
    XYPlot plot = (XYPlot) chart.getPlot();
    Paint[] paintArray = {
            new Color(0x80ff0000, true),
            new Color(0x8000ff00, true),
            new Color(0x800000ff, true)
    };
    plot.setDrawingSupplier(new DefaultDrawingSupplier(
            paintArray,
            DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE,
            DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
            DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
            DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
            DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
    ChartPanel panel = new ChartPanel(chart);
    panel.setMouseWheelEnabled(true);
    panel.setDomainZoomable(false);
    panel.setRangeZoomable(false);
    return panel;
  }
}

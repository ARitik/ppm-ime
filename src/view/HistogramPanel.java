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
    HistogramDataset dataset = new HistogramDataset();
    Raster raster = image.getRaster();
    final int w = image.getWidth();
    final int h = image.getHeight();
    double[] r = new double[w * h];
    double[] g = new double[w * h];
    double[] b = new double[w * h];
    r = raster.getSamples(0, 0, w, h, 0, r);
    g = raster.getSamples(0, 0, w, h, 1, g);
    b = raster.getSamples(0, 0, w, h, 2, b);
    double[] i = new double[w * h];
    for (int j = 0; j < i.length; j++) {
      i[j] = (r[j] * 0.2989) + (g[j] * 0.5870) + (b[j] * 0.1140);
    }
    dataset.addSeries("Red", r, BINS);
    dataset.addSeries("Green", g, BINS);
    dataset.addSeries("Blue", b, BINS);
    dataset.addSeries("Intensity", i, BINS);

    // chart
    JFreeChart chart = ChartFactory.createXYLineChart("Histogram", "Value", "Count", dataset,
            PlotOrientation.VERTICAL, true, true, false);
    XYPlot plot = (XYPlot) chart.getPlot();
    Paint[] paintArray = {
            new Color(0x80ff0000, true),
            new Color(0x8000ff00, true),
            new Color(0x800000ff, true),
            Color.yellow
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

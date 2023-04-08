package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JPanel;

public class HistogramPanel extends JPanel {

  private BufferedImage image;
  private int[] redHistogram;
  private int[] greenHistogram;
  private int[] blueHistogram;
  private int[] intensityHistogram;

  public HistogramPanel(BufferedImage image) {
    this.image = image;
    this.redHistogram = new int[256];
    this.greenHistogram = new int[256];
    this.blueHistogram = new int[256];
    this.intensityHistogram = new int[256];
    computeHistograms();
  }

  private void computeHistograms() {
    int width = image.getWidth();
    int height = image.getHeight();

    // Compute histograms for red, green, blue, and intensity components
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = image.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        int intensity = (red + green + blue) / 3;
        redHistogram[red]++;
        greenHistogram[green]++;
        blueHistogram[blue]++;
        intensityHistogram[intensity]++;
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g2) {
    super.paintComponent(g2);


    Graphics2D g = (Graphics2D) g2;

    int width = getWidth();
    int height = getHeight();
    int margin = 20;
    int histogramWidth = width - 2 * margin;
    int histogramHeight = height - 2 * margin;
    int chartWidth = width - 2 * margin;
    int chartHeight = height - 2 * margin;

    // Draw background
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);
    // Draw chart title
    g.setColor(Color.BLACK);
    g.setFont(new Font("Arial", Font.BOLD, 18));
    String title = "Image Histogram";
    int titleWidth = g.getFontMetrics().stringWidth(title);
    g.drawString(title, (width - titleWidth) / 2, margin / 2);

    // Draw x axis label
    String xAxisLabel = "Value";
    int xAxisLabelWidth = g.getFontMetrics().stringWidth(xAxisLabel);
    g.drawString(xAxisLabel, margin + (chartWidth - xAxisLabelWidth) / 2, height - margin / 4);

    // Draw y axis label
    String yAxisLabel = "Frequency";
    int yAxisLabelWidth = g.getFontMetrics().stringWidth(yAxisLabel);
    g.rotate(-Math.PI / 2);
    g.drawString(yAxisLabel, -margin - (chartHeight - yAxisLabelWidth) / 2, margin / 2);
    g.rotate(Math.PI / 2);

    // Draw grid lines
    g.setColor(Color.LIGHT_GRAY);
    int numLines = 5;
    for (int i = 0; i < numLines; i++) {
      int x1 = margin;
      int y1 = margin + i * chartHeight / numLines;
      int x2 = margin + chartWidth;
      int y2 = margin + i * chartHeight / numLines;
      g.drawLine(x1, y1, x2, y2);
      int x3 = margin + i * chartWidth / numLines;
      int y3 = margin;
      int x4 = margin + i * chartWidth / numLines;
      int y4 = margin + chartHeight;
      g.drawLine(x3, y3, x4, y4);
    }

    // Draw red histogram
    g.setColor(Color.RED);
    drawHistogram(g, margin, margin, histogramWidth, histogramHeight, redHistogram);

    // Draw green histogram
    g.setColor(Color.GREEN);
    drawHistogram(g, margin, margin, histogramWidth, histogramHeight, greenHistogram);

    // Draw blue histogram
    g.setColor(Color.BLUE);
    drawHistogram(g, margin, margin, histogramWidth, histogramHeight, blueHistogram);

    // Draw intensity histogram
    Graphics2D g2d = (Graphics2D) g;
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
    g.setColor(Color.BLACK);
    drawHistogram(g, margin, margin, histogramWidth, histogramHeight, intensityHistogram);
  }

  private void drawHistogram(Graphics g, int x, int y, int width, int height, int[] histogram) {
    int maxValue = Arrays.stream(histogram).max().getAsInt();
    int barWidth = width / histogram.length;

    for (int i = 0; i < histogram.length; i++) {
      int barHeight = (int) (((double) histogram[i]) / maxValue * height);
      g.drawLine(x + i * barWidth, y + height, x + i * barWidth, y + height - barHeight);
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(400, 300);
  }

}

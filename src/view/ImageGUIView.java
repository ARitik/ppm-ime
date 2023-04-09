package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.AppController;

public class ImageGUIView extends JFrame implements AppView {
  private BufferedImage image;
  private JPanel imageViewPanel;
  private JPanel imagePanel;
  private JButton fileChooserButton;
  private JButton ditherButton;
  private JButton sharpenButton;
  private JButton blurButton;
  private JButton sepiaButton;
  private JButton horizontalFlipButton;
  private JButton verticalFlipButton;
  private JComboBox<String> greyScaleDropdown;
  private JSlider brightnessSlider;
  private JPanel logPanel;
  private JPanel histogramPanel;
  private HistogramPanel histogram;

  public ImageGUIView(PrintStream out) {
    super("GRIME");
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    //ImagePanel
    imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image Panel"));
    imagePanel.setLayout(new BorderLayout());
    this.add(imagePanel, BorderLayout.CENTER);

    //ImageViewPanel
    imageViewPanel = new JPanel();
    imageViewPanel.setLayout(new BorderLayout());
    imagePanel.add(imageViewPanel, BorderLayout.CENTER);

    //ImageLogPane
    logPanel = new JPanel();
    logPanel.setBorder(BorderFactory.createTitledBorder("Operations Log"));
    logPanel.setPreferredSize(new Dimension(300, 400));
    logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.PAGE_AXIS));
//    logPanel.setEditable(false);
    JScrollPane logScrollPane = new JScrollPane();
    logScrollPane.setViewportView(logPanel);

    //ImageLogPane
    histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setPreferredSize(new Dimension(300, 400));


    //ImageInfoPanel
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, histogramPanel,
            logScrollPane);
    splitPane.setResizeWeight(0.5);
    splitPane.setDividerLocation(0.7);
    imagePanel.add(splitPane, BorderLayout.SOUTH);


    JPanel toolBench = new JPanel();
    toolBench.setBorder(BorderFactory.createTitledBorder("Tool Bench"));
//    toolBench.setLayout(new BoxLayout(toolBench, BoxLayout.PAGE_AXIS));
    toolBench.setPreferredSize(new Dimension(400, 0));
    this.add(toolBench, BorderLayout.LINE_END);

    //Add buttons to toolbench

    // File chooser button
    fileChooserButton = new JButton("Load Image");
    toolBench.add(fileChooserButton);
    JLabel brightnessLabel = new JLabel("Brighten");
    toolBench.add(brightnessLabel);
    brightnessSlider = new JSlider(-50, 50, 0);
    brightnessSlider.setMinorTickSpacing(10);
    brightnessSlider.setMajorTickSpacing(50);
    brightnessSlider.setPaintTicks(true);
    brightnessSlider.setPaintLabels(true);
    toolBench.add(brightnessSlider);

    ditherButton = new JButton("Dither");
    toolBench.add(ditherButton);

    blurButton = new JButton("Blur");
    toolBench.add(blurButton);

    sharpenButton = new JButton("Sharpen");
    toolBench.add(sharpenButton);

    sepiaButton = new JButton("Sepia");
    toolBench.add(sepiaButton);

    JLabel label = new JLabel("Select Greyscale Component:");
    toolBench.add(label);
    String[] options = {"Luma", "Intensity", "Value", "Red", "Green", "Blue"};
    greyScaleDropdown = new JComboBox<>(options);
    toolBench.add(greyScaleDropdown);

    horizontalFlipButton = new JButton("Horizontal Flip");
    toolBench.add(horizontalFlipButton);

    verticalFlipButton = new JButton("Vertical Flip");
    toolBench.add(verticalFlipButton);


    this.setVisible(true);
    this.pack();
  }

  @Override
  public void log(String operation, String message, boolean isPass) {
    if (isPass) {
      logPanel.add(new JLabel("Log: " + operation + " completed successfully!\n")
      );
    } else {
      logPanel.add(new JLabel("Log: " + operation + " failed!\n" + message + "\n"));
    }
    imageViewPanel.removeAll();
    imageViewPanel.revalidate();
    imageViewPanel.requestFocus();
  }

  @Override
  public void log(String operation, boolean isPass) {
    if (isPass) {
      logPanel.add(new JLabel("Log: " + operation + " completed successfully!\n")
      );
    } else {
      logPanel.add(new JLabel("Log: " + operation + " failed!\n"));
    }

    imageViewPanel.revalidate();
    logPanel.revalidate();
    imageViewPanel.requestFocus();
  }

  @Override
  public void addFeatures(AppController features) {
    fileChooserButton.addActionListener(evt -> {
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showOpenDialog(ImageGUIView.this);
      if (result == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        try {
          features.processCommands("load " + file.toString() + " opFile");
        } catch (Exception ex) {
          log("load",ex.getMessage(),false);
        }
      }
    });
    brightnessSlider.addChangeListener(evt -> {
      int brightnessConstant = brightnessSlider.getValue();
      try {
        features.processCommands("brighten " + brightnessConstant + " opFile opFile");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    ditherButton.addActionListener(evt -> {
      try {
        features.processCommands("dither opFile opFile");
      } catch (IOException e) {
        log("dither",e.getMessage(),false);
      }
    });
    horizontalFlipButton.addActionListener(evt -> {
      try {
        features.processCommands("horizontal-flip opFile opFile");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    verticalFlipButton.addActionListener(evt -> {
      try {
        features.processCommands("vertical-flip opFile opFile");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    sepiaButton.addActionListener(evt -> {
      try {
        features.processCommands("sepia opFile opFile");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    sharpenButton.addActionListener(evt -> {
      try {
        features.processCommands("sharpen opFile opFile");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    blurButton.addActionListener(evt -> {
      try {
        features.processCommands("blur opFile opFile");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    greyScaleDropdown.addActionListener(evt -> {
      String selectedOption =
              Objects.requireNonNull(greyScaleDropdown.getSelectedItem()).toString().toLowerCase();
      try {
        features.processCommands("greyscale " + selectedOption + "-component opFile opFile");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  public void setImage(BufferedImage image) {
    this.image = image;
    imageViewPanel.removeAll();
    histogramPanel.removeAll();
    histogram = new HistogramPanel(image);
    histogramPanel.add(histogram.createChartPanel());
    histogramPanel.revalidate();
    JScrollPane imageScrollPane = new JScrollPane();
    imageScrollPane.setViewportView(new JLabel(new ImageIcon(image)));
    imageViewPanel.add(imageScrollPane);
    imageViewPanel.revalidate();
    imageViewPanel.requestFocus();
  }


}

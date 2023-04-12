package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;
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
  private JButton saveFileButton;
  private JComboBox<String> greyScaleDropdown;
  private JSlider brightnessSlider;
  private JButton exitButton;
  private JPanel logPanel;
  private JPanel histogramPanel;
  private JButton splitButton;
  private JButton combineButton;
  private HistogramPanel histogram;
  private JPanel mainPanel;
  private JPanel verticalLayoutPanel;
  private JPanel operationsGrid;
  private JPanel brightnessGreyscaleGrid;

  public ImageGUIView(PrintStream out) {
    super("GRIME");
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Main Panel
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    this.add(mainScrollPane);


    //ImagePanel
    imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image Panel"));
    imagePanel.setLayout(new BorderLayout());
    mainPanel.add(imagePanel, BorderLayout.CENTER);

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
    JScrollPane logScrollPane = new JScrollPane(logPanel);
    logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    //ImageLogPane
    histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setPreferredSize(new Dimension(600, 450));
    JScrollPane histogramScrollPane = new JScrollPane(histogramPanel);
    histogramScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);



    //ImageInfoPanel
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, histogramScrollPane, logScrollPane);
    splitPane.setResizeWeight(0.5);
    splitPane.setDividerLocation(0.7);
    JScrollPane splitScrollPane = new JScrollPane(splitPane);
    splitScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    imagePanel.add(splitScrollPane, BorderLayout.SOUTH);


    JPanel toolBench = new JPanel();
    toolBench.setBorder(BorderFactory.createTitledBorder("Tool Bench"));
//    toolBench.setLayout(new BoxLayout(toolBench, BoxLayout.PAGE_AXIS));
    toolBench.setPreferredSize(new Dimension(400, 0));
    mainPanel.add(toolBench, BorderLayout.LINE_END);

    JPanel loadSavePanel = new JPanel(new GridLayout(1, 2));
    loadSavePanel.setBorder(BorderFactory.createTitledBorder("Load and Save image"));
    loadSavePanel.setPreferredSize(new Dimension(350, 50));

    //Add buttons to toolBench

    // File chooser button
    fileChooserButton = new JButton("Load Image");
    loadSavePanel.add(fileChooserButton);
    //toolBench.add(fileChooserButton);

    //loadSavePanel.add(Box.createHorizontalStrut(3));


    saveFileButton = new JButton("Save Image");
    //toolBench.add(saveFileButton);
    loadSavePanel.add(saveFileButton);

    // Add the Load Image and Save Image panel to the Tool Bench
    //toolBench.add(loadSavePanel);
    //toolBench.add(Box.createVerticalStrut(60));

    // Add some vertical spacing
    //toolBench.add(Box.createRigidArea(new Dimension(0, 10)));

    verticalLayoutPanel = new JPanel();
    verticalLayoutPanel.setPreferredSize(new Dimension(380, 700));
    verticalLayoutPanel.setBorder(BorderFactory.createTitledBorder("Operations"));
    verticalLayoutPanel.setLayout(new BoxLayout(verticalLayoutPanel, BoxLayout.Y_AXIS));

    operationsGrid = new JPanel();
    operationsGrid.setLayout(new GridLayout(0, 1, 10, 10));


    ditherButton = new JButton("Dither");
    //toolBench.add(ditherButton);
    //ditherButton.setPreferredSize(new Dimension(200, 30));
    operationsGrid.add(ditherButton);
    //verticalLayoutPanel.add(Box.createVerticalStrut(20));

    blurButton = new JButton("Blur");
    //toolBench.add(blurButton);
    operationsGrid.add(blurButton);
    //verticalLayoutPanel.add(Box.createVerticalStrut(20));

    sharpenButton = new JButton("Sharpen");
    //toolBench.add(sharpenButton);
    operationsGrid.add(sharpenButton);
    //verticalLayoutPanel.add(Box.createVerticalStrut(20));

    sepiaButton = new JButton("Sepia");
    //toolBench.add(sepiaButton);
    operationsGrid.add(sepiaButton);
    //verticalLayoutPanel.add(Box.createVerticalStrut(20));


    horizontalFlipButton = new JButton("Horizontal Flip");
    //toolBench.add(horizontalFlipButton);
    operationsGrid.add(horizontalFlipButton);
    //verticalLayoutPanel.add(Box.createVerticalStrut(20));

    verticalFlipButton = new JButton("Vertical Flip");
    //toolBench.add(verticalFlipButton);
    operationsGrid.add(verticalFlipButton);
    //verticalLayoutPanel.add(Box.createVerticalStrut(20));


    splitButton = new JButton("Split");
    operationsGrid.add(splitButton);
    //verticalLayoutPanel.add(Box.createVerticalStrut(20));

    combineButton = new JButton("Combine");
    operationsGrid.add(combineButton);
    //verticalLayoutPanel.add(Box.createVerticalStrut(40));

    exitButton = new JButton("Exit");
    //toolBench.add(exitButton);
    operationsGrid.add(exitButton);

    //verticalLayoutPanel.add(Box.createVerticalStrut(20));
    verticalLayoutPanel.add(operationsGrid);

    brightnessGreyscaleGrid = new JPanel();
    brightnessGreyscaleGrid.setLayout(new GridLayout(0, 1, 10, 10));

    JLabel brightnessButton = new JLabel("Brighten");
    brightnessButton.setHorizontalAlignment(JLabel.CENTER);
    //toolBench.add(brightnessLabel);
    //brightnessButton.setPreferredSize(new Dimension(200, 30));
    brightnessGreyscaleGrid.add(brightnessButton);

    brightnessSlider = new JSlider(-50, 50, 0);
    brightnessSlider.setMinorTickSpacing(10);
    brightnessSlider.setMajorTickSpacing(50);
    brightnessSlider.setPaintTicks(true);
    brightnessSlider.setPaintLabels(true);
    //toolBench.add(brightnessSlider);
    brightnessGreyscaleGrid.add(brightnessSlider);
    brightnessGreyscaleGrid.add(Box.createVerticalStrut(20));

    JLabel label = new JLabel("Greyscale");
    label.setHorizontalAlignment(JLabel.CENTER);
    //toolBench.add(label);
    brightnessGreyscaleGrid.add(label);
    String[] options = {"Luma", "Intensity", "Value", "Red", "Green", "Blue"};
    greyScaleDropdown = new JComboBox<>(options);
    //toolBench.add(greyScaleDropdown);
    brightnessGreyscaleGrid.add(greyScaleDropdown);

    // Create an empty border to give some space around the panel
    brightnessGreyscaleGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

// Create a new JPanel with a GridBagLayout
    JPanel centerPanel = new JPanel(new GridBagLayout());

// Create a GridBagConstraints object to specify the layout properties
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.PAGE_START;

// Add the original panel to the new panel
    centerPanel.add(brightnessGreyscaleGrid, gbc);

// Add the new panel to the container
    verticalLayoutPanel.add(centerPanel);


    //ImageInfoPanel
    JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, loadSavePanel, verticalLayoutPanel);
    splitPane.setResizeWeight(0.5);
    splitPane.setDividerLocation(0.7);

    toolBench.add(rightSplitPane, BorderLayout.EAST);

    //toolBench.add(verticalLayoutPanel);

    this.add(mainPanel);

    this.setVisible(true);
    this.pack();
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
          ex.printStackTrace();
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
        throw new RuntimeException(e);
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
    splitButton.addActionListener(evt -> {
      try{
        features.processCommands("rgb-split opFile opFile opFile opFile");

        new ImageGUIView(System.out);
        new ImageGUIView(System.out);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    combineButton.addActionListener(evt -> {
      try{
        features.processCommands("rgb-combine opFile opFile opFile opFile");
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
    saveFileButton.addActionListener(evt -> {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showSaveDialog(ImageGUIView.this);
    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      try {
        features.processCommands("save " + file.toString() + " opFile");
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    });
    exitButton.addActionListener(evt -> {
      try {
        features.processCommands("exit");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  @Override
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

  @Override
  public void log(String operation, String message, boolean isPass) throws IOException {
    if (isPass) {
      logPanel.add(new JLabel("Log: " + operation + " completed successfully!\n")
      );
    } else {
      logPanel.add(new JLabel("Log: " + operation + " failed!\n" + message + "\n"));
    }
    imageViewPanel.revalidate();
  }

  @Override
  public void log(String operation, boolean isPass) throws IOException {
    if (isPass) {
      logPanel.add(new JLabel("Log: " + operation + " completed successfully!\n")
      );
    } else {
      logPanel.add(new JLabel("Log: " + operation + " failed!\n"));
    }

    imageViewPanel.revalidate();
  }
}

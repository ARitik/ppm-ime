package controller;


import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import model.ImageOperationsBasicPlus;
import view.AppView;
import view.ImageLogView;

import static org.junit.Assert.assertEquals;

/**
 * This a JUnit test class for AppController controller.
 */
public class ImageCommandControllerTest {

  class MockImageModel implements ImageOperationsBasicPlus {
    private final StringBuilder modelLog;

    public MockImageModel(StringBuilder modelLog) {
      this.modelLog = modelLog;
    }

    @Override
    public void loadImage(InputStream in, String type, String identifier) {
      modelLog.append("ImageType: " + type + " " + "identifier: " + identifier);
    }

    @Override
    public BufferedImage getImage(String identifier) {
      modelLog.append("save: " +
              "identifier of the image to be saved: " + identifier);
      return new BufferedImage(120, 120, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void brighten(int value, String identifier, String brightenIdentifier) {
      modelLog.append("brighten constant: " + value + " " +
              "identifier: " + identifier + " " + "new identifier: " + brightenIdentifier);
    }

    @Override
    public void flip(String orientation, String identifier, String flippedIdentifier) {
      modelLog.append("flip orientation: " + orientation + " " +
              "identifier: " + identifier + " " + "new identifier: " + flippedIdentifier);
    }

    @Override
    public void greyscale(String component, String identifier, String greyScaleIdentifier) {
      modelLog.append("component: " + component + " " +
              "identifier: " + identifier + " " + "new identifier: " + greyScaleIdentifier);
    }

    @Override
    public void greyscale(String identifier, String greyScaleIdentifier) {
      modelLog.append("identifier: " + identifier + " " + "new identifier: " + greyScaleIdentifier);
    }

    @Override
    public void rgbSplit(String identifier, String redIdentifier, String greenIdentifier,
                         String blueIdentifier) {
      modelLog.append("identifier: " + identifier + " " +
              "red identifier: " + redIdentifier + " " + "green identifier: " + greenIdentifier
              + " " + "blue identifier: " + blueIdentifier);
    }

    @Override
    public void rgbCombine(String identifier, String redIdentifier, String greenIdentifier,
                           String blueIdentifier) {
      modelLog.append("identifier: " + identifier + " " +
              "red identifier: " + redIdentifier + " " + "green identifier: " + greenIdentifier
              + " " + "blue identifier: " + blueIdentifier);

    }

    @Override
    public void blur(String identifier, String blurIdentifier) {
      modelLog.append("identifier: " + identifier + " " + "new identifier: " + blurIdentifier);
    }

    @Override
    public void sharpen(String identifier, String sharpenIdentifier) {
      modelLog.append("identifier: " + identifier + " " + "new identifier: " + sharpenIdentifier);
    }

    @Override
    public void sepia(String identifier, String sepiaIdentifier) {
      modelLog.append("identifier: " + identifier + " " + "new identifier: " + sepiaIdentifier);
    }


    @Override
    public void dither(String identifier, String ditherIdentifier) {
      modelLog.append("identifier: " + identifier + " " + "new identifier: " + ditherIdentifier);
    }
  }



  @Test
  public void testWhenInvalidParamsAreEntered() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm");
    controller.run(in);
    assertEquals("Log: load failed!\nInvalid parameters supplied!\n",
            viewLog.toString());
  }


  @Test
  public void testLoadWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample",
            modelLog.toString());
  }

  @Test
  public void testLoadWhenIncorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("laod res/sample.ppm sample");
    controller.run(in);
    assertEquals("Log: laod failed!\n" +
                    "Invalid Command!\n",
            viewLog.toString());
  }


  @Test
  public void testSaveWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "save res/sample-new.ppm sample");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "save: identifier of the image to be saved: sample",
            modelLog.toString());
  }

  @Test
  public void testSaveWhenIncorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\nsae res/sample-new.ppm " +
            "sample");
    controller.run(in);
    assertEquals("Log: load completed successfully!\n" +
                    "Log: sae failed!\n" +
                    "Invalid Command!\n",
            viewLog.toString());
  }


  @Test
  public void testBrightenWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "brighten 10 sample sample-brighter\n" +
            "save res/sample-brighter.ppm " +
            "sample-brighter");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
            "save: identifier of the image to be saved: sample" +
            "brighten constant: 10 identifier: sample new identifier: sample-brighter" +
            "save: identifier of the image to be saved: sample-brighter" +
            "save: identifier of the image to be saved: sample-brighter", modelLog.toString());
  }


  @Test
  public void testDarkenWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "brighten -10 sample sample-darker\n" +
            "save res/sample-darker.ppm " +
            "sample-darker");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "brighten constant: -10 identifier: sample new identifier: sample-darker" +
                    "save: identifier of the image to be saved: sample-darker" +
                    "save: identifier of the image to be saved: sample-darker",
            modelLog.toString());
  }


  @Test
  public void testVerticalFlipWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "vertical-flip sample sample-vertical\n" +
            "save res/sample-vertical.ppm " +
            "sample-vertical");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
            "save: identifier of the image to be saved: sample" +
            "flip orientation: vertical-flip identifier: sample new identifier: sample-vertical" +
            "save: identifier of the image to be saved: sample-vertical" +
            "save: identifier of the image to be saved: sample-vertical", modelLog.toString());
  }

  @Test
  public void testHorizontalFlipWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "horizontal-flip sample sample-horizontal\n" +
            "save res/sample-horizontal.ppm " +
            "sample-horizontal");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
            "save: identifier of the image to be saved: sample" +
            "flip orientation: horizontal-flip identifier: sample new identifier: sample-horizontal" +
            "save: identifier of the image to be saved: sample-horizontal" +
            "save: identifier of the image to be saved: sample-horizontal", modelLog.toString());
  }

  @Test
  public void testHorizontalFlipWhenImageIsVerticallyFlippedWhenCorrectParams() throws
          IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "horizontal-flip sample-vertical sample-vertical-horizontal\n" +
            "save res/sample-vertical-horizontal.ppm " +
            "sample-vertical-horizontal");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "flip orientation: horizontal-flip identifier: sample-vertical " +
                    "new identifier: sample-vertical-horizontal" +
                    "save: identifier of the image to be saved: sample-vertical-horizontal" +
                    "save: identifier of the image to be saved: sample-vertical-horizontal",
            modelLog.toString());
  }

  @Test
  public void testVerticalFlipWhenImageIsHorizontallyFlippedWhenCorrectParams() throws
          IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "vertical-flip sample-horizontal sample-horizontal-vertical\n" +
            "save res/sample-horizontal-vertical.ppm " +
            "sample-horizontal-vertical");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "flip orientation: vertical-flip identifier: sample-horizontal " +
                    "new identifier: sample-horizontal-vertical" +
                    "save: identifier of the image to be saved: sample-horizontal-vertical" +
                    "save: identifier of the image to be saved: sample-horizontal-vertical",
            modelLog.toString());
  }

  @Test
  public void testVerticalFlipWhenImageIsVerticallyFlippedWhenCorrectParams() throws
          IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "vertical-flip sample-vertical sample-vertical-vertical\n" +
            "save res/sample-vertical-vertical.ppm " +
            "sample-vertical-vertical");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "flip orientation: vertical-flip identifier: sample-vertical " +
                    "new identifier: sample-vertical-vertical" +
                    "save: identifier of the image to be saved: sample-vertical-vertical" +
                    "save: identifier of the image to be saved: sample-vertical-vertical",
            modelLog.toString());
  }

  @Test
  public void testHorizontalFlipWhenImageIsHorizontallyFlippedWhenCorrectParams()
          throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "horizontal-flip sample-horizontal sample-horizontal-horizontal\n" +
            "save res/sample-horizontal-horizontal.ppm " +
            "sample-horizontal-horizontal");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "flip orientation: horizontal-flip identifier: sample-horizontal " +
                    "new identifier: sample-horizontal-horizontal" +
                    "save: identifier of the image to be saved: sample-horizontal-horizontal" +
                    "save: identifier of the image to be saved: sample-horizontal-horizontal",
            modelLog.toString());
  }

  @Test
  public void testGreyscaleForValueCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "greyscale value-component sample sample-greyscale-value\n" +
            "save res/sample-greyscale-value.ppm " +
            "sample-greyscale-value");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "component: value identifier: sample new identifier: sample-greyscale-value" +
                    "save: identifier of the image to be saved: sample-greyscale-value" +
                    "save: identifier of the image to be saved: sample-greyscale-value",
            modelLog.toString());
  }

  @Test
  public void testGreyscaleForLumaCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "greyscale luma-component sample sample-greyscale-luma\n" +
            "save res/sample-greyscale-luma.ppm " +
            "sample-greyscale-luma");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "component: luma identifier: sample new identifier: sample-greyscale-luma" +
                    "save: identifier of the image to be saved: sample-greyscale-luma" +
                    "save: identifier of the image to be saved: sample-greyscale-luma",
            modelLog.toString());
  }

  @Test
  public void testGreyscaleForIntensityCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "greyscale intensity-component sample sample-greyscale-intensity\n" +
            "save res/sample-greyscale-intensity.ppm " +
            "sample-greyscale-intensity");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "component: intensity identifier: sample new identifier: sample-greyscale-intensity" +
                    "save: identifier of the image to be saved: sample-greyscale-intensity" +
                    "save: identifier of the image to be saved: sample-greyscale-intensity",
            modelLog.toString());
  }

  @Test
  public void testGreyscaleForRedComponentCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "greyscale red-component sample sample-greyscale-red\n" +
            "save res/sample-greyscale-red.ppm " +
            "sample-greyscale-red");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "component: red identifier: sample new identifier: sample-greyscale-red" +
                    "save: identifier of the image to be saved: sample-greyscale-red" +
                    "save: identifier of the image to be saved: sample-greyscale-red",
            modelLog.toString());
  }

  @Test
  public void testGreyscaleForGreenComponentCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "greyscale green-component sample sample-greyscale-green\n" +
            "save res/sample-greyscale-green.ppm " +
            "sample-greyscale-green");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "component: green identifier: sample new identifier: sample-greyscale-green" +
                    "save: identifier of the image to be saved: sample-greyscale-green" +
                    "save: identifier of the image to be saved: sample-greyscale-green",
            modelLog.toString());
  }

  @Test
  public void testGreyscaleForBlueComponentCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "greyscale blue-component sample sample-greyscale-blue\n" +
            "save res/sample-greyscale-blue.ppm " +
            "sample-greyscale-blue");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "component: blue identifier: sample new identifier: sample-greyscale-blue" +
                    "save: identifier of the image to be saved: sample-greyscale-blue" +
                    "save: identifier of the image to be saved: sample-greyscale-blue",
            modelLog.toString());
  }

  @Test
  public void testRGBSplitCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "rgb-split sample sample-red sample-green sample-blue");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
            "save: identifier of the image to be saved: sample" +
            "component: red identifier: sample new identifier: sample-red" +
            "component: green identifier: sample new identifier: sample-blue" +
            "component: blue identifier: sample new identifier: sample-green" +
            "save: identifier of the image to be saved: sample-red" +
            "save: identifier of the image to be saved: sample-blue" +
            "save: identifier of the image to be saved: sample-green", modelLog.toString());
  }

  @Test
  public void testRGBCombineCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "rgb-combine sample-red-tint sample-red sample-green sample-blue");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
            "save: identifier of the image to be saved: sample" +
            "identifier: sample-red-tint red identifier: sample-red " +
            "green identifier: sample-green blue identifier: sample-blue", modelLog.toString());
  }


  @Test
  public void testLoadWhenImageDoesNotExist() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageCommandController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/cat.txt cat");
    controller.run(in);
    assertEquals("Log: load failed!\n" +
            "File does not exist!\n", viewLog.toString());
  }

  @Test
  public void testIdentifierWithSpecialCharacters() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageCommandController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm $sample");
    controller.run(in);
    assertEquals("Log: load completed successfully!\n", viewLog.toString());
  }


  @Test
  public void testBlurCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "blur sample sample-blur\n" +
            "save res/sample-blur.ppm sample-blur");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "identifier: sample new identifier: sample-blur" +
                    "save: identifier of the image to be saved: sample-blur" +
                    "save: identifier of the image to be saved: sample-blur",
            modelLog.toString());
  }

  @Test
  public void testSharpenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "sharpen sample sample-sharpen\n" +
            "save res/sample-sharpen.ppm sample-sharpen");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "identifier: sample new identifier: sample-sharpen" +
                    "save: identifier of the image to be saved: sample-sharpen" +
                    "save: identifier of the image to be saved: sample-sharpen",
            modelLog.toString());
  }

  @Test
  public void testSepiaCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "sepia sample sample-sepia\n" +
            "save res/sample-sepia.ppm sample-sepia");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "identifier: sample new identifier: sample-sepia" +
                    "save: identifier of the image to be saved: sample-sepia" +
                    "save: identifier of the image to be saved: sample-sepia",
            modelLog.toString());
  }

  @Test
  public void testGreyscaleCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "greyscale sample sample-greyscale\n" +
            "save res/sample-greyscale.ppm sample-greyscale");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "identifier: sample new identifier: sample-greyscale" +
                    "save: identifier of the image to be saved: sample-greyscale" +
                    "save: identifier of the image to be saved: sample-greyscale",
            modelLog.toString());
  }

  @Test
  public void testDitherCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "dither sample sample-dither\n" +
            "save res/sample-dither.ppm sample-dither");
    controller.run(in);
    assertEquals("ImageType: ppm identifier: sample" +
                    "save: identifier of the image to be saved: sample" +
                    "identifier: sample new identifier: sample-dither" +
                    "save: identifier of the image to be saved: sample-dither" +
                    "save: identifier of the image to be saved: sample-dither",
            modelLog.toString());
  }

  @Test
  public void testLoadWhenImageIsJPG() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageCommandController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.jpg sample");
    controller.run(in);
    assertEquals("Log: load completed successfully!\n", viewLog.toString());
  }

  @Test
  public void testLoadWhenImageIsPNG() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageCommandController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.png sample");
    controller.run(in);
    assertEquals("Log: load completed successfully!\n", viewLog.toString());
  }

  @Test
  public void testLoadWhenImageIsBMP() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperationsBasicPlus model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageCommandController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.bmp sample");
    controller.run(in);
    assertEquals("Log: load completed successfully!\n", viewLog.toString());
  }
}
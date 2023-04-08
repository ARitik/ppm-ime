package model;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import controller.AppController;
import controller.ImageCommandController;
import utils.ImageUtil;
import view.AppView;
import view.ImageLogView;

import static org.junit.Assert.assertEquals;

/**
 * This a JUnit test class for ImageOperationsBasic model.
 */
public class RGBOperationsBasicTest {
  @Test
  public void testLoadIfImageExistsLoadSuccessfully() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n" +
            "save res/sample-test.ppm sample-test\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample.ppm", "sample");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test.ppm",
            "sample-test");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testLoadWhenImageFileDoesNotExistLogError() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/x.ppm sample-test\n");
    controller.run(in);
    assertEquals("Log: load failed!\nFile does not exist!\n", out.toString());
  }

  @Test
  public void testLoadWhenScriptFileIsLoaded() throws IllegalStateException, IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    String message = "Script cannot run script! Recursive possibility";
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("run scripts/loadscript.txt\n");
    controller.run(in);
    String message2 = new String(message);
    assertEquals(message, message2);
  }

  @Test
  public void testLoadWhenScriptFileisInvalid() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("run scripts/failscript.txt\n");
    controller.run(in);
    assertEquals("Log: fail failed!\nInvalid Command!\n", out.toString());

  }


  @Test
  public void testSave() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n" +
            "save res/sample-test.ppm sample-test\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample.ppm", "sample");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test.ppm",
            "sample-test");
    assertEquals(sampleImage, loadedSampleImage);
  }


  @Test
  public void testImageBrighten() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "brighten 50 sample sample-test-brighter\n" +
            "save res/sample-test-brighter.ppm sample-test-brighter");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-brighter.ppm",
            "sample-brighter");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-brighter.ppm",
            "sample-test-brighter");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testImageBrightenWhenConstantGreaterThan255GenerateWhiteImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "brighten 350 sample sample-test-super-brighter\n" +
            "save res/sample-test-brighter.ppm sample-test-super-brighter");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-super-brighter.ppm",
            "sample-brighter");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-super-brighter.ppm",
            "sample-test-brighter");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testImageDarkerWhenConstantLessThan255GenerateBlackImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "brighten -350 sample sample-test-super-darker\n" +
            "save res/sample-test-super-darker.ppm sample-test-super-darker");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-super-darker.ppm",
            "sample-darker");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-super-darker.ppm",
            "sample-test-darker");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testImageDarken() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n" + "brighten -100 " +
            "sample-test sample-test-darker\n" +
            "save res/sample-test-darker.ppm sample-test-darker\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-darker.ppm",
            "sample-darker");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-darker.ppm",
            "sample-test" +
                    "-darker");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testVerticalFlipForImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n" + "vertical-flip " +
            "sample-test sample-test-vertical\n" +
            "save res/sample-test-vertical.ppm sample-test-vertical\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-vertical.ppm",
            "sample-vertical");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-vertical.ppm",
            "sample-test" +
                    "-vertical");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testHorizontalFlipForImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n" + "horizontal-flip " +
            "sample-test sample-test-horizontal\n" +
            "save res/sample-test-horizontal.ppm sample-test-horizontal\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-horizontal.ppm",
            "sample-horizontal");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-horizontal.ppm",
            "sample-test" +
                    "-horizontal");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testVerticalFlipForHorizontallyFlippedImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test-horizontal.ppm sample-test-horizontal\n"
            + "vertical-flip sample-test-horizontal sample-test-horizontal-vertical\n"
            + "save res/sample-test-horizontal-vertical.ppm sample-test-horizontal-vertical");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-horizontal-vertical.ppm",
            "sample" +
                    "-horizontal-vertical");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-horizontal-vertical.ppm",
            "sample-test-horizontal-vertical");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testHorizontalFlipForVerticallyFlippedImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test-vertical.ppm sample-test-vertical\n"
            + "horizontal-flip sample-test-vertical sample-test-vertical-horizontal\n"
            + "save res/sample-test-vertical-horizontal.ppm sample-test-vertical-horizontal");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-vertical-horizontal.ppm",
            "sample" +
                    "-vertical-horizontal");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-vertical-horizontal.ppm",
            "sample-test-vertical-horizontal");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testHorizontalFlipForHorizontallyFlippedImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test-horizontal.ppm sample-test-horizontal\n"
            + "horizontal-flip sample-test-horizontal sample-test-horizontal-horizontal\n"
            + "save res/sample-test-horizontal-horizontal.ppm sample-test-horizontal-horizontal");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-horizontal-horizontal.ppm",
            "sample-horizontal-horizontal");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-horizontal-horizontal.ppm",
            "sample-test-horizontal-horizontal");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testVerticalFlipForVerticallyFlippedImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test-vertical.ppm sample-test-vertical\n"
            + "vertical-flip sample-test-vertical sample-test-vertical-vertical\n"
            + "save res/sample-test-vertical-vertical.ppm sample-test-vertical-vertical");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-vertical-vertical.ppm",
            "sample-vertical" +
                    "-vertical");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-vertical-vertical.ppm",
            "sample-test-vertical-vertical");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForValueComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test.ppm sample-test\n"
            + "greyscale value-component sample-test sample-test-greyscale-value\n"
            + "save res/sample-test-greyscale-value.ppm sample-test-greyscale-value");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-greyscale-value.ppm",
            "sample-greyscale" +
                    "-value");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-greyscale-value.ppm",
            "sample" +
                    "-test-greyscale-value");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForIntensityComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test.ppm sample-test\n"
            + "greyscale intensity-component sample-test sample-test-greyscale-intensity\n"
            + "save res/sample-test-greyscale-intensity.ppm sample-test-greyscale-intensity");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-greyscale-intensity.ppm",
            "sample" +
                    "-greyscale-intensity");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-greyscale-intensity.ppm",
            "sample-test-greyscale-intensity");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForLumaComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test.ppm sample-test\n"
            + "greyscale luma-component sample-test sample-test-greyscale-luma\n"
            + "save res/sample-test-greyscale-luma.ppm sample-test-greyscale-luma");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-greyscale-luma.ppm",
            "sample-greyscale" +
                    "-luma");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-greyscale-luma.ppm",
            "sample" +
                    "-test-greyscale-luma");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForRedComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test.ppm sample-test\n"
            + "greyscale red-component sample-test sample-test-greyscale-red\n"
            + "save res/sample-test-greyscale-red.ppm sample-test-greyscale-red");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-greyscale-red.ppm",
            "sample-greyscale" +
                    "-red");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-greyscale-red.ppm",
            "sample" +
                    "-test-greyscale-red");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForGreenComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test.ppm sample-test\n"
            + "greyscale green-component sample-test sample-test-greyscale-green\n"
            + "save res/sample-test-greyscale-green.ppm sample-test-greyscale-green");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-greyscale-green.ppm",
            "sample-greyscale" +
                    "-green");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-greyscale-green.ppm",
            "sample" +
                    "-test-greyscale-green");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForBlueComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test.ppm sample-test\n"
            + "greyscale blue-component sample-test sample-test-greyscale-blue\n"
            + "save res/sample-test-greyscale-blue.ppm sample-test-greyscale-blue");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-greyscale-blue.ppm",
            "sample-greyscale" +
                    "-blue");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-greyscale-blue.ppm",
            "sample-test-greyscale-blue");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testIfGreyScaleImageOnInvalidComponentLogError() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "greyscale wrong-component sample sample-greyscale");
    controller.run(in);
    assertEquals("Log: load completed successfully!\n" +
                    "Log: greyscale failed!\nInvalid Component!\n"
            , out.toString());
  }

  @Test
  public void testToGiveImageARedTint() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test.ppm sample-test\n"
            + "rgb-split sample-test sample-test-red sample-test-green sample-test-blue\n"
            + "brighten 50 sample-test-red sample-test-red\n"
            + "rgb-combine sample-test-red-tint sample-test-red sample-test-green " +
            "sample-test-blue\n"
            + "save res/sample-test-red-tint.ppm sample-test-red-tint");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-red-tint.ppm",
            "sample-red-tint");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-red-tint.ppm",
            "sample-test-red-tint");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testToGiveImageAGreenTint() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test.ppm sample-test\n"
            + "rgb-split sample-test sample-test-red sample-test-green sample-test-blue\n"
            + "brighten 50 sample-test-green sample-test-green\n"
            + "rgb-combine sample-test-green-tint sample-test-red sample-test-green " +
            "sample-test-blue\n"
            + "save res/sample-test-green-tint.ppm sample-test-green-tint");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-green-tint.ppm",
            "sample-green-tint");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-green-tint.ppm",
            "sample-test-green-tint");
    assertEquals(sampleImage, loadedSampleImage);

  }

  @Test
  public void testToGiveImageABlueTint() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test.ppm sample-test\n"
            + "rgb-split sample-test sample-test-red sample-test-green sample-test-blue\n"
            + "brighten 50 sample-test-blue sample-test-blue\n"
            + "rgb-combine sample-test-blue-tint sample-test-red sample-test-green " +
            "sample-test-blue\n"
            + "save res/sample-test-blue-tint.ppm sample-test-blue-tint");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-blue-tint.ppm",
            "sample-blue-tint");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-blue-tint.ppm",
            "sample-test-blue-tint");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testIfImageToBeSavedDoesNotExistLogError() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("save res/fail.ppm fail]n");
    controller.run(in);
    assertEquals("Log: save failed!\nImage does not exist!\n", out.toString());
  }

  @Test
  public void testPPMImageIsLoadedAndSavedAsPng() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n" +
            "save res/sample-test.png sample-test\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample.png", "sample");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test.png",
            "sample-test");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testPPMImageIsLoadedAndSavedAsJpg() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n" +
            "save res/sample-test.jpg sample-test\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample.jpg", "sample");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test.jpg",
            "sample-test");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testPPMImageIsLoadedAndSavedAsBmp() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n" +
            "save res/sample-test.bmp sample-test\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample.bmp", "sample");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test.bmp",
            "sample-test");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testPngImageisLoadedAndSavedAsPpm() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.png sample-test\n" +
            "save res/sample-test.ppm sample-test\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample.ppm", "sample");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test.ppm",
            "sample-test");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testJpgImageisLoadedAndSavedAsPpm() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.jpg sample-test\n" +
            "save res/sample-test.ppm sample-test\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample-test.ppm", "sample");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test.ppm",
            "sample-test");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testBmpImageisLoadedAndSavedAsPpm() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.bmp sample-test\n" +
            "save res/sample-test.ppm sample-test\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample.bmp", "sample");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test.bmp",
            "sample-test");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testPngImageisLoadedAndSavedAsBmp() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.png sample-test\n" +
            "save res/sample-test.bmp sample-test\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample.bmp", "sample");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test.bmp",
            "sample-test");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testColorTransformationGreyscale() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n"
            + "greyscale sample-test sample-test-greyscale-luma\n"
            + "save res/sample-test-greyscale.png sample-test-greyscale-luma");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample-test-greyscale.png",
            "sample-greyscale" +
                    "-luma");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test-greyscale.png",
            "sample" +
                    "-test-greyscale-luma");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testColorTransformationSepia() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample-test.ppm sample-test\n"
            + "sepia sample-test sample-test-sepia\n"
            + "save res/sample-test-sepia.png sample-test-sepia");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample-sepia.png",
            "sample-sepia");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test-sepia.png",
            "sample-sepia");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testDitherOnPng() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n"
            + "dither sample-test sample-test-dither\n"
            + "save res/sample-test-dither.png sample-test-dither");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample-dither.png",
            "sample-greyscale" +
                    "-luma");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test-dither.png",
            "sample" +
                    "-test-greyscale-luma");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testSharpenOnPng() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n"
            + "sharpen sample-test sample-test-dither\n"
            + "save res/sample-sharpen.png sample-test-dither");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample-sharpen.png", "s-sharpen");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-sharpen.png",
            "sample" +
                    "-test-greyscale-luma");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testBlurOnPng() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n"
            + "blur sample-test sample-test-dither\n"
            + "save res/sample-test-blur.png sample-test-dither");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample-blur.png",
            "sample-greyscale" +
                    "-luma");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test-blur.png",
            "sample" +
                    "-test-greyscale-luma");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testBlurOnPpm() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n"
            + "blur sample-test sample-test-blur\n"
            + "save res/sample-test-blur.ppm sample-test-blur");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample-test-blur.ppm", "sample-greyscale-luma");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test-blur.ppm", "sample-test" +
            "-greyscale-luma");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testBlurOnBmp() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.bmp sample-test\n"
            + "blur sample-test sample-test-blur\n"
            + "save res/sample-test-blur.bmp sample-test-blur");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample-blur.png", "sample-greyscale-luma");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test-blur.bmp", "sample-test" +
            "-greyscale-luma");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testBlurOnJpg() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.jpg sample-test\n"
            + "blur sample-test sample-test-blur\n"
            + "save res/sample-test-blur.jpg sample-test-blur");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample-test-blur.jpg", "sample-greyscale-luma");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-test-blur.jpg", "sample-test" +
            "-greyscale-luma");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testSharpenOnJpg() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.jpg sample-test\n"
            + "sharpen sample-test sample-test-dither\n"
            + "save res/sample-sharpen.jpg sample-test-dither");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample-sharpen.jpg", "s-sharpen");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-sharpen.jpg",
            "sample" +
                    "-test-greyscale-luma");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testSharpenOnBmp() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.bmp sample-test\n"
            + "sharpen sample-test sample-test-dither\n"
            + "save res/sample-sharpen.bmp sample-test-dither");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample-sharpen.bmp", "s-sharpen");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-sharpen.bmp",
            "sample" +
                    "-test-greyscale-luma");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testSharpenOnPpm() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    AppView view = new ImageLogView(out);
    AppController controller = new ImageCommandController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample-test\n"
            + "sharpen sample-test sample-test-dither\n"
            + "save res/sample-sharpen.ppm sample-test-dither");
    controller.run(in);
    Image sampleImage = ImageUtil.readImage("res/sample-sharpen.ppm", "s-sharpen");
    Image loadedSampleImage = ImageUtil.readImage("res/sample-sharpen.ppm",
            "sample" +
                    "-test-greyscale-luma");
    assertEquals(sampleImage, loadedSampleImage);
  }

}
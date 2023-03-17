package model;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import controller.ImageAppController;
import utils.ImageUtil;
import view.ImageLogView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * A JUnit test class that tests the ImageOperations model directly.
 */
public class PPMOperationsModelTest {
  @Test
  public void testLoadIfImageExistsLoadSuccessfully() throws IOException {
    ImageOperations model = new PPMOperations();
    model.load("res/sample.ppm", "sample");
    model.save("res/sample-test.ppm", "sample");
    Image sampleImage = ImageUtil.readPPM("res/sample.ppm", "sample");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test.ppm",
            "sample-test");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testLoadWhenImageFileDoesNotExistReturnEmptyMap() throws IOException {
    ImageOperations model = new PPMOperations();
    Reader in = new StringReader("load res/x.ppm sample-test\n");
    Map<String, PPMImage> imageMap = model.load("res/x.ppm", "sample-test");
    assertNotNull(imageMap);
  }

  @Test
  public void testLoadWhenScriptFileIsLoaded() throws IllegalStateException, IOException {
    ImageOperations model = new PPMOperations();
    String message = "Script cannot run script! Recursive possibility";
    String message2 = new String(message);
    assertEquals(message, message2);
  }


  @Test
  public void testSave() throws IOException {
    ImageOperations model = new PPMOperations();
    Reader in = new StringReader("load res/sample.ppm sample-test\n" +
            "save res/sample-test.ppm sample-test\n");
    model.load("res/sample.ppm", "sample-test");
    model.save("res/sample-test.ppm", "sample-test");
    Image sampleImage = ImageUtil.readPPM("res/sample.ppm", "sample");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test.ppm",
            "sample-test");
    assertEquals(sampleImage, loadedSampleImage);
  }


  @Test
  public void testImageBrighten() throws IOException {
    ImageOperations model = new PPMOperations();
    model.load("res/sample.ppm", "sample");
    model.brighten(50, "sample", "sample-test-brighter");
    model.save("res/sample-test-brighter.ppm", "sample-test-brighter");
    Image sampleImage = ImageUtil.readPPM("res/sample-brighter.ppm",
            "sample-brighter");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-brighter.ppm",
            "sample-test-brighter");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testImageBrightenWhenConstantGreaterThan255GenerateWhiteImage() throws IOException {
    ImageOperations model = new PPMOperations();
    model.load("res/sample.ppm", "sample");
    model.brighten(350, "sample", "sample-test-super-brighter");
    model.save("res/sample-test-brighter.ppm", "sample-test-super-brighter");
    Image sampleImage = ImageUtil.readPPM("res/sample-super-brighter.ppm",
            "sample-brighter");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-super-brighter.ppm",
            "sample-test-brighter");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testImageDarkerWhenConstantLessThan255GenerateBlackImage() throws IOException {
    ImageOperations model = new PPMOperations();
    model.load("res/sample.ppm", "sample");
    model.brighten(-350, "sample", "sample-test-super-darker");
    model.save("res/sample-test-super-darker.ppm", "sample-test-super-darker");
    Image sampleImage = ImageUtil.readPPM("res/sample-super-darker.ppm",
            "sample-darker");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-super-darker.ppm",
            "sample-test-darker");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testImageDarken() throws IOException {
    ImageOperations model = new PPMOperations();
    model.load("res/sample.ppm", "sample-test");
    model.brighten(-100, "sample-test", "sample-test-darker");
    model.save("res/sample-test-darker.ppm", "sample-test-darker");
    Image sampleImage = ImageUtil.readPPM("res/sample-darker.ppm",
            "sample-darker");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-darker.ppm",
            "sample-test-darker");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testVerticalFlipForImage() throws IOException {
    ImageOperations model = new PPMOperations();
    model.load("res/sample.ppm", "sample-test");
    model.flip("vertical-flip", "sample-test",
            "sample-test-vertical");
    model.save("res/sample-test-vertical.ppm", "sample-test-vertical");
    Image sampleImage = ImageUtil.readPPM("res/sample-vertical.ppm",
            "sample-vertical");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-vertical.ppm",
            "sample-test-vertical");
    assertEquals(sampleImage, loadedSampleImage);
  }


  @Test
  public void testHorizontalFlipForImage() throws IOException {
    ImageOperations model = new PPMOperations();
    model.load("res/sample.ppm", "sample-test");
    model.flip("horizontal-flip", "sample-test",
            "sample-test-horizontal");
    model.save("res/sample-test-horizontal.ppm", "sample-test-horizontal");
    Image sampleImage = ImageUtil.readPPM("res/sample-horizontal.ppm",
            "sample-horizontal");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-horizontal.ppm",
            "sample-test-horizontal");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForValueComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
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
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load res/sample-test.ppm sample-test\n"
            + "greyscale intensity-component sample-test sample-test-greyscale-intensity\n"
            + "save res/sample-test-greyscale-intensity.ppm sample-test-greyscale-intensity");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-greyscale-intensity.ppm",
            "sample" +
                    "-greyscale-intensity");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-greyscale-intensity.ppm",
            "sample-test-greyscale-intensity");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForLumaComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
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
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
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
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
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
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
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
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\n" +
            "greyscale wrong-component sample sample-greyscale");
    controller.run(in);
    assertEquals("Log: load completed successfully!\n" +
            "Log: greyscale failed!\nInvalid component " +
            "supplied!\n", out.toString());
  }

  @Test
  public void testToGiveImageARedTint() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
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
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
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
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load res/sample-test.ppm sample-test\n"
            + "rgb-split sample-test sample-test-red sample-test-green sample-test-blue\n"
            + "brighten 50 sample-test-blue sample-test-blue\n"
            + "rgb-combine sample-test-blue-tint sample-test-red sample-test-green " +
            "sample-test-blue\n"
            + "save res/sample-test-blue-tint.ppm sample-test-blue-tint");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample-blue-tint.ppm",
            "sample-blue-tint");
    Image loadedSampleImage = ImageUtil.readPPM("res/sample-test-blue-tint.ppm",
            "sample-test-blue-tint");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testIfImageToBeSavedDoesNotExistLogError() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("save res/fail.ppm fail]n");
    controller.run(in);
    assertEquals("Log: save failed!\nImage does not exist!\n", out.toString());
  }

}

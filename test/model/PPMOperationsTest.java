package model;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import controller.ImageAppController;
import utils.ImageUtil;
import view.ImageLogView;

import static org.junit.Assert.assertEquals;

/**
 * This a JUnit test class for PPMOperations model.
 */
public class PPMOperationsTest {
  @Test
  public void testLoad() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample.ppm sample-test\n" +
            "save images/sample-test.ppm sample-test\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("res/sample.ppm", "sample");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test.ppm", "sample-test");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testLoadWhenImageFileDoesNotExist() throws IOException {

  }

  @Test
  public void testLoadWhenScriptFileIsLoaded() throws IllegalStateException {

  }

  @Test
  public void testSave() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample.ppm sample-test\n" +
            "save images/sample-test.ppm sample-test\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample.ppm", "sample");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test.ppm", "sample-test");
    assertEquals(sampleImage, loadedSampleImage);
  }


  @Test
  public void testImageBrighten() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-brighter.ppm", "sample-brighter");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-brighter.ppm", "sample-test" +
            "-brighter");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testImageDarken() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample.ppm sample-test\n" + "brighten -100 " +
            "sample-test sample-test-darker\n" +
            "save images/sample-test-darker.ppm sample-test-darker\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-darker.ppm", "sample-darker");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-darker.ppm", "sample-test" +
            "-darker");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testVerticalFlipForImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample.ppm sample-test\n" + "vertical-flip " +
            "sample-test sample-test-vertical\n" +
            "save images/sample-test-vertical.ppm sample-test-vertical\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-vertical.ppm", "sample-vertical");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-vertical.ppm", "sample-test" +
            "-vertical");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testHorizontalFlipForImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample.ppm sample-test\n" + "horizontal-flip " +
            "sample-test sample-test-horizontal\n" +
            "save images/sample-test-horizontal.ppm sample-test-horizontal\n");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-horizontal.ppm", "sample-horizontal");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-horizontal.ppm", "sample-test" +
            "-horizontal");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testVerticalFlipForHorizontallyFlippedImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample-test-horizontal.ppm sample-test-horizontal\n"
            + "vertical-flip sample-test-horizontal sample-test-horizontal-vertical\n"
            + "save images/sample-test-horizontal-vertical.ppm sample-test-horizontal-vertical");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-horizontal-vertical.ppm", "sample" +
            "-horizontal-vertical");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-horizontal-vertical.ppm",
            "sample-test-horizontal-vertical");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testHorizontalFlipForVerticallyFlippedImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample-test-vertical.ppm sample-test-vertical\n"
            + "horizontal-flip sample-test-vertical sample-test-vertical-horizontal\n"
            + "save images/sample-test-vertical-horizontal.ppm sample-test-vertical-horizontal");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-vertical-horizontal.ppm", "sample" +
            "-vertical-horizontal");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-vertical-horizontal.ppm",
            "sample-test-vertical-horizontal");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testHorizontalFlipForHorizontallyFlippedImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample-test-horizontal.ppm sample-test-horizontal\n"
            + "horizontal-flip sample-test-horizontal sample-test-horizontal-horizontal\n"
            + "save images/sample-test-horizontal-horizontal.ppm " +
            "sample-test-horizontal-horizontal");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-horizontal-horizontal.ppm", "sample" +
            "-horizontal-horizontal");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-horizontal-horizontal.ppm",
            "sample-test-horizontal-horizontal");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testVerticalFlipForVerticallyFlippedImage() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample-test-vertical.ppm sample-test-vertical\n"
            + "vertical-flip sample-test-vertical sample-test-vertical-vertical\n"
            + "save images/sample-test-vertical-vertical.ppm sample-test-vertical-vertical");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-vertical-vertical.ppm", "sample-vertical" +
            "-vertical");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-vertical-vertical.ppm",
            "sample-test-vertical-vertical");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForValueComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample-test.ppm sample-test\n"
            + "greyscale value-component sample-test sample-test-greyscale-value\n"
            + "save images/sample-test-greyscale-value.ppm sample-test-greyscale-value");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-greyscale-value.ppm", "sample-greyscale" +
            "-value");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-greyscale-value.ppm", "sample" +
            "-test-greyscale-value");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForIntensityComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample-test.ppm sample-test\n"
            + "greyscale intensity-component sample-test sample-test-greyscale-intensity\n"
            + "save images/sample-test-greyscale-intensity.ppm sample-test-greyscale-intensity");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-greyscale-intensity.ppm", "sample" +
            "-greyscale-intensity");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-greyscale-intensity.ppm",
            "sample-test-greyscale-intensity");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForLumaComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample-test.ppm sample-test\n"
            + "greyscale luma-component sample-test sample-test-greyscale-luma\n"
            + "save images/sample-test-greyscale-luma.ppm sample-test-greyscale-luma");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-greyscale-luma.ppm", "sample-greyscale" +
            "-luma");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-greyscale-luma.ppm", "sample" +
            "-test-greyscale-luma");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForRedComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample-test.ppm sample-test\n"
            + "greyscale red-component sample-test sample-test-greyscale-red\n"
            + "save images/sample-test-greyscale-red.ppm sample-test-greyscale-red");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-greyscale-red.ppm", "sample-greyscale" +
            "-red");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-greyscale-red.ppm", "sample" +
            "-test-greyscale-red");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForGreenComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample-test.ppm sample-test\n"
            + "greyscale green-component sample-test sample-test-greyscale-green\n"
            + "save images/sample-test-greyscale-green.ppm sample-test-greyscale-green");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-greyscale-green.ppm", "sample-greyscale" +
            "-green");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-greyscale-green.ppm", "sample" +
            "-test-greyscale-green");
    assertEquals(sampleImage, loadedSampleImage);
  }

  @Test
  public void testGreyscaleForBlueComponent() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample-test.ppm sample-test\n"
            + "greyscale blue-component sample-test sample-test-greyscale-blue\n"
            + "save images/sample-test-greyscale-blue.ppm sample-test-greyscale-blue");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-greyscale-blue.ppm", "sample-greyscale" +
            "-blue");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-greyscale-blue.ppm", "sample" +
            "-test-greyscale-blue");
    assertEquals(sampleImage, loadedSampleImage);
  }

  //@Test
  public void testToGiveImageARedTint() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load images/sample-test.ppm sample-test\n"
            + "rgb-split sample-test sample-test-red sample-test-green sample-test-blue\n"
            + "brighten 50 sample-test-red sample-test-red\n"
            + "rgb-combine sample-test-red-tint sample-test-red sample-test-green sample-test-blue"
            + "save images/sample-test-red-tint.ppm sample-test-red-tint");
    controller.run(in);
    Image sampleImage = ImageUtil.readPPM("images/sample-red-tint.ppm", "sample-red-tint");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test-red-tint.ppm", "sample-test" +
            "-red-tint");
    assertEquals(sampleImage, loadedSampleImage);
  }

  //@Test
  public void testToGiveImageAGreenTint() throws IOException {

  }

  //@Test
  public void testToGiveImageABlueTint() throws IOException {

  }

  //@Test
  public void testIfImageToBeSavedDoesNotExist() {

  }

}
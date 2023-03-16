package controller;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import model.Image;
import model.ImageOperations;
import model.PPMImage;
import view.AppView;
import view.ImageLogView;

import static org.junit.Assert.assertEquals;

/**
 * This a JUnit test class for AppController controller.
 */
public class ImageAppControllerTest {

  @Test
  public void testWhenMoreParamsAreEntered() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample sample");
    controller.run(in);
    assertEquals("Log: load failed!\nIncorrect params supplied\n",
            viewLog.toString());
  }


  @Test
  public void testLoadWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample");
    controller.run(in);
    assertEquals("filepath: res/sample.ppm identifier: sample",
            modelLog.toString());
    assertEquals("Log: load completed successfully!\n", viewLog.toString());
  }

  @Test
  public void testLoadWhenIncorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("laod res/sample.ppm sample");
    controller.run(in);
    assertEquals("Log: Invalid Command entered! failed!\n",
            viewLog.toString());
  }


  @Test
  public void testSaveWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("load res/sample.ppm sample\nsave res/sample-new.ppm " +
            "sample");
    controller.run(in);
    assertEquals("filepath: res/sample.ppm identifier: samplesavepath: res/sample-new.ppm " +
            "identifier of the image to be saved: sample", modelLog.toString());
  }

  @Test
  public void testSaveWhenInCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("saev images/sample.ppm sample");
    controller.run(in);
  }

  @Test
  public void testSaveWhenTheImageDoesNotExist() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("save images/x.ppm x");
    controller.run(in);
  }

  @Test
  public void testBrightenWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("brighten 10 sample sample-brighter");
    controller.run(in);
    assertEquals("brighten constant: 10 identifier: sample new identifier: " +
            "sample-brighter", modelLog.toString());
  }

  @Test
  public void testDarkenWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("brighten -10 sample sample-brighter");
    controller.run(in);
    assertEquals("brighten constant: -10 identifier: sample new identifier: sample-brighter",
            modelLog.toString());
    assertEquals("Operation:brighten, Passed:true", viewLog.toString());
  }

  @Test
  public void testVerticalFlipWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("vertical-flip sample sample-vertical");
    controller.run(in);
    assertEquals("flip orientation: vertical-flip identifier: sample new identifier: " +
            "sample-vertical", modelLog.toString());
    assertEquals("Operation:vertical-flip, Passed:true", viewLog.toString());
  }

  @Test
  public void testHorizontalFlipWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("horizontal-flip sample sample-horizontal");
    controller.run(in);
    assertEquals("flip orientation: horizontal-flip identifier: sample new identifier: " +
            "sample-horizontal", modelLog.toString());
    assertEquals("Operation:horizontal-flip, Passed:true", viewLog.toString());
  }

  @Test
  public void testHorizontalFlipWhenImageIsVerticallyFlippedWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("horizontal-flip sample-vertical sample-vertical-horizontal");
    controller.run(in);
    assertEquals("flip orientation: horizontal-flip identifier: sample-vertical new identifier: " +
            "sample-vertical-horizontal", modelLog.toString());
    assertEquals("Operation:horizontal-flip, Passed:true", viewLog.toString());
  }

  @Test
  public void testVerticalFlipWhenImageIsHorizontallyFlippedWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("vertical-flip sample-horizontal sample-horizontal-vertical");
    controller.run(in);
    assertEquals("flip orientation: vertical-flip identifier: sample-horizontal new identifier: " +
            "sample-horizontal-vertical", modelLog.toString());
    assertEquals("Operation:vertical-flip, Passed:true", viewLog.toString());
  }

  @Test
  public void testVerticalFlipWhenImageIsVerticallyFlippedWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("vertical-flip sample-vertical sample-vertical-vertical");
    controller.run(in);
    assertEquals("flip orientation: vertical-flip identifier: sample-vertical new identifier: " +
            "sample-vertical-vertical", modelLog.toString());
    assertEquals("Operation:vertical-flip, Passed:true", viewLog.toString());
  }

  @Test
  public void testHorizontalFlipWhenImageIsHorizontallyFlippedWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("horizontal-flip sample-horizontal sample-horizontal-horizontal");
    controller.run(in);
    assertEquals("flip orientation: horizontal-flip identifier: sample-horizontal new identifier:" +
            " sample-horizontal-horizontal", modelLog.toString());
    assertEquals("Operation:horizontal-flip, Passed:true", viewLog.toString());
  }

  @Test
  public void testGreyscaleForValueCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("greyscale value-component sample sample-greyscale");
    controller.run(in);
    assertEquals("component: value identifier: sample new identifier: sample-greyscale",
            modelLog.toString());
    assertEquals("Operation:greyscale, Passed:true", viewLog.toString());
  }

  @Test
  public void testGreyscaleForLumaCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("greyscale luma-component sample sample-greyscale");
    controller.run(in);
    assertEquals("component: luma identifier: sample new identifier: sample-greyscale",
            modelLog.toString());
    assertEquals("Operation:greyscale, Passed:true", viewLog.toString());
  }

  @Test
  public void testGreyscaleForIntensityCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("greyscale intensity-component sample sample-greyscale");
    controller.run(in);
    assertEquals("component: intensity identifier: sample new identifier: sample-greyscale",
            modelLog.toString());
    assertEquals("Operation:greyscale, Passed:true", viewLog.toString());
  }

  @Test
  public void testGreyscaleForRedComponentCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("greyscale red-component sample sample-greyscale");
    controller.run(in);
    assertEquals("component: red identifier: sample new identifier: sample-greyscale",
            modelLog.toString());
    assertEquals("Operation:greyscale, Passed:true", viewLog.toString());
  }

  @Test
  public void testGreyscaleForGreenComponentCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("greyscale green-component sample sample-greyscale");
    controller.run(in);
    assertEquals("component: green identifier: sample new identifier: sample-greyscale",
            modelLog.toString());
    assertEquals("Operation:greyscale, Passed:true", viewLog.toString());
  }

  @Test
  public void testGreyscaleForBlueComponentCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("greyscale blue-component sample sample-greyscale");
    controller.run(in);
    assertEquals("component: blue identifier: sample new identifier: sample-greyscale",
            modelLog.toString());
    assertEquals("Operation:greyscale, Passed:true", viewLog.toString());
  }

  @Test
  public void testRGBSplitCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("rgb-split sample sample-red sample-green sample-blue");
    controller.run(in);
    assertEquals("identifier: sample red identifier: sample-red green identifier: sample-green " +
            "blue identifier: sample-blue", modelLog.toString());
    assertEquals("Operation:rgb-split, Passed:true", viewLog.toString());
  }

  @Test
  public void testRGBCombineCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new ImageLogView(viewLog);
    ImageAppController controller = new ImageAppController(model, view);
    Reader in = new StringReader("rgb-combine sample-red-tint sample-red sample-green sample-blue");
    controller.run(in);
    assertEquals("identifier: sample-red-tint red identifier: sample-red green identifier: " +
            "sample-green blue identifier: sample-blue", modelLog.toString());
    assertEquals("Operation:rgb-combine, Passed:true", viewLog.toString());
  }

  //@Test
  public void testBrightenWhenBrightenConstantIsNotInteger() throws IOException {

  }

  // @Test
  public void testWhenUserSendsImageOtherThanPPM() throws IOException {

  }

  //@Test
  public void testWhenOperationPerformedWithoutLoadingImage() {

  }

  //@Test
  public void testWhenFilenamePassedIsEmpty() {

  }

  //@Test
  public void testWhenIdentifierPassedIsEmpty() {

  }

  //@Test
  public void testWhenImagePathIsIncorrect() {

  }

  //@Test
  public void testIdentifierWithSpecialCharacters() {

  }

  //@Test
  public void testIfImageToBeSavedDoesNotExist() {

  }


  class MockImageModel implements ImageOperations {
    private final StringBuilder modelLog;

    public MockImageModel(StringBuilder modelLog) {
      this.modelLog = modelLog;
    }

    @Override
    public Map<String, PPMImage> load(String filePath, String identifier) {
      modelLog.append("filepath: " + filePath + " " + "identifier: " + identifier);
      return null;
    }

    @Override
    public Image save(String savePath, String imageToBeSaved) throws IOException {
      modelLog.append("savepath: " + savePath + " " +
              "identifier of the image to be saved: " + imageToBeSaved);
      return null;
    }

    @Override
    public Image brighten(int value, String identifier, String brightenIdentifier) {
      modelLog.append("brighten constant: " + value + " " +
              "identifier: " + identifier + " " + "new identifier: " + brightenIdentifier);
      return null;
    }

    @Override
    public Image flip(String orientation, String identifier, String flippedIdentifier) {
      modelLog.append("flip orientation: " + orientation + " " +
              "identifier: " + identifier + " " + "new identifier: " + flippedIdentifier);
      return null;
    }

    @Override
    public Image greyscale(String component, String identifier, String greyScaleIdentifier) {
      modelLog.append("component: " + component + " " +
              "identifier: " + identifier + " " + "new identifier: " + greyScaleIdentifier);
      return null;
    }

    @Override
    public List<Image> rgbSplit(String identifier, String redIdentifier, String greenIdentifier,
                                String blueIdentifier) {
      modelLog.append("identifier: " + identifier + " " +
              "red identifier: " + redIdentifier + " " + "green identifier: " + greenIdentifier
              + " " + "blue identifier: " + blueIdentifier);
      return null;
    }

    @Override
    public Image rgbCombine(String identifier, String redIdentifier, String greenIdentifier,
                            String blueIdentifier) {
      modelLog.append("identifier: " + identifier + " " +
              "red identifier: " + redIdentifier + " " + "green identifier: " + greenIdentifier
              + " " + "blue identifier: " + blueIdentifier);
      return null;

    }
  }
}


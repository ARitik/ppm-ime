package controller;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import model.ImageOperations;
import view.AppView;

import static org.junit.Assert.*;

public class AppControllerTest {
  @Test
  public void testLoadWhenCorrectParams() throws IOException {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    ImageOperations model = new MockImageModel(modelLog);
    AppView view = new MockImageView(viewLog);
    AppController controller = new AppController(model,view);
    Reader in = new StringReader("load images/sample.ppm sample");
    controller.go(in);
    assertEquals("filepath: images/sample.ppm identifier: sample",modelLog.toString());
    assertEquals("Operation:load, Passed:true",viewLog.toString());
  }

  class MockImageView implements AppView {

    StringBuilder viewLog;
    public MockImageView(StringBuilder viewLog) {
      this.viewLog = viewLog;
    }
    @Override
    public void log(String operation, boolean isPass) throws IOException {
      viewLog.append("Operation:" + operation + ", " + "Passed:" + isPass);
    }
  }

  class MockImageModel implements ImageOperations {
    private final StringBuilder modelLog;
    public MockImageModel(StringBuilder modelLog) {
      this.modelLog = modelLog;
    }

    @Override
    public void load(String filePath, String identifier) {
      modelLog.append("filepath: " + filePath + " " + "identifier: " + identifier);
    }

    @Override
    public void save(String savePath, String imageToBeSaved) throws IOException {
      modelLog.append("savepath: " + savePath + " " +
              "identifier of the image to be saved: " + imageToBeSaved);
    }

    @Override
    public void brighten(int value, String identifier, String brightenIdentifier) {
      modelLog.append("brighten constant: " + value + " " +
              "identifier: " + identifier + " " + "new identifier: " + brightenIdentifier);
    }

    @Override
    public void flip(String orientation, String identifier, String flippedIdentifier) {
      modelLog.append("brighten constant: " + orientation + " " +
              "identifier: " + identifier + " " + "new identifier: " + flippedIdentifier);
    }

    @Override
    public void greyscale(String component, String identifier, String greyScaleIdentifier) {
      modelLog.append("component: " + component + " " +
              "identifier: " + identifier + " " + "new identifier: " + greyScaleIdentifier);
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
  }
}


package controller;

import java.io.IOException;

import model.ImageOperations;

import static org.junit.Assert.*;

public class AppControllerTest {

}

class MockImageModel implements ImageOperations {
  private StringBuilder log;

  @Override
  public void load(String filePath, String identifier) {
    log.append("filepath: " + filePath + " " + "identifier: " + identifier);
  }

  @Override
  public void save(String savePath, String imageToBeSaved) throws IOException {
    log.append("savepath: " + savePath + " " +
            "identifier of the image to be saved: " + imageToBeSaved);
  }

  @Override
  public void brighten(int value, String identifier, String brightenIdentifier) {
    log.append("brighten constant: " + value + " " +
            "identifier: " + identifier + " " + "new identifier: " + brightenIdentifier);
  }

  @Override
  public void flip(String orientation, String identifier, String flippedIdentifier) {
    log.append("brighten constant: " + orientation + " " +
            "identifier: " + identifier + " " + "new identifier: " + flippedIdentifier);
  }

  @Override
  public void greyscale(String component, String identifier, String greyScaleIdentifier) {
    log.append("component: " + component + " " +
            "identifier: " + identifier + " " + "new identifier: " + greyScaleIdentifier);
  }

  @Override
  public void rgbSplit(String identifier, String redIdentifier, String greenIdentifier,
                       String blueIdentifier) {
    log.append("identifier: " + identifier + " " +
            "red identifier: " + redIdentifier + " " + "green identifier: " + greenIdentifier
    + " " + "blue identifier: " + blueIdentifier);
  }

  @Override
  public void rgbCombine(String identifier, String redIdentifier, String greenIdentifier,
                         String blueIdentifier) {
    log.append("identifier: " + identifier + " " +
            "red identifier: " + redIdentifier + " " + "green identifier: " + greenIdentifier
            + " " + "blue identifier: " + blueIdentifier);

  }
}


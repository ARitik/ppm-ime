package model;

import utils.ImageUtil;

public class PPMOperations implements ImageOperations {
  Image image;

  public PPMOperations() {
    this.image = null;
  }

  @Override
  public void load(String filePath, String identifier) {
    image = ImageUtil.readPPM(filePath, identifier);
    image.getMaxValue();
  }

  @Override
  public void save(String savePath, String saveAsFileName) {

  }

  @Override
  public void brighten(int value, String identifier, String brightenIdentifier) {

  }

  @Override
  public void flip(String orientation, String identifier, String flippedIdentifier) {

  }

  @Override
  public void greyscale(String component, String identifier, String greyScaleIdentifier) {

  }

  @Override
  public void rgbSplit(String identifier, String tint) {

  }

  @Override
  public void rgbCombine(String identifierOne, String identifierTwo) {

  }
}

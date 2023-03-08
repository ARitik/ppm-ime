package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.ImageUtil;

public class PPMOperations implements ImageOperations {
  PPMImage image;
  Map<String, PPMImage> newImages;

  public PPMOperations() {
    this.image = null;
    this.newImages = new HashMap<String, PPMImage>();
  }

  @Override
  public void load(String filePath, String identifier) {
    image = (PPMImage) ImageUtil.readPPM(filePath, identifier);
//    image.getMaxValue();
  }

  @Override
  public void save(String savePath, String imageIdentifier) throws IOException {
    ImageUtil.savePPM(savePath, newImages.get(imageIdentifier));
  }

  @Override
  public void brighten(int value, String identifier, String brightenIdentifier) {
    System.out.println(image.getWidth());
    PPMImage.ImageBuilder newImageBuilder = PPMImage
            .getBuilder()
            .identifier(identifier)
            .width(image.getWidth())
            .height(image.getHeight())
            .maxValue(Math.min(255, image.getMaxValue() + value));


    for (int row = 0; row < image.getHeight(); row++) {
      for (int column = 0; column < image.getWidth(); column++) {
        int r = image.getRPixel(row,column);
        int g = image.getGPixel(row,column);
        int b = image.getBPixel(row,column);
        newImageBuilder.R(Math.min(r + value,255), row,column);
        newImageBuilder.G(Math.min(g + value,255), row,column);
        newImageBuilder.B(Math.min(b + value,255), row,column);
      }
    }
    newImages.put(brightenIdentifier,newImageBuilder.build());
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

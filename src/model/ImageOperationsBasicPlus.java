package model;

public interface ImageOperationsBasicPlus extends ImageOperations {
  void blur(String identifier, String blurIdentifier);

  void sharpen(String identifier, String sharpenIdentifier);

  void sepia(String identifier, String sepiaIdentifier);

  void greyscale(String identifier, String greyScaleIdentifier);

  void dither(String identifier, String greyScaleIdentifier);
}
